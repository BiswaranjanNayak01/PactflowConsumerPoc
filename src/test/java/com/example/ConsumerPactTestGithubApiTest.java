package com.example;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerPactTestGithubApiTest {

    @Pact(consumer = "ConsumerGithubBiswa",provider = "ProviderGithubBiswa")
    public V4Pact createGithubPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .given("test github GET")
                .uponReceiving("GET REQUEST github")
                .path("/users/BiswaranjanNayak01")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body("{\n" +
                        "    \"login\": \"BiswaranjanNayak01\",\n" +
                        "    \"id\": 120440057,\n" +
                        "    \"node_id\": \"U_kgDOBy3E-Q\",\n" +
                        "    \"avatar_url\": \"https://avatars.githubusercontent.com/u/120440057?v=4\",\n" +
                        "    \"gravatar_id\": \"\",\n" +
                        "    \"url\": \"https://api.github.com/users/BiswaranjanNayak01\",\n" +
                        "    \"html_url\": \"https://github.com/BiswaranjanNayak01\",\n" +
                        "    \"followers_url\": \"https://api.github.com/users/BiswaranjanNayak01/followers\",\n" +
                        "    \"following_url\": \"https://api.github.com/users/BiswaranjanNayak01/following{/other_user}\",\n" +
                        "    \"gists_url\": \"https://api.github.com/users/BiswaranjanNayak01/gists{/gist_id}\",\n" +
                        "    \"starred_url\": \"https://api.github.com/users/BiswaranjanNayak01/starred{/owner}{/repo}\",\n" +
                        "    \"subscriptions_url\": \"https://api.github.com/users/BiswaranjanNayak01/subscriptions\",\n" +
                        "    \"organizations_url\": \"https://api.github.com/users/BiswaranjanNayak01/orgs\",\n" +
                        "    \"repos_url\": \"https://api.github.com/users/BiswaranjanNayak01/repos\",\n" +
                        "    \"events_url\": \"https://api.github.com/users/BiswaranjanNayak01/events{/privacy}\",\n" +
                        "    \"received_events_url\": \"https://api.github.com/users/BiswaranjanNayak01/received_events\",\n" +
                        "    \"type\": \"User\",\n" +
                        "    \"user_view_type\": \"public\",\n" +
                        "    \"site_admin\": false,\n" +
                        "    \"name\": null,\n" +
                        "    \"company\": null,\n" +
                        "    \"blog\": \"\",\n" +
                        "    \"location\": null,\n" +
                        "    \"email\": null,\n" +
                        "    \"hireable\": null,\n" +
                        "    \"bio\": null,\n" +
                        "    \"twitter_username\": null,\n" +
                        "    \"public_repos\": 11,\n" +
                        "    \"public_gists\": 0,\n" +
                        "    \"followers\": 0,\n" +
                        "    \"following\": 0,\n" +
                        "    \"created_at\": \"2022-12-13T04:35:24Z\",\n" +
                        "    \"updated_at\": \"2024-12-29T17:58:23Z\"\n" +
                        "}")
                .toPact(V4Pact.class);
    }

    @PactTestFor(pactMethod = "createGithubPact")
    @Test
    void testGithubPact(MockServer mockServer) {
        RestAssured.baseURI = mockServer.getUrl();

        given()
                .when()
                .get("/users/BiswaranjanNayak01")
                .then()
                .statusCode(200)
                .body("login", equalTo("BiswaranjanNayak01"))
                .body("id", equalTo(120440057))
                .body("public_repos", equalTo(11));
    }

    //@PactTestFor(providerName = "Provider Github Biswa", pactVersion = PactSpecVersion.V3)
//    @PactTestFor(providerName = "ProviderBiswa")

}
