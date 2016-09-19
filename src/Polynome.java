import java.util.ArrayList;
import java.util.Scanner;

public class Polynome {
	
	public Polynome() {
		scan = new Scanner(System.in);
	}

	// һ�����������
	private Scanner scan;
	// ͨ��ʹ��һ������ΪItem���б��浱ǰ���ʽ������Ҫ��
	private ArrayList<Item> expressionArray = new ArrayList<Item>();
	// ���浱ǰ����ʽ���ַ�����ʽ
	private String tmpStr = "";
	// opCodeΪ�����룬opStrΪ�����֡�������������getInput���޸ģ�������impOperation
	private int opCode = 0;
	private String opStr = "";
	//
	private static final String EXIT_FLAG = "!exit";
	private static final String SIMPLIFY = "simplify";
	private static final String DIFF = "d/d";
	
	
	public static void main(String[] args) {
		Polynome po = new Polynome();
		// ��ӡ��ʾ��Ϣ
		po.prompt();
		while(true){
			//��ȡһ���ַ������ж��Ǳ��ʽ�������������Ӧ�Ĵ����Լ��õ�һ��������
			po.getInput();
			//������һ���л�õĲ�����ִ����Ӧ�Ĳ���
			po.impOperation();
		}
	}
	
	private void impOperation() {
		//���ݲ�����ִ����Ӧ����	
		switch(opCode) {
			case -1: //�˳�
				System.out.println("exit");
				System.exit(0);
				break;
			case 0:	//���³ɹ�
//				System.out.println("ok");
				//print();
				break;
			case 1:	//���³ɹ�
//				System.out.println("ok");
				print();
				break;
			case 2:	//������ʽ
				simplify();
				break;
			case 3:	//��
				derivative();
				break;
			case 4:	//��
				System.out.println("Error: "+opStr+".");
				break;
			default:
				System.err.println("����������������룺");
				break;
		}
	}
	
	private void print() {
		String resStr = "";
		boolean firstFlag = true;//Ҳ�����Ż�
		for (int i=0; i<this.expressionArray.size();i++){
			resStr+=this.expressionArray.get(i).toString(firstFlag);
			firstFlag = false;
		}
		System.out.println(resStr);
	}
	public void prompt(){
		// ϵͳ��ʼ���к��ӡ��ʾ��Ϣ
		// ���ڴ���Ӱ����汾�������ȵ���ʾ��Ϣ
		System.out.println("<Welcome to Polynomials System 1.0>");
	}
	
	public void getInput(){
		
		// ��ȡһ���ַ���
		String strInput = scan.nextLine();
		
		// ���ַ����в���������������Ϊ���ʽ������Ϊ����
		// ��������Ӧ�Ĵ���
		if (strInput.indexOf("!")==-1){
			expression(strInput);
		}
		else{
			command(strInput);
		}
  	}
	
	public void expression(String strInput){
		
		// ������Str�ĺϷ��Խ����ж���ͬʱȥ�����ʽ�еĿո���Ʊ��
		if (validateExpressionAndStrip(strInput) == false){
			opCode = 4;
			opStr  = "Invalid character inside";
			return;
		}
		
		// ����+��-���Ա��ʽ��Ӧ���ַ������в��
		// ���Բ�ֺ�ĸ����ַ�����Ϊ�������ֱ���Item���󣬼��뵽expressionArray��
		expressionArray.clear();
		String itemStr = "+";
		char[] chars = tmpStr.toCharArray();
		for (char ch : chars){
			if (ch == '+'){
				expressionArray.add(new Item(itemStr));
				itemStr = "+";
			}
			else if (ch == '-'){
				expressionArray.add(new Item(itemStr));
				itemStr = "-";
			}
			else{
				itemStr += ch;
			}
		}
		expressionArray.add(new Item(itemStr));
		opCode = 1;
	}
	
