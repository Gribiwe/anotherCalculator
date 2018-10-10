package modelparser;

import org.junit.Before;
import org.junit.Test;

/**
 * class for testing parsed
 * model values with some combined operations
 * and clear operations
 */
public class DifferentOperationsTest {

   /**
    * exemplar of TestUtil class for testing the cases
    */
   private static TestUtil testUtil;

   /**
    * initialization of testUtil
    */
   @Before
   public void setup() {
      testUtil = new TestUtil();
   }

   /**
    * testing different cases with different operations
    */
   @Test
   public void testSomeCases() {
      testUtil.doTest("5 / 3 * 1000000000000000 = =", "1,666666666666667e+30", "");
      testUtil.doTest("1 / 7 * 7 - 1 =", "0", "");
      testUtil.doTest("1 / 7 * 1000000000000000 * 7 - 1000000000000000 =", "0", "");
      testUtil.doTest("/ * n * / - + / * % n % =", "0", "");
      testUtil.doTest("0,3 n n n + / - * 3 n n +", "-0,9", "-0,3   ×   3   +   ");
      testUtil.doTest("0, +", "0", "0   +   ");
      testUtil.doTest("0, n +", "0", "0   +   ");
      testUtil.doTest("1 + 2 - 3 + 4 + 5 n * 6 / 7 1/x", "0,1428571428571429", "1   +   2   -   3   +   4   +   -5   ×   6   ÷   1/( 7 )");
      testUtil.doTest("1 + 2 - 3 + 4 + 5 n * 6 / 7 1/x sqr", "0,0204081632653061", "1   +   2   -   3   +   4   +   -5   ×   6   ÷   sqr( 1/( 7 ) )");
      testUtil.doTest("1 + 2 - 3 + 4 + 5 n * 6 / 7 1/x sqr √", "0,1428571428571429", "1   +   2   -   3   +   4   +   -5   ×   6   ÷   √( sqr( 1/( 7 ) ) )");
      testUtil.doTest("25 sqr + √ n % % %", "-6 103,515625", "sqr( 25 )   +   -6103,515625");
      testUtil.doTest("25 + √ %", "1,25", "25   +   1,25");
      testUtil.doTest("25 + √ + 25 -", "55", "25   +   √( 25 )   +   25   -   ");
      testUtil.doTest("25 + √ + 25 =", "55", "");
      testUtil.doTest("25 + √ + 25 = =", "80", "");
      testUtil.doTest("25 + √ 25 =", "50", "");
      testUtil.doTest("25 + √ 25 = =", "75", "");
   }

