package org.zerock.myweb.service;

import java.sql.SQLException;
import java.util.List;

import org.zerock.myweb.domain.EmpDTO;
import org.zerock.myweb.domain.EmpVO;
import org.zerock.myweb.persistence.EmpDAO;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class SelectService {
	public List<EmpVO> execute(EmpDTO dto) throws SQLException {
		log.debug("execute(dto) invoked.");
		
		EmpDAO dao = new EmpDAO();
		
		//비지니스 로직을 수행하고, 그 결과를 받아서 콘솔에 출력 
		
		List<EmpVO> employees = dao.selectAll();
		employees.forEach(log::info);
		
		return dao.selectAll();
	}//execute

	
}//end class