	private boolean validateExpressionAndStrip(String str){
		tmpStr = "";
		char[] chars = str.toCharArray();
		for(char ch : chars){
			// ������ ���֡���ĸ���ո�tab��+��-��*��^ ���� ���ַ�
			if(ch != ' ' && ch != '\t' ){
				if(!((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') 
				|| (ch >= 'A' && ch <= 'Z') || ch == '+' || ch == '-'
				||  ch == '*' || ch == '^')){
					//System.out.println(ch);
					return false;
				}
				tmpStr += ch;
			}
		}
		return true;
	}
	
	public void command(String strInput){
		// ������������ĺϷ���
		if (validateCommandAndStrip(strInput) == false)
		{
			opCode = 4;
			opStr = "Wrong command!";
			return;
		}
		
		// �ж���������
		char[] chars = strInput.toCharArray();
		int index;
		if ((index = strInput.indexOf(SIMPLIFY)) != -1) {
			index += SIMPLIFY.length() + 1;
			opCode = 2;//������ʽ
			opStr = strInput.substring(index);
			return;
		}
		else if((index = strInput.indexOf(DIFF)) != -1) {
			index += DIFF.length() + 1;
			opCode = 3;//���ʽ��
			opStr = strInput.substring(index);
		}
		else {
			opCode = 4;//�������
			opStr = "unrecognized command";
			return;
		}
	}
	
	public boolean validateCommandAndStrip(String str){
		// �������������Ƿ��ڣ�ǰ�г��ո���Ʊ��֮����ַ�
		// ����У���˵���������������
		char[] chars = str.toCharArray();
		boolean checkFlag = true;
		for (char ch : chars){
			if(ch == '!'){
				checkFlag = false;
				break;
			}
			else if((ch != ' ' || ch != '\t') && checkFlag){
				return false;
			}
		}
		// !!!����ӹ��� ȥ���ո�
		tmpStr = str;
		return true;
	}

	public void simplify() {
		String resStr = "";
		boolean firstFlag = true;//Ҳ�����Ż�
		
//		tmpP= this;
		for (int i=0; i<this.expressionArray.size();i++){
			resStr += this.expressionArray.get(i).simplify(opStr).toString(firstFlag);
			firstFlag = false;
		}
		System.out.println(resStr);
	}

	public void derivative() {
		String resStr = "";
		boolean firstFlag = true;//Ҳ�����Ż�
		for (int i=0; i<this.expressionArray.size();i++){
			resStr+=this.expressionArray.get(i).diff(this.opStr).toString(firstFlag);
			firstFlag = false;
		}
		System.out.println(resStr);
	}


}


class Node {
	public Node(String v,int power) {
		this.power = power;
		this.v = v;
	}

	public Node() {
	}

	public int power;
	public String v;
	
	public Node(String v) {
		this.power = 1;
		this.v = v;
	}
	
	public void inc(int power) {
		this.power += power;
	}
	
	public void dec() {
		this.power--;
	}
	
	public Node getCopy(){
		Node res = new Node();
		res.power = this.power;
		res.v = this.v;
		return res;
	}
	
	public boolean equals(Node n) {
		return n.v.equals(this.v)
		&& n.power == this.power;
	}
}

class Item {
	public int coe=1;
	public ArrayList<Node> item = new ArrayList<Node>();

