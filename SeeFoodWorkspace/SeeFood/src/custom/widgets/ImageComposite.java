package custom.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

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
		super(parent, style);
		GridLayout gl = new GridLayout(1, true);
		this.setLayout(gl);

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
		Canvas imageCanvas = new Canvas(parent, SWT.NONE);
		GridData gridData = new GridData();
		gridData.widthHint = image.getBounds().width;
		gridData.heightHint = image.getBounds().height;
		imageCanvas.setLayoutData(gridData);
		imageCanvas.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				// e.gc.drawLine(20, 40, 40, 40);
				e.gc.drawRectangle(0, 0, imageCanvas.getSize().x - 1, imageCanvas.getSize().y - 1);
				e.gc.drawImage(image, 0, 0);

			}

		});
	}
}
