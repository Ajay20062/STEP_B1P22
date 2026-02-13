package Week1_Week2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Username_Availability_Checker {

    // username -> userId (existing users)
    private static final Map<String, Integer> userMap = new ConcurrentHashMap<>();

    // username -> number of attempts
    private static final Map<String, Integer> attemptFrequency = new ConcurrentHashMap<>();

    private static String mostAttempted = "";
    private static int maxAttempts = 0;

    // Initialize with some existing usernames
    static {
        userMap.put("john_doe", 101);
        userMap.put("admin", 1);
        userMap.put("root", 2);
    }

    // O(1) availability check
    public static boolean checkAvailability(String username) {
        // Track attempt frequency
        int count = attemptFrequency.getOrDefault(username, 0) + 1;
        attemptFrequency.put(username, count);

        // Track most attempted username
        if (count > maxAttempts) {
            maxAttempts = count;
            mostAttempted = username;
        }

        return !userMap.containsKey(username);
    }

    // Suggest alternative usernames
    public static List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();

        int i = 1;
        while (suggestions.size() < 3) {
            String newName = username + i;
            if (!userMap.containsKey(newName)) {
                suggestions.add(newName);
            }
            i++;
        }

        // Replace underscore with dot
        String dotVersion = username.replace("_", ".");
        if (!userMap.containsKey(dotVersion)) {
            suggestions.add(dotVersion);
        }

        return suggestions;
    }

    // Get most attempted username
    public static String getMostAttempted() {
        return mostAttempted + " (" + maxAttempts + " attempts)";
    }

    // Main method (test cases)
    public static void main(String[] args) {

        System.out.println("checkAvailability(\"john_doe\") → "
                + checkAvailability("john_doe"));

        System.out.println("checkAvailability(\"jane_smith\") → "
                + checkAvailability("jane_smith"));

        System.out.println("suggestAlternatives(\"john_doe\") → "
                + suggestAlternatives("john_doe"));

        System.out.println("Most Attempted Username → "
                + getMostAttempted());
    }
}