	/*
	 *������ʽ��������������������ʽ������ݽṹ��
	 *	���ʽ��ı���������һ��ArrayList���
	 *	���ʽ��ϵ����������
	 */
	//'-','11*x*y*x*8' => -88,Node(x,2),Node(y,1)
	public Item(String exp, boolean isPositive) {
		////����ϵ������������
		if( !isPositive){
			this.coe *= -1;
		}
		////���һ������ʽ��һ��
		//��'11*x*y*x*8'���Ϊ   �б�[11 x y x 8]
		String[] tmp_list=exp.split("\\*");
		for(int i=0;i<tmp_list.length;i++){
			String tmp_i=tmp_list[i];
			Integer num=isNumber(tmp_i);
			
			int power = 1;
			if(tmp_i.indexOf("^") != -1) {
				String[] tmmp = tmp_i.split("\\^");
				power = Integer.parseInt(tmmp[1]);
				tmp_i = tmmp[0];
			}
			
			// num=null ��ʾtmp_i�������֣����򷵻�������֣�int�������������ֱ����ϵ����˼���
			if( num!= null ){
				this.coe *= num;
			}
			// tmp_i Ϊ������������Ĵ������жϱ���tmp_i�Ƿ���ֹ�
			else{
				//�ж��ַ�tmp_i�Ƿ��Ѿ����ֵı�־
				boolean appear_flag=false;// ��Ҳ���ܱ��Ż�
				//����tmp_list���Ѿ�����Ĳ��֣��жϱ���tmp_i�Ƿ���ֹ�
				for(int j=0;j<this.item.size();j++){
					Node Node_j=this.item.get(j);
					// ����tmp_i������֮ǰ������ַ����У���ԭ������Ӧ�ڵ��ָ��+1
					if(Node_j.v.equals(tmp_i)){
						Node_j.inc(power);
						appear_flag=true;
					}
				}
				//���δ���ֵı���
				if (appear_flag==false){
					this.item.add(new Node(tmp_i, power));
				}
			}
		}
	}
	
	public Item() {}

	public Item(String itemStr) {
		this(itemStr.substring(1),itemStr.toCharArray()[0] == '+' );
	}

	public Item diff(String x) {
		Item tmp = getCopy();
		for(int i=0;i<tmp.item.size();i++){
			if (tmp.item.get(i).v.equals(x)){
				tmp.coe *= tmp.item.get(i).power;
				tmp.item.get(i).dec();
				return tmp;
			}
		}
		return null;
	}
	public Item getCopy(){
		// Ҳ�����Ż�
		Item res = new Item();
		res.coe = this.coe;
		for(int i=0;i<this.item.size();i++){
			res.item.add(this.item.get(i).getCopy());
		}
		return res;
	}
	
	//�����ǰ��
	public String toString(Boolean firstFlag) {
		String str = "";
		if (coe == 0) return "";
		if(coe > 0 && !firstFlag)
			str += "+";
		str += coe;
		for(Node n : item) {
			if(n.power > 1 && !n.v.equals(""))
				str += "*"+n.v+"^"+n.power;
			else if(n.power == 1 && !n.v.equals(""))
				str += "*"+n.v;
		}
		return str;
	}
	
	//�ж������Ƿ��ܺϲ�
	public boolean equals(Item it) {
		ArrayList<Node> arr = it.item;
		for(Node n : arr){
			if(!this.hasNode(n))
				return false;
		}
		return true;
	}
	
	//�жϸ������Ƿ��д˱���
	public boolean hasNode(Node n) {
		for(Node nn:item){
			if(nn.equals(n))
				return true;
		}
		return false;
	}
	
	public boolean hasVarialbe(Node n) {
		for(Node nn:item){
			if(nn.v.equals(n.v))
				return true;
		}
		return false;
	}
	
	public Node hasVarialbe(String v) {
		for(Node nn:item){
			if(nn.v.equals(v))
				return nn;
		}
		return null;
	}	
	
	//Ϊ���ʽ��ֵ��֧�ֶ��������ֵ����'&'�ֿ�
	//�ݲ�֧�ֶ������ֵ
	//����null˵���Ҳ���
	public Item simplify(String value) {
		Item tmp = getCopy();

		
		String[] strArray = {value};
		if (value.indexOf(" ") != -1){
			strArray = value.split(" ");
		}
		for (String tmpStr : strArray){
			String[] ss = tmpStr.split("=");
			String name = ss[0];
			int v = Integer.parseInt(ss[1]);
			Node n = null;
			//item �����������Ϊ�յ���
			if((n = tmp.hasVarialbe(name)) != null){
				tmp.coe *= (int)Math.pow(v, n.power);
				tmp.item.remove(n);
			}
			else tmp = null;
		}
		return tmp;
	}
	
	private Integer isNumber(String s) {
		Integer tmp = null;
		try{
			 tmp = Integer.parseInt(s);
		} catch(NumberFormatException e){
			return null;
		}
		return tmp;
	}

}

