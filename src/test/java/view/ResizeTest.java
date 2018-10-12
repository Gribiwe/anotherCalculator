package view;

import org.junit.jupiter.api.Test;
import org.loadui.testfx.utils.FXTestUtils;

import java.awt.event.InputEvent;

/**
 * Class for testing a resizing of calculator
 *
 * @author Gribiwe
 * @see ResizeUtil
 */
class ResizeTest extends UITest {

   /**
    * tests resizing with different offsets and directions
    */
   @Test
   void testResize() {
      resizeTestTemplateX(0);
      resizeTestTemplateX(600);
      resizeTestTemplateY(0);
      resizeTestTemplateY(175);
   }

   /**
    * tests drag with different directions
    */
   @Test
   void testDrag() {
      dragTestTemplate(100, 50);
      dragTestTemplate(300, 150);
   }

   /**
    * method for resize tests with different X startOffsets
    *
    * @param directionX value of final X coordinate of mouse
    */
   private void resizeTestTemplateX(int directionX) {
      ResizeUtil.testResizeRight(0, directionX);
      ResizeUtil.testResizeLeft(0, directionX);
      ResizeUtil.testResizeRight(1, directionX);
      ResizeUtil.testResizeLeft(1, directionX);
      ResizeUtil.testResizeRight(6, directionX);
      ResizeUtil.testResizeLeft(6, directionX);
   }

   /**
    * method for resize tests with different Y startOffsets
    *
    * @param directionY value of final Y coordinate of mouse
    */
   private void resizeTestTemplateY(int directionY) {
      ResizeUtil.testResizeBottom(0, directionY);
      ResizeUtil.testResizeTop(1, directionY);
      ResizeUtil.testResizeBottom(1, directionY);
      ResizeUtil.testResizeTop(4, directionY);
      ResizeUtil.testResizeBottom(4, directionY);
   }

   /**
    * test method for drag which contains different cases with provided
    * {@code xDirection} and {@code yDirection} values
    *
    * @param xDirection number of pixel to drag on X coordinate
    * @param yDirection number of pixel to drag on Y coordinate
    */
   private void dragTestTemplate(int xDirection, int yDirection) {
      dragTest(xDirection, 0);
      dragTest(-xDirection, 0);
      dragTest(-xDirection, 0);
      dragTest(xDirection, 0);

      dragTest(0, yDirection);
      dragTest(0, -yDirection);
      dragTest(0, -yDirection);
      dragTest(0, yDirection);

      dragTest(xDirection, yDirection);
      dragTest(-xDirection, -yDirection);
      dragTest(-xDirection, -yDirection);
      dragTest(xDirection, yDirection);
   }

   /**
    * method for testing a dragging of calculator
    * checks calculator's coordinates after drugging
    *
    * @param xDirection value of final X coordinate of mouse
    * @param yDirection value of final Y coordinate of mouse
    */
   private void dragTest(int xDirection, int yDirection) {
      int oldTopBorderY = WindowUtil.getElementStartY(borderedVisionPaneNode);
      int oldRightBorderX = WindowUtil.getElementEndX(borderedVisionPaneNode);
      int oldLeftBorderX = WindowUtil.getElementStartX(borderedVisionPaneNode);
      int oldBottomBorderY = WindowUtil.getElementEndY(borderedVisionPaneNode);

      int xStart = WindowUtil.getElementCenterX(borderedVisionPaneNode);
      int yStart = oldTopBorderY + 15;

      robot.mouseMove(xStart + 5, yStart);
      robot.mouseMove(xStart, yStart);
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.mouseMove(xStart + xDirection, yStart + yDirection);
      robot.mouseRelease(InputEvent.BUTTON1_MASK);

      FXTestUtils.awaitEvents();

      assertEquals(oldRightBorderX + xDirection, WindowUtil.getElementEndX(borderedVisionPaneNode));
      assertEquals(oldLeftBorderX + xDirection, WindowUtil.getElementStartX(borderedVisionPaneNode));
      assertEquals(oldTopBorderY + yDirection, WindowUtil.getElementStartY(borderedVisionPaneNode));
      assertEquals(oldBottomBorderY + yDirection, WindowUtil.getElementEndY(borderedVisionPaneNode));
   }


}
