// Design and implement a sorting algorithm using Merge Sort to efficiently arrange customer 
// orders based on their timestamps. The solution should handle a large dataset (up to 1 million 
// orders) with minimal computational overhead. Additionally, analyze the time complexity and 
// compare it with traditional sorting techniques.

//Sohan Patil - 123B5F139

import java.text.SimpleDateFormat;
import java.util.*;

class OrderRecord {
    String code;
    long time;

    public OrderRecord(String code, long time) {
        this.code = code;
        this.time = time;
    }
}

public class OrderSorting {

    private static void generateOrders(List<OrderRecord> list, int total) {
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.JUNE, 24, 12, 0, 0);
        long baseTime = cal.getTimeInMillis();
        Random rng = new Random();

        for (int i = 0; i < total; i++) {
            int ranMin = rng.nextInt(100000);
            long ts = baseTime + (ranMin * 60L * 1000L);
            list.add(new OrderRecord("ORD" + (i + 1), ts));
        }
    }

    private static void merge(List<OrderRecord> list, int left, int mid, int right) {
        OrderRecord[] temp = new OrderRecord[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (list.get(i).time <= list.get(j).time) {
                temp[k++] = list.get(i++);
            } else {
                temp[k++] = list.get(j++);
            }
        }

        while (i <= mid) temp[k++] = list.get(i++);
        while (j <= right) temp[k++] = list.get(j++);

        for (i = left, k = 0; i <= right; i++, k++) list.set(i, temp[k]);
    }

    private static void mergeSort(List<OrderRecord> list, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(list, left, mid);
            mergeSort(list, mid + 1, right);
            merge(list, left, mid, right);
        }
    }

    private static void printOrders(List<OrderRecord> list, int count) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < Math.min(count, list.size()); i++) {
            String fTime = fmt.format(new Date(list.get(i).time));
            System.out.println("Order: " + list.get(i).code + " | Time: " + fTime);
        }
    }

    public static void main(String[] args) {
        final int TOTAL_ORDERS = 1_000_000;
        List<OrderRecord> orders = new ArrayList<>(TOTAL_ORDERS);

        generateOrders(orders, TOTAL_ORDERS);

        long start = System.currentTimeMillis();
        mergeSort(orders, 0, orders.size() - 1);
        long end = System.currentTimeMillis();

        double sec = (end - start) / 1000.0;
        System.out.println("\nSorting Complete in " + sec + " sec");
        System.out.println("\nFirst few sorted orders:");
        printOrders(orders, 10);
    }
}

