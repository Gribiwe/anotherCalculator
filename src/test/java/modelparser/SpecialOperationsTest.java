package modelparser;

import org.junit.Before;
import org.junit.Test;

/**
 * Class for testing special operations
 * like root, sqr, one divide by x, etc.
 */
public class SpecialOperationsTest {

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
    * testing operation sqr
    */
   @Test
   public void testSQR() {
      testUtil.doTest("0 sqr","0","sqr( 0 )");
      testUtil.doTest("1 sqr","1","sqr( 1 )");
      testUtil.doTest("1 sqr sqr sqr sqr sqr sqr sqr =","1","");
      testUtil.doTest("2 sqr","4","sqr( 2 )");
      testUtil.doTest("2 sqr sqr","16","sqr( sqr( 2 ) )");
      testUtil.doTest("2 sqr sqr sqr","256","sqr( sqr( sqr( 2 ) ) )");
      testUtil.doTest("2 sqr =","4","");
      testUtil.doTest("3 sqr =","9","");
      testUtil.doTest("2 sqr = =","4","");
      testUtil.doTest("2 sqr = = = = = = = = = = = = =","4","");
      testUtil.doTest("2 sqr = = = = = = = = = = = = = + 2 - 2 sqr sqr / 1 -","-10","4   +   2   -   sqr( sqr( 2 ) )   ÷   1   -   ");
      testUtil.doTest("2 sqr = = = = = = = = = = = = = + 2 - 2 sqr sqr / 1 =","-10","");

      testUtil.doTest("0,0000000000000001 sqr =","1,e-32","");
      testUtil.doTest("0,0000000000000001 sqr sqr =","1,e-64","");
      testUtil.doTest("0,0000000000000001 sqr sqr sqr =","1,e-128","");
      testUtil.doTest("0,0000000000000001 sqr sqr sqr sqr =","1,e-256","");
      testUtil.doTest("0,0000000000000001 sqr sqr sqr sqr sqr =","1,e-512","");
      testUtil.doTest("0,0000000000000001 sqr sqr sqr sqr sqr sqr =","1,e-1024","");
      testUtil.doTest("0,0000000000000001 sqr sqr sqr sqr sqr sqr sqr =","1,e-2048","");
      testUtil.doTest("0,0000000000000001 sqr sqr sqr sqr sqr sqr sqr sqr =","1,e-4096","");
      testUtil.doTest("0,0000000000000001 sqr sqr sqr sqr sqr sqr sqr sqr sqr =","1,e-8192","");

      testUtil.doTest("9999999999999999 sqr =","9,999999999999998e+31","");
      testUtil.doTest("9999999999999999 sqr sqr =","9,999999999999996e+63","");
      testUtil.doTest("9999999999999999 sqr sqr sqr =","9,999999999999992e+127","");
      testUtil.doTest("9999999999999999 sqr sqr sqr sqr =","9,999999999999984e+255","");
      testUtil.doTest("9999999999999999 sqr sqr sqr sqr sqr =","9,999999999999968e+511","");
      testUtil.doTest("9999999999999999 sqr sqr sqr sqr sqr sqr =","9,999999999999936e+1023","");
      testUtil.doTest("9999999999999999 sqr sqr sqr sqr sqr sqr sqr =","9,999999999999872e+2047","");
      testUtil.doTest("9999999999999999 sqr sqr sqr sqr sqr sqr sqr sqr =","9,999999999999744e+4095","");
      testUtil.doTest("9999999999999999 sqr sqr sqr sqr sqr sqr sqr sqr sqr =","9,999999999999488e+8191","");
   }

