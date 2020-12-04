/*
 	DAO란?
 	-Data Access Object를 의미하는 어플리케이션의 설계분야 용어 
 	-Data Access란 데이터베이스와의 Create(=insert) Read(=select)UD작업을 전담한다는 의미 
 */

package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBManager;

public class NoticeDAO {
	DBManager dbManager = new DBManager();
	
	//재사용성 고려하지 않은 swing 만의 로직 작성 
	//insert 는 글 한건 ~ 하나의 VO (테이블명을 그대로 ! )`
	public int regist(Notice notice) {
		Connection con=null;
		PreparedStatement pstmt=null;
		int result=0; // 글 등록 후 그 결과값 보관 
		
		con = dbManager.getConnection();
		
		String sql = "insert into notice(author, title, content) values(?,?,?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, notice.getAuthor());//작성자
			pstmt.setString(2, notice.getTitle());//제목
			pstmt.setString(3, notice.getContent());//내용
			
			result = pstmt.executeUpdate();

			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con,pstmt);
		}
		return result;
	}
	
	//모든레코드 가져오기 
	public ArrayList<Notice> selectAll() {
		Connection con=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		ArrayList<Notice> list = new ArrayList<Notice>(); //rs를 대체할 녀석 
		
		String sql = "select * from notice order by notice_id desc";
		
		con = dbManager.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			//rs는 레코드가 복수개이므로, 즉 여러개 이므로 VO또한 여러개가 필요하고 , 
			//이 VO를 한꺼번에 모아서 반환해야하므로, 집합형 자료형이 필요하다! 
			//객체를 모아놓은 집합을 지원하는 프레임웍은 Collection Framework이므로, 
			//이 중 하나의 api를 이용해본다 
			while(rs.next()) {
				Notice notice = new Notice();
				
				notice = new Notice(); // 텅빈 empty상태의 VO생성
				notice.setNotice_id(rs.getInt("notice_id"));
				notice.setAuthor(rs.getString("author"));
				notice.setTitle(rs.getString("title"));
				notice.setContent(rs.getString("content"));
				notice.setRegdate(rs.getString("regdate"));
				notice.setHit(rs.getInt("hit"));
				//notice 변수가 사라지기 전에 얼른 list에 담자 
				list.add(notice); // 위에 미리 new해놓음 담기위해서!! 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt, rs);
		}
		return list; //rs가 아닌 ArrayList를 반환하자 ! 
	}
	
	//게시물 1건 가져오기 (상세보기)
	public Notice select(int notice_id) {
		Connection con=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		Notice notice =null;//rs대신 데이터 1건을 담을 객체 
		
		String sql="select * from notice where notice_id=?";
				
		con=dbManager.getConnection();//접속 후 그 결과 가져오기 
		try {
			pstmt = con.prepareStatement(sql);//쿼리준비
			pstmt.setInt(1,notice_id);//바인드변수값지정 
			
			rs = pstmt.executeQuery();//쿼리수행 
			
			//지금 탄생한 rs는 곧 죽는다 따라서 rs를 대체할 객체가 필요
			//rs는 레코드 한건을 담는 객체이므로, 레코드 1건을 담아 전달용으로 사용되는 VO를 이용하자!!!
			if (rs.next()) {//레코드가 존재하는 것임 ! 이때 VO를 올리자!!!
				notice = new Notice(); // 텅빈 empty상태의 VO생성
				notice.setNotice_id(rs.getInt("notice_id"));
				notice.setAuthor(rs.getString("author"));
				notice.setTitle(rs.getString("title"));
				notice.setContent(rs.getString("content"));
				notice.setRegdate(rs.getString("regdate"));
				notice.setHit(rs.getInt("hit"));
			}				
			
			//조회수 증가 
			sql="update notice set hit=hit+1 where notice_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, notice_id);
			pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(con, pstmt, rs);
		}
		return notice;
	}
	
	//수정하기 
	public int update(Notice notice) {
		Connection con=null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "update notice set author=?, title=?, content=? where notice_id=?";
		
		con = dbManager.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, notice.getAuthor());
			pstmt.setString(2, notice.getTitle());
			pstmt.setString(3, notice.getContent());
			pstmt.setInt(4, notice.getNotice_id());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt);
		}
		return result;
	}
	
	//삭제하기 
	public int delete(int notice_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql ="delete from notice where notice_id=?";
		
		con = dbManager.getConnection();
		try {
			pstmt= con.prepareStatement(sql);
			pstmt.setInt(1, notice_id);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt);
		}
		return result;
	}
}
