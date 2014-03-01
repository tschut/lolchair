package com.lolchair.server;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/lolchair")
public class LolchairService {
    @GET
    @Path("/hello")
    public Response hello() {
        String response = "Hello world";
        return Response.status(200).entity(response).build();
    }

    @POST
    @Path("/submit")
    @Consumes("*/*")
    public Response submit(InputStream file, @QueryParam("message") String message) {
        Mailer mailer = new Mailer();
        mailer.submit(message, file);
        return Response.status(200).entity(message).build();
    }
}