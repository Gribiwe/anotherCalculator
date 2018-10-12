package gribiwe.model;

import gribiwe.model.dto.AnswerDTO;
import gribiwe.model.dto.OutputNumberDTO;
import gribiwe.model.exception.CalculatorException;
import gribiwe.model.exception.UncorrectedDataException;
import gribiwe.model.exception.ZeroDivideException;
import gribiwe.model.exception.ZeroDivideZeroException;
import gribiwe.model.util.*;

import java.math.BigDecimal;
import java.util.List;

import static gribiwe.model.util.ResultNumberStatus.*;
import static gribiwe.model.util.SimpleOperation.DIVIDE;
import static gribiwe.model.util.SimpleOperation.NOTHING;
import static gribiwe.model.util.SpecialOperation.*;

/**
 * implementation of interface for model brain
 * for working with calculator
 *
 * @author Gribiwe
 * @see ModelBrain
 */
public class ModelBrainImpl implements ModelBrain {

   /**
    * shows that {@link #enteringNumber} builds a number
    */
   private boolean buildingNumber;

   /**
    * needs for building a new number
    *
    * @see EnteringNumber
    * @see EnteringNumberImpl
    */
   private EnteringNumber enteringNumber;

   /**
    * keeps last non-saved special operation progress
    *
    * @see TailSpecialOperationHistory
    * @see TailSpecialOperationHistoryImpl
    */
   private TailSpecialOperationHistory tailSpecialOperationHistory;

   /**
    * keeps history of numbers and operations
    * calculates it
    *
    * @see HistoryLineImpl
    * @see HistoryLine
    */
   private HistoryLine historyLine;

   /**
    * keeps number saved to memory
    *
    * @see Memory
    * @see MemoryImpl
    */
   private Memory memory;

   /**
    * keeps number to send in answer
    * is {@link #buildingNumber} is false
    * and status of number
    *
    * @see ResultNumber
    * @see ResultNumberImpl
    */
   private ResultNumber resultNumber;

   /**
    * exception message for digit which is null
    */
   private static final String DIGIT_IS_NULL_EXCEPTION_TEXT = "Digit is formAnswer(). I cant make calculations with formAnswer()";

   /**
    * exception message for operation which is null
    */
   private static final String OPERATION_IS_NULL_EXCEPTION_TEXT = "Operation is formAnswer(). I cant make calculations using formAnswer() as operation";

   //TODO exceptions to lower level

   /**
    * creation of new exemplar of this class
    * sets default values
    */
   public ModelBrainImpl() {
      buildingNumber = false;
      enteringNumber = new EnteringNumberImpl();
      tailSpecialOperationHistory = new TailSpecialOperationHistoryImpl();
      historyLine = new HistoryLineImpl();
      memory = new MemoryImpl();
      resultNumber = new ResultNumberImpl();
   }

   @Override
   public AnswerDTO deleteDigit() {
      if (buildingNumber) {
         enteringNumber.removeSymbol();
      }
      return formAnswer();
   }

   @Override
   public AnswerDTO deleteAllDigits() {
      startBuildingNumber();
      enteringNumber.removeAllSymbols();
      return formAnswer();
   }

   @Override
   public AnswerDTO deleteAllDigitsAndHistory() {
      deleteAllDigits();
      tailSpecialOperationHistory = new TailSpecialOperationHistoryImpl();
      historyLine = new HistoryLineImpl();
      return formAnswer();
   }

   @Override
   public AnswerDTO addDigit(Digit digit) throws CalculatorException {
      verifyNull(digit, DIGIT_IS_NULL_EXCEPTION_TEXT);
      startBuildingNumber();
      enteringNumber.addDigit(digit);
      return formAnswer();
   }

   @Override
   public AnswerDTO addPoint() {
      startBuildingNumber();
      enteringNumber.addPoint();
      return formAnswer();
   }

   @Override
   public AnswerDTO doOperation(SimpleOperation operation) throws CalculatorException {
      verifyNull(operation, OPERATION_IS_NULL_EXCEPTION_TEXT);

      if (tailSpecialOperationHistory.isProcessing()) {
         doOperationAfterSpecialOperation(operation);
      } else {
         ResultNumberStatus resultNumberStatus = resultNumber.getStatus();

         if (buildingNumber || resultNumberStatus == EQUALS_RESULT) {
            doOperationWithBuiltOrEqualsOrMemoryNumber(operation);
         } else if (resultNumberStatus == HISTORY_RESULT) {
            historyLine.changeLastOperation(operation);
         } else if (resultNumberStatus == LOADED_FROM_MEMORY) {
            doOperationWithBuiltOrEqualsOrMemoryNumber(operation);
         } else if (resultNumberStatus == BLOCKED_BY_MEMORY) {

            if (historyLine.getLastOperation() == null) {
               doOperationWithBuiltOrEqualsOrMemoryNumber(operation);
            } else {
               historyLine.changeLastOperation(operation);
            }
         }
      }
      return formAnswer();
   }

