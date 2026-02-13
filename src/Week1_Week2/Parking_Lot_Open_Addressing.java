package Week1_Week2;

import java.util.Scanner;

class Parking_Lot_Open_Addressing {

    static class Slot {
        String plate;
        long entryTime;
        boolean occupied;
    }

    private final int SIZE = 500;
    private final Slot[] table = new Slot[SIZE];
    private int occupiedCount = 0;
    private int totalProbes = 0;

    public Parking_Lot_Open_Addressing() {
        for (int i = 0; i < SIZE; i++) table[i] = new Slot();
    }

    private int hash(String plate) {
        return Math.abs(plate.hashCode()) % SIZE;
    }

    public void parkVehicle(String plate) {
        int idx = hash(plate);
        int probes = 0;

        while (table[idx].occupied) {
            idx = (idx + 1) % SIZE;
            probes++;
        }

        table[idx].plate = plate;
        table[idx].entryTime = System.currentTimeMillis();
        table[idx].occupied = true;
        occupiedCount++;
        totalProbes += probes;

        System.out.println("Assigned spot #" + idx + " (" + probes + " probes)");
    }

    public void exitVehicle(String plate) {
        int idx = hash(plate);

        while (table[idx].occupied) {
            if (table[idx].plate.equals(plate)) {
                long durationMs = System.currentTimeMillis() - table[idx].entryTime;
                double hours = durationMs / 3600000.0;
                double fee = Math.ceil(hours) * 5;

                table[idx].occupied = false;
                occupiedCount--;

                System.out.printf("Spot #%d freed Duration: %.2fh Fee: $%.2f\n",
                        idx, hours, fee);
                return;
            }
            idx = (idx + 1) % SIZE;
        }
        System.out.println("Vehicle not found");
    }

    public void getStatistics() {
        double occupancy = (occupiedCount * 100.0) / SIZE;
        double avgProbes = occupiedCount == 0 ? 0 : (double) totalProbes / occupiedCount;

        System.out.printf("Occupancy: %.2f%% Avg Probes: %.2f\n", occupancy, avgProbes);
    }

    public static void main(String[] args) {
        Parking_Lot_Open_Addressing lot = new Parking_Lot_Open_Addressing();
        Scanner sc = new Scanner(System.in);

        while (true) {
            String cmd = sc.next();
            if (cmd.equalsIgnoreCase("exit")) break;

            if (cmd.equalsIgnoreCase("park")) {
                String plate = sc.next();
                lot.parkVehicle(plate);
            }

            if (cmd.equalsIgnoreCase("leave")) {
                String plate = sc.next();
                lot.exitVehicle(plate);
            }

            if (cmd.equalsIgnoreCase("stats")) {
                lot.getStatistics();
            }
        }
        sc.close();
    }
}
