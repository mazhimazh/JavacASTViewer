package astview.provider;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import astview.JavacASTNode;

public class ViewContentProvider extends ArrayContentProvider implements ITreeContentProvider {
	
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
