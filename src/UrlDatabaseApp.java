
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class UrlDatabase {
    private Map<String, UrlEntry> urlMap;

    public UrlDatabase() {
        this.urlMap = new HashMap<>();
    }

    public String storeUrl(String url) {
        String shortKey = generateShortKey();
        urlMap.put(shortKey, new UrlEntry(url));
        return shortKey;
    }

    public String getUrl(String shortKey) {
        UrlEntry entry = urlMap.get(shortKey);
        if (entry != null) {
            entry.incrementCount();
            return entry.getUrl();
        } else {
            return null;
        }
    }

    public int getCount(String shortKey) {
        UrlEntry entry = urlMap.get(shortKey);
        return (entry != null) ? entry.getCount() : -1;
    }

    public String listUrls() {
        StringBuilder result = new StringBuilder("{\n");
        for (Map.Entry<String, UrlEntry> entry : urlMap.entrySet()) {
            result.append(String.format("  \"%s\": %d,\n", entry.getValue().getUrl(), entry.getValue().getCount()));
        }
        if (!urlMap.isEmpty()) {
            result.delete(result.length() - 2, result.length()); // Remove the trailing comma and newline
        }
        result.append("}\n");
        return result.toString();
    }

    private String generateShortKey() {
        // Logic to generate a unique short key
        return String.valueOf(urlMap.size() + 1);
    }

    private static class UrlEntry {
        private String url;
        private int count;

        public UrlEntry(String url) {
            this.url = url;
            this.count = 0;
        }

        public String getUrl() {
            return url;
        }

        public int getCount() {
            return count;
        }

        public void incrementCount() {
            count++;
        }
    }
}

public class UrlDatabaseApp {
    public static void main(String[] args) {
        UrlDatabase urlDatabase = new UrlDatabase();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the URL Database!");
        System.out.println("Enter commands: storeurl, get, count, list, exit");

        while (true) {
            System.out.print("> ");
            String command = scanner.next();

            switch (command) {
                case "storeurl":
                    String urlToStore = scanner.next();
                    String shortKey = urlDatabase.storeUrl(urlToStore);
                    System.out.println("Stored. Short key: " + shortKey);
                    break;

                case "get":
                    String shortKeyToGet = scanner.next();
                    String retrievedUrl = urlDatabase.getUrl(shortKeyToGet);
                    System.out.println("Retrieved URL: " + retrievedUrl);
                    break;

                case "count":
                    String shortKeyToCount = scanner.next();
                    int count = urlDatabase.getCount(shortKeyToCount);
                    System.out.println("Usage count: " + count);
                    break;

                case "list":
                    System.out.println(urlDatabase.listUrls());
                    break;

                case "exit":
                    System.out.println("Exiting program. Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid command. Try again.");
            }
        }
    }
}
