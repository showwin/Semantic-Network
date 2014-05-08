package jp.ac.kyoto_u.i.soc.ai.sito.b4training.ex1.semantic_network;

public class Link {
	private Node subject;
	private Node object;
	private String relationship;
	
	/**
	 * Linkの作成時に値を設定するコンストラクタです。
	 * @param s 主語(Subject)
	 * @param r 関係(Relationship)
	 * @param o 述語(Object)
	 */
	Link(String s, String r, String o) {
		Node sNode = new Node(s);
		Node oNode = new Node(o);
		this.subject = sNode;
		this.object = oNode;
		this.relationship = r;
	}

	/**
	 * 主語の値を取得するメソッドです。
	 * @return 主語の値
	 */
	public String getSubject() {
		return this.subject.getValue();
	}

	/**
	 * 述語の値を取得するメソッドです。
	 * @return 述語の値
	 */
	public String getObject() {
		return this.object.getValue();
	}

	/**
	 * 関係の値を取得するメソッドです。
	 * @return 関係の値
	 */
	public String getRelationship() {
		return this.relationship;
	}
}
