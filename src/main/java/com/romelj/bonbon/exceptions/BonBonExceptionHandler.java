package com.romelj.bonbon.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BonBonExceptionHandler implements ExceptionMapper<BonBonException> {

	@Override
	public Response toResponse(BonBonException e) {
		return Response.status(e.getBonBonError().getHttpStatusCode()).entity(new BonBonExceptionWrapper(e)).type(MediaType.APPLICATION_JSON_TYPE).build();
	}
}
