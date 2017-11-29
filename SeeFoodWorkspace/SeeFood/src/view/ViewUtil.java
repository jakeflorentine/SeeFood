package view;

import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import custom.objects.SeefoodImage;
import util.ParserUtil;
import util.WebServiceUtil;

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
	}

	/**
	 * 
	 * @param parent
	 * @param style
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public static void launchUploadView(Composite parent, int style, String parentFilePath, String[] files)
			throws UnknownHostException, IOException {
		uploadImageView = new UploadImageView(parent, style);
		uploadImageView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 3));
		parent.layout();

		uploadImages(parentFilePath, files);
	}

	/**
	 * 
	 * @param parentFilePath
	 * @param files
	 */
	public static void uploadImages(String parentFilePath, String[] files) {
		if (parentFilePath != null && files != null) {

			// parse all files read in from user selection
			files = ParserUtil.parseFiles(files);

			SeefoodImage[] results = WebServiceUtil.getResults(parentFilePath, files);
			// uploadImageView.displayImages(parentFilePath, files);
			uploadImageView.displaySeefoodImages(results);
			// uploadImageView.fillImageComp(Arrays.asList(results));
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
