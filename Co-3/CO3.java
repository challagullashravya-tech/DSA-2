class FenwickTree {
    int[] bit;
    int n;

    FenwickTree(int n) {
        this.n = n;
        bit = new int[n + 1];
    }

    void update(int index, int value) {
        while (index <= n) {
            bit[index] += value;
            index += index & (-index);
        }
    }

    int query(int index) {
        int sum = 0;
        while (index > 0) {
            sum += bit[index];
            index -= index & (-index);
        }
        return sum;
    }

    int rangeQuery(int left, int right) {
        return query(right) - query(left - 1);
    }
}

public class CO3 {
    public static void main(String[] args) {

        int[] production = {500, 700, 300, 450, 250};

        FenwickTree ft = new FenwickTree(production.length);

        for (int i = 0; i < production.length; i++) {
            ft.update(i + 1, production[i]);
        }

        System.out.println("Total Production (Region 1 to 5): " + ft.query(5));
        System.out.println("Production (Region 2 to 4): " + ft.rangeQuery(2, 4));
    }
}