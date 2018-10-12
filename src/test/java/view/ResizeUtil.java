package view;

import org.loadui.testfx.utils.FXTestUtils;

import java.awt.event.InputEvent;

/**
 * Class for resizing a calculator
 *
 * @author Gribiwe
 */
class ResizeUtil extends UITest {

   /**
    * resize a window from left border, checks is it resized
    * and turning it back, then checks that everything turned back
    *
    * @param xStartOffset value of start X coordinate offset
    * @param xDirection   value of final X coordinate of mouse
    */
   static void testResizeLeft(int xStartOffset, int xDirection) {
      int oldTopBorderY = WindowUtil.getElementStartY(borderedVisionPaneNode);
      int oldLeftBorderX = WindowUtil.getElementStartX(borderedVisionPaneNode);

      int finishScreenX = oldLeftBorderX - xDirection - xStartOffset;

      int yStart = WindowUtil.getElementCenterY(borderedVisionPaneNode);

      robot.mouseMove(oldLeftBorderX - xStartOffset, yStart);
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.mouseMove(finishScreenX, yStart);
      robot.mouseRelease(InputEvent.BUTTON1_MASK);
      robot.mouseMove(finishScreenX, yStart);
      FXTestUtils.awaitEvents();

      assertEquals(finishScreenX, WindowUtil.getElementStartX(borderedVisionPaneNode) - xStartOffset);
      assertEquals(oldTopBorderY, WindowUtil.getElementStartY(borderedVisionPaneNode));

      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.mouseMove(oldLeftBorderX, yStart);
      robot.mouseRelease(InputEvent.BUTTON1_MASK);
      FXTestUtils.awaitEvents();

      assertEquals(oldLeftBorderX, WindowUtil.getElementStartX(borderedVisionPaneNode));
      assertEquals(oldTopBorderY, WindowUtil.getElementStartY(borderedVisionPaneNode));
   }

   /**
    * resize a window from bottom border, checks is it resized
    * and turning it back, then checks that everything turned back
    *
    * @param yStartOffset value of start Y coordinate offset
    * @param yDirection   value of final Y coordinate of mouse
    */
   static void testResizeBottom(int yStartOffset, int yDirection) {
      int oldTopBorderY = WindowUtil.getElementStartY(borderedVisionPaneNode);
      int oldRightBorderX = WindowUtil.getElementEndX(borderedVisionPaneNode);
      int oldLeftBorderX = WindowUtil.getElementStartX(borderedVisionPaneNode);
      int oldBottomBorderY = WindowUtil.getElementEndY(borderedVisionPaneNode);
      int finishScreenY = oldBottomBorderY + yDirection + yStartOffset;

      int xStart = WindowUtil.getElementCenterX(borderedVisionPaneNode);

      robot.mouseMove(xStart, oldBottomBorderY + yStartOffset);
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.mouseMove(xStart, finishScreenY);
      robot.mouseRelease(InputEvent.BUTTON1_MASK);
      FXTestUtils.awaitEvents();

      assertEquals(finishScreenY, WindowUtil.getElementEndY(borderedVisionPaneNode) + yStartOffset);
      assertEquals(oldTopBorderY, WindowUtil.getElementStartY(borderedVisionPaneNode));
      assertEquals(oldRightBorderX, WindowUtil.getElementEndX(borderedVisionPaneNode));
      assertEquals(oldLeftBorderX, WindowUtil.getElementStartX(borderedVisionPaneNode));

      robot.mouseMove(xStart, finishScreenY - 1);
      robot.mouseMove(xStart, finishScreenY);
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.mouseMove(xStart, oldBottomBorderY + yStartOffset);
      robot.mouseRelease(InputEvent.BUTTON1_MASK);
      FXTestUtils.awaitEvents();

      assertEquals(oldBottomBorderY, WindowUtil.getElementEndY(borderedVisionPaneNode));
      assertEquals(oldTopBorderY, WindowUtil.getElementStartY(borderedVisionPaneNode));
      assertEquals(oldRightBorderX, WindowUtil.getElementEndX(borderedVisionPaneNode));
      assertEquals(oldLeftBorderX, WindowUtil.getElementStartX(borderedVisionPaneNode));
   }

