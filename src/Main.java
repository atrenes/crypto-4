import java.math.BigInteger;
import java.nio.charset.Charset;

public class Main {
    public static void main(String[] args) {
        BigInteger N = new BigInteger("199463062753");
        BigInteger e1 = new BigInteger("419513");
        BigInteger e2 = new BigInteger("830477");

        String C1 = """
                177528135337
                131197957980
                181321285074
                96738779356
                127632416974
                161779284378
                148599198368
                2033602084
                141914496373
                105405878640
                120038779975
                7139491789""";

        String C2 = """
                63508097139
                142467940607
                131649552179
                182684157712
                22912524157
                94825501208
                189716623763
                86236434624
                94875774697
                120252092430
                26215384541
                53782670605""";

        System.out.printf("N = %d\n", N);
        System.out.printf("e1 = %d\n", e1);
        System.out.printf("e2 = %d\n", e2);
        System.out.printf("C1 = %s\n\n", C1);
        System.out.printf("C2 = %s\n\n", C2);

        String[] c1Array = C1.split("\n");
        String[] c2Array = C2.split("\n");
        StringBuilder message = new StringBuilder();

        BigInteger[] gcdExtended = gcdExtended(e1, e2);
        BigInteger r = gcdExtended[1];
        BigInteger s = gcdExtended[2];

        System.out.println("(e1 * r) + (e2 * s) = ±1");
        System.out.printf("r = %d\n", r);
        System.out.printf("s = %d\n\n", s);

        for (int i = 0; i < c1Array.length; i++) {
            BigInteger c1r = new BigInteger(c1Array[i]).modPow(r, N);
            BigInteger c2s = new BigInteger(c2Array[i]).modPow(s, N);
            BigInteger m = c1r.multiply(c2s).mod(N);
            System.out.printf("(C1[%d]^r) mod N = %d\n", i, c1r);
            System.out.printf("(C2[%d]^s) mod N = %d\n", i, c2s);

            byte[] b = m.toByteArray();
            String ms = new String(b, Charset.forName("Windows-1251"));

            System.out.printf("m%d = (%d * %d) mod %d = %d => text(%d) = %s\n\n", i, c1r, c2s, N, m, m, ms);
            message.append(ms);
        }

        System.out.println("message = " + message);
    }

    public static BigInteger[] gcdExtended(BigInteger num1, BigInteger num2) {
        BigInteger[] result;
        if (num1.equals(BigInteger.ZERO)) {
            result = new BigInteger[3];
            result[0] = num2;
            result[1] = BigInteger.ZERO;
            result[2] = BigInteger.ONE;
        } else {
            result = gcdExtended(num2.mod(num1), num1);
            BigInteger temp = result[1];
            result[1] = result[2].subtract(num2.divide(num1).multiply(result[1]));
            result[2] = temp;
        }
        return result;
    }
}