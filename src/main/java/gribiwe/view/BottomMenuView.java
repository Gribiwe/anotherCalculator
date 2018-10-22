package gribiwe.view;

import gribiwe.view.listener.KeyListener;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 * class for creating a bottom menu.
 * bottom menu used for showing history or
 * memory.
 *
 * @author Gribiwe
 */
public class BottomMenuView {

   /**
    * root of visible part of calculator
    */
   private final AnchorPane mainPane;

   /**
    * root pane of calculator
    */
   private final AnchorPane root;

   /**
    * value that represents is bottom menu shows
    */
   private boolean shows;

   /**
    * visible pane that creates when menu showing
    */
   private AnchorPane visibleArea;

   /**
    * invisible pane for blocking free of menu calculator's area
    */
   private AnchorPane invisibleArea;

   /**
    * amount of pixels of left and right borders
    */
   private static final double X_BORDER = 8;

   /**
    * amount of pixels of top and bottom borders
    */
   private static final double Y_BORDER = 6;

   /**
    * bounds value of mainPane
    * uses for getting coordinates
    */
   private Bounds mainPaneScreenBounds;

   /**
    * bounds value of percentButtonScreenBounds
    * needs for coordinates:
    * maximum y of menu is minimum y of this button
    */
   private Bounds percentButtonScreenBounds;

   /**
    * amount of pixels for menu's height
    */
   private double neededHeight;

   /**
    * left anchor padding value of
    * mainPane for dragging before dragging
    */
   private double startHistoryAnchorLeft;

   /**
    * right anchor padding value of
    * mainPane for dragging before dragging
    */
   private double startHistoryAnchorRight;

   /**
    * bottom anchor padding value of
    * mainPane for dragging before dragging
    */
   private double startHistoryAnchorBottom;

   /**
    * top anchor padding value of
    * mainPane for dragging before dragging
    */
   private double startHistoryAnchorTop;

   /**
    * left anchor padding value of
    * invisiblePane for dragging before dragging
    */
   private double startInvisibleHistoryAnchorLeft;
   /**
    * right anchor padding value of
    * invisiblePane for dragging before dragging
    */
   private double startInvisibleHistoryAnchorRight;
   /**
    * bottom anchor padding value of
    * invisiblePane for dragging before dragging
    */
   private double startInvisibleHistoryAnchorBottom;

   /**
    * top anchor padding value of
    * invisiblePane for dragging before dragging
    */
   private double startInvisibleHistoryAnchorTop;

   /**
    * font for label in history
    */
   private final Font fontForHistoryLabel = Font.font("Segoe UI Semibold", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 14.5);

   /**
    * font for label in memory
    */
   private final Font fontForMemoryLabel = Font.font("Segoe UI Semibold", FontWeight.NORMAL, FontPosture.REGULAR, 23);

   /**
    * shows is this history bottom menu or not
    * if it's false, it means:
    * 1. it's memory bottom menu;
    * 2. it's not initialized yet;
    */
   private boolean isHistory;

   /**
    * label of number at memory
    */
   private Label memoryLabel;

   /**
    * key listener for blocking it when menu shows
    */
   private final KeyListener keyListener;

   /**
    * constructor of bottom menu
    *
    * @param mainPane     root visible pane
    * @param root         root invisible pane
    * @param keyListener  key listener which will blocked
    */
   public BottomMenuView(AnchorPane mainPane, AnchorPane root, KeyListener keyListener) {
      this.mainPane = mainPane;
      this.root = root;
      this.keyListener = keyListener;
   }

   /**
    * method for showing bottom menu
    *
    * @param callHistory if true - history bottom menu will shown
    */
   public void show(boolean callHistory) {
      shows = true;
      keyListener.setEnable(false);
      isHistory = callHistory;
      Node buttonPercent = mainPane.lookup("#button_percent");
      percentButtonScreenBounds = buttonPercent.localToScreen(buttonPercent.getBoundsInLocal());
      mainPaneScreenBounds = mainPane.localToScreen(mainPane.getBoundsInLocal());
      root.lookup("#thirdPartOfcalc").setDisable(true);
      initInvisibleArea();
      initVisibleMenu();

      if (callHistory) {
         initHistoryLabel();
      } else {
         initMemory();
      }
   }

   /**
    * initials of invisible area for blocking free space of calculator
    */
   private void initInvisibleArea() {
      invisibleArea = new AnchorPane();
      root.getChildren().add(invisibleArea);
      invisibleArea.setId("invisibleArea");

      AnchorPane.setLeftAnchor(invisibleArea, mainPaneScreenBounds.getMinX() + X_BORDER  + 1);
      AnchorPane.setTopAnchor(invisibleArea, mainPaneScreenBounds.getMinY() + 35);
      AnchorPane.setBottomAnchor(invisibleArea, Properties.getScreenHeight() - mainPaneScreenBounds.getMaxY() + Y_BORDER + 1);
      AnchorPane.setRightAnchor(invisibleArea, Properties.getScreenWidth() - mainPaneScreenBounds.getMaxX() + X_BORDER + 1);

      invisibleArea.setStyle("-fx-background-color: #c6c6c6");
      invisibleArea.setOpacity(0.01);
      invisibleArea.setOnMouseReleased((event -> closeHistory()));
   }

   /**
    * @return true if bottom menu are shows. false if it isn't
    */
   public boolean isShows() {
      return shows;
   }

