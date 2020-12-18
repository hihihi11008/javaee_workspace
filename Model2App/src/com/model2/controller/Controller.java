/*
 * 모든 하위 컨트롤러가 반드시 구현해야할 메서드를 정의한다 
 * */
package com.model2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
	//알맞는 비즈니스 객체에 일 시키기 
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	//어떤뷰페이지를 보여줘야할지 결정 
	//만일 하위 컨트롤러가 이 업무를 처리하지 않으면, DispatcherServlet에서 if 문으로 처리해야한ㄷ ㅏ
	public String getResultView();
	
	//요청을 끊어야할지, 유지해야할지를 결정하는 메서드, 포워딩할지말지  
	public boolean isForward();
}
