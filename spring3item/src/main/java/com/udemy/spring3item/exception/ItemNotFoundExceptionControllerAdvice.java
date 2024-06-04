package com.udemy.spring3item.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//例外をハンドルするためのクラス
@ControllerAdvice
public class ItemNotFoundExceptionControllerAdvice {
	@ResponseBody
	@ExceptionHandler(ItemNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String ItemNoFoundHandler(ItemNotFoundException ex) {
		return ex.getMessage();
	}
}
