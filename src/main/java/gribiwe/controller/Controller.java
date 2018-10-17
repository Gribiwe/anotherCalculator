package gribiwe.controller;

import gribiwe.controller.util.HistoryLineParser;
import gribiwe.controller.util.OutputNumberParser;
import gribiwe.model.ModelBrainImpl;
import gribiwe.model.dto.BuildingNumberDto;
import gribiwe.model.dto.FormingSpecialOperationsDto;
import gribiwe.model.dto.HistoryLineDto;
import gribiwe.model.exception.*;
import gribiwe.model.util.Digit;
import gribiwe.model.util.SimpleOperation;
import gribiwe.model.util.SpecialOperation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import static gribiwe.model.util.MemoryOperation.ADD;
import static gribiwe.model.util.MemoryOperation.SUBTRACT;

/**
 * class of controller for connecting view part of calculator
 * with logic (model) part
 *
 * @author Gribiwe
 */
public class Controller implements Initializable {

   /**
    * message of divide by zero exception
    *
    * @see gribiwe.model.exception.ZeroDivideException
    */
   private static final String DIVIDE_BY_ZERO_EXCEPTION_TEXT = "Деление на ноль невозможно";

   /**
    * message of zero divide by zero exception
    *
    * @see gribiwe.model.exception.ZeroDivideZeroException
    */
   private static final String ZERO_DIVIDE_BY_ZERO_EXCEPTION_TEXT = "Результат не определен";

   /**
    * message of incorrect data exception
    *
    * @see gribiwe.model.exception.UncorrectedDataException
    */
   private static final String INCORRECT_DATA_EXCEPTION_TEXT = "Введены неверные данные";

   /**
    * message of overflow
    */
   private static final String OVERFLOW_EXCEPTION_TEXT = "Переполнение";

   /**
    * label for viewing a current number
    */
   public Label inputFieldNumber;

   /**
    * label for viewing a history line
    */
   public Label historyLine;

   /**
    * label for viewing a current memory number
    * while memory isn't active, user don't see
    * this label
    */
   public Label memoryText;

   /**
    * button for loading number from memory to inputFieldNumber
    */
   public AnchorPane button_mr;

   /**
    * button for clearing number from memory
    */
   public AnchorPane button_mc;

   /**
    * button for opening a memory
    */
   public AnchorPane button_open_memory;

   /**
    * this button doing nothing
    * but it's suppose this button
    * adds a new memory number
    */
   public AnchorPane button_ms;

   /**
    * button for subtracting from memory
    */
   public AnchorPane button_msub;

   /**
    * button for adding to memory
    */
   public AnchorPane button_mp;

   /**
    * operation percent button
    */
   public AnchorPane button_percent;

   /**
    * operation root button
    */
   public AnchorPane button_root;

   /**
    * operation square button
    */
   public AnchorPane button_square;

   /**
    * operation one divide by x button
    */
   public AnchorPane button_onedivx;

   /**
    * operation divide button
    */
   public AnchorPane button_divide;

   /**
    * operation multiple button
    */
   public AnchorPane button_multiple;

   /**
    * operation subtract button
    */
   public AnchorPane button_subtract;

   /**
    * operation plus button
    */
   public AnchorPane button_plus;

   /**
    * operation negate button
    */
   public AnchorPane button_negate;

   /**
    * button for adding point to
    * current number
    */
   public AnchorPane button_point;

   /**
    * parser of numbers which will be sent to view
    */
   private OutputNumberParser outputNumberParser;

   /**
    * parser of history line for forming
    * a string value for history line label
    */
   private HistoryLineParser historyLineParser;

   /**
    * exemplar of model brain
    */
   private ModelBrainImpl mainModel;

   /**
    * shows is buttons of calculator
    * blocked (by exception)
    */
   private boolean enabledOperationButtons = true;

   /**
    * initial method. Calls by loading application
    */
   @Override
   public void initialize(URL location, ResourceBundle resources) {
      mainModel = new ModelBrainImpl();
      outputNumberParser = new OutputNumberParser();
      historyLineParser = new HistoryLineParser();
   }

   /**
    * method for adding number one to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitOne() {
      mainModel.addDigit(Digit.ONE);
      updateAll();
   }

   /**
    * method for adding number two to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitTwo() {
      mainModel.addDigit(Digit.TWO);
      updateAll();
   }

   /**
    * method for adding number three to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitThree() {
      mainModel.addDigit(Digit.THREE);
      updateAll();
   }

   /**
    * method for adding number four to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitFour() {
      mainModel.addDigit(Digit.FOUR);
      updateAll();
   }


   /**
    * method for adding number five to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitFive() {
      mainModel.addDigit(Digit.FIVE);
      updateAll();
   }

   /**
    * method for adding number six to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitSix() {
      mainModel.addDigit(Digit.SIX);
      updateAll();
   }

   /**
    * method for adding number seven to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitSeven() {
      mainModel.addDigit(Digit.SEVEN);
      updateAll();
   }

   /**
    * method for adding number eight to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitEight() {
      mainModel.addDigit(Digit.EIGHT);
      updateAll();
   }

   /**
    * method for adding number nine to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitNine() {
      mainModel.addDigit(Digit.NINE);
      updateAll();
   }

   /**
    * method for adding number zero to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitZero() {
      mainModel.addDigit(Digit.ZERO);
      updateAll();
   }

   /**
    * method for adding point to
    * printed number (or new number user will print)
    */
   @FXML
   public void addPoint() {
      mainModel.addPoint();
      updateAll();
   }

