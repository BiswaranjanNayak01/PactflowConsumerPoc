package com.example;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class) // Extend with Pact's JUnit 5 extension
@PactTestFor(providerName = "UserService", port = "8080") // Specify provider and port
public class UserServiceClientTest {

    @Pact(consumer = "UserServiceClient") // Define the Pact contract
    public V4Pact createPact(PactDslWithProvider builder) {
        return builder
                .given("User 1 exists")
                .uponReceiving("A request for user 1")
                .path("/user/1")
                .method("GET")
                .willRespondWith()
                .status(HttpStatus.SC_OK)
                .body("{\"id\": 1, \"name\": \"John Doe\"}")
                .toPact(V4Pact.class); // Specify V4Pact.class
    }

    @Test
    @PactTestFor(pactMethod = "createPact") // Link the test to the Pact contract
    void testGetUser() throws Exception {
        UserServiceClient client = new UserServiceClient("http://localhost:8080");
        String response = client.getUser(1);
        assertEquals("{\"id\": 1, \"name\": \"John Doe\"}", response);
    }
}