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
	
	//insert : ���۵�� 
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
			
			result = pstmt.executeUpdate();//����
			
			//team�� ��� �� ���ڵ忡 ���� �߻��� pk ������ ������Ʈ!! 
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
	1. ������ �������ۺ��� rank�� ū ���� rank�� ��� 1�� �����ǽÿ� (����Ȯ��)
	update qna rank=rank+1 where rank > ������rank and team=������team

	2.������� ��������(�亯)
	insert qna(~team, rank, depth) values(������team, ������rank+1, ������depth+1)
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
				QnA qna = new QnA();//���ڵ常ŭ vo �����ؾ��� 
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
			if(rs.next()){//���ڵ尡 �ִٸ� 
				qna = new QnA();//���ڵ常ŭ vo �����ؾ��� 
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