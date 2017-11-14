package custom.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class ConfidenceMeter extends Composite {
	private Rectangle correct;
	private Rectangle incorrect;
	private double confidencePct;

	public ConfidenceMeter(Composite parent, int style, double confidencePct) {
		super(parent, SWT.NO_BACKGROUND);
		GridLayout gl = new GridLayout(2, false);
		this.setLayout(gl);
		this.confidencePct = confidencePct;
		createContent(this);
	}

	private void createContent(Composite parent) {
		Canvas meterCanvas = new Canvas(parent, SWT.NONE);
		GridData gridData = new GridData();
		gridData.widthHint = parent.getBounds().width;
		gridData.heightHint = parent.getBounds().height;
		meterCanvas.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		meterCanvas.setSize(parent.getBounds().width, 50);
		meterCanvas.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				// int w = confidenceLevel;

				// e.gc.drawLine(20, 40, 40, 40);
				meterCanvas.setBackground(new Color(Display.getCurrent(), 255, 0, 0));
				e.gc.drawRoundRectangle(0, 0, meterCanvas.getSize().x - 1, meterCanvas.getSize().y - 1, 10, 10);
				meterCanvas.setBackground(new Color(Display.getCurrent(), 0, 255, 0));
				e.gc.drawRoundRectangle(0, 0, (int) ((meterCanvas.getSize().x - 1) * confidencePct),
						meterCanvas.getSize().y - 1, 10, 10);
				// e.gc.drawRoundedRectangle(int x, int y, int width, int height, int arcWidth,
				// int arcHeight);
			}

		});

		Canvas meterCanvas2 = new Canvas(parent, SWT.NONE);
		meterCanvas2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		meterCanvas2.setSize(parent.getBounds().width, 50);
		meterCanvas2.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				meterCanvas2.setBackground(new Color(Display.getCurrent(), 255, 0, 0));
				e.gc.drawRoundRectangle(0, 0, (int) ((meterCanvas2.getSize().x - 1) * confidencePct),
						meterCanvas2.getSize().y - 1, 10, 10);
				// e.gc.drawRoundedRectangle(int x, int y, int width, int height, int arcWidth,
				// int arcHeight);
			}

		});
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

	}

}
