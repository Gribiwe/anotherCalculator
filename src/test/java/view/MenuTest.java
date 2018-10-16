package view;

import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.Test;
import org.loadui.testfx.utils.FXTestUtils;

/**
 * Class for testing menu button
 * (menu with choice of calculator)
 *
 * @author Gribiwe
 */
class MenuTest extends UITest {

   /**
    * value of width of invisible pane
    */
   private static final double INVISIBLE_PANE_WIDTH = 320.0;

   /**
    * Value of width of menu's pane user can see
    */
   private static final double MENU_WIDTH = 255.0;

   /**
    * Amount of milliseconds for clearly loading
    * of menu (when it appears as a little stick
    * and becoming a full width menu)
    */
   private static final int TIME_FOR_APPEARING = 400;

   /**
    * Testing the appearing of menu when
    * button for it was clicked
    */
   @Test
   void testAppearsMenu() {
      assertFalse(WindowUtil.isNodeExists("#invisibleMenuArea"));
      assertFalse(WindowUtil.isNodeExists("#menu"));
      clickMenuButton();
      AnchorPane invisibleMenuArea = (AnchorPane) WindowUtil.getNode("#invisibleMenuArea");
      assertEquals(INVISIBLE_PANE_WIDTH, invisibleMenuArea.getWidth(), 0);
      AnchorPane menu = (AnchorPane) WindowUtil.getNode("#menu");
      assertEquals(MENU_WIDTH, menu.getWidth(), 0);
      clickMenuButton();
   }

   /**
    * Testing the disappearing of menu
    * with different cases
    */
   @Test
   void testTheDisappearing() {
      clickMenuButton();
      testDisappearingOnClickOnInvisibleArea();
      clickMenuButton();
      testDisappearingOnClickAtMenuButton();
      clickMenuButton();
      testDisappearingOnResize();
      clickMenuButton();
   }

   /**
    * Method for testing the disappearing
    * of menu when mouse was clicked
    * on invisible pane of menu (for user
    * it's like to click on the free of menu
    * calculator area)
    */
   private void testDisappearingOnClickOnInvisibleArea() {
      assertTrue(WindowUtil.isNodeExists("#invisibleMenuArea"));
      assertTrue(WindowUtil.isNodeExists("#menu"));
      RobotUtil.clickOn("#invisibleMenuArea", 100, 0);
      RobotUtil.sleep(TIME_FOR_APPEARING);
      assertFalse(WindowUtil.isNodeExists("#invisibleMenuArea"));
      assertFalse(WindowUtil.isNodeExists("#menu"));
   }

   /**
    * Method for testing the disappearing
    * of menu when mouse was clicked
    * on the open (or close) button of
    * menu
    */
   private void testDisappearingOnClickAtMenuButton() {
      assertTrue(WindowUtil.isNodeExists("#invisibleMenuArea"));
      assertTrue(WindowUtil.isNodeExists("#menu"));
      clickMenuButton();
      assertFalse(WindowUtil.isNodeExists("#invisibleMenuArea"));
      assertFalse(WindowUtil.isNodeExists("#menu"));
   }

   /**
    * Method for testing the disappearing
    * of menu when calculator's window
    * are resizing
    */
   private void testDisappearingOnResize() {
      checkExists(true);
      ResizeUtil.testResizeLeft(2, 50);
      checkExists(false);
      clickMenuButton();

      checkExists(true);
      ResizeUtil.testResizeRight(2, 50);
      checkExists(false);
      clickMenuButton();
      FXTestUtils.awaitEvents();
      checkExists(true);
      ResizeUtil.testResizeBottom(2, 50);
      checkExists(false);
      clickMenuButton();

      checkExists(true);
      ResizeUtil.testResizeTop(2, 50);
      checkExists(false);
      clickMenuButton();
   }

   /**
    * method for opening or closing menu
    */
   private void clickMenuButton() {
      RobotUtil.clickOn("#menuButton");
      RobotUtil.sleep(TIME_FOR_APPEARING);
   }

   /**
    * Time for waiting an existing of node per retry.
    */
   private static final int MILLISECONDS_FOR_RETRY = 20;

   /**
    * Amount of retries for checking an existing of node per retry.
    */
   private static final int RETRY_COUNT = 20;

   private void checkExists(boolean shouldBeExist) {
      for (int retry = 0; retry < RETRY_COUNT && WindowUtil.isNodeExists("#invisibleMenuArea") != shouldBeExist; retry++) {
         RobotUtil.sleep(MILLISECONDS_FOR_RETRY);
         System.out.println("deb "+retry);
      }

      for (int retry = 0; retry < RETRY_COUNT && WindowUtil.isNodeExists("#menu") != shouldBeExist; retry++) {
         RobotUtil.sleep(MILLISECONDS_FOR_RETRY);

      }
      assertEquals(shouldBeExist, WindowUtil.isNodeExists("#invisibleMenuArea"));
      assertEquals(shouldBeExist, WindowUtil.isNodeExists("#menu"));
   }
}
