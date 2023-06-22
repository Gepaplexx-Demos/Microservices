package at.gepardec.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;


@Path("/test-alert")
@ApplicationScoped
public class TestAlert {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogErrorGeneratorResource.class);
    AtomicInteger testAlertActive;

    TestAlert(MeterRegistry registry) {
        this.testAlertActive = new AtomicInteger(0);
        Gauge.builder("demo.microservice.test.alert.active", testAlertActive, AtomicInteger::doubleValue)
                .description("This is a custom metric that is toggled on and off via the /test-alert/ endpoint.")
                .register(registry);
    }

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("Starting TestAlert endpoint");
    }

    @GET
    @Path("/on")
    @Produces(MediaType.TEXT_PLAIN)
    public Response turnAlertOn() {

        testAlertActive.set(1);
        return Response
                .status(200)
                .entity("Alert turned on!")
                .build();
    }

    @GET
    @Path("/off")
    @Produces(MediaType.TEXT_PLAIN)
    public Response turnAlertOff() {

        testAlertActive.set(0);
        return Response
                .status(200)
                .entity("Alert turned off!")
                .build();
    }

}