   /**
    * method for deletion a one number from printed number
    */
   @FXML
   public void deleteDigit() {
      mainModel.deleteDigit();
      updateAll();
   }

   /**
    * method for deletion all of numbers from printed number
    */
   @FXML
   public void deleteAllDigits() {
      mainModel.deleteAllDigits();
      updateAll();
   }

   /**
    * method for deletion all of numbers from input label
    * and history
    */
   @FXML
   public void deleteAllDigitsAndHistory() {
      mainModel.deleteAllDigitsAndHistory();
      updateAll();
   }

   /**
    * method of operation percent
    */
   @FXML
   public void doPercent() {
      try {
         mainModel.doPercent();
         updateAll();
      } catch (OverflowException e) {
         updateError(OVERFLOW_EXCEPTION_TEXT);
      }
   }

   /**
    * method of operation negate
    */
   @FXML
   public void
   doNegate() {
      mainModel.doNegate();
      updateAll();
   }

   /**
    * method of operation plus
    */
   @FXML
   public void doPlus() {
      try {
         mainModel.doOperation(SimpleOperation.PLUS);
         updateAll();
      } catch (ZeroDivideException e) {
         updateError(DIVIDE_BY_ZERO_EXCEPTION_TEXT);
      } catch (ZeroDivideZeroException e) {
         updateError(ZERO_DIVIDE_BY_ZERO_EXCEPTION_TEXT);
      } catch (OverflowException e) {
         updateError(OVERFLOW_EXCEPTION_TEXT);
      }
   }

   /**
    * method of operation subtract
    */
   @FXML
   public void doSubtract() {
      try {
         mainModel.doOperation(SimpleOperation.SUBTRACT);
         updateAll();
      } catch (ZeroDivideException e) {
         updateError(DIVIDE_BY_ZERO_EXCEPTION_TEXT);
      } catch (ZeroDivideZeroException e) {
         updateError(ZERO_DIVIDE_BY_ZERO_EXCEPTION_TEXT);
      } catch (OverflowException e) {
         updateError(OVERFLOW_EXCEPTION_TEXT);
      }
   }

   /**
    * method of operation multiply
    */
   @FXML
   public void doMultiply() {
      try {
         mainModel.doOperation(SimpleOperation.MULTIPLY);
         updateAll();
      } catch (ZeroDivideException e) {
         updateError(DIVIDE_BY_ZERO_EXCEPTION_TEXT);
      } catch (ZeroDivideZeroException e) {
         updateError(ZERO_DIVIDE_BY_ZERO_EXCEPTION_TEXT);
      } catch (OverflowException e) {
         updateError(OVERFLOW_EXCEPTION_TEXT);
      }
   }

   /**
    * method of operation divide
    */
   @FXML
   public void doDivide() {
      try {
         mainModel.doOperation(SimpleOperation.DIVIDE);
         updateAll();
      } catch (ZeroDivideException e) {
         updateError(DIVIDE_BY_ZERO_EXCEPTION_TEXT);
      } catch (ZeroDivideZeroException e) {
         updateError(ZERO_DIVIDE_BY_ZERO_EXCEPTION_TEXT);
      } catch (OverflowException e) {
         updateError(OVERFLOW_EXCEPTION_TEXT);
      }
   }

   /**
    * method of operation square
    */
   @FXML
   public void doSquare() {
      try {
         mainModel.doSpecialOperation(SpecialOperation.SQUARE);
         updateAll();
      } catch (ZeroDivideException e) {
         updateError(DIVIDE_BY_ZERO_EXCEPTION_TEXT);
      } catch (UncorrectedDataException e) {
         updateError(INCORRECT_DATA_EXCEPTION_TEXT);
      } catch (OverflowException e) {
         updateError(OVERFLOW_EXCEPTION_TEXT);
      }
   }

   /**
    * method of operation one divide by x
    */
   @FXML
   public void doOneDivX() {
      try {
         mainModel.doSpecialOperation(SpecialOperation.ONE_DIV_X);
         updateAll();
      } catch (ZeroDivideException e) {
         updateError(DIVIDE_BY_ZERO_EXCEPTION_TEXT);
      } catch (UncorrectedDataException e) {
         updateError(INCORRECT_DATA_EXCEPTION_TEXT);
      } catch (OverflowException e) {
         updateError(OVERFLOW_EXCEPTION_TEXT);
      }
   }

