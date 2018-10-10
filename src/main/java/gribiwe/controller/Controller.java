package gribiwe.controller;

import gribiwe.controller.util.LastSpecialOperationStoryParser;
import gribiwe.controller.util.HistoryLineParser;
import gribiwe.controller.util.OutputNumberParser;
import gribiwe.model.ModelBrain;
import gribiwe.model.ModelBrainImpl;
import gribiwe.model.dto.*;
import gribiwe.model.exception.CalculatorException;
import gribiwe.model.util.Digit;
import gribiwe.model.util.SimpleOperation;
import gribiwe.model.util.SpecialOperation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * class of controller for connecting view part of calculator
 * with logic (model) part
 *
 * @author Gribiwe
 */
public class Controller implements Initializable {

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
   public AnchorPane button_onedix;

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
   private boolean enabled = true;

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
    * forming {@code Action} object
    * with provided operations for model
    * which returns Answer from model
    * and sends it to method {@link #updateView(Action)}
    *
    * @see Action
    */
   @FXML
   public void doPercent() {
      updateView(() -> mainModel.doSpecialOperation(SpecialOperation.PERCENT));
   }

   /**
    * method of operation negate
    * forming {@code Action} object
    * with provided operations for model
    * which returns Answer from model
    * and sends it to method {@link #updateView(Action)}
    *
    * @see Action
    */
   @FXML
   public void doNegate() {
      updateView(() -> mainModel.doNegate());
   }

   /**
    * method of operation plus
    * forming {@code Action} object
    * with provided operations for model
    * which returns Answer from model
    * and sends it to method {@link #updateView(Action)}
    *
    * @see Action
    */
   @FXML
   public void doPlus() {
      updateView(() -> mainModel.doOperation(SimpleOperation.PLUS));
   }

   /**
    * method of operation subtract
    * forming {@code Action} object
    * with provided operations for model
    * which returns Answer from model
    * and sends it to method {@link #updateView(Action)}
    *
    * @see Action
    */
   @FXML
   public void doSubtract() {
      updateView(() -> mainModel.doOperation(SimpleOperation.SUBTRACT));
   }

   /**
    * method of operation multiply
    * forming {@code Action} object
    * with provided operations for model
    * which returns Answer from model
    * and sends it to method {@link #updateView(Action)}
    *
    * @see Action
    */
   @FXML
   public void doMultiply() {
      updateView(() -> mainModel.doOperation(SimpleOperation.MULTIPLY));
   }

   /**
    * method of operation divide
    * forming {@code Action} object
    * with provided operations for model
    * which returns Answer from model
    * and sends it to method {@link #updateView(Action)}
    *
    * @see Action
    */
   @FXML
   public void doDivide() {
      updateView(() -> mainModel.doOperation(SimpleOperation.DIVIDE));
   }

   /**
    * method of operation square
    * forming {@code Action} object
    * with provided operations for model
    * which returns Answer from model
    * and sends it to method {@link #updateView(Action)}
    *
    * @see Action
    */
   @FXML
   public void doSquare() {
      updateView(() -> mainModel.doSpecialOperation(SpecialOperation.SQUARE));
   }

   /**
    * method of operation one divide by x
    * forming {@code Action} object
    * with provided operations for model
    * which returns Answer from model
    * and sends it to method {@link #updateView(Action)}
    *
    * @see Action
    */
   @FXML
   public void doOneDivX() {
      updateView(() -> mainModel.doSpecialOperation(SpecialOperation.ONEDIVX));
   }

   /**
    * method of operation root
    * forming {@code Action} object
    * with provided operations for model
    * which returns Answer from model
    * and sends it to method {@link #updateView(Action)}
    *
    * @see Action
    */
   @FXML
   public void doRoot() {
      updateView(() -> mainModel.doSpecialOperation(SpecialOperation.ROOT));
   }

   /**
    * method of equals calling
    * forming {@code Action} object
    * with provided operations for model
    * which returns Answer from model
    * and sends it to method {@link #updateView(Action)}
    *
    * @see Action
    */
   @FXML
   public void doEquals() {
      updateView(() -> mainModel.doEquals());
   }

   /**
    * method of adding number to memory
    * forming {@code Action} object
    * with provided operations for model
    * which returns Answer from model
    * and sends it to method {@link #updateView(Action)}
    *
    * @see Action
    */
   @FXML
   public void doAddMemory() {
      updateView(() -> mainModel.addMemory());
   }

   /**
    * method of removing number from memory
    * forming {@code Action} object
    * with provided operations for model
    * which returns Answer from model
    * and sends it to method {@link #updateView(Action)}
    *
    * @see Action
    */
   @FXML
   public void doRemoveMemory() {
      updateView(() -> mainModel.removeMemory());
   }

   /**
    * method of clearing memory
    * forming {@code Action} object
    * with provided operations for model
    * which returns Answer from model
    * and sends it to method {@link #updateView(Action)}
    *
    * @see Action
    */
   @FXML
   public void doClearMemory() {
      updateView(() -> mainModel.clearMemory());
   }

   /**
    * method of loading memory number
    * forming {@code Action} object
    * with provided operations for model
    * which returns Answer from model
    * and sends it to method {@link #updateView(Action)}
    *
    * @see Action
    */
   @FXML
   public void loadFromMemory() {
      updateView(() -> mainModel.loadFromMemory());
   }

   /**
    * method which processes an {@code AnswerDTO}
    * by parsers. Than sends a string values to view
    *
    * @see Action
    * @see AnswerDTO
    * @see OutputNumberParser
    * @see HistoryLineParser
    * @see LastSpecialOperationStoryParser
    */
   private void updateView(Action action) {
      AnswerDTO answerDTO;
      try {
         answerDTO = action.doAction();
      } catch (CalculatorException e) {
         updateError(e);
         return;
      }
      if (answerDTO != null) {

         String outputNumber;
         OutputNumberDTO outputNumberDTO = answerDTO.getOutputNumberDTO();
         if (outputNumberDTO.getClass().equals(EnteredNumberDTO.class)) {
            outputNumber = outputNumberParser.formatInput((EnteredNumberDTO) outputNumberDTO);
         } else {
            outputNumber = outputNumberParser.formatResult(outputNumberDTO.getValue(), true);
         }

         inputFieldNumber.setText(outputNumber);

         String historyLineText = historyLineParser.parse(answerDTO.getHistoryLineDTO());
         historyLineText += lastSpecialOperationStoryParser.parse(answerDTO.getLastSpecialOperationHistoryDTO());
         historyLine.setText(historyLineText);

         if (answerDTO.getMemoryDTO().isEnable()) {
            String memoryString = outputNumberParser.formatResult(answerDTO.getMemoryDTO().getMemoryNumber(), true);
            memoryText.setText(memoryString);
         } else {
            memoryText.setText("");
         }
         if (!enabled) {
            setDisable(false);
         }
      }
   }

   /**
    * method for proceeding an exception
    *
    * @param e exception, which was thrown by actions
    *          in the model
    */
   private void updateError(CalculatorException e) {
      setDisable(true);
      mainModel = new ModelBrainImpl();
      inputFieldNumber.setText(e.getMessage());

      HistoryLineDTO historyLineDTO = e.getAnswerDTO().getHistoryLineDTO();
      LastSpecialOperationHistoryDTO lastSpecialOperationStory = e.getAnswerDTO().getLastSpecialOperationHistoryDTO();

      String historyLineText = historyLineParser.parse(historyLineDTO);
      historyLineText += lastSpecialOperationStoryParser.parse(lastSpecialOperationStory);
      historyLine.setText(historyLineText);
   }

   /**
    * method for blocking a buttons of operations
    * and memory
    *
    * @param disable if true - buttons will be blocked. False - unblocked
    */
   private void setDisable(boolean disable) {
      enabled = false;
      button_ms.setDisable(disable);
      button_msub.setDisable(disable);
      button_mp.setDisable(disable);
      button_percent.setDisable(disable);
      button_root.setDisable(disable);
      button_square.setDisable(disable);
      button_onedix.setDisable(disable);
      button_divide.setDisable(disable);
      button_multiple.setDisable(disable);
      button_subtract.setDisable(disable);
      button_plus.setDisable(disable);
      button_negate.setDisable(disable);
      button_point.setDisable(disable);
   }
}
