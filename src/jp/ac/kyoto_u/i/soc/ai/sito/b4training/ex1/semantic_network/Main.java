package jp.ac.kyoto_u.i.soc.ai.sito.b4training.ex1.semantic_network;

import java.io.IOException;

public class Main {
	/**
	 * Networkのメインのメソッドを動かすメソッドです。
	 * このメソッドを使うことで
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Network network = new Network();
		network.start();
	}
}