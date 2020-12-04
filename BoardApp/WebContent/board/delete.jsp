<%@page import="board.model.NoticeDAO"%>
<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.sql.*"%>
<%@include file="/inc/lib.jsp"%>
<%
	String notice_id = request.getParameter("notice_id"); 
	//웹상에서 돌아다니는 숫자는 모두 String이다. 먼저 String으로 받은 다음 int형으로 변환해줘야해
	NoticeDAO noticeDAO = new NoticeDAO();
	
	int result = noticeDAO.delete(Integer.parseInt(notice_id));

	if(result==0){
		out.print(getMsgBack("삭제실패"));
	}else{
		out.print(getMsgURL("삭제성공", "/board/list.jsp"));
	}
%>