package view;

import javafx.scene.Node;
import javafx.scene.control.Label;
import org.junit.Assert;
import org.loadui.testfx.GuiTest;
import org.testfx.matcher.control.LabeledMatchers;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Class which represents a something like user
 * which can type buttons or click on them;
 *
 * @author Gribiwe
 */
class RobotUtil extends Assert {

   /**
    * Time for waiting an expected value of label.
    */
   private static final int SECONDS_FOR_WAIT = 4;

   /**
    * Clicks at buttons by mouse, moving mouse
    * and even type the buttons.
    */
   private static Robot robot;

   /**
    * Clicks at center of some javaFx {@code Node} with provided offsets.
    *
    * @param nodeId  string value of fx:id of node
    * @param offsetX value of X coordinate offset of center of node
    * @param offsetY value of Y coordinate offset of center of node
    */
   static void clickOn(String nodeId, int offsetX, int offsetY) {
      Node button = WindowUtil.getNode(nodeId);
      robot.mouseMove(WindowUtil.getElementCenterX(button) + offsetX, WindowUtil.getElementCenterY(button) + offsetY);
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.mouseRelease(InputEvent.BUTTON1_MASK);
   }

   /**
    * Clicks at center of some javaFx {@code Node}
    * by mouse.
    *
    * @param nodeId string value of fx:id of node
    */
   static void clickOn(String nodeId) {
      clickOn(nodeId, 0, 0);
   }

   /**
    * Clicks button of calculator by mouse
    * which clears input line, history and memory.
    */
   private static void clickClear() {
      RobotUtil.clickOn("#button_clear");
      RobotUtil.clickOn("#button_mc");
      isNeededText("#inputFieldNumber", "0");
      isNeededText("#memoryText", "");
   }

   /**
    * @return current value of {@code Robot}.
    */
   static Robot getRobot() {
      return robot;
   }

   /**
    * presses key then releases it.
    *
    * @param keyEvent int value of {@code KeyEvent} needed to press
    * @see KeyEvent
    */
   private static void tap(int keyEvent) {
      robot.keyPress(keyEvent);
      robot.keyRelease(keyEvent);
   }

   /**
    * presses key with a shift key then releases it.
    *
    * @param keyEvent int value of {@code KeyEvent} needed to press
    * @see KeyEvent
    */
   private static void tapShift(int keyEvent) {
      robot.keyPress(KeyEvent.VK_SHIFT);
      robot.keyPress(keyEvent);
      robot.keyRelease(keyEvent);
      robot.keyRelease(KeyEvent.VK_SHIFT);
   }

   /**
    * Clicks button of calculator by key on keyboard
    * which clears input line and history.
    */
   private static void tapClear() {
      tap(KeyEvent.VK_ESCAPE);
      isNeededText("#inputFieldNumber", "0");
   }

   /**
    * parsing a {@code number} string then typing it by keyboard
    *
    * @param number needed number to type
    */
   private static void enterNumberByKey(String number) {
      char character;
      for (int i = 0; i < number.length(); i++) {
         character = number.charAt(i);
         tap(character);
      }
   }

   /**
    * parsing some action sequence string then typing it by keyboard
    *
    * @param actionSequence  string of actions to represent by {@code Robot}
    *                        e.g.: "2 + 2 = = - 2 - 2"
    * @param expectedOut     string of expected result of action sequence
    * @param expectedHistory string of expected history line after represented
    *                        action sequence by robot
    */
   static void enterCaseByKey(String actionSequence, String expectedOut, String expectedHistory) {
      for (String section : actionSequence.split(" ")) {
         if (section.equals("+")) {
            tapShift(KeyEvent.VK_EQUALS);
         } else if (section.equals("-")) {
            tap('-');
         } else if (section.equals("*")) {
            tapShift(KeyEvent.VK_8);
         } else if (section.equals("/")) {
            tap('/');
         } else if (section.equals("=")) {
            tap('=');
         } else if (section.equals("√")) {
            tapShift(KeyEvent.VK_2);
         } else if (section.equals("1/x")) {
            tap(KeyEvent.VK_R);
         } else if (section.equals("sqr")) {
            tap(KeyEvent.VK_Q);
         } else if (section.equals("%")) {
            tapShift(KeyEvent.VK_5);
         } else if (section.equals("n")) {
            tap(KeyEvent.VK_F9);
         } else if (section.equals("ce")) {
            tap(KeyEvent.VK_DELETE);
         } else if (section.equals("c")) {
            tap(KeyEvent.VK_ESCAPE);
         } else if (section.equals("backspace")) {
            tap(KeyEvent.VK_BACK_SPACE);
         } else {
            enterNumberByKey(section);
         }
      }

      isNeededText("#inputFieldNumber", expectedOut);
      isNeededText("#historyLine", expectedHistory);
      tapClear();
   }

