package view;

import java.io.IOException;
import java.net.UnknownHostException;

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
		Composite btnComp = new Composite(parent, SWT.BORDER | SWT.NO_BACKGROUND);
		btnComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 3, 1));
		btnComp.setLayout(new GridLayout(2, true));

		Button upload = new Button(btnComp, SWT.FLAT);
		upload.setText("Upload");
		upload.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
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

				if (files.length > 0) {
					String parentFilePath = fd.getFilterPath();
					try {
						openUploadView(parentFilePath, files);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// b.setBackgroundImage(new Image(display, parentPath+fd.getFileName()));

					for (String s : files) {
						System.out.println(s);

						// ci.setBackgroundImage(new Image(ci.getDisplay(), s));
					}
				}
			}

		});

		Button gallery = new Button(btnComp, SWT.FLAT);
		gallery.setText("Gallery");
		gallery.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		gallery.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// launch the gallery view
				openGallery();
			}

		});

	}

	public void openUploadView(String parentFilePath, String[] files) throws UnknownHostException, IOException {
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
