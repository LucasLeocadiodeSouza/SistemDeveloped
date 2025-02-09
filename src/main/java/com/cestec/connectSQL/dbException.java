package com.cestec.connectSQL;

public class dbException extends RuntimeException{
	public static final long serialVersionUID = 1L;

	public dbException(String msg) {
		super(msg);
	}
}
