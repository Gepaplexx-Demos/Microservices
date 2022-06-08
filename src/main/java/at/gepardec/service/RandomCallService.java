package at.gepardec.service;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.logging.Logger;

import java.net.URI;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class RandomCallService {

    Logger Log = Logger.getLogger(RandomCallService.class);

    Random random;

    List<String> serviceCollection;

    public RandomCallService(List<String> serviceCollection, Random random) {
        this.serviceCollection = serviceCollection;
        this.random = random;
    }

    public void callRandomService(int ttl, UUID transactionID) {
        getService(getRandomUrl()).getNextResource(ttl, transactionID);
    }

    public MiddlemanService getService(String url) {
        Log.info("Service: " + url);
        return RestClientBuilder
                .newBuilder()
                .baseUri(URI.create(url))
                .build(MiddlemanService.class);
    }

    public String getRandomUrl() {
        return serviceCollection.get(random.nextInt(serviceCollection.size()));
    }

}