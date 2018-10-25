package gribiwe.view;

import gribiwe.controller.Controller;
import gribiwe.view.listener.CursorListener;
import gribiwe.view.listener.KeyListener;
import gribiwe.view.listener.OverrunListener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * class for starting JavaFx application
 *
 * @author Gribiwe
 */
public class Starter extends Application {

   /**
    * stage of application
    */
   private Stage primaryStage;

   /**
    * root pane of application
    * it's takes fullscreen so makes able to
    * resize or drag window
    */
   private AnchorPane rootLayout;

   /**
    * application scene
    */
   private Scene scene;

   /**
    * VBox makes my root layout able to
    * be transparent. Without this one
    * transparent will become white
    */
   private VBox box;

   /**
    * listener for cursor.
    * Makes able a resizing and drag
    * also, make some buttons work
    */
   private CursorListener cursorListener;

   /**
    * controller of application
    */
   private Controller controller;

   /**
    * value of minimal width of calculator
    */
   private static final int MIN_WIDTH_OF_CALC = 330;

   /**
    * value of maximal width of calculator
    */
   private static final int MIN_HEIGHT_OF_CALC = 501;

   /**
    * the main method of application
    * starts the application
    */
   public void start(String[] args) {
      launch(args);
   }

   /**
    * style string for close to invisible background color
    * if rgba will be rgba(0, 0, 0, 0) background will be disappeared
    * and not able to click. Listeners will be dead too.
    */
   private final static String closeToFullInvisibleBackGroundColor = "-fx-background-color: rgba(0, 0, 0, 0.01);";

   /**
    * FX style of invisible background color
    */
   private final static String fullInvisibleBackgroundStyle = "-fx-background-color: rgba(0, 0, 0, 0);";

   /**
    * FX:id of button plus
    */
   private final static String BUTTON_PLUS = "#button_plus";

   /**
    * FX:id of output number label
    */
   private final static String LABEL_OUTPUT = "#inputFieldNumber";

   /**
    * FX:id of button maximize
    */
   private final static String BUTTON_MAXIMIZE = "#maximize";

   /**
    * FX:id of text of maximize button
    */
   private final static String MAXIMIZE_TEXT_ID = "#maximizeText";

   /**
    * FX:id of pane of bar of window
    */
   private final static String PANE_BAR = "#bar";

   /**
    * FX:id of button exit
    */
   private final static String BUTTON_EXIT = "#exit";

   /**
    * FX:id of button hide
    */
   private final static String BUTTON_HIDE = "#hide";

   /**
    * FX:id of button open menu
    */
   private final static String BUTTON_OPEN_MENU = "#menuButton";

   /**
    * FX:id of button open history
    */
   private final static String BUTTON_OPEN_HISTORY = "#button_history";

   /**
    * FX:id of button open memory
    */
   private final static String BUTTON_OPEN_MEMORY = "#button_open_memory";

   /**
    * dir at resources of icon image of calculator
    */
   private final static String ICON_IMAGE_PATH = "/calc.png";

   /**
    * path of fxml file of calculator
    */
   private final static String FXML_PATH = "/sample.fxml";

   /**
    * start method for javaFx Application
    */
   @Override
   public void start(Stage primaryStage) {
      initScreenSize();
      initRootLayout();
      initBox();
      initScene();
      this.primaryStage = primaryStage;
      initBorderedVisionPane();
      initViewListeners();
      initStage();
   }

   /**
    * initial of screen width and height
    */
   private void initScreenSize() {
      Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
      Properties.setScreenHeight((int) primaryScreenBounds.getHeight());
      Properties.setScreenWidth((int) primaryScreenBounds.getWidth());
   }

