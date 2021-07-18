package org.zerock.myweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

//@WebServlet("/EmpSelect")
public class EmpSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
//	/Users/jinsung/Desktop/opt/OracleCloudWallet/ATP/tnsnames.ora
	
//	static final String log4jdbcDriver = "net.sf.log4jdbc.sql.jdbcapi.DriverSpy";
	
//	static final String driver = "oracle.jdbc.OracleDriver";
//	static final String jdbcUrl = "jdbc:log4jdbc:oracle:thin:@db202106301639_high?TNS_ADMIN=/Users/jinsung/Desktop/opt/OracleCloudWallet/ATP";
//	static final String user = "HR";
//	static final String pass = "Oracle12345!!!";
	
	private String log4jdbcDriver;
	private String driver;
	private String jdbcUrl;
	private String jdbcUrlSpy;
	private String user;
	private String pass;
	
	
	static final String sql = "SELECT empno, ename, sal, deptno FROM emp";
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		log.debug("init() invoked.");
		try {
			// InitParameter
			this.log4jdbcDriver = config.getInitParameter("log4jdbcDriver");
			this.driver = config.getInitParameter("driver");
			this.jdbcUrlSpy = config.getInitParameter("jdbcUrlSpy");
			this.jdbcUrl = config.getInitParameter("jdbcUrl");
			this.user = config.getInitParameter("user");
			this.pass = config.getInitParameter("pass");
			
			log.info("log4jdbcDriver : " + this.log4jdbcDriver);
			log.info("driver : " + this.driver);
			log.info("jdbcUrlSpy : " + this.jdbcUrlSpy);
			log.info("jdbcUrl : " + this.jdbcUrl);
			log.info("user : " + this.user);
			log.info("pass : " + this.pass);
			
			// Servlet Context
//			ServletContext ctx = config.getServletContext();
//			log.info("\t+ ctx : " + ctx);
//			
//			this.driver = ctx.getInitParameter("driver"); 
//			this.jdbcUrl = ctx.getInitParameter("jdbcUrl");
//			this.user = ctx.getInitParameter("user");
//			this.pass = ctx.getInitParameter("pass");
//			
//			log.info("driver : " + this.driver);
//			log.info("jdbcUrl : " + this.jdbcUrl);
//			log.info("user : " + this.user);
//			log.info("pass : " + this.pass);
			
			
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	

	@Override
	public void destroy() {
		log.debug("destroy() invoked.");
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		
		log.debug("service(req, res) invoked.");
		

		
		try {
			//--1. JDBC 드라이버 로딩 
			Class.forName(driver);
//			Class.forName(log4jdbcDriver);
			
			//--2. JDBC 연동코드 
			Connection conn = DriverManager.getConnection(jdbcUrl, user, pass);
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			try(conn; pstmt; rs;){
				log.info("conn : " + conn);
				log.info("pstmt : " + pstmt);
				log.info("rs : " + rs);
				
				res.setContentType("text/html; charset=utf-8");
				PrintWriter out = res.getWriter();
				
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
				out.println("	<title>사원목록</title>");
				out.println("</head>");
				out.println("<body>");
				
				try(out) {
					
					out.println("	<h1>전체 사원 목록</h1>"); 
					
					//--3. 결과셋으로 각 레코드별로 컬럼값 추출 / 출력 
					while(rs.next()) {
						String empno = rs.getString("EMPNO");
						String ename = rs.getString("ENAME");
						String sal = rs.getString("SAL");
						String deptno = rs.getString("DEPTNO");
						
//						String employee = String.format(
//								"%s, %s, %s, %s",
//								empno, ename, sal, deptno);
//						
//						log.info(employee);
						
						out.print(empno + "\t" + ename + "\t" + sal + "\t" + deptno + "<br>");
						
					}//while
					
					out.println("</body>");
					out.println("</html>");
					
					out.flush();
					
				}//try-with-resources
				
			}//try-with-resources
			
		} catch (Exception e) {
			throw new IOException(e);
		}//try-catch
		
	}//service



}//end class
