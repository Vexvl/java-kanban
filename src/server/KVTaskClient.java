package server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final String url;
    private final String api_token;
    private final HttpClient taskClient;

    public KVTaskClient(String str) throws IOException, InterruptedException {
        url = str;
        taskClient = HttpClient.newHttpClient();
        URI uri = URI.create(url + "/register");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = taskClient.send(request, handler);
        api_token = response.body();
    }

    public void put(String key, String json) throws IOException, InterruptedException {
        URI uri = URI.create(url + "/save/" + key + "?API_TOKEN=" + api_token);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = taskClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public String load(String key) throws IOException, InterruptedException {
        URI uri = URI.create(url + "/load/" + key + "?API_TOKEN=" + api_token);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = taskClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}