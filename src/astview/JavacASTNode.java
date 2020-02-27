package astview;

import java.util.ArrayList;
import java.util.List;

public class JavacASTNode {

	private String property;
	private String type;
	private String value;

	private List<JavacASTNode> children = null;
	private JavacASTNode parent = null;
	
	private int startpos,endpos;

	public JavacASTNode(String property, String type) {
		this.property = property;
		this.type = type;
		children = new ArrayList<JavacASTNode>();
	}

	public JavacASTNode(String property, String type, String value) {
		this(property, type);
		this.value = value;
	}

	public JavacASTNode(int startpos,int endpos) {
		children = new ArrayList<JavacASTNode>();
		this.startpos = startpos;
		this.endpos = endpos;
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

	public String getProperty() {
		return property;
	}

	public void setProperty(String name) {
		this.property = name;
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
	
	

	public int getStartpos() {
		return startpos;
	}

	public void setStartpos(int startpos) {
		this.startpos = startpos;
	}

	public int getEndpos() {
		return endpos;
	}

	public void setEndpos(int endpos) {
		this.endpos = endpos;
	}

	public String toString() {
		String display = property;
		if (type != null && type.length() > 0) {
			display = display + "={" + type.trim() + "}";
		} else {
			display = display + "=";
		}
		if (value != null && value.length() > 0) {
			display = display + " " + value.trim();
		}
		return display;
	}

}