   /**
    * initials of memory pane with putting a memory label o the right place
    */
   private void initMemory() {
      AnchorPane memoryNumber = new AnchorPane();
      visibleArea.getChildren().add(memoryNumber);
      AnchorPane.setRightAnchor(memoryNumber, 0D);
      AnchorPane.setLeftAnchor(memoryNumber, 0D);
      AnchorPane.setTopAnchor(memoryNumber, 15D);

      memoryNumber.setMinHeight(70);
      memoryNumber.getStyleClass().add("standardMenuButton");

      memoryLabel = (Label) root.lookup("#memoryText");
      memoryLabel.setOpacity(1);
      memoryNumber.getChildren().add(memoryLabel);
      memoryLabel.setFont(fontForMemoryLabel);

      AnchorPane.setTopAnchor(memoryLabel, 0D);
      AnchorPane.setRightAnchor(memoryLabel, 15D);
   }

   /**
    * initials of history label
    * it's doesn't have any better "Журнала еще нет"
    */
   private void initHistoryLabel() {
      Label thereIsNoHistory = new Label("Журнала еще нет");
      thereIsNoHistory.setId("no_history_label");
      visibleArea.getChildren().add(thereIsNoHistory);
      thereIsNoHistory.setFont(fontForHistoryLabel);

      thereIsNoHistory.setLayoutX(15);
      thereIsNoHistory.setLayoutY(15);
   }

   /**
    * initials of visible panes of history
    */
   private void initVisibleMenu() {
      visibleArea = new AnchorPane();
      root.getChildren().add(visibleArea);
      visibleArea.setId("visibleArea");

      AnchorPane.setBottomAnchor(visibleArea, Properties.getScreenHeight() - mainPaneScreenBounds.getMaxY() + Y_BORDER +1);
      AnchorPane.setLeftAnchor(visibleArea, mainPaneScreenBounds.getMinX() + X_BORDER + 1);
      AnchorPane.setRightAnchor(visibleArea, Properties.getScreenWidth() - mainPaneScreenBounds.getMaxX() + X_BORDER + 1);

      double maxTopAnchor = percentButtonScreenBounds.getMinY();
      neededHeight = percentButtonScreenBounds.getMinY() - mainPaneScreenBounds.getMinY() + 1;

      Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2), new EventHandler<ActionEvent>() {
         private double i = maxTopAnchor + neededHeight;

         @Override
         public void handle(ActionEvent event) {
            AnchorPane.setTopAnchor(visibleArea, i);
            i -= 2;
         }
      }));
      timeline.setOnFinished((event -> AnchorPane.setTopAnchor(visibleArea, maxTopAnchor)));
      timeline.setCycleCount((int) neededHeight / 2);
      timeline.play();
      visibleArea.setStyle("-fx-background-color: #f2f2f2");
   }

   /**
    * method for closing the history
    */
   public void closeHistory() {
      Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
         private double i = AnchorPane.getTopAnchor(visibleArea);

         @Override
         public void handle(ActionEvent event) {
            AnchorPane.setTopAnchor(visibleArea, i);
            i += 4;
         }
      }));
      timeline.setOnFinished((event -> {
         if (!isHistory) {
            root.getChildren().add(memoryLabel);
            memoryLabel.setOpacity(0);
         }
         root.getChildren().remove(visibleArea);
         root.getChildren().remove(invisibleArea);
         shows = false;
         root.lookup("#thirdPartOfcalc").setDisable(false);
         keyListener.setEnable(true);
      }));
      timeline.setCycleCount((int) neededHeight / 4 - 2);
      timeline.play();
   }

   /**
    * initials for start anchor padding of panes
    * needs for dragging
    */
   public void initStartAnchor() {
      startHistoryAnchorLeft = AnchorPane.getLeftAnchor(visibleArea);
      startHistoryAnchorRight = AnchorPane.getRightAnchor(visibleArea);
      startHistoryAnchorTop = AnchorPane.getTopAnchor(visibleArea);
      startHistoryAnchorBottom = AnchorPane.getBottomAnchor(visibleArea);

      startInvisibleHistoryAnchorLeft = AnchorPane.getLeftAnchor(invisibleArea);
      startInvisibleHistoryAnchorRight = AnchorPane.getRightAnchor(invisibleArea);
      startInvisibleHistoryAnchorTop = AnchorPane.getTopAnchor(invisibleArea);
      startInvisibleHistoryAnchorBottom = AnchorPane.getBottomAnchor(invisibleArea);
   }

   /**
    * drags the bottom menu
    *
    * @param xDir amount of pixels to drag on X
    * @param yDir amount of pixels to drag on Y
    */
   public void draggingBottomMenu(double xDir, double yDir) {
      AnchorPane.setLeftAnchor(visibleArea, startHistoryAnchorLeft + xDir);
      AnchorPane.setRightAnchor(visibleArea, startHistoryAnchorRight - xDir);
      AnchorPane.setTopAnchor(visibleArea, startHistoryAnchorTop + yDir);
      AnchorPane.setBottomAnchor(visibleArea, startHistoryAnchorBottom - yDir);

      AnchorPane.setLeftAnchor(invisibleArea, startInvisibleHistoryAnchorLeft + xDir);
      AnchorPane.setRightAnchor(invisibleArea, startInvisibleHistoryAnchorRight - xDir);
      AnchorPane.setTopAnchor(invisibleArea, startInvisibleHistoryAnchorTop + yDir);
      AnchorPane.setBottomAnchor(invisibleArea, startInvisibleHistoryAnchorBottom - yDir);
   }
}
