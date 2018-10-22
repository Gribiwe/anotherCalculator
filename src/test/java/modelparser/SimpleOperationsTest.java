package modelparser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Class for testing simple operations
 * like plus, subtract, multiple, etc
 */
class SimpleOperationsTest {

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
    * testing operation plus
    */
   @Test
   void testPlus() {
      testUtil.doTest("0 +", "0", "0   +   ");
      testUtil.doTest("+", "0", "0   +   ");

      testUtil.doTest("1 + 0 +", "1", "1   +   0   +   ");
      testUtil.doTest("2 + 0 +", "2", "2   +   0   +   ");
      testUtil.doTest("5 + 0 +", "5", "5   +   0   +   ");
      testUtil.doTest("9 + 0 +", "9", "9   +   0   +   ");
      testUtil.doTest("17 + 0 +", "17", "17   +   0   +   ");
      testUtil.doTest("99 + 0 +", "99", "99   +   0   +   ");
      testUtil.doTest("99999 + 0 +", "99 999", "99999   +   0   +   ");

      testUtil.doTest("1 + 0 =", "1", "");
      testUtil.doTest("2 + 0 =", "2", "");
      testUtil.doTest("5 + 0 =", "5", "");
      testUtil.doTest("9 + 0 =", "9", "");
      testUtil.doTest("17 + 0 =", "17", "");
      testUtil.doTest("99 + 0 =", "99", "");
      testUtil.doTest("99999 + 0 =", "99 999", "");

      testUtil.doTest("0 + 1 +", "1", "0   +   1   +   ");
      testUtil.doTest("0 + 2 +", "2", "0   +   2   +   ");
      testUtil.doTest("0 + 5 +", "5", "0   +   5   +   ");
      testUtil.doTest("0 + 9 +", "9", "0   +   9   +   ");
      testUtil.doTest("0 + 17 +", "17", "0   +   17   +   ");
      testUtil.doTest("0 + 99 +", "99", "0   +   99   +   ");
      testUtil.doTest("0 + 99999 +", "99 999", "0   +   99999   +   ");

      testUtil.doTest("0 + 1 =", "1", "");
      testUtil.doTest("0 + 2 =", "2", "");
      testUtil.doTest("0 + 5 =", "5", "");
      testUtil.doTest("0 + 9 =", "9", "");
      testUtil.doTest("0 + 17 =", "17", "");
      testUtil.doTest("0 + 99 =", "99", "");
      testUtil.doTest("0 + 99999 =", "99 999", "");

      testUtil.doTest("1 +", "1", "1   +   ");
      testUtil.doTest("2 +", "2", "2   +   ");
      testUtil.doTest("5 +", "5", "5   +   ");
      testUtil.doTest("9 +", "9", "9   +   ");
      testUtil.doTest("17 +", "17", "17   +   ");
      testUtil.doTest("99 +", "99", "99   +   ");
      testUtil.doTest("99999 +", "99 999", "99999   +   ");

      testUtil.doTest("1 + =", "2", "");
      testUtil.doTest("2 + =", "4", "");
      testUtil.doTest("5 + =", "10", "");
      testUtil.doTest("9 + =", "18", "");
      testUtil.doTest("17 + =", "34", "");
      testUtil.doTest("99 + =", "198", "");
      testUtil.doTest("99999 + =", "199 998", "");

      testUtil.doTest("1 + 1", "1", "1   +   ");
      testUtil.doTest("2 + 1", "1", "2   +   ");
      testUtil.doTest("5 + 1", "1", "5   +   ");
      testUtil.doTest("9 + 1", "1", "9   +   ");
      testUtil.doTest("17 + 1", "1", "17   +   ");
      testUtil.doTest("99 + 1", "1", "99   +   ");
      testUtil.doTest("99999 + 1", "1", "99999   +   ");

      testUtil.doTest("1 + 1", "1", "1   +   ");
      testUtil.doTest("1 + 2", "2", "1   +   ");
      testUtil.doTest("1 + 5", "5", "1   +   ");
      testUtil.doTest("1 + 9", "9", "1   +   ");
      testUtil.doTest("1 + 17", "17", "1   +   ");
      testUtil.doTest("1 + 99", "99", "1   +   ");
      testUtil.doTest("1 + 99999", "99 999", "1   +   ");

      testUtil.doTest("1 + 1 =", "2", "");
      testUtil.doTest("2 + 1 =", "3", "");
      testUtil.doTest("5 + 1 =", "6", "");
      testUtil.doTest("9 + 1 =", "10", "");
      testUtil.doTest("17 + 1 =", "18", "");
      testUtil.doTest("99 + 1 =", "100", "");
      testUtil.doTest("99999 + 1 =", "100 000", "");

      testUtil.doTest("1 + 2 =", "3", "");
      testUtil.doTest("1 + 5 =", "6", "");
      testUtil.doTest("1 + 9 =", "10", "");
      testUtil.doTest("1 + 17 =", "18", "");
      testUtil.doTest("1 + 99 =", "100", "");
      testUtil.doTest("1 + 99999 =", "100 000", "");

      testUtil.doTest("1 + 1 + 1", "1", "1   +   1   +   ");
      testUtil.doTest("2 + 2 + 1", "1", "2   +   2   +   ");
      testUtil.doTest("5 + 5 + 1", "1", "5   +   5   +   ");
      testUtil.doTest("9 + 9 + 1", "1", "9   +   9   +   ");
      testUtil.doTest("17 + 17 + 1", "1", "17   +   17   +   ");
      testUtil.doTest("99 + 99 + 1", "1", "99   +   99   +   ");
      testUtil.doTest("99999 + 99999 + 1", "1", "99999   +   99999   +   ");

      testUtil.doTest("1 + 1 + 1 +", "3", "1   +   1   +   1   +   ");
      testUtil.doTest("2 + 2 + 1 +", "5", "2   +   2   +   1   +   ");
      testUtil.doTest("5 + 5 + 1 +", "11", "5   +   5   +   1   +   ");
      testUtil.doTest("9 + 9 + 1 +", "19", "9   +   9   +   1   +   ");
      testUtil.doTest("17 + 17 + 1 +", "35", "17   +   17   +   1   +   ");
      testUtil.doTest("99 + 99 + 1 +", "199", "99   +   99   +   1   +   ");
      testUtil.doTest("99999 + 99999 + 1 +", "199 999", "99999   +   99999   +   1   +   ");

      testUtil.doTest("1 + 1 + 1 + =", "6", "");
      testUtil.doTest("2 + 2 + 1 + =", "10", "");
      testUtil.doTest("5 + 5 + 1 + =", "22", "");
      testUtil.doTest("9 + 9 + 1 + =", "38", "");
      testUtil.doTest("17 + 17 + 1 + =", "70", "");
      testUtil.doTest("99 + 99 + 1 + =", "398", "");
      testUtil.doTest("99999 + 99999 + 1 + =", "399 998", "");

      testUtil.doTest("1 + 1 = =", "3", "");
      testUtil.doTest("1000000000000000 + = = = = = = = =", "9 000 000 000 000 000", "");
      testUtil.doTest("1000000000000000 + = = = = = = = = =", "1,e+16", "");
      testUtil.doTest("1000000000000000 + = = = = = = = = = +", "1,e+16", "1,e+16   +   ");
      testUtil.doTest("1000000000000000 + = = = = = = = = = + 1 =", "1,e+16", "");

      testUtil.doTest("1 + 1 +", "2", "1   +   1   +   ");
      testUtil.doTest("1 + 1 + +", "2", "1   +   1   +   ");
      testUtil.doTest("1 + 1 + + + + + + +", "2", "1   +   1   +   ");

      testUtil.doTest(",0000000000000 +", "0", "0   +   ");
      testUtil.doTest(",000000000000000000 +", "0", "0   +   ");
      testUtil.doTest("0000000,000000000000000000 +", "0", "0   +   ");
      testUtil.doTest("00000000000000 +", "0", "0   +   ");
      testUtil.doTest("+", "0", "0   +   ");
   }

