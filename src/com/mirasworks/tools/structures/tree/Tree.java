package com.mirasworks.tools.structures.tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author koda
 *
 * @param <T>
 */
public class Tree<T> {

	private TreeNode<T> root;

	public Tree() {
		super();
	}

	public TreeNode<T> getRoot() {
		return this.root;
	}

	public void setRoot(TreeNode<T> root) {
		this.root = root;
	}

	public int count() {
		int count = 0;

		if (root != null) {
			count = recurseCount(root) + 1; // 1 for the root!
		}

		return count;
	}

	private int recurseCount(TreeNode<T> node) {
		int count = node.getChildrenCount();

		for (TreeNode<T> child : node.getChildren()) {
			count += recurseCount(child);
		}

		return count;
	}

	public boolean exists(T dataToFind) {
		return (find(dataToFind) != null);
	}

	public TreeNode<T> find(T dataToFind) {
		TreeNode<T> node = null;

		if (root != null) {
			node = recurseFind(root, dataToFind);
		}

		return node;
	}

	private TreeNode<T> recurseFind(TreeNode<T> currentNode, T dataToFind) {
		TreeNode<T> node = null;
		int i = 0;

		if (currentNode.getData().equals(dataToFind)) {
			node = currentNode;
		}

		else if (currentNode.hasChildren()) {
			i = 0;
			while (node == null && i < currentNode.getChildrenCount()) {
				node = recurseFind(currentNode.getChildAt(i), dataToFind);
				i++;
			}
		}
		return node;
	}

	public boolean isEmpty() {
		return (root == null);
	}

	public List<TreeNode<T>> getTraversalList(EnumTraversalOrder traversalOrder) {
		List<TreeNode<T>> traversal = null;

		if (root != null) {
			traversal = getTraversalList(root, traversalOrder);
		}

		return traversal;
	}

	public List<TreeNode<T>> getTraversalList(TreeNode<T> node, EnumTraversalOrder order) {
		List<TreeNode<T>> traversalResult = new ArrayList<TreeNode<T>>();

		if (order == EnumTraversalOrder.PRE) {
			buildPreOrder(node, traversalResult);
		}

		else if (order == EnumTraversalOrder.POST) {
			buildPostOrder(node, traversalResult);
		}

		return traversalResult;
	}

	private void buildPreOrder(TreeNode<T> node, List<TreeNode<T>> result) {
		result.add(node);

		for (TreeNode<T> child : node.getChildren()) {
			buildPreOrder(child, result);
		}
	}

	private void buildPostOrder(TreeNode<T> node, List<TreeNode<T>> result) {
		for (TreeNode<T> child : node.getChildren()) {
			buildPostOrder(child, result);
		}

		result.add(node);
	}

	public Map<TreeNode<T>, Integer> buildWithDepth(EnumTraversalOrder order) {
		Map<TreeNode<T>, Integer> returnMap = null;

		if (root != null) {
			returnMap = buildWithDepth(root, order);
		}

		return returnMap;
	}

	public Map<TreeNode<T>, Integer> buildWithDepth(TreeNode<T> node, EnumTraversalOrder order) {
		Map<TreeNode<T>, Integer> traversalResult = new LinkedHashMap<TreeNode<T>, Integer>();

		if (order == EnumTraversalOrder.PRE) {
			buildPreOrderWithDepth(node, traversalResult, 0);
		}

		else if (order == EnumTraversalOrder.POST) {
			buildPostOrderWithDepth(node, traversalResult, 0);
		}

		return traversalResult;
	}

	private void buildPreOrderWithDepth(TreeNode<T> node, Map<TreeNode<T>, Integer> result, int depth) {
		result.put(node, depth);

		for (TreeNode<T> child : node.getChildren()) {
			buildPreOrderWithDepth(child, result, depth + 1);
		}
	}

	private void buildPostOrderWithDepth(TreeNode<T> node, Map<TreeNode<T>, Integer> result, int depth) {
		for (TreeNode<T> child : node.getChildren()) {
			buildPostOrderWithDepth(child, result, depth + 1);
		}

		result.put(node, depth);
	}

	public String toString() {

		String str = "";

		if (root != null) {
			str = getTraversalList(EnumTraversalOrder.PRE).toString();

		}

		return str;
	}

	public String toStringWithDepth() {

		String stringRepresentation = "";

		if (root != null) {
			stringRepresentation = buildWithDepth(EnumTraversalOrder.PRE).toString();
		}

		return stringRepresentation;
	}
}