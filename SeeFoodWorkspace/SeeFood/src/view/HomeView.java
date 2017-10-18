package view;

import org.eclipse.swt.SWT;
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

public class HomeView extends Composite{

	public HomeView(Composite parent, int style) {
		super(parent, SWT.BORDER);
		GridLayout gl = new GridLayout(3, true);
        gl.marginWidth = 50;
        gl.marginHeight = 25;
        this.setLayout(gl);
		createContent(this, SWT.BORDER);
	}
	
	public void createContent(Composite parent, int style) {
//		Composite c = new Composite(parent, SWT.None);
//		GridLayout gl = new GridLayout(3, false);
//		gl.marginWidth = 50;
//		gl.marginHeight = 25;
//		c.setLayout(gl);
		Composite ci = new Composite(parent, SWT.BORDER);
        ci.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 3));
        
        
        
        Button upload = new Button(parent, SWT.FLAT);
        upload.setText("Upload");
        upload.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
        upload.addSelectionListener(new SelectionAdapter() {
        	
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				FileDialog fd = new FileDialog(Display.getCurrent().getActiveShell(), SWT.APPLICATION_MODAL | SWT.MULTI);
				//fd.setFilterExtensions(ImageUtil.filterExtensions);
				fd.setText("Select a photo");
				fd.open();
				String[] files = fd.getFileNames();
				String parentPath = fd.getFilterPath();
				try {
					String si = parentPath+fd.getFileName();
					System.out.println(si);
					Image i = new Image(Display.getCurrent(), parentPath+"/"+fd.getFileName());
					Image scaled = ImageUtil.resize(i, ci.getBounds());
				
					//set the composite background to be the scaled image
					ci.setBackgroundImage(scaled);
				}
				catch(Exception e1) {
					System.out.println("Cannot create image");
					ci.setBackground(new Color(Display.getCurrent(), 255, 0, 0));
				}
				//b.setBackgroundImage(new Image(display, parentPath+fd.getFileName()));
				
				for(String s : files) {
					System.out.println(s);
					
					//ci.setBackgroundImage(new Image(ci.getDisplay(), s));
				}
				
			}
        	
        });
        
              
        Button gallery = new Button(parent, SWT.FLAT);
        gallery.setText("Gallery");
        gallery.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
        gallery.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
			}
        
        });
		
		
		
	}
}