   /**
    * resize a window from top border, checks is it resized
    * and turning it back, then checks that everything turned back
    *
    * @param yStartOffset value of start Y coordinate offset
    * @param yDirection   value of final Y coordinate of mouse
    */
   static void testResizeTop(int yStartOffset, int yDirection) {
      int oldTopBorderY = WindowUtil.getElementStartY(borderedVisionPaneNode);
      int oldRightBorderX = WindowUtil.getElementEndX(borderedVisionPaneNode);
      int oldLeftBorderX = WindowUtil.getElementStartX(borderedVisionPaneNode);

      int finishScreenY = oldTopBorderY - yDirection - yStartOffset;

      int xStart = WindowUtil.getElementCenterX(borderedVisionPaneNode);

      robot.mouseMove(xStart, oldTopBorderY + yStartOffset);
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.mouseMove(xStart, finishScreenY);
      robot.mouseRelease(InputEvent.BUTTON1_MASK);
      FXTestUtils.awaitEvents();

      assertEquals(finishScreenY, WindowUtil.getElementStartY(borderedVisionPaneNode) + yStartOffset);
      assertEquals(oldRightBorderX, WindowUtil.getElementEndX(borderedVisionPaneNode));
      assertEquals(oldLeftBorderX, WindowUtil.getElementStartX(borderedVisionPaneNode));

      robot.mouseMove(xStart, finishScreenY + 1);
      robot.mouseMove(xStart, finishScreenY);
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.mouseMove(xStart, oldTopBorderY + yStartOffset);
      robot.mouseRelease(InputEvent.BUTTON1_MASK);
      FXTestUtils.awaitEvents();

      assertEquals(oldTopBorderY, WindowUtil.getElementStartY(borderedVisionPaneNode));
      assertEquals(oldRightBorderX, WindowUtil.getElementEndX(borderedVisionPaneNode));
      assertEquals(oldLeftBorderX, WindowUtil.getElementStartX(borderedVisionPaneNode));
   }

   /**
    * resize a window from right border, checks is it resized
    * and turning it back, then checks that everything turned back
    *
    * @param xStartOffset value of start X coordinate offset
    * @param xDirection   value of final X coordinate of mouse
    */
   static void testResizeRight(int xStartOffset, int xDirection) {
      int oldTopBorderY = WindowUtil.getElementStartY(borderedVisionPaneNode);
      int oldRightBorderX = WindowUtil.getElementEndX(borderedVisionPaneNode);

      int finishScreenX = oldRightBorderX + xDirection + xStartOffset;

      int yStart = WindowUtil.getElementCenterY(borderedVisionPaneNode);

      robot.mouseMove(oldRightBorderX + xStartOffset, yStart);
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.mouseMove(finishScreenX, yStart);
      robot.mouseRelease(InputEvent.BUTTON1_MASK);
      robot.mouseMove(finishScreenX + 1, yStart);
      robot.mouseMove(finishScreenX, yStart);
      FXTestUtils.awaitEvents();

      assertEquals(finishScreenX, WindowUtil.getElementEndX(borderedVisionPaneNode) + xStartOffset);
      assertEquals(oldTopBorderY, WindowUtil.getElementStartY(borderedVisionPaneNode));

      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.mouseMove(oldRightBorderX + xStartOffset, yStart);

      FXTestUtils.awaitEvents();
      robot.mouseRelease(InputEvent.BUTTON1_MASK);

      assertEquals(oldRightBorderX, WindowUtil.getElementEndX(borderedVisionPaneNode));
      assertEquals(oldTopBorderY, WindowUtil.getElementStartY(borderedVisionPaneNode));
   }

}
