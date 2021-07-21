package org.zerock.myweb.servlet;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2

@WebServlet("/Upload")
@MultipartConfig(location="/Users/jinsung/tmp", maxFileSize=1024*1024*200)
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		
		
		try {
		    
			Date now = new Date();
			DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			String today = formatter.format(now);
			
			log.info(today);
			
			Part part = req.getPart("theFile");
			log.info("\t+ 1. getName() : " + part.getName());
			log.info("\t+ 2. getSize() : " + part.getSize());
			log.info("\t+ 3. getContentType() : " + part.getContentType());
			log.info("\t+ 4. getSubmittedFileName() : " + part.getSubmittedFileName());
			log.info("\t+ 5. getInputStream() : " + part.getInputStream());
			log.info("\t+ part : " + part);
			
			
			LocalDate todaysDate = LocalDate.now();
	        log.info("todaysDate : "+ todaysDate);
	         
	         
	        String filePath = todaysDate+"/";   //날짜별로 저장할 폴더 경로
	        String fullPath="/Users/jinsung/tmp/"+filePath;   //어노테이션에서 지정한 경로 + 날짜 경로
	
	        File file = new File(fullPath);   //fullPath로 파일 객체 생성
	
	        if(!file.isDirectory()){   //만약 경로에 지정된 폴더가 없으면
	
	            file.mkdirs();         //폴더 새로 만들기 
	
	        }//if
		    
			
			// 상대경로 지정(왜? @MultipartConfig어노테이션의 location 경로가 어떻게 작동하는가)
//			part.write(part.getSubmittedFileName()); //location경로 + 지정파일명 OK
//			part.write("/Users/jinsung/tmp2/" + part.getSubmittedFileName()); //절대경로 OK
//			part.write("ttt/" + part.getSubmittedFileName());
			part.write(filePath + part.getSubmittedFileName());
			
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

}
