
public class Node {
	public int power;
	public String v;
	
	public Node(String v) {
		this.power = 1;
		this.v = v;
	}
	
	public void inc() {
		this.power++;
	}
	
	public boolean equals(Node n) {
		return n.v == this.v 
		&& n.power == this.power;
	}
}