   /**
    * initializing root pane properties
    * loads application structure from fxml file
    */
   private void initRootLayout() {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH));
      try {
         rootLayout = loader.load();
      } catch (IOException e) {
         e.printStackTrace();
      }
      controller = loader.getController();
      rootLayout.setStyle(fullInvisibleBackgroundStyle);
      rootLayout.getChildren().get(0).setStyle(closeToFullInvisibleBackGroundColor); // TODO: 24.10.2018 magic done
      rootLayout.setBackground(Background.EMPTY);
      rootLayout.setLayoutX(0);
      rootLayout.setLayoutY(0);
      rootLayout.setMinWidth(Properties.getScreenWidth());
      rootLayout.setMinHeight(Properties.getScreenHeight());
   }

   /**
    * initializing stage properties like style, icon, etc
    */
   private void initStage() {
      primaryStage.initStyle(StageStyle.TRANSPARENT);
      primaryStage.setScene(scene);
      primaryStage.setResizable(true);
      primaryStage.toBack();
      primaryStage.show();

      Image image = new Image(String.valueOf((getClass().getResource(ICON_IMAGE_PATH))));
      ImageView scaledImageView = new ImageView(image);
      scaledImageView.setSmooth(false);
      primaryStage.getIcons().add(scaledImageView.getImage());
   }

   /**
    * initializing of VBox, making it transparent
    */
   private void initBox() {
      box = new VBox(rootLayout);
      box.setLayoutX(0);
      box.setLayoutY(0);
      box.setMinWidth(Properties.getScreenWidth());
      box.setMaxWidth(Properties.getScreenWidth());
      box.setMinHeight(Properties.getScreenHeight());
      box.setMaxHeight(Properties.getScreenHeight());
      box.setStyle(fullInvisibleBackgroundStyle);
   }

   /**
    * initializing of scene
    */
   private void initScene() {
      scene = new Scene(box);
      scene.setFill(Color.TRANSPARENT);
   }

   /**
    * pane of visible calculator
    * with invisible borders for resizing
    */
   private Pane borderedVisiblePane;

   /**
    * initial of {@link #borderedVisiblePane}
    */
   private void initBorderedVisionPane() {
      borderedVisiblePane = (Pane) scene.lookup("#borderedVisionPane");
      double requiredBottomAnchorPadding;
      double requiredTopAnchorPadding;
      requiredTopAnchorPadding = (Properties.getScreenHeight() - MIN_HEIGHT_OF_CALC) / 2D;
      requiredBottomAnchorPadding = requiredTopAnchorPadding - Properties.Y_BORDER;
      AnchorPane.setTopAnchor(borderedVisiblePane, requiredTopAnchorPadding);
      AnchorPane.setBottomAnchor(borderedVisiblePane, requiredBottomAnchorPadding);

      double requiredSideAnchorPadding;
      requiredSideAnchorPadding = (Properties.getScreenWidth() - MIN_WIDTH_OF_CALC - Properties.X_BORDER) / 2D;
      AnchorPane.setLeftAnchor(borderedVisiblePane, requiredSideAnchorPadding);
      AnchorPane.setRightAnchor(borderedVisiblePane, requiredSideAnchorPadding);
   }

   /**
    * initializing of listeners of keys and mouse
    */
   private void initViewListeners() {
      KeyListener keyListener = new KeyListener(controller, scene.lookup(BUTTON_PLUS));
      Label outputNumber = (Label) scene.lookup(LABEL_OUTPUT);
      new OverrunListener(outputNumber);

      Pane maximizeButton = (Pane) scene.lookup(BUTTON_MAXIMIZE);
      cursorListener = new CursorListener((AnchorPane) borderedVisiblePane,
              (Label) maximizeButton.lookup(MAXIMIZE_TEXT_ID), keyListener);
      borderedVisiblePane.setOnMouseMoved((mouseEvent) -> cursorListener.updateCursor(mouseEvent));
      borderedVisiblePane.setOnMouseDragged((mouseEvent) -> cursorListener.resizeWindow(mouseEvent));
      borderedVisiblePane.setOnMousePressed((mouseEvent) -> cursorListener.beforeResizePress(mouseEvent));

      Pane toolBar = (Pane) scene.lookup(PANE_BAR);
      toolBar.setOnMousePressed((mouseEvent) -> cursorListener.pressing(mouseEvent));
      toolBar.setOnMouseDragged((mouseEvent) -> cursorListener.dragging(mouseEvent));
      toolBar.setOnMouseReleased((mouseEvent) -> cursorListener.draggingFinish());

      Pane exitButton = (Pane) scene.lookup(BUTTON_EXIT);
      exitButton.setOnMouseClicked((mouseEvent) -> cursorListener.closeApp());
      exitButton.setOnMouseExited((mouseEvent) -> cursorListener.buttonUnDetected());
      exitButton.setOnMouseEntered((mouseEvent) -> cursorListener.buttonDetected());

      maximizeButton.setOnMouseExited((mouseEvent) -> cursorListener.buttonUnDetected());
      maximizeButton.setOnMouseEntered((mouseEvent) -> cursorListener.buttonDetected());
      maximizeButton.setOnMouseClicked((mouseEvent) -> cursorListener.fullScreen());

      Pane minimizeButton = (Pane) scene.lookup(BUTTON_HIDE);
      minimizeButton.setOnMouseExited((mouseEvent) -> cursorListener.buttonUnDetected());
      minimizeButton.setOnMouseEntered((mouseEvent) -> cursorListener.buttonDetected());
      minimizeButton.setOnMouseClicked((mouseEvent) -> cursorListener.hide(primaryStage));

      AnchorPane showMenuButton = (AnchorPane) scene.lookup(BUTTON_OPEN_MENU);
      showMenuButton.setOnMouseClicked((mouseEvent) -> cursorListener.showMenu(showMenuButton, rootLayout));

      AnchorPane showHistoryButton = (AnchorPane) scene.lookup(BUTTON_OPEN_HISTORY);
      showHistoryButton.setOnMouseClicked((mouseEvent) -> cursorListener.showBottomMenu(rootLayout, true));

      AnchorPane showMemoryButton = (AnchorPane) scene.lookup(BUTTON_OPEN_MEMORY);
      showMemoryButton.setOnMouseClicked((mouseEvent) -> cursorListener.showBottomMenu(rootLayout, false));

      borderedVisiblePane.setOnMouseClicked((mouseEvent) -> cursorListener.focusUnfocusedMainPane(mouseEvent, exitButton, maximizeButton, minimizeButton));
      rootLayout.setOnMouseClicked((mouseEvent) -> cursorListener.lookForMemoryButtons(rootLayout));
      scene.setOnKeyPressed(keyListener::manageKeyEvent);
   }
}
