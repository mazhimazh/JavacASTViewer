package astview.provider;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;

// 被监听方的视图，需要实现 ISelectionProvider 接口
public class ASTViewSelectionProvider implements ISelectionProvider {
	ListenerList<ISelectionChangedListener> fListeners= new ListenerList<>(ListenerList.IDENTITY);

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		fListeners.add(listener);
	}
	
	TreeViewer fViewer;
	public ASTViewSelectionProvider(TreeViewer fViewer) {
		this.fViewer = fViewer;
	}

	@Override
	public ISelection getSelection() {
		
		System.out.println("getSelection()方法");
		// fViewer的类型为TreeViewer
		IStructuredSelection selection= (IStructuredSelection) fViewer.getSelection();
		ArrayList<Object> externalSelection= new ArrayList<>();
		for (Iterator<?> iter= selection.iterator(); iter.hasNext();) {
//			Object unwrapped= JavacASTViewer.unwrapAttribute(iter.next());
//			if (unwrapped != null)
//				externalSelection.add(unwrapped);
		}
		// Creates a structured selection from the given List.
		return new StructuredSelection(externalSelection);
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		fListeners.remove(listener);
	}

	@Override
	public void setSelection(ISelection selection) {
		//not supported
	}
}
