package org.zerock.myweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zerock.myweb.domain.EmpDTO;
import org.zerock.myweb.domain.EmpVO;
import org.zerock.myweb.service.DeleteService;
import org.zerock.myweb.service.InsertService;
import org.zerock.myweb.service.SelectService;
import org.zerock.myweb.service.UpdateService;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@NoArgsConstructor
@Log4j2

@WebServlet("*.do") //FrontController 패턴 : URL Mapping은 확장자 패턴을 사용하고 
											// --> 이를통해서, RequestURI가 `.do` 로 끝나면 모든요청은 
											// 이 서블릿이 수신/처리 한다.

public class FrontControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		
		log.debug("service(req, res) invoked.");
		
		try {
			String requestURI = req.getRequestURI();
//			String contextPath = req.getContextPath(); // ' / ' ----> 현재 이 서블릿 프로젝트의 경로  
//			String httpMethod = req.getMethod();
//			
//			
//			EmpDTO dto = new EmpDTO(); 
			
			//응답문서의 기본 골격 생성
//			res.setContentType("text/html; charset=utf-8");
//			PrintWriter out = res.getWriter();
//
//			
//			
//			log.info("\t+ requestURI : " + requestURI);
//			log.info("\t+ contextPath : " + contextPath);
//			log.info("\t+ httpMethod : " + httpMethod);
//			
//			
//			
//			
//			out.println("<!DOCTYPE html>");
//			out.println("<html><head>");
//			out.println("<title>FRONTController</title>");
//			out.println("</head>");
//			out.println("<body>");
//			
//			
//			
//			try(out){
//				 //각 요청별 응답 HTML 조각을 만들어 넣는다.
//				//요청유형식별
//				
//				
//				out.println("</body></html>");
//				
//				out.flush();
//				
//			} //try-with-resources
			
			
			//--------------------------------//
			//각 요청별 응답 HTML 조각을 만들어 넣는다.
			//요청유형식별
			switch(requestURI) {
			case "/insert.do": //저장요청
				log.info("\t+ command : " + requestURI);
				
				String empno1 = req.getParameter("empno");
				String ename1 = req.getParameter("ename");
				String sal1 = req.getParameter("sal");
				String deptno1 = req.getParameter("deptno");
				
				EmpDTO dto1 = new EmpDTO();
				dto1.setEmpno(Integer.parseInt(empno1));
				dto1.setEname(ename1);
				dto1.setSal(Double.parseDouble(sal1));
				dto1.setDeptno(Integer.parseInt(deptno1));
				
				InsertService insertServlet = new InsertService();
				insertServlet.execute(dto1);
//				boolean isSuccess1 = insertServlet.execute(dto1);
//				
//				if(isSuccess1) {
//					log.info("입력성공");
//				} else {
//					log.info("입력실패");
//				}
//				
				req.setAttribute("__EMPINSERT__", dto1);
				
				RequestDispatcher dispatcher1 = req.getRequestDispatcher("/WEB-INF/createResponse.jsp");
				dispatcher1.forward(req, res);
								
				
				
				break;
			case "/delete.do": //삭제요청 
				log.info("\t+ command : " + requestURI);

				String empno = req.getParameter("empno");
				
				EmpDTO dto = new EmpDTO();
				dto.setEmpno(Integer.parseInt(empno));
				
				DeleteService deleteService = new DeleteService();
				boolean isSuccess = deleteService.execute(dto);
				
				if(isSuccess) {
					log.info("삭제성공");
				} else {
					log.info("삭제실패");
				}
				
				req.setAttribute("__EMPDELETE__", dto);
				
				RequestDispatcher dispatcher2 = req.getRequestDispatcher("/WEB-INF/createResponse.jsp");
				dispatcher2.forward(req, res);
				
				break;
			case "/update.do": //갱신요청 
				log.info("\t+ command : " + requestURI);
				
				String deptno3 = req.getParameter("deptno");
				String empno3 = req.getParameter("empno");
				
				EmpDTO dto3 = new EmpDTO();
				dto3.setDeptno(Integer.parseInt(deptno3));
				dto3.setEmpno(Integer.parseInt(empno3));
				
				UpdateService updateService = new UpdateService();
				
				boolean isSuccess3 = updateService.execute(dto3);
				
				if(isSuccess3) {
					log.info("존나성공해버림");
				} else {
					log.info("씨ㅣ발 ");
				}
				
				req.setAttribute("__EMPUPDATE__", dto3);
				
				RequestDispatcher dispatcher3 = req.getRequestDispatcher("/WEB-INF/createResponse.jsp");
				dispatcher3.forward(req, res);
				
				break;
			case "/select.do": //조회요청
				log.info("\t+ command : " + requestURI);
				SelectService service = new SelectService();
				List<EmpVO> employees = service.execute(null);
				

				//-------------------------------------------------//
				//요청 포워딩 수행 전에, View가 응답을 생성하는데 필요한 모든 데이터.
				//대표적으로 Model을 Request Scope공유영역에 속성바인딩 해서 전달한다.
				//또한, 그 외 필요한 데이터 역시 같은 공유영역에 속성바인딩 해서 전달한다.
				//-------------------------------------------------//
				req.setAttribute("__EMPLOYEES__", employees);
				req.setAttribute("__EMPDTO__", null); //null 값은 속성바인딩 불가하다. 코드가 존재하더라도 실제로 값이 들어가지도 않음
				
				
				//-------------------------------------------------//
				//응답문서를 생성/전송할 View를 호출(즉, View로 요청 포워딩 수행)
				//-------------------------------------------------//
				RequestDispatcher dispatcher = 
						req.getRequestDispatcher("/WEB-INF/createResponse.jsp");
				dispatcher.forward(req, res);
				
				
				
//				try {
//				out.println("<ul>");
//				
//				employees.forEach(vo -> { 
//					out.println("<li>" + vo + "</li>");
//				});
//				
//				out.println("</ul>");
//				} finally { //자원해제 
//					Objects.requireNonNull(employees);
//					
//					employees.clear();
//					employees = null;
//				}//try-finally
				
				break;
		
			}//switch
			
		} catch (Exception e) {
			throw new ServletException(e);
		}//try-catch
		
	}//service

}//end class
