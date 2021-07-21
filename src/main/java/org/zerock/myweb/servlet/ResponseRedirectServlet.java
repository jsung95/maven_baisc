package org.zerock.myweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2
@WebServlet("/ResponseRedirect")
public class ResponseRedirectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		log.debug("service(req, res) invoked.");
		
		
		try {
			//첫 번째 서블릿이 공유한 속성을 얻어보자!!
			Object name1 = req.getAttribute("NAME1");
			Object name2 = req.getAttribute("NAME2");
			
			log.info("\t + name1 : " + name1);
			log.info("\t + name2 : " + name2);
			
			
			String age = req.getParameter("age");
			log.info("\t+ age : " + age);
			
			res.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = res.getWriter();
			
			try(out) {
				out.println("<h1>1. name1 : " + name1 + "</h1>");
				out.println("<h1>2. name2 : " + name2 + "</h1>");
				
				out.flush();
			}//try-with-resources
			
		} catch (Exception e) {
			throw new ServletException(e);
		}//try-catch
		
		
	}

}
