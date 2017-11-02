package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import util.ParserUtil;

public class HomeView extends Composite {

	public HomeView(Composite parent, int style) {
		super(parent, SWT.BORDER | SWT.NO_BACKGROUND);
		GridLayout gl = new GridLayout(3, true);
		gl.marginWidth = 50;
		gl.marginHeight = 25;
		this.setLayout(gl);
		createContent(this, SWT.BORDER);
	}

	public void createContent(Composite parent, int style) {

		Composite ci = new Composite(parent, SWT.BORDER | SWT.NO_BACKGROUND);
		ci.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 3));

		Button upload = new Button(parent, SWT.FLAT);
		upload.setText("Upload");
		upload.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 3, 1));
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
				
				files = ParserUtil.parseFiles(files);
				
				if(files.length > 0) {
					String parentFilePath = fd.getFilterPath();
					openUploadView(parentFilePath, files);
					// b.setBackgroundImage(new Image(display, parentPath+fd.getFileName()));

					for (String s : files) {
						System.out.println(s);

						// ci.setBackgroundImage(new Image(ci.getDisplay(), s));
					}
				}
			}

		});

		Button gallery = new Button(parent, SWT.FLAT);
		gallery.setText("Gallery");
		gallery.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 3, 1));
		gallery.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// launch the gallery view
				openGallery();
			}

		});

	}

	public void openUploadView(String parentFilePath, String[] files) {
		Composite mainComposite = this.getParent();
		this.dispose();
		ViewUtil.launchUploadView(mainComposite, SWT.BORDER, parentFilePath, files);
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
