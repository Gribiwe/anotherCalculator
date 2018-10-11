package gribiwe.view;

import gribiwe.view.listener.KeyListener;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * class for creating left menu
 *
 * @author Gribiwe
 */
public class MenuView {

   /**
    * root of visible part of calculator
    */
   private AnchorPane mainPane;

   /**
    * root pane of calculator
    */
   private AnchorPane root;

   /**
    * pane-button of calling of menu
    */
   private AnchorPane button;

   /**
    * width of left side menu
    */
   private static final double WIDTH = 255.0;

   /**
    * value that represents is bottom menu shows
    */
   private boolean shows;

   /**
    * visible pane of menu. Contains another
    * panes of "types of calculator"
    */
   private AnchorPane menu;

   /**
    * invisible pane for blocking free of menu calculator's area
    */
   private AnchorPane invisibleMenu;

   /**
    * value of Y coordinate of top border of button than calls of this menu
    */
   private double topYOfButton;

   /**
    * value of Y coordinate of bottom border of button than calls of this menu
    */
   private double bottomYOfButton;

   /**
    * Scroll pane for scrolling of items
    */
   private ScrollPane scrollPane;

   /**
    * pane which contains content for scroll pane
    */
   private AnchorPane content;

   /**
    * font for labels of this menu
    */
   private Font fontForLabels;

   /**
    * number of monitor width
    */
   private int screenWidth;

   private final static double Y_BORDER = 6;

   private final static double BORDER = 8;

   /**
    * number of monitor height
    */
   private int screenHeight;

   /**
    * key listener for blocking it
    * while menu is shows
    */
   private KeyListener keyListener;

   /**
    * constructor of menu
    *
    * @param button      pane of button which calls of this menu
    * @param mainPane    visible root pane of calculator
    * @param root        invisible root pane of calculator
    * @param keyListener key listener for blocking it while
    *                    menu is shows
    * @param screenHeight       height size of screen
    * @param screenWidth        width size of screen
    */
   public MenuView(AnchorPane button, AnchorPane mainPane, AnchorPane root, int screenWidth, int screenHeight, KeyListener keyListener) {
      this.button = button;
      this.mainPane = mainPane;
      this.root = root;
      this.keyListener = keyListener;
      this.screenHeight = screenHeight;
      this.screenWidth = screenWidth;
      shows = false;
   }

   /**
    * method for showing out the menu
    */
   public void show() {
      keyListener.setEnable(false);
      shows = true;

      topYOfButton = button.localToScreen(button.getBoundsInLocal()).getMinY();
      bottomYOfButton = mainPane.localToScreen(mainPane.getBoundsInLocal()).getMaxY() + 2;

      initInvisibleMenuArea();
      initMenuArea();
      initButton();
      initScrollPane();
      initScrollPaneContent();
      fillContent();
      initAboutProgramButton();
   }

   /**
    * initials for invisible menu pane
    */
   private void initInvisibleMenuArea() {
      invisibleMenu = new AnchorPane();
      root.getChildren().add(invisibleMenu);
      invisibleMenu.setId("invisibleMenuArea");
      double border = 8;
      invisibleMenu.setMinSize(mainPane.getWidth() - border * 2 - 2, bottomYOfButton - topYOfButton - BORDER - 1);
      invisibleMenu.setLayoutX(button.localToScreen(button.getBoundsInLocal()).getMinX());
      invisibleMenu.setLayoutY(topYOfButton);
      invisibleMenu.setStyle("-fx-background-color: #c6c6c6");
      invisibleMenu.setOpacity(0.01);
      invisibleMenu.setOnMouseReleased((event -> closeMenu()));
   }

