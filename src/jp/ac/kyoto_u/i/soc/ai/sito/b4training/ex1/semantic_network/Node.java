package jp.ac.kyoto_u.i.soc.ai.sito.b4training.ex1.semantic_network;

public class Node {
	String node;

	Node(String name) {
		node = name;
	}

	public void setValue(String newName) {
		node = newName;
	}

	public String getValue() {
		return node;
	}
}
