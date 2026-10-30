package com.mycompany.sortingbenchmark;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

/**
 * نقارنو بين 5 خوارزميات ترتيب على مصفوفة فيها 100000 عنصر
 * وفي 4 حالات مختلفة للبيانات، ونقيسو الوقت بالميكروثانية.
 */
public class SortingBenchmark {

    // هذي حجم المصفوفة المطلوب في الواجب
    private static final int N = 100_000;
    // بذرة ثابتة بيش الارقام العشوائية تطلع نفس الشي كل مرة
    private static final long SEED = 42L;

    // اسماء الخوارزميات بالانقليزي والعربي
    private static final String[] ALGORITHM_NAMES_EN = {
            "Selection Sort",
            "Insertion Sort",
            "Bubble Sort",
            "Merge Sort",
            "Quick Sort"
    };
    private static final String[] ALGORITHM_NAMES_AR = {
            "ترتيب بالاختيار",
            "ترتيب بالادراج",
            "ترتيب فقاعي",
            "ترتيب بالدمج",
            "ترتيب سريع"
    };

    // اسماء حالات البيانات بالانقليزي والعربي
    private static final String[] CASE_NAMES_EN = {
            "Random",
            "Reversely Sorted",
            "Partially Sorted",
            "Fully Sorted"
    };
    private static final String[] CASE_NAMES_AR = {
            "عشوائي",
            "مرتب بالعكس",
            "مرتب جزئيا",
            "مرتب كامل"
    };

    public static void main(String[] args) {
        System.out.println("Sorting Algorithms Benchmark / مقارنة خوارزميات الترتيب");
        // Locale.US بيش الارقام تبان انجليزي
        System.out.println("N = " + String.format(Locale.US, "%,d", N) + " / حجم المصفوفة");
        System.out.println("Timing unit: microseconds (us) / وحدة القياس: ميكروثانية");
        System.out.println("Generating datasets... / جاري تجهيز البيانات...");
        System.out.println();

        // نحضرو الحالات الاربعة قبل ما نبدا القياس
        int[] randomArr = generateRandom(N);
        int[] reverseArr = generateReverseSorted(N);
        int[] partialArr = generatePartiallySorted(N);
        int[] sortedArr = generateFullySorted(N);

        int[][] datasets = {randomArr, reverseArr, partialArr, sortedArr};

        // results[رقم الخوارزمية][رقم الحالة] نخزن فيه الازمنة
        long[][] results = new long[ALGORITHM_NAMES_EN.length][CASE_NAMES_EN.length];

        for (int algo = 0; algo < ALGORITHM_NAMES_EN.length; algo++) {
            for (int c = 0; c < CASE_NAMES_EN.length; c++) {
                System.out.printf("Running %s (%s) on %s (%s) ... ",
                        ALGORITHM_NAMES_EN[algo], ALGORITHM_NAMES_AR[algo],
                        CASE_NAMES_EN[c], CASE_NAMES_AR[c]);
                System.out.flush();

                // ناخذو نسخة جديدة بيش البيانات الاصلية ما تتبهدل بعد الترتيب
                int[] testArray = Arrays.copyOf(datasets[c], datasets[c].length);

                // نقيسو بس وقت الترتيب (مش النسخ ولا التجهيز)
                long startTime = System.nanoTime();
                runSort(algo, testArray);
                long endTime = System.nanoTime();

                // نحول من نانو ثانية لميكروثانية
                long durationUs = (endTime - startTime) / 1000L;
                results[algo][c] = durationUs;
                System.out.printf(Locale.US, "%,d us / ميكروثانية%n", durationUs);
            }
        }

        printTable(results);
    }

    /** هذي تستدعي الخوارزمية حسب رقمها */
    private static void runSort(int algorithmIndex, int[] arr) {
        switch (algorithmIndex) {
            case 0:
                selectionSort(arr);
                break;
            case 1:
                insertionSort(arr);
                break;
            case 2:
                bubbleSort(arr);
                break;
            case 3:
                mergeSort(arr);
                break;
            case 4:
                quickSort(arr);
                break;
            default:
                throw new IllegalArgumentException("خوارزمية مش معروفة: " + algorithmIndex);
        }
    }

