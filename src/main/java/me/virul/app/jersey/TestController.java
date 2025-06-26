package me.virul.app.jersey;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/test")
public class TestController {
    @GET
    public String index(){
        return "Hello World";
    }
}
