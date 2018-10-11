package view;

import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.Test;
import org.loadui.testfx.utils.FXTestUtils;

/**
 * Class for testing bottom menu
 * which appears after clicking on
 * open history button or open memory
 * button
 *
 * @author Gribiwe
 */
class BottomMenuTest extends UITest {

   /**
    * value of pixels of width of menu
    */
   private static final double WIDTH = 320.0;

   /**
    * amount of milliseconds to wait
    * until bottom menu will fully opened
    */
   private static final int TIME_FOR_APPEARING = 400;

   /**
    * testing that bottom menu
    * appears after clicking a button
    * of opening a history
    */
//   @Test
   void testHistoryAppears() {
      assertFalse(WindowUtil.isNodeExists("#invisibleArea"));
      assertFalse(WindowUtil.isNodeExists("#visibleArea"));
      clickOpenHistoryButton();
      AnchorPane invisibleMenuArea = (AnchorPane) WindowUtil.getNode("#invisibleArea");
      assertEquals(WIDTH, (int) invisibleMenuArea.getWidth(), 0);
      AnchorPane visibleArea = (AnchorPane) WindowUtil.getNode("#visibleArea");
      assertEquals(WIDTH, (int) visibleArea.getWidth(), 0);
      clickOpenHistoryButton();
   }

   /**
    * testing that bottom menu
    * appears after clicking a button
    * of opening a memory
    */
//   @Test
   void testMemoryAppears() {
      assertFalse(WindowUtil.isNodeExists("#invisibleArea"));
      assertFalse(WindowUtil.isNodeExists("#visibleArea"));
      RobotUtil.enterCase("m+", "0", "", false);
      clickOpenMemoryButton();
      AnchorPane invisibleMenuArea = (AnchorPane) WindowUtil.getNode("#invisibleArea");
      assertEquals(WIDTH, (int) invisibleMenuArea.getWidth(), 0);
      AnchorPane visibleArea = (AnchorPane) WindowUtil.getNode("#visibleArea");
      assertEquals(WIDTH, (int) visibleArea.getWidth(), 0);
      clickOpenMemoryButton();
      RobotUtil.enterCase("m-", "0", "", true);
   }

   /**
    * checking that bottom menu will
    * disappear after some cases
    */
//   @Test
   void testDisappearing() {
      FXTestUtils.awaitEvents();
      clickOpenHistoryButton();
      testDisappearingOnClickOnInvisibleArea();
      clickOpenHistoryButton();
      testDisappearingOnResize();
   }

   /**
    * testing of disappearing of
    * bottom menu after clicking
    * on the free calculator area
    */
   private void testDisappearingOnClickOnInvisibleArea() {
      assertTrue(WindowUtil.isNodeExists("#invisibleArea"));
      assertTrue(WindowUtil.isNodeExists("#visibleArea"));
      RobotUtil.clickOn("#invisibleArea", 0, -100);
      RobotUtil.sleep(TIME_FOR_APPEARING);
      assertFalse(WindowUtil.isNodeExists("#invisibleArea"));
      assertFalse(WindowUtil.isNodeExists("#visibleArea"));
   }

   /**
    * testing of disappearing of
    * bottom menu after resizing
    * a calculator
    */
   private void testDisappearingOnResize() {
      assertTrue(WindowUtil.isNodeExists("#invisibleArea"));
      assertTrue(WindowUtil.isNodeExists("#visibleArea"));
      ResizeUtil.testResizeLeft(3, 50);
      assertFalse(WindowUtil.isNodeExists("#invisibleArea"));
      assertFalse(WindowUtil.isNodeExists("#visibleArea"));
      clickOpenHistoryButton();

      assertTrue(WindowUtil.isNodeExists("#invisibleArea"));
      assertTrue(WindowUtil.isNodeExists("#visibleArea"));
      ResizeUtil.testResizeRight(3, 50);
      assertFalse(WindowUtil.isNodeExists("#invisibleArea"));
      assertFalse(WindowUtil.isNodeExists("#visibleArea"));
      clickOpenHistoryButton();

      assertTrue(WindowUtil.isNodeExists("#invisibleArea"));
      assertTrue(WindowUtil.isNodeExists("#visibleArea"));
      ResizeUtil.testResizeBottom(3, 50);
      assertFalse(WindowUtil.isNodeExists("#invisibleArea"));
      assertFalse(WindowUtil.isNodeExists("#visibleArea"));
      clickOpenHistoryButton();

      assertTrue(WindowUtil.isNodeExists("#invisibleArea"));
      assertTrue(WindowUtil.isNodeExists("#visibleArea"));
      ResizeUtil.testResizeTop(3, 50);
      assertFalse(WindowUtil.isNodeExists("#invisibleArea"));
      assertFalse(WindowUtil.isNodeExists("#visibleArea"));
   }

   /**
    * method for clicking on the
    * open history button
    */
   private void clickOpenHistoryButton() {
      RobotUtil.clickOn("#button_history");
      RobotUtil.sleep(TIME_FOR_APPEARING);
   }

   /**
    * method for clicking on the
    * open memory button
    */
   private void clickOpenMemoryButton() {
      RobotUtil.clickOn("#button_open_memory");
      RobotUtil.sleep(TIME_FOR_APPEARING);
   }

}
