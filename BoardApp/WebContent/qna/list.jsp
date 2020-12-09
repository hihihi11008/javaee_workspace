<%@page import="java.util.List"%>
<%@page import="board.model.QnA"%>
<%@page import="board.model.QnADAO"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="board.model.NoticeDAO"%>
<%@page import="board.model.Notice"%>

<%
	QnADAO qnaDAO= new QnADAO();
	List<QnA> list= qnaDAO.selectAll();
	
	int totalRecord=list.size(); //총 레코드 수 , 실제 DB에 있는 데이터 수를 대입하면 된다 
	int pageSize=10; //한 페이지당 보여질 레코드 수 
	int totalPage=(int)Math.ceil((float)totalRecord/pageSize);//총페이지수
	int blockSize=10; //한 블럭당 보여질 페이지 수 
	int currentPage=1;//현재페이지
	
	//아래의 코드는 아무떄나 하는게 아니다 ! 즉 누군가가 파라미터를 넘겼을때만 즉 페이지 넘버를 클릭한 경우를 전제로 숫자화 시키는 코드이다 
	if(request.getParameter("currentPage")!=null){//즉 페이지를 넘겼다면 , 즉 넘버를 클릭했다면 
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	int firstPage=((currentPage-1)/blockSize)*10+1; //반복문의 시작값 // currentPage-(currentPage-1)%blockSize
	int lastPage=(firstPage+blockSize)-1; //반복문의 끝값
	int num=totalRecord-(currentPage-1)*pageSize;//페이지당 시작 번호 (힌트 : 여전히 위에있는 변수이용)
	int curPos=(currentPage-1)*pageSize;//페이지당 List에서의 시작 index 
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
img{
	box-sizing: border-box;
}
a{
	text-decoration: none;
}
.inactive{
	color:#cccccc;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
$(function(){
	$("button").on("click",function(){
		location.href="/qna/regist_form.jsp";
	});
});
</script>
</head>
<body>

<table>
  <tr>
    <th>No</th>
    <th>제목</th>
    <th>작성자</th>
	<th>등록일</th>
	<th>조회수</th>
  </tr>
  	<%for(int i=1;i<=pageSize;i++){ %>
	<%if(num<1)break; %>
	<%
		//break문을 만나지 않았다는 것은 레코드가 있다는 것이므로, break문 아래에서 데이터를 추출하자
		QnA qna=list.get(curPos++); //1page : 0~9,  2page:10~19...
	%>
  <tr>
    <td><%=num-- %></td>
    <td>
   		<%if(qna.getDepth()>0){//depth가 0보다 큰 경우 답변으로 판단하자 %>
    		<img src="/images/reply.png" style="margin-left:<%=20*qna.getDepth()%>px">
    	<%} %>
    	<%if(qna.getWriter().length()<1){ %>
    		<span class="inactive"><%=qna.getTitle() %></span>
    	<%}else{ %>
			<a href="/qna/detail.jsp?qna_id=<%=qna.getQna_id()%>"><%=qna.getTitle()%></a>
		<%} %>
	</td>
    <td><%=qna.getWriter()%></td>
	<td><%=qna.getRegdate().substring(0,10)%></td>
	<td><%=qna.getHit() %></td>
  </tr>
<%}%>
  <tr>
	<td colspan="5" style="text-align: center" > 
		<%if(firstPage-1>=1){ //페이지가 있따면 %>
		<a href="/qna/list2.jsp?currentPage=<%=firstPage-1%>">◀</a>
		<%}else{ %>
		<a href="javascript:alert('처음 페이지입니다');">◀</a>
		<%} %>
		<%for(int i=firstPage;i<=lastPage;i++){ %>
		<%if(i>totalPage)break;//페이지를 출력하는 i 가 총페이지수에 도달하면 반복문 빠져나와랐! %>
		<a href="/qna/list2.jsp?currentPage=<%=i %>" <%if(currentPage==i){ %>class="pageNum"<%} %> >[<%=i %>]</a>
		<%} %>
		<%if(lastPage+1 <totalPage){ //페이지가 있따면 %>
		<a href="/qna/list2.jsp?currentPage=<%=lastPage+1 %>">▶</a>
		<%}else{ %>
		<a href="javascript:alert('마지막 페이지입니다');">▶</a>
		<%} %>
	</td>
  </tr>
  <tr>
	<td colspan="5" > 
		<button>글등록</button>
	</td>
  </tr>
  <tr>
	<td colspan="5" style="text-align:center"> 
		<%@ include file="/inc/footer.jsp"%>
	</td>
  </tr>

</table>

</body>
</html>