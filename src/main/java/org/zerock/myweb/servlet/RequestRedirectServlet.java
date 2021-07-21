package org.zerock.myweb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2
@WebServlet("/RequestRedirect")
public class RequestRedirectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		log.debug("service(req, res) invoked.");
		
		// Request Scope 공유영역에 속성 2개 바인딩 
		req.setAttribute("NAME1", "VALUE1");
		req.setAttribute("NAME2", "VALUE2");
		
		//Redirect응답을 전송
		res.sendRedirect("ResponseRedirect" + "?age=23");
		
		
	}//service

}//end class
