package astview.test;

import java.util.ArrayList;
import java.util.List;

public class TestASTNode {
	private int id;
	private String name;
	private List<TestASTNode> children;
	private TestASTNode parent = null;

	public TestASTNode() {
		children = new ArrayList<TestASTNode>();
	}

	public List<TestASTNode> getChildren() {
		return children;
	}

	public void setChildren(List<TestASTNode> children) {
		this.children = children;
	}

	public TestASTNode getParent() {
		return parent;
	}

	public void setParent(TestASTNode parent) {
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
