package astview;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;

import org.osgi.framework.Bundle;

public class ASTViewImages {

	private static final IPath ICONS_PATH = new Path("$nl$/icons"); //$NON-NLS-1$

	public static final String COLLAPSE = "collapseall.png"; //$NON-NLS-1$
	public static final String EXPAND = "expandall.png"; //$NON-NLS-1$
	public static final String LINK_WITH_EDITOR = "synced.png"; //$NON-NLS-1$

	public static final String SETFOCUS = "setfocus.png"; //$NON-NLS-1$
	public static final String REFRESH = "refresh.png"; //$NON-NLS-1$
	public static final String CLEAR = "clear.png"; //$NON-NLS-1$

	public static final String ADD_TO_TRAY = "add.png"; //$NON-NLS-1$

	// ---- Helper methods to access icons on the file system
	// --------------------------------------

	public static void setImageDescriptors(IAction action, String type) {
		ImageDescriptor id = create("d", type); //$NON-NLS-1$
		if (id != null)
			action.setDisabledImageDescriptor(id);

		id = create("e", type); //$NON-NLS-1$
		if (id != null) {
			action.setHoverImageDescriptor(id);
			action.setImageDescriptor(id);
		} else {
			action.setImageDescriptor(ImageDescriptor.getMissingImageDescriptor());
		}
	}

	private static ImageDescriptor create(String prefix, String name) {
		IPath path = ICONS_PATH.append(prefix).append(name);
		ASTViewPlugin ap = ASTViewPlugin.getDefault();
		Bundle bl = ap.getBundle();
		return createImageDescriptor(bl, path);
	}

	/*
	 * Since 3.1.1. Load from icon paths with $NL$
	 */
	public static ImageDescriptor createImageDescriptor(Bundle bundle, IPath path) {
		URL url = FileLocator.find(bundle, path, null);
		if (url != null) {
			return ImageDescriptor.createFromURL(url);
		}
		return null;
	}
}