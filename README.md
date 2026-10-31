# Sorting Algorithms Benchmark

## Personal Information

- **Name:** معتصم بالله علي بالقاسم
- **Student ID:** 3167123
- **Section / Group:** A

## About

مقارنة بين 5 خوارزميات ترتيب على مصفوفة حجمها N = 100000:

- Selection Sort
- Insertion Sort
- Bubble Sort
- Merge Sort
- Quick Sort

كل خوارزمية تُختبر على 4 حالات: Random, Reverse, Partial (~80% مرتب), Fully Sorted.

القياس بـ `System.nanoTime()` والنتيجة بالميكروثانية (us)، وننسخ المصفوفة قبل كل تشغيل.

## How to Run

```bash
javac SortingBenchmark.java
java SortingBenchmark
```

## Results (us)

| Algorithm | Random | Reverse | Partial | Sorted |
|---|---:|---:|---:|---:|
| Selection Sort | 15022592 | 16973566 | 8004630 | 7339546 |
| Insertion Sort | 7856165 | 14200964 | 2135395 | 525 |
| Bubble Sort | 39349089 | 21600937 | 10800027 | 137 |
| Merge Sort | 63828 | 44876 | 39158 | 31042 |
| Quick Sort | 66138 | 14558 | 11735 | 5456 |

## Chart

| Algorithm | Random | Reverse | Partial | Sorted |
|---|---|---|---|---|
| Selection | ████████████ | █████████████ | ██████ | ██████ |
| Insertion | ██████ | ███████████ | ██ | ▏ |
| Bubble | ██████████████████████████████ | ████████████████ | ████████ | ▏ |
| Merge | ▏ | ▏ | ▏ | ▏ |
| Quick | ▏ | ▏ | ▏ | ▏ |

## Analysis

1. خوارزميات O(N^2) أبطأ بكثير من Merge و Quick عند N=100000.
2. Insertion و Bubble سريعان جداً على البيانات المرتبة، بينما Selection لا يستفيد من الترتيب.
3. في Quick Sort اختيار المحور مهم: لو المحور دائماً آخر عنصر، الحالة المرتبة/المعكوسة تصير سيئة. في الكود اخترت العنصر الوسط كمحور عشان الأداء يبقى أفضل.

## Files

- `SortingBenchmark.java` 
- `نتائج_الترتيب.xlsx` (جدول النتائج + الرسوم في Excel)

