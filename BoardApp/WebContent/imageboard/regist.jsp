<%@page import="board.model.ImageBoard"%>
<%@page import="board.model.ImageBoardDAO"%>
<%@page import="common.FileManager"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/inc/lib.jsp" %>
<%!
	String saveDir="C:/workspace/javaee_workspace/BoardApp/WebContent/data";
	int maxSize=3*1024*1024; //3M byte
	ImageBoardDAO dao = new ImageBoardDAO();
%>
<%
	//실습했던 예제보다 기능이 더 추가됨, db에 넣어야함 DAO 이용 
	//업로드컴포넌트에 대한 설정을 하기 위해 FileItemFactory 객체를 이용해야 한다 
	DiskFileItemFactory itemFactory = new DiskFileItemFactory();
	itemFactory.setRepository(new File(saveDir));
	itemFactory.setSizeThreshold(maxSize);
	
	ServletFileUpload upload = new ServletFileUpload(itemFactory);
	//업로드된 정보 분석! 각각의 컴포넌트들을 FileItem 단위로 쪼갠다
	request.setCharacterEncoding("utf-8");//다국어 인코딩
	List<FileItem> items = upload.parseRequest(request);
	
	ImageBoard board = new ImageBoard();//Empty상태의 VO 생성 
	for(FileItem item : items){
		if(item.isFormField()){//textfield라면....db에 넣어야지
			//vo에 텍스트 필드들의 값을 담자 
			if(item.getFieldName().equals("author")){//필드명이 author라면 
				board.setAuthor(item.getString());				
			}else if(item.getFieldName().equals("title")){	//필드명이 title 라면
				board.setTitle(item.getString());
			}else if(item.getFieldName().equals("content")){//필드명이 content 라면 
				board.setContent(item.getString());
			}
		}else{//textfield가 아니라면 업로드처리
			String newName = System.currentTimeMillis()+"."+FileManager.getExtend(item.getName());
			String desfFile= saveDir+"/"+newName;
			File file = new File(desfFile);
			item.write(file);//물리적 저장시점
			
			out.print("업로드 완료");
			//vo에 파일명 값을 담자 
			board.setFilename(newName);
		}
	}
	//반복문을 지나친 이 시점에는 VO에 데이터가 이미 채워진 상태일것이다.
	int result = dao.insert(board);//이시점에 채워진 VO를 원함 
	if(result==0){
		out.print(getMsgBack("등록실패"));
	}else{
		out.print(getMsgURL("등록성공","/imageboard/list.jsp"));
	}
%>