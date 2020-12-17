package blood.model;

public class BloodAdvisor {
	public String getAdvice(String blood) {
		String msg = null;
		if(blood.equals("A")){
			msg="꼼꼼하고 세심하다 착하고 솔직하다, 그러나 때론 소심하다";
		}else if(blood.equals("B")){
			msg="고집이세다";
		}else if(blood.equals("O")){
			msg="사교성이 좋다";
		}else if(blood.equals("AB")){
			msg="결정ㅇ ㅣ자주바뀐다.";
		}
		return msg;
	}
}