   /**
    * initials for visible menu area
    */
   private void initMenuArea() {
      menu = new AnchorPane();
      root.getChildren().add(menu);
      menu.setId("menu");
      menu.setMinHeight(bottomYOfButton - topYOfButton - BORDER - 1);
      Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
         private double i = WIDTH;

         @Override
         public void handle(ActionEvent event) {
            i -= 2;
            menu.setMinWidth(WIDTH - i);
            menu.setMaxWidth(WIDTH - i);
         }
      }));
      timeline.setOnFinished((event -> {
         menu.setMinWidth(WIDTH);
         menu.setMaxWidth(WIDTH);
      }));
      timeline.setCycleCount((int) WIDTH / 2);
      timeline.play();
      menu.setLayoutX(button.localToScreen(button.getBoundsInLocal()).getMinX());
      menu.setLayoutY(topYOfButton);
      menu.setStyle("-fx-background-color: #f2f2f2");
   }

   /**
    * re-initials of button which opened this menu
    * now it's closing menu
    */
   private void initButton() {
      AnchorPane menuButton = new AnchorPane();
      menu.getChildren().add(menuButton);
      menuButton.setId("menuButton");
      menuButton.setPrefSize(48, 43);
      menuButton.setMaxSize(48, 43);
      menuButton.getStyleClass().add("standardMenuButton");
      menuButton.setOnMouseClicked((event -> closeMenu()));

      Text originalText = (Text) button.getChildren().get(0);
      Text text = new Text();
      text.setFont(originalText.getFont());
      text.setText(originalText.getText());
      AnchorPane.setLeftAnchor(text, 14D);
      AnchorPane.setTopAnchor(text, 14D);
      menuButton.getChildren().add(text);
   }

   /**
    * initials of scroll pane
    */
   private void initScrollPane() {
      scrollPane = new ScrollPane();
      menu.getChildren().add(scrollPane);
      AnchorPane.setLeftAnchor(scrollPane, 0D);
      AnchorPane.setRightAnchor(scrollPane, 0D);
      AnchorPane.setTopAnchor(scrollPane, 52D);
      AnchorPane.setBottomAnchor(scrollPane, 68D);
      scrollPane.setMaxHeight(scrollPane.getHeight());
      scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
      scrollPane.getStyleClass().add("scroll");
   }

   /**
    * filling content pane with items
    * of types of calculator
    */
   private void fillContent() {
      Font fontForTypes = Font.font("Segoe UI", FontWeight.BLACK, FontPosture.REGULAR, 15D);
      fontForLabels = Font.font("Segoe MDL2 Assets", FontWeight.NORMAL, FontPosture.REGULAR, 16D);
      Label calculatorLabel = new Label("Калькулятор");
      content.getChildren().add(calculatorLabel);
      calculatorLabel.setFont(fontForTypes);
      calculatorLabel.setLayoutX(15);
      calculatorLabel.setLayoutY(10);

      AnchorPane commonCalculator = new AnchorPane();
      content.getChildren().add(commonCalculator);
      commonCalculator.setMinSize(256, 40);
      commonCalculator.getStyleClass().add("standardMenuButton");
      commonCalculator.setLayoutX(0);
      commonCalculator.setLayoutY(40);
      Label commonCalculatorLabel = new Label("\uE1D0    Обычный");
      commonCalculator.getChildren().add(commonCalculatorLabel);
      commonCalculatorLabel.setFont(fontForLabels);
      commonCalculatorLabel.setLayoutX(15);
      commonCalculatorLabel.setLayoutY(10);

      AnchorPane engineerCalculator = new AnchorPane();
      content.getChildren().add(engineerCalculator);
      engineerCalculator.setMinSize(256, 40);
      engineerCalculator.getStyleClass().add("standardMenuButton");
      engineerCalculator.setLayoutX(0);
      engineerCalculator.setLayoutY(80);
      Label engineerCalculatorLabel = new Label("        Инженерный");
      engineerCalculator.getChildren().add(engineerCalculatorLabel);
      engineerCalculatorLabel.setFont(fontForLabels);
      engineerCalculatorLabel.setLayoutX(15);
      engineerCalculatorLabel.setLayoutY(10);

      AnchorPane programmerCalculator = new AnchorPane();
      content.getChildren().add(programmerCalculator);
      programmerCalculator.setMinSize(256, 40);
      programmerCalculator.getStyleClass().add("standardMenuButton");
      programmerCalculator.setLayoutX(0);
      programmerCalculator.setLayoutY(120);
      Label programmerCalculatorLabel = new Label("‹/›    Программист");
      programmerCalculator.getChildren().add(programmerCalculatorLabel);
      programmerCalculatorLabel.setFont(fontForLabels);
      programmerCalculatorLabel.setLayoutX(15);
      programmerCalculatorLabel.setLayoutY(10);

      AnchorPane dateCalculator = new AnchorPane();
      content.getChildren().add(dateCalculator);
      dateCalculator.setMinSize(256, 40);
      dateCalculator.getStyleClass().add("standardMenuButton");
      dateCalculator.setLayoutX(0);
      dateCalculator.setLayoutY(160);
      Label dateCalculatorLabel = new Label("\uE787     Вычисление даты");
      dateCalculator.getChildren().add(dateCalculatorLabel);
      dateCalculatorLabel.setFont(fontForLabels);
      dateCalculatorLabel.setLayoutX(15);
      dateCalculatorLabel.setLayoutY(10);

      Label converterLabel = new Label("Преобразователь");
      content.getChildren().add(converterLabel);
      converterLabel.setFont(fontForTypes);
      converterLabel.setLayoutX(20);
      converterLabel.setLayoutY(210);

      AnchorPane currency = new AnchorPane();
      content.getChildren().add(currency);
      currency.setMinSize(256, 40);
      currency.getStyleClass().add("standardMenuButton");
      currency.setLayoutX(0);
      currency.setLayoutY(240);
      Label currencyLabel = new Label("         Валюта");
      currency.getChildren().add(currencyLabel);
      currencyLabel.setFont(fontForLabels);
      currencyLabel.setLayoutX(15);
      currencyLabel.setLayoutY(10);

      AnchorPane size = new AnchorPane();
      content.getChildren().add(size);
      size.setMinSize(256, 40);
      size.getStyleClass().add("standardMenuButton");
      size.setLayoutX(0);
      size.setLayoutY(280);
      Label sizeLabel = new Label("\uF158    Объем");
      size.getChildren().add(sizeLabel);
      sizeLabel.setFont(fontForLabels);
      sizeLabel.setLayoutX(15);
      sizeLabel.setLayoutY(10);

      AnchorPane length = new AnchorPane();
      content.getChildren().add(length);
      length.setMinSize(256, 40);
      length.getStyleClass().add("standardMenuButton");
      length.setLayoutX(0);
      length.setLayoutY(320);
      Label lengthLabel = new Label("\uECC6    Длина");
      length.getChildren().add(lengthLabel);
      lengthLabel.setFont(fontForLabels);
      lengthLabel.setLayoutX(15);
      lengthLabel.setLayoutY(10);

      AnchorPane weightAndMass = new AnchorPane();
      content.getChildren().add(weightAndMass);
      weightAndMass.setMinSize(256, 40);
      weightAndMass.getStyleClass().add("standardMenuButton");
      weightAndMass.setLayoutX(0);
      weightAndMass.setLayoutY(360);
      Label weightAndMassLabel = new Label("         Вес и масса");
      weightAndMass.getChildren().add(weightAndMassLabel);
      weightAndMassLabel.setFont(fontForLabels);
      weightAndMassLabel.setLayoutX(15);
      weightAndMassLabel.setLayoutY(10);

      AnchorPane temperature = new AnchorPane();
      content.getChildren().add(temperature);
      temperature.setMinSize(256, 40);
      temperature.getStyleClass().add("standardMenuButton");
      temperature.setLayoutX(0);
      temperature.setLayoutY(400);
      Label temperatureLabel = new Label("\uE9CA    Температура");
      temperature.getChildren().add(temperatureLabel);
      temperatureLabel.setFont(fontForLabels);
      temperatureLabel.setLayoutX(15);
      temperatureLabel.setLayoutY(10);

      AnchorPane energy = new AnchorPane();
      content.getChildren().add(energy);
      energy.setMinSize(256, 40);
      energy.getStyleClass().add("standardMenuButton");
      energy.setLayoutX(0);
      energy.setLayoutY(440);
      Label energyLabel = new Label("\uECAD    Энергия");
      energy.getChildren().add(energyLabel);
      energyLabel.setFont(fontForLabels);
      energyLabel.setLayoutX(15);
      energyLabel.setLayoutY(10);

      AnchorPane area = new AnchorPane();
      content.getChildren().add(area);
      area.setMinSize(256, 40);
      area.getStyleClass().add("standardMenuButton");
      area.setLayoutX(0);
      area.setLayoutY(480);
      Label areaLabel = new Label("\uE809    Площадь");
      area.getChildren().add(areaLabel);
      areaLabel.setFont(fontForLabels);
      areaLabel.setLayoutX(15);
      areaLabel.setLayoutY(10);

      AnchorPane speed = new AnchorPane();
      content.getChildren().add(speed);
      speed.setMinSize(256, 40);
      speed.getStyleClass().add("standardMenuButton");
      speed.setLayoutX(0);
      speed.setLayoutY(520);
      Label speedLabel = new Label("\ud83c\udfc3    Скорость");
      speed.getChildren().add(speedLabel);
      speedLabel.setFont(fontForLabels);
      speedLabel.setLayoutX(15);
      speedLabel.setLayoutY(10);

      AnchorPane time = new AnchorPane();
      content.getChildren().add(time);
      time.setMinSize(256, 40);
      time.getStyleClass().add("standardMenuButton");
      time.setLayoutX(0);
      time.setLayoutY(560);
      Label timeLabel = new Label("\uED5A    Время");
      time.getChildren().add(timeLabel);
      timeLabel.setFont(fontForLabels);
      timeLabel.setLayoutX(15);
      timeLabel.setLayoutY(10);

      AnchorPane power = new AnchorPane();
      content.getChildren().add(power);
      power.setMinSize(256, 40);
      power.getStyleClass().add("standardMenuButton");
      power.setLayoutX(0);
      power.setLayoutY(600);
      Label powerLabel = new Label("\uE945    Мощность");
      power.getChildren().add(powerLabel);
      powerLabel.setFont(fontForLabels);
      powerLabel.setLayoutX(15);
      powerLabel.setLayoutY(10);

      AnchorPane data = new AnchorPane();
      content.getChildren().add(data);
      data.setMinSize(256, 40);
      data.getStyleClass().add("standardMenuButton");
      data.setLayoutX(0);
      data.setLayoutY(640);
      Label dataLabel = new Label("\uE7F1    Данные");
      data.getChildren().add(dataLabel);
      dataLabel.setFont(fontForLabels);
      dataLabel.setLayoutX(15);
      dataLabel.setLayoutY(10);

      AnchorPane pressure = new AnchorPane();
      content.getChildren().add(pressure);
      pressure.setMinSize(256, 40);
      pressure.getStyleClass().add("standardMenuButton");
      pressure.setLayoutX(0);
      pressure.setLayoutY(680);
      Label pressureLabel = new Label("\uEC4A    Давление");
      pressure.getChildren().add(pressureLabel);
      pressureLabel.setFont(fontForLabels);
      pressureLabel.setLayoutX(15);
      pressureLabel.setLayoutY(10);

      AnchorPane corner = new AnchorPane();
      content.getChildren().add(corner);
      corner.setMinSize(256, 40);
      corner.getStyleClass().add("standardMenuButton");
      corner.setLayoutX(0);
      corner.setLayoutY(720);
      Label cornerLabel = new Label("         Угол");
      corner.getChildren().add(cornerLabel);
      cornerLabel.setFont(fontForLabels);
      cornerLabel.setLayoutX(15);
      cornerLabel.setLayoutY(10);
   }

   /**
    * initials of button "about program"
    * it's doing nothing
    */
   private void initAboutProgramButton() {
      AnchorPane about = new AnchorPane();
      menu.getChildren().add(about);
      about.setMinSize(256, 40);
      about.getStyleClass().add("standardMenuButton");

      AnchorPane.setRightAnchor(about, 0D);
      AnchorPane.setLeftAnchor(about, 0D);
      AnchorPane.setBottomAnchor(about, 10D);

      Label aboutLabel = new Label("\uE946    О программе");
      about.getChildren().add(aboutLabel);
      aboutLabel.setFont(fontForLabels);
      aboutLabel.setLayoutX(15);
      aboutLabel.setLayoutY(10);
   }

   /**
    * initials for content pane of scroll pane
    */
   private void initScrollPaneContent() {
      content = new AnchorPane();
      scrollPane.setContent(content);
      content.setLayoutY(0);
      content.setLayoutX(0);
      content.setMinSize(500, 700);
      content.setStyle("-fx-background-color: #f2f2f2");
   }

   /**
    * method for closing menu
    */
   public void closeMenu() {
      Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
         private double i = 0;

         @Override
         public void handle(ActionEvent event) {
            i += 4;
            menu.setMinWidth(WIDTH - i);
            menu.setMaxWidth(WIDTH - i);
         }
      }));
      timeline.setOnFinished((event -> {
         root.getChildren().remove(invisibleMenu);
         root.getChildren().remove(menu);
         shows = false;
         keyListener.setEnable(true);
      }));
      timeline.setCycleCount((int) WIDTH / 4 - 2);
      timeline.play();
   }

   /**
    * showing is menu shows
    *
    * @return true if menu is shows. false if isn't
    */
   public boolean isShows() {
      return shows;
   }
}
