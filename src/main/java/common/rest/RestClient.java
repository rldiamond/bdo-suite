package common.rest;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Client to handle REST requests to external APIs.
 */
public class RestClient {

    /**
     * Static method to force proper building of the client.
     *
     * @param baseUrl The base URL for REST requests.
     * @return Built RestClient.
     */
    public static RestClient build(String baseUrl) {
        return new RestClient(baseUrl);
    }

    private final Client CLIENT = ClientBuilder.newClient();
    private final String BASE_URL;

    /**
     * Default constructor.
     *
     * @param baseUrl The base URL for REST requests.
     */
    private RestClient(String baseUrl) {
        this.BASE_URL = baseUrl;
    }

    /**
     * Perform a GET request at the provided endpoint and return the provided class.
     *
     * @param endpoint    The endpoint to request.
     * @param objectClass The class of the object to return.
     * @param <T>         The object returned.
     * @return Return the provided object retrieved from the REST call.
     */
    public <T> T get(String endpoint, Class<T> objectClass) {
        return CLIENT
                .target(BASE_URL)
                .path(endpoint)
                .request(MediaType.APPLICATION_JSON)
                .get(objectClass);
    }

    /**
     * Perform a GET request at the provided endpoint and return a generic response.
     *
     * @param endpoint The endpoint to request.
     * @return The response from the get call.
     */
    public Response get(String endpoint) {
        return CLIENT
                .target(BASE_URL)
                .path(endpoint)
                .request(MediaType.APPLICATION_ATOM_XML_TYPE)
                .get();
    }

}
