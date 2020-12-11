/*

 * Ŀ�ؼ� Ǯ ����� ���� ���ϰ� ó���ؾ� DAO �鿡�� ���� ���Ѵ�

 * */

 

 

package common.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.tomcat.jni.Poll;

 
public class PoolManager {
	InitialContext context;//JNDI �˻��� ����ϴ� ��ü 
	DataSource ds; //Ŀ�ؼ� Ǯ
	private static PoolManager instance;

	//�� �������ʹ� �ƹ��� new�� �� ����
	private PoolManager() {
		try {
			context= new InitialContext();//�˻���ü ����
			ds = (DataSource)context.lookup("java.comp/env/jdbc/myoracle");//ã�� ���� and Ǯ ��ȯ
		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}
	
	public static PoolManager getInstance() {
		if(instance==null) {
			instance = new PoolManager(); // ���� ������ new �� �� �ֵ� .
		}
		return instance;
	}
	//Ŀ�ؼ��� �ʿ��� �ڿ��� Connection�� ��ȯ���ִ� (�뿩) �޼���
	public Connection getConnection() {
		Connection con=null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	//�ݳ�
	public void release(Connection con,PreparedStatement pstmt) {
		if(pstmt!=null) {
			try {
				pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(con!=null) {
			try {
				con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void release(Connection con,PreparedStatement pstmt,ResultSet rs) {
		if(pstmt!=null) {
			try {
				pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(con!=null) {
			try {
				con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(rs!=null) {
			try {
				rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}