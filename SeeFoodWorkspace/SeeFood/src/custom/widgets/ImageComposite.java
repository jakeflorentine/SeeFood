package custom.widgets;

import org.eclipse.swt.layout.GridLayout;
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
		GridLayout gl = new GridLayout(3, true);
		gl.marginWidth = 50;
		gl.marginHeight = 25;
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

	}
}
