package gribiwe.view.listener;

import gribiwe.view.BottomMenuView;
import gribiwe.view.MenuView;
import gribiwe.view.Properties;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * class for listening of mouse actions
 * it's used for resizing, dragging and making some buttons alive
 *
 * @author Gribiwe
 */
public class CursorListener {

   /**
    * visible root pane of calculator
    */
   private final AnchorPane area;

   /**
    * value of width of screen
    */
   private double localScreenWidth;

   /**
    * value of height of screen
    */
   private double localScreenHeight;

   /**
    * value that shows is mouse was
    * in area which makes able to resize
    * calculator in top-left or top-right zone
    */
   private boolean isTopDiagonal = false;

   /**
    * shows that mouse situated in place
    * which makes able to resize a calculator
    */
   private boolean onBorder = false;

   /**
    * shows that mouse situated on button.
    * needs for disabling resize
    */
   private boolean onButton = false;

   /**
    * shows that mouse was pressed for dragging
    */
   private boolean pressedForDrag = false;

   /**
    * value of left anchor padding of visible root pane
    * needs for dragging
    */
   private double startAnchorLeft;

   /**
    * value of right anchor padding of visible root pane
    * needs for dragging
    */
   private double startAnchorRight;

   /**
    * value of top anchor padding of visible root pane
    * needs for dragging
    */
   private double startAnchorTop;

   /**
    * value of bottom anchor padding of visible root pane
    * needs for dragging
    */
   private double startAnchorBottom;

   /**
    * value of x offset on dragging calculator
    */
   private double xOffset;

   /**
    * value of y offset on dragging calculator
    */
   private double yOffset;

   /**
    * exemplar of class MenuView for showing
    * left side menu
    */
   private MenuView menu;

   /**
    * exemplar of class BottomMenuView for showing
    * bottom menu
    */
   private BottomMenuView bottomMenuView;

   /**
    * saved value of right anchor padding
    * before fullscreen
    */
   private double savedRightAnchor;
   /**
    * saved value of left anchor padding
    * before fullscreen
    */
   private double savedLeftAnchor;

   /**
    * saved value of top anchor padding
    * before fullscreen
    */
   private double savedTopAnchor;

   /**
    * saved value of bottom anchor padding
    * before fullscreen
    */
   private double savedBottomAnchor;

   /**
    * shows if application in fullscreen
    */
   private boolean isFull = false;

   /**
    * label of maximize button. Changes when it's become
    * a not fullscreen button
    */
   private final Label maximizeButtonText;

   /**
    * value of top anchor padding before resize tick
    */
   private double topBeforeResizeY;

   /**
    * value of left anchor padding before resize tick
    */
   private double leftBeforeResizeX;

   /**
    * value of right anchor padding before resize tick
    */
   private double rightBeforeResizeX;

   /**
    * value of bottom anchor padding before resize tick
    */
   private double bottomBeforeResizeY;

   /**
    * exemplar of key listener
    * blocks when i need it
    * (e.g. dragging)
    */
   private final KeyListener keyListener;

   /**
    * creates cursor listener
    *
    * @param area               visible root pane of calculator
    * @param maximizeButtonText pane of maximize button
    * @param keyListener        exemplar of key listener for blocking it
    */
   public CursorListener(AnchorPane area, Label maximizeButtonText, KeyListener keyListener) {
      this.area = area;
      this.maximizeButtonText = maximizeButtonText;
      this.keyListener = keyListener;
   }

