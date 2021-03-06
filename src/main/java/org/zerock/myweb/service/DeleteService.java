package org.zerock.myweb.service;

import java.sql.SQLException;

import org.zerock.myweb.domain.EmpDTO;
import org.zerock.myweb.persistence.EmpDAO;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class DeleteService {
	public boolean execute(EmpDTO dto) throws SQLException {
		
		EmpDAO dao = new EmpDAO();
		
		boolean isSuccess = dao.deleteEmp(dto);
		
		if(isSuccess) {
			return true;
		} else {
			return false;
		}
		
	}
}
