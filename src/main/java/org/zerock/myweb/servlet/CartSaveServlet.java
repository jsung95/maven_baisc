package org.zerock.myweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2
@WebServlet("/CartSave")
public class CartSaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		log.debug("service(req, res) invoked.");
		
		
		try {
			//----------------------------------//
			//1. 요청처리 
			//----------------------------------//
			
			//--1. 전송파라미터 얻기
			String product = req.getParameter("product");
			log.info("\t+ product : " + product);
			
			//--2. HttpSession 객체를 얻기
			HttpSession sess = req.getSession();
			log.info("\t+ sess : " + sess);
			log.info("\t+ >>>>>> session ID : " + sess.getId());
			
			//--3. Session Scope 공유영역에 저장된 장바구니(List<String>)를 획득
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) sess.getAttribute("product");
			
			if(list == null) {//장바구니가 없으면.. 새로운 장바구니를 생성해서, Session Scope에 저장
				list = new ArrayList<>();
				
				sess.setAttribute("product", list);
			}//if
			
			//세션스콥에 리스트객체를 올려놨으니 요청파라미터를 그 리스트에 추가해준다.
			list.add(product);
			log.info("\t+ 장바구니리스트 : " + list);
			
			//----------------------------------//
			//2. 응답문서 만들기
			//----------------------------------//
			res.setContentType("text/html; charset=utf-8");
			PrintWriter out = res.getWriter();
			
			try(out) {
				out.print("<html><body>");
				out.print("Product Add <br>");
				out.print("<a href='CartBasket'>장바구니보기</a>");
				out.print("</body></html>");
				
				out.flush();
			}//try-with-resources
			
		} catch (Exception e) {
			throw new ServletException(e);
		}//try-catch 
		
	}//service

}//end class
