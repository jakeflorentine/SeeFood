package custom.objects;

import java.io.File;

import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author jakeflorentine
 *
 */
public class SeefoodImage {
	private int confidence = 0;
	private double confidenceLevel = 0;
	private Image originalImage;
	private boolean isFood;
	private File imageFile = null;

	/**
	 * 
	 * @param confidence
	 * @param image
	 * @param isFood
	 */
	public SeefoodImage(double confidence, Image image, boolean isFood) {
		setConfidence(confidence);
		this.originalImage = image;
		this.isFood = isFood;
	}

	/**
	 * 
	 * @param confidence
	 *            Confidence Level as a percentage
	 * @param imageFile
	 * @param isFood
	 */
	public SeefoodImage(double confidence, File imageFile, boolean isFood) {
		setConfidence(confidence);
		this.imageFile = imageFile;
		this.isFood = isFood;
	}

	/**
	 * 
	 * @param confidence
	 */
	public void setConfidence(double confidence) {
		this.confidenceLevel = confidence * 100;
		this.confidence = (int) confidenceLevel;
	}

	public double getConfidence() {
		return this.confidenceLevel;
	}

	public String getConfidenceAsString() {
		return Integer.toString(confidence);
	}

	/**
	 * 
	 * @param image
	 */
	public void setImage(Image image) {
		this.originalImage = image;
	}

	/**
	 * 
	 * @return
	 */
	public Image getImage() {
		return this.originalImage;
	}

	/**
	 * 
	 * @param isFood
	 */
	public void setIsFood(boolean isFood) {
		this.isFood = isFood;
	}

	/**
	 * 
	 * @return
	 */
	public boolean getIsFood() {
		return this.isFood;
	}

	/**
	 * 
	 * @param imageFile
	 */
	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	/**
	 * 
	 * @return
	 */
	public File getImageFile() {
		return imageFile;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (getIsFood()) {
			sb.append("Food \n");
		} else {
			sb.append("Not Food \n");
		}
		sb.append(getConfidenceAsString() + "%");

		return sb.toString();

	}

}
