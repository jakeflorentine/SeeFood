package view;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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

import com.jcraft.jsch.SftpException;

import custom.objects.SeefoodImage;
import custom.widgets.ImageComposite;
import util.WebServiceUtil;

public class GalleryView extends Composite {
	// scrolled composite to be filled with seefood images
	private ScrolledComposite photoGrid;
	private TabFolder filter;
	private Image img = new Image(Display.getCurrent(), "/Users/jakeflorentine/git/CEG-SeeFoodAI/fries.jpg");
	private Image img2 = new Image(Display.getCurrent(), "/Users/jakeflorentine/git/CEG-SeeFoodAI/samples/poodle.png");
	private List<SeefoodImage> allImagesList = new ArrayList<>();
	private List<SeefoodImage> foodList = new ArrayList<>();
	private List<SeefoodImage> notFoodList = new ArrayList<>();
	private TabItem foodTab;
	private TabItem allImagesTab;
	private TabItem notFoodTab;

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

		foodTab = new TabItem(filter, SWT.NONE);
		foodTab.setText("Food");
		foodTab.setControl(getFilterControl());

		allImagesTab = new TabItem(filter, SWT.BORDER);
		allImagesTab.setText("All Images");
		allImagesTab.setControl(getFilterControl());

		notFoodTab = new TabItem(filter, SWT.NONE);
		notFoodTab.setText("Not Food");
		notFoodTab.setControl(getFilterControl());

		filter.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				List<SeefoodImage> results;

				if (photoGrid != null) {
					photoGrid.dispose();
				}
				testFill((ScrolledComposite) filter.getSelection()[0].getControl());
				System.out.println("Selection");
				try {
					// NOTE: These come back with the already calculated percentage. The confidence
					// is already parsed for the gallery
					results = WebServiceUtil.getImages();
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}

				// testFill((ScrolledComposite) filter.getSelection()[0].getControl());

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				System.out.println("Selection");

				testFill((ScrolledComposite) filter.getSelection()[0].getControl());

			}

		});

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
		// this gets all images from the server and sorts into food/not food
		sortImages();

		// fills the comps
		testFill((ScrolledComposite) foodTab.getControl());
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

	/**
	 * 
	 * @return seefood image list based on open tab
	 */
	private List<SeefoodImage> getCorrectList() {
		TabItem[] tabItems = filter.getSelection();
		TabItem openTab = tabItems[0];
		if (openTab == foodTab) {
			return foodList;
		}

		if (openTab == notFoodTab) {
			return notFoodList;
		}

		return allImagesList;
	}

	private void sortImages() {
		List<SeefoodImage> seefoodImages = null;
		try {
			seefoodImages = WebServiceUtil.getImages();
			allImagesList = seefoodImages;
		} catch (SftpException e) {
		}

		// check if images were null
		if (seefoodImages != null && !seefoodImages.isEmpty()) {
			System.out.println("Gallery Images reveived");
		}

		// sort images to food and not food
		for (SeefoodImage s : seefoodImages) {
			if (s.getIsFood()) {
				foodList.add(s);
			} else {
				notFoodList.add(s);
			}
		}
	}

	public void testFill(ScrolledComposite selectedControl) {
		Image image = img;
		if (filter.getSelectionIndex() == 1) {
			image = img2;
		}
		Composite additionalImageComp = new Composite(selectedControl, SWT.BORDER);
		additionalImageComp.setLayout(new GridLayout(3, false));
		additionalImageComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));

		// use the correct list
		List<SeefoodImage> imageList = getCorrectList();

		for (SeefoodImage seefoodImage : imageList) {
			fillPhotoGrid(additionalImageComp, seefoodImage);
		}
		selectedControl.setContent(additionalImageComp);
		selectedControl.setMinSize(additionalImageComp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	private void fillPhotoGrid(Composite parent, SeefoodImage s) {
		ImageComposite iComp = new ImageComposite(parent, SWT.NONE, s);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd.heightHint = 175;
		gd.widthHint = 175;
		iComp.setLayoutData(gd);
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
