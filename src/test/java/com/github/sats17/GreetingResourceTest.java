package com.github.sats17;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GreetingResourceTest {
    @Test
    void testGeoEndpoint() {
        given()
          .when().get("/api/geolocation/city/mumbai")
          .then()
             .statusCode(200);
    }

}