   /**
    * testing of backspace
    */
   @Test
   public void backspaceTest() {
      testUtil.doTest("25,25 backspace backspace backspace backspace + 25,2525 backspace backspace +", "27,25", "2   +   25,25   +   ");
      testUtil.doTest("25,25 backspace backspace backspace backspace backspace + 25,2525 backspace backspace +", "25,25", "0   +   25,25   +   ");
      testUtil.doTest("25,25 backspace backspace backspace backspace backspace backspace backspace backspace + 25,2525 backspace backspace +", "25,25", "0   +   25,25   +   ");
      testUtil.doTest("25 + 0 + 5 = backspace", "30", "");
      testUtil.doTest("25 + 0 + 5 = backspace backspace backspace", "30", "");
      testUtil.doTest("25 + 0 + 5 backspace 54 backspace 543 backspace - backspace backspace + backspace 2 -", "581", "25   +   0   +   554   +   2   -   ");

      testUtil.doTest("backspace", "0", "");
      testUtil.doTest("9999999999999999 backspace", "999 999 999 999 999", "");
      testUtil.doTest("9999999999999999 backspace backspace", "99 999 999 999 999", "");
      testUtil.doTest("1 backspace", "0", "");
      testUtil.doTest("11 backspace", "1", "");
      testUtil.doTest("11 backspace backspace", "0", "");
      testUtil.doTest("111 backspace backspace", "1", "");
      testUtil.doTest("111 backspace backspace backspace", "0", "");
      testUtil.doTest("111 backspace backspace backspace backspace backspace", "0", "");

      testUtil.doTest("2 backspace ", "0", "");
      testUtil.doTest("22 backspace ", "2", "");
      testUtil.doTest("22 backspace backspace", "0", "");
      testUtil.doTest("222 backspace backspace", "2", "");
      testUtil.doTest("222 backspace backspace backspace", "0", "");
      testUtil.doTest("222 backspace backspace backspace backspace backspace", "0", "");

      testUtil.doTest("3 backspace ", "0", "");
      testUtil.doTest("33 backspace ", "3", "");
      testUtil.doTest("33 backspace backspace", "0", "");
      testUtil.doTest("333 backspace backspace", "3", "");
      testUtil.doTest("333 backspace backspace backspace", "0", "");
      testUtil.doTest("333 backspace backspace backspace backspace backspace", "0", "");

      testUtil.doTest("4 backspace ", "0", "");
      testUtil.doTest("44 backspace ", "4", "");
      testUtil.doTest("44 backspace backspace", "0", "");
      testUtil.doTest("444 backspace backspace", "4", "");
      testUtil.doTest("444 backspace backspace backspace", "0", "");
      testUtil.doTest("444 backspace backspace backspace backspace backspace", "0", "");

      testUtil.doTest("5 backspace ", "0", "");
      testUtil.doTest("55 backspace ", "5", "");
      testUtil.doTest("55 backspace backspace", "0", "");
      testUtil.doTest("555 backspace backspace", "5", "");
      testUtil.doTest("555 backspace backspace backspace", "0", "");
      testUtil.doTest("555 backspace backspace backspace backspace backspace", "0", "");

      testUtil.doTest("6 backspace ", "0", "");
      testUtil.doTest("66 backspace ", "6", "");
      testUtil.doTest("66 backspace backspace", "0", "");
      testUtil.doTest("666 backspace backspace", "6", "");
      testUtil.doTest("666 backspace backspace backspace", "0", "");
      testUtil.doTest("666 backspace backspace backspace backspace backspace", "0", "");

      testUtil.doTest("7 backspace ", "0", "");
      testUtil.doTest("77 backspace ", "7", "");
      testUtil.doTest("77 backspace backspace", "0", "");
      testUtil.doTest("777 backspace backspace", "7", "");
      testUtil.doTest("777 backspace backspace backspace", "0", "");
      testUtil.doTest("777 backspace backspace backspace backspace backspace", "0", "");

      testUtil.doTest("8 backspace ", "0", "");
      testUtil.doTest("88 backspace ", "8", "");
      testUtil.doTest("88 backspace backspace", "0", "");
      testUtil.doTest("888 backspace backspace", "8", "");
      testUtil.doTest("888 backspace backspace backspace", "0", "");
      testUtil.doTest("888 backspace backspace backspace backspace backspace", "0", "");

      testUtil.doTest("9 backspace ", "0", "");
      testUtil.doTest("99 backspace ", "9", "");
      testUtil.doTest("99 backspace backspace", "0", "");
      testUtil.doTest("999 backspace backspace", "9", "");
      testUtil.doTest("999 backspace backspace backspace", "0", "");
      testUtil.doTest("999 backspace backspace backspace backspace backspace", "0", "");

      testUtil.doTest("0 backspace ", "0", "");
      testUtil.doTest("00 backspace ", "0", "");
      testUtil.doTest("00 backspace backspace", "0", "");
      testUtil.doTest("000 backspace backspace", "0", "");
      testUtil.doTest("000 backspace backspace backspace", "0", "");
      testUtil.doTest("000 backspace backspace backspace backspace backspace", "0", "");
   }

