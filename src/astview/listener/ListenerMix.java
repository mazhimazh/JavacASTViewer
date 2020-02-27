package astview.listener;

import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;

import astview.JavacASTViewer;

public class ListenerMix implements IDocumentListener, 
									IDoubleClickListener {

	private JavacASTViewer fView;

	public ListenerMix(JavacASTViewer view) {
		fView= view;
	}

	public void dispose() {
		fView= null;
	}

	@Override
	public void documentAboutToBeChanged(DocumentEvent event) {
		// not interesting
	}

	@Override
	public void documentChanged(DocumentEvent event) {
		fView.handleDocumentChanged();
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {
		fView.handleDoubleClick();
	}

}
