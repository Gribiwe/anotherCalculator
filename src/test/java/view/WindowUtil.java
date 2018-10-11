package view;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Assert;
import org.testfx.util.WaitForAsyncUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Class for getting information about nodes
 * (their coordinates or of their objects)
 *
 * @author Gribiwe
 */
class WindowUtil extends Assert {

   /**
    * Current scene
    *
    * @see Scene
    */
   private static Scene scene;

   static public int getRounded(double amount) {
      DecimalFormat df = new DecimalFormat("0.#");
      df.setRoundingMode(RoundingMode.HALF_UP);
      DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
      decimalFormatSymbols.setDecimalSeparator('.');
      df.setDecimalFormatSymbols(decimalFormatSymbols);
      String formatted = df.format(amount);
      df = new DecimalFormat("0");
      df.setRoundingMode(RoundingMode.HALF_UP);
      formatted = df.format(Double.parseDouble(formatted));

      return Integer.parseInt(formatted);
   }

   /**
    * @param element node which will proceeded
    * @return rounded double value of X
    * coordinate of left border of node
    */
   static int getElementStartX(Node element) {
      return getRounded(element.localToScreen(element.getBoundsInLocal()).getMinX());
   }

   /**
    * @param element node which will proceeded
    * @return rounded double value of X
    * coordinate of right border of node
    */
   static int getElementEndX(Node element) {
      return getElementStartX(element) + getElementWidth(element);
   }

   /**
    * @param element node which will proceeded
    * @return rounded double value of Y
    * coordinate of top border of node
    */
   static int getElementStartY(Node element) {
      return getRounded(element.localToScreen(element.getBoundsInLocal()).getMinY());
   }

   /**
    * @param element node which will proceeded
    * @return rounded double value of Y
    * coordinate of bottom border of node
    */
   static int getElementEndY(Node element) {
      return getElementStartY(element) + getElementHeight(element);
   }

   /**
    * @param element node which will proceeded
    * @return rounded double value of real width of node
    */
   private static int getElementWidth(Node element) {
      return getRounded(element.localToScreen(element.getBoundsInLocal()).getWidth());
   }

   /**
    * @param element node which will proceeded
    * @return rounded double value of real height of node
    */
   private static int getElementHeight(Node element) {
      return getRounded(element.localToScreen(element.getBoundsInLocal()).getHeight());
   }

   /**
    * @param element node which will proceeded
    * @return double value of X coordinate of center of node
    */
   static int getElementCenterX(Node element) {
      return getElementStartX(element) + getElementWidth(element) / 2;
   }

   /**
    * @param element node which will proceeded
    * @return double value of Y coordinate of center of node
    */
   static int getElementCenterY(Node element) {
      return getElementStartY(element) + getElementHeight(element) / 2;
   }

   /**
    * making initialization of scene
    */
   static void init() {
      Stage stage = FXRobotHelper.getStages().get(0);
      assertNotNull(stage);
      scene = stage.getScene();
      assertNotNull(scene);
   }

   /**
    * @param nodeID string representation of fx:id of node
    * @return node object founded by nodeId
    */
   static Node getNode(String nodeID) {
      Node node = scene.lookup(nodeID);
      assertNotNull("node don't exists: " + nodeID, node);
      return node;
   }

   /**
    * checks is node exists at scene
    *
    * @param nodeID string representation of fx:id of node
    * @return true if exists, false if doesn't
    */
   static boolean isNodeExists(String nodeID) {
      Node node = scene.lookup(nodeID);
      return node != null;
   }
}
