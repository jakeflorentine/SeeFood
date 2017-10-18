package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class GalleryView extends Composite{

	
	
	public GalleryView(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
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
	}

}