   /**
    * testing operation subtract
    */
   @Test
   void testSubtract() {
      testUtil.doTest("0 -", "0", "0   -   ");
      testUtil.doTest("-", "0", "0   -   ");

      testUtil.doTest("1 - 0 -", "1", "1   -   0   -   ");
      testUtil.doTest("2 - 0 -", "2", "2   -   0   -   ");
      testUtil.doTest("5 - 0 -", "5", "5   -   0   -   ");
      testUtil.doTest("9 - 0 -", "9", "9   -   0   -   ");
      testUtil.doTest("17 - 0 -", "17", "17   -   0   -   ");
      testUtil.doTest("99 - 0 -", "99", "99   -   0   -   ");
      testUtil.doTest("99999 - 0 -", "99 999", "99999   -   0   -   ");

      testUtil.doTest("1 - 0 =", "1", "");
      testUtil.doTest("2 - 0 =", "2", "");
      testUtil.doTest("5 - 0 =", "5", "");
      testUtil.doTest("9 - 0 =", "9", "");
      testUtil.doTest("17 - 0 =", "17", "");
      testUtil.doTest("99 - 0 =", "99", "");
      testUtil.doTest("99999 - 0 =", "99 999", "");

      testUtil.doTest("0 - 1 -", "-1", "0   -   1   -   ");
      testUtil.doTest("0 - 2 -", "-2", "0   -   2   -   ");
      testUtil.doTest("0 - 5 -", "-5", "0   -   5   -   ");
      testUtil.doTest("0 - 9 -", "-9", "0   -   9   -   ");
      testUtil.doTest("0 - 17 -", "-17", "0   -   17   -   ");
      testUtil.doTest("0 - 99 -", "-99", "0   -   99   -   ");
      testUtil.doTest("0 - 99999 -", "-99 999", "0   -   99999   -   ");

      testUtil.doTest("0 - 1 =", "-1", "");
      testUtil.doTest("0 - 2 =", "-2", "");
      testUtil.doTest("0 - 5 =", "-5", "");
      testUtil.doTest("0 - 9 =", "-9", "");
      testUtil.doTest("0 - 17 =", "-17", "");
      testUtil.doTest("0 - 99 =", "-99", "");
      testUtil.doTest("0 - 99999 =", "-99 999", "");

      testUtil.doTest("1 -", "1", "1   -   ");
      testUtil.doTest("2 -", "2", "2   -   ");
      testUtil.doTest("5 -", "5", "5   -   ");
      testUtil.doTest("9 -", "9", "9   -   ");
      testUtil.doTest("17 -", "17", "17   -   ");
      testUtil.doTest("99 -", "99", "99   -   ");
      testUtil.doTest("99999 -", "99 999", "99999   -   ");

      testUtil.doTest("1 - =", "0", "");
      testUtil.doTest("2 - =", "0", "");
      testUtil.doTest("5 - =", "0", "");
      testUtil.doTest("9 - =", "0", "");
      testUtil.doTest("17 - =", "0", "");
      testUtil.doTest("99 - =", "0", "");
      testUtil.doTest("99999 - =", "0", "");

      testUtil.doTest("1 - 1", "1", "1   -   ");
      testUtil.doTest("2 - 1", "1", "2   -   ");
      testUtil.doTest("5 - 1", "1", "5   -   ");
      testUtil.doTest("9 - 1", "1", "9   -   ");
      testUtil.doTest("17 - 1", "1", "17   -   ");
      testUtil.doTest("99 - 1", "1", "99   -   ");
      testUtil.doTest("99999 - 1", "1", "99999   -   ");

      testUtil.doTest("1 - 1", "1", "1   -   ");
      testUtil.doTest("1 - 2", "2", "1   -   ");
      testUtil.doTest("1 - 5", "5", "1   -   ");
      testUtil.doTest("1 - 9", "9", "1   -   ");
      testUtil.doTest("1 - 17", "17", "1   -   ");
      testUtil.doTest("1 - 99", "99", "1   -   ");
      testUtil.doTest("1 - 99999", "99 999", "1   -   ");

      testUtil.doTest("1 - 1 =", "0", "");
      testUtil.doTest("2 - 1 =", "1", "");
      testUtil.doTest("5 - 1 =", "4", "");
      testUtil.doTest("9 - 1 =", "8", "");
      testUtil.doTest("17 - 1 =", "16", "");
      testUtil.doTest("99 - 1 =", "98", "");
      testUtil.doTest("99999 - 1 =", "99 998", "");

      testUtil.doTest("1 - 2 =", "-1", "");
      testUtil.doTest("1 - 5 =", "-4", "");
      testUtil.doTest("1 - 9 =", "-8", "");
      testUtil.doTest("1 - 17 =", "-16", "");
      testUtil.doTest("1 - 99 =", "-98", "");
      testUtil.doTest("1 - 99999 =", "-99 998", "");

      testUtil.doTest("1 - 1 - 1", "1", "1   -   1   -   ");
      testUtil.doTest("2 - 2 - 1", "1", "2   -   2   -   ");
      testUtil.doTest("5 - 5 - 1", "1", "5   -   5   -   ");
      testUtil.doTest("9 - 9 - 1", "1", "9   -   9   -   ");
      testUtil.doTest("17 - 17 - 1", "1", "17   -   17   -   ");
      testUtil.doTest("99 - 99 - 1", "1", "99   -   99   -   ");
      testUtil.doTest("99999 - 99999 - 1", "1", "99999   -   99999   -   ");

      testUtil.doTest("1 - 1 - 1 -", "-1", "1   -   1   -   1   -   ");
      testUtil.doTest("2 - 2 - 1 -", "-1", "2   -   2   -   1   -   ");
      testUtil.doTest("5 - 5 - 1 -", "-1", "5   -   5   -   1   -   ");
      testUtil.doTest("9 - 9 - 1 -", "-1", "9   -   9   -   1   -   ");
      testUtil.doTest("17 - 17 - 1 -", "-1", "17   -   17   -   1   -   ");
      testUtil.doTest("99 - 99 - 1 -", "-1", "99   -   99   -   1   -   ");
      testUtil.doTest("99999 - 99999 - 1 -", "-1", "99999   -   99999   -   1   -   ");

      testUtil.doTest("1 - 1 - 1 - =", "0", "");
      testUtil.doTest("2 - 2 - 1 - =", "0", "");
      testUtil.doTest("5 - 5 - 1 - =", "0", "");
      testUtil.doTest("9 - 9 - 1 - =", "0", "");
      testUtil.doTest("17 - 17 - 1 - =", "0", "");
      testUtil.doTest("99 - 99 - 1 - =", "0", "");
      testUtil.doTest("99999 - 99999 - 1 - =", "0", "");

      testUtil.doTest("1 - 1 = =", "-1", "");
      testUtil.doTest("1000000000000000 - = = = = = = = = =", "-8 000 000 000 000 000", "");
      testUtil.doTest("1000000000000000 - = = = = = = = = = =", "-9 000 000 000 000 000", "");
      testUtil.doTest("1000000000000000 - = = = = = = = = = = =", "-1,e+16", "");
      testUtil.doTest("1000000000000000 - = = = = = = = = = = = - 1 -", "-1,e+16", "-1,e+16   -   1   -   ");
      testUtil.doTest("1000000000000000 - = = = = = = = = = = = - 1 - 1 -", "-1,e+16", "-1,e+16   -   1   -   1   -   ");
      testUtil.doTest("1000000000000000 - = = = = = = = = = = = - 1 =", "-1,e+16", "");

      testUtil.doTest("1 - 1 -", "0", "1   -   1   -   ");
      testUtil.doTest("1 - 1 - -", "0", "1   -   1   -   ");
      testUtil.doTest("1 - 1 - - - - - - -", "0", "1   -   1   -   ");

      testUtil.doTest(",0000000000000 -", "0", "0   -   ");
      testUtil.doTest(",000000000000000000 -", "0", "0   -   ");
      testUtil.doTest("0000000,000000000000000000 -", "0", "0   -   ");
      testUtil.doTest("00000000000000 -", "0", "0   -   ");
      testUtil.doTest("-", "0", "0   -   ");
   }

