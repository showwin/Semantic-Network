import java.io.*;
import java.util.*;

public class Network{
	List<Link> network = new ArrayList<Link>();
	
	public void start() throws IOException {
		while(true){
			//コマンド入力
			BufferedReader r = new BufferedReader(new InputStreamReader(System.in), 1);
			String s = r.readLine();
			String[] sAry = s.split(",");
			//コマンド処理
			if("end".equals(sAry[0])) break;
			if("show".equals(sAry[0])) showLink();
			if("setInit".equals(sAry[0])) setInit();
			if(sAry.length == 3){
				if(sAry[2].endsWith("?")){
					tfCheck(sAry[0], sAry[1], sAry[2].substring(0,sAry[2].length()-1));
				}else if("?x".equals(sAry[0])){
					sSearch(sAry[1], sAry[2]);
				}else if("?x".equals(sAry[2])){
					oSearch(sAry[0], sAry[1]);
				}else{
					Link l = new Link(sAry[0], sAry[1], sAry[2]);
					network.add(l);
				}
			}
		}
	}
	
	//主語検索
	public void sSearch(String r, String o){
		List<String> results;
		for(Link link : network){
			if(r.equals(link.getRelationship()) && o.equals(link.getObject())) results.add(link.getString());
		}
		return results;
	}
	
	//述語検索
	public void oSearch(String s, String r){
		
	}
	
	//and検索
	public void andSearch(){
		
	}
	
	//or検索
	public void orSearch(){
		
	}
	
	//真偽チェック
	public void tfCheck(String s, String r, String o){
		boolean flg = false;
		for(Link link : network){
			if(s.equals(link.getSubject()) && r.equals(link.getRelationship()) && o.equals(link.getObject())) flg = true;
		}
		if(flg){
			System.out.println("TRUE");
		}else{
			System.out.println("FALSE");
		}
	}
	
	public void showLink(){
		for(Link link : network){
			System.out.println(link.getSubject()+" : "+link.getRelationship()+" : "+link.getObject());
		}
	}
	
	public void setInit(){
		Link l;
		l= new Link("S.Jobs", "is-a", "Former Apple CEO");
		network.add(l);
		l = new Link("S.Jobs", "likes", "The Beatles");
		network.add(l);
		l = new Link("S.Jobs", "is-a", "Apple's Founder");
		network.add(l);
		l = new Link("S.Wozniak", "is-a", "Apple's Founder");
		network.add(l);
		l = new Link("S.Wozniak", "has-a", "Doctor of Science");
		network.add(l);
	}
}