package org.example.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.dtos.PackagingDTO;
import org.example.enums.Category;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class PackagingService {

    static final String BASE_URL ="https://packingapi.cphbusinessapps.dk/packinglist";

    static HttpClient client = HttpClient.newHttpClient();

    public static List<PackagingDTO> getPackaging(Category category) throws IOException, InterruptedException {

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .uri(URI.create(BASE_URL + "/" + category.toString()))
                .GET();

        requestBuilder.header("Accept", "application/json");

        HttpRequest request = requestBuilder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String jsonString = response.body();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        JsonNode rootNode = objectMapper.readTree(jsonString);
        JsonNode packagingNode = rootNode.path("items");


        List<PackagingDTO> packagingList = objectMapper.readValue(packagingNode.toString(), objectMapper.getTypeFactory().constructCollectionType(List.class, PackagingDTO.class));

        return packagingList;

    }

}
