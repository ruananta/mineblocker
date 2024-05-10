package ru.mor.iv.mineblocker.exception;

public class MineBlockerException extends Exception{

	private static final long serialVersionUID = -2091350799139931779L;
	private String message;
	
	public static MineBlockerException getNewException(String msg){
		return new MineBlockerException(msg);
	}
	
	public MineBlockerException(String msg) {
		message = msg;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
