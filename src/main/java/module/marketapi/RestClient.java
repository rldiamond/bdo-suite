package module.marketapi;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;

class RestClient {

    public static RestClient build(String baseUrl) {
        return new RestClient(baseUrl);
    }

    private final Client CLIENT = ClientBuilder.newClient();
    private final String BASE_URL;

    private RestClient(String baseUrl) {
        this.BASE_URL = baseUrl;
    }

    public <T> T get(String endpoint, Class<T> objectClass) {
        return CLIENT
                .target(BASE_URL)
                .path(endpoint)
                .request(MediaType.APPLICATION_JSON)
                .get(objectClass);
    }

}
