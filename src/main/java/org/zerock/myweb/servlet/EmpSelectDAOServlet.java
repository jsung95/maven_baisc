package org.zerock.myweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zerock.myweb.domain.EmpVO;
import org.zerock.myweb.persistence.EmpDAO;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor
@WebServlet("/EmpSelectDAO")
public class EmpSelectDAOServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		
		log.debug("service(req, res) invoked.");
		
		try {
			
			//DAO의 selectALL() 메소드 호출
			EmpDAO dao = new EmpDAO();
			List<EmpVO> employees = dao.selectAll();
			
			
			//employees.forEach(log::info);
			
			// 전체 사원목록을 응답문서로 만들어 웹 브라우저에 전송
			res.setContentType("text/html; charset=utf-8");
			PrintWriter out = res.getWriter();
			
			out.println("<!DOCTYPE html>");
			out.println("<html><head>");
			out.println("	<title>TITLE</title>");
			out.println("<body>");
			
			try(out) {
				
				out.println("<h1>전체 사원 목록</h1>");
				
				employees.forEach( e -> {
										out.print(e.getEmpno() + " ");
										out.print(e.getEname() + " ");
										out.print(e.getSal() + " ");
										out.print(e.getDeptno() + " ");
										out.print("<br>");
										} );
				
				out.println("</body></html>");
				
				out.flush();
			}
			
			
		} catch (Exception e) {
			throw new ServletException(e);
		}//try-catch
		
	}//service

}//end class
