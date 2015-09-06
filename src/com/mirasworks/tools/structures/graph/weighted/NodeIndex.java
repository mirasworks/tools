package com.mirasworks.tools.structures.graph.weighted;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeIndex<T extends Comparable<T>> {

	@SuppressWarnings("unused")
	private Logger l = LoggerFactory.getLogger(NodeIndex.class);
	@SuppressWarnings("rawtypes")
	private SortedMap<Comparable<T>, Node<T>> dico = new TreeMap<Comparable<T>, Node<T>>();

	public void register(Node<T> node) {
		if (!dico.containsKey(node.getContent())) {
			dico.put(node.getContent(), node);
		}
	}

	public Node<T> getOrCreate(T content) {
		Node<T> node = null;
		if (dico.containsKey(content)) {
			node = (Node<T>) dico.get(content);
			l.info("retrieved from dico : {}", node);
		} else {
			node = new Node<T>(this);
			node.setContent(content);
			dico.put(content, node);
		}

		return node;
	}
	
	public Collection<Node<T>>  getAllNodes() {
		return dico.values();
	}

}
