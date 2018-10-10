package view;

import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.Test;
import org.loadui.testfx.utils.FXTestUtils;
import org.testfx.util.WaitForAsyncUtils;

/**
 * class for testing of exceptions
 *
 * @author Gribiwe
 */
class ExceptionTest extends UITest {

   /**
    * open memory button
    */
   public static AnchorPane button_ms;

   /**
    * subtract from memory button
    */
   public static AnchorPane button_msub;

   /**
    * add to memory button
    */
   public static AnchorPane button_mp;

   /**
    * percent operation button
    */
   public static AnchorPane button_percent;

   /**
    * root operation button
    */
   public static AnchorPane button_root;

   /**
    * square operation button
    */
   public static AnchorPane button_square;

   /**
    * one divide by x operation button
    */
   public static AnchorPane button_onedix;

   /**
    * divide operation button
    */
   public static AnchorPane button_divide;

   /**
    * multiple operation button
    */
   public static AnchorPane button_multiple;

   /**
    * subtract operation button
    */
   public static AnchorPane button_subtract;

   /**
    * plus operation button
    */
   public static AnchorPane button_plus;

   /**
    * negate operation button
    */
   public static AnchorPane button_negate;

   /**
    * add point button
    */
   public static AnchorPane button_point;

   /**
    * default constructor for initialization
    * of buttons
    */
   public ExceptionTest() {
      button_ms = (AnchorPane) WindowUtil.getNode("#button_ms");
      button_msub = (AnchorPane) WindowUtil.getNode("#button_msub");
      button_mp = (AnchorPane) WindowUtil.getNode("#button_mp");
      button_percent = (AnchorPane) WindowUtil.getNode("#button_percent");
      button_root = (AnchorPane) WindowUtil.getNode("#button_root");
      button_square = (AnchorPane) WindowUtil.getNode("#button_square");
      button_onedix = (AnchorPane) WindowUtil.getNode("#button_onedix");
      button_divide = (AnchorPane) WindowUtil.getNode("#button_divide");
      button_multiple = (AnchorPane) WindowUtil.getNode("#button_multiple");
      button_subtract = (AnchorPane) WindowUtil.getNode("#button_subtract");
      button_plus = (AnchorPane) WindowUtil.getNode("#button_plus");
      button_negate = (AnchorPane) WindowUtil.getNode("#button_negate");
      button_point = (AnchorPane) WindowUtil.getNode("#button_point");
   }

   /**
    * testing of dividing zero by zero
    * and error message which appears
    * after it
    */
   @Test
   public void testUncertainException() {
      testException("0 / 0 =", "Результат не определен", "0   ÷   ", "135");
      testException("0 / 0 =", "Результат не определен", "0   ÷   ", "=", "0");
      testException("0 / 0 =", "Результат не определен", "0   ÷   ", "backspace", "0");
      testException("0 / 0 =", "Результат не определен", "0   ÷   ", "ce", "0");
      testException("0 / 0 =", "Результат не определен", "0   ÷   ", "c", "0");
      testException("0,000000000000000000000 / 0 =", "Результат не определен", "0   ÷   ", "c", "0");
   }

   /**
    * test error message
    * which appears after dividing by zero
    */
   @Test
   public void testZeroDivideException() {
      testException("1 / 0 =", "Деление на ноль невозможно", "1   ÷   ", "135");
      testException("2 / 0 =", "Деление на ноль невозможно", "2   ÷   ", "=", "0");
      testException("10 / 0 =", "Деление на ноль невозможно", "10   ÷   ", "ce", "0");
      testException("9999999999999999 / 0 =", "Деление на ноль невозможно", "9999999999999999   ÷   ", "backspace", "0");
      testException("99999999999999999999 / 0 =", "Деление на ноль невозможно", "9999999999999999   ÷   ", "ce", "0");
      testException("0,0000000000000001111111 / 0 =", "Деление на ноль невозможно", "0,0000000000000001   ÷   ", "c", "0");
   }

   /**
    * test error message
    * which appears after making root of negated value
    */
   @Test
   public void testUncorrectedDataException() {
      testException("2 - 4 = √", "Введены неверные данные", "√( -2 )", "135");
      testException("2 - 4 - √", "Введены неверные данные", "2   -   4   -   √( -2 )", "135");
      testException("4 n √", "Введены неверные данные", "√( -4 )", "135");
      testException("4 n + 4 - 1 = √", "Введены неверные данные", "√( -1 )", "135");
      testException("4 n + 4 - 1 - √", "Введены неверные данные", "-4   +   4   -   1   -   √( -1 )", "135");
   }

   /**
    * testing error message
    * which appears after becoming value
    * bigger than 9999999999999999,e+9999
    */
   @Test
   public void testOverflowException() {
      FXTestUtils.awaitEvents();
      testException("9999999999999999 sqr sqr sqr sqr sqr sqr sqr sqr sqr sqr", "Переполнение", "sqr( sqr( sqr( sqr( sqr( sqr( sqr( sqr( sqr( sqr( 9999999999999999 ) ) ) ) ) ) ) ) ) )", "135");
      testException("0,0000000000000001 sqr sqr sqr sqr sqr sqr sqr sqr sqr sqr", "Переполнение", "sqr( sqr( sqr( sqr( sqr( sqr( sqr( sqr( sqr( sqr( 0,0000000000000001 ) ) ) ) ) ) ) ) ) )", "135");
   }

   /**
    * test method for exception messages
    *
    * @param actionSequence  string value of actions
    *                        robot will do
    * @param expectedOut     string value of expected value of
    *                        input (or output) number
    * @param expectedHistory string value of expected
    *                        history line
    * @param numberForReLife string value of number
    *                        robot will enter after exception
    */
   private void testException(String actionSequence, String expectedOut, String expectedHistory, String numberForReLife) {
      testException(actionSequence, expectedOut, expectedHistory, numberForReLife, numberForReLife);
   }

   /**
    * test method for exception messages
    *
    * @param actionSequence         string value of actions
    *                               robot will do
    * @param expectedOut            string value of expected value of
    *                               input (or output) number
    * @param expectedHistory        string value of expected
    *                               history line
    * @param numberForReLife        string value of number
    *                               robot will enter after exception
    * @param expectedOutAfterReLife string value of expected value of
    *                               input (or output) after entering a number
    *                               after exception
    */
   private void testException(String actionSequence, String expectedOut, String expectedHistory, String numberForReLife, String expectedOutAfterReLife) {
      RobotUtil.enterCase(actionSequence, expectedOut, expectedHistory, false);
      checkDisabled(true);
      RobotUtil.enterCase(numberForReLife, expectedOutAfterReLife, "");
      checkDisabled(false);
   }

   /**
    * test method for checking is buttons
    * of operations and memory are disabled or not
    *
    * @param disabled if true - checks that buttons
    *                 are disabled. False - that buttons are enabled
    */
   private void checkDisabled(boolean disabled) {
      WaitForAsyncUtils.waitForFxEvents();
      assertEquals(disabled, button_msub.isDisable());
      assertEquals(disabled, button_percent.isDisable());
      assertEquals(disabled, button_root.isDisable());
      assertEquals(disabled, button_square.isDisable());
      assertEquals(disabled, button_onedix.isDisable());
      assertEquals(disabled, button_divide.isDisable());
      assertEquals(disabled, button_multiple.isDisable());
      assertEquals(disabled, button_subtract.isDisable());
      assertEquals(disabled, button_plus.isDisable());
      assertEquals(disabled, button_negate.isDisable());
      assertEquals(disabled, button_point.isDisable());
   }

}
