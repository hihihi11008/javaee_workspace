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
		//�Ķ���� �ޱ� 

		String movie = request.getParameter("movie");
		
		//3�ܰ� : �˸´� ���� ��ü���� �� ��Ų�� .
		MovieCritic critic = new MovieCritic();
		String msg = critic.getCritic(movie);
		
		//4�ܰ� : Ŭ���̾�Ʈ���� ������ ����� �����س��´� 
		HttpSession session = request.getSession();
		session.setAttribute("msg", msg);
		
	}

	@Override
	public String getViewPage() {
		return "/movie/movie_result.jsp";
	}
}
