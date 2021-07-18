package org.zerock.myweb.domain;

import java.sql.Timestamp;

import lombok.Value;

@Value
// 자바빈즈 규약에 맞게, 대응 테이블의 스키마와 동일하게 작성 
public class EmpVO {
	
	// 각 컬럼에 대응하는 필드의 타입은 기본타입을 사용해서는 안되고,
	// 참조타입으로 지정(mapping)시켜야 함 ----> 이 부분이 어려운 부분이다.
	private Integer empno;
	private String ename;
	private String job;
	private Integer mgr;
	private Timestamp hiredate;
	private Double sal;
	private Double comm;
	private Integer deptno;
		
}//end class
