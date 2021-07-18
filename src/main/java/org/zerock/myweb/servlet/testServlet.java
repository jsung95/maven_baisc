package org.zerock.myweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2


@WebServlet("/test")
public class testServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name = "jdbc/OracleCloud")
	private DataSource ds;
	
	private String sql = "SELECT * FROM emp";
	
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}


	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		try {
			
			Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			
			try(rs; pstmt; conn;) {
				
				res.setContentType("text/html; charset=utf-8");
				PrintWriter out = res.getWriter();
				
				out.println("<!DOCTYPE html>");
				out.println("<html><head>");
				out.println("	<title>test</title>");
				out.println("</head>");
				out.println("<body>");
				
				try(out) {
					out.println("<h3>GET DB</h3>");
					int count = 0;
					
					while(rs.next()) {
						String empno = rs.getString("empno");
						String deptno = rs.getString("deptno");
						
						out.print(count + "empno : " + empno + "<br>");
						out.print(count + "deptno ; " + deptno + "<br>");
						
						count++;
					}
					
					out.flush();
				}
				
				out.println("</body>");
				out.println("</html>");
				
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}

}
