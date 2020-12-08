<%@page import="board.model.QnA"%>
<%@page import="board.model.QnADAO"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/inc/lib.jsp"%>
<%
	String rank = request.getParameter("rank");
	String qna_id = request.getParameter("qna_id");
	
	QnA qna = new QnA();
	qna.setQna_id(Integer.parseInt(qna_id));
	qna.setRank(Integer.parseInt(rank));
	
	QnADAO qnaDAO = new QnADAO();
	
	int result = qnaDAO.delete(qna);
	
	if(result==0){
		out.print(getMsgBack("삭제실패"));
	}else{
		out.print(getMsgURL("삭제성공","/qna/list.jsp"));		
	}
%>