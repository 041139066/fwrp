package utilities;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The LocalDateTimeAdapter class implements custom serialization and deserialization
 * of LocalDateTime objects for Gson. It formats the date and time as a string in
 * the pattern "yyyy/MM/dd hh:mm:ss".
 */
public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");

    /**
     * Serializes a LocalDateTime object into its JSON representation as a formatted string.
     *
     * @param localDateTime The LocalDateTime object to serialize.
     * @param type The type of the object being serialized.
     * @param jsonSerializationContext The context for serialization.
     * @return A JsonElement representing the serialized LocalDateTime as a string.
     */
    @Override
    public JsonElement serialize(LocalDateTime localDateTime,
                                 Type type,
                                 JsonSerializationContext jsonSerializationContext) {

        return new JsonPrimitive(localDateTime.format(formatter));
    }

    /**
     * Deserializes a JSON element into a LocalDateTime object.
     * The JSON element is expected to contain a string formatted as "yyyy/MM/dd hh:mm:ss".
     *
     * @param jsonElement The JSON element to deserialize.
     * @param type The type of the object being deserialized.
     * @param jsonDeserializationContext The context for deserialization.
     * @return The deserialized LocalDateTime object.
     * @throws JsonParseException if the JSON element cannot be parsed into a LocalDateTime.
     */
    @Override
    public LocalDateTime deserialize(JsonElement jsonElement,
                                     Type type,
                                     JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        return LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(), formatter);
    }

}