   @Override
   public AnswerDTO doPercent() {
      BigDecimal result = new CalculatorMath().percent(historyLine.calculate(), getNumberFromBuildingOrResultNumber());
      resultNumber.loadResult(result, EQUALS_RESULT);
      tailSpecialOperationHistory.clear();
      tailSpecialOperationHistory.initNumber(result);
      return formAnswer();
   }

   @Override
   public AnswerDTO doEquals() throws CalculatorException {
      if (tailSpecialOperationHistory.isProcessing()) {
         doEqualsAfterSpecialOperation();
      } else {
         ResultNumberStatus resultNumberStatus = resultNumber.getStatus();
         if (buildingNumber || resultNumberStatus != EQUALS_RESULT) {
            doEqualsWithHistoryAndBuiltNumber();
         } else {
            doEqualsAfterEquals();
         }
      }
      return formAnswer();
   }

   @Override
   public AnswerDTO doSpecialOperation(SpecialOperation operation) throws CalculatorException {
      if (tailSpecialOperationHistory.isProcessing()) {
         doNextSpecialOperation(operation);
      } else {
         doFirstSpecialOperation(operation);
      }
      resultNumber.loadResult(tailSpecialOperationHistory.calculate(), EQUALS_RESULT);
      return formAnswer();
   }

   @Override
   public AnswerDTO doNegate() throws CalculatorException {
      if (buildingNumber) {
         enteringNumber.negate();
      } else {
         doSpecialOperation(NEGATE);
      }
      return formAnswer();
   }

   @Override
   public AnswerDTO addMemory() {// TODO: 12.10.2018 Consider adding ENUM for memory operations
      BigDecimal numberToMemory = getNumberFromBuildingOrResultNumber();
      memory.add(numberToMemory);
      resultNumber.loadResult(numberToMemory, BLOCKED_BY_MEMORY);
      return formAnswer();
   }

   @Override
   public AnswerDTO removeMemory() {
      BigDecimal numberToMemory = getNumberFromBuildingOrResultNumber();
      memory.remove(numberToMemory);
      resultNumber.loadResult(numberToMemory, BLOCKED_BY_MEMORY);
      return formAnswer();
   }

   @Override
   public AnswerDTO loadFromMemory() {
      buildingNumber = false;
      resultNumber.loadResult(memory.getNumber(), LOADED_FROM_MEMORY);
      return formAnswer();
   }

   @Override
   public AnswerDTO clearMemory() {
      memory.clear();
      resultNumber.loadResult(getNumberFromBuildingOrResultNumber(), BLOCKED_BY_MEMORY);
      return formAnswer();
   }

   /**
    * takes current number
    * if number is building, built number will be returned
    * in another case - number from {@link #resultNumber}
    *
    * @return current number for operations
    */
   private BigDecimal getNumberFromBuildingOrResultNumber() {
      if (buildingNumber) {
         return stopBuildingAndGetNumber();
      } else {
         return resultNumber.getNumber();
      }
   }

   /**
    * stops building of new number and returns this
    *
    * @return built number
    */
   private BigDecimal stopBuildingAndGetNumber() {
      buildingNumber = false;
      return enteringNumber.getNumber();
   }

   /**
    * starts building a new number
    */
   private void startBuildingNumber() {
      if (!buildingNumber) {
         enteringNumber = new EnteringNumberImpl();
         tailSpecialOperationHistory.clear();
         buildingNumber = true;
      }
   }

   /**
    * method for processing new simple operation while
    * current number is on building or it's {@link #enteringNumber} with
    * status EQUALS or number from {@link #memory}
    *
    * @param operation new operation to process
    * @throws ZeroDivideZeroException if tries to
    *                                 divide zero by zero
    * @throws ZeroDivideException     if tries to divide any number (not zero) by zero
    */
   private void doOperationWithBuiltOrEqualsOrMemoryNumber(SimpleOperation operation) throws ZeroDivideZeroException, ZeroDivideException {
      BigDecimal numberToHistory = getNumberFromBuildingOrResultNumber();

      verifyZeroDividingByZero(numberToHistory, operation);
      verifyDividingByZero(numberToHistory, operation);

      historyLine.add(numberToHistory, operation, true);
      resultNumber.loadResult(historyLine.calculate(), HISTORY_RESULT);
   }

