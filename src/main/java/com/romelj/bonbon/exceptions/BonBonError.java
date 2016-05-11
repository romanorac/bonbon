package com.romelj.bonbon.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static org.apache.http.HttpStatus.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum BonBonError {
	UserExists("User already exists", SC_BAD_REQUEST, 100),
	MissingRegistrationData("Missing registration data", SC_BAD_REQUEST, 101),
	MissingUserEmail("Missing user email", SC_BAD_REQUEST, 102),
	IncorrectLoginData("Incorrect login data", SC_BAD_REQUEST, 103),
	UserNotExists("User does not exist", SC_NOT_FOUND, 104),
	IncorrectPassword("Incorrect password", SC_BAD_REQUEST, 105),
	MissingPassword("Missing password", SC_BAD_REQUEST, 106),
	InvalidContext("Invalid context", SC_BAD_REQUEST, 108),
//	InvalidData("Invalid data", SC_BAD_REQUEST, 107),
//	SessionNotFound("Session not found", SC_NOT_FOUND, 109),
//	UnAuthorizedAccess("UnAuthorized Access", SC_FORBIDDEN, 110),
//	NotAuthorized("Authorization header must be provided", SC_FORBIDDEN, 111),


	NULL("Undefined exception", 0, 999);

	private final int httpStatusCode;
	private final String message;
	private final int errorCode;

	BonBonError(String message, int httpStatusCode, int errorCode) {
		this.httpStatusCode = httpStatusCode;
		this.message = message;
		this.errorCode = errorCode;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public String getMessage() {
		return message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	@Override
	public String toString() {
		return "HttpStatus code: " + String.valueOf(httpStatusCode) + ", " + message + ", Error code: " + String.valueOf(errorCode) + ".";
	}
}
