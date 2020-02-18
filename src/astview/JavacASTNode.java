package astview;

import java.util.ArrayList;
import java.util.List;

public class JavacASTNode {
	
	private String name;
	private String type;
	private List<JavacASTNode> children = null;
	private JavacASTNode parent = null;
	
	public JavacASTNode(String k) {
		
	}
	
	public void test() {
		JavacASTNode x = new JavacASTNode("dd");
		   JavacASTNode jand = new JavacASTNode("pid","JCExpression");
	}
	
	
	public JavacASTNode(String name,String type) {
		this.name = name;
		this.type = type;
	}

	public JavacASTNode() {
		children = new ArrayList<JavacASTNode>();
	}

	public List<JavacASTNode> getChildren() {
		return children;
	}

	public void setChildren(List<JavacASTNode> children) {
		this.children = children;
	}
	
	public void addChild(JavacASTNode node) {
		children.add(node);
	}

	public JavacASTNode getParent() {
		return parent;
	}

	public void setParent(JavacASTNode parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		return name + "=" + type;
	}

}