package blood.model;

public class BloodAdvisor {
	public String getAdvice(String blood) {
		String msg = null;
		if(blood.equals("A")){
			msg="�Ĳ��ϰ� �����ϴ� ���ϰ� �����ϴ�, �׷��� ���� �ҽ��ϴ�";
		}else if(blood.equals("B")){
			msg="�����̼���";
		}else if(blood.equals("O")){
			msg="�米���� ����";
		}else if(blood.equals("AB")){
			msg="������ �����ֹٲ��.";
		}
		return msg;
	}
}
