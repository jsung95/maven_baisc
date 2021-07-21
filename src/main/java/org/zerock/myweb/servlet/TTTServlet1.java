package org.zerock.myweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

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
@WebServlet("/TTT1")
public class TTTServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	// 인증성공가능한 아이디와 암호를 하드코딩 
	private final String sid = "trinity";
	private final String spw = "12345";
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		log.debug("service(req, res) invoked.");
		
		//아이디와 암호 전송(post방식으로 들어옴) 파라미터 획득 
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		
		log.info("\t + name : " + id);
		log.info("\t + age : " + pw);
		
		
		
		
		
		
		PrintWriter out = res.getWriter();
		
		out.println("TTT1");
		
		

		if(sid.equals(id) && spw.equals(pw)) { //인증성공 
			out.println("success");
			
			//Session Scope에 인증성공했다라는 정보를 올려 놓자!!
			HttpSession session = req.getSession();
			session.setAttribute("__AUTH__", id);
			
		} else { //인증실패
			out.println("fail");
		}
		
		
		out.flush();
		out.close();
	}

}
