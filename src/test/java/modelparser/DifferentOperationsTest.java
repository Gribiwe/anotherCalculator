package modelparser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * class for testing parsed
 * model values with some combined operations
 * and clear operations
 */
class DifferentOperationsTest {

   /**
    * exemplar of TestUtil class for testing the cases
    */
   private static TestUtil testUtil;

   /**
    * initialization of testUtil
    */
   @BeforeAll
   static void setup() {
      testUtil = new TestUtil();
   }

   /**
    * testing different cases with different operations
    */
   @Test
   void testSomeCases() {
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
      testUtil.doTest("1234567899876543 n n n % n % n =", "0", "");
   }

   /**
    * testing of backspace operation
    */
   @Test
   void backSPaceTest() {
      testUtil.doTest("25,25 ← ← ← ← + 25,2525 ← ← +", "27,25", "2   +   25,25   +   ");
      testUtil.doTest("25,25 ← ← ← ← ← + 25,2525 ← ← +", "25,25", "0   +   25,25   +   ");
      testUtil.doTest("25,25 ← ← ← ← ← ← ← ← + 25,2525 ← ← +", "25,25", "0   +   25,25   +   ");
      testUtil.doTest("25 + 0 + 5 = ←", "30", "");
      testUtil.doTest("25 + 0 + 5 = ← ← ←", "30", "");
      testUtil.doTest("25 + 0 + 5 ← 54 ← 543 ← - ← ← + ← 2 -", "581", "25   +   0   +   554   +   2   -   ");

      testUtil.doTest("←", "0", "");
      testUtil.doTest("9999999999999999 ←", "999 999 999 999 999", "");
      testUtil.doTest("9999999999999999 ← ←", "99 999 999 999 999", "");
      testUtil.doTest("1 ←", "0", "");
      testUtil.doTest("11 ←", "1", "");
      testUtil.doTest("11 ← ←", "0", "");
      testUtil.doTest("111 ← ←", "1", "");
      testUtil.doTest("111 ← ← ←", "0", "");
      testUtil.doTest("111 ← ← ← ← ←", "0", "");

      testUtil.doTest("2 ← ", "0", "");
      testUtil.doTest("22 ← ", "2", "");
      testUtil.doTest("22 ← ←", "0", "");
      testUtil.doTest("222 ← ←", "2", "");
      testUtil.doTest("222 ← ← ←", "0", "");
      testUtil.doTest("222 ← ← ← ← ←", "0", "");

      testUtil.doTest("3 ← ", "0", "");
      testUtil.doTest("33 ← ", "3", "");
      testUtil.doTest("33 ← ←", "0", "");
      testUtil.doTest("333 ← ←", "3", "");
      testUtil.doTest("333 ← ← ←", "0", "");
      testUtil.doTest("333 ← ← ← ← ←", "0", "");

      testUtil.doTest("4 ← ", "0", "");
      testUtil.doTest("44 ← ", "4", "");
      testUtil.doTest("44 ← ←", "0", "");
      testUtil.doTest("444 ← ←", "4", "");
      testUtil.doTest("444 ← ← ←", "0", "");
      testUtil.doTest("444 ← ← ← ← ←", "0", "");

      testUtil.doTest("5 ← ", "0", "");
      testUtil.doTest("55 ← ", "5", "");
      testUtil.doTest("55 ← ←", "0", "");
      testUtil.doTest("555 ← ←", "5", "");
      testUtil.doTest("555 ← ← ←", "0", "");
      testUtil.doTest("555 ← ← ← ← ←", "0", "");

      testUtil.doTest("6 ← ", "0", "");
      testUtil.doTest("66 ← ", "6", "");
      testUtil.doTest("66 ← ←", "0", "");
      testUtil.doTest("666 ← ←", "6", "");
      testUtil.doTest("666 ← ← ←", "0", "");
      testUtil.doTest("666 ← ← ← ← ←", "0", "");

      testUtil.doTest("7 ← ", "0", "");
      testUtil.doTest("77 ← ", "7", "");
      testUtil.doTest("77 ← ←", "0", "");
      testUtil.doTest("777 ← ←", "7", "");
      testUtil.doTest("777 ← ← ←", "0", "");
      testUtil.doTest("777 ← ← ← ← ←", "0", "");

      testUtil.doTest("8 ← ", "0", "");
      testUtil.doTest("88 ← ", "8", "");
      testUtil.doTest("88 ← ←", "0", "");
      testUtil.doTest("888 ← ←", "8", "");
      testUtil.doTest("888 ← ← ←", "0", "");
      testUtil.doTest("888 ← ← ← ← ←", "0", "");

      testUtil.doTest("9 ← ", "0", "");
      testUtil.doTest("99 ← ", "9", "");
      testUtil.doTest("99 ← ←", "0", "");
      testUtil.doTest("999 ← ←", "9", "");
      testUtil.doTest("999 ← ← ←", "0", "");
      testUtil.doTest("999 ← ← ← ← ←", "0", "");

      testUtil.doTest("0 ← ", "0", "");
      testUtil.doTest("00 ← ", "0", "");
      testUtil.doTest("00 ← ←", "0", "");
      testUtil.doTest("000 ← ←", "0", "");
      testUtil.doTest("000 ← ← ←", "0", "");
      testUtil.doTest("000 ← ← ← ← ←", "0", "");
   }

