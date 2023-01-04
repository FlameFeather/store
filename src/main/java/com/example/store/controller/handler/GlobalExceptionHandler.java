package com.example.store.controller.handler;

import com.example.store.exception.ServiceException;
import com.example.store.response.ResponseJson;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

//目标：处理请求时抛出异常，则会调用此类中处理异常的方法
//@ControllerAdvice:   springMVC会在处理请求时按需调用此类中的方法
//@RestControllerAdvice = @ResponseBody+@ControllerAdvice

//@ExceptionHandler 此注解写在方法上，此方法是统一处理异常的方法

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ServiceException.class)
	public ResponseJson<Void> exceptionHandle(ServiceException e) {
		return ResponseJson.fail(e);
	}

	@ExceptionHandler(BindException.class)
	public ResponseJson<Void> handleBindException(BindException e) {
		String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
		return ResponseJson.fail(ResponseJson.State.ERR_BAO_REQUEST, message);
	}
}
