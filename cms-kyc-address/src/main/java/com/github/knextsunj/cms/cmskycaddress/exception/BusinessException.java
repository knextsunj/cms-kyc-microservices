package com.github.knextsunj.cms.cmskycaddress.exception;

public class BusinessException extends RuntimeException {

	public BusinessException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public BusinessException(String message) {
		super(message, null);
	}

}