   /**
    * testing of CE button
    */
   @Test
   public void clearStrokeTest() {
      testUtil.doTest("1 ce ", "0", "");
      testUtil.doTest("111531 ce ", "0", "");
      testUtil.doTest("111531 ce 1243", "1 243", "");
      testUtil.doTest("111531 ce 1243 ce", "0", "");
      testUtil.doTest("111531 + 2 + 3 ce", "0", "111531   +   2   +   ");
      testUtil.doTest("111531 + ce ce 3 + ce ce 3 ce ce", "0", "111531   +   3   +   ");
      testUtil.doTest("1 + 2 ce + 3 ce + 4 ce + 5 ce + 6 ce + 7 ce + 8 ce + 9 ce +", "1", "1   +   0   +   0   +   0   +   0   +   0   +   0   +   0   +   0   +   ");
      testUtil.doTest("+ ce +", "0", "0   +   ");
      testUtil.doTest("111531 - 2 - 3 ce", "0", "111531   -   2   -   ");
      testUtil.doTest("111531 - ce ce 3 - ce ce 3 ce ce", "0", "111531   -   3   -   ");
      testUtil.doTest("1 - 2 ce - 3 ce - 4 ce - 5 ce - 6 ce - 7 ce - 8 ce - 9 ce -", "1", "1   -   0   -   0   -   0   -   0   -   0   -   0   -   0   -   0   -   ");
      testUtil.doTest("- ce -", "0", "0   -   ");
      testUtil.doTest("111531 * 2 * 3 ce", "0", "111531   ×   2   ×   ");
      testUtil.doTest("111531 * ce ce 3 * ce ce 3 ce ce", "0", "111531   ×   3   ×   ");
      testUtil.doTest("1 * 2 ce * 3 ce * 4 ce * 5 ce * 6 ce * 7 ce * 8 ce * 9 ce *", "0", "1   ×   0   ×   0   ×   0   ×   0   ×   0   ×   0   ×   0   ×   0   ×   ");
      testUtil.doTest("* ce *", "0", "0   ×   ");
      testUtil.doTest("111531 / 2 / 3 ce", "0", "111531   ÷   2   ÷   ");
      testUtil.doTest("111531 / ce ce 3 / ce ce 3 ce ce", "0", "111531   ÷   3   ÷   ");
      testUtil.doTest("/ ce /", "0", "0   ÷   ");
   }

   /**
    * testing of C button
    */
   @Test
   public void clearStrokeAndHistoryTest() {
      testUtil.doTest("1 c ", "0", "");
      testUtil.doTest("111531 c ", "0", "");
      testUtil.doTest("111531 c 1243", "1 243", "");
      testUtil.doTest("111531 c 1243 c", "0", "");
      testUtil.doTest("111531 + 2 + 3 c", "0", "");
      testUtil.doTest("111531 + c c 3 + c c 3 c c", "0", "");
      testUtil.doTest("1 + 2 c + 3 c + 4 c + 5 c + 6 c + 7 c + 8 c + 9 c +", "0", "0   +   ");
      testUtil.doTest("+ c +", "0", "0   +   ");
      testUtil.doTest("111531 - 2 - 3 c", "0", "");
      testUtil.doTest("111531 - c c 3 - c c 3 c c", "0", "");
      testUtil.doTest("1 - 2 c - 3 c - 4 c - 5 c - 6 c - 7 c - 8 c - 9 c -", "0", "0   -   ");
      testUtil.doTest("- c -", "0", "0   -   ");
      testUtil.doTest("111531 * 2 * 3 c", "0", "");
      testUtil.doTest("111531 * c c 3 * c c 3 c c", "0", "");
      testUtil.doTest("1 * 2 c * 3 c * 4 c * 5 c * 6 c * 7 c * 8 c * 9 c *", "0", "0   ×   ");
      testUtil.doTest("* c *", "0", "0   ×   ");
      testUtil.doTest("111531 / 2 / 3 c", "0", "");
      testUtil.doTest("111531 / c c 3 / c c 3 c c", "0", "");
      testUtil.doTest("/ c /", "0", "0   ÷   ");
   }
}
