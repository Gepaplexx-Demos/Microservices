package my.groupId.loadtesting;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.mvc.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/loadtesting")
public class memoryload {
    
    @GET
    @Controller
    @Path("/mem/{size_in_MB}")
    public Response loadMemory(int size) throws InterruptedException {
        try{
            int leech = ' ' * 1024 * 1024 * size;
        } catch (OutOfMemoryError oom) {
            return Response.status(400).build();
        }
        //Thread.sleep(5000);
        return Response.status(200).build();
    }

    @GET
    @Path("/mem/test/{text}")
    public Response test(int text) throws InterruptedException {
        byte[][] leech;
        try{

            leech = new byte[1024 * text][1024];
            
        } catch (OutOfMemoryError oom) {
            return Response.status(400).build();
        }
        Thread.sleep(5000);
        leech = null;
        System.gc();
        return Response.status(200).build();
    }
}