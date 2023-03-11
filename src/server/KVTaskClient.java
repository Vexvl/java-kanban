package server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final String URL;
    private final String API_TOKEN;
    private final HttpClient taskClient;

    public KVTaskClient(String str) throws IOException, InterruptedException {
        URL = str;
        taskClient = HttpClient.newHttpClient();
        URI uri = URI.create(URL + "/register");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = taskClient.send(request, handler);
        API_TOKEN = response.body();
    }

    public void put(String key, String json) throws IOException, InterruptedException {
        URI uri = URI.create(URL + "/save/" + key + "?API_TOKEN=" + API_TOKEN);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = taskClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public String load(String key) throws IOException, InterruptedException {
        URI uri = URI.create(URL + "/load/" + key + "?API_TOKEN=" + API_TOKEN);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = taskClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}