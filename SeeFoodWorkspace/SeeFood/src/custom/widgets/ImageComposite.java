package custom.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import custom.objects.SeefoodImage;

public class ImageComposite extends Composite {
	private SeefoodImage seefoodImage;
	private ConfidenceMeter confidenceMeter;

	/**
	 * 
	 * @param parent
	 * @param style
	 * @param seefoodImage
	 */
	public ImageComposite(Composite parent, int style, SeefoodImage seefoodImage) {
		super(parent, SWT.BORDER);
		GridLayout gl = new GridLayout(1, true);
		this.setLayout(gl);
		System.out.println("Drawing Image 1");
		setSeefoodImage(seefoodImage);

		createContent(this);
	}

	/**
	 * 
	 * @param seefoodImage
	 */
	public void setSeefoodImage(SeefoodImage seefoodImage) {
		this.seefoodImage = seefoodImage;
	}

	/**
	 * 
	 * @return
	 */
	public SeefoodImage getSeefoodImage() {
		return seefoodImage;
	}

	/**
	 * 
	 * @param parent
	 */
	private void createContent(Composite parent) {
		Image image = getSeefoodImage().getImage();
		// Canvas imageCanvas = new Canvas(parent, SWT.NONE);
		GridData gridData = new GridData();
		gridData.widthHint = image.getBounds().width;
		gridData.heightHint = image.getBounds().height;
		parent.setBackgroundImage(new Image(Display.getCurrent(), image.getImageData()));
		// imageCanvas.setLayoutData(gridData);
		// imageCanvas.setSize(parent.getBounds().width, parent.getBounds().height);
		// imageCanvas.addPaintListener(new PaintListener() {
		//
		// @Override
		// public void paintControl(PaintEvent e) {
		// System.out.println("Drawing Image");
		// // e.gc.drawLine(20, 40, 40, 40);
		//
		// // If the image is of food the background of the bar will be green, otherwise
		// it
		// // will be red.
		// Color background = getSeefoodImage().getIsFood() ? new
		// Color(Display.getCurrent(), 0, 255, 0)
		// : new Color(Display.getCurrent(), 255, 0, 0);
		// // set the backr=ground color
		// e.gc.setBackground(background);
		// // draw the image
		// e.gc.drawImage(image, 0, 0);
		// e.gc.drawRectangle(0, 0, imageCanvas.getSize().x - 1, imageCanvas.getSize().y
		// - 1);
		//
		// }
		//
		// });
		// parent.layout();
	}
}
