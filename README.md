# Sorting Algorithms Benchmark

## Personal Information

- **Name:** معتصم بالله علي بالقاسم
- **Student ID:** 3167123
- **Section / Group:** A

## Project Overview

This project compares the practical performance of five sorting algorithms on arrays of size **N = 100,000**:

| Complexity class | Algorithms |
|---|---|
| O(N²) | Selection Sort, Insertion Sort, Bubble Sort |
| O(N log N) | Merge Sort, Quick Sort |

Each algorithm is tested on four input cases:

1. **Random** — fully unsorted random integers  
2. **Reversely Sorted** — descending order  
3. **Partially Sorted** — ~80% sorted with scattered random elements  
4. **Fully Sorted** — ascending order  

Timing is measured with `System.nanoTime()` and reported in **microseconds (us)**.  
Only the sorting step is timed; array generation and copying are excluded.  
A fresh copy of the array is created before every run using `Arrays.copyOf`.

## How to Run

```bash
javac SortingBenchmark.java
java com.mycompany.sortingbenchmark.SortingBenchmark
```

## Results (us)

Measured on a local machine with **N = 100,000**:

| Algorithm | Random | Reversely Sorted | Partially Sorted | Fully Sorted |
|---|---:|---:|---:|---:|
| Selection Sort | 15,022,592 | 16,973,566 | 8,004,630 | 7,339,546 |
| Insertion Sort | 7,856,165 | 14,200,964 | 2,135,395 | 525 |
| Bubble Sort | 39,349,089 | 21,600,937 | 10,800,027 | 137 |
| Merge Sort | 63,828 | 44,876 | 39,158 | 31,042 |
| Quick Sort | 66,138 | 14,558 | 11,735 | 5,456 |

## Chart

Comparison of sorting times (microseconds, approximate scale):

| Algorithm | Random | Reverse | Partial | Fully Sorted |
|---|---|---|---|---|
| Selection | ████████████████ | █████████████████ | ████████ | ███████ |
| Insertion | ████████ | ██████████████ | ██ | ▏ |
| Bubble | ████████████████████████████████████████ | ██████████████████████ | ███████████ | ▏ |
| Merge | ▏ | ▏ | ▏ | ▏ |
| Quick | ▏ | ▏ | ▏ | ▏ |

(Longer bars mean slower. Merge and Quick are much faster than Selection/Insertion/Bubble.)

## Analysis and Discussion

### 1. O(N²) vs O(N log N)

Selection, Insertion, and Bubble Sort are clearly slower at N = 100,000. Their times are in millions of microseconds on difficult cases, while Merge Sort and Quick Sort stay in tens of thousands. This matches the theoretical gap between quadratic and N log N growth.

### 2. Best algorithms on sorted / partially sorted data

- **Insertion Sort** becomes extremely fast on fully sorted data (**525 us**) because each element needs almost no shifting (near O(N)).
- **Bubble Sort** with early-exit is also excellent on fully sorted data (**137 us**) after one pass with no swaps.
- **Selection Sort** does **not** benefit from sorted order; it still performs about N²/2 comparisons (**~7.3M us** even when already sorted).
- On partially sorted data, Insertion improves a lot compared to Random, while Merge/Quick remain fast and stable.

### 3. Quick Sort behavior and the pivot

If the pivot is always the first or last element, sorted and reverse-sorted inputs can create unbalanced partitions and degrade toward O(N²).

This implementation uses **median-of-three** (first, middle, last). That keeps partitions more balanced on ordered inputs, so Quick Sort stays fast on Reverse and Fully Sorted cases in the table above. Pivot choice is therefore a major factor in Quick Sort’s practical worst-case behavior.

## Source File

- `SortingBenchmark.java` — full program (data generation, timing, and the five sorting algorithms)
