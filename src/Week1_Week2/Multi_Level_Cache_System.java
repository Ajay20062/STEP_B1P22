package Week1_Week2;

import java.util.*;

class Multi_Level_Cache_System {

    static class LRUCache<K, V> extends LinkedHashMap<K, V> {
        int capacity;

        LRUCache(int capacity) {
            super(capacity, 0.75f, true);
            this.capacity = capacity;
        }

        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > capacity;
        }
    }

    private final LRUCache<String, String> L1 = new LRUCache<>(10000);
    private final LRUCache<String, String> L2 = new LRUCache<>(100000);
    private final Map<String, String> L3 = new HashMap<>();
    private final Map<String, Integer> accessCount = new HashMap<>();

    private int l1Hit = 0, l2Hit = 0, l3Hit = 0;

    public Multi_Level_Cache_System() {
        for (int i = 1; i <= 200000; i++) {
            L3.put("video_" + i, "VideoData_" + i);
        }
    }

    public String getVideo(String videoId) {
        long start = System.nanoTime();

        if (L1.containsKey(videoId)) {
            l1Hit++;
            print("L1 Cache HIT", start);
            return L1.get(videoId);
        }

        if (L2.containsKey(videoId)) {
            l2Hit++;
            String data = L2.get(videoId);
            promoteToL1(videoId, data);
            print("L2 Cache HIT", start);
            return data;
        }

        String data = L3.get(videoId);
        l3Hit++;
        accessCount.put(videoId, accessCount.getOrDefault(videoId, 0) + 1);
        L2.put(videoId, data);
        print("L3 Database HIT", start);
        return data;
    }

    private void promoteToL1(String videoId, String data) {
        accessCount.put(videoId, accessCount.getOrDefault(videoId, 0) + 1);
        if (accessCount.get(videoId) >= 2) {
            L1.put(videoId, data);
        }
    }

    private void print(String level, long start) {
        double time = (System.nanoTime() - start) / 1_000_000.0;
        System.out.printf("%s (%.2fms)\n", level, time);
    }

    public void getStatistics() {
        int total = l1Hit + l2Hit + l3Hit;
        System.out.printf("L1 Hit Rate: %.2f%%\n", (l1Hit * 100.0) / total);
        System.out.printf("L2 Hit Rate: %.2f%%\n", (l2Hit * 100.0) / total);
        System.out.printf("L3 Hit Rate: %.2f%%\n", (l3Hit * 100.0) / total);
        System.out.printf("Overall Hit Rate: %.2f%%\n", ((l1Hit + l2Hit) * 100.0) / total);
    }

    public static void main(String[] args) {
        Multi_Level_Cache_System cache = new Multi_Level_Cache_System();
        Scanner sc = new Scanner(System.in);

        while (true) {
            String cmd = sc.next();
            if (cmd.equalsIgnoreCase("exit")) break;

            if (cmd.equalsIgnoreCase("get")) {
                String video = sc.next();
                cache.getVideo(video);
            }

            if (cmd.equalsIgnoreCase("stats")) {
                cache.getStatistics();
            }
        }
        sc.close();
    }
}
