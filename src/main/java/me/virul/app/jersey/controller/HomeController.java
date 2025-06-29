package me.virul.app.jersey.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.io.FilenameUtils;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.server.mvc.Viewable;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Path("/")
public class HomeController {

    @Context
    ServletContext context;

    @GET
    public Viewable index() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("message", "Hello World");
        return new Viewable("/index", model);
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(@FormDataParam("file") FormDataBodyPart file) {
        InputStream content = file.getContent();
        ContentDisposition contentDisposition = file.getContentDisposition(); /// file meta data
        String extension = FilenameUtils.getExtension(contentDisposition.getFileName()); /// get file extension
        String realPath = context.getRealPath("/");
        java.nio.file.Path path = java.nio.file.Paths.get(realPath+"/uploads");
        if (!Files.exists(path)) {
            try{
                Files.createDirectory(path);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        try {
            int read = 0;
            byte[] buffer = new byte[1024];
            FileOutputStream fileOutputStream = new FileOutputStream(new File( path+"/"+ System.currentTimeMillis() + "." + extension));
            while ((read = content.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, read);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.ok().build();
    }

    @POST
    @Path("/upload/files")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFiles(@FormDataParam("files[]") FormDataBodyPart files) {
        files.getParent().getBodyParts().forEach(file -> {
            InputStream inputStream = file.getEntityAs(InputStream.class);
            String extension = FilenameUtils.getExtension(file.getContentDisposition().getFileName()); /// get file extension
            java.nio.file.Path path = java.nio.file.Paths.get(context.getRealPath("/")+"/uploads");
            if (!Files.exists(path)) {
                try{
                    Files.createDirectory(path);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            try {
                int read = 0;
                byte[] buffer = new byte[1024];
                FileOutputStream fileOutputStream = new FileOutputStream(new File( path+"/"+ System.currentTimeMillis() + "." + extension));
                while ((read = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, read);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return Response.ok().build();
    }
}
