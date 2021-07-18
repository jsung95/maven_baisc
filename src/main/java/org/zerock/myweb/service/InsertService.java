package org.zerock.myweb.service;

import java.sql.SQLException;

import org.zerock.myweb.domain.EmpDTO;
import org.zerock.myweb.persistence.EmpDAO;

public class InsertService {
	public boolean execute(EmpDTO dto) throws SQLException {
		EmpDAO dao = new EmpDAO();
		
		boolean isSuccess = dao.insertEmp(dto);
		
		return isSuccess;
		
	}
}
