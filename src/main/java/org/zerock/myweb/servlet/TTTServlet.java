package org.zerock.myweb.servlet;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor
@WebServlet("/TTT")
public class TTTServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		
		log.debug("service(req, res) invoked.");
		
		try {
			//HttpServletRequest 객체가 곧, 웹 브라우저에서 보낸 요청문서(request document)를 받는 객체
			//누가주느냐? -> Servlet Container 가
			// 1) Request Scope 공유영역 제공
			// 2) Request URL에 대한 모든 정보를 제공
			// 3) Request 문서에 포함되어 오는, 모든 전송파라미터를 획득 
			
			// 1) Request Scope 공유영역 관리
//			req.setAttribute(name, value); //공유영역에 속성 바인딩
//			req.getAttribute(name);		 //공유영역에 특정 속성값을 획득
//			req.removeAttribute(name);	 //공유영역의 특정 속성을 언바인딩 
			
			
			
//			EmpDTO dto = new EmpDTO();
//			dto.setEmpno(10000);
//			dto.setEname("LEE");
//			dto.setSal(70000.0);
//			dto.setDeptno(10);
//			req.setAttribute("NAME_1", dto); //속성 바인딩 into request scope.
//			
//			EmpDTO attrDTO = (EmpDTO) req.getAttribute("NAME_1");
//			
//			log.info("- attrDTO : " + attrDTO);
//			
//			req.removeAttribute("NAME_1");
			
			
			
			
			// 2) Request URL에 대한 모든 정보를 제공
			// get방식인지 post방식인지
			String httpMethod = req.getMethod();
			log.info("\t0. httpMethod : " + httpMethod);
			
			//현지 서블릿 프로젝트의 경로 (' / ') 
			String contextPath = req.getContextPath();
			log.info("\t1. contextPath : " + contextPath);
			
			//웹브라우저의나와있는 URL
			StringBuffer requestURL = req.getRequestURL();
			log.info("\t2. requestURL : " + requestURL);

			//요청 URL에서 URI만 추출 
			String requestURI = req.getRequestURI();
			log.info("\t3. requestURI : " + requestURI);
			
			//요청문서 body에 어떤 내용의 컨텐츠가있다면 그컨텐츠유형을 되돌려준다 
			String contentType = req.getContentType();
			log.info("\t4. contentType : " + contentType);

			//쿼리스트링을 얻는다.
			String queryString = req.getQueryString();
			log.info("\t5. queryString : " + queryString);
			
			//클라이언트 PC의 ip값
			String getRemoteAddr = req.getRemoteAddr();
			log.info("\t6. getRemoteAddr : " + getRemoteAddr);
			
			//클라이언트 PC의 ip값
			String getRemoteHost = req.getRemoteHost();
			log.info("\t7. getRemoteHost : " + getRemoteHost);
			
			//클라이언트 PC의 port번호
			int getRemotePort = req.getRemotePort();
			log.info("\t8. getRemotePort : " + getRemotePort);

			//전송파라미터의 길이를 얻는다 .
			int getContentLength = req.getContentLength();
			log.info("\t9. getContentLength : " + getContentLength);
			
			//요청파라미터 값 획득 
			String name = req.getParameter("name");
			log.info("\t10. name : " + name);
			
			String age = req.getParameter("age");
			log.info("\t11. age : " + age);
			
			//사용자 컴퓨터 이름
			String serverName = req.getServerName();
			log.info("\t12. serverName : " + serverName);
			
			//아파치의 포트번호 
			int serverPort = req.getServerPort();
			log.info("\t13. serverPort : " + serverPort);
			
			//인코딩 처리 방식 출력 -> UTF-8
			String charEncoding = req.getCharacterEncoding();
			log.info("\t14. charEncoding : " + charEncoding);
			
			//현재 요청을 처리하고있는 서블릿의 이름(FQCN)
			HttpServletMapping mapping = req.getHttpServletMapping();
			log.info("\t15. getHttpServletMapping : " + mapping);
			
			
			String getServletName = mapping.getServletName();
			log.info("\t16. getServletName : " + getServletName);
			
			//URL Mapping 정보 URL패턴 
			String getPattern = mapping.getPattern();
			log.info("\t17. getPattern : " + getPattern);

			//URL 패턴에서 Mapping 정보 값만 (즉, '/' 뺀값) 
			String getMatchValue = mapping.getMatchValue();
			log.info("\t18. getMatchValue : " + getMatchValue);
			
			//Session Scope 공유영역 관리 객체 
			HttpSession session = req.getSession();
			log.info("\t19. session : " + session);
			
			//Session Scope의 값 획득 
			session.setAttribute("NAME_2", "LEE");
			String name_2 = (String) session.getAttribute("NAME_2");
			log.info("\t20. name_2 : " + name_2);
			
			session.removeAttribute("NAME_2");
			
			//쿠키(작은데이터를 저장하고있는객체)의 객체정보를 얻는다.
			Cookie[] cookies = req.getCookies();
			log.info("\t21. cookies : " + Arrays.toString(cookies));
			
//			for(Cookie cookie : cookies) {
//				//쿠키의 이름 (아파치 톰캣에서는 'JSESSIONID')
//				//쿠키의 이름이 곧 브라우저의 이름이다.
//				log.info("\t22. cookie's name : " + cookie.getName());
//				
//				//
//				log.info("\t23. cookie's value : " + cookie.getValue());
//			}
			
			//공유영역중에 가장 오래살아남는 application scope
			//이 공유 영역에 저장한 파라미터 값을 얻는다. 
			ServletContext sc = req.getServletContext();
			sc.setAttribute("NAME_3", 23);
			int appScopeValue = (Integer)sc.getAttribute("NAME_3");
			
			log.info("\t24. appScopeValue : " + appScopeValue);
			
			sc.removeAttribute("NAME_3");
			
			
			
			//---------------------------------------------//
			// Request Forwarding : 여기까지는 내가 처리했고, 
			// 나머지는 너가 처리해라고 요청처리를 위임하는 행위 
			//---------------------------------------------//
//			RequestDispatcher rd = req.getRequestDispatcher("/EmpSelect");
//			rd.forward(req, res);
			
			
			//Request Scope 공유영역 Model, DTO, 그 외 모든 필요한 데이터를 속성 바인딩 
			req.setAttribute("MODEL", 1);
			req.setAttribute("DTO", 2);
			req.setAttribute("ETC", 3);
			
			RequestDispatcher dispatcher = 
					req.getRequestDispatcher("/WEB-INF/createResponse.jsp");
			
			dispatcher.forward(req, res);
			
			//Forwarding과 Redirect는 동시에 진행될 수 없다. 
			
			//---------------------------------------------//
			// Redirect : HttpServlerResponse 객체의 메소드 이용 
			//---------------------------------------------//
			
			
//			res.sendRedirect("http://www.naver.com");
			
		} catch (Exception e) {
			throw new IOException(e);
		}//try-catch
		
	}//service

}//end class
