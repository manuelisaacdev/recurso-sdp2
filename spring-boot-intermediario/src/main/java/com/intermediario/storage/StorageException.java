package com.intermediario.storage;

@SuppressWarnings("serial")
public class StorageException extends RuntimeException {

	public StorageException(String message) {
		super(message);
	}

	public StorageException(Throwable cause) {
		super(cause);
	}
	
	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
