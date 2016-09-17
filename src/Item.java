import java.util.ArrayList;

public class Item {
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
						Node_j.inc();
						appear_flag=true;
					}
				}
				//���δ���ֵı���
				if (appear_flag==false){
					this.item.add(new Node(tmp_i));
				}
			}
		}
	}
	
	public Item() {}

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
		String[] ss = value.split("=");
		String name = ss[0];
		int v = Integer.parseInt(ss[1]);
		
		Node n = null;
		//item �����������Ϊ�յ���
		if((n = tmp.hasVarialbe(name)) != null){
			tmp.coe *= (int)Math.pow(v, n.power);
			tmp.item.remove(n);
		}
		else tmp = null;
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
