package server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * The class KeyValueStore is to store key-value pairs on the server side.
 */
public class KeyValueStore {
    private final ConcurrentHashMap<String, String> store = new ConcurrentHashMap<>();

    /**
     * Handles PUT operation.
     *
     * @param key   Key for storage.
     * @param value Value to store.
     * @return Response message.
     */
    public String put(String key, String value) {
        String previousValue = store.putIfAbsent(key, value);
        if (previousValue == null) {
            return "OK";
        } else {
            return "ERROR Key already exists";
        }
    }

    /**
     * Handles GET operation.
     *
     * @param key Key to retrieve.
     * @return Response message.
     */
    public String get(String key) {
        if (store.containsKey(key)) {
            return "OK " + store.get(key);
        } else {
            return "ERROR Key not found";
        }
    }

    /**
     * Handles DELETE operation.
     *
     * @param key Key to delete.
     * @return Response message.
     */
    public String delete(String key) {
        if (store.containsKey(key)) {
            store.remove(key);
            return "OK";
        } else {
            return "ERROR Key not found";
        }
    }

    /**
     * Returns the contents of the key-value store as a string.
     *
     * @return A string representation of the key-value store.
     */
    public String show() {
        if (store.isEmpty()) {
            return "The key-value store is empty.";
        } else {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : store.entrySet()) {
                sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
            }
            return sb.toString();
        }
    }
}
