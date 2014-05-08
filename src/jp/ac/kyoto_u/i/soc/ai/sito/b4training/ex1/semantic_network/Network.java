package jp.ac.kyoto_u.i.soc.ai.sito.b4training.ex1.semantic_network;

import java.io.*;
import java.util.*;

public class Network {
	private static final String WHITE_SPACE = " ";
	private static final String STRING_SUCCESS = "Success!";
	private static final String STRING_QUESTION = "?";
	private static final String STRING_WHAT = "?x";
	private static final String STRING_SMALL_OR = "or";
	private static final String STRING_BIG_OR = "OR";
	private static final String STRING_SMALL_AND = "and";
	private static final String STRING_BIG_AND = "AND";
	private static final String STRING_SETUP = "setUp";
	private static final String STRING_SHOW = "show";
	private static final String STRING_TRUE = "True";
	private static final String STRING_FALSE = "False";
	private static final String STRING_END = "end";
	List<Link> network = new ArrayList<Link>();
	List<String> results = new ArrayList<String>();
	
	/**
	 * コマンドを受け取ってネットワークに作用させるメインメソッドです。
	 * @throws IOException
	 */
	public void start() throws IOException {
		while (true) {
			// コマンド入力
			System.out.println("");
			BufferedReader r = new BufferedReader(new InputStreamReader(System.in), 1);
			String[] sAry = r.readLine().split(WHITE_SPACE);
			// コマンド処理
			if (sAry.length == 1) {
				if (STRING_END.equals(sAry[0]))
					break;
				shortcut(sAry[0]);
				continue;
			}
			if (sAry.length >= 3) {
				results = proc(sAry[0], sAry[1], sAry[2]);
				for (int i = 1; i < (sAry.length + 1) / 4; i++) {
					nextSearch(sAry[4 * i - 1], sAry[4 * i], sAry[4 * i + 1], sAry[4 * i + 2]);
				}
			}
			showResults();
		}
	}
	
	/**
	 * JUnitでテストするようのインターフェイスです。
	 * 中身はstartメソッドと変わりません。
	 * @param query
	 * @return クエリの結果
	 */
	public String ask(String query){
		// コマンド入力
		String[] sAry = query.split(WHITE_SPACE);
		// コマンド処理
		if (sAry.length == 1) {
			if (STRING_END.equals(sAry[0]))
				return "";
			shortcut(sAry[0]);
			return "";
		}
		if (sAry.length >= 3) {
			results = proc(sAry[0], sAry[1], sAry[2]);
			for (int i = 1; i < (sAry.length + 1) / 4; i++) {
				nextSearch(sAry[4 * i - 1], sAry[4 * i], sAry[4 * i + 1], sAry[4 * i + 2]);
			}
		}
		return returnResults();
	}
	
	// JUnit用インターフェイス(表示のための結果整形)
	private String returnResults() {
		// 真偽チェックでOR検索した場合
		if (results.size() >= 2
				&& (STRING_FALSE.equals(results.get(0)) || STRING_TRUE.equals(results
						.get(0)))) {
			String flg = STRING_FALSE;
			for (String result : results) {
				if (STRING_TRUE.equals(result))
					flg = STRING_TRUE;
			}
			return flg;
		} else if (results.size() == 0) {
			return STRING_FALSE;
		} else {
			String result = "";
			for (String r : results) {
				result += " AND " + r;
			}
			return result.substring(5, result.length());
		}
	}

	// ショートカットコマンド
	private void shortcut(String command) {
		if (STRING_SHOW.equals(command))
			showLink();
		if (STRING_SETUP.equals(command))
			setUp();
	}

	// 2つめ以降のクエリ処理
	private void nextSearch(String cond, String s, String r, String o) {
		if (STRING_BIG_AND.equals(cond) || STRING_SMALL_AND.equals("cond")) {
			andSearch(s, r, o);
		} else if (STRING_BIG_OR.equals(cond) || STRING_SMALL_OR.equals(cond)) {
			orSearch(s, r, o);
		}
	}

