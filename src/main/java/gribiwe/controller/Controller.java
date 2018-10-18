package gribiwe.controller;

import gribiwe.controller.util.HistoryLineParser;
import gribiwe.controller.util.OutputNumberParser;
import gribiwe.model.ModelBrain;
import gribiwe.model.dto.BuildingNumber;
import gribiwe.model.dto.BuildingSpecialOperations;
import gribiwe.model.dto.HistoryInfo;
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
    * exemplar of model brain
    */
   private ModelBrain mainModel;

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
      mainModel = new ModelBrain();
   }

   /**
    * method for adding number one to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitOne() {
      updateAll(() ->mainModel.addDigit(Digit.ONE));
   }

   /**
    * method for adding number two to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitTwo() {
      updateAll(() ->mainModel.addDigit(Digit.TWO));
   }

   /**
    * method for adding number three to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitThree() {
      updateAll(() -> mainModel.addDigit(Digit.THREE));
   }

   /**
    * method for adding number four to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitFour() {
      updateAll(() ->  mainModel.addDigit(Digit.FOUR));
   }


   /**
    * method for adding number five to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitFive() {
      updateAll(() ->  mainModel.addDigit(Digit.FIVE));
   }

   /**
    * method for adding number six to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitSix() {
      updateAll(() ->  mainModel.addDigit(Digit.SIX));
   }

   /**
    * method for adding number seven to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitSeven() {
      updateAll(() -> mainModel.addDigit(Digit.SEVEN));
   }

   /**
    * method for adding number eight to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitEight() {
      updateAll(() -> mainModel.addDigit(Digit.EIGHT));
   }

   /**
    * method for adding number nine to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitNine() {
      updateAll(() -> mainModel.addDigit(Digit.NINE));
   }

   /**
    * method for adding number zero to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitZero() {
      updateAll(() -> mainModel.addDigit(Digit.ZERO));
   }

   /**
    * method for adding point to
    * printed number (or new number user will print)
    */
   @FXML
   public void addPoint() {
      updateAll(() ->mainModel.addPoint());
   }

   /**
    * method for deletion a one number from printed number
    */
   @FXML
   public void deleteDigit() {
      updateAll(() -> mainModel.deleteDigit());
   }

   /**
    * method for deletion all of numbers from printed number
    */
   @FXML
   public void deleteAllDigits() {
      updateAll(() ->mainModel.deleteAllDigits());
   }

   /**
    * method for deletion all of numbers from input label
    * and history
    */
   @FXML
   public void deleteAllDigitsAndHistory() {
      updateAll(() -> mainModel.deleteAllDigitsAndHistory());
   }

   /**
    * method of operation percent
    */
   @FXML
   public void doPercent() {
         updateAll(() -> mainModel.doPercent());
   }

   /**
    * method of operation negate
    */
   @FXML
   public void
   doNegate() {
      updateAll(() -> mainModel.doNegate());
   }

   /**
    * method of operation plus
    */
   @FXML
   public void doPlus() {
      updateAll(() -> mainModel.doOperation(SimpleOperation.PLUS));
   }

   /**
    * method of operation subtract
    */
   @FXML
   public void doSubtract() {
      updateAll(() -> mainModel.doOperation(SimpleOperation.SUBTRACT));
   }

   /**
    * method of operation multiply
    */
   @FXML
   public void doMultiply() {
      updateAll(() -> mainModel.doOperation(SimpleOperation.MULTIPLY));
   }

   /**
    * method of operation divide
    */
   @FXML
   public void doDivide() {
      updateAll(() -> mainModel.doOperation(SimpleOperation.DIVIDE));
   }

   /**
    * method of operation square
    */
   @FXML
   public void doSquare() {
      updateAll(() -> mainModel.doSpecialOperation(SpecialOperation.SQUARE));
   }

   /**
    * method of operation one divide by x
    */
   @FXML
   public void doOneDivX() {
      updateAll(() -> mainModel.doSpecialOperation(SpecialOperation.ONE_DIV_X));
   }

   /**
    * method of operation root
    */
   @FXML
   public void doRoot() {
      updateAll(() -> mainModel.doSpecialOperation(SpecialOperation.ROOT));
   }

   /**
    * method of equals calling
    */
   @FXML
   public void doEquals() {
      updateAll(() -> mainModel.doEquals());
   }

   /**
    * method of adding number to memory
    */
   @FXML
   public void doAddMemory() {
      updateAll(() -> mainModel.operateMemory(ADD));
   }

   /**
    * method of removing number from memory
    */
   @FXML
   public void doRemoveMemory() {
      updateAll(() -> mainModel.operateMemory(SUBTRACT));
   }

   /**
    * method of clearing memory
    */
   @FXML
   public void doClearMemory() {
      updateAll(() -> mainModel.clearMemory());
   }

   /**
    * method of loading memory number
    */
   @FXML
   public void loadFromMemory() {
      updateAll(() -> mainModel.loadFromMemory());
   }

   /**
    * method which gets information from model
    * and sends then to view
    *
    * @param action action to do with model
    * @see OutputNumberParser
    * @see HistoryLineParser
    */
   private void updateAll(ControllerAction action) {
      BigDecimal modelResponseNumber;
      try {
         modelResponseNumber = action.doAction();
         String outputNumber;
         if (mainModel.isBuildingNumber()) {
            BuildingNumber buildingNumber = mainModel.getBuildingNumber();
            outputNumber = OutputNumberParser.formatInput(buildingNumber);
         } else {
            outputNumber = OutputNumberParser.formatResult(modelResponseNumber, true);
         }
         inputFieldNumber.setText(outputNumber);
         updateHistory();
         updateMemory();
      } catch (OverflowException e) {
         updateError(OVERFLOW_EXCEPTION_TEXT);
      } catch (ZeroDivideException e) {
         updateError(DIVIDE_BY_ZERO_EXCEPTION_TEXT);
      } catch (ZeroDivideZeroException e) {
         updateError(ZERO_DIVIDE_BY_ZERO_EXCEPTION_TEXT);
      } catch (UncorrectedDataException e) {
         updateError(INCORRECT_DATA_EXCEPTION_TEXT);
      }
   }

   /**
    * method which gets information about
    * memory from model, parsing it
    * and sends to the view
    */
   private void updateMemory() {
      if (mainModel.isMemoryActive()) {
         BigDecimal memoryNumber = mainModel.getMemoryNumber();
         String memoryString = OutputNumberParser.formatResult(memoryNumber, true);
         memoryText.setText(memoryString);
      } else {
         memoryText.setText("");
      }
      if (!enabledOperationButtons) {
         setOperationButtonsDisable(false);
      }
   }

   /**
    * method which gets information about
    * history line from the model, parsing it
    * and sends it to the view
    */
   private void updateHistory() {
      HistoryInfo historyInfo = mainModel.getHistoryLineDto();
      String historyLineText = HistoryLineParser.parse(historyInfo);

      if (mainModel.isFormingSpecialOperation()) {
         BuildingSpecialOperations buildingSpecialOperations = mainModel.getFormingSpecialOperationsDto();
         historyLineText += HistoryLineParser.parseSpecialOperations(buildingSpecialOperations);
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
