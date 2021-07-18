package org.zerock.myweb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.zerock.myweb.domain.EmpDTO;
import org.zerock.myweb.domain.EmpVO;

import lombok.extern.log4j.Log4j2;

// **DAO**
// 1. 기본생성자를 가진다. -> DAO 객체를 만들기 쉽도록 ...
// 2. 매번 요청을 처리할 때마다, DAO 객체를 새로 만들어야 한다.

@Log4j2
public class EmpDAO {
	
	//JNDI은 공유객체이므로 static 
	private static DataSource ds;
	
	
	//static field의 초기화 수행 
	static {
		log.debug("static initializer invoked.");
		Context ctx = null;
		
		try {
			ctx = new InitialContext(); //JNDI root 획득
			
			
			Object obj = ctx.lookup("java:comp/env/jdbc/OracleCloud");
			ds = (DataSource) obj;
			
			log.info("\t+ ds : " + ds);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ctx != null) {
				try {
					ctx.close();
				} catch (NamingException e) {
					
				} //try- catch
			}//if
		}//try-catch-finally
		
	}//static initializer
	
	
	public boolean insertNewEmployee(EmpDTO dto) throws SQLException {
		log.debug("insertNewEmployee(dto) invoked.");
		
		String sql = "INSERT INTO emp(empno, ename, sal, deptno) VALUES (?,?,?,?)";
		
		try {
			Connection conn = EmpDAO.ds.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getEmpno());
			pstmt.setString(2, dto.getEname());
			pstmt.setDouble(3, dto.getSal());
			pstmt.setInt(4, dto.getDeptno());
			
			int affectedLines = pstmt.executeUpdate(); //for DML
			
			try(conn; pstmt;) {
				
				if(affectedLines > 0) { //if success
					return true;
				} else { 				//if failed.
					return false;
				} //if-else
				
			}//try-with-resources
			
		} catch (SQLException e) {
			throw e;
		}//try-catch
		
	}//insertNewEmployee
	
	
	public boolean deleteEmp(EmpDTO dto) throws SQLException {
		String sql = "DELETE FROM emp WHERE empno = ?";
		
		try {
			Connection conn = EmpDAO.ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getEmpno());
			
			int affectedLines = pstmt.executeUpdate();
			
			try(conn; pstmt;){
				
				if(affectedLines > 0) {
					return true;
				} else {
					return false;
				}//if-else
				
			}//try-with-resources
			
		} catch (SQLException e) {
			throw e;
		}//try-catch
		
	}//deleteEmp
	
	public boolean insertEmp(EmpDTO dto) throws SQLException{
		String sql = "INSERT INTO emp(empno, ename, sal, deptno) VALUES(?, ?, ?, ?)";
		
		try {
			Connection conn = EmpDAO.ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getEmpno());
			pstmt.setString(2, dto.getEname());
			pstmt.setDouble(3, dto.getSal());
			pstmt.setInt(4, dto.getDeptno());
			
			int affectedLines = pstmt.executeUpdate();
			
			try(conn; pstmt;) {
				if(affectedLines > 0) { 
					return true;
				} else {
					return false;
				}
			}
			
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public boolean updateEmp(EmpDTO dto) throws SQLException {
		String sql = "UPDATE emp SET deptno = ? WHERE empno = ?";
		
		try {
			
			Connection conn = EmpDAO.ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getDeptno());
			pstmt.setInt(2, dto.getEmpno());
			
			int affectedLines = pstmt.executeUpdate();
			
			if(affectedLines > 0) {
				return true;
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			throw e;
		}
	}
	
	
	public List<EmpVO> selectAll() throws SQLException {
		log.debug("selectAll() invoked.");
		
		List<EmpVO> employees = null;
		
		String sql = "SELECT empno, ename, sal, deptno FROM emp";
		
		try {
			
			Connection conn = EmpDAO.ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			
			try(conn; pstmt; rs;) {
				
				employees = new ArrayList<>();
				
				while(rs.next()) {
					int empno = rs.getInt("empno");
					String ename = rs.getString("ename");
					double sal = rs.getDouble("sal");
					int deptno = rs.getInt("deptno");

					//1개의 VO객체안에, 위에서 얻은 1개의 레코드의 데이터를 읽기전용으로 저장 
					EmpVO emp = new EmpVO(empno, ename, null, null, null, sal, null, deptno);
//					EmpVO emp = new EmpVO(empno, ename, sal, deptno);
					
					//각 VO객체를 List에 저장
					employees.add(emp);
					
				}//while
				
			}//try-with-resources
			
		} catch(SQLException e) {
			throw e;
		}//try-catch
		
		return employees;
	} //selectAll
	
	
	
	
	
}//end class