   /**
    * updates flags by mouse position
    * sets needed cursor if it on border for resize
    */
   public void updateCursor(MouseEvent mouseEvent) {
      if (!isFull) {
         double cursorX = mouseEvent.getX();
         double cursorY = mouseEvent.getY();

         localScreenWidth = area.getParent().getScene().getWidth();
         localScreenHeight = area.getParent().getScene().getHeight();

         double mainPaneWidth = area.getWidth();
         double mainPaneHeight = area.getHeight();

         double appLeftBorderX = Properties.X_BORDER;
         double appRightBorderX = mainPaneWidth - Properties.X_BORDER;

         double appTopBorderY = 0;
         double appBottomBorderY = mainPaneHeight - Properties.Y_BORDER;


         boolean isLeftBorder = cursorX - 1 < appLeftBorderX && cursorX - 1 >= appLeftBorderX - Properties.X_BORDER &&
                 cursorY > appTopBorderY && cursorY < appBottomBorderY;

         boolean isRightBorder = cursorX + 1 > appRightBorderX && cursorX + 1 <= appRightBorderX + Properties.X_BORDER &&
                 cursorY > appTopBorderY && cursorY < appBottomBorderY;

         boolean isBottomBorder = cursorY + 1 > appBottomBorderY && cursorY + 1 <= appBottomBorderY + Properties.Y_BORDER &&
                 cursorX > appLeftBorderX - Properties.X_BORDER && cursorX < appRightBorderX + Properties.X_BORDER;

         boolean isTopBorder = cursorY + 1 < appTopBorderY + Properties.Y_BORDER && cursorY + 1 >= appTopBorderY &&
                 cursorX > appLeftBorderX - Properties.X_BORDER && cursorX < appRightBorderX + Properties.X_BORDER;

         boolean isBottomLeftBorder = cursorX > appLeftBorderX - Properties.X_BORDER && cursorX < appLeftBorderX + Properties.X_BORDER &&
                 cursorY > appTopBorderY && cursorY < appBottomBorderY + Properties.X_BORDER;

         boolean isBottomRightBorder = cursorX < appRightBorderX + Properties.X_BORDER && cursorX > appRightBorderX - Properties.X_BORDER &&
                 cursorY > appTopBorderY && cursorY < appBottomBorderY + Properties.X_BORDER;

         boolean isTopDiagonalBorder = cursorY < appTopBorderY + Properties.X_BORDER && cursorY > appTopBorderY &&
                 cursorX > appLeftBorderX - Properties.X_BORDER && cursorX < appRightBorderX + Properties.X_BORDER;


         if (isTopDiagonal) {
            isLeftBorder = cursorX < appLeftBorderX + Properties.X_BORDER && cursorX > appLeftBorderX - Properties.X_BORDER &&
                    cursorY > appTopBorderY && cursorY < appBottomBorderY;
            isRightBorder = cursorX > appRightBorderX - Properties.X_BORDER && cursorX < appRightBorderX + Properties.X_BORDER &&
                    cursorY > appTopBorderY && cursorY < appBottomBorderY;
         }


         if (isLeftBorder && isTopDiagonalBorder) {
            isTopDiagonal = true;
            onBorder = true;
            area.setCursor(Cursor.NW_RESIZE);
         } else if (isRightBorder && isTopDiagonalBorder) {
            isTopDiagonal = true;
            onBorder = true;
            area.setCursor(Cursor.NE_RESIZE);
         } else if (isBottomLeftBorder && isBottomBorder) {
            onBorder = true;
            area.setCursor(Cursor.SW_RESIZE);
         } else if (isBottomRightBorder && isBottomBorder) {
            onBorder = true;
            area.setCursor(Cursor.SE_RESIZE);
         } else if (isLeftBorder) {
            onBorder = true;
            area.setCursor(Cursor.W_RESIZE);
         } else if (isRightBorder) {
            onBorder = true;
            area.setCursor(Cursor.E_RESIZE);
         } else if (isTopBorder) {
            onBorder = true;
            area.setCursor(Cursor.N_RESIZE);
         } else if (isBottomBorder) {
            onBorder = true;
            area.setCursor(Cursor.S_RESIZE);
         } else {
            isTopDiagonal = false;
            onBorder = false;
            area.setCursor(Cursor.DISAPPEAR);
         }
      }
   }

   /**
    * method for making calculator to
    * fullscreen mode
    */
   public void fullScreen() {
      if (menu != null && menu.isShows()) {
         menu.closeMenu();
      }

      if (bottomMenuView != null && bottomMenuView.isShows()) {
         bottomMenuView.closeHistory();
      }

      Node visionPane = area.getChildren().get(0);
      if (!isFull) {
         savedLeftAnchor = AnchorPane.getLeftAnchor(area);
         savedRightAnchor = AnchorPane.getRightAnchor(area);
         savedTopAnchor = AnchorPane.getTopAnchor(area);
         savedBottomAnchor = AnchorPane.getBottomAnchor(area);

         AnchorPane.setBottomAnchor(area, 0.0);
         AnchorPane.setTopAnchor(area, 0.0);
         AnchorPane.setLeftAnchor(area, 0.0);
         AnchorPane.setRightAnchor(area, 0.0);

         AnchorPane.setLeftAnchor(visionPane, 0.0);
         AnchorPane.setRightAnchor(visionPane, 0.0);
         AnchorPane.setBottomAnchor(visionPane, 0.0);

         maximizeButtonText.setText("\uE923");
         isFull = true;
      } else {
         AnchorPane.setBottomAnchor(area, savedBottomAnchor);
         AnchorPane.setTopAnchor(area, savedTopAnchor);
         AnchorPane.setLeftAnchor(area, savedLeftAnchor);
         AnchorPane.setRightAnchor(area, savedRightAnchor);

         AnchorPane.setLeftAnchor(visionPane, Properties.X_BORDER);
         AnchorPane.setRightAnchor(visionPane, Properties.X_BORDER);
         AnchorPane.setBottomAnchor(visionPane, Properties.Y_BORDER);
         maximizeButtonText.setText("\uE003");
         isFull = false;
      }
   }

