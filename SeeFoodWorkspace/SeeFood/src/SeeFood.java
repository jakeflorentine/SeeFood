import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * This is the license header. All code is IP of SeeFood Inc.
 */

/**
 * @author Jake Florentine
 *
 */
public class SeeFood {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//call a system start
		Display display = new Display();

        Shell shell = new Shell(display);

        // the layout manager handle the layout
        // of the widgets in the container
        shell.setLayout(new FillLayout());

        /**
         * This is where I think we will want to call a class to build the UI
         * NOTE: remember to dispose widgets to prevent errors
         */
        Composite c = new Composite(shell, SWT.None);
        GridLayout gl = new GridLayout(3, false);
        gl.marginWidth = 50;
        gl.marginHeight = 25;
        c.setLayout(gl);
        c.setBackground(new Color(display, 255, 252, 155));
        
        Label t = new Label(c, SWT.NONE);
        t.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false, 3, 1));
        t.setText("SeeFood");
        
        //Composite should be a "drop target"
        
        Composite ci = new Composite(c, SWT.None);
        ci.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
        
        
        
        Button upload = new Button(c, SWT.FLAT);
        upload.setText("Upload");
        upload.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
        upload.addSelectionListener(new SelectionAdapter() {
        	
			@Override
			public void widgetSelected(SelectionEvent e) {
				String[] filterExtensions = {"*.png", "*.jpg", "*.jpeg"};
				
				FileDialog fd = new FileDialog(Display.getCurrent().getActiveShell(), SWT.APPLICATION_MODAL | SWT.MULTI);
				//fd.setFilterExtensions(filterExtensions);
				fd.setText("Select a photo");
				fd.open();
				String[] files = fd.getFileNames();
				String parentPath = fd.getFilterPath();
				try {
					String si = parentPath+fd.getFileName();
					System.out.println(si);
					Image i = new Image(display, parentPath+"/"+fd.getFileName());
					
					
					System.out.println("Created image");
					ci.setBackground(new Color(display, 0, 255, 0));
					ci.setBackgroundImage(i);
				}
				catch(Exception e1) {
					System.out.println("Cannot create image");
					ci.setBackground(new Color(display, 255, 0, 0));
				}
				//b.setBackgroundImage(new Image(display, parentPath+fd.getFileName()));
				
				for(String s : files) {
					System.out.println(s);
					
					//ci.setBackgroundImage(new Image(ci.getDisplay(), s));
				}
				
			}
        	
        });
        
              
        Button gallery = new Button(c, SWT.FLAT);
        gallery.setText("Gallery");
        gallery.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
        
        
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
		
	}

}
