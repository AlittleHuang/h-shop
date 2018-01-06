package com.shengchuang.service.result;

public class Message implements IMessage {

	private static final long serialVersionUID = 4597129995780069182L;
	
	protected boolean success = true;
	protected String message;

	public Message() {
	}

	public Message(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public Message(boolean success) {
		super();
		this.success = success;
	}

	public Message(String message) {
		super();
		this.message = message;
	}

	public Message success(boolean success) {
		this.success = success;
		return this;
	}


	public Message msg(String message) {
		this.message = message;
		return this;
	}

	public Message failMsg(String message) {
		this.success = false;
		this.message = message;
		return this;
	}
	

	@Override
	public boolean success() {
		return this.success;
	}

	@Override
	public String msg() {
		return message;
	}

}
