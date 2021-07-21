package org.zerock.myweb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
@WebServlet("/TTT2")
public class TTTServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		log.debug("service(req, res) invoked.");
		
		//Session Scope에 미리 지정된 이름(로그인 성공시의 속성값)의 값이 있는지 확인
		HttpSession session = req.getSession();
		
		//임시로, 시나리오 때문에, 로그인 정보를 세션영역에서 삭제.
//		session.removeAttribute("__AUTH__");
		
		Object obj = session.getAttribute("__AUTH__");
		log.info("\t + __AUTH__ : " + obj);
		
		if(obj != null) { //이미 로그인되어있다면 
			//계좌이체를 수행하도록 /TTT3로 요청 포워딩 수행
			RequestDispatcher dispatcher = req.getRequestDispatcher("/TTT3");
			dispatcher.forward(req, res);
			
			log.info("\t + Request forward to /TTT3");
		} else { //아직 로그인 되지 않았다면
			//로그인 창으로 다시 화면을 이동시켜야 한다.
			//redirect로는 WEB-INF 폴더 밑에 있는 지원으로 이동시킬 수 없다. 
			res.sendRedirect("/login.html");
			log.info("\t + 인증이 안되어있기 때문에, 로그인 창으로 이동시킴.");
		}
		
	}//service

}//end class
