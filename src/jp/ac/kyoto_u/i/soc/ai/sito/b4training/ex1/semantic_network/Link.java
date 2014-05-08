package jp.ac.kyoto_u.i.soc.ai.sito.b4training.ex1.semantic_network;

public class Link {
	Node subject;
	Node object;
	String relationship;

	Link(String s, String r, String o) {
		Node sNode = new Node(s);
		Node oNode = new Node(o);
		subject = sNode;
		object = oNode;
		relationship = r;
	}

	public String getSubject() {
		return subject.getValue();
	}

	public String getObject() {
		return object.getValue();
	}

	public String getRelationship() {
		return relationship;
	}
}
