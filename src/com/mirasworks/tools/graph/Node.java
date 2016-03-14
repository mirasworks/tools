package com.mirasworks.tools.graph;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Node {

	private Logger l = LoggerFactory.getLogger(Node.class);
	private final int id;
	// what we want is something fast to read not really fast to insert
	private List<Node> childs = new ArrayList<Node>();
	private List<Node> parents = new ArrayList<Node>();

	public Node(Integer id) {
		super();
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	private void addParent(Node node) {
		if (node != null) {
			boolean contains = false;
			int nodeId = node.getId();
			for (Node parent : parents) {
				if (parent.getId() == nodeId) {
					contains = true;
				}
			}
			if (contains == false) {
				parents.add(node);
			}
		} else {
			// seriously ? never trust anyone even if it's me :)
			l.error("try to add a null node as child of nodeId " + id);
		}
	}
	
	public void add(Node node) {
		if (node != null) {
			boolean contains = false;
			int nodeId = node.getId();
			for (Node child : childs) {
				if (child.getId() == nodeId) {
					contains = true;
				}
			}
			if (contains == false) {
				childs.add(node);
				node.addParent(this);
			}
		} else {
			// seriously ? never trust anyone even if it's me :)
			l.error("try to add a null node as child of nodeId " + id);
		}
	}



	@Override
	public String toString() {
		StringBuilder stChilds = new StringBuilder();
		stChilds.append("{");
		for (Node child : childs) {
			stChilds.append(child.getId()).append(",");
		}
		int lc = stChilds.length();
		if (lc > 1) {
			stChilds.delete(lc - 1, lc);
		}
		stChilds.append("}");

		return "node.Id:" + id + " childs" + stChilds;
	}

}
