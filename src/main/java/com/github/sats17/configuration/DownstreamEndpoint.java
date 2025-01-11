package com.github.sats17.configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

import java.net.http.HttpRequest.Builder;

public class DownstreamEndpoint {

	private final HttpClient httpClient;
	private final Map<String, String> configProperties;

	public DownstreamEndpoint(Map<String, String> configProperties, HttpClient httpClient) {
		this.httpClient = httpClient;
		this.configProperties = configProperties;
	}

	/**
	 * @return the webClient
	 */
	public HttpClient getHttpClient() {
		return httpClient;
	}

	public HttpResponse<String> get(String baseUri, Map<String, String> headers, Map<String, String> queryParams,
			Duration timeout) throws Exception {

		URI uri = buildUriWithParams(baseUri, queryParams);

		Builder requestBuilder = HttpRequest.newBuilder().uri(uri).timeout(timeout).GET();

		if (headers != null) {
			headers.forEach(requestBuilder::header);
		}

		HttpRequest request = requestBuilder.build();

		return this.httpClient.send(request, BodyHandlers.ofString());
	}

	private static URI buildUriWithParams(String baseUri, Map<String, String> queryParams) throws URISyntaxException {
		if (queryParams == null || queryParams.isEmpty()) {
			return new URI(baseUri);
		}

		String queryString = queryParams.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue())
				.collect(Collectors.joining("&"));

		return new URI(baseUri + "?" + queryString);
	}

	public Map<String, String> getConfigProperties() {
		return configProperties;
	}

}