   /**
    * method of operation root
    */
   @FXML
   public void doRoot() {
      try {
         mainModel.doSpecialOperation(SpecialOperation.ROOT);
         updateAll();
      } catch (ZeroDivideException e) {
         updateError(DIVIDE_BY_ZERO_EXCEPTION_TEXT);
      } catch (UncorrectedDataException e) {
         updateError(INCORRECT_DATA_EXCEPTION_TEXT);
      } catch (OverflowException e) {
         updateError(OVERFLOW_EXCEPTION_TEXT);
      }
   }

   /**
    * method of equals calling
    */
   @FXML
   public void doEquals() {
      try {
         mainModel.doEquals();
         updateAll();
      } catch (ZeroDivideException e) {
         updateError(DIVIDE_BY_ZERO_EXCEPTION_TEXT);
      } catch (ZeroDivideZeroException e) {
         updateError(ZERO_DIVIDE_BY_ZERO_EXCEPTION_TEXT);
      } catch (OverflowException e) {
         updateError(OVERFLOW_EXCEPTION_TEXT);
      }
   }

   /**
    * method of adding number to memory
    */
   @FXML
   public void doAddMemory() {
      mainModel.operateMemory(ADD);
      updateAll();
   }

   /**
    * method of removing number from memory
    */
   @FXML
   public void doRemoveMemory() {
      mainModel.operateMemory(SUBTRACT);
      updateAll();
   }

   /**
    * method of clearing memory
    */
   @FXML
   public void doClearMemory() {
      mainModel.clearMemory();
      updateAll();
   }

   /**
    * method of loading memory number
    */
   @FXML
   public void loadFromMemory() {
      try {
         mainModel.loadFromMemory();
         updateAll();
      } catch (OverflowException e) {
         updateError(OVERFLOW_EXCEPTION_TEXT);
      }
   }

   /**
    * method which gets information from model
    * and sends then to view
    *
    * @see OutputNumberParser
    * @see HistoryLineParser
    */
   private void updateAll() {
      updateHistory();
      updateOutPutNumber();
      updateMemory();
   }

   private void updateMemory() {
      if (mainModel.isMemoryActive()) {
         BigDecimal memoryNumber = mainModel.getMemoryNumber();
         String memoryString = outputNumberParser.formatResult(memoryNumber, true);
         memoryText.setText(memoryString);
      } else {
         memoryText.setText("");
      }
      if (!enabledOperationButtons) {
         setOperationButtonsDisable(false);
      }
   }

   private void updateOutPutNumber() {
      String outputNumber;
      if (mainModel.isBuildingNumber()) {
         BuildingNumberDto buildingNumberDto = mainModel.getBuildingNumber();
         outputNumber = outputNumberParser.formatInput(buildingNumberDto);
      } else {
         BigDecimal numberAtResult = mainModel.getResultNumber();
         outputNumber = outputNumberParser.formatResult(numberAtResult, true);
      }
      inputFieldNumber.setText(outputNumber);
   }

   private void updateHistory() {
      HistoryLineDto historyLineDto = mainModel.getHistoryLineDto();
      String historyLineText = historyLineParser.parse(historyLineDto);

      if (mainModel.isFormingSpecialOperation()) {
         FormingSpecialOperationsDto formingSpecialOperationsDto = mainModel.getFormingSpecialOperationsDto();
         historyLineText += historyLineParser.parseSpecialOperations(formingSpecialOperationsDto);
      }
      historyLine.setText(historyLineText);
   }

   /**
    * method for sending info about exception
    * to view of calculator
    *
    * @param messageToOutput message to show in result
    */
   private void updateError(String messageToOutput) {
      setOperationButtonsDisable(true);
      inputFieldNumber.setText(messageToOutput);
      updateHistory();
      mainModel.clearModel();
   }

   /**
    * method for blocking a buttons of operations
    * and memory
    *
    * @param disable if true - buttons will be blocked. False - unblocked
    */
   private void setOperationButtonsDisable(boolean disable) {
      enabledOperationButtons = false;
      button_ms.setDisable(disable);
      button_msub.setDisable(disable);
      button_mp.setDisable(disable);
      button_percent.setDisable(disable);
      button_root.setDisable(disable);
      button_square.setDisable(disable);
      button_onedivx.setDisable(disable);
      button_divide.setDisable(disable);
      button_multiple.setDisable(disable);
      button_subtract.setDisable(disable);
      button_plus.setDisable(disable);
      button_negate.setDisable(disable);
      button_point.setDisable(disable);
   }
}
// TODO: 17.10.2018 static. more static
