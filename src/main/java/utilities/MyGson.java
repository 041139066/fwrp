package utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

/**
 * The MyGson class provides a singleton instance of a Gson object
 * configured with a custom serializer for LocalDateTime.
 */
public class MyGson {
    private static Gson gson;

    /**
     * Returns a singleton instance of Gson configured with a custom
     * JsonSerializer for LocalDateTime.
     *
     * @return The configured Gson instance.
     */
    public static Gson getMyGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    // register custom JsonSerializer for LocalDateTime
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();
        }
        return gson;
    }
}

