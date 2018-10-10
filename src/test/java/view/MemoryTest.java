package view;

import javafx.scene.control.Label;
import org.junit.jupiter.api.Test;
import org.loadui.testfx.utils.FXTestUtils;

/**
 * Class for testing work with memory
 */
class MemoryTest extends UITest {

   /**
    * testing is the memory become able to use
    * after adding or removing a number
    * to memory
    */
   @Test
   void testEnableAfterAddOrRemoveNumber() {
      FXTestUtils.awaitEvents();
      testEnableAfterAddOrRemoveMemory("5 m+ 2 mr", "5", "", "5");
      testEnableAfterAddOrRemoveMemory("5 m- 2 mr", "-5", "", "-5");
   }

   /**
    * test of disabled memory button
    */
   @Test
   void testDisabledMemory() {
      FXTestUtils.awaitEvents();
      Label memoryText = (Label) WindowUtil.getNode("#memoryText");
      assertEquals("root", memoryText.getParent().getId());
      assertEquals(0, memoryText.getOpacity(), 0);
      RobotUtil.isNeededText("#memoryText", "");
      checkDisable(true);
   }

   /**
    * Method for testing is the memory become able to use
    * after adding or removing a number
    * to memory
    */
   private void testEnableAfterAddOrRemoveMemory(String actionSequence, String expectedOut, String expectedHistory, String expectedMemory) {
      checkDisable(true);
      RobotUtil.enterCase(actionSequence, expectedOut, expectedHistory, false);
      RobotUtil.isNeededText("#memoryText", expectedMemory);
      checkDisable(false);
      clearAllActions();
   }

   /**
    * clearing history, input and memory method
    */
   private void clearAllActions() {
      RobotUtil.enterCase("mc c", "0", "", false);
      RobotUtil.isNeededText("#memoryText", "");
      checkDisable(true);
   }

   /**
    * method for checking is
    * memory buttons are disabled or not
    * @param needToBeDisabled if true - checks is buttons are disabled.
    *                        If false - checks is it enabled
    */
   private void checkDisable(boolean needToBeDisabled) {
      assertEquals(WindowUtil.getNode("#button_mc").isDisable(), needToBeDisabled);
      assertEquals(WindowUtil.getNode("#button_mr").isDisable(), needToBeDisabled);
      assertEquals(WindowUtil.getNode("#button_open_memory").isDisable(), needToBeDisabled);
   }
}
