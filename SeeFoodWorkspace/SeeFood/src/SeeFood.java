import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
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
	
	public static Image resize(Image image, Rectangle compBounds) {
		System.out.println("Created image.  Width: " +compBounds.width + " Height: " +compBounds.height);
		Rectangle imageBounds = image.getBounds();
		int width = 400, height = 424;
		//check to see whether the image is wider or taller
		//set the corresponding dimension to the composites'
		if(imageBounds.width > imageBounds.height) {
			width = compBounds.width;
			height = getRemainingBound(imageBounds, compBounds, true);
		}
		else {
			height = compBounds.height;
			width = getRemainingBound(imageBounds, compBounds, false);
		}
		
		Image scaled = new Image(Display.getDefault(), width, height);
		  GC gc = new GC(scaled);
		  gc.setAntialias(SWT.ON);
		  gc.setInterpolation(SWT.HIGH);
		  gc.drawImage(image, 0, 0,image.getBounds().width, image.getBounds().height, 0, 0, width, height);
		  gc.dispose();
		  image.dispose();
		  
		  return scaled;
		}
	
	/**
	 * Calculates the remaining bound in order to display the image within the specified composite bounds.
	 * Specify height or width with the "getHeight" value: true if the height is desired.
	 * 
	 * @param imageBounds Bounds of the original image
	 * @param compBounds Bounds of the available composite space
	 * @param getHeight True if the new height is to be returned, false otherwise.
	 * @return The remaining bound.
	 */
	public static int getRemainingBound(Rectangle imageBounds, Rectangle compBounds, boolean getHeight) {
		int remainingBound = 0;
		double pct;
		
		if(getHeight) {
			pct = (double)compBounds.width/(double)imageBounds.width;
			remainingBound = (int)(pct*imageBounds.height);
		}
		else {
			pct = (double)compBounds.height/(double)imageBounds.height;
			remainingBound = (int)(pct*imageBounds.width);
		}
		
		return remainingBound;		
	}

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
        t.setFont( new Font(display,"Arial", 28, SWT.BOLD ) );
        
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
					Image scaled = resize(i, ci.getBounds());
				
					//set the composite background to be the scaled image
					ci.setBackgroundImage(scaled);
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
