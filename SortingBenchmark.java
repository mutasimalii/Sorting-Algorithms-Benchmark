import java.util.Arrays;
import java.util.Random;

public class SortingBenchmark {

    static final int N = 100000;

    public static void main(String[] args) {
        System.out.println("Sorting Algorithms Benchmark");
        System.out.println("N = " + N);
        System.out.println();

        int[] random = makeRandom(N);
        int[] reverse = makeReverse(N);
        int[] partial = makePartial(N);
        int[] sorted = makeSorted(N);

        System.out.println("Algorithm       | Random     | Reverse    | Partial    | Sorted");
        System.out.println("----------------+------------+------------+------------+-----------");

        runAll("Selection Sort", random, reverse, partial, sorted, 1);
        runAll("Insertion Sort", random, reverse, partial, sorted, 2);
        runAll("Bubble Sort", random, reverse, partial, sorted, 3);
        runAll("Merge Sort", random, reverse, partial, sorted, 4);
        runAll("Quick Sort", random, reverse, partial, sorted, 5);
    }

    // يقيس الخوارزمية على الحالات الاربع ويطبع صف في الجدول
    static void runAll(String name, int[] r, int[] rev, int[] p, int[] s, int type) {
        long t1 = measure(r, type);
        long t2 = measure(rev, type);
        long t3 = measure(p, type);
        long t4 = measure(s, type);
        System.out.printf("%-15s | %10d | %10d | %10d | %10d%n", name, t1, t2, t3, t4);
    }

    // نسخة من المصفوفة + قياس الوقت بالميكروثانية
    static long measure(int[] original, int type) {
        int[] a = Arrays.copyOf(original, original.length);
        long start = System.nanoTime();
        if (type == 1) selectionSort(a);
        else if (type == 2) insertionSort(a);
        else if (type == 3) bubbleSort(a);
        else if (type == 4) mergeSort(a, 0, a.length - 1);
        else if (type == 5) quickSort(a, 0, a.length - 1);
        long end = System.nanoTime();
        return (end - start) / 1000;
    }

    // ---------- توليد البيانات ----------

    static int[] makeRandom(int n) {
        Random rand = new Random();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = rand.nextInt();
        return a;
    }

    static int[] makeReverse(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = n - i;
        return a;
    }

    static int[] makeSorted(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i;
        return a;
    }

    // حوالي 80% مرتب
    static int[] makePartial(int n) {
        Random rand = new Random();
        int[] a = makeSorted(n);
        for (int i = 0; i < n / 5; i++) {
            a[rand.nextInt(n)] = rand.nextInt(n * 2);
        }
        return a;
    }

    // ---------- الخوارزميات ----------

    static void selectionSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[min]) min = j;
            }
            int t = a[i];
            a[i] = a[min];
            a[min] = t;
        }
    }

    static void insertionSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= 0 && a[j] > key) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }

    static void bubbleSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int t = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = t;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    static void mergeSort(int[] a, int left, int right) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        mergeSort(a, left, mid);
        mergeSort(a, mid + 1, right);
        merge(a, left, mid, right);
    }

    static void merge(int[] a, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        int[] L = new int[n1];
        int[] R = new int[n2];
        for (int i = 0; i < n1; i++) L[i] = a[left + i];
        for (int j = 0; j < n2; j++) R[j] = a[mid + 1 + j];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) a[k++] = L[i++];
            else a[k++] = R[j++];
        }
        while (i < n1) a[k++] = L[i++];
        while (j < n2) a[k++] = R[j++];
    }

    static void quickSort(int[] a, int low, int high) {
        if (low >= high) return;
        int p = partition(a, low, high);
        quickSort(a, low, p - 1);
        quickSort(a, p + 1, high);
    }

    // نختار العنصر الوسط كمحور (اذا اخترنا دايما الاخير تضعف على المرتب والمعكوس)
    static int partition(int[] a, int low, int high) {
        int mid = (low + high) / 2;
        int temp = a[mid];
        a[mid] = a[high];
        a[high] = temp;

        int pivot = a[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (a[j] <= pivot) {
                i++;
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
            }
        }
        int t = a[i + 1];
        a[i + 1] = a[high];
        a[high] = t;
        return i + 1;
    }
}
