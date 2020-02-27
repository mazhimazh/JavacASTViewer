package astview;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;

import org.osgi.framework.Bundle;

public class ASTViewImages {

	private static final IPath ICONS_PATH = new Path("$nl$/icons"); 

	public static final String COLLAPSE = "collapseall.png"; 
	public static final String EXPAND = "expandall.png"; 
	public static final String REFRESH = "refresh.png"; 
	public static final String CLEAR = "clear.png"; 


	public static void setImageDescriptors(IAction action, String type) {
		ImageDescriptor id = create("d", type);
		if (id != null)
			action.setDisabledImageDescriptor(id);

		id = create("e", type); 
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