	// and検索
	private void andSearch(String s, String r, String o) {
		List<String> answers = new ArrayList<String>();
		List<String> temp = new ArrayList<String>();
		answers = proc(s, r, o);
		for (String result : results) {
			for (String answer : answers) {
				if (result.equals(answer))
					temp.add(answer);
			}
		}
		results = temp;
	}

	// or検索
	private void orSearch(String s, String r, String o) {
		List<String> answers = new ArrayList<String>();
		answers = proc(s, r, o);
		for (String answer : answers) {
			results.add(answer);
		}

	}

	// コマンドによる検索/リンク作成の割り当て
	private List<String> proc(String s, String r, String o) {
		if (o.endsWith(STRING_QUESTION)) {
			return tfCheck(s, r, o.substring(0, o.length() - 1));
		} else if (STRING_WHAT.equals(s)) {
			return sSearch(r, o);
		} else if (STRING_WHAT.equals(o)) {
			return oSearch(s, r);
		} else {
			return makeLink(s, r, o);
		}
	}

	// リンク作成
	private List<String> makeLink(String s, String r, String o) {
		List<String> message = new ArrayList<String>();
		Link l = new Link(s, r, o);
		network.add(l);
		message.add(STRING_SUCCESS);
		return message;
	}

	// 主語検索
	private List<String> sSearch(String r, String o) {
		List<String> answers = new ArrayList<String>();
		List<String> deepAnswers = new ArrayList<String>();
		for (Link link : network) {
			if (r.equals(link.getRelationship()) && o.equals(link.getObject()))
				answers.add(link.getSubject());
		}
		for (String answer : answers) {
			deepAnswers = sSearch(r, answer);
		}
		for (String dAns : deepAnswers) {
			answers.add(dAns);
		}
		return answers;
	}

	// 述語検索
	private List<String> oSearch(String s, String r) {
		List<String> answers = new ArrayList<String>();
		List<String> deepAnswers = new ArrayList<String>();
		for (Link link : network) {
			if (s.equals(link.getSubject()) && r.equals(link.getRelationship()))
				answers.add(link.getObject());
		}
		for (String answer : answers) {
			deepAnswers = oSearch(answer, r);
		}
		for (String dAns : deepAnswers) {
			answers.add(dAns);
		}
		return answers;
	}

	// 真偽検索
	private List<String> tfCheck(String s, String r, String o) {
		List<String> answers = new ArrayList<String>();
		boolean flg = false;
		List<String> candidates = oSearch(s, r);
		for (String c : candidates) {
			if (c.equals(o))
				flg = true;
		}
		if (flg) {
			answers.add(STRING_TRUE);
		} else {
			answers.add(STRING_FALSE);
		}
		return answers;
	}

	// 結果表示のための整形
	private void showResults() {
		// 真偽検索でOR検索した場合
		if (results.size() >= 2
				&& (STRING_FALSE.equals(results.get(0)) || STRING_TRUE.equals(results.get(0)))) {
			String flg = STRING_FALSE;
			for (String result : results) {
				if (STRING_TRUE.equals(result))
					flg = STRING_TRUE;
			}
			System.out.println(flg);
		// 結果なしの場合 & 真偽検索でFalseの場合
		} else if (results.size() == 0) {
			System.out.println(STRING_FALSE);
		// それ以外
		} else {
			String result = "";
			for (String r : results) {
				result += " AND " + r;
			}
			System.out.println(result.substring(5, result.length()));
		}
	}

	// 現在作られているリンクの表示
	private void showLink() {
		for (Link link : network) {
			System.out.println(link.getSubject() + " : "
					+ link.getRelationship() + " : " + link.getObject());
		}
	}

	// 初期化
	public void setUp() {
		Link l;
		l = new Link("S.Jobs", "is-a", "Former_Apple_CEO");
		network.add(l);
		l = new Link("S.Jobs", "likes", "The_Beatles");
		network.add(l);
		l = new Link("S.Jobs", "is-a", "Apple's_Founder");
		network.add(l);
		l = new Link("Apple's_Founder", "is-a", "Genius");
		network.add(l);
		l = new Link("S.Wozniak", "is-a", "Apple's_Founder");
		network.add(l);
		l = new Link("S.Wozniak", "has-a", "Doctor_of_Science");
		network.add(l);
	}
}