   /**
    * testing operation root
    */
   @Test
   public void testRoot() {
      testUtil.doTest("0 √ =","0", "");
      testUtil.doTest("0 √ √ =","0", "");
      testUtil.doTest("0 √","0", "√( 0 )");
      testUtil.doTest("0 √ √","0", "√( √( 0 ) )");
      testUtil.doTest("1 √ =","1", "");
      testUtil.doTest("1 √ √ =","1", "");
      testUtil.doTest("1 √","1", "√( 1 )");
      testUtil.doTest("1 √ √","1", "√( √( 1 ) )");
      testUtil.doTest("4 √","2", "√( 4 )");
      testUtil.doTest("256 √","16","√( 256 )");
      testUtil.doTest("14 √","3,741657386773941","√( 14 )");
      testUtil.doTest("115 √","10,72380529476361","√( 115 )");
      testUtil.doTest("421 √","20,51828452868319","√( 421 )");
      testUtil.doTest("2372 √","48,70318264754368","√( 2372 )");
      testUtil.doTest("9327 √ ","96,57639463140048","√( 9327 )");
      testUtil.doTest("6813 √","82,54089895318563","√( 6813 )");
      testUtil.doTest("27213 √","164,9636323557408","√( 27213 )");
      testUtil.doTest("462737 √","680,2477489856177","√( 462737 )");
      testUtil.doTest("1465871643875631 √","38 286 703,22547543","√( 1465871643875631 )");

      testUtil.doTest("1,111111111111111 √","1,05409255338946","√( 1,111111111111111 )");
      testUtil.doTest("0,0000000000000001 √ =","0,00000001","");
      testUtil.doTest("0,0000000000000001 √ √ =","0,0001","");
      testUtil.doTest("0,0000000000000001 √ √ √ =","0,01","");
      testUtil.doTest("0,0000000000000001 √ √ √ √ =","0,1","");
      testUtil.doTest("0,0000000000000001 √ √ √ √ √ =","0,3162277660168379","");
      testUtil.doTest("543,2342234 √","23,30738559770272","√( 543,2342234 )");
      testUtil.doTest("15251235,12521351 √","3 905,282976330078","√( 15251235,12521351 )");

   }

   /**
    * testing operation one divide by x
    */
   @Test
   public void testOneDivX() {
      testUtil.doTest("1 1/x =","1", "");
      testUtil.doTest("1 1/x 1/x =","1", "");
      testUtil.doTest("10 1/x =","0,1", "");
      testUtil.doTest("10 1/x 1/x =","10", "");
      testUtil.doTest("10 1/x 1/x 1/x =","0,1", "");
      testUtil.doTest("10 1/x 1/x 1/x 1/x =","10", "");
      testUtil.doTest("977,03 1/x =","0,0010235100252807","");
      testUtil.doTest("288, n 7 1/x =","-0,0034638032559751","");
      testUtil.doTest("263,89 n 1/x =","-0,003789457728599","");
      testUtil.doTest("300,01 n 1/x =","-0,0033332222259258","");
      testUtil.doTest("496,26 n 1/x =","-0,0020150727441261","");
      testUtil.doTest("12312,3 1/x =","8,121959341471537e-5","");
      testUtil.doTest("14078,41 1/x =","7,103074850071848e-5","");
      testUtil.doTest("9999999999999999 1/x =","0,0000000000000001", "");
      testUtil.doTest("9999999999999999 1/x 1/x =","9 999 999 999 999 999", "");
      testUtil.doTest("9999999999999999 1/x 1/x 1/x =","0,0000000000000001", "");
      testUtil.doTest("9999999999999999 1/x 1/x 1/x 1/x =","9 999 999 999 999 999", "");
      testUtil.doTest("0,0000000000000001 1/x =","1,e+16", "");
      testUtil.doTest("0,0000000000000001 1/x =","1,e+16", "");
   }

