package astview.provider;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;

import astview.JavacASTNode;
import astview.JavacASTVisitor;

public class ViewContentProvider extends ArrayContentProvider implements ITreeContentProvider {
	
	
	public Object[] getElements(Object inputElement) {
		
//		JavacASTNode root = new JavacASTNode("root","JCCompilation");
		JavacASTNode root = null;
		if(inputElement instanceof JCCompilationUnit) {
			JavacASTVisitor visitor = new JavacASTVisitor();
			root  = visitor.traverse((JCCompilationUnit)inputElement);
		}
		
//		JavacASTNode compilatinUnitNode = new JavacASTNode();
//		compilatinUnitNode.setName("JCCompilationUnit");
//		
//		JavacASTNode importNode = new JavacASTNode();
//		importNode.setName("JCImport");
//		
//		JavacASTNode classNode = new JavacASTNode();
//		classNode.setName("JCClassDecl");
//
//		compilatinUnitNode.getChildren().add(importNode);
//		compilatinUnitNode.getChildren().add(classNode);
//		importNode.setParent(compilatinUnitNode);
//		classNode.setParent(compilatinUnitNode);
		
		return new JavacASTNode[] {root};
	}

	public Object getParent(Object element) {
		JavacASTNode node = (JavacASTNode) element;
		return node.getParent();
	}
	
	
	public Object[] getChildren(Object parentElement) {
		JavacASTNode node = (JavacASTNode) parentElement;
		return node.getChildren().toArray();
	}

	public boolean hasChildren(Object element) {
		JavacASTNode node = (JavacASTNode) element;
		return node.getChildren().size() > 0 ? true : false;
	}

	

}
