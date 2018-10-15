package gribiwe.controller;

import gribiwe.controller.util.LastSpecialOperationStoryParser;
import gribiwe.controller.util.HistoryLineParser;
import gribiwe.controller.util.OutputNumberParser;
import gribiwe.model.ModelBrain;
import gribiwe.model.ModelBrainImpl;
import gribiwe.model.dto.*;
import gribiwe.model.exception.CalculatorException;
import gribiwe.model.exception.UncorrectedDataException;
import gribiwe.model.exception.ZeroDivideException;
import gribiwe.model.exception.ZeroDivideZeroException;
import gribiwe.model.util.Digit;
import gribiwe.model.util.SimpleOperation;
import gribiwe.model.util.SpecialOperation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import static gribiwe.model.util.MemoryOperation.*;

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
    * value of max possible exponent
    */
   private final static int MAX_EXPONENT = 9999;

   /**
    * value of min possible exponent
    */
   private final static int MIN_EXPONENT = -9999;

   /**
    * string patter for checking overflow
    */
   private final static String CHECK_OVERFLOW_NUMBER_PATTERN = "0.###############E0";

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
    * parser of special "fake" operations
    * for history
    */
   private LastSpecialOperationStoryParser lastSpecialOperationStoryParser;

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
      mainModel = new ModelBrainImpl();
      outputNumberParser = new OutputNumberParser();
      historyLineParser = new HistoryLineParser();
      lastSpecialOperationStoryParser = new LastSpecialOperationStoryParser();
   }

   /**
    * method for adding number one to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitOne() {
      updateView(() -> mainModel.addDigit(Digit.ONE));
   }

   /**
    * method for adding number two to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitTwo() {
      updateView(() -> mainModel.addDigit(Digit.TWO));
   }

   /**
    * method for adding number three to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitThree() {
      updateView(() -> mainModel.addDigit(Digit.THREE));
   }

   /**
    * method for adding number four to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitFour() {
      updateView(() -> mainModel.addDigit(Digit.FOUR));
   }


   /**
    * method for adding number five to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitFive() {
      updateView(() -> mainModel.addDigit(Digit.FIVE));
   }

   /**
    * method for adding number six to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitSix() {
      updateView(() -> mainModel.addDigit(Digit.SIX));
   }

   /**
    * method for adding number seven to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitSeven() {
      updateView(() -> mainModel.addDigit(Digit.SEVEN));
   }

   /**
    * method for adding number eight to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitEight() {
      updateView(() -> mainModel.addDigit(Digit.EIGHT));
   }

   /**
    * method for adding number nine to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitNine() {
      updateView(() -> mainModel.addDigit(Digit.NINE));
   }

   /**
    * method for adding number zero to
    * printed number (or new number user will print)
    */
   @FXML
   public void addDigitZero() {
      updateView(() -> mainModel.addDigit(Digit.ZERO));
   }

   /**
    * method for adding point to
    * printed number (or new number user will print)
    */
   @FXML
   public void addPoint() {
      updateView(() -> mainModel.addPoint());
   }

   /**
    * method for deletion a one number from printed number
    */
   @FXML
   public void deleteDigit() {
      updateView(() -> mainModel.deleteDigit());
   }

   /**
    * method for deletion all of numbers from printed number
    */
   @FXML
   public void deleteAllDigits() {
      updateView(() -> mainModel.deleteAllDigits());
   }

   /**
    * method for deletion all of numbers from input label
    * and history
    */
   @FXML
   public void deleteAllDigitsAndHistory() {
      updateView(() -> mainModel.deleteAllDigitsAndHistory());
   }

   /**
    * method of operation percent
    *
    * @see CalculatorAction
    */
   @FXML
   public void doPercent() {
      updateView(() -> mainModel.doPercent());
   }

   /**
    * method of operation negate
    *
    * @see CalculatorAction
    */
   @FXML
   public void doNegate() {
      updateView(() -> mainModel.doNegate());
   }

   /**
    * method of operation plus
    *
    * @see CalculatorAction
    */
   @FXML
   public void doPlus() {
      updateView(() -> mainModel.doOperation(SimpleOperation.PLUS));
   }

   /**
    * method of operation subtract
    *
    * @see CalculatorAction
    */
   @FXML
   public void doSubtract() {
      updateView(() -> mainModel.doOperation(SimpleOperation.SUBTRACT));
   }

   /**
    * method of operation multiply
    *
    * @see CalculatorAction
    */
   @FXML
   public void doMultiply() {
      updateView(() -> mainModel.doOperation(SimpleOperation.MULTIPLY));
   }

   /**
    * method of operation divide
    *
    * @see CalculatorAction
    */
   @FXML
   public void doDivide() {
      updateView(() -> mainModel.doOperation(SimpleOperation.DIVIDE));
   }

   /**
    * method of operation square
    *
    * @see CalculatorAction
    */
   @FXML
   public void doSquare() {
      updateView(() -> mainModel.doSpecialOperation(SpecialOperation.SQUARE));
   }

   /**
    * method of operation one divide by x
    *
    * @see CalculatorAction
    */
   @FXML
   public void doOneDivX() {
      updateView(() -> mainModel.doSpecialOperation(SpecialOperation.ONE_DIV_X));
   }

   /**
    * method of operation root
    *
    * @see CalculatorAction
    */
   @FXML
   public void doRoot() {
      updateView(() -> mainModel.doSpecialOperation(SpecialOperation.ROOT));
   }

   /**
    * method of equals calling
    *
    * @see CalculatorAction
    */
   @FXML
   public void doEquals() {
      updateView(() -> mainModel.doEquals());
   }

   /**
    * method of adding number to memory
    *
    * @see CalculatorAction
    */
   @FXML
   public void doAddMemory() {
      updateView(() -> mainModel.operateMemory(ADD));
   }

   /**
    * method of removing number from memory
    *
    * @see CalculatorAction
    */
   @FXML
   public void doRemoveMemory() {
      updateView(() -> mainModel.operateMemory(SUBTRACT));
   }

   /**
    * method of clearing memory
    *
    * @see CalculatorAction
    */
   @FXML
   public void doClearMemory() {
      updateView(() -> mainModel.clearMemory());
   }

   /**
    * method of loading memory number
    *
    * @see CalculatorAction
    */
   @FXML
   public void loadFromMemory() {
      updateView(() -> mainModel.loadFromMemory());
   }

   /**
    * verifies is there is an overflow exception
    *
    * @param answerDTO dto from model to parse at updating view
    *                  with exception
    */
   private boolean verifyOverflow(AnswerDTO answerDTO) {
      String output = new DecimalFormat(CHECK_OVERFLOW_NUMBER_PATTERN).format(answerDTO.getOutputNumberDTO().getValue());
      long exponent = Long.parseLong(output.substring(output.indexOf("E") + 1));

      if (exponent > MAX_EXPONENT || exponent < MIN_EXPONENT) {
         updateError(OVERFLOW_EXCEPTION_TEXT, answerDTO);
         return false;
      }

      return true;
   }

   /**
    * method which processes an {@code AnswerDTO}
    * by parsers. Than sends a string values to view
    *
    * @see CalculatorAction
    * @see AnswerDTO
    * @see OutputNumberParser
    * @see HistoryLineParser
    * @see LastSpecialOperationStoryParser
    */
   private void updateView(CalculatorAction calculatorAction) {
      AnswerDTO answerDTO;
      try {
         answerDTO = calculatorAction.doAction();
      } catch (CalculatorException e) {
         processException(e);
         return;
      }

      if (answerDTO != null && verifyOverflow(answerDTO)) {
         String outputNumber;
         OutputNumberDTO outputNumberDTO = answerDTO.getOutputNumberDTO();
         if (outputNumberDTO.getClass().equals(EnteredNumberDTO.class)) {
            outputNumber = outputNumberParser.formatInput((EnteredNumberDTO) outputNumberDTO);
         } else {
            outputNumber = outputNumberParser.formatResult(outputNumberDTO.getValue(), true);
         }
         inputFieldNumber.setText(outputNumber);

         String historyLineText = historyLineParser.parse(answerDTO.getHistoryLineDTO());
         historyLineText += lastSpecialOperationStoryParser.parse(answerDTO.getTailSpecialOperationHistoryDTO());
         historyLine.setText(historyLineText);

         MemoryDTO memoryDTO = answerDTO.getMemoryDTO();
         if (memoryDTO.isEnable()) {
            String memoryString = outputNumberParser.formatResult(memoryDTO.getMemoryNumber(), true);
            memoryText.setText(memoryString);
         } else {
            memoryText.setText("");
         }
         if (!enabledOperationButtons) {
            setOperationButtonsDisable(false);
         }
      }
   }

   /**
    * method for sending info about exception
    * to view of calculator
    *
    * @param messageToOutput message to show in result
    * @param answerDTO       dto to view
    */
   private void updateError(String messageToOutput, AnswerDTO answerDTO) {
      setOperationButtonsDisable(true);
      mainModel = new ModelBrainImpl();
      inputFieldNumber.setText(messageToOutput);

      HistoryLineDTO historyLineDTO = answerDTO.getHistoryLineDTO();
      TailSpecialOperationHistoryDTO lastSpecialOperationStory = answerDTO.getTailSpecialOperationHistoryDTO();

      String historyLineText = historyLineParser.parse(historyLineDTO);
      historyLineText += lastSpecialOperationStoryParser.parse(lastSpecialOperationStory);
      historyLine.setText(historyLineText);
   }

   /**
    * method for processing an exception
    *
    * @param e exception to process
    */
   private void processException(CalculatorException e) {
      Class exceptionClass = e.getClass();
      AnswerDTO answerDTO = e.getAnswerDTO();

      if (exceptionClass.equals(UncorrectedDataException.class)) {
         updateError(INCORRECT_DATA_EXCEPTION_TEXT, answerDTO);
      } else if (exceptionClass.equals(ZeroDivideException.class)) {
         updateError(DIVIDE_BY_ZERO_EXCEPTION_TEXT, answerDTO);
      } else if (exceptionClass.equals(ZeroDivideZeroException.class)) {
         updateError(ZERO_DIVIDE_BY_ZERO_EXCEPTION_TEXT, answerDTO);
      }
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
