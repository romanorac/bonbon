package com.romelj.bonbon.utils;

import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

/**
 * Utility methods for requests
 */
public final class RequestUtils {

	private final static String AuthorizationPrefix = "Basic";

	private RequestUtils() {
	}

	/**
	 * get user agent from http request
	 *
	 * @param request - http request
	 * @return user agent
	 */
	public static String getUserAgent(HttpServletRequest request) {
		return getHeader(request, "User-Agent");
	}

	/**
	 * Get session token from http request
	 *
	 * @param httpRequest - http request
	 * @return - session token or null
	 */
	public static String getToken(HttpServletRequest httpRequest) {
		String authorizationHeader = getHeader(httpRequest, HttpHeaders.AUTHORIZATION);

		if (!Strings.isNullOrEmpty(authorizationHeader)) {
			return authorizationHeader.substring(AuthorizationPrefix.length()).trim();
		}
		return null;
	}

	/**
	 * Get value for header key
	 *
	 * @param request - http request
	 * @param header - header name
	 * @return - return value of the header
	 */
	private static String getHeader(HttpServletRequest request, String header) {
		if (request == null) {
			return null;
		}

		return request.getHeader(header);
	}

}
