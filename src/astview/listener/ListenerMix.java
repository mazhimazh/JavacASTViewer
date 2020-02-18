package astview.listener;

import org.eclipse.core.filebuffers.IFileBuffer;
import org.eclipse.core.filebuffers.IFileBufferListener;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;

import astview.JavacASTViewer;

public class ListenerMix implements ISelectionListener, 
									IFileBufferListener, 
									IDocumentListener, 
									ISelectionChangedListener, 
									IDoubleClickListener, 
									IPartListener2 {

//	private boolean fASTViewVisible= true;
	private JavacASTViewer fView;

	public ListenerMix(JavacASTViewer view) {
		fView= view;
	}

	public void dispose() {
		fView= null;
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
//		if (fASTViewVisible) {
//			fView.handleEditorPostSelectionChanged(part, selection);
//		}
		System.out.println("selectionChanged(IWorkbenchPart part, ISelection selection)");
	}

	@Override
	public void bufferCreated(IFileBuffer buffer) {
		// not interesting
	}

	@Override
	public void bufferDisposed(IFileBuffer buffer) {
		if (buffer instanceof ITextFileBuffer) {
			fView.handleDocumentDisposed();
		}
		System.out.println("bufferDisposed");
	}

	@Override
	public void bufferContentAboutToBeReplaced(IFileBuffer buffer) {
		// not interesting
	}

	@Override
	public void bufferContentReplaced(IFileBuffer buffer) {
		// not interesting
	}

	@Override
	public void stateChanging(IFileBuffer buffer) {
		// not interesting
	}

	@Override
	public void dirtyStateChanged(IFileBuffer buffer, boolean isDirty) {
		// not interesting
	}

	@Override
	public void stateValidationChanged(IFileBuffer buffer, boolean isStateValidated) {
		// not interesting
	}

	@Override
	public void underlyingFileMoved(IFileBuffer buffer, IPath path) {
		// not interesting
	}

	@Override
	public void underlyingFileDeleted(IFileBuffer buffer) {
		// not interesting
	}

	@Override
	public void stateChangeFailed(IFileBuffer buffer) {
		// not interesting
	}

	@Override
	public void documentAboutToBeChanged(DocumentEvent event) {
		// not interesting
	}

	@Override
	public void documentChanged(DocumentEvent event) {
		fView.handleDocumentChanged();
		System.out.println("documentChanged");
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
//		fView.handleSelectionChanged(event.getSelection());
		System.out.println("selectionChanged(SelectionChangedEvent event)");
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {
//		fView.handleDoubleClick();
		System.out.println("doubleClick");
	}

	@Override
	public void partHidden(IWorkbenchPartReference partRef) {
//		IWorkbenchPart part= partRef.getPart(false);
//		if (part == fView) {
//			fASTViewVisible= false;
//		}
		System.out.println("partHidden");
	}

	@Override
	public void partVisible(IWorkbenchPartReference partRef) {
//		IWorkbenchPart part= partRef.getPart(false);
//		if (part == fView) {
//			fASTViewVisible= true;
//		}
		System.out.println("partVisible");
	}

	@Override
	public void partActivated(IWorkbenchPartReference partRef) {
		// not interesting
	}

	@Override
	public void partBroughtToTop(IWorkbenchPartReference partRef) {
		// not interesting
	}

	@Override
	public void partClosed(IWorkbenchPartReference partRef) {
//		fView.notifyWorkbenchPartClosed(partRef);
		System.out.println("partClosed");
	}

	@Override
	public void partDeactivated(IWorkbenchPartReference partRef) {
		// not interesting
	}

	@Override
	public void partOpened(IWorkbenchPartReference partRef) {
		// not interesting
	}

	@Override
	public void partInputChanged(IWorkbenchPartReference partRef) {
		// not interesting
	}
}
