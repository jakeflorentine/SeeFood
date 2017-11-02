package view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import util.ImageUtil;
import util.ParserUtil;

public class UploadImageView extends Composite {
	private Composite imageComp;
	private ScrolledComposite scrolledComposite;
	private Composite additionalImageComp;

	public UploadImageView(Composite parent, int style) {
		super(parent, style);
		GridLayout gl = new GridLayout(3, true);
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
		// this should only appear if there is more than 1 uploaded image
		// TODO design this
		scrolledComposite = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.BORDER);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 2));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setMinSize(250, 250);

		imageComp = new Composite(parent, SWT.BORDER);
		imageComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));

		Composite btnComp = new Composite(parent, SWT.BORDER);
		btnComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));
		btnComp.setLayout(new GridLayout(2, true));
		Button upload = new Button(btnComp, SWT.FLAT | SWT.CENTER);
		upload.setText("Upload");
		upload.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));

		upload.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				/**
				 * Selection of upload from the home view should launch the file dialog, then
				 * proceed to the UploadImageView
				 */

				FileDialog fd = new FileDialog(Display.getCurrent().getActiveShell(),
						SWT.APPLICATION_MODAL | SWT.MULTI);
				// fd.setFilterExtensions(ImageUtil.filterExtensions);
				fd.setText("Select a photo");
				fd.open();
				String[] files = fd.getFileNames();

				// parse all files read in from user selection
				files = ParserUtil.parseFiles(files);

				String parentFilePath = fd.getFilterPath();
				displayImages(parentFilePath, files);
				// b.setBackgroundImage(new Image(display, parentPath+fd.getFileName()));

				for (String s : files) {
					System.out.println(s);

					// ci.setBackgroundImage(new Image(ci.getDisplay(), s));
				}

			}

		});
		Button gallery = new Button(btnComp, SWT.FLAT | SWT.CENTER);
		gallery.setText("Gallery");
		gallery.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		gallery.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// launch the gallery
				openGallery();
			}
		});

	}

	public void displayImages(String parentFilePath, String[] files) {
		List<Image> validImages = new ArrayList<>();
		for (String file : files) {
			try {
				String fullFilePath = parentFilePath + "/" + file;
				// need to check if fullFilePath is an image

				Image i = new Image(Display.getCurrent(), fullFilePath);
				validImages.add(i);
				// Image scaled = ImageUtil.resize(i, imageComp.getBounds());
				// // set the composite background to be the scaled image
				// imageComp.setBackgroundImage(scaled);
			} catch (Exception e1) {
				System.out.println("Cannot create image");
				imageComp.setBackground(new Color(Display.getCurrent(), 255, 0, 0));
			}
		}
		fillImageComp(validImages);
	}

	/**
	 * Fills the image composite and the additional display area with the images
	 * provided. The content area within the scrolled composite will only be created
	 * if the is more than one supplied image: in order to speed up the process.
	 * 
	 * @param validImages
	 *            A list of created images to be displayed
	 */
	public void fillImageComp(List<Image> validImages) {
		// no need to continue if the list is null
		if (validImages == null) {
			return;
		}

		/**
		 * if there's more than one image being uploaded, display the additional images
		 * in the scrolled composite. The "additionalImageComp" is the content area of
		 * the scrolled composite.
		 */
		if (validImages.size() > 1) {
			additionalImageComp = new Composite(scrolledComposite, SWT.BORDER);
			additionalImageComp.setLayout(new GridLayout(1, false));
		}

		// boolean variable to aid in checking if the main image has been set
		boolean mainCompSet = false;

		for (Image image : validImages) {
			// if the main image composite is not set fill it with an image
			if (!mainCompSet) {
				Image scaled = ImageUtil.resize(image, imageComp.getBounds());
				// set the composite background to be the scaled image
				imageComp.setBackgroundImage(scaled);
				mainCompSet = true;
			}
			// add the remaining images to the scrolled composite
			else {
				// create the composite to display the image
				Composite imageInScroll = new Composite(additionalImageComp, SWT.BORDER);
				GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
				// account for the scroll bar when finding the size
				int vertBarWidth = scrolledComposite.getVerticalBar().getMaximum();
				int size = scrolledComposite.getBounds().width;
				gd.widthHint = size;
				gd.heightHint = size;
				imageInScroll.setLayoutData(gd);

				// resize the image to fit the composite
				Image scaledImage = ImageUtil.resize(image, new Rectangle(0, 0, size, size));
				imageInScroll.setBackgroundImage(scaledImage);
			}

		}

		// there's no need to proceed if there's nothing in the additional image
		// composite
		if (additionalImageComp == null) {
			return;
		}

		// add the additional image composite to the scrolled composite
		scrolledComposite.setContent(additionalImageComp);
		scrolledComposite.setMinSize(additionalImageComp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
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