   /**
    * testing of CE button
    */
   @Test
   void clearStrokeTest() {
      testUtil.doTest("1 ce ", "0", "");
      testUtil.doTest("111531 ce ", "0", "");
      testUtil.doTest("111531 ce 1243", "1 243", "");
      testUtil.doTest("111531 ce 1243 ce", "0", "");
      testUtil.doTest("111531 + 2 + 3 ce", "0", "111531   +   2   +   ");
      testUtil.doTest("111531 + ce ce 3 + ce ce 3 ce ce", "0", "111531   +   3   +   ");
      testUtil.doTest("1 + 2 ce + 3 ce + 4 ce + 5 ce + 6 ce + 7 ce + 8 ce + 9 ce +", "1", "1   +   0   +   0   +   0   +   0   +   0   +   0   +   0   +   0   +   ");
      testUtil.doTest("+ ce +", "0", "0   +   0   +   ");
      testUtil.doTest("111531 - 2 - 3 ce", "0", "111531   -   2   -   ");
      testUtil.doTest("111531 - ce ce 3 - ce ce 3 ce ce", "0", "111531   -   3   -   ");
      testUtil.doTest("1 - 2 ce - 3 ce - 4 ce - 5 ce - 6 ce - 7 ce - 8 ce - 9 ce -", "1", "1   -   0   -   0   -   0   -   0   -   0   -   0   -   0   -   0   -   ");
      testUtil.doTest("- ce -", "0", "0   -   0   -   ");
      testUtil.doTest("111531 * 2 * 3 ce", "0", "111531   ×   2   ×   ");
      testUtil.doTest("111531 * ce ce 3 * ce ce 3 ce ce", "0", "111531   ×   3   ×   ");
      testUtil.doTest("1 * 2 ce * 3 ce * 4 ce * 5 ce * 6 ce * 7 ce * 8 ce * 9 ce *", "0", "1   ×   0   ×   0   ×   0   ×   0   ×   0   ×   0   ×   0   ×   0   ×   ");
      testUtil.doTest("* ce *", "0", "0   ×   0   ×   ");
      testUtil.doTest("111531 / 2 / 3 ce", "0", "111531   ÷   2   ÷   ");
      testUtil.doTest("111531 / ce ce 3 / ce ce 3 ce ce", "0", "111531   ÷   3   ÷   ");
   }

   /**
    * testing of C button
    */
   @Test
   void clearStrokeAndHistoryTest() {
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
