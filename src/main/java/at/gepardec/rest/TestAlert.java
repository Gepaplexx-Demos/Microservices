package at.gepardec.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.micrometer.core.instrument.MeterRegistry;

import java.util.concurrent.atomic.AtomicInteger;


@Path("/test-alert")
public class TestAlert {


    AtomicInteger testalertactive;

    TestAlert(MeterRegistry registry) {
        this.testalertactive = new AtomicInteger(0);
        registry.gauge("test.alert", testalertactive);
    }

    @GET
    @Path("/on")
    @Produces(MediaType.TEXT_PLAIN)
    public Response turnOnAlert() {

        testalertactive.set(1);
        return Response
                .status(200)
                .entity("Alert turned on!")
                .build();
    }

    @GET
    @Path("/off")
    @Produces(MediaType.TEXT_PLAIN)
    public Response turnOffAlert() {

        testalertactive.set(0);
        return Response
                .status(200)
                .entity("Alert turned off!")
                .build();
    }

}