   /**
    * method for processing a new simple operation
    * while {@link #tailSpecialOperationHistory} is
    * on process. Shortly, it saves number and
    * special operations from tail to history, clears
    * {@link #tailSpecialOperationHistory} and adds new operation
    *
    * @param operation new operation to process
    */
   private void doOperationAfterSpecialOperation(SimpleOperation operation) {
      BigDecimal number = tailSpecialOperationHistory.getNumber();
      List<SpecialOperation> operations = tailSpecialOperationHistory.getOperations();

      historyLine.add(number, operation, true);
      historyLine.uploadSpecialOperations(operations);

      tailSpecialOperationHistory.clear();
      resultNumber.loadResult(historyLine.calculate(), HISTORY_RESULT);
   }

   /**
    * method for calculation equals value, while
    * {@link #enteringNumber} have a status EQUALS.
    * Adds to history last saved result and operation,
    * calculates it, clears history and loads result
    * as new equals result.
    */
   private void doEqualsAfterEquals() {
      historyLine.add(resultNumber.getNumber(), historyLine.getSavedOperation(), false);
      historyLine.add(historyLine.getSavedResult(), NOTHING, false);

      resultNumber.loadResult(historyLine.calculate(), EQUALS_RESULT);
      historyLine.clearHistory();
   }

   /**
    * adds current number to history, calculates history
    * and loads history result as equals number
    *
    * @throws ZeroDivideZeroException if tries to
    *                                 divide zero by zero
    * @throws ZeroDivideException     if tries to divide any number (not zero) by zero
    */
   private void doEqualsWithHistoryAndBuiltNumber() throws ZeroDivideZeroException, ZeroDivideException {
      BigDecimal numberToCalculate = getNumberFromBuildingOrResultNumber();

      verifyZeroDividingByZero(numberToCalculate);
      verifyDividingByZero(numberToCalculate);

      historyLine.setSavedResult(numberToCalculate);
      historyLine.add(numberToCalculate, NOTHING, false);

      resultNumber.loadResult(historyLine.calculate(), EQUALS_RESULT);
      historyLine.clearHistory();
   }

   /**
    * saves processing special operations and number
    * to history. Calculates it. Loads it as equals result
    */
   private void doEqualsAfterSpecialOperation() {
      historyLine.add(tailSpecialOperationHistory.getNumber(), NOTHING, false);
      historyLine.setSavedResult(tailSpecialOperationHistory.calculate());
      historyLine.uploadSpecialOperations(tailSpecialOperationHistory.getOperations());

      tailSpecialOperationHistory.clear();
      resultNumber.loadResult(historyLine.calculate(), EQUALS_RESULT);
      historyLine.clearHistory();
   }

   /**
    * adds a new special operations to processing
    * special operations
    *
    * @param operation new special operation to process
    * @throws ZeroDivideException      if tries to divide any number (not zero) by zero
    * @throws UncorrectedDataException if tries to calculate root with negated value
    */
   private void doNextSpecialOperation(SpecialOperation operation) throws ZeroDivideException, UncorrectedDataException {
      verifyOneDivX(operation);
      verifyRootOfNegatedNumberInNextSpecialOperation(operation);

      tailSpecialOperationHistory.addOperation(operation);
   }

   /**
    * starts new processing of special operations
    *
    * @param operation first special operation to
    *                  start new processing
    * @throws ZeroDivideException      if tries to divide any number (not zero) by zero
    * @throws UncorrectedDataException if tries to calculate root with negated value
    */
   private void doFirstSpecialOperation(SpecialOperation operation) throws UncorrectedDataException, ZeroDivideException {
      BigDecimal numberForSpecialOperations = getNumberFromBuildingOrResultNumber();

      verifyRootOfNegatedNumberInFirstSpecialOperation(numberForSpecialOperations, operation);
      tailSpecialOperationHistory.initNumber(numberForSpecialOperations);

      verifyOneDivX(operation);
      tailSpecialOperationHistory.addOperation(operation);
   }

   /**
    * verifies is current history will be
    * divided by zero when history is zero too
    *
    * @param divisor number to divide by
    * @throws ZeroDivideZeroException if tries to
    *                                 divide zero by zero
    */
   private void verifyZeroDividingByZero(BigDecimal divisor) throws ZeroDivideZeroException {
      verifyZeroDividingByZero(divisor, null);
   }

   /**
    * verifies is current history will be
    * divided by zero
    *
    * @param divisor number to divide by
    * @throws ZeroDivideException if tries to
    *                             divide any number
    *                             (not zero) by zero
    */
   private void verifyDividingByZero(BigDecimal divisor) throws ZeroDivideException {
      verifyDividingByZero(divisor, null);
   }

   /**
    * verifies is current history with new operation will be
    * divided by zero when history is zero too
    *
    * @param divisor   number to divide by
    * @param operation operation to add
    * @throws ZeroDivideZeroException if tries to
    *                                 divide zero by zero
    */
   private void verifyZeroDividingByZero(BigDecimal divisor, SimpleOperation operation) throws ZeroDivideZeroException {
      SimpleOperation lastOperation = historyLine.getLastOperation();
      if (lastOperation != null && isZero(historyLine.calculate()) && // TODO: 12.10.2018 Make If() simple
              lastOperation == DIVIDE && isZero(divisor)) {
         if (operation != null) {
            historyLine.add(divisor, operation, false);
         }

         throw new ZeroDivideZeroException(formAnswer());
      }
   }

   /**
    * verifies is current history with new
    * operation will be divided by zero
    *
    * @param divisor   number to divide by
    * @param operation operation to add
    * @throws ZeroDivideException if tries to
    *                             divide any number
    *                             (not zero) by zero
    */
   private void verifyDividingByZero(BigDecimal divisor, SimpleOperation operation) throws ZeroDivideException {
      if (historyLine.getLastOperation() == DIVIDE && isZero(divisor)) {
         if (operation != null) {
            historyLine.add(divisor, operation, false);
         }

         throw new ZeroDivideException(formAnswer());
      }
   }

   /**
    * verifies is added object is null
    *
    * @param object  object to check
    * @param message exception message
    * @throws CalculatorException if object is null
    */
   private void verifyNull(Object object, String message) throws CalculatorException {
      if (object == formAnswer()) {
         throw new CalculatorException(message, formAnswer());
      }
   }

   /**
    * verifies is special operation
    * "one divide by x" will divide by zero
    *
    * @param operation special operation to process
    * @throws ZeroDivideException if tries to divide by zero
    */
   private void verifyOneDivX(SpecialOperation operation) throws ZeroDivideException {
      if (isZero(tailSpecialOperationHistory.calculate()) && operation == ONE_DIV_X) {
         tailSpecialOperationHistory.addOperation(operation);
         throw new ZeroDivideException(formAnswer());
      }
   }

   /**
    * verifies is there trying to find root value
    * of negated number while processing
    * special operations tail
    *
    * @param operation operation to process
    * @throws UncorrectedDataException if tries to root negate number
    */
   private void verifyRootOfNegatedNumberInNextSpecialOperation(SpecialOperation operation) throws UncorrectedDataException {
      if (isLessThenZero(tailSpecialOperationHistory.calculate()) && operation == ROOT) {
         tailSpecialOperationHistory.addOperation(operation);
         throw new UncorrectedDataException(formAnswer());
      }
   }

   /**
    * verifies is there trying to find root value
    * of negated number before starting processing
    * a special operations tail
    *
    * @param operation operation to process
    * @throws UncorrectedDataException if tries to root negate number
    */
   private void verifyRootOfNegatedNumberInFirstSpecialOperation(BigDecimal number, SpecialOperation operation) throws UncorrectedDataException {
      if (isLessThenZero(number) && operation == ROOT) {
         tailSpecialOperationHistory.initNumber(number);
         tailSpecialOperationHistory.addOperation(operation);

         throw new UncorrectedDataException(formAnswer());
      }
   }

   /**
    * checks number is zero
    *
    * @param number number to check
    * @return true if number equals to zero
    */
   private boolean isZero(BigDecimal number) {
      return number.compareTo(BigDecimal.ZERO) == 0;
   }

   /**
    * checks is number less then zero
    *
    * @param number number to check
    * @return true if number less to zero
    */
   private boolean isLessThenZero(BigDecimal number) {
      return number.compareTo(BigDecimal.ZERO) < 0;
   }

   /**
    * forms AnswerDTO answer with information of current history,
    * result number, memory or history tail
    *
    * @return formed answerDTO with needed components
    */
   private AnswerDTO formAnswer() {
      OutputNumberDTO outputNumberDTO;
      if (buildingNumber) {
         outputNumberDTO = enteringNumber.getNumberDTO();
      } else {
         outputNumberDTO = resultNumber.getNumberDTO();
      }

      return new AnswerDTO(outputNumberDTO, historyLine.getHistoryLineDTO(), tailSpecialOperationHistory.getDTO(), memory.getDTO());
   }
}
