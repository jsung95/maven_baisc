package org.zerock.myweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zerock.myweb.domain.EmpDTO;
import org.zerock.myweb.domain.EmpVO;
import org.zerock.myweb.persistence.EmpDAO;

import lombok.NoArgsConstructor;


@NoArgsConstructor
@WebServlet("/EmpInsertDAO")
public class EmpInsertDAOServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		
		try {
			//-1. 수신된 전송파라미터에 추가
			String empno = req.getParameter("empno");
			String ename = req.getParameter("ename");
			String sal = req.getParameter("sal");
			String deptno = req.getParameter("deptno");
			
			//-2. 1에서 획득한 전송파라미터를 DTO 객체로 저장
			EmpDTO dto = new EmpDTO();
			dto.setEmpno(Integer.parseInt(empno));
			dto.setEname(ename);
			dto.setSal(Double.parseDouble(sal));
			dto.setDeptno(Integer.parseInt(deptno));
			
			//-3. 비지니스 로직 처리 (신규사원 저장)
			EmpDAO dao = new EmpDAO();
			boolean isSucess = dao.insertNewEmployee(dto);
			
			
			List<EmpVO> selectEMP = dao.selectAll();
			
			//-4. 응답문서 생성
			res.setContentType("text/html; charset=utf-8");
			PrintWriter out = res.getWriter();
			
			try(out) {
				
				if(isSucess) {
					out.println("저장성공!");
				} else {
					out.println("저장실패!");
				}//if-else
				
				selectEMP.forEach(e -> {
							out.print("<h1>" + e.getEname() + "</h1>");
					} );
				
				out.flush();
			}//try-with-resources
			
			
		} catch (Exception e) {
			throw new ServletException(e);
		}//try-catch
	}//service

}//end class
