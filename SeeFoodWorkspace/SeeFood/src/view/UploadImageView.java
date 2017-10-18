package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import util.ImageUtil;

public class UploadImageView extends Composite {

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
		ScrolledComposite sc = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.BORDER);
		sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 2));

		Composite imageComp = new Composite(parent, SWT.BORDER);
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
				String parentPath = fd.getFilterPath();
				try {
					String si = parentPath + fd.getFileName();
					System.out.println(si);
					Image i = new Image(Display.getCurrent(), parentPath + "/" + fd.getFileName());
					Image scaled = ImageUtil.resize(i, imageComp.getBounds());

					// set the composite background to be the scaled image
					imageComp.setBackgroundImage(scaled);
				} catch (Exception e1) {
					System.out.println("Cannot create image");
					imageComp.setBackground(new Color(Display.getCurrent(), 255, 0, 0));
				}
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

	}

}