   /**
    * method for resizing a calculator
    */
   public void resizeWindow(MouseEvent mouseEvent) {
      if (onBorder) {
         if (menu != null && menu.isShows()) {
            menu.closeMenu();
         }
         if (bottomMenuView != null && bottomMenuView.isShows()) {
            bottomMenuView.closeHistory();
         }
         Cursor cursor = area.getCursor();
         if (cursor.equals(Cursor.W_RESIZE)) {
            doResizeW(mouseEvent);
         } else if (cursor == Cursor.E_RESIZE) {
            doResizeE(mouseEvent);
         } else if (cursor == Cursor.S_RESIZE) {
            doResizeS(mouseEvent);
         } else if (cursor == Cursor.N_RESIZE) {
            doResizeN(mouseEvent);
         } else if (cursor == Cursor.SE_RESIZE) {
            doResizeS(mouseEvent);
            doResizeE(mouseEvent);
         } else if (cursor == Cursor.NE_RESIZE) {
            doResizeN(mouseEvent);
            doResizeE(mouseEvent);
         } else if (cursor == Cursor.SW_RESIZE) {
            doResizeS(mouseEvent);
            doResizeW(mouseEvent);
         } else if (cursor == Cursor.NW_RESIZE) {
            doResizeN(mouseEvent);
            doResizeW(mouseEvent);
         }
      }
   }

   /**
    * method for dragging a calculator
    */
   public void dragging(MouseEvent mouseEvent) {
      if (pressedForDrag) {
         keyListener.setEnable(false);
         if (isFull) {
            fullScreen();
         } else {
            double xDir = mouseEvent.getScreenX() + xOffset;
            double yDir = mouseEvent.getScreenY() + yOffset;

            AnchorPane.setLeftAnchor(area, startAnchorLeft + xDir);
            AnchorPane.setRightAnchor(area, startAnchorRight - xDir);
            AnchorPane.setTopAnchor(area, startAnchorTop + yDir);
            AnchorPane.setBottomAnchor(area, startAnchorBottom - yDir);

            if (bottomMenuView != null && bottomMenuView.isShows()) {
               bottomMenuView.draggingBottomMenu(xDir, yDir);
            }
         }
      }
   }

   /**
    * closing a javaFx application
    */
   public void closeApp() {
      Platform.exit();
   }

   /**
    * method for setting flag to
    * onButton = true if mouse situated on button zone
    * it's blocks a dragging
    */
   public void buttonDetected() {
      onButton = true;
   }

   /**
    * clearing onButton flag
    * mouse now doesn't situated on button
    */
   public void buttonUnDetected() {
      onButton = false;
   }

   /**
    * hide button
    *
    * @param stage stage of application
    */
   public void hide(Stage stage) {
      if (menu != null && menu.isShows()) {
         menu.closeMenu();
      }

      if (bottomMenuView != null && bottomMenuView.isShows()) {
         bottomMenuView.closeHistory();
      }

      stage.setIconified(true);
   }

