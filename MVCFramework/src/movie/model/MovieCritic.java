package movie.model;

public class MovieCritic {
	public String getCritic(String movie) {
		String msg = null;
		if(movie.equals("�̼����ļ���5")) {
			msg="�̼����ļ����̴�";
		}else if(movie.equals("��Ÿ����")) {
			msg="��Ÿ�����̴�";
		}else if(movie.equals("����3")) {
			msg="�����̴�.";
		}else if(movie.equals("�г�������")) {
			msg="�г��������̴�";
		}
		return msg;
	}
}