   /**
    * testing operation negate
    */
   @Test
   public void testNegate() {
      testUtil.doTest("2 n","-2", "");
      testUtil.doTest("2 n n","2", "");
      testUtil.doTest("0 n","0", "");
      testUtil.doTest("n","0", "negate( 0 )");
      testUtil.doTest("n =","0", "");
      testUtil.doTest("n = = = =","0", "");
      testUtil.doTest("nn= = = = =","0", "");

      testUtil.doTest(",","0,","");
      testUtil.doTest(", n","-0,","");
      testUtil.doTest(",0","0,0","");
      testUtil.doTest(",0 n","-0,0","");
      testUtil.doTest(",000 n 500","-0,000500","");
      testUtil.doTest(",0005 n 00","-0,000500","");
      testUtil.doTest(",000500 n","-0,000500","");

      testUtil.doTest("5 n n n - 6 - n n n", "11", "-5   -   6   -   negate( negate( negate( -11 ) ) )");
      testUtil.doTest("20 + 20 = n + 20 -", "-20", "negate( 40 )   +   20   -   ");
      testUtil.doTest("0,1234567891234567 * 0,1 = n", "-0,0123456789123457", "negate( 0,0123456789123457 )");
      testUtil.doTest("n", "0", "negate( 0 )");
      testUtil.doTest("9999999999999999 n", "-9 999 999 999 999 999", "");
      testUtil.doTest("9999999999999999 - n", "-9 999 999 999 999 999", "9999999999999999   -   negate( 9999999999999999 )");
      testUtil.doTest("0,0000000000000001 n", "-0,0000000000000001", "");
      testUtil.doTest("0,0000000000000001 - n", "-0,0000000000000001", "0,0000000000000001   -   negate( 0,0000000000000001 )");
      testUtil.doTest("9999999999999999 n + 9999999999999999 =", "0", "");
      testUtil.doTest("9999999999999999 - n + 9999999999999999 =", "3,e+16", "");
      testUtil.doTest("0,0000000000000001 n + 0,0000000000000001 =", "0", "");
      testUtil.doTest("0,0000000000000001 - n + 0,0000000000000001 =", "0,0000000000000003", "");
      testUtil.doTest("5 n n n - 6 - n n", "-11", "-5   -   6   -   negate( negate( -11 ) )");
      testUtil.doTest("5 n n n - 6 - n n =", "0", "");
      testUtil.doTest("5 n n n - 6 - n n = = = =", "33", "");
      testUtil.doTest("1 / 3 * 3 - 1 n n - n", "0", "1   ÷   3   ×   3   -   1   -   negate( 0 )");
      testUtil.doTest("1 / 3 * 3 - 1 n n - n =", "0", "");
      testUtil.doTest("1 / 3 * 3 - 1 n n - n = =", "0", "");
   }

   /**
    * testing operation percent
    */
   @Test
   public void testPercent() {
      testUtil.doTest("2 %","0", "0");
      testUtil.doTest("2 % % % %","0", "0");
      testUtil.doTest("2 % % % % =","0", "");
      testUtil.doTest("236 + 8913 n %","-21 034,68","236   +   -21034,68");
      testUtil.doTest("0,4 % =","0", "");
      testUtil.doTest("83 n + 6215 %","-5 158,45","-83   +   -5158,45");
      testUtil.doTest("625 + 532 %","3 325","625   +   3325");
      testUtil.doTest("24521 + 532 %","130 451,72","24521   +   130451,72");
      testUtil.doTest("772 n + 37 %","-285,64","-772   +   -285,64");
      testUtil.doTest("236 + 532 %","1 255,52","236   +   1255,52");
      testUtil.doTest("62 n + 716 %","-443,92","-62   +   -443,92");
      testUtil.doTest("4627 + 623 %","28 826,21","4627   +   28826,21");
      testUtil.doTest("0,4 %","0", "0");
      testUtil.doTest("2368 n + 536 %","-12 692,48","-2368   +   -12692,48");
      testUtil.doTest("100 + 25 % =","125", "");
      testUtil.doTest("124616 + 2367 %","2 949 660,72","124616   +   2949660,72");
      testUtil.doTest("24521 + 235362 %","57 713 116,02","24521   +   57713116,02");
      testUtil.doTest("672 + 236 %","1 585,92","672   +   1585,92");
      testUtil.doTest("200 + 25 % % % % % % % % %","12 800", "200   +   12800");
   }
}
