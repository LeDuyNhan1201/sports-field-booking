package org.jakartaee5g23.sportsfieldbooking.repositories;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.UrlEncodedContent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GoogleAuthorizationCodeTokenRequest {

    HttpTransport transport;

    String tokenUrl;

    String clientId;

    String clientSecret;

    String code;

    String redirectUri;

    public String getAccessToken() throws Exception {
        HttpRequestFactory requestFactory = transport.createRequestFactory();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("client_id", clientId);
        parameters.put("client_secret", clientSecret);
        parameters.put("code", code);
        parameters.put("redirect_uri", redirectUri);
        parameters.put("grant_type", "authorization_code");

        HttpRequest request = requestFactory.buildPostRequest(
                new GenericUrl(tokenUrl),
                new UrlEncodedContent(parameters)
        );

        String response = request.execute().parseAsString();

        // Lấy access_token từ JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        return jsonNode.get("access_token").asText(); // Trả về giá trị access_token
    }
}
