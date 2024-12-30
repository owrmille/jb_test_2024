import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ArrayList<Long> vals = new ArrayList<Long>(List.of(2L, 5L, 1L));
        System.out.println(solve(vals, 1, 5));
    }

    public static List<List<Long>> solve(
            List<Long> vals,
            Integer start,
            Integer end
    ) {
        List<List<Long>> res = new ArrayList<>();
        for (int subset_size = start; subset_size <= end; subset_size++) {
            res.add(help(vals, subset_size));
        }
        return res;
    }

    private static List<Long> help(
            List<Long> vals,
            int subset_size
    ) {
        List<Long> products = new ArrayList<>();
        backtrack_values(vals, 0, subset_size,  1L, products);
        return products;
    }

    /*
    Backtracking algorithm that
    takes:
    - the current start index (start),
    - the remaining number of elements to add in a "product subset" (elements_left),
    - the current product built so far (current_product),
    collects:
    - final products in 'products'.
     */
    private static void backtrack_values(
            List<Long> vals,
            int start,
            int elements_left,
            long current_product,
            List<Long> products
    ) {
        // If we have chosen already k elements, add currentProduct to the final list
        if (elements_left == 0) {
            products.add(current_product);
            return;
        }
        // Try all possible ways to pick the next element from [start..size-1].
        for (int i = start; i < vals.size(); i++) {
            backtrack_values(vals, i + 1, elements_left - 1, current_product * vals.get(i), products);
        }
    }
}