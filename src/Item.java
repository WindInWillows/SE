import java.util.ArrayList;

public class Item {
	public int coe=1;
	public ArrayList<Node> item;

	/*
	 *������ʽ��������������������ʽ������ݽṹ��
	 *	���ʽ��ı���������һ��ArrayList���
	 *	���ʽ��ϵ����������
	 */
	public Item(String exp, boolean isPositive) {
/*		if( !isPositive){
			this.coe *= -1;
		}
		String[] tmp_list=exp.split("*");//11 x y x 8
		for(int i=0;i<tmp_list.length;i++){
			String tmp_i=tmp_list[i];
			num=isNumber(tmp_i);
			if( num!= -1 ){
				this.coe *= num;
			}
			else{
				for(int j;j<i;j++){
					String tmp_j=tmp_list[j];
					if(tmp_j!=tmp_j){// tmp_i δ������֮ǰ������ַ�����
						
					}
				}
				//this.item.add(Node(tmp_i));
			}
		}
*/
	}
	
	//�󵼺���
	public Item diff() {
		Item tmp = null;
		
		return tmp;
	}
	
	//�����ǰ��
	public String toString() {
		String str = "";
		str += coe;
		for(Node n : item) {
			str += "*"+n.v+"^"+n.power;
		}
		return str;
	}
	
	//�ж������Ƿ�ÿ��������ָ�������
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
			if(nn.v == n.v)
				return true;
		}
		return false;
	}
	
	//Ϊ���ʽ��ֵ��֧�ֶ��������ֵ����'&'�ֿ�
	public Item simplify(String value) {
		Item tmp = getCopy(this);
		String[] vals = value.split("&");
		for (String tmp_str : vals){
			String[] tmp_arr = tmp_str.split("="); 
			String var_str = tmp_arr[0];
			String num_str = tmp_arr[1];
			
		}
		return tmp;
	}

	private Item getCopy(Item item2) {
		// TODO Auto-generated method stub
		return null;
	}
}
