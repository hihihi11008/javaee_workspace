package movie.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Controller;

import movie.model.MovieCritic;

public class MovieController implements Controller{
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//파라미터 받기 

		String movie = request.getParameter("movie");
		
		//3단계 : 알맞는 로직 객체에게 일 시킨다 .
		MovieCritic critic = new MovieCritic();
		String msg = critic.getCritic(movie);
		
		//4단계 : 클라이언트에게 전달할 결과를 저장해놓는다 
		HttpSession session = request.getSession();
		session.setAttribute("msg", msg);
		
	}

	@Override
	public String getViewPage() {
		return "/movie/movie_result.jsp";
	}
}