   /**
    * checks is provided node contains expected text.
    * waits for it for about {@value SECONDS_FOR_WAIT} seconds
    *
    * @param node         string value of fx:id of needed node
    * @param expectedText value of string which expected
    */
   static void isNeededText(String node, String expectedText) {
      Label label = (Label) WindowUtil.getNode(node);
      try {
         GuiTest.waitUntil(label, LabeledMatchers.hasText(expectedText), SECONDS_FOR_WAIT);
      } catch (Exception ignored) {
         assertEquals(expectedText, label.getText());
      }
   }

   /**
    * parsing some action sequence string then typing it by mouse with clearing
    * input, history and memory after it
    *
    * @param actionSequence  string of actions to represent by {@code Robot}
    *                        e.g.: "2 + 2 = = - 2 - 2"
    * @param expectedOut     string of expected result of action sequence
    * @param expectedHistory string of expected history line after represented
    *                        action sequence by robot
    */
   static void enterCase(String actionSequence, String expectedOut, String expectedHistory) {
      enterCase(actionSequence, expectedOut, expectedHistory, true);
   }

   /**
    * parsing a {@code number} string then typing it by mouse
    *
    * @param number needed number to type
    */
   private static void enterNumber(String number) {
      char character;
      for (int i = 0; i < number.length(); i++) {
         character = number.charAt(i);
         if (character == ',') {
            clickOn("#button_point");
         } else if (character == '0') {
            clickOn("#button_0");
         } else if (character == '1') {
            clickOn("#button_1");
         } else if (character == '2') {
            clickOn("#button_2");
         } else if (character == '3') {
            clickOn("#button_3");
         } else if (character == '4') {
            clickOn("#button_4");
         } else if (character == '5') {
            clickOn("#button_5");
         } else if (character == '6') {
            clickOn("#button_6");
         } else if (character == '7') {
            clickOn("#button_7");
         } else if (character == '8') {
            clickOn("#button_8");
         } else if (character == '9') {
            clickOn("#button_9");
         }
      }
   }

   /**
    * parsing some action sequence string then typing it by mouse.
    *
    * @param actionSequence  string of actions to represent by {@code Robot}
    *                        e.g.: "2 + 2 = = - 2 - 2"
    * @param expectedOut     string of expected result of action sequence
    * @param expectedHistory string of expected history line after represented
    *                        action sequence by robot
    * @param afterClear      if true - after checking value, input, history and memory will
    *                        be cleared. Not if false
    */
   static void enterCase(String actionSequence, String expectedOut, String expectedHistory, boolean afterClear) {
      for (String section : actionSequence.split(" ")) {
         if (section.equals("+")) {
            clickOn("#button_plus");
         } else if (section.equals("-")) {
            clickOn("#button_subtract");
         } else if (section.equals("*")) {
            clickOn("#button_multiple");
         } else if (section.equals("/")) {
            clickOn("#button_divide");
         } else if (section.equals("=")) {
            clickOn("#button_equals");
         } else if (section.equals("√")) {
            clickOn("#button_root");
         } else if (section.equals("1/x")) {
            clickOn("#button_onedivx");
         } else if (section.equals("sqr")) {
            clickOn("#button_square");
         } else if (section.equals("%")) {
            clickOn("#button_percent");
         } else if (section.equals("n")) {
            clickOn("#button_negate");
         } else if (section.equals("ce")) {
            clickOn("#button_delete_stroke");
         } else if (section.equals("c")) {
            clickOn("#button_clear");
         } else if (section.equals("backspace")) {
            clickOn("#button_delete_digit");
         } else if (section.equals("mc")) {
            clickOn("#button_mc");
         } else if (section.equals("mr")) {
            clickOn("#button_mr");
         } else if (section.equals("m+")) {
            clickOn("#button_mp");
         } else if (section.equals("m-")) {
            clickOn("#button_msub");
         } else {
            enterNumber(section);
         }
      }
      isNeededText("#inputFieldNumber", expectedOut);
      isNeededText("#historyLine", expectedHistory);

      if (afterClear) {
         clickClear();
      }
   }

   /**
    * stopping current thread for waiting a processing stuff, etc.
    *
    * @param milliseconds int value of milliseconds for wait
    */
   static void sleep(int milliseconds) {
      try {
         Thread.sleep(milliseconds);
      } catch (InterruptedException e) {
         //expected
      }
   }

   /**
    * Method for initialization a new {@code Robot}
    *
    * @see Robot
    */
   static void initRobot() {
      try {
         robot = new Robot();
      } catch (AWTException e) {
         e.printStackTrace();
      }
   }
}
