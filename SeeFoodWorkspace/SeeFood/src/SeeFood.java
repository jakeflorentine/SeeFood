import view.SeefoodView;

/**
 * This is the license header. All code is IP of SeeFood Inc.
 */

/**
 * @author Jake Florentine
 *
 */
public class SeeFood {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		loadSwtJar();
		SeefoodView.createContent();
	}

	private static void loadSwtJar() {
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

		// try {
		// URLClassLoader classLoader = (URLClassLoader) getClass().getClassLoader();
		// Method addUrlMethod = URLClassLoader.class.getDeclaredMethod("addURL",
		// URL.class);
		// addUrlMethod.setAccessible(true);
		//
		// URL swtFileUrl = new URL("rsrc:"+swtFileName); // I am using Jar-in-Jar class
		// loader which understands this URL; adjust accordingly if you don't
		// addUrlMethod.invoke(classLoader, swtFileUrl);
		// }
		// catch(Exception e) {
		// throw new RuntimeException("Unable to add the SWT jar to the class path:
		// "+swtFileName, e);
		// }
	}

}
