package com.jeremy94.diary.util;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
//com.jeremy94.diary 패키지에서 예외가 발생하면 
//에러 페이지를 지정해주는 어노테이션  
@ControllerAdvice("com.jeremy94.diary")
public class CommonExceptionAdvice {
	@ExceptionHandler(Exception.class)
	public String errorPage(Model model, Exception ex) {
		model.addAttribute("error", ex.getMessage());
		// error 디렉토리에 error.jsp 파일을 에러페이지로 설정
		return "/error/error";
	}
}

	

