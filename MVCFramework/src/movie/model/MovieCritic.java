package movie.model;

public class MovieCritic {
	public String getCritic(String movie) {
		String msg = null;
		if(movie.equals("미션임파서블5")) {
			msg="미션임파서블이다";
		}else if(movie.equals("스타워즈")) {
			msg="스타워즈이다";
		}else if(movie.equals("존윅3")) {
			msg="존윅이다.";
		}else if(movie.equals("분노의질주")) {
			msg="분노의질주이다";
		}
		return msg;
	}
}
