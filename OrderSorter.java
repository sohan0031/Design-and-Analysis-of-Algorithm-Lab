import java.util.*;
import java.text.SimpleDateFormat;

class OrderRecord {
    String code;
    long time;

    public OrderRecord(String code, long time) {
        this.code = code;
        this.time = time;
    }
}

public class OrderSorter{
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
        List<OrderRecord> leftList = new ArrayList<>(list.subList(left, mid + 1));
        List<OrderRecord> rightList = new ArrayList<>(list.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;

        while (i < leftList.size() && j < rightList.size()) {
            if (leftList.get(i).time <= rightList.get(j).time) {
                list.set(k++, leftList.get(i++));
            } else {
                list.set(k++, rightList.get(j++));
            }
        }

        while (i < leftList.size()) {
            list.set(k++, leftList.get(i++));
        }

        while (j < rightList.size()) {
            list.set(k++, rightList.get(j++));
        }
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
        System.out.println("\nSorting Complete in "+sec+" sec");
        System.out.println("\nFirst few sorted orders:");
        printOrders(orders, 10);
    }
}
