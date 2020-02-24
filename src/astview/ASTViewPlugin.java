package astview;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import org.eclipse.ui.plugin.AbstractUIPlugin;

public class ASTViewPlugin extends AbstractUIPlugin {

	private static ASTViewPlugin fgDefault;

	public ASTViewPlugin() {
		fgDefault = this;
	}

	public static String getPluginId() {
		return "astview"; 
	}

	public static ASTViewPlugin getDefault() {
		return fgDefault;
	}

	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}

	public static void logErrorMessage(String message) {
		log(new Status(IStatus.ERROR, getPluginId(), IStatus.ERROR, message, null));
	}

	public static void logErrorStatus(String message, IStatus status) {
		if (status == null) {
			logErrorMessage(message);
			return;
		}
		MultiStatus multi = new MultiStatus(getPluginId(), IStatus.ERROR, message, null);
		multi.add(status);
		log(multi);
	}

	public static void log(String message, Throwable e) {
		log(new Status(IStatus.ERROR, getPluginId(), IStatus.ERROR, message, e));
	}

}