   /**
    * method for detecting where mouse was clicked
    */
   public void pressing(MouseEvent mouseEvent) {

      if (menu != null && menu.isShows()) {
         menu.closeMenu();
         pressedForDrag = false;
      } else {
         pressedForDrag = (!onBorder && !onButton);
         if (isFull) {
            int screenWidth = Properties.getScreenWidth();
            double appWidth = screenWidth - savedRightAnchor - savedLeftAnchor;
            if (mouseEvent.getScreenX() < appWidth + Properties.X_BORDER) {
               startAnchorLeft = 0 - Properties.X_BORDER;
               startAnchorRight = savedRightAnchor + savedLeftAnchor + Properties.X_BORDER;
            } else if (mouseEvent.getScreenX() > (screenWidth - appWidth) + Properties.X_BORDER) {
               startAnchorRight = 0 - Properties.X_BORDER;
               startAnchorLeft = screenWidth - appWidth + Properties.X_BORDER;
            } else {
               startAnchorLeft = mouseEvent.getScreenX() - appWidth / 2 + Properties.X_BORDER;
               startAnchorRight = screenWidth - mouseEvent.getScreenX() - appWidth / 2 - Properties.X_BORDER;
            }
            startAnchorTop = 0.0 + Properties.Y_BORDER;
            startAnchorBottom = savedBottomAnchor + savedTopAnchor - Properties.Y_BORDER;
         } else {
            startAnchorLeft = AnchorPane.getLeftAnchor(area);
            startAnchorRight = AnchorPane.getRightAnchor(area);
            startAnchorTop = AnchorPane.getTopAnchor(area);
            startAnchorBottom = AnchorPane.getBottomAnchor(area);

            if (bottomMenuView != null && bottomMenuView.isShows()) {
               bottomMenuView.initStartAnchor();
            }
         }

         xOffset = ((Node) mouseEvent.getSource()).getScene().getWindow().getX() - mouseEvent.getScreenX();
         yOffset = ((Node) mouseEvent.getSource()).getScene().getWindow().getY() - mouseEvent.getScreenY();

      }

   }

   /**
    * method for rounding a coordinate values
    * i created it for killing values like 328.9999992
    *
    * @param amount double to round
    * @return rounded double value
    */
   private int getRounded(double amount) {
      DecimalFormat df = new DecimalFormat("#");
      df.setRoundingMode(RoundingMode.HALF_UP);
      return Integer.parseInt(df.format(amount));
   }

   /**
    * method for showing a left side menu
    *
    * @param button pane of button which calling menu out
    * @param root   invisible root pane of calculator
    */
   public void showMenu(AnchorPane button, AnchorPane root) {
      if (menu == null) {
         menu = new MenuView(button, area, root, keyListener);
      }
      menu.show();
   }

   /**
    * method for initial of border coordinates of calculator
    */
   public void beforeResizePress(MouseEvent mouseEvent) {
      int appWidth = getRounded(area.localToScreen(area.getBoundsInLocal()).getWidth());
      int appHeight = getRounded(area.localToScreen(area.getBoundsInLocal()).getHeight());

      topBeforeResizeY = mouseEvent.getY() - Properties.Y_BORDER;
      bottomBeforeResizeY = mouseEvent.getY() - appHeight + Properties.Y_BORDER;
      leftBeforeResizeX = -(mouseEvent.getX() - Properties.X_BORDER);
      rightBeforeResizeX = mouseEvent.getX() - appWidth + Properties.X_BORDER;
   }

   /**
    * resizing a right border
    */
   private void doResizeE(MouseEvent mouseEvent) {
      if (mouseEvent.getX() + Properties.X_BORDER - rightBeforeResizeX >= 338) {
         AnchorPane.setRightAnchor(area, localScreenWidth - mouseEvent.getSceneX() - Properties.X_BORDER + rightBeforeResizeX);
      } else if (mouseEvent.getX() + Properties.X_BORDER - rightBeforeResizeX < 338) {
         AnchorPane.setRightAnchor(area, localScreenWidth - (AnchorPane.getLeftAnchor(area) + 338));
      }
   }

   /**
    * resizing a left border
    */
   private void doResizeW(MouseEvent mouseEvent) {
      double mouseX = mouseEvent.getX() - 1;
      if (area.localToScreen(area.getBoundsInLocal()).getMinX() <= -7.9) {
         mouseX += 2;
      }
      if (mouseX - Properties.X_BORDER + leftBeforeResizeX < 0) {
         AnchorPane.setLeftAnchor(area, mouseEvent.getSceneX() - Properties.X_BORDER + leftBeforeResizeX);
      } else if (mouseX - Properties.X_BORDER + leftBeforeResizeX > 0) {
         if (localScreenWidth - AnchorPane.getRightAnchor(area) - AnchorPane.getLeftAnchor(area) - mouseX - Properties.X_BORDER - leftBeforeResizeX > 322) {
            AnchorPane.setLeftAnchor(area, mouseEvent.getSceneX() - Properties.X_BORDER + leftBeforeResizeX);
         } else {
            AnchorPane.setLeftAnchor(area, localScreenWidth - (AnchorPane.getRightAnchor(area) + 338));
         }
      }
   }

