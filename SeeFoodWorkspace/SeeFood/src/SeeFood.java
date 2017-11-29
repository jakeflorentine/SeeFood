import view.SeefoodView;

/**
 * This is the license header. All code is IP of SeeFood Inc.
 */

/**
 * @author Jake Florentine
 *
 */
public class SeeFood {

	private void loadSwtJar() {
		String osName = System.getProperty("os.name").toLowerCase();
		String osArch = System.getProperty("os.arch").toLowerCase();
		String swtFileNameOsPart = osName.contains("win") ? "win32"
				: osName.contains("mac") ? "macosx"
						: osName.contains("linux") || osName.contains("nix") ? "linux_gtk" : ""; // throw new
																									// RuntimeException("Unknown
																									// OS name:
																									// "+osName)

		String swtFileNameArchPart = osArch.contains("64") ? "x64" : "x86";
		String swtFileName = "swt_" + swtFileNameOsPart + "_" + swtFileNameArchPart + ".jar";
		System.out.println(swtFileName);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SeeFood s = new SeeFood();
		s.loadSwtJar();
		SeefoodView.createContent();
	}

}
