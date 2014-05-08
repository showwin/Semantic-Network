package jp.ac.kyoto_u.i.soc.ai.sito.b4training.ex1.semantic_network;

public class Node {
	private String node;
	
	/**
	 * ノードを作成時に値を設定するコンストラクタです。
	 * @param name ノードの名前
	 */
	Node(String name) {
		this.node = name;
	}
	
	/**
	 * ノードの名前を取得するメソッドです。
	 * @return ノードの名前
	 */
	public String getValue() {
		return this.node;
	}
}
