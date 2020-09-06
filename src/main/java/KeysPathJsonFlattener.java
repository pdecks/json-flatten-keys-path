import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.*;

/**
 * Walk the JSON object until we hit a primitive, tracking the path so far to form the keys-path for each primitive
 * in the JSON object, and store the path-primitive pair in a map. We'll create the flattened JsonObject from the
 * key-value pairs in the map.
 *
 * Uses GSON {@link}https://github.com/google/gson/blob/master/UserGuide.md to parse string input to a JSON Object, to
 * walk the resulting JSON Object, and to convert the map to the final flattened JSON Object.
 */
public class KeysPathJsonFlattener {
    // for storing pairs of path-primitive when we reach a primitive in a (nested) object
    public Map<String, JsonPrimitive> flattenedPairs;

    public KeysPathJsonFlattener() {
        flattenedPairs = new HashMap<>();
    }

    // Takes the JSON Object parsed from the input string and generates the flattened JSON Object.
    public JsonObject generateFlatJsonObject(JsonObject origJsonObject) {
        if (origJsonObject == null || origJsonObject.entrySet() == null) return null;

        // populate the map of path-to-primitive pairs
        for (Map.Entry<String, JsonElement> entry : origJsonObject.entrySet()) {
            findPath(entry.getKey(), entry.getValue());
        }

        // convert these pairs to the desired flattened JSON Object
        JsonObject flatObject = new JsonObject();
        for (Map.Entry<String, JsonPrimitive> entry : flattenedPairs.entrySet()) {
            flatObject.add(entry.getKey(), entry.getValue());
        }

        return flatObject;
    }

    // recursive method for walking JSON tree to all leaf nodes (primitives)
    private void findPath(String pathSoFar, JsonElement element) {
        // found the end of a path
        if (element.isJsonPrimitive()) {
            flattenedPairs.put(pathSoFar, element.getAsJsonPrimitive());
            return;
        }
        // otherwise descend into child elements, updating path
        for (Map.Entry<String, JsonElement> entry: element.getAsJsonObject().entrySet()) {
            findPath(pathSoFar + "." + entry.getKey(), entry.getValue());
        }
    }

    // accepts input from stdin
    public JsonObject parseStdinAsJsonObject() {
        JsonObject root = null;
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        try (JsonReader jsonParser = new JsonReader(inputStreamReader)) {
            root = Streams.parse(jsonParser).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return root;
    }

    // accepts input from file
    public JsonObject parseFileAsJsonObject(String inputFile) {
        JsonObject root = null;
        try (JsonReader jsonParser = new JsonReader(new FileReader(inputFile))) {
            root = Streams.parse(jsonParser).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return root;
    }

    public static String getPrettyPrintFlatJson(JsonObject flattenedJsonObject) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(flattenedJsonObject);
    }

    private void writeResultToFile(String flatJson) {
        File result = new File(System.getProperty("user.dir") + "/output.json");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(result))) {
            bw.write(flatJson);
            System.out.println("File written successfully to " + System.getProperty("user.dir") + "/output.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        KeysPathJsonFlattener flattener = new KeysPathJsonFlattener();
        JsonObject inputJson;
        if (args.length == 0) {
            inputJson = flattener.parseStdinAsJsonObject(); // created with input from stdin
        } else if (args.length == 2 && "-file".equals(args[0])) {
            inputJson = flattener.parseFileAsJsonObject(args[1]);
        }
        else {
            throw new IllegalArgumentException("When reading from a file with '-file' you must specify the absolute file path.");
        }

        JsonObject flatJson = flattener.generateFlatJsonObject(inputJson);
        String flatString = getPrettyPrintFlatJson(flatJson);

        // write to output
        flattener.writeResultToFile(flatString);
    }
}
