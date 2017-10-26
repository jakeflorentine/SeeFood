package custom.objects;

import org.eclipse.swt.graphics.Image;

public class SeefoodImage {
	private double confidenceLevel = 0;
	private Image originalImage;

	public SeefoodImage(double confidence, Image image) {
		this.confidenceLevel = confidence;
		this.originalImage = image;
	}

	public void SetConfidence(double confidence) {
		this.confidenceLevel = confidence;
	}

	public double GetConfidence() {
		return this.confidenceLevel;
	}

	public void SetImage(Image image) {
		this.originalImage = image;
	}

	public Image GetImage() {
		return this.originalImage;
	}
}
