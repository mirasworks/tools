package com.mirasworks.tools.structures.graph.weighted;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * bad implementation it realy cannot works at all Damien MIRAS
 *
 * @param <T>
 */
public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {

	private Logger l = LoggerFactory.getLogger(Node.class);
	
	private T content;
	private SortedMap<Node<T>, Edge> childs = new TreeMap<Node<T>, Edge>();

	private SortedMap<Float, Node<T>> percentIndex = new TreeMap<Float, Node<T>>();

	private NodeIndex<T> index;

	public Node(NodeIndex<T> nodeIndex) {
		index = nodeIndex;
	}

	public T getContent() {
		return content;
	}

	public boolean isEmpty() {
		return childs.isEmpty();
	}

	public int size() {
		return childs.size();
	}

	public void setContent(T content) {
		this.content = content;
	}

	public Node<T> add(T content) {
		Node<T> node = index.getOrCreate(content);
		addNode(node);
		return node;
	}

	public void addNode(Node<T> node) {
		Edge link = null;

		if (childs.containsKey(node)) {
			link = childs.get(node);
			link.incrementCount();
		} else {
			link = new Edge();
		}

		childs.put(node, link);

		updateScores();
	}

	private void updateScores() {

		int childCount = childs.size();
		Float ratio = 0f;
		if (childCount > 0) {
			ratio = 100.0f / childCount;
		}
		
		
		for (Map.Entry<Node<T>, Edge> entry : childs.entrySet()) {
			Edge v = entry.getValue();
			Node<T> node = entry.getKey();
			int count = v.getCount();
			Float percent = count * ratio;
			v.setPercent(percent);
			percentIndex.put(percent, node);
		}
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("Node@");
		buffer.append(super.hashCode());
		buffer.append(" ");
		buffer.append(content.toString());
		buffer.append("\r\n");
		for (Map.Entry<Node<T>, Edge> entry : childs.entrySet()) {
			Edge link = entry.getValue();
			Node<T> node = entry.getKey();
			buffer.append("\t--");
			buffer.append(link.getPercent());
			buffer.append("%-->");
			buffer.append(node.getContent().toString());
			buffer.append("\r\n");
		}
		return buffer.toString();
	}

	@Override
	public int compareTo(Node<T> o) {
		return this.getContent().compareTo(o.getContent());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node<T> other = (Node<T>) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		return true;
	}

}
