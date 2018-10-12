package view;

import org.junit.jupiter.api.Test;
import org.loadui.testfx.utils.FXTestUtils;

/**
 * Class for testing a work of
 * number and operation buttons.
 * Also it tests a logic of calculator
 */
class KeyAndButtonTest extends UITest {

   /**
    * Test of calculator behaviour
    * with the largest number, the smallest number
    * and smallest number, which bigger than zero
    */
   @Test
   void testTheBiggestAndSmallestValues() {
      FXTestUtils.awaitEvents();

      //testing the biggest value
      enterTheBiggest(false);
      RobotUtil.enterCase("+ 1 =", "Переполнение", "", true);

      enterTheBiggest(false);
      RobotUtil.enterCase("- 1 =", "9,999999999999999e+9999", "", true);

      enterTheBiggest(false);
      RobotUtil.enterCase("- =", "0", "", true);

      //testing the biggest value with the smallest value which bigger then 0
      enterTheBiggest(true);
      enterTheSmallestValueThatBiggerThenZero();
      RobotUtil.enterCase("+ mr =", "Переполнение", "", true);

      enterTheBiggest(true);
      enterTheSmallestValueThatBiggerThenZero();
      RobotUtil.enterCase("- mr =", "9,999999999999999e+9999", "", true);

      enterTheBiggest(false);
      RobotUtil.enterCase("* 10 =", "Переполнение", "", true);

      enterTheBiggest(false);
      RobotUtil.enterCase("sqr =", "Переполнение", "", true);

      enterTheBiggest(false);
      RobotUtil.enterCase("√", "1,e+5000", "√( 9,999999999999999e+9999 )", true);

      //Testing the smallest value
      enterTheSmallest(false);
      RobotUtil.enterCase("- 1 =", "Переполнение", "", true);

      enterTheSmallest(false);
      RobotUtil.enterCase("+ 1 =", "-9,999999999999999e+9999", "", true);

      //Testing the smallest value with the smallest value which bigger then 0
      enterTheSmallest(true);
      enterTheSmallestValueThatBiggerThenZeroNegated();
      RobotUtil.enterCase("+ mr =", "Переполнение", "", true);

      enterTheSmallest(true);
      enterTheSmallestValueThatBiggerThenZeroNegated();
      RobotUtil.enterCase("- mr =", "-9,999999999999999e+9999", "", true);

      enterTheSmallest(false);
      RobotUtil.enterCase("* 10 =", "Переполнение", "", true);

      enterTheSmallest(false);
      RobotUtil.enterCase("√", "Введены неверные данные", "√( -9,999999999999999e+9999 )", true);

      enterTheSmallest(false);
      RobotUtil.enterCase("sqr", "Переполнение", "sqr( -9,999999999999999e+9999 )", true);

      enterTheSmallestValueThatBiggerThenZero();
      RobotUtil.enterCase("/ =", "1", "", true);

      enterTheSmallestValueThatBiggerThenZero();
      RobotUtil.enterCase("/ 1,000000000000001 =", "Переполнение", "", true);

      enterTheSmallestValueThatBiggerThenZero();
      RobotUtil.enterCase("+ =", "2,e-9999", "", true);

      enterTheSmallestValueThatBiggerThenZero();
      RobotUtil.enterCase("- =", "0", "", true);

      enterTheSmallestValueThatBiggerThenZeroNegated();
      RobotUtil.enterCase("/ 2 =", "Переполнение", "", true);

      enterTheSmallestValueThatBiggerThenZeroNegated();
      RobotUtil.enterCase("+ =", "-2,e-9999", "", true);

      enterTheSmallestValueThatBiggerThenZeroNegated();
      RobotUtil.enterCase("- =", "0", "", true);
   }

   /**
    * it's make a robot to type the biggest number
    *
    * @param setInMemory if true - value of the
    *                    largest number will saved to memory
    */
   private void enterTheBiggest(boolean setInMemory) {
      RobotUtil.enterCase("1000000000000000 sqr sqr sqr sqr sqr sqr sqr sqr sqr * 1000000000000000 sqr sqr sqr sqr sqr sqr sqr * 1000000000000000 sqr sqr sqr sqr * " +
              "1000000000000000 sqr sqr sqr * 100000000000 sqr * 10 " +
              "= m+ * 1000000000000000 * 10 = 1/x * 9 = 1/x * 10 - mr = * 9 + mr = = = =", "9,999999999999999e+9999", "", false);
      if (setInMemory) {
         RobotUtil.enterCase("mc m+ =", "9,999999999999999e+9999", "", false);
      }
   }

   /**
    * it's make a robot to type the smallest number
    *
    * @param setInMemory if true - value of the
    *                    smallest number will saved to memory
    */
   private void enterTheSmallest(boolean setInMemory) {
      RobotUtil.enterCase("1000000000000000 sqr sqr sqr sqr sqr sqr sqr sqr sqr * 1000000000000000 sqr sqr sqr sqr sqr sqr sqr * 1000000000000000 sqr sqr sqr sqr * " +
              "1000000000000000 sqr sqr sqr * 100000000000 sqr * 10 " +
              "= m+ * 1000000000000000 * 10 = 1/x * 9 = 1/x * 10 - mr = * 9 + mr = = = = * 1 n =", "-9,999999999999999e+9999", "", false);
      if (setInMemory) {
         RobotUtil.enterCase("mc m+ =", "-9,999999999999999e+9999", "", false);
      }
   }

