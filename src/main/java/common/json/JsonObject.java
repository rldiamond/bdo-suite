package common.json;

import com.google.gson.Gson;

/**
 * Abstract class to handle converting objects to JSON.
 */
public abstract class JsonObject {

    private static final Gson GSON = new Gson();

    /**
     * Converts this object to a JSON string.
     * @return A JSON string for this object.
     */
    public String toJson() {
        return GSON.toJson(this);
    }

}
