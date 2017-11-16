package custom.objects;

import org.eclipse.swt.graphics.Image;

public class SeefoodImage {
	private double confidenceLevel = 0;
	private Image originalImage;
	private boolean isFood;

	public SeefoodImage(double confidence, Image image, boolean isFood) {
		this.confidenceLevel = confidence;
		this.originalImage = image;
		this.isFood = isFood;
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
	
	public void setIsFood(boolean isFood) {
		this.isFood = isFood;
	}
	
	public boolean getIsFood() {
		return this.isFood;
	}
	
	public double getConfidencePercentage(double confidence1, double confidence2){
		double max = Math.max(confidence1, confidence2);
		double min = Math.min(confidence1, confidence2);
		double dif = max - min;
		return dif;
	}
}
