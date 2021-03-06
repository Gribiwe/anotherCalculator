package view;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.loadui.testfx.utils.FXTestUtils;
import org.testfx.api.FxToolkit;

/**
 * Class for testing window's bar buttons
 * tests exit, maximize (unMaximize) and minimize buttons
 *
 * @author Gribiwe
 */

class WindowBarButtonsTest extends UITest {

   /**
    * tests maximize (and unMaximize) button
    */
   @Test
   void maximizeTest() {
      Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
      int screenHeight = (int) primaryScreenBounds.getHeight();
      int screenWidth = (int) primaryScreenBounds.getWidth();

      FXTestUtils.awaitEvents();
      AnchorPane visiblePane = (AnchorPane) WindowUtil.getNode("#borderedVisionPane");
      int oldStartX = WindowUtil.getElementStartX(visiblePane);
      int oldEndX = WindowUtil.getElementEndX(visiblePane);
      int oldStartY = WindowUtil.getElementStartY(visiblePane);
      int oldEndY = WindowUtil.getElementEndY(visiblePane);
      assertNotEquals(oldStartX, 0);
      assertNotEquals(oldEndX, screenWidth);
      assertNotEquals(oldStartY, 0);
      assertNotEquals(oldEndY, screenHeight);

      RobotUtil.clickOn("#maximize");
      FXTestUtils.awaitEvents();
      assertEquals(WindowUtil.getElementStartX(visiblePane), 0);
      assertEquals(WindowUtil.getElementEndX(visiblePane), screenWidth);
      assertEquals(WindowUtil.getElementStartY(visiblePane), 0);
      assertEquals(WindowUtil.getElementEndY(visiblePane), screenHeight);
      RobotUtil.clickOn("#maximize");
      FXTestUtils.awaitEvents();

      assertEquals(WindowUtil.getElementStartX(visiblePane), oldStartX);
      assertEquals(WindowUtil.getElementEndX(visiblePane), oldEndX);
      assertEquals(WindowUtil.getElementStartY(visiblePane), oldStartY);
      assertEquals(WindowUtil.getElementEndY(visiblePane), oldEndY);
   }

   /**
    * tests minimize button
    */
   @Test
   void minimizeTest() {
      FXTestUtils.awaitEvents();
      Stage stage = FXRobotHelper.getStages().get(0);
      assertFalse("App already iconified", stage.isIconified());
      RobotUtil.clickOn("#hide");
      FXTestUtils.awaitEvents();
      assertTrue("App didn't iconified", stage.isIconified());
      Platform.runLater(() -> stage.setIconified(false));
   }

   /**
    * tests close button by checking is
    * JavaFx thread stopped after clicking on exit button
    */
   @Test
   void windowCloseTest() {
      FXTestUtils.awaitEvents();
      assertFalse("App already closed", !FxToolkit.isFXApplicationThreadRunning());
      RobotUtil.clickOn("#exit");
      int i = 0;
      while (FxToolkit.isFXApplicationThreadRunning() && i < 40) {
         RobotUtil.sleep(50);
         i++;
      }
      assertTrue("App doesn't closed", !FxToolkit.isFXApplicationThreadRunning());
   }

}
