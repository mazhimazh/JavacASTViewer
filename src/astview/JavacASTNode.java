package astview;

import java.util.ArrayList;
import java.util.List;

public class JavacASTNode {
	private int id;
	private String name;
	private List<JavacASTNode> children;
	private JavacASTNode parent = null;

	public JavacASTNode() {
		children = new ArrayList<JavacASTNode>();
	}

	public List<JavacASTNode> getChildren() {
		return children;
	}

	public void setChildren(List<JavacASTNode> children) {
		this.children = children;
	}

	public JavacASTNode getParent() {
		return parent;
	}

	public void setParent(JavacASTNode parent) {
		this.parent = parent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}