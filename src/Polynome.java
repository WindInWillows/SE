import java.util.Scanner;

public class Polynome {
	private String raw;
	Scanner scan;
	
	public static void main(String[] args) {
		Polynome po = new Polynome();
		while(po.getInput()){
			po.expression();
			
		}
	}

	
	public Polynome() {
		scan = new Scanner(System.in);
	}
	
	public boolean getInput() {
		String tmp = scan.nextLine();
		System.out.println();
		return true;
	}
	
	public String execCommand() {
		String com = null;
		
		
		return com;
	}

	public boolean expression() {
		
		return true;
	}
	
	public String simplify() {
		
		return null;
	}
	
	public String derivative() {
		
		return null;
	}
}