   /**
    * testing operation divide
    */
   @Test
   void testDivide() {
      // zero and one dividing
      testUtil.doTest("0 / 1 =", "0", "");
      testUtil.doTest("0 / 1 /", "0", "0   ÷   1   ÷   ");
      testUtil.doTest("1 / 1 /", "1", "1   ÷   1   ÷   ");
      testUtil.doTest("1 / 1 =", "1", "");
      testUtil.doTest("2 / 1 =", "2", "");
      testUtil.doTest("3 / 1 =", "3", "");
      testUtil.doTest("7 / 7 =", "1", "");
      testUtil.doTest("2 / 2 =", "1", "");
      testUtil.doTest("3 / 3 =", "1", "");

      // real values and rounding
      testUtil.doTest("1 / 3 * 3 =", "1", "");
      testUtil.doTest("1 / 3 * 3 - 1 =", "0", "");
      testUtil.doTest("5 / 3 /", "1,666666666666667", "5   ÷   3   ÷   ");
      testUtil.doTest("5 / 3 =", "1,666666666666667", "");
      testUtil.doTest("5 / 3 = =", "0,5555555555555556", "");
      testUtil.doTest("5 / 3 = = =", "0,1851851851851852", "");
      testUtil.doTest("5 / 3 = = = =", "0,0617283950617284", "");
      testUtil.doTest("5 / 3 = = = = / 100 =", "6,17283950617284e-4", "");
      testUtil.doTest("5 / 10000 / 3 = =", "5,555555555555556e-5", "");
      testUtil.doTest("10 / 3 =", "3,333333333333333", "");
      testUtil.doTest("10 / 3 = =", "1,111111111111111", "");
      testUtil.doTest("10 / 3 = = =", "0,3703703703703704", "");
      testUtil.doTest("123456789 / 987654321 =", "0,1249999988609375", "");
      testUtil.doTest("5 / 3000 =", "0,0016666666666667", "");

      // simple dividing
      testUtil.doTest("25 / 5 /", "5", "25   ÷   5   ÷   ");
      testUtil.doTest("25 / 5 =", "5", "");
      testUtil.doTest("25 / 5 =", "5", "");
      testUtil.doTest("4 / 2 =", "2", "");
      testUtil.doTest("4 / 2 = =", "1", "");
      testUtil.doTest("14 / 7 =", "2", "");
      testUtil.doTest("14 / 7 /", "2", "14   ÷   7   ÷   ");
      testUtil.doTest("21 / 7 =", "3", "");
      testUtil.doTest("21 / 3 =", "7", "");
      testUtil.doTest("48 / 2 =", "24", "");
      testUtil.doTest("48 / 4 =", "12", "");
      testUtil.doTest("48 / 4 /", "12", "48   ÷   4   ÷   ");
      testUtil.doTest("48 / 8 =", "6", "");
      testUtil.doTest("48 / 6 =", "8", "");
      testUtil.doTest("48 / 6 /", "8", "48   ÷   6   ÷   ");
      testUtil.doTest("48 / 6 =", "8", "");
      testUtil.doTest("1000000000000000 / =", "1", "");
      testUtil.doTest("1000000000000000 / 1000000000000000 =", "1", "");
      testUtil.doTest("1000000000000000 / 2 =", "500 000 000 000 000", "");
      testUtil.doTest("500000000000000 / 2 =", "250 000 000 000 000", "");
      testUtil.doTest("250000000000000 / 25 =", "10 000 000 000 000", "");

      // evil (e) values
      testUtil.doTest("1 / 1000000000000000 / 10 =", "0,0000000000000001", "");
      testUtil.doTest("1 / 1000000000000000 / 11 =", "9,090909090909091e-17", "");
      testUtil.doTest("1 / 1000000000000000 / 10 / 10 =", "1,e-17", "");
      testUtil.doTest("1 / 1000000000000000 = =", "1,e-30", "");
      testUtil.doTest("1 / 1000000000000000 = = =", "1,e-45", "");
      testUtil.doTest("1 / 1000000000000000 = = = =", "1,e-60", "");
      testUtil.doTest("1 / 1000000000000000 = = = = =", "1,e-75", "");
      testUtil.doTest("1 / 0,0000000000000001 =", "1,e+16", "");
      testUtil.doTest("1 / 0,0000000000000001 = =", "1,e+32", "");
   }

