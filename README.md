# jb_test_2024

# What does the solve() method compute?
Method `solve()` returns a list of lists of possible products of the values in the initial list (considering that we calculate products inside the subsets of the sizes in the range `[l, k]`). For each iteration `i` in the range `[l, k]` it calls method `help(vals, i)` that adds the results to the final list. In its turn, method `help(vals, i)` returns a list of all possible products of `i` different values from the list `vals`. 

- When `k = 1`, `help(vals, 1)` returns just `vals`.
- When `k > 1`, `help(vals, k)` recursively:
  1. Picks `i-th` element from vals
  2. Removes first `i + 1` elements from the list
  3. Multiplies this picked `i-th` by the products returned by a recursive call of `help()` with `k - 1` 

Overall, the `help()` method goes through all possible products of `i` different elements from vals. The `solve()` method just collects all lists of products for subsets of sizes `[l, k]` in the final list.

---

# Time Complexity Analysis for `help(vals, k)` and `solve(vals, l, k)`

## Observations

### Base Case (k = 1)
- When `k = 1`, we loop over `vals.size()` elements and do `res.add(vals.get(i))`.
- This takes **O(n)** time (`n` is the size of `vals`).

### Recursive Case (k > 1)
- We iterate over each element `i` in `vals` (so we do `n` iterations if `n` is the size of `vals`).
- For each `i`, we:
  1. Create a new `ArrayList<Long> newVals = new ArrayList<>(vals)`  
     → **O(n)** time to copy  
  2. Remove `(i+1)` elements from beginning of `newVals`  
     → Up to **O(n)** in the worst case  
  3. Call `help(newVals, k - 1)` and do `.map(...)` on the result  
     → The recursive call contributes the major cost  

- So, each iteration makes a recursive call to `help` with a smaller list (size decreases by at least 1 each time).

---

## Recurrence Relation

Let:
- `n` be size of `vals`
- `T(n, k)` be time complexity of `help(vals, k)` when `vals.size() = n`.

### Base Case
- `T(n, 1) = O(n)`  
  Because when `k = 1` we simply add each element to the result list.

### Recursive Case
- When `k > 1`:  
  ```
  T(n, k) = ∑(i = 0 to n - 1) [O(n) + T(n - (i + 1), k - 1)]
  ```

---

## Estimate of the Growth

### Small Values of `k`:

#### k = 1
- `T(n, 1) = O(n)`

#### k = 2
- `T(n, 2) = ∑(i = 0 to n - 1) [O(n) + T(n - (i + 1), 1)]`  
  Here `T(n - (i + 1), 1) = O(n - (i + 1))`.  
  So:
  ```
  T(n, 2) = ∑(i = 0 to n - 1) [O(n) + O(n - (i + 1))] = O(n^2)
  ```

#### k = 3
- `T(n, 3) = ∑(i = 0 to n - 1) [O(n) + T(n - (i + 1), 2)]`  
  From the previous case `T(n, 2) = O(n^2)`, then: `T(n - (i + 1), 2) = O(n - (i + 1)^2)`.  
  So:
  ```
  T(n, 3) = ∑(i = 0 to n - 1) [O(n) + O((n - (i + 1))^2)] = O(n^3)
  ```


#### Result
Folowing the pattern, we can inductively conclude that:
```
T(n, k) = O(n^k)
```

---

## Overall Complexity

### `help(vals, k)`
- For a fixed `k`, the time complexity of `help(vals, k)` is:
  ```
  O(n^k)
  ```

### `solve(vals, l, k)`

- `solve` calls `help(vals, i)` once for each `i` in `[l .. k]`.  
- The worst-case for each `i` is still **O(n^i)**.  
- If `(k - l + 1)` is treated as a constant, the overall complexity becomes:
  ```
  O(n^k)
  ```