    /** هذي تطبع جدول النتائج في الاخر */
    private static void printTable(long[][] results) {
        System.out.println();
        System.out.println("==================== RESULTS (us) / النتائج (ميكروثانية) ====================");
        System.out.println("Algorithm | الخوارزمية | Random/عشوائي | Reverse/بالعكس | Partial/جزئي | Fully/مرتب");
        System.out.println("----------+------------+---------------+----------------+--------------+-----------");

        for (int algo = 0; algo < ALGORITHM_NAMES_EN.length; algo++) {
            System.out.printf(Locale.US,
                    "%-14s | %-10s | %,11d us | %,12d us | %,10d us | %,9d us%n",
                    ALGORITHM_NAMES_EN[algo],
                    ALGORITHM_NAMES_AR[algo],
                    results[algo][0],
                    results[algo][1],
                    results[algo][2],
                    results[algo][3]);
        }
        System.out.println("==============================================================================");
    }

    // ===================== تجهيز البيانات =====================

    /** حالة عشوائية: ارقام مخلوطة بالكامل */
    private static int[] generateRandom(int n) {
        Random random = new Random(SEED);
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt();
        }
        return arr;
    }

    /** حالة مرتبة بالعكس: من الكبير للصغير */
    private static int[] generateReverseSorted(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = n - 1 - i;
        }
        return arr;
    }

    /**
     * حالة مرتبة شوية (~80%): نبداو مرتبة
     * وبعدين نخربشو حوالي 20% من الاماكن بارقام عشوائية
     */
    private static int[] generatePartiallySorted(int n) {
        Random random = new Random(SEED + 1);
        int[] arr = generateFullySorted(n);
        int scrambleCount = (int) (n * 0.20);
        for (int i = 0; i < scrambleCount; i++) {
            int index = random.nextInt(n);
            arr[index] = random.nextInt(n * 2);
        }
        return arr;
    }

    /** حالة مرتبة كاملة: من الصغير للكبير */
    private static int[] generateFullySorted(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        return arr;
    }

    // ===================== خوارزميات الترتيب =====================

    /** Selection Sort — بطيئة O(N^2) تقريبا في كل الحالات */
    private static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            // ندور على اصغر عنصر في الجزء اللي مزال مش مترتب
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                swap(arr, i, minIndex);
            }
        }
    }

    /**
     * Insertion Sort — O(N^2) في الحالة السيئة
     * بس تطلع سريعة وايد اذا البيانات مرتبة او شبه مرتبة
     */
    private static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            // نزحلحو العناصر الاكبر من المفتاح لليمين
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    /**
     * Bubble Sort مع ايقاف بدري اذا ما صارش تبديل
     * تبقى O(N^2) على العشوائي والمعكوس، وسريعة اذا مرتبة
     */
    private static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }
            // اذا ما تبدلش حاجة معناها المصفوفة خلاص مرتبة
            if (!swapped) {
                break;
            }
        }
    }

    /** Merge Sort — O(N log N) دايما، بس تاخذ ذاكرة زيادة */
    private static void mergeSort(int[] arr) {
        if (arr.length < 2) {
            return;
        }
        mergeSort(arr, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    /** ندمجو جزئين مرتبين في جزء واحد مرتب */
    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        int[] leftPart = new int[n1];
        int[] rightPart = new int[n2];

        System.arraycopy(arr, left, leftPart, 0, n1);
        System.arraycopy(arr, mid + 1, rightPart, 0, n2);

        int i = 0;
        int j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (leftPart[i] <= rightPart[j]) {
                arr[k++] = leftPart[i++];
            } else {
                arr[k++] = rightPart[j++];
            }
        }
        while (i < n1) {
            arr[k++] = leftPart[i++];
        }
        while (j < n2) {
            arr[k++] = rightPart[j++];
        }
    }

    /**
     * Quick Sort مع اختيار المحور بطريقة الوسيط من ثلاثة
     * متوسطها O(N log N). الوسيط من ثلاثة يخفف الحالات السيئة
     * اللي تصير اذا المحور دايما اول او اخر عنصر على بيانات مرتبة او معكوسة
     */
    private static void quickSort(int[] arr) {
        if (arr.length < 2) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }
        int pivotIndex = partition(arr, low, high);
        quickSort(arr, low, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, high);
    }

    /** نقسمو المصفوفة حول المحور ونرجعو مكانه النهائي */
    private static int partition(int[] arr, int low, int high) {
        medianOfThree(arr, low, high);
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    /** نحطو وسيط (اول، اوسط، اخر) في الاخر بيش يكون هو المحور */
    private static void medianOfThree(int[] arr, int low, int high) {
        int mid = low + (high - low) / 2;
        if (arr[mid] < arr[low]) {
            swap(arr, low, mid);
        }
        if (arr[high] < arr[low]) {
            swap(arr, low, high);
        }
        if (arr[mid] < arr[high]) {
            swap(arr, mid, high);
        }
    }

    /** نبدلو عنصرين في المصفوفة */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
