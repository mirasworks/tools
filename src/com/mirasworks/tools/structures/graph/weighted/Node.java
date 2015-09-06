package com.mirasworks.tools.structures.graph.weighted;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @deprecated bad implementation it realy cannot works at all
 * @author koda
 *
 * @param <T>
 */
public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {

	private Logger l = LoggerFactory.getLogger(Node.class);
	private T content;
	private SortedMap<Node<T>, LinkInfo> childs = new TreeMap<Node<T>, LinkInfo>();
	private SortedMap<Float, Node<T>> percentIndex = new TreeMap<Float, Node<T>>();

	private Integer totalOutCount = 0;
	private NodeIndex<T> index;
	private LinkInfo parentLink = null;

	public LinkInfo getParentLink() {
		return parentLink;
	}

	public void setParentLink(LinkInfo parentLink) {
		this.parentLink = parentLink;
	}

	private Long id;

	public Node(NodeIndex<T> nodeIndex) {
		index = nodeIndex;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public T findBestMatch() {
		if (percentIndex.isEmpty() == false) {
			Float best = percentIndex.lastKey();
			Node node = percentIndex.get(best);
			if (node != null) {
				//TODO make an unike entry point to pickup the node and update date
				LinkInfo parentLink = node.getParentLink();
				if (parentLink != null) {
					parentLink.updateUsedDate();
				}
				return (T) node.getContent();
			} else {
				return null;
			}
		} else {
			l.info("no best match {}", this.toString());
			return null;
		}

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
		LinkInfo link = null;

		if (childs.containsKey(node)) {
			link = childs.get(node);
			link.incrementCount();
		} else {
			link = new LinkInfo();
		}
		node.setParentLink(link);
		childs.put(node, link);
		totalOutCount++;

		updateScores();
	}

	private void updateScores() {

		Float ratio = 100.0f / totalOutCount;
		// percentIndex.clear();
		for (Map.Entry<Node<T>, LinkInfo> entry : childs.entrySet()) {
			LinkInfo v = entry.getValue();
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
		for (Map.Entry<Node<T>, LinkInfo> entry : childs.entrySet()) {
			LinkInfo link = entry.getValue();
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
