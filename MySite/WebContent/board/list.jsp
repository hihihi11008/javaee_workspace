<%@page import="board.model.Board"%>
<%@page import="java.util.List"%>
<%@page import="board.model.BoardDAO"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%
	BoardDAO dao = new BoardDAO();
	List<Board> list = dao.selectAll();
	
	int totalRecord =list.size(); //총 레코드 수 
	int pageSize = 10; //페이지당 보여질 레코드수
	int totalPage = (int)Math.ceil((float)totalRecord/pageSize); // 총페이지수 
	int blockSize = 10; //블럭당 보여질 페이지수
	int currentPage = 1; //현재페이지(default값주기 첫페이지! )
	if(request.getParameter("currentPage")!=null){//파라미터로 페이지가 전달된다면 
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	int firstPage = currentPage-(currentPage-1)%blockSize;
	int lastPage = firstPage+blockSize-1;
	int curPos=(currentPage-1)*pageSize;
	int num = totalRecord-curPos;//페이지당 시작번호 

%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
table {
	border-collapse: collapse;
	border-spacing: 0;
	width: 100%;
	border: 1px solid #ddd;
}
th, td {
	text-align: left;
	padding: 16px;
}
tr:nth-child(even) {
	background-color: #f2f2f2;
}
.pageNum{
	font-size: 20pt;
	color : red;
	font-weight : bold;
}
</style>
</head>
<body>

	<table>
		<tr>
			<th>No</th>
			<th>이미지</th>
			<th>제목</th>
			<th>작성자</th>
			<th>등록일</th>
			<th>조회수</th>
		</tr>
		<%for(int i=1; i<=pageSize; i++){ %>
		<%if(num<1)break; %>
		<%Board board =list.get(curPos++); %>
		<tr>
			<th><%=num-- %></th>
			<th>이미지</th>
			<th>제목</th>
			<th>작성자</th>
			<th>등록일</th>
			<th>조회수</th>
		</tr>
		<%} %>
		
		<tr>
			<td colspan="3" style="text-align:center">
				<a href="list.jsp?currentPage=<%=firstPage-1%>">◀</a>
				<%for(int i =firstPage;i<=lastPage;i++){ %>
				<%if(i>totalPage)break; %>
				<a <%if(currentPage==i){ %>class="pageNum" <%} %>href="list.jsp?currentPage=<%=i%>">[<%=i %>]</a>
				<%} %>
				<a href="list.jsp?currentPage=<%=lastPage+1%>">▶</a>
			</td>
		</tr>
		
		
		<tr>
			<td colspan="3">
				<button onClick="location.href='regist_form.jsp'">글등록</button>
			</td>
		</tr>
	</table>
</body>
</html>