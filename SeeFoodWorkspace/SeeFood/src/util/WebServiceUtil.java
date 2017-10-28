package util;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class WebServiceUtil {

	public static boolean connectToServer() {
		try {
			JSch jsch = new JSch();

			String user = "ec2-user";
			String host = "ec2-54-156-251-225.compute-1.amazonaws.com";
			int port = 22;
			String privateKey = "/Users/jakeflorentine/Downloads/florentine-ssh.pem";

			jsch.addIdentity(privateKey);
			System.out.println("identity added ");

			Session session = jsch.getSession(user, host, port);
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
}
