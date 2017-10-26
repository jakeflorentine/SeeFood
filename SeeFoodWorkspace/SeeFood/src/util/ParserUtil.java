package util;

import java.util.ArrayList;

public class ParserUtil {

	public static String[] parseFiles(String[] filenames) {
		ArrayList<String> invalidFiles = new ArrayList<String>();
		ArrayList<String> validFiles = new ArrayList<String>();

		for (String filename : filenames) {
			if (isValid(filename)) {
				validFiles.add(filename);
			} else {
				invalidFiles.add(filename);
			}
		}

		return null;
	}

	private static boolean isValid(String filename) {
		return true;
	}

}
