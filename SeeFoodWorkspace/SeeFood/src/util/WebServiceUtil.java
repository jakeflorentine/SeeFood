package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import custom.objects.SeefoodImage;

public class WebServiceUtil {
	private static Session session;
	private static Channel channel;
	private static ChannelSftp channelSftp;
	private static ChannelExec channelExec;
	private static InputStream in;
	private static Socket clientSocket;
	private final static double CONFIDENCE_SPAN = 5.0;

	public static boolean connectToServer() {
		try {
			JSch jsch = new JSch();

			String user = "ec2-user";
			String host = "ec2-54-156-251-225.compute-1.amazonaws.com";
			int port = 22;
			File privateKey = new File("florentine-ssh.pem");
			String test = privateKey.getAbsolutePath();
			jsch.addIdentity(privateKey.getAbsolutePath());
			System.out.println("identity added ");

			session = jsch.getSession(user, host, port);
			System.out.println("session created.");

			// disabling StrictHostKeyChecking may help to make connection but makes it
			// insecure
			// see
			// http://stackoverflow.com/questions/30178936/jsch-sftp-security-with-session-setconfigstricthostkeychecking-no
			//
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			session.connect();
			System.out.println("We have established connection with Amazon EC2.");

			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	/**
	 * Open a new socket connection
	 */
	private static void openConnection() {
		String hostName = "ec2-54-156-251-225.compute-1.amazonaws.com";
		int portNumber = 2025;
		try {
			// open a socket
			clientSocket = new Socket(hostName, portNumber);
		} catch (Exception e) {
			System.out.println(e + " Unable to open socket.");
		}
	}

	/**
	 * If the first tensor value is greater than the second, the image is food.
	 * 
	 * @param tensor1
	 * @param tensor2
	 * @return
	 */
	private static boolean tensorIsFood(double tensor1, double tensor2) {
		return tensor1 > tensor2;
	}

	/**
	 * Pass in a tensor response in the form: [[t1,t2]]
	 * 
	 * @param response
	 * @return an array of string values
	 */
	private static double[] parseTensorResponse(String response) {
		if (response == null || response.isEmpty()) {
			return null;
		}

		if (response.startsWith("[[")) {
			// remove nonsense from the string
			response = response.substring(3);
			response = response.replace("]]", "");
			// a response contains 2 spaces if the second number isn't negative, otherwise
			// we need to account
			if (response.contains("  ")) {
				response = response.replace("  ", ",");
			} else {
				response = response.replace(" ", ",");
			}

			// get array of values in line
			String values[] = response.split(",");

			// assign tensor values
			double tensor1 = Double.parseDouble(values[0]);
			double tensor2 = Double.parseDouble(values[1]);
			// add values to double array
			double[] tensorDoubles = { tensor1, tensor2 };
			return tensorDoubles;
		}

		return null;
	}

	/**
	 * 
	 * @param tensor1
	 * @param tensor2
	 * @return
	 */
	private static double getConfidenceLevel(double tensor1, double tensor2) {
		double confidence = tensor1 - tensor2;
		confidence = confidence / CONFIDENCE_SPAN;

		return confidence;
	}

	public static SeefoodImage[] getResults(String parentFilePath, String[] files) {
		List<SeefoodImage> results = new ArrayList<SeefoodImage>();
		long t = System.currentTimeMillis();
		long end = t + 20000;		
		
		try {
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd("seefood/CEG4110-Fall2017/temp/");

			for (String s : files) {
				// Open a new connection for each request to circumvent the receiving buffer
				// issue on the python script
				openConnection();

				// transfer file to server
				String path = Paths.get(parentFilePath, s).toString();
				File f = new File(path);
				channelSftp.put(new FileInputStream(f), f.getName());
				// channelExec = (ChannelExec) session.openChannel("exec");
				// in = channelExec.getInputStream();
				// channelExec.setCommand(
				// "cd ~/seefood/CEG4110-Fall2017/; python find_food.py
				// /home/ec2-user/seefood/CEG4110-Fall2017/temp/"
				// + s);
				// channelExec.connect();
				try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
						PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);) {
					String imagePath = "temp/" + f.getName();
					System.out.println("Requesting analysis for: " + imagePath);

					// Send the image path to the server script
					// In the app version of this, you'll just out.println() for each path string we
					// make in getResults()

					// appending an extra space for testing :: Jake

					String osName = System.getProperty("os.name").toLowerCase();
					if (osName.contains("win")) {
						// WINDOWS VERSION
						out.println("/home/ec2-user/seefood/CEG4110-Fall2017/" + imagePath);
					} else {
						// EVERYTHING ELSE VERSION
						out.println(imagePath + " ");
					}

					boolean received = false;
					while (System.currentTimeMillis() < end) {
						String response;
						double confidence = 0;
						double tensor1 = 0;
						double tensor2 = 0;
						boolean isFood = false;
						while ((response = in.readLine()) != null) {
							System.out.println("receiving");
							// response is the value we will end up parsing to compute confidence

							if (response.startsWith("[[")) {
								// remove nonsense from the string
								double values[] = parseTensorResponse(response);

								// assign tensor values
								tensor1 = values[0];
								tensor2 = values[1];

								// check if the image is of food based on the doubles
								isFood = tensorIsFood(tensor1, tensor2);

								// rearrange the tensor values to get an appropriate confidence measure
								if (!isFood) {
									// swap the tensor values in order to get appropriate confidence
									double tempTens = tensor1;
									tensor1 = tensor2;
									tensor2 = tempTens;
								}

								// decide how confident we are with conclusion
								confidence = getConfidenceLevel(tensor1, tensor2);

							} else {
								System.out.println("strange response " + response.toString());
							}
							// create a seefood image using a file path
							boolean createdImage = results
									.add(new SeefoodImage(confidence, new File(parentFilePath + "/" + s), isFood));

							if (createdImage) {
								System.out.println("Created SeeFood Image | Is Food = " + isFood + "  |  Confidence = "
										+ confidence);
							}

							received = true;
						}
						if (received)
							clientSocket.close();
						break;
					}
				}
			}

			return results.toArray(new SeefoodImage[files.length]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * 
	 * @return
	 * @throws SftpException
	 */
	public static List<SeefoodImage> getImages() throws SftpException {
		List<SeefoodImage> images = new ArrayList<SeefoodImage>();
		try {
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd("/home/ec2-user/seefood/CEG4110-Fall2017/Food/");
			Vector<ChannelSftp.LsEntry> list = channelSftp.ls("*.jpg");
			File file;
			Collections.reverse(list);
			int total = 0;
			
			for (ChannelSftp.LsEntry entry : list) {
				if(total == 15) {
					break;
				}
				String filename = entry.getFilename();
				String path = "gallery/" + filename;
				channelSftp.get(entry.getFilename(), path);

				file = new File(path);
				String test = file.getAbsolutePath();
				double confidence = Double
						.parseDouble(filename.substring(filename.lastIndexOf('-') + 1, filename.lastIndexOf('.')));
				Image image = new Image(Display.getCurrent(), path);
				SeefoodImage sfi = new SeefoodImage(confidence, image, true);
				images.add(sfi);
				total++;
			}

			channelSftp.cd("/home/ec2-user/seefood/CEG4110-Fall2017/NotFood/");
			list = channelSftp.ls("*.jpg");
			Collections.reverse(list);
			total = 0;
			for (ChannelSftp.LsEntry entry : list) {
				if(total == 15) {
					break;
				}
				String filename = entry.getFilename();
				String path = "gallery/" + filename;
				channelSftp.get(entry.getFilename(), path);

				file = new File(path);
				double confidence = Double
						.parseDouble(filename.substring(filename.lastIndexOf('-') + 1, filename.lastIndexOf('.')));
				Image image = new Image(Display.getCurrent(), file.getAbsolutePath());
				SeefoodImage sfi = new SeefoodImage(confidence, image, false);
				images.add(sfi);
				total++;
			}
		} catch (Exception e) {
		}

		return images;
	}

	// public static JSONArray createJsonArray(String[][] jsonAsString) {
	// JSONArray json = new JSONArray();
	// for (int i = 0; i < jsonAsString.length; i++) {
	// for (int j = 0; j < jsonAsString[i].length; j++) {
	// JSONObject jsonObject = new JSONObject();
	// jsonObject.put("image_name", "some/image/path");
	// json.add(jsonObject);
	// }
	// }
	// return json;
	//
	// }
}
