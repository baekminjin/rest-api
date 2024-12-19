package com.ll.rest.global.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RsData<T>  {
	private String resultCode;
	private String msg;
	private T data;

	public RsData(String resultCode, String msg) {
		this(resultCode, msg, null);
	}
}