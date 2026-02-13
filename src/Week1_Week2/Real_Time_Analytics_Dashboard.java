package Week1_Week2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class Real_Time_Analytics_Dashboard {

    private final Map<String, Integer> pageViews = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> uniqueVisitors = new ConcurrentHashMap<>();
    private final Map<String, Integer> trafficSources = new ConcurrentHashMap<>();

    public synchronized void processEvent(String url, String userId, String source) {
        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);

        trafficSources.put(source, trafficSources.getOrDefault(source, 0) + 1);
    }

    public void getDashboard() {
        System.out.println("\nTop Pages:");

        PriorityQueue<String> pq = new PriorityQueue<>(
                (a, b) -> pageViews.get(b) - pageViews.get(a)
        );
        pq.addAll(pageViews.keySet());

        int rank = 1;
        while (!pq.isEmpty() && rank <= 10) {
            String page = pq.poll();
            System.out.println(rank + ". " + page + " - " +
                    pageViews.get(page) + " views (" +
                    uniqueVisitors.get(page).size() + " unique)");
            rank++;
        }

        System.out.println("\nTraffic Sources:");
        int total = trafficSources.values().stream().mapToInt(Integer::intValue).sum();

        for (String src : trafficSources.keySet()) {
            double percent = (trafficSources.get(src) * 100.0) / total;
            System.out.printf("%s: %.0f%%\n", src, percent);
        }
    }

    public static void main(String[] args) throws Exception {
        Real_Time_Analytics_Dashboard dashboard = new Real_Time_Analytics_Dashboard();
        Scanner sc = new Scanner(System.in);

        while (true) {
            String cmd = sc.next();

            if (cmd.equalsIgnoreCase("exit")) break;

            if (cmd.equalsIgnoreCase("event")) {
                String url = sc.next();
                String user = sc.next();
                String source = sc.next();
                dashboard.processEvent(url, user, source);
            }

            if (cmd.equalsIgnoreCase("dashboard")) {
                dashboard.getDashboard();
            }
        }
        sc.close();
    }
}
