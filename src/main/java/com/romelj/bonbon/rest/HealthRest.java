package com.romelj.bonbon.rest;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@PermitAll
@Path("/rest")
public class HealthRest {

    @GET
    @Path("/health")
    public Response health(){
        return Response.ok("OK").build();
    }
}
