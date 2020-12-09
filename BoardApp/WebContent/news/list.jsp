<%@page import="board.model.News"%>
<%@page import="java.util.List"%>
<%@page import="board.model.NewsDAO"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%
	NewsDAO dao = new NewsDAO();
	List<News> list = dao.selectAll();

	int totalRecord=list.size(); //총 레코드 수 
	int pageSize=10;//페이지당 보여질 레코드수(개발자가 임의로 지정가능)
	int totalPage=(int)Math.ceil((float)totalRecord/pageSize);//총페이지수
	int blockSize =10;//블럭당 보여질 페이지수 (개발자가 임의로 지정가능)
	int currentPage=1;//현재 페이지(default 값은 첫페이지다 따라서 1)
	if(request.getParameter("currentPage")!=null){//파라미터로 페이지가 전달된다면!
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	int firstPage = currentPage-(currentPage-1)%blockSize;
	int lastPage = firstPage+blockSize-1;
	int curPos=(currentPage-1)*pageSize;
	int num = totalRecord -curPos;//페이지당 시작번호 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<style>
boday{font-size: 9pt;}
table{
	width:100%;
	border:1px solid #cccccc;
}
td{
	border:1px solid #cccccc;
}
a{text-decoration: none}
.pageNum{
	font-size: 20pt;
	color : blue;
	font-weight: bold;
}
.inactive{
	color:#cccccc;
}
</style>
<script>
function showColor(obj){
	obj.style.background="#3C3B6E";
}
function hideColor(obj){
	obj.style.background="";
}
</script>
</head>
<body>
	<table>
		<tr >
			<td width="5%">No</td>
			<td>제목</td>
			<td>작성자</td>
			<td>등록일</td>
			<td width="5%">조회수</td>
		</tr>
		<%for(int i =1; i<=pageSize;i++){ %>
		<%if(num<1)break; %>
		<%News news=list.get(curPos++); %>
		<tr onmouseover="showColor(this)" onMouseout="hideColor(this)">
			<td><%=num--%></td>
			<td>
			<%if(news.getWriter().length()<1){//작성자의 문자열 길이가 0이라면 %>
				<span class="inactive"><%=news.getTitle() %></span>
			<%}else{ %>
				<a href="detail.jsp?news_id=<%=news.getNews_id()%>"><%=news.getTitle() %>
				<%if(news.getCnt()>0){ %>
					[<%=news.getCnt() %>]
				<%} %>
			<%}%>
			</a>
			</td>
			<td><%=news.getWriter() %></td>
			<td><%=news.getRegdate().substring(0,10) %></td>
			<td><%=news.getHit() %></td>
		</tr>
		<%} %>
		<tr>
			<td colspan="5" align="center">
				<a href="list.jsp?currentPage=<%=firstPage-1%>">◀</a>
				<%for(int i=firstPage;i<=lastPage; i++){ %>
				<%if(i>totalPage)break;//총페이지수 넘어서면  %>
				<a <%if(currentPage==i){ %>class="pageNum" <%} %> href="list.jsp?currentPage=<%=i %>">[<%=i %>]</a>
				<%} %>
				<a href="list.jsp">▶</a>
			</td>
		</tr>
		<tr>
			<td colspan="5">
				<button onclick="location.href='regist_form.jsp';">글쓰기</button>
			</td>
		</tr>
	</table>
</body>
</html>