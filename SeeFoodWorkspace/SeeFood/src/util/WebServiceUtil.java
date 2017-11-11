package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
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
			// the channel is what we will want to use to send commands
			
//	        int exitStatus = channelExec.getExitStatus();
//	        channelExec.disconnect();
//	        session.disconnect();
//	        if(exitStatus < 0){
//	            System.out.println("Done, but exit status not set!");
//	        }
//	        else if(exitStatus > 0){
//	            System.out.println("Done, but with error!");
//	        }
//	        else{
//	            System.out.println("Done!");
//	        }
			
			// Channel channel=session.openChannel("shell");
			// channel.setInputStream(System.in);
			// channel.setOutputStream(System.out);
			// channel.connect(3*1000);
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
			
			for(String s : files) {
				String path = Paths.get(parentFilePath, s).toString();
				File f = new File(path);
				channelSftp.put(new FileInputStream(f), f.getName());
				channelExec = (ChannelExec)session.openChannel("exec");
				in = channelExec.getInputStream();
				channelExec.setCommand("cd ~/seefood/CEG4110-Fall2017/; python find_food.py /home/ec2-user/seefood/CEG4110-Fall2017/temp/" + s);
				channelExec.connect();
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String line;
				double confidence = 0;
				boolean isFood = false;
				while ((line = reader.readLine()) != null)
		        {
					if(line.startsWith("[[")) {
						// Skip "[[ " because it breaks shit
						line = line.substring(3);
						sc = new Scanner(line);
						sc.useLocale(Locale.getDefault());
		            	confidence = sc.nextDouble();
		            	isFood = reader.readLine().contains("yes");
		            }
		            
		        }
				results.add(new SeefoodImage(confidence, new Image(Display.getCurrent(), path), isFood));
			}
			
			return results.toArray(new SeefoodImage[files.length]);
		}
		catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @return
	 */
	public List<SeefoodImage> getImages() {
		List<SeefoodImage> images = null;

		return images;
	}
}
