/*
 * ImageBoard 테이블에 대한 CRUD만을 전담하는 DAO 정의 
 * */
package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBManager;

public class ImageBoardDAO {
	DBManager dbManager = new DBManager();//ImageBoardDAO의 인스턴스가 생성될떄 
																		//DBManager의 인스턴스도 같이 생성됨 
	
	//create(insert)
	public int  insert(ImageBoard board) {
		Connection con=null;
		PreparedStatement pstmt=null;
		int result=0;
		String sql ="insert into imageboard(author,title,content,filename) values(?,?,?,?)";
		
		con = dbManager.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getAuthor());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());
			pstmt.setString(4, board.getFilename());
			
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt);
		}
		return result;
	}
	
	//selectAll()
	public ArrayList<ImageBoard> selectAll() {
		Connection con = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		ArrayList<ImageBoard> list = new ArrayList<ImageBoard>();
		
		String sql="select * from imageboard";
		
		con = dbManager.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ImageBoard board = new ImageBoard();
				
				board = new ImageBoard();
				// board_id | author | title   | content   | regdate | hit  | filename
				board.setBoard_id(rs.getInt("board_id"));
				board.setAuthor(rs.getString("author"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setRegdate(rs.getString("regdate"));
				board.setHit(rs.getInt("hit"));
				board.setFilename(rs.getString("filename"));
				
				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt, rs);
		}
		return list;
	}
	
	//select
	public ImageBoard select(int board_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ImageBoard board=null;//return을 해야하므로 밖에다가 
		
		String sql ="select * from imageboar where board_id=?";
		
		con = dbManager.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_id);
			
			rs= pstmt.executeQuery();
			
			if(rs.next()) {
				board = new ImageBoard();
				
				board.setBoard_id(rs.getInt("board_id"));
				board.setAuthor(rs.getString("author"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setRegdate(rs.getString("regdate"));
				board.setHit(rs.getInt("hit"));
				board.setFilename(rs.getString("filename"));
			};
			
			//조회수 
			sql = "update imageboard set hit=hit+1 where board_id=?";
			pstmt.setInt(1, board_id);
			pstmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt, rs);
		}
		return board;
	}
	
	//update
	public int update(ImageBoard board) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql="update imageboard set author=?, title=?, content=? where board_id=?";
		
		con = dbManager.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getAuthor());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());
			pstmt.setInt(4, board.getBoard_id());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt);
		}	
		return result;
	}
	
	//delete
	public int delete(int board_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0 ;
		
		String sql ="delete from imageboard where board_id=?";
		
		con = dbManager.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_id);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