   /**
    * it's make a robot to type the smallest number that bigger then zero
    */
   private void enterTheSmallestValueThatBiggerThenZero() {
      RobotUtil.enterCase("1000000000000000 sqr sqr sqr sqr sqr sqr sqr sqr sqr * 1000000000000000 sqr sqr sqr sqr sqr sqr sqr * 1000000000000000 sqr sqr sqr sqr * " +
              "1000000000000000 sqr sqr sqr * 100000000000 sqr * 10 * 1000000000000000 * 10 = 1/x * 1 =", "1,e-9999", "", false);
   }

   /**
    * it's make a robot to type the smallest number that bigger then zero
    * but it's negated
    */
   private void enterTheSmallestValueThatBiggerThenZeroNegated() {
      RobotUtil.enterCase("1000000000000000 sqr sqr sqr sqr sqr sqr sqr sqr sqr * 1000000000000000 sqr sqr sqr sqr sqr sqr sqr * 1000000000000000 sqr sqr sqr sqr * " +
              "1000000000000000 sqr sqr sqr * 100000000000 sqr * 10 * 1000000000000000 * 10 = 1/x * 1 n =", "-1,e-9999", "", false);
   }

   /**
    * Testing of behaviour of calculator with normal
    * values. Also tests a logic of calculator.
    * Works via emulating a pressing of keyboard keys
    */
   @Test
   void testKeys() {
      FXTestUtils.awaitEvents();
      RobotUtil.enterCaseByKey("1", "1", "");
      RobotUtil.enterCaseByKey("2", "2", "");
      RobotUtil.enterCaseByKey("3", "3", "");
      RobotUtil.enterCaseByKey("4", "4", "");
      RobotUtil.enterCaseByKey("5", "5", "");
      RobotUtil.enterCaseByKey("6", "6", "");
      RobotUtil.enterCaseByKey("7", "7", "");
      RobotUtil.enterCaseByKey("8", "8", "");
      RobotUtil.enterCaseByKey("9", "9", "");
      RobotUtil.enterCaseByKey("1234567890 + 9", "9", "1234567890   +   ");
      RobotUtil.enterCaseByKey("1234567890 backspace backspace 11 backspace", "123 456 781", "");
      RobotUtil.enterCaseByKey("1234567890 backspace backspace 11 backspace + 5 + 135135 ce", "0", "123456781   +   5   +   ");
      RobotUtil.enterCaseByKey("1234567890 backspace backspace 11 backspace + 5 + 135135 c", "0", "");
      RobotUtil.enterCaseByKey("1234567890 + 9 =", "1 234 567 899", "");
      RobotUtil.enterCaseByKey("1234567890 - 9", "9", "1234567890   -   ");
      RobotUtil.enterCaseByKey("1234567890 - 9 =", "1 234 567 881", "");
      RobotUtil.enterCaseByKey("2 + 2 * 2 =", "8", "");
      RobotUtil.enterCaseByKey("1,5 * 1,5 =", "2,25", "");
      RobotUtil.enterCaseByKey("0 / 1,5 =", "0", "");
      RobotUtil.enterCaseByKey("5 n + 5 n =", "-10", "");
      RobotUtil.enterCaseByKey("2 sqr", "4", "sqr( 2 )");
      RobotUtil.enterCaseByKey("4 √", "2", "√( 4 )");
      RobotUtil.enterCaseByKey("10 1/x =", "0,1", "");
      RobotUtil.enterCaseByKey("100 + 25 % =", "125", "");
   }

   /**
    * Testing of behaviour of calculator with normal
    * values. Also tests a logic of calculator.
    * Works via emulating a clicking on buttons
    */
   @Test
   void testButtons() {
      FXTestUtils.awaitEvents();
      RobotUtil.enterCase("1", "1", "");
      RobotUtil.enterCase("2", "2", "");
      RobotUtil.enterCase("3", "3", "");
      RobotUtil.enterCase("4", "4", "");
      RobotUtil.enterCase("5", "5", "");
      RobotUtil.enterCase("6", "6", "");
      RobotUtil.enterCase("7", "7", "");
      RobotUtil.enterCase("8", "8", "");
      RobotUtil.enterCase("9", "9", "");
      RobotUtil.enterCase("1234567890 + 9", "9", "1234567890   +   ");
      RobotUtil.enterCase("1234567890 + 9 =", "1 234 567 899", "");
      RobotUtil.enterCase("1234567890 - 9", "9", "1234567890   -   ");
      RobotUtil.enterCase("1234567890 - 9 =", "1 234 567 881", "");
      RobotUtil.enterCase("2 + 2 * 2 =", "8", "");
      RobotUtil.enterCase("2 + 2 / 2 =", "2", "");
      RobotUtil.enterCase("1,5 * 1,5 =", "2,25", "");
      RobotUtil.enterCase("5 n + 5 n =", "-10", "");
      RobotUtil.enterCase("2 sqr", "4", "sqr( 2 )");
      RobotUtil.enterCase("4 √", "2", "√( 4 )");
      RobotUtil.enterCase("10 1/x =", "0,1", "");
      RobotUtil.enterCase("100 + 25 % =", "125", "");
      RobotUtil.enterCase("000000000", "0", "");
      RobotUtil.enterCase("0000,00000", "0,00000", "");
      RobotUtil.enterCase("00000,000000000000000000000", "0,0000000000000000", "");
      RobotUtil.enterCase("11111111111111111111111111", "1 111 111 111 111 111", "");
      RobotUtil.enterCase("111111111111111111111111111111", "1 111 111 111 111 111", "");
      RobotUtil.enterCase("11111111111111111111111111111100", "1 111 111 111 111 111", "");
   }
}