package org.zerock.myweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@WebServlet("/EmpInsert")
public class EmpInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
//	@Resource(name = "jdbc/OracleCloudWithLog4jdbc")
	private DataSource ds;
	
	private String sql = "INSERT INTO emp(empno, ename, sal, deptno) VALUES (?, ?, ?, ?)";
	
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		log.debug("init(config) invoked.");
		
//		JNDI lookup을 통해서, DataSource 객체를 얻어서 Connection Pool을 사용하자!!
		Context ctx = null; //자원객체이므로, 반드시 사용이 끝나면 close 해줘야 함.
							//단 이 Context 인터페이스는 AutoClosable 하지 않으므로, 직접 close해줘야 함.
		
		try {
//			-1.JNDI tree의 뿌리(root)을 먼저 접근해야 한다.
			ctx = new InitialContext(); // JNDI root 참조 획득
			log.info("\t+ ctx : " + ctx);
			
//			-2. JNDI root을 통해, JNDI tree를 lookup
//			자원객체의 이름 지정은 표준화되어 있다.
//			"java:comp/env/" + 자원객체의 이름 
			Object obj = ctx.lookup("java:comp/env/jdbc/OracleCloud");
			this.ds = (DataSource) obj;
			
			log.info("\t+ ds : " + ds);
			
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			if(ctx != null) {
				try {
					ctx.close();
				} catch (NamingException e) {
					
				} //try- catch
			}//if
		}//try-catch-finally
		
	}//init

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		
		log.debug("service(req, res) invoked.");
		
		try {
			//--1. 웹 브라우저에서 전송한 파라미터들을 획득한다.
			String empno = req.getParameter("empno");
			String ename = req.getParameter("ename");
			String sal = req.getParameter("sal");
			String deptno = req.getParameter("deptno");
			
			log.info("\t+ empno : " + empno);
			log.info("\t+ ename : " + ename);
			log.info("\t+ sal : " + sal);
			log.info("\t+ deptno : " + deptno);
			
			//--2. DataSource(Connection Pool)으로부터 Connection 얻기
			Connection conn = ds.getConnection();	// 자원객체-1
			PreparedStatement pstmt = conn.prepareStatement(sql); // 자원객체-2
			
			//바인딩 변수 설정
			pstmt.setInt(1, Integer.parseInt(empno));
			pstmt.setString(2, ename);
			pstmt.setDouble(3, Double.parseDouble(sal));
			pstmt.setInt(4, Integer.parseInt(deptno));
			
			int affectLines = pstmt.executeUpdate();

			//--3. 응답문서 준비 단계
			res.setContentType("text/html; charset=utf-8");
			PrintWriter out = res.getWriter(); // 자원객체-3
			
			try(conn; pstmt; out;) {
				if(affectLines > 0) {
					out.println("저장 성공! ");
				} else {
					out.println("저장 실패! ");
				}//if-else
				
				out.flush();
			}//try-with-resources
			
		} catch (Exception e) {
			throw new ServletException(e);
		}//try-catch
		
	}//service

}//end class
