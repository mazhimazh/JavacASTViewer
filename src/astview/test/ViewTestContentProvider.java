package astview.test;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class ViewTestContentProvider extends ArrayContentProvider implements ITreeContentProvider {

	public Object[] getChildren(Object parentElement) {
		TestASTNode node = (TestASTNode) parentElement;
		return node.getChildren().toArray();
	}

	public Object getParent(Object element) {
		TestASTNode node = (TestASTNode) element;
		return node.getParent();
	}

	public boolean hasChildren(Object element) {
		TestASTNode node = (TestASTNode) element;
		return node.getChildren().size() > 0 ? true : false;
	}

	public Object[] getElements(Object inputElement) {
		TestASTNode compilatinUnitNode = new TestASTNode();
		compilatinUnitNode.setId(001);
		compilatinUnitNode.setName("JCCompilationUnit");

		TestASTNode importNode = new TestASTNode();
		importNode.setId(002);
		importNode.setName("JCImport");

		TestASTNode classNode = new TestASTNode();
		classNode.setId(003);
		classNode.setName("JCClassDecl");

		compilatinUnitNode.getChildren().add(importNode);
		compilatinUnitNode.getChildren().add(classNode);
		importNode.setParent(compilatinUnitNode);
		classNode.setParent(compilatinUnitNode);

		return new TestASTNode[] { compilatinUnitNode };
	}

}