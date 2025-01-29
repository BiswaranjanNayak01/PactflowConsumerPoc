package com.example;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "ProviderBiswa", pactVersion = PactSpecVersion.V3)
public class ConsumerPactTest {

    @Pact(consumer = "ConsumerBiswa")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .given("test GET")
                .uponReceiving("GET REQUEST")
                .path("/data")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body("{\"data\": \"response\"}")
                .toPact();
    }

    @PactTestFor(pactMethod = "createPact")
    @Test
    public void testPact(MockServer mockServer) {
        RestAssured.baseURI = mockServer.getUrl();

        given()
                .when()
                .get("/data")
                .then()
                .statusCode(200)
                .body("data", equalTo("response"));
    }
}
