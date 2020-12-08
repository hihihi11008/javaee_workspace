<%@page import="board.model.QnA"%>
<%@page import="board.model.QnADAO"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%
	//상세보기 페이지의 히든을 통해 전송된 파라미터 중 
	//team, rank, depth를 다시 현재페이지의 히든에 보관해두자 
	String team=request.getParameter("team");//내본글 team
	String rank=request.getParameter("rank");//내본글 rank
	String depth=request.getParameter("depth");//내본글 depth
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {font-family: Arial, Helvetica, sans-serif;}
* {box-sizing: border-box;}

input[type=text], select, textarea {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  margin-top: 6px;
  margin-bottom: 16px;
  resize: vertical;
}

input[type=button] {
  background-color: #AD96B5;
  color: white;
  padding: 12px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

input[type=button]:hover {
  background-color: #D1B4B4;
}

.container {
  border-radius: 5px;
  background-color: #BEA9C7;
  padding: 20px;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.ckeditor.com/4.15.1/standard/ckeditor.js"></script>
<script>

$(function(){
	CKEDITOR.replace("subject"); //textarea에 부여한 id를 넣음
	$($("input[type='button']")[0]).click(function(){//답글 등록 
		$("form").attr({
			method:"get",
			action:"/qna/reply.jsp"
		});
		$("form").submit() //전송행위
	});
	$($("input[type='button']")[1]).click(function(){//답글 등록 
		history.back();
	});
});
</script>
</head>
<body>

<div class="container">
  <form>
    <input type="hidden" name="team" value="<%=team%>">
    <input type="hidden" name="rank" value="<%=rank%>">
    <input type="hidden" name="depth" value="<%=depth%>">
    
    <label for="fname">First Name</label>
    <input type="text" id="fname" name="writer" placeholder="Your name..">

    <label for="lname">Title</label>
    <input type="text" id="lname" name="title" placeholder="Your title..">

    <label for="subject">Content</label>
    <textarea id="subject" name="content" placeholder="Write something.." style="height:200px"></textarea>

    <input type="button" value="답글등록">
    <input type="button" value="이전으로">
  </form>
</div>
<div style="text-align:center">
	Copyright All reserved java board
</div>
</body>
</html>
