/*
 * ������ ��� Ŭ���̾�Ʈ�� ��û�� �ް�, ������ �����ϴ� ��Ʈ�ѷ� ���� 
 * */
package com.model2.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DispatcherServlet extends HttpServlet{
	FileReader fis;//��Ʈ�ѷ� ���� ���������� �б����� ��Ʈ��
	JSONObject controllerMap;//��Ʈ�ѷ��� �������� ����ִ� �� 
	JSONObject viewMap;
	
	public void init(ServletConfig config) throws ServletException {
		String contextConfigLocation = config.getInitParameter("contextConfigLocation");
		String realPath = config.getServletContext().getRealPath(contextConfigLocation);
		//System.out.println(realPath);
		try {
			fis = new FileReader(realPath);
			JSONParser jsonParser = new JSONParser();
			//�Ľ�!! 
			JSONObject json = (JSONObject)jsonParser.parse(fis);
			
			//�����Ϳ� ����
			controllerMap= (JSONObject)json.get("controller");
			viewMap=(JSONObject)json.get("view");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	//doXXX�� �޼��带 �����Ͽ� ��û�� �޴´�
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		//2�ܰ� : ��û�� �м��Ѵ� 
		String uri = request.getRequestURI(); //Ŭ���̾�Ʈ�� ��û�� ����� uri ��ü�� ��û�� ���а����� ���� �� �ִ�.
		//System.out.println(uri+ "  uri��?? �̰� Ű ��");
		//if���� ����� ����ȭ�� �����͸� �������� (xml, json, properties)
		String controllerName = (String)controllerMap.get(uri);
		//System.out.println("���� ���� ��û�� ó���� ��Ʈ�ѷ� Ŭ������" + controllerName);
		
		//���� ���� ��Ʈ�ѷ��� �̸��� �˾�����, �ν��Ͻ��� ����� execute(), getResultViewȣ��()
		Class controllerClass=null;
		Controller controller = null;
		try {
			controllerClass = Class.forName(controllerName);//String, �� ���ڿ��� ������ Ŭ������ ���� ���� Ŭ������ȯ
			controller = (Controller) controllerClass.newInstance();//���� ��Ʈ�ѷ��� �ν��Ͻ� ����
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		controller.execute(request, response); //3�ܰ� ���� 

		//���� ��Ʈ�ѷ��κ��� �Ѱܹ��� �信 ���� ������ �̿��Ͽ� Ŭ���̾�Ʈ���� �˸´� �並 �����ش� 
		String resultKey = controller.getResultView();
		//���� ��Ʈ�ѷ��� 
		String viewPage= (String)viewMap.get(resultKey);
		//System.out.println("���� ��Ʈ�ѷ����� �Ѱܹ��� Ű���� "+ resultKey);
		//System.out.println("���� ��Ʈ�ѷ����� �Ѱܹ��� value���� "+ viewPage);
		
		//����� sendRedirect�� ó���ؾ��� ��찡 �ְ�, ���ۼ��� ����Ʈ, ���� �ٸ� �������� �������� �õ��ϰ� �Ҷ� 
		if(controller.isForward()) {
			//���δ� forwarding ó���ؾ��� ��찡 �ִ�.. �����͸� �����ϰ� ������ 
			RequestDispatcher dis = request.getRequestDispatcher(viewPage);
			dis.forward(request, response);//������� �������� �Ǵٸ� �ڿ����� ��û�� ���� 
		}else {
			response.sendRedirect(viewPage);
		}
	}
	
	public void destroy() {
		if (fis!=null) {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}