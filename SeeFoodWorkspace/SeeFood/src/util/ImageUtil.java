package util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public class ImageUtil {
	public static String[] filterExtensions = { "*.png", "*.jpg", "*.jpeg" };

	public static Image resize(Image image, Rectangle compBounds) {
		System.out.println("Created image.  Width: " + compBounds.width + " Height: " + compBounds.height);
		Rectangle imageBounds = image.getBounds();
		int width = 400, height = 424;
		// check to see whether the image is wider or taller
		// set the corresponding dimension to the composites'
		if (imageBounds.width > imageBounds.height) {
			width = compBounds.width;
			height = getRemainingBound(imageBounds, compBounds, true);
		} else {
			height = compBounds.height;
			width = getRemainingBound(imageBounds, compBounds, false);
		}

		Image scaled = new Image(Display.getCurrent(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, width, height);
		gc.dispose();
		image.dispose();

		return scaled;
	}

	/**
	 * Calculates the remaining bound in order to display the image within the
	 * specified composite bounds. Specify height or width with the "getHeight"
	 * value: true if the height is desired.
	 * 
	 * @param imageBounds
	 *            Bounds of the original image
	 * @param compBounds
	 *            Bounds of the available composite space
	 * @param getHeight
	 *            True if the new height is to be returned, false otherwise.
	 * @return The remaining bound.
	 */
	public static int getRemainingBound(Rectangle imageBounds, Rectangle compBounds, boolean getHeight) {
		int remainingBound = 0;
		double pct;

		if (getHeight) {
			pct = (double) compBounds.width / (double) imageBounds.width;
			remainingBound = (int) (pct * imageBounds.height);
		} else {
			pct = (double) compBounds.height / (double) imageBounds.height;
			remainingBound = (int) (pct * imageBounds.width);
		}

		return remainingBound;
	}

	/**
	 * Return a byte array for the given image
	 * 
	 * @param image
	 * @return
	 */
	public static byte[] getByteArray(Image image) {
		return image.getImageData().data;

	}
}
