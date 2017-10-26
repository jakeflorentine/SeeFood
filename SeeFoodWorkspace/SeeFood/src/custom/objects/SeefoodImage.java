package custom.objects;

import org.eclipse.swt.graphics.Image;

public class SeefoodImage {
	private double confidenceLevel = 0;
	private Image originalImage;

	public SeefoodImage(double confidence, Image image) {
		this.confidenceLevel = confidence;
		this.originalImage = image;
	}

	public void setConfidence(double confidence) {
		this.confidenceLevel = confidence;
	}

	public double getConfidence() {
		return this.confidenceLevel;
	}

	public void setImage(Image image) {
		this.originalImage = image;
	}

	public Image getImage() {
		return this.originalImage;
	}
}
