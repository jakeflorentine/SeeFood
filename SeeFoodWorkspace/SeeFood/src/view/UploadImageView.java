package view;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.ImageTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import custom.objects.SeefoodImage;
import util.ImageUtil;
import util.ParserUtil;
import util.WebServiceUtil;

public class UploadImageView extends Composite {
	private Composite imageComp;
	private ScrolledComposite scrolledComposite;
	private Composite additionalImageComp;
	private final int RED = SWT.COLOR_RED;
	private final int GREEN = SWT.COLOR_GREEN;
	private boolean mainCompSet = false;
	private List<SeefoodImage> currentImages = new ArrayList<>();

	/**
	 * 
	 * @param parent
	 * @param style
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public UploadImageView(Composite parent, int style) throws UnknownHostException, IOException {
		super(parent, style);
		GridLayout gl = new GridLayout(3, true);
		gl.marginWidth = 50;
		gl.marginHeight = 25;
		this.setLayout(gl);
		DropTarget target = new DropTarget(this, SWT.NONE);
		final FileTransfer fTransfer = FileTransfer.getInstance();
		final ImageTransfer iTransfer = ImageTransfer.getInstance();
		Transfer[] transfers = new Transfer[] { fTransfer, iTransfer };
		target.setTransfer(transfers);
		target.addDropListener(new DropTargetAdapter() {
			@Override
			public void dropAccept(DropTargetEvent event) {

			}

			@Override
			public void drop(DropTargetEvent event) {
				// A drop has occurred, copy over the data

				// no data to copy if either are true
				if (event.data == null || !(event.data instanceof String[])) {
					event.detail = DND.DROP_NONE;
					return;
				}

				String[] files = (String[]) event.data;

				if (files == null) {
					event.detail = DND.DROP_NONE;
					return;
				}

				// parse all files read in from user selection
				files = ParserUtil.parseFiles(files);

				if (files == null) {
					event.detail = DND.DROP_NONE;
					return;
				}

				// String parentFilePath = fd.getFilterPath();

				// When adding additional images, this keeps all current images and adds the new
				// ones after them in the side scroll
				SeefoodImage[] results = WebServiceUtil.getResults("", files);
				currentImages.addAll(Arrays.asList(results));
				results = new SeefoodImage[currentImages.size()];
				for (int i = 0; i < results.length; i++) {
					results[i] = currentImages.get(i);
				}

				// redraw images
				displaySeefoodImages(results);
				System.out.println("image has been dropped" + event.toString()); // data copied to label text
			}
		});
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
		imageComp.setLayout(new GridLayout());
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

				// When adding additional images, this keeps all current images and adds the new
				// ones after them in the side scroll
				SeefoodImage[] results = WebServiceUtil.getResults(parentFilePath, files);
				currentImages.addAll(Arrays.asList(results));
				results = new SeefoodImage[currentImages.size()];
				for (int i = 0; i < results.length; i++) {
					results[i] = currentImages.get(i);
				}

				// redraw images
				displaySeefoodImages(results);

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

	/**
	 * 
	 * @param parentFilePath
	 * @param files
	 */
	public void displaySeefoodImages(SeefoodImage[] seefoodImages) {
		if (seefoodImages == null)
			return;
		List<SeefoodImage> validImages = Arrays.asList(seefoodImages);
		currentImages.addAll(validImages);

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
	public void fillImageComp(List<SeefoodImage> validImages) {
		// no need to continue if the list is null
		if (validImages == null) {
			return;
		}

		/**
		 * if there's more than one image being uploaded, display the additional images
		 * in the scrolled composite. The "additionalImageComp" is the content area of
		 * the scrolled composite.
		 */
		additionalImageComp = new Composite(scrolledComposite, SWT.BORDER);
		additionalImageComp.setLayout(new GridLayout(1, false));

		// boolean variable to aid in checking if the main image has been set

		for (SeefoodImage image : validImages) {
			// if the main image composite is not set fill it with an image
			if (!mainCompSet) {

				addImageCanvas(imageComp, image);

				mainCompSet = true;
			}
			// add the remaining images to the scrolled composite
			else {

				addImageCanvas(additionalImageComp, image);
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

	/**
	 * 
	 * @param parent
	 * @param image
	 */
	private void addImageCanvas(Composite parent, SeefoodImage image) {
		Composite canvasShell = new Composite(parent, SWT.NONE);
		canvasShell.setLayout(new GridLayout());
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd.heightHint = 300;
		canvasShell.setLayoutData(gd);
		Canvas canvas = new Canvas(canvasShell, SWT.NONE);

		canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// Create a paint handler for the canvas
		canvas.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				Font font = new Font(Display.getCurrent(), "Arial", 25, SWT.BOLD | SWT.ITALIC);
				Rectangle r = canvas.getBounds();
				Image tempImage = new Image(Display.getCurrent(), image.getImageFile().getAbsolutePath());
				Image scaled = ImageUtil.resize(tempImage, r);
				e.gc.drawImage(scaled, 0, 0);
				if (image.getIsFood()) {
					e.gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN));
				} else {
					e.gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
				}
				e.gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				e.gc.drawText(image.toString(), 5, 5);
				e.gc.setFont(font);

			}
		});

		canvasShell.layout();
		parent.layout();
	}

	/**
	 * 
	 */
	public void openHome() {
		Composite mainComposite = this.getParent();
		this.dispose();
		ViewUtil.launchHomeView(mainComposite, SWT.BORDER);
	}

	/**
	 * 
	 */
	public void openGallery() {
		Composite mainComposite = this.getParent();
		this.dispose();
		ViewUtil.launchGallery(mainComposite, SWT.BORDER);
	}
}