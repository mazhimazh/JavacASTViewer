package astview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.source.tree.Tree;
import com.sun.source.tree.Tree.Kind;

public class JavacASTNode {
	
	private String name;
	private String type;
	private String value;
	
	private List<JavacASTNode> children = null;
	private JavacASTNode parent = null;
	
	public JavacASTNode(String name, String type) {
		this.name = name;
		this.type = type;
		children = new ArrayList<JavacASTNode>();
	}

	public JavacASTNode(String name, String type, String value) {
		this(name, type);
		this.value = value;
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
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	

	public String toString() {
		String display = name;
		if (type != null && type.length() > 0) {
			display = display + "={" + type.trim() + "}";
		}else {
			display = display + "=";
		}
		if (value != null && value.length() > 0) {
			display = display + " " + value.trim();
		}
		return display;
	}

}




