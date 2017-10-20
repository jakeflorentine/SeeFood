package view;

import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;

public class ViewUtil {
	private static Composite openView = null;
	private static StackLayout layout = new StackLayout();

	/**
	 * 
	 * @param parent
	 * @param style
	 */
	public static void launchHomeView(Composite parent, int style) {
		HomeView homeView = new HomeView(parent, style);
		// disposePriorView(homeView);
	}

	/**
	 * 
	 * @param parent
	 * @param style
	 */
	public static void launchGallery(Composite parent, int style) {
		GalleryView galleryView = new GalleryView(parent, style);
		// disposePriorView(galleryView);
	}

	/**
	 * 
	 * @param parent
	 * @param style
	 */
	public static void launchUploadView(Composite parent, int style) {
		UploadImageView uploadImageView = new UploadImageView(parent, style);
		// disposePriorView(uploadImageView);
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
