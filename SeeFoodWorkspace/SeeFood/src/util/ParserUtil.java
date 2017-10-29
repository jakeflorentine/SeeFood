package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

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

		return (String[]) validFiles.toArray();
	}

	private static boolean isValid(String filename) {
		List<String> filetypes = new ArrayList<>(Arrays.asList("xyz", "abc"));
		String extension = filename.substring(filename.lastIndexOf('.'));
		return filetypes.contains(extension.toLowerCase());
//		try {
//			Image image = new Image(Display.getCurrent(), filename);
//			// SWT was able to use this file as an image
//			return true;
//		} catch (Exception e) {
//			// Not a valid file type or not a valid image type
//			return false;
//		}
	}

}
