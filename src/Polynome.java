import java.util.ArrayList;
import java.util.Scanner;

public class Polynome {
	
	public Polynome() {
		scan = new Scanner(System.in);
	}

	// 一个输出流对象
	private Scanner scan;
	// 通过使用一个类型为Item的列表保存当前表达式，是主要的
	private ArrayList<Item> expressionArray = new ArrayList<Item>();
	// 保存当前多项式的字符串形式
	private String tmpStr = "";
	// opCode为操作码，opStr为操作字。这两个变量在getInput被修改，作用于impOperation
	private int opCode = 0;
	private String opStr = "";
	//
	private static final String EXIT_FLAG = "!exit";
	private static final String SIMPLIFY = "simplify";
	private static final String DIFF = "d/d";
	
	
	public static void main(String[] args) {
		Polynome po = new Polynome();
		// 打印提示信息
		po.prompt();
		while(true){
			//读取一个字符串，判断是表达式还是命令，并做相应的处理，以及得到一个操作码
			po.getInput();
			//根据上一步中获得的操作码执行相应的操作
			po.impOperation();
		}
	}
	
	private void impOperation() {
		//根据操作码执行相应操作	
		switch(opCode) {
			case -1: //退出
				System.out.println("exit");
				System.exit(0);
				break;
			case 0:	//更新成功
//				System.out.println("ok");
				//print();
				break;
			case 1:	//更新成功
//				System.out.println("ok");
				print();
				break;
			case 2:	//化简表达式
				simplify();
				break;
			case 3:	//求导
				derivative();
				break;
			case 4:	//求导
				System.out.println("Error: "+opStr+".");
				break;
			default:
				System.err.println("输入错误！请重新输入：");
				break;
		}
	}
	
	private void print() {
		String resStr = "";
		boolean firstFlag = true;//也许能优化
		for (int i=0; i<this.expressionArray.size();i++){
			resStr+=this.expressionArray.get(i).toString(firstFlag);
			firstFlag = false;
		}
		System.out.println(resStr);
	}
	public void prompt(){
		// 系统开始运行后打印提示信息
		// 可在此添加包括版本，帮助等的提示信息
		System.out.println("<Welcome to Polynomials System 1.0>");
	}
	
	public void getInput(){
		
		// 读取一个字符串
		String strInput = scan.nextLine();
		
		// 此字符串中不包含“！”，则为表达式；否则，为命令
		// 并进行相应的处理
		if (strInput.indexOf("!")==-1){
			expression(strInput);
		}
		else{
			command(strInput);
		}
  	}
	
	public void expression(String strInput){
		
		// 对输入Str的合法性进行判定，同时去除表达式中的空格和制表符
		if (validateExpressionAndStrip(strInput) == false){
			opCode = 4;
			opStr  = "Invalid character inside";
			return;
		}
		
		// 根据+和-，对表达式对应的字符串进行拆分
		// 并以拆分后的各个字符串作为参数，分别建立Item对象，加入到expressionArray中
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
			// 包含除 数字、字母、空格、tab、+、-、*、^ 以外 的字符
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
		// 检查输入的命令的合法性
		if (validateCommandAndStrip(strInput) == false)
		{
			opCode = 4;
			opStr = "Wrong command!";
			return;
		}
		
		// 判断命令类型
		char[] chars = strInput.toCharArray();
		int index;
		if ((index = strInput.indexOf(SIMPLIFY)) != -1) {
			index += SIMPLIFY.length() + 1;
			opCode = 2;//化简表达式
			opStr = strInput.substring(index);
			return;
		}
		else if((index = strInput.indexOf(DIFF)) != -1) {
			index += DIFF.length() + 1;
			opCode = 3;//表达式求导
			opStr = strInput.substring(index);
		}
		else {
			opCode = 4;//命令错误
			opStr = "unrecognized command";
			return;
		}
	}
	
	public boolean validateCommandAndStrip(String str){
		// 检查输入的命令是否在！前有除空格和制表符之外的字符
		// 如果有，则说明输入的命令有误
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
		// !!!可添加功能 去除空格
		tmpStr = str;
		return true;
	}

	public void simplify() {
		String resStr = "";
		boolean firstFlag = true;//也许能优化
		
//		tmpP= this;
		for (int i=0; i<this.expressionArray.size();i++){
			resStr += this.expressionArray.get(i).simplify(opStr).toString(firstFlag);
			firstFlag = false;
		}
		System.out.println(resStr);
	}

	public void derivative() {
		String resStr = "";
		boolean firstFlag = true;//也许能优化
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
	 *输入表达式项和项的正负，建立起表达式项的数据结构：
	 *	表达式项的变量部分由一个ArrayList组成
	 *	表达式的系数包含正负
	 */
	//'-','11*x*y*x*8' => -88,Node(x,2),Node(y,1)
	public Item(String exp, boolean isPositive) {
		////处理系数的正负问题
		if( !isPositive){
			this.coe *= -1;
		}
		////拆分一个多项式中一项
		//将'11*x*y*x*8'拆分为   列表[11 x y x 8]
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
			
			// num=null 表示tmp_i不是数字，否则返回这个数字（int），将这个数字直接与系数相乘即可
			if( num!= null ){
				this.coe *= num;
			}
			// tmp_i 为变量，在下面的代码中判断变量tmp_i是否出现过
			else{
				//判断字符tmp_i是否已经出现的标志
				boolean appear_flag=false;// ！也许能被优化
				//遍历tmp_list中已经处理的部分，判断变量tmp_i是否出现过
				for(int j=0;j<this.item.size();j++){
					Node Node_j=this.item.get(j);
					// 变量tmp_i出现在之前处理的字符串中，对原变量对应节点的指数+1
					if(Node_j.v.equals(tmp_i)){
						Node_j.inc(power);
						appear_flag=true;
					}
				}
				//添加未出现的变量
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
		// 也许能优化
		Item res = new Item();
		res.coe = this.coe;
		for(int i=0;i<this.item.size();i++){
			res.item.add(this.item.get(i).getCopy());
		}
		return res;
	}
	
	//输出当前项
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
	
	//判断两项是否能合并
	public boolean equals(Item it) {
		ArrayList<Node> arr = it.item;
		for(Node n : arr){
			if(!this.hasNode(n))
				return false;
		}
		return true;
	}
	
	//判断该项中是否有此变量
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
	
	//为表达式赋值，支持多个变量赋值，用'&'分开
	//暂不支持多变量赋值
	//返回null说明找不到
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
			//item 里面可能有名为空的项
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

