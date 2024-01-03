import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {

        Path path = Path.of("src/main/java/merged.json");

        FileReader reader1 = new FileReader(String.valueOf("src/main/java/1.txt"));

        FileReader reader2 = new FileReader(String.valueOf("src/main/java/2.txt"));

        FileWriter writer = new FileWriter(String.valueOf(path));

        JsonObject firstJsonObject = JsonObject.readFrom(reader1);

        JsonObject secondJsonObject = JsonObject.readFrom(reader2);

        mergeObjects(firstJsonObject, secondJsonObject);

        firstJsonObject.writeTo(writer);

        writer.close();

    }

    private static void mergeObjects(JsonObject firstJsonObject, JsonObject secondJsonObject) {
        for (String key : secondJsonObject.names()) {
            JsonValue secondInner = secondJsonObject.get(key);

            if (secondInner.isObject()) {
                JsonObject firstInner = firstJsonObject.get(key).isObject() ? firstJsonObject.get(key).asObject() : Json.object();
                mergeObjects(firstInner, secondInner.asObject());
                firstJsonObject.set(key, firstInner);
            } else {
                firstJsonObject.set(key, secondInner);
            }
        }
    }

}
