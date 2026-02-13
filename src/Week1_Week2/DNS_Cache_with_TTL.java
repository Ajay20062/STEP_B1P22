package Week1_Week2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

class DNS_Cache_with_TTL {

    static class DNSEntry {
        String ip;
        long expiryTime;

        DNSEntry(String ip, long ttlSeconds) {
            this.ip = ip;
            this.expiryTime = System.currentTimeMillis() + ttlSeconds * 1000;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }

    private Map<String, DNSEntry> cache = new HashMap<>();

    public String resolve(String domain) {
        if (cache.containsKey(domain)) {
            DNSEntry entry = cache.get(domain);
            if (!entry.isExpired()) {
                System.out.println("HIT " + entry.ip);
                return entry.ip;
            } else {
                cache.remove(domain);
            }
        }

        String ip = queryUpstreamDNS();
        cache.put(domain, new DNSEntry(ip, 300));
        System.out.println("MISS " + ip);
        return ip;
    }

    private String queryUpstreamDNS() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {}
        return "172.217.14." + new Random().nextInt(255);
    }

    public static void main(String[] args) {
        DNS_Cache_with_TTL dnsCache = new DNS_Cache_with_TTL();
        Scanner sc = new Scanner(System.in);

        while (true) {
            String domain = sc.nextLine();
            if (domain.equalsIgnoreCase("exit")) break;
            dnsCache.resolve(domain);
        }
        sc.close();
    }
}