   /**
    * resizing a top border
    */
   private void doResizeN(MouseEvent mouseEvent) {
      if (mouseEvent.getY() - Properties.Y_BORDER - topBeforeResizeY < 0) {
         AnchorPane.setTopAnchor(area, mouseEvent.getSceneY() - Properties.Y_BORDER - topBeforeResizeY);
      } else if (mouseEvent.getY() - Properties.Y_BORDER - topBeforeResizeY > 0) {
         if (localScreenHeight - AnchorPane.getBottomAnchor(area) - AnchorPane.getTopAnchor(area) - mouseEvent.getY() - Properties.Y_BORDER + topBeforeResizeY > 495) {
            AnchorPane.setTopAnchor(area, mouseEvent.getSceneY() - Properties.Y_BORDER - topBeforeResizeY);
         } else {
            AnchorPane.setTopAnchor(area, localScreenHeight - (AnchorPane.getBottomAnchor(area) + 507));
         }
      }
   }

   /**
    * resizing a bottom border
    */
   private void doResizeS(MouseEvent mouseEvent) {
      if (mouseEvent.getY() + Properties.Y_BORDER - bottomBeforeResizeY >= 507) {
         AnchorPane.setBottomAnchor(area, localScreenHeight - mouseEvent.getSceneY() - Properties.Y_BORDER + bottomBeforeResizeY);
      } else {
         AnchorPane.setBottomAnchor(area, localScreenHeight - AnchorPane.getTopAnchor(area) - 507);
      }
   }

   /**
    * this method makes borders of calculator black on focus
    * and gray a bit on focusing out of calculator
    */
   public void focusUnfocusedMainPane(MouseEvent mouseEvent, Pane exitButton, Pane maximizeButton, Pane minimizeButton) {
      GridPane visiblePane = (GridPane) ((AnchorPane) mouseEvent.getSource()).getChildren().get(0);
      visiblePane.setFocusTraversable(true);

      Node exitText = exitButton.getChildren().get(0);
      Node maximizeText = maximizeButton.getChildren().get(0);
      Node minimizeText = minimizeButton.getChildren().get(0);

      visiblePane.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
         if (newValue) {
            visiblePane.setId("clearVisionPane");

            exitText.setId("exitText");
            maximizeText.setId("maximizeAndMinimizeText");
            minimizeText.setId("maximizeAndMinimizeText");
         } else {
            visiblePane.setId("unFocused");

            exitText.setId("unFocusedExitText");
            maximizeText.setId("unFocusedMaximizeAndMinimizeText");
            minimizeText.setId("unFocusedMaximizeAndMinimizeText");
         }
      });
   }

   /**
    * detecting is memory buttons have to be enabled
    *
    * @param root invisible root pane of application
    */
   public void lookForMemoryButtons(AnchorPane root) {
      AnchorPane button_mr = (AnchorPane) root.lookup("#button_mr");
      AnchorPane button_mc = (AnchorPane) root.lookup("#button_mc");
      AnchorPane button_open_memory = (AnchorPane) root.lookup("#button_open_memory");
      Label memoryLabel = (Label) root.lookup("#memoryText");
      if (!memoryLabel.getText().equals("") && !root.lookup("#button_plus").isDisable()) {
         button_mr.setDisable(false);
         button_mc.setDisable(false);
         button_open_memory.setDisable(false);
      } else {
         button_mr.setDisable(true);
         button_mc.setDisable(true);
         button_open_memory.setDisable(true);
      }
   }

   /**
    * activating a key listener after dragging
    */
   public void draggingFinish() {
      keyListener.setEnable(true);
   }

   /**
    * method for showing bottom menu out
    *
    * @param rootLayout  root pane of application
    * @param callHistory if it's true - history will be shown. false is memory bottom menu
    */
   public void showBottomMenu(AnchorPane rootLayout, boolean callHistory) {
      if (bottomMenuView == null) {
         bottomMenuView = new BottomMenuView(area, rootLayout, keyListener);
      }
      bottomMenuView.show(callHistory);
   }
}
