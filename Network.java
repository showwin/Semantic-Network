
import java.io.*;
import java.util.*;

public class Network{
	List<Link> network = new ArrayList<Link>();
	List<String> results = new ArrayList<String>();
	
	public void start() throws IOException {
		while(true){
			//コマンド入力
			System.out.println("");
			BufferedReader r = new BufferedReader(new InputStreamReader(System.in), 1);
			String[] sAry = r.readLine().split(",");
			//コマンド処理
			if(sAry.length==1){
				if("end".equals(sAry[0])) break;
				shortcut(sAry[0]);
				continue;
			}
			if(sAry.length>=3){
				results = proc(sAry[0],sAry[1],sAry[2]);
				for(int i=1; i<(sAry.length+1)/4; i++){
					nextSearch(sAry[4*i-1], sAry[4*i], sAry[4*i+1], sAry[4*i+2]);
				}
			}
			showResults();
		}
	}
	
	//ショートカットコマンド
	public void shortcut(String command){
		if("show".equals(command)) showLink();
		if("setUp".equals(command)) setUp();
	}
	
	//2つめ以降のクエリ
	public void nextSearch(String cond, String s, String r, String o){
		if("AND".equals(cond) || "and".equals("cond")){
			andSearch(s, r, o);
		}else if("OR".equals(cond) || "or".equals(cond)){
			orSearch(s, r, o);
		}
	}
	
	//and検索
	public void andSearch(String s, String r, String o){
		List<String> answers = new ArrayList<String>();
		List<String> temp = new ArrayList<String>();
		answers = proc(s,r,o);
		for(String result : results){
			for(String answer : answers){
				if(result.equals(answer)) temp.add(answer);
			}
		}
		results = temp;
	}
	
	//or検索
	public void orSearch(String s, String r, String o){
		List<String> answers = new ArrayList<String>();
		answers = proc(s,r,o);
		for(String answer : answers){
			results.add(answer);
		}
		
	}
	
	//コマンドによる仕事の割り当て
	public List<String> proc(String s, String r, String o){
		if(o.endsWith("?")){
			return tfCheck(s, r, o.substring(0, o.length()-1));
		}else if("?x".equals(s)){
			return sSearch(r, o);
		}else if("?x".equals(o)){
			return oSearch(s, r);
		}else{
			return makeLink(s, r, o);
		}
	}
	
	//リンク作成
	public List<String> makeLink(String s, String r, String o){
		List<String> message = new ArrayList<String>();
		Link l = new Link(s, r, o);
		network.add(l);
		message.add("Success!");
		return message;
	}
	
	
	//主語検索
	public List<String> sSearch(String r, String o){
		List<String> answers = new ArrayList<String>();
		List<String> deepAnswers = new ArrayList<String>();
		for(Link link : network){
			if(r.equals(link.getRelationship()) && o.equals(link.getObject())) answers.add(link.getSubject());
		}
		for(String answer : answers){
			deepAnswers = sSearch(r, answer);	
		}
		for(String dAns : deepAnswers){
			answers.add(dAns);
		}
		return answers;
	}
	
	//述語検索
	public List<String> oSearch(String s, String r){
		List<String> answers = new ArrayList<String>();
		List<String> deepAnswers = new ArrayList<String>();
		for(Link link : network){
			if(s.equals(link.getSubject()) && r.equals(link.getRelationship())) answers.add(link.getObject());
		}
		for(String answer : answers){
			deepAnswers = oSearch(answer, r);
		}
		for(String dAns : deepAnswers){
			answers.add(dAns);
		}
		return answers;
	}
	
	//真偽チェック
	public List<String> tfCheck(String s, String r, String o){
		List<String> answers = new ArrayList<String>();
		boolean flg = false;
		List<String> candidates = oSearch(s, r);
		for(String c : candidates){
			if(c.equals(o)) flg = true;
		}
		if(flg){
			answers.add("True");
		}else{
			answers.add("False");
		}
		return answers;
	}
	
	//結果の表示
	public void showResults(){
		// 真偽チェックでOR検索した場合
		if(results.size()>=2 && ("False".equals(results.get(0)) || "True".equals(results.get(0)))){
			String flg = "False";
			for(String result : results){
				if ("True".equals(result)) flg = "True";
			}
			System.out.println(flg);
		}else if(results.size()==0){
			System.out.println("False");
		}else{
			String result = "";
			for(String r : results){
				result += " AND "+r;
			}
			System.out.println(result.substring(5,result.length()));
		}
	}
	
	//現在作られているリンクの表示
	public void showLink(){
		for(Link link : network){
			System.out.println(link.getSubject()+" : "+link.getRelationship()+" : "+link.getObject());
		}
	}
	
	//初期化
	public void setUp(){
		Link l;
		l= new Link("S.Jobs", "is-a", "Former Apple CEO");
		network.add(l);
		l = new Link("S.Jobs", "likes", "The Beatles");
		network.add(l);
		l = new Link("S.Jobs", "is-a", "Apple's Founder");
		network.add(l);
		l = new Link("Apple's Founder", "is-a", "Genius");
		network.add(l);
		l = new Link("S.Wozniak", "is-a", "Apple's Founder");
		network.add(l);
		l = new Link("S.Wozniak", "has-a", "Doctor of Science");
		network.add(l);
	}
}