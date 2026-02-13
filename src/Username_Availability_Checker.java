import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class Username_Availability_Checker {

    // username -> userId
    private Map<String, Integer> userMap = new ConcurrentHashMap<>();

    // username -> attempt count
    private Map<String, Integer> attemptCount = new ConcurrentHashMap<>();

    private String mostAttempted = "";
    private int maxAttempts = 0;

    // Constructor (simulate existing users)
    public void UsernameChecker() {
        userMap.put("john_doe", 101);
        userMap.put("admin", 1);
        userMap.put("root", 2);
    }

    // O(1) username availability check
    public boolean checkAvailability(String username) {
        // Track frequency
        int count = attemptCount.getOrDefault(username, 0) + 1;
        attemptCount.put(username, count);

        // Update most attempted
        if (count > maxAttempts) {
            maxAttempts = count;
            mostAttempted = username;
        }

        return !userMap.containsKey(username);
    }

    // Suggest alternative usernames
    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();

        int i = 1;
        while (suggestions.size() < 3) {
            String candidate = username + i;
            if (!userMap.containsKey(candidate)) {
                suggestions.add(candidate);
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
    public String getMostAttempted() {
        return mostAttempted + " (" + maxAttempts + " attempts)";
    }

    // Test Driver
    public static void main(String[] args) {
        Username_Availability_Checker checker = new Username_Availability_Checker();

        System.out.println(checker.Username_Availability_Checker("john_doe"));   // false
        System.out.println(checker.Username_Availability_Checker("jane_smith")); // true

        System.out.println(checker.suggestAlternatives("john_doe"));
        System.out.println(checker.getMostAttempted());
    }
}
