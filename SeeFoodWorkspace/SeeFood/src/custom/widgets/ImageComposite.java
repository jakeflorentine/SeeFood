package custom.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
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

		Image newImage = new Image(Display.getCurrent(), image.getImageData());
		GC gc = new GC(newImage);
		Font font = new Font(Display.getCurrent(), "Arial", 25, SWT.BOLD | SWT.ITALIC);
		// Image tempImage = new Image(Display.getCurrent(),
		// image.getImageFile().getAbsolutePath());
		// Image scaled = ImageUtil.resize(tempImage, r);
		if (getSeefoodImage().getIsFood()) {
			gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN));
		} else {
			gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		}
		gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		gc.drawText(getSeefoodImage().toString(), 5, 5);
		gc.setFont(font);
		gc.dispose();

		parent.setBackgroundImage(newImage);

	}
}