   /**
    * testing operation multiple
    */
   @Test
   void testMultiple() {
      // simple numbers
      testUtil.doTest("1 / 3 * 0,0000000000000001 * 0,00000000001 * 10000000000000000 * 10000000000000 * 3 *", "10", "1   ÷   3   ×   0,0000000000000001   ×   0,00000000001   ×   1000000000000000   ×   10000000000000   ×   3   ×   ");
      testUtil.doTest("0 * 2 =", "0", "");
      testUtil.doTest("0 * 0 =", "0", "");
      testUtil.doTest("2 * 0 =", "0", "");
      testUtil.doTest("2 * 2 =", "4", "");
      testUtil.doTest("1 * 1 =", "1", "");
      testUtil.doTest("1 * * * * 1 =", "1", "");
      testUtil.doTest("1 * * * * 1", "1", "1   ×   ");
      testUtil.doTest("1 / 3 * 10000000000 - 3333333333 * 10000000000 - 3333333333 * 10000000000 - 3333333333 * 10000000000 - 3333333333 * 10000000000 - 3333333333 * 10000000000 - 3333333333 * 3 - 1 =", "0", "");
      testUtil.doTest("1 * 1 =", "1", "");
      testUtil.doTest("1 * 1000000000000000 =", "1 000 000 000 000 000", "");
      testUtil.doTest("123 * 123 * 321 * 321 =", "1 558 907 289", "");
      testUtil.doTest("321 * 321 * 123 * 123 =", "1 558 907 289", "");
      testUtil.doTest("321 * 321 * 123 * 123 *", "1 558 907 289", "321   ×   321   ×   123   ×   123   ×   ");
      testUtil.doTest("7 * 7 * 3 * 11 * 23 =", "37 191", "");
      testUtil.doTest("333 * 777 * 23 * 111 *", "660 565 773", "333   ×   777   ×   23   ×   111   ×   ");

      // real numbers
      testUtil.doTest("1,5 * 10 =", "15", "");
      testUtil.doTest("0,5 * 10 =", "5", "");
      testUtil.doTest("1,75 * 10 =", "17,5", "");
      testUtil.doTest("1,75 * 0,1 =", "0,175", "");
      testUtil.doTest("123123,123123 * 123123 =", "15 159 288 288,27313", "");
      testUtil.doTest("123,443 * 7777 * 111,000111000222 * 123124 =", "13 120 328 112 252,44", "");
      testUtil.doTest("52352,13513 * 1111,3333451 * * * * 1 * 2 =", "116 361 346,9143002", "");
      testUtil.doTest("52352,13513 * 1111,3333451 * * * * 1 * 2 *", "116 361 346,9143002", "52352,13513   ×   1111,3333451   ×   1   ×   2   ×   ");
      testUtil.doTest(",123123 * 123123 * =", "229 803 561,7996215", "");
      testUtil.doTest("0,9999999999999999 * 999999999999999,1 =", "999 999 999 999 999", "");
      testUtil.doTest("0,9999999999999999 * 999999999999999,1 =", "999 999 999 999 999", "");
      testUtil.doTest("0,9999999999999999 * 999999999999999,9 =", "999 999 999 999 999,8", "");
      testUtil.doTest("0,9999999999999999 * 999999999999999,9 *", "999 999 999 999 999,8", "0,9999999999999999   ×   999999999999999,9   ×   ");

      // evil numbers
      testUtil.doTest("10 / 3 = = * 1000000000000000 = = = = = = = =", "1,111111111111111e+120", "");
      testUtil.doTest("10 / 3 = = * 1000000000000000 = = = = = = = = * 3 = ", "3,333333333333333e+120", "");
      testUtil.doTest("10 / 3 = * 1000000000000000 = = = = = = = =", "3,333333333333333e+120", "");
      testUtil.doTest("111111111,1111111 * =", "1,234567901234568e+16", "");
      testUtil.doTest("111111111,1111111 * = =", "1,371742112482853e+24", "");
      testUtil.doTest("777,777 * 7777777777777777 =", "6,049376666666666e+18", "");
      testUtil.doTest("333 * 777 * 23 * 111 * =", "4,363471404590875e+17", "");
      testUtil.doTest("1 * 1000000000000000 = =", "1,e+30", "");
      testUtil.doTest("1 * 1000000000000000 = = =", "1,e+45", "");
      testUtil.doTest("1 * 1000000000000000 = = = =", "1,e+60", "");
      testUtil.doTest("9999999999999999 * 9999999999999999 =", "9,999999999999998e+31", "");
      testUtil.doTest("9999999999999999 * 9999999999999999 = =", "9,999999999999997e+47", "");
      testUtil.doTest("9999999999999999 * * * * * 9999999999999999 = =", "9,999999999999997e+47", "");
      testUtil.doTest("0,6257577525824582 * 0,0003444444532000 =", "2,155387869239257e-4", "");
      testUtil.doTest("1552467,3733278 * = * 1245151515533356 * = * 4551347,3966122 =", "4,098966000338904e+61", "");
   }
}
