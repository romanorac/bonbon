package com.romelj.bonbon.exceptions;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Arrays;
import java.util.logging.Logger;

class BonBonExceptionWrapper {

	private static final Logger logger = Logger.getLogger(BonBonExceptionWrapper.class.getSimpleName());
	private final Throwable cause;
	private final StackTraceElement[] stackTrace;
	private final String bonBonErrorString;
	private final String message;
	private final String localizedMessage;
	private final Throwable[] throwable;

	BonBonExceptionWrapper(BonBonException e) {
		this.cause = e.getCause();
		this.stackTrace = e.getStackTrace();
		this.message = e.getMessage();
		this.localizedMessage = e.getLocalizedMessage();
		this.throwable = e.getSuppressed();
		this.bonBonErrorString = e.getBonBonError() == null ? BonBonError.NULL.toString() : e.getBonBonError().toString();

		logger.severe(toString());
	}

	@JsonIgnore
	public Throwable getCause() {
		return cause;
	}

	@JsonIgnore
	public StackTraceElement[] getStackTrace() {
		return stackTrace;
	}

	public String getBonBonError() {
		return bonBonErrorString;
	}

	@JsonIgnore
	public String getMessage() {
		return message;
	}

	@JsonIgnore
	public String getLocalizedMessage() {
		return localizedMessage;
	}

	@JsonIgnore
	public Throwable[] getThrowable() {
		return throwable;
	}

	public String toString() {
		String errorLog = "";
		if (cause != null) {
			errorLog += cause + "\n";
		}

		if (stackTrace != null && stackTrace.length > 0) {
			errorLog += Joiner.on('\n').join(Arrays.asList(stackTrace)) + "\n";
		}

		if (Strings.isNullOrEmpty(bonBonErrorString)) {
			errorLog += bonBonErrorString + "\n";
		}

		if (!Strings.isNullOrEmpty(message)) {
			errorLog += message + "\n";
		}

		if (!Strings.isNullOrEmpty(localizedMessage)) {
			errorLog += localizedMessage + "\n";
		}

		if (throwable != null && throwable.length > 0) {
			errorLog += Joiner.on('\n').join(Arrays.asList(throwable)) + "\n";
		}

		return errorLog;

	}
}
