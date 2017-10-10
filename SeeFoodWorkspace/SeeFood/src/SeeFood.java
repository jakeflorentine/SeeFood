import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

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
        c.setLayout(new GridLayout(2, true));
        c.setBackground(new Color(display, 255, 252, 155));
        
        Composite ci = new Composite(c, SWT.None);
        ci.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
        
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
		
	}

}