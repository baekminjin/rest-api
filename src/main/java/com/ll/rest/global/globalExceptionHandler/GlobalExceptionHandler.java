package com.ll.rest.global.globalExceptionHandler;

import com.ll.rest.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<RsData<Void>> handle(NoSuchElementException ex) {
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(new RsData<>(
						"404-1",
						"해당 데이터가 존재하지 않습니다."
				));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class) //데이터를 이상하게 보냈다.
	public ResponseEntity<RsData<Void>> handle(MethodArgumentNotValidException ex) {
		String message = ex.getBindingResult().getFieldError().getDefaultMessage();
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new RsData<>(
						"400-1",
						message
				));
	}
}