<%@page
	language="java"
	contentType="text/html; charset=utf8"
	pageEncoding="utf8" %>
	
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<!DOCTYPE html>
<html lang="ko">
<body>
	<h2>/WEB-INF/createResponse.jsp</h2>
	
	<hr>
	
	<!--  Request Scope 공유영역의 공유 데이터 얻기 -->
	<h3>전체사원목록조회</h3>
	<p>${__EMPLOYEES__}</p>
	
	<table border="1" style="border-collapse: collapse;">
		<caption>전체사원목록</caption>
		
		<thead>
			<th>EMPNO</th>
			<th>ENAME</th>
			<th>JOB</th>
			<th>MGR</th>
			<th>HIREDATE</th>
			<th>SAL</th>
			<th>COMM</th>
			<th>DEPTNO</th>
		</thead>
		
		<tbody>
			<c:forEach items="${__EMPLOYEES__}" var="empVO">
				<tr>
					<td>${empVO.empno}</td>
					<td>${empVO.ename}</td>
					<td>${empVO.job}</td>
					<td>${empVO.mgr}</td>
					<td>${empVO.hiredate}</td>
					<td>${empVO.sal}</td>
					<td>${empVO.comm}</td>
					<td>${empVO.deptno}</td>
				</tr>
			</c:forEach>
			
			
		</tbody>
		
	</table>
	
	<p>${__EMPDELETE__}</p>
	
	
	<p>${__EMPINSERT__}</p>
	
	<p>${__EMPUPDATE__}</p>
	
	
</body>
</html>
