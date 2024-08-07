package utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

public class MyGson {
    private static Gson gson;

    public static Gson getMyGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    // register custom JsonSerializer for LocalDate
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();
        }
        return gson;
    }

}
