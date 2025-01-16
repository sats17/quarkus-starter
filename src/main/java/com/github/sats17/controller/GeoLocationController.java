package com.github.sats17.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.github.sats17.service.LocationDBService;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
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
	LocationDBService locationDBService;

	@Inject
	JsonWebToken jwt;

	@Inject
	ObjectMapper objectMapper;

	@GET
	@Path("/token")
	@Produces(MediaType.TEXT_PLAIN)
	public String getToken() {
		String token =
				Jwt.issuer("https://example.com/issuer")
						.upn("jdoe@quarkus.io")
						.groups(new HashSet<>(Arrays.asList("User", "Admin")))
						.claim(Claims.birthdate.name(), "2001-07-13")
						.sign();
//		JwtClaimsBuilder builder1 = Jwt.claims();
//		String token = builder1.claim("customClaim", "custom-value").issuer("https://issuer.org").build();
		System.out.println(token);
		return token;
	}

	@GET
	@Path("/city/{cityName}")
	@Produces(MediaType.APPLICATION_JSON)
	//@RolesAllowed({ "User", "Admin" }) uncomment this if jwt validation required
	public Response getGeolocationData(@PathParam("cityName") String cityName, @Context SecurityContext securityContext) {
		//System.out.println(securityContext.isSecure());
		System.out.println(locationDBService.getAll().toString());
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
