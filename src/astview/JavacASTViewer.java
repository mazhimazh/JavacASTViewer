package astview;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;


public class JavacASTViewer extends ViewPart {
	public static final String ID = "javacastviewer";
	
	private TreeViewer viewer;

	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.SINGLE);
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setInput(getSite());
	}

	public void setFocus() {
		// not supported
	}

class ViewContentProvider extends ArrayContentProvider implements ITreeContentProvider {

	public Object[] getChildren(Object parentElement) {
		JavacASTNode node = (JavacASTNode) parentElement;
		return node.getChildren().toArray();
	}

	public Object getParent(Object element) {
		JavacASTNode node = (JavacASTNode) element;
		return node.getParent();
	}

	public boolean hasChildren(Object element) {
		JavacASTNode node = (JavacASTNode) element;
		return node.getChildren().size() > 0 ? true : false;
	}

	public Object[] getElements(Object inputElement) {
		JavacASTNode compilatinUnitNode = new JavacASTNode();
		compilatinUnitNode.setId(001);
		compilatinUnitNode.setName("JCCompilationUnit");
		
		JavacASTNode importNode = new JavacASTNode();
		importNode.setId(002);
		importNode.setName("JCImport");
		
		JavacASTNode classNode = new JavacASTNode();
		classNode.setId(003);
		classNode.setName("JCClassDecl");

		compilatinUnitNode.getChildren().add(importNode);
		compilatinUnitNode.getChildren().add(classNode);
		importNode.setParent(compilatinUnitNode);
		classNode.setParent(compilatinUnitNode);
		
		return new JavacASTNode[] {compilatinUnitNode};
	}

}

class ViewLabelProvider extends LabelProvider {
	public Image getColumnImage(Object element) {
		return null;
	}

	public String getColumnText(Object element) {
		return ((JavacASTNode) element).toString();
	}
}
}