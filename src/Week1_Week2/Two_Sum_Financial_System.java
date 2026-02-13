package Week1_Week2;

import java.util.*;

class Two_Sum_Financial_System {

    static class Transaction {
        int id;
        int amount;
        String merchant;
        String account;
        long time;

        Transaction(int id, int amount, String merchant, String account, long time) {
            this.id = id;
            this.amount = amount;
            this.merchant = merchant;
            this.account = account;
            this.time = time;
        }
    }

    private final List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(int id, int amount, String merchant, String account, long time) {
        transactions.add(new Transaction(id, amount, merchant, account, time));
    }

    public void findTwoSum(int target) {
        Map<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {
            int need = target - t.amount;
            if (map.containsKey(need)) {
                System.out.println("(" + map.get(need).id + ", " + t.id + ")");
                return;
            }
            map.put(t.amount, t);
        }
        System.out.println("No pair found");
    }

    public void findTwoSumWithTime(int target, long windowMs) {
        Map<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {
            int need = target - t.amount;
            if (map.containsKey(need)) {
                if (Math.abs(t.time - map.get(need).time) <= windowMs) {
                    System.out.println("(" + map.get(need).id + ", " + t.id + ")");
                    return;
                }
            }
            map.put(t.amount, t);
        }
        System.out.println("No pair in time window");
    }

    public void detectDuplicates() {
        Map<String, List<Transaction>> map = new HashMap<>();

        for (Transaction t : transactions) {
            String key = t.amount + "-" + t.merchant;
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t);
        }

        for (String key : map.keySet()) {
            Set<String> accounts = new HashSet<>();
            for (Transaction t : map.get(key)) accounts.add(t.account);
            if (accounts.size() > 1) {
                System.out.println(key + " accounts " + accounts);
            }
        }
    }

    public void findKSum(int k, int target) {
        List<Integer> path = new ArrayList<>();
        dfs(0, k, target, path);
    }

    private void dfs(int idx, int k, int target, List<Integer> path) {
        if (k == 0 && target == 0) {
            System.out.println(path);
            return;
        }
        if (k == 0 || idx == transactions.size()) return;

        path.add(transactions.get(idx).id);
        dfs(idx + 1, k - 1, target - transactions.get(idx).amount, path);
        path.remove(path.size() - 1);

        dfs(idx + 1, k, target, path);
    }

    public static void main(String[] args) {
        Two_Sum_Financial_System sys = new Two_Sum_Financial_System();
        Scanner sc = new Scanner(System.in);

        while (true) {
            String cmd = sc.next();
            if (cmd.equalsIgnoreCase("exit")) break;

            if (cmd.equalsIgnoreCase("add")) {
                int id = sc.nextInt();
                int amt = sc.nextInt();
                String merch = sc.next();
                String acc = sc.next();
                long time = sc.nextLong();
                sys.addTransaction(id, amt, merch, acc, time);
            }

            if (cmd.equalsIgnoreCase("two")) {
                int target = sc.nextInt();
                sys.findTwoSum(target);
            }

            if (cmd.equalsIgnoreCase("twotime")) {
                int target = sc.nextInt();
                long window = sc.nextLong();
                sys.findTwoSumWithTime(target, window);
            }

            if (cmd.equalsIgnoreCase("dup")) {
                sys.detectDuplicates();
            }

            if (cmd.equalsIgnoreCase("ksum")) {
                int k = sc.nextInt();
                int target = sc.nextInt();
                sys.findKSum(k, target);
            }
        }
        sc.close();
    }
}
