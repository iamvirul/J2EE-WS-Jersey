package me.virul.app.jersey.config;

import jakarta.ws.rs.ApplicationPath;
import me.virul.app.jersey.model.User;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

public class AppConfig extends ResourceConfig {
    public AppConfig() {
        packages("me.virul.app.jersey.controller");
        packages("me.virul.app.jersey.middleware");
        register(JspMvcFeature.class);
        new AbstractBinder() {
            @Override
            protected void configure() {
                bind(User.class).to(User.class);
            }
        };
        property(JspMvcFeature.TEMPLATE_BASE_PATH, "/WEB-INF/views");
    }
}
