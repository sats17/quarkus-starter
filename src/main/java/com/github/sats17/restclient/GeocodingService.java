package com.github.sats17.restclient;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/v1/search")
@RegisterRestClient(configKey = "geocoding-api")
public interface GeocodingService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    String getGeoData(@QueryParam("name") String city,
                      @QueryParam("count") int count,
                      @QueryParam("language") String language,
                      @QueryParam("format") String format);
}
