package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class GalleryView extends Composite {

	public GalleryView(Composite parent, int style) {
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
		TabFolder filter = new TabFolder(parent, style);
		filter.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		TabItem food = new TabItem(filter, SWT.NONE);
		food.setText("Food");

		TabItem allImages = new TabItem(filter, SWT.NONE);
		allImages.setText("All Images");

		TabItem notFood = new TabItem(filter, SWT.NONE);
		notFood.setText("Not Food");

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
				// get the files
				// launch the upload view
				openUploadView();
			}

		});
	}

	public void openUploadView() {
		Composite mainComposite = this.getParent();
		this.dispose();
		ViewUtil.launchUploadView(mainComposite, SWT.BORDER);
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
