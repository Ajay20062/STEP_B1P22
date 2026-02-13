package Week1_Week2;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

class Distributed_Rate_Limiter {

    static class TokenBucket {
        long tokens;
        long lastRefillTime;

        TokenBucket(long tokens, long lastRefillTime) {
            this.tokens = tokens;
            this.lastRefillTime = lastRefillTime;
        }
    }

    private final long MAX_TOKENS = 1000;
    private final long REFILL_INTERVAL = 3600_000;
    private final Map<String, TokenBucket> buckets = new ConcurrentHashMap<>();

    public synchronized void checkRateLimit(String clientId) {
        long now = System.currentTimeMillis();

        buckets.putIfAbsent(clientId, new TokenBucket(MAX_TOKENS, now));
        TokenBucket bucket = buckets.get(clientId);

        if (now - bucket.lastRefillTime >= REFILL_INTERVAL) {
            bucket.tokens = MAX_TOKENS;
            bucket.lastRefillTime = now;
        }

        if (bucket.tokens > 0) {
            bucket.tokens--;
            System.out.println("Allowed (" + bucket.tokens + " requests remaining)");
        } else {
            long retryAfter = (REFILL_INTERVAL - (now - bucket.lastRefillTime)) / 1000;
            System.out.println("Denied (retry after " + retryAfter + "s)");
        }
    }

    public void getRateLimitStatus(String clientId) {
        TokenBucket bucket = buckets.get(clientId);
        if (bucket == null) {
            System.out.println("{used: 0, limit: 1000}");
            return;
        }
        long used = MAX_TOKENS - bucket.tokens;
        long reset = (bucket.lastRefillTime + REFILL_INTERVAL) / 1000;
        System.out.println("{used: " + used + ", limit: 1000, reset: " + reset + "}");
    }

    public static void main(String[] args) {
        Distributed_Rate_Limiter limiter = new Distributed_Rate_Limiter();
        Scanner sc = new Scanner(System.in);

        while (true) {
            String cmd = sc.next();
            if (cmd.equalsIgnoreCase("exit")) break;

            if (cmd.equalsIgnoreCase("check")) {
                String clientId = sc.next();
                limiter.checkRateLimit(clientId);
            }

            if (cmd.equalsIgnoreCase("status")) {
                String clientId = sc.next();
                limiter.getRateLimitStatus(clientId);
            }
        }
        sc.close();
    }
}
