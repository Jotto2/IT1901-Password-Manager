package client;

import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class RestTalker {
    /**
     * OK response value.
     */
    private static final int OK_CODE = 200;
    /**
     * URL to remote server.
     */
    private final String url;
    /**
     * Port to the remote server.
     */
    private final int port;

    /**
     * Constructs a LogClient from a builder.
     *
     * @param serverUrl  server base url.
     * @param serverPort server port.
     */
    public RestTalker() {
        this.url = "http://localhost";
        this.port = 8080;
    }

  public String test() {
    // Create a new HttpClient to /test endpoint
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url + ":" + port + "/api/v1/entries/test"))
        .build();
    // Send the request and get the response
    HttpResponse<String> response = null;
    try {
      response = client.send(request, BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    // Check the response code
    if (response.statusCode() == OK_CODE) {
      return response.body();
    } else {
      return "Error";
    }
  }

  /**
   * Elementary synchronous get request.
   *
   * @param endpoint Where to send the request to.
   * @return The Http response.
   * @throws URISyntaxException If the query entries ruin the query string syntax.
   * @throws InterruptedException If the request was interrupted before retrieving the http
   *         response.
   * @throws ExecutionException If the request completed exceptionally.
   * @throws ServerResponseException If there was an error with the server response.
   */
  private HttpResponse<String> get(final String endpoint)
      throws URISyntaxException, InterruptedException, ExecutionException, ServerResponseException {
    HttpResponse<String> response = this.getAsync(endpoint).get();

    if (response.statusCode() != OK_CODE) {
      throw new ServerResponseException(HttpResponses.getResponseText(response.statusCode()),
          response.statusCode());
    }

    return response;
  }

  /**
   * Elementary asynchronous get request.
   *
   * @param endpoint Where to send the request to.
   * @return The Http response promise.
   * @throws URISyntaxException If the URI syntax is incorrect.
   */
  private CompletableFuture<HttpResponse<String>> getAsync(final String endpoint)
      throws URISyntaxException {
    HttpClient client = HttpClient.newBuilder().build();

    HttpRequest request =
        HttpRequest.newBuilder().GET().uri(new URI(this.url + ":" + this.port + endpoint)).build();

    return client.sendAsync(request, BodyHandlers.ofString());
  }

  public boolean login(String username, String password) {
    return false;
  }

  public Map<String, String> getProfiles(String username) {
    // Get response string
    return null;
  }

  public boolean registerUser(String username, String password) {
    return false;
  }

  public void insertProfile(String username, String email, String password) {

  }

  public boolean userExists(String username) {
    return false;
  }

}
