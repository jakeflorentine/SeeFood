package custom.widgets;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class ConfidenceMeter extends Canvas {
	private Rectangle correct;
	private Rectangle incorrect;

	public ConfidenceMeter(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * 
	 * @param confidenceLevel
	 * @param scale
	 * 
	 *            Ex: 10% = (10, 100)
	 */
	public void setConfidenceLevel(int confidenceLevel, int scale) {
		// get the percentage of the bar to display correct
		double confidencePct = (double) confidenceLevel / (double) scale;
		// get the width in which to display the bar
		int width = this.getBounds().width;

		// determine the portion of the composite to fill
		int correctWidth = (int) (width * confidencePct);
		int incorrectWidth = width - correctWidth;

		// create both rectangles
		// correct = new Rectangle(this.getBounds().x, this.getBounds().y, correctWidth,
		// 20);

	}

}
