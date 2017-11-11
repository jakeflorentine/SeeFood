package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import util.WebServiceUtil;

public class SeefoodView {

	public SeefoodView() {
		createContent();
	}

	public static void createContent() {
		// call a system start
		Display display = new Display();

		Shell shell = new Shell(display);
		// the layout manager handle the layout
		// of the widgets in the container
		shell.setLayout(new FillLayout());

		/**
		 * This is where I think we will want to call a class to build the UI NOTE:
		 * remember to dispose widgets to prevent errors
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
		t.setFont(new Font(display, "Arial", 28, SWT.BOLD));

		// fill the content area with the HomeView
		/**
		 * Can probably be replaced with ViewUtil.launchHomeView();
		 */
		HomeView h = new HomeView(c, SWT.NONE);
		h.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 3));

		shell.open();

		// establish a connection to the ec2 after the view is created
		//WebServiceUtil.connectToServer();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();

	}
}
