package com.romelj.bonbon.exceptions;

public class BonBonException extends RuntimeException {

	private final BonBonError bonBonError;

	public BonBonException(BonBonError bonBonError) {
		this.bonBonError = bonBonError;
	}

	public BonBonException(String message, BonBonError bonBonError) {
		super(message);
		this.bonBonError = bonBonError;
	}

	public BonBonException(String message, Throwable cause, BonBonError bonBonError) {
		super(message, cause);
		this.bonBonError = bonBonError;
	}

	public BonBonException(Throwable cause, BonBonError bonBonError) {
		super(cause);
		this.bonBonError = bonBonError;
	}

	public BonBonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, BonBonError bonBonError) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.bonBonError = bonBonError;
	}

	public BonBonError getBonBonError() {
		return bonBonError;
	}

}
