package com.github.sats17.controller;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sats17.restclient.GeocodingService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/geolocation")
public class GeoLocationController {

	@Inject
	@RestClient
	GeocodingService geocodingService;

	@Inject
	ObjectMapper objectMapper;

	@GET
	@Path("/city/{cityName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGeolocationData(@PathParam("cityName") String cityName) {
		String response = geocodingService.getGeoData(cityName, 10, "en", "json");

		// Convert the response to JsonNode using Jackson
		JsonNode jsonNode;
		try {
			System.out.println(response);
			jsonNode = objectMapper.readTree(response);
		} catch (JsonMappingException e) {
			return Response.status(Response.Status.NOT_FOUND).entity("Something went wrong").build();
		} catch (JsonProcessingException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong in API").build();
		}

		JsonNode results = jsonNode.get("results");
        if (results == null || results.size() == 0) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Did not recieve data from API").build();
        }


		// Fetch the latitude and longitude of the first result
		JsonNode firstResult = results.get(0); // Assumes array response like OpenStreetMap
		double latitude = firstResult.get("latitude").asDouble();
		double longitude = firstResult.get("longitude").asDouble();

		// Create a new response JSON with only lat and lon
		Map<String, Double> resultMap = new HashMap<>();
		resultMap.put("latitude", latitude);
		resultMap.put("longitude", longitude);

		return Response.ok(resultMap).build();
	}
}
