import java.util.ArrayList;
import java.util.Scanner;

public class Polynome {
	private String raw;
	private static final String EXIT_FLAG = "!exit";
	private static final String SIMPLIFY = "simplify";
	private static final String DIFF = "d/d";
	private Scanner scan;
	private ArrayList<Item> items = new ArrayList<Item>();
	
	/*
	 * op = 0 	: null
	 * op = -1 	: exit
	 * op = 1 	: simplify
	 * op = 2	: diff 
	 * op = default : reinput
	 */
	private int op = 0;
	private String opStr = "";
	
	public static void main(String[] args) {
		Polynome po = new Polynome();
		boolean flag = true;
		while(flag){
			po.getInput();
			po.operate();
		}
	}
	
	
	private void print() {
		String resStr = "";
		boolean firstFlag = true;//Ҳ�����Ż�
		for (int i=0; i<this.items.size();i++){
			resStr+=this.items.get(i).toString(firstFlag);
			firstFlag = false;
		}
		System.out.println(resStr);
	}

	//���ݲ�����ִ����Ӧ����
	private void operate() {	
		switch(op) {
		case 0:	//���³ɹ�
//			System.out.println("ok");
			print();
			break;
		case -1: //�˳�
			System.out.println("exit");
			System.exit(0);
			break;
		case 1:	//������ʽ
			simplify();
			break;
		case 2:	//��
			derivative();
			break;
		default:
			System.err.println("����������������룺");
			break;
		}
	}

	//�����ַ������õ�������
	//�ж���������Ǳ��ʽ��������
	private void getInput() {
		String tmp = scan.nextLine();
		char[] chars = tmp.toCharArray();
		if(tmp.equals(EXIT_FLAG)){
			op = -1;
			return;
		}
		for(char ch : chars){
			if(ch == '!') {
				breakOpStr(tmp);
				return;
			}
		}
		expression(tmp);
	}
	
	//����ո�
	//��+���
	//��ÿ����ֺ���ַ���������item
	//op=0
	private void expression(String tmp) {
		items.clear();
		String res = "";
		char[] chars = tmp.toCharArray();
		for(char ch : chars){
			if(ch != ' ' && ch != '\t')
				res += ch;
		}
		chars = res.toCharArray();
		boolean flag = chars[0] == '-'  ?  false : true;
		res = "";
		for(int i =  chars[0] == '+' || chars[0] == '-' ? 1 : 0
				; i < chars.length; i++){
			if(chars[i] == '+'){
				items.add(new Item(res,flag));
				flag = true;
				res = "";
			}
			else if(chars[i] == '-'){
				items.add(new Item(res,flag));
				flag = false;
				res = "";
			}
			else {
				res += chars[i];
			}
		}
		items.add(new Item(res,flag));
		op = 0;
	}
	
	//����ո�
	//ʶ������
	//�õ�������Ͳ���str
	private void breakOpStr(String tmp) {
		char[] chars = tmp.toCharArray();
		boolean checkFlag = true;
		for (char ch : chars){
			if(ch == '!'){
				checkFlag = false;
			}
			else if((ch != ' ' || ch != '\t') && checkFlag){
				op = 3;
				return;
			}
		}
		int index;
		if ((index = tmp.indexOf(SIMPLIFY)) != -1) {
			index += SIMPLIFY.length();
			op = 1;//������ʽ
		}
		else if((index = tmp.indexOf(DIFF)) != -1) {
			index += DIFF.length();
			op = 2;//���ʽ��
		}
		else {
			op = 3;//�������
			return;
		}
		String res = "";
		for (int i = index; i < chars.length; i++) {
			if(chars[i] != ' ' && chars[i] != '\t'){
				res += chars[i];
			}
		}
		opStr = res;
	}

	public Polynome() {
		scan = new Scanner(System.in);
	}
	
	public void simplify() {
		String resStr = "";
		boolean firstFlag = true;//Ҳ�����Ż�
		for (int i=0; i<this.items.size();i++){
			resStr+=this.items.get(i).simplify(this.opStr).toString(firstFlag);
			
			firstFlag = false;
		}
		System.out.println(resStr);
	}
	
	public void derivative() {
		String resStr = "";
		boolean firstFlag = true;//Ҳ�����Ż�
		for (int i=0; i<this.items.size();i++){
			resStr+=this.items.get(i).diff(this.opStr).toString(firstFlag);
			firstFlag = false;
		}
		System.out.println(resStr);
	}
}
