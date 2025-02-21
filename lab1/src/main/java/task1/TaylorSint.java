package task1;

public class TaylorSint {
    // Public API
    public static double sin(double x, int terms) {
        double result = 0.0;
        for (int n = 0; n < terms; n++) {
            double term = Math.pow(-1, n) * Math.pow(x, 2 * n + 1) / factorial(2 * n + 1);
            result += term;
        }
        return result;
    }

    private static long factorial(int n) {
        if (n == 0) return 1;
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
