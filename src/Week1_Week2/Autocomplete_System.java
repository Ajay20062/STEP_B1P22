package Week1_Week2;

import java.util.*;

class Autocomplete_System {

    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        Map<String, Integer> freqMap = new HashMap<>();
        boolean isEnd;
    }

    private final TrieNode root = new TrieNode();
    private final Map<String, Integer> globalFreq = new HashMap<>();

    public void insert(String query) {
        globalFreq.put(query, globalFreq.getOrDefault(query, 0) + 1);
        TrieNode node = root;

        for (char c : query.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
            node.freqMap.put(query, globalFreq.get(query));
        }
        node.isEnd = true;
    }

    public void search(String prefix) {
        TrieNode node = root;

        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                System.out.println("No suggestions");
                return;
            }
            node = node.children.get(c);
        }

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());

        pq.addAll(node.freqMap.entrySet());

        int rank = 1;
        while (!pq.isEmpty() && rank <= 10) {
            Map.Entry<String, Integer> e = pq.poll();
            System.out.println(rank + ". " + e.getKey() + " (" + e.getValue() + ")");
            rank++;
        }
    }

    public static void main(String[] args) {
        Autocomplete_System ac = new Autocomplete_System();
        Scanner sc = new Scanner(System.in);

        while (true) {
            String cmd = sc.next();

            if (cmd.equalsIgnoreCase("exit")) break;

            if (cmd.equalsIgnoreCase("add")) {
                sc.nextLine();
                String q = sc.nextLine();
                ac.insert(q);
            }

            if (cmd.equalsIgnoreCase("search")) {
                String prefix = sc.next();
                ac.search(prefix);
            }
        }
        sc.close();
    }
}
