package view;

import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import custom.objects.SeefoodImage;
import custom.widgets.ImageComposite;

public class GalleryView extends Composite {
	// scrolled composite to be filled with seefood images
	private ScrolledComposite photoGrid;
	private TabFolder filter;
	private Image img = new Image(Display.getCurrent(), "/Users/jakeflorentine/git/CEG-SeeFoodAI/fries.jpg");
	private Image img2 = new Image(Display.getCurrent(), "/Users/jakeflorentine/git/CEG-SeeFoodAI/samples/poodle.png");

	public GalleryView(Composite parent, int style) {
		super(parent, style);
		GridLayout gl = new GridLayout(1, true);
		gl.marginWidth = 50;
		gl.marginHeight = 25;
		this.setLayout(gl);
		createContent(this, SWT.BORDER);
	}

	/**
	 * 
	 * @param parent
	 * @param style
	 */
	public void createContent(Composite parent, int style) {
		filter = new TabFolder(parent, style);
		filter.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		TabItem food = new TabItem(filter, SWT.NONE);
		food.setText("Food");
		food.setControl(getFilterControl());

		TabItem allImages = new TabItem(filter, SWT.BORDER);
		allImages.setText("All Images");
		allImages.setControl(getFilterControl());

		TabItem notFood = new TabItem(filter, SWT.NONE);
		notFood.setText("Not Food");
		notFood.setControl(getFilterControl());

		testFill((ScrolledComposite) food.getControl());

		filter.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (photoGrid != null) {
					photoGrid.dispose();
				}
				testFill((ScrolledComposite) filter.getSelection()[0].getControl());
				System.out.println("Selection");
				// setPhotoGridData();
				// fillPhotoGrid();

				testFill((ScrolledComposite) filter.getSelection()[0].getControl());

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				System.out.println("Selection");

				testFill((ScrolledComposite) filter.getSelection()[0].getControl());

			}

		});

		// for (int i = 0; i < 12; i++) {
		// testFill((Composite) food.getControl());
		// }

		/**
		 * Each tab should be filled with a scrolled composite which will contain a 3xn
		 * grid
		 */

		Button upload = new Button(parent, SWT.FLAT);
		upload.setText("Upload");
		upload.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 3, 1));
		upload.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// open file dialog
				FileDialog fd = new FileDialog(Display.getCurrent().getActiveShell(),
						SWT.APPLICATION_MODAL | SWT.MULTI);
				fd.setText("Select a photo");
				fd.open();

				// get the files
				String[] files = fd.getFileNames();
				String parentPath = fd.getFilterPath();

				try {
					openUploadView(parentPath, files);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
	}

	private void setPhotoGridData() {
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.widthHint = SWT.DEFAULT;
		gridData.heightHint = SWT.DEFAULT;
		photoGrid.setLayoutData(gridData);
		GridLayout gl = new GridLayout(3, true);
		gl.marginWidth = 50;
		gl.marginHeight = 25;
		photoGrid.setLayout(gl);

	}

	public void testFill(ScrolledComposite selectedControl) {
		Image image = img;
		if (filter.getSelectionIndex() == 1) {
			image = img2;
		}
		Composite additionalImageComp = new Composite(selectedControl, SWT.BORDER);
		additionalImageComp.setLayout(new GridLayout(3, false));
		additionalImageComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));

		for (int i = 0; i < 12; i++) {
			// ScrolledComposite scrolledComposite = (ScrolledComposite) food.getControl();

			Composite comp = new Composite(additionalImageComp, SWT.BORDER);
			GridData g = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
			int size = selectedControl.getBounds().width / 1;
			g.heightHint = 200;
			g.widthHint = 200;
			comp.setLayoutData(g);
			Image ii = image;
			// Image scaledImage = ImageUtil.resize(ii, new Rectangle(0, 0, size, size));
			comp.setBackgroundImage(ii);
		}
		selectedControl.setContent(additionalImageComp);
		selectedControl.setMinSize(additionalImageComp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	private void fillPhotoGrid() {
		SeefoodImage s = new SeefoodImage(40,
				new Image(Display.getCurrent(), "/Users/jakeflorentine/git/CEG-SeeFoodAI/fries.jpg"));
		ImageComposite iComp = new ImageComposite(photoGrid, SWT.NONE, s);
		iComp.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
	}

	public void openUploadView(String parentFilePath, String[] files) throws UnknownHostException, IOException {
		Composite mainComposite = this.getParent();
		this.dispose();
		ViewUtil.launchUploadView(mainComposite, SWT.BORDER, parentFilePath, files);
	}

	private ScrolledComposite getFilterControl() {
		ScrolledComposite scrolledComposite = new ScrolledComposite(filter, SWT.V_SCROLL | SWT.BORDER);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 3));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setMinSize(250, 250);
		scrolledComposite.setLayout(new GridLayout(1, true));

		return scrolledComposite;
	}

	public void openHome() {
		Composite mainComposite = this.getParent();
		this.dispose();
		ViewUtil.launchHomeView(mainComposite, SWT.BORDER);
	}

	public void openGallery() {
		Composite mainComposite = this.getParent();
		this.dispose();
		ViewUtil.launchGallery(mainComposite, SWT.BORDER);
	}

}
