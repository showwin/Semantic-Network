public class Node{
	String node;
	
	Node(String name){
		node = name;
	}
	
	public void setValue(String newName){
		node = newName;
	}
	
	public String getValue(){
		return node;
	}
}