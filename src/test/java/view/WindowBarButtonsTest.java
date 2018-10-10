package view;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.loadui.testfx.utils.FXTestUtils;
import org.testfx.api.FxToolkit;

/**
 * Class for testing window's bar buttons
 * tests exit, maximize (unMaximize) and minimize buttons
 *
 * @author Gribiwe
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class WindowBarButtonsTest extends UITest {

   /**
    * tests maximize (and unMaximize) button
    */
   @Test
   public void maximizeTest() {
      FXTestUtils.awaitEvents();
      AnchorPane visiblePane = (AnchorPane) WindowUtil.getNode("#borderedVisionPane");
      int oldStartX = WindowUtil.getElementStartX(visiblePane);
      int oldEndX = WindowUtil.getElementEndX(visiblePane);
      int oldStartY = WindowUtil.getElementStartY(visiblePane);
      int oldEndY = WindowUtil.getElementEndY(visiblePane);
      assertNotEquals(oldStartX, 0);
      assertNotEquals(oldEndX, 1600);
      assertNotEquals(oldStartY, 0);
      assertNotEquals(oldEndY, 870);

      RobotUtil.clickOn("#maximize");
      FXTestUtils.awaitEvents();
      assertEquals(WindowUtil.getElementStartX(visiblePane), 0);
      assertEquals(WindowUtil.getElementEndX(visiblePane), 1600);
      assertEquals(WindowUtil.getElementStartY(visiblePane), 0);
      assertEquals(WindowUtil.getElementEndY(visiblePane), 870);
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
   public void minimizeTest() {
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
