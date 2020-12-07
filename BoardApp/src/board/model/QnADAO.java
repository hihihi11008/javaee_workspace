package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBManager;

public class QnADAO {
	DBManager dbManager = new DBManager();
	
	//insert : 원글등록 
	public int  insert(QnA qna) {
		Connection con =null;
		PreparedStatement pstmt = null;
		int result=0;
		con=dbManager.getConnection();
		
		String sql= "insert into qna(writer, title, content) values(?,?,?)";
		try {
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, qna.getWriter());
			pstmt.setString(2, qna.getTitle());
			pstmt.setString(3, qna.getContent());
			
			result = pstmt.executeUpdate();//실행
			
			//team을 방금 들어간 레코드에 의해 발생한 pk 값으로 업데이트!! 
			sql="update qna set team=(select last_insert_id()) where qna_id=(select last_insert_id())";
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt);
		}
		return result;
	}
	
	/*
	1. 기존에 내가본글보다 rank가 큰 글의 rank는 모두 1씩 증가되시오 (공간확보)
	update qna rank=rank+1 where rank > 내본글rank and team=내본글team

	2.빈공간을 내가차지(답변)
	insert qna(~team, rank, depth) values(내본글team, 내본글rank+1, 내본글depth+1)
	*/
	public int reply() {
		int result=0;
		
		return result;
	}
	
	//selectAll
	public List<QnA> selectAll() {
		Connection con = null; 
		PreparedStatement pstmt =null; 
		ResultSet rs = null;
		ArrayList<QnA> list = new ArrayList<QnA>();
		
		String sql= "select * from qna order by team desc, rank asc";
		
		con = dbManager.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				QnA qna = new QnA();//레코드만큼 vo 생성해야함 
				qna.setQna_id(rs.getInt("qna_id"));
				qna.setWriter(rs.getString("writer"));
				qna.setTitle(rs.getString("title"));
				qna.setContent(rs.getString("content"));
				qna.setRegdate(rs.getString("regdate"));
				qna.setHit(rs.getInt("hit"));
				qna.setTeam(rs.getInt("team"));
				qna.setRank(rs.getInt("rank"));
				qna.setDepth(rs.getInt("depth"));
				
				list.add(qna);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt, rs);
		}
		return list;
	}
	
	//select
	public QnA select(int qna_id) {
		Connection con = null; 
		PreparedStatement pstmt =null; 
		ResultSet rs = null;
		QnA qna = null;
		
		String sql= "select * from qna where qna_id=?";
		
		con = dbManager.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qna_id);
			rs = pstmt.executeQuery();
			if(rs.next()){//레코드가 있다면 
				qna = new QnA();//레코드만큼 vo 생성해야함 
				qna.setQna_id(rs.getInt("qna_id"));
				qna.setWriter(rs.getString("writer"));
				qna.setTitle(rs.getString("title"));
				qna.setContent(rs.getString("content"));
				qna.setRegdate(rs.getString("regdate"));
				qna.setHit(rs.getInt("hit"));
				qna.setTeam(rs.getInt("team"));
				qna.setRank(rs.getInt("rank"));
				qna.setDepth(rs.getInt("depth"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt, rs);
		}
		return qna;
	}
	
	//update
	public int update() {
		int result=0;
		
		return result;
	}
	
	//delete
	public int delete() {
		int result=0;
		
		return result;
	}	
}
