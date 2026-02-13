package Week1_Week2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

class Flash_Sale_Inventory_Manager {

    private final ConcurrentHashMap<String, AtomicInteger> stock = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Queue<Integer>> waitlist = new ConcurrentHashMap<>();

    public Flash_Sale_Inventory_Manager() {
        stock.put("IPHONE15_256GB", new AtomicInteger(100));
        waitlist.put("IPHONE15_256GB", new LinkedList<>());
    }

    public void checkStock(String productId) {
        int available = stock.get(productId).get();
        System.out.println(available + " units available");
    }

    public synchronized void purchaseItem(String productId, int userId) {
        AtomicInteger availableStock = stock.get(productId);

        if (availableStock.get() > 0) {
            int remaining = availableStock.decrementAndGet();
            System.out.println("Success " + remaining + " units remaining");
        } else {
            Queue<Integer> queue = waitlist.get(productId);
            queue.add(userId);
            System.out.println("Added to waiting list position #" + queue.size());
        }
    }

    public static void main(String[] args) {
        Flash_Sale_Inventory_Manager manager = new Flash_Sale_Inventory_Manager();
        Scanner sc = new Scanner(System.in);

        while (true) {
            String cmd = sc.next();

            if (cmd.equalsIgnoreCase("exit")) break;

            if (cmd.equalsIgnoreCase("check")) {
                String product = sc.next();
                manager.checkStock(product);
            }

            if (cmd.equalsIgnoreCase("buy")) {
                String product = sc.next();
                int userId = sc.nextInt();
                manager.purchaseItem(product, userId);
            }
        }
        sc.close();
    }
}
