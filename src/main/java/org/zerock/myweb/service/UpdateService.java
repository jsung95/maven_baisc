package org.zerock.myweb.service;

import java.sql.SQLException;

import org.zerock.myweb.domain.EmpDTO;
import org.zerock.myweb.persistence.EmpDAO;

public class UpdateService {
	public boolean execute(EmpDTO dto) throws SQLException{
		EmpDAO dao = new EmpDAO();
		
		boolean isSuccess = dao.updateEmp(dto);
		
		return isSuccess;
	}
} 
