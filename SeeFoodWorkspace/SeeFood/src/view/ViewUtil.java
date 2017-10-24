package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class ViewUtil {
	private static Composite openView = null;
	private static StackLayout layout = new StackLayout();
	private static GalleryView galleryView;
	private static HomeView homeView;
	private static UploadImageView uploadImageView;

	/**
	 * 
	 * @param parent
	 * @param style
	 */
	public static void launchHomeView(Composite parent, int style) {
		homeView = new HomeView(parent, style);
		homeView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 3));
		parent.layout();
		// disposePriorView(homeView);
	}

	/**
	 * 
	 * @param parent
	 * @param style
	 */
	public static void launchGallery(Composite parent, int style) {
		galleryView = new GalleryView(parent, style);
		galleryView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 3));
		parent.layout();
		// disposePriorView(galleryView);
	}

	/**
	 * 
	 * @param parent
	 * @param style
	 */
	public static void launchUploadView(Composite parent, int style, String parentFilePath, String[] files) {
		uploadImageView = new UploadImageView(parent, style);
		uploadImageView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 3));
		parent.layout();

		uploadImages(parentFilePath, files);
		// disposePriorView(uploadImageView);
	}

	/**
	 * 
	 * @param parentFilePath
	 * @param files
	 */
	public static void uploadImages(String parentFilePath, String[] files) {
		if (parentFilePath != null && files != null) {
			uploadImageView.displayImages(parentFilePath, files);
		}
	}

	/**
	 * 
	 * @param newView
	 * @return
	 */
	private static boolean disposePriorView(Composite newView) {
		// unable to change prior view if the new one is null
		if (newView == null) {
			return false;
		}

		// ensure previous view is disposed
		if (openView != null && !openView.isDisposed()) {
			openView.dispose();
		}
		// establish reference to the new open view
		openView = newView;

		// view switched
		return true;
	}

	public static StackLayout getLayout() {
		return layout;
	}

}