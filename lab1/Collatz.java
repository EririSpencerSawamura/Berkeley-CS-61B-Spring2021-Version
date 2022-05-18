/** Class that prints the Collatz sequence starting from a given number.
 *  @author Ren Wang
 */
public class Collatz {

    /** Generating the next number in the Collatz sequence with the current number.
     * @param  n the current number in the Collatz sequence
     * @return   the next number in the Collatz sequence*/
    public static int nextNumber(int n) {
        if (n % 2 == 1) {
            return 3 * n + 1;
        }
        else {
            return n / 2;
        }
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.print(n + " ");
        while (n != 1) {
            n = nextNumber(n);
            System.out.print(n + " ");
        }
        System.out.println();
    }
}

