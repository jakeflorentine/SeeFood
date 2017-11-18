package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
	private static Scanner sc;

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

	public static SeefoodImage[] getResults(String parentFilePath, String[] files) {
		List<SeefoodImage> results = new ArrayList<SeefoodImage>();

		try {
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd("seefood/CEG4110-Fall2017/temp/");

			for (String s : files) {
				String path = Paths.get(parentFilePath, s).toString();
				File f = new File(path);
				channelSftp.put(new FileInputStream(f), f.getName());
				channelExec = (ChannelExec) session.openChannel("exec");
				in = channelExec.getInputStream();
				channelExec.setCommand(
						"cd ~/seefood/CEG4110-Fall2017/; python find_food.py /home/ec2-user/seefood/CEG4110-Fall2017/temp/"
								+ s);
				channelExec.connect();

				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String line;
				double confidence = 0;
				double tensor1 = 0;
				double tensor2 = 0;
				boolean isFood = false;
				while ((line = reader.readLine()) != null) {
					if (line.startsWith("[[")) {
						// remove nonsense from the string
						line = line.substring(3);
						line = line.replace("]]", "");
						line = line.replace("  ", ",");
						// get array of values in line
						String values[] = line.split(",");

						// assign tensor values
						tensor1 = Double.parseDouble(values[0]);
						tensor2 = Double.parseDouble(values[1]);

						// tensor1 is greater if the image is of food
						if (tensor1 > tensor2) {
							isFood = true;
						} else {
							// swap the tensor values in order to get appropriate confidence
							double tempTens = tensor1;
							tensor1 = tensor2;
							tensor2 = tempTens;
						}

						// decide how confident we are with conclusion
						confidence = tensor1 - tensor2;

					}

				}
				results.add(new SeefoodImage(confidence, new Image(Display.getCurrent(), path), isFood));
				if (isFood == true) {
					System.out.println("Seefood has determined that the picture is of food :: " + confidence);
				} else {
					System.out.println("Seefood has determined that the picture is not of food :: " + confidence);
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
			for(ChannelSftp.LsEntry entry : list) {
				String filename = entry.getFilename();
				String path = "gallery/" + filename;
			    channelSftp.get(entry.getFilename(), path);
			    
			    file = new File(path);
			    String test = file.getAbsolutePath();
			    double confidence = Double.parseDouble( filename.substring(filename.lastIndexOf('-') + 1, filename.lastIndexOf('.')));
			    Image image = new Image(Display.getCurrent(), path);
			    SeefoodImage sfi = new SeefoodImage(confidence, image, true);
			    images.add(sfi);
			}
			
			channelSftp.cd("/home/ec2-user/seefood/CEG4110-Fall2017/NotFood/");
			list = channelSftp.ls("*.jpg");
			for(ChannelSftp.LsEntry entry : list) {
				String filename = entry.getFilename();
				String path = "gallery/" + filename;
			    channelSftp.get(entry.getFilename(), path);
			    
			    file = new File(path);
			    double confidence = Double.parseDouble( filename.substring(filename.lastIndexOf('-') + 1, filename.lastIndexOf('.')));
			    Image image = new Image(Display.getCurrent(), file.getAbsolutePath());
			    SeefoodImage sfi = new SeefoodImage(confidence, image, false);
			    images.add(sfi);
			}
		} catch (Exception e) {}
		

		return images;
	}
}
