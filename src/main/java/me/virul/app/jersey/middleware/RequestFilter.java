package me.virul.app.jersey.middleware;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.logging.Logger;

@Provider
public class RequestFilter implements ContainerRequestFilter {
    private static final Logger logger = Logger.getLogger(RequestFilter.class.getName());
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        logger.info("RequestFilter");
    }
}
