package astview.provider;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import astview.JavacASTNode;

public class ViewLabelProvider extends LabelProvider {
	public Image getColumnImage(Object element) {
		return null;
	}

	public String getColumnText(Object element) {
		return ((JavacASTNode) element).toString();
	}
}