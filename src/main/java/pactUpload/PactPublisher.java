package pactUpload;

import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PactPublisher {
    public static void main(String[] args) {
        String brokerUrl = "https://nayakorg.pactflow.io";
        String brokerToken = "Mcwdtspm9oesFkwmAdTa2w";
        String pactFilePath = "pacts/ConsumerGithubBiswa-ProviderGithubBiswa.json"; // Path to your Pact file
        String providerName = "ProviderGithubBiswa"; // Your provider name
        String consumerName = "ConsumerGithubBiswa"; // Your consumer name
        String consumerAppVersion = "1.0.3"; // Your consumer app version

        String url = brokerUrl + "/pacts/provider/" + providerName + "/consumer/" + consumerName + "/version/" + consumerAppVersion;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPut uploadFile = new HttpPut(url);
            uploadFile.setHeader("Authorization", "Bearer " + brokerToken);
            uploadFile.setHeader("Content-Type", "application/json");

            // Read the contents of the Pact file
            String pactFileContent = new String(Files.readAllBytes(Paths.get(pactFilePath)));

            // Set the JSON payload
            StringEntity entity = new StringEntity(pactFileContent);
            uploadFile.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(uploadFile)) {
                System.out.println("Response Status: " + response.getCode());
                System.out.println("Response Body: " + EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}