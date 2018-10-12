package gribiwe.model;

import gribiwe.model.dto.AnswerDTO;
import gribiwe.model.exception.*;
import gribiwe.model.util.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

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
      buildingNumber = true;
      enteringNumber = new EnteringNumberImpl();
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
      } else if (buildingNumber || resultNumber.getStatus().equals(ResultNumberStatus.EQUALS_RESULT)) {
         doOperationWithBuiltOrEqualsOrMemoryNumber(operation);
      } else if (resultNumber.getStatus().equals(ResultNumberStatus.HISTORY_RESULT)) {
         historyLine.changeLastOperation(operation);
      } else if (resultNumber.getStatus().equals(ResultNumberStatus.LOADED_FROM_MEMORY)) {
         doOperationWithBuiltOrEqualsOrMemoryNumber(operation);
      } else if (resultNumber.getStatus().equals(ResultNumberStatus.BLOCKED_BY_MEMORY)) {
         if (historyLine.getLastOperation() == null) {
            doOperationWithBuiltOrEqualsOrMemoryNumber(operation);
         } else {
            historyLine.changeLastOperation(operation);
         }
      }
      verifyOverflow();
      return formAnswer();
   }

   @Override
   public AnswerDTO doPercent() throws CalculatorException {
      BigDecimal numberOfPercents;
      if (buildingNumber) {
         numberOfPercents = stopBuildingAndGetNumber();
      } else {
         numberOfPercents = resultNumber.getNumber();
      }
      BigDecimal previousNumber;
      previousNumber = historyLine.calculate();
      CalculatorMath calculatorMath = new CalculatorMath();
      BigDecimal result = calculatorMath.percent(previousNumber, numberOfPercents);
      resultNumber.loadAsEqualsResult(result);
      tailSpecialOperationHistory.clear();
      tailSpecialOperationHistory.initNumber(result);
      verifyOverflow();
      return formAnswer();
   }

   @Override
   public AnswerDTO doEquals() throws CalculatorException {
      if (tailSpecialOperationHistory.isProcessing()) {
         doEqualsAfterSpecialOperation();
      } else if (buildingNumber || !resultNumber.getStatus().equals(ResultNumberStatus.EQUALS_RESULT)) {
         doEqualsWithHistoryAndBuiltNumber();
      } else if (resultNumber.getStatus().equals(ResultNumberStatus.EQUALS_RESULT)) {
         doEqualsAfterEquals();
      }
      verifyOverflow();
      return formAnswer();
   }

   @Override
   public AnswerDTO doSpecialOperation(SpecialOperation operation) throws CalculatorException {
      if (tailSpecialOperationHistory.isProcessing()) {
         doNextSpecialOperation(operation);
      } else {
         doFirstSpecialOperation(operation);
      }
      BigDecimal tailSpecialOperationHistoryResult;
      tailSpecialOperationHistoryResult = tailSpecialOperationHistory.calculate();
      resultNumber.loadAsEqualsResult(tailSpecialOperationHistoryResult);
      verifyOverflow();
      return formAnswer();
   }

   @Override
   public AnswerDTO doNegate() throws CalculatorException {
      if (buildingNumber) {
         enteringNumber.negate();
      } else {
         doSpecialOperation(SpecialOperation.NEGATE);
      }
      return formAnswer();
   }

   @Override
   public AnswerDTO addMemory() {
      memory.enable();
      BigDecimal numberToMemory;
      numberToMemory = getNumberFromBuildingOrResultNumber();
      memory.add(numberToMemory);
      stopBuildingAndGetNumber();
      resultNumber.loadAsMemoryNumbersWithBlock(numberToMemory);
      return formAnswer();
   }

   @Override
   public AnswerDTO removeMemory() {
      memory.enable();
      BigDecimal numberToMemory;
      numberToMemory = getNumberFromBuildingOrResultNumber();
      memory.remove(numberToMemory);
      stopBuildingAndGetNumber();
      resultNumber.loadAsMemoryNumbersWithBlock(numberToMemory);
      return formAnswer();
   }

   @Override
   public AnswerDTO loadFromMemory() {
      BigDecimal numberFromMemory;
      numberFromMemory = memory.getNumber();
      stopBuildingAndGetNumber();
      resultNumber.loadAsMemoryNumber(numberFromMemory);
      return formAnswer();
   }

   @Override
   public AnswerDTO clearMemory() {
      memory.clear();
      BigDecimal numberToMakeEquals;
      numberToMakeEquals = getNumberFromBuildingOrResultNumber();
      stopBuildingAndGetNumber();
      resultNumber.loadAsMemoryNumbersWithBlock(numberToMakeEquals);
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
      BigDecimal builtNumber;
      builtNumber = enteringNumber.getNumber();
      enteringNumber = new EnteringNumberImpl();
      return builtNumber;
   }

   /**
    * starts building a new number
    */
   private void startBuildingNumber() {
      if (!buildingNumber) {
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
      BigDecimal numberToHistory;
      if (buildingNumber) {
         numberToHistory = stopBuildingAndGetNumber();
      } else {
         numberToHistory = resultNumber.getNumber();
      }
      verifyZeroDividingByZero(numberToHistory, operation);
      verifyDividingByZero(numberToHistory, operation);
      historyLine.add(numberToHistory, operation, true);
      BigDecimal historyResult;
      historyResult = historyLine.calculate();
      resultNumber.loadAsHistoryResult(historyResult);
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
      historyLine.add(tailSpecialOperationHistory.getNumber(), operation, true);
      List<SpecialOperation> tailSpecialOperations;
      tailSpecialOperations = tailSpecialOperationHistory.getOperations();
      historyLine.uploadSpecialOperations(tailSpecialOperations);
      tailSpecialOperationHistory.clear();
      BigDecimal historyResult;
      historyResult = historyLine.calculate();
      resultNumber.loadAsHistoryResult(historyResult);
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
      historyLine.add(historyLine.getSavedResult(), SimpleOperation.NOTHING, false);
      BigDecimal equalsResult;
      equalsResult = historyLine.calculate();
      historyLine.clearHistory();
      resultNumber.loadAsEqualsResult(equalsResult);
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
      BigDecimal numberToCalculate;
      if (buildingNumber) {
         numberToCalculate = stopBuildingAndGetNumber();
         verifyZeroDividingByZero(numberToCalculate);
         verifyDividingByZero(numberToCalculate);
      } else {
         numberToCalculate = resultNumber.getNumber();
      }

      historyLine.setSavedResult(numberToCalculate);
      historyLine.add(numberToCalculate, SimpleOperation.NOTHING, false);

      BigDecimal equalsResult = historyLine.calculate();
      resultNumber.loadAsEqualsResult(equalsResult);

      historyLine.clearHistory();
   }

   /**
    * saves processing special operations and number
    * to history. Calculates it. Loads it as equals result
    */
   private void doEqualsAfterSpecialOperation() {
      BigDecimal specialOperationsResult;
      specialOperationsResult = tailSpecialOperationHistory.calculate();
      historyLine.add(tailSpecialOperationHistory.getNumber(), SimpleOperation.NOTHING, false);
      historyLine.setSavedResult(specialOperationsResult);

      List<SpecialOperation> tailSpecialOperations;
      tailSpecialOperations = tailSpecialOperationHistory.getOperations();
      historyLine.uploadSpecialOperations(tailSpecialOperations);

      tailSpecialOperationHistory.clear();
      BigDecimal equalsResult;
      equalsResult = historyLine.calculate();
      historyLine.clearHistory();
      resultNumber.loadAsEqualsResult(equalsResult);
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
      BigDecimal numberForSpecialOperations;
      if (buildingNumber) {
         numberForSpecialOperations = stopBuildingAndGetNumber();
      } else {
         numberForSpecialOperations = resultNumber.getNumber();
      }
      verifyRootOfNegatedNumberInFirstSpecialOperation(numberForSpecialOperations, operation);

      tailSpecialOperationHistory.initNumber(numberForSpecialOperations);
      verifyOneDivX(operation);
      tailSpecialOperationHistory.addOperation(operation);
   }

   /**
    * verifies is there is an overflow exception
    *
    * @throws OverflowException if rounded number bigger then 9999999999999999e+9999
    *                           or less then -9999999999999999e+9999
    *                           or less then 1e-9999 when bigger then zero
    */
   private void verifyOverflow() throws OverflowException {
      String pattern = "0.###############E0";
      DecimalFormat myFormatter = new DecimalFormat(pattern);
      String output = myFormatter.format(resultNumber.getNumber());
      long exponent = Long.parseLong(output.substring(output.indexOf("E") + 1));
      if (exponent >= 10000 || exponent <= -10000) {
         throw new OverflowException(formAnswer());
      }
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
      BigDecimal dividend = historyLine.calculate();
      SimpleOperation lastOperation = historyLine.getLastOperation();

      if (lastOperation != null && isZero(dividend) &&
              lastOperation.equals(SimpleOperation.DIVIDE) && isZero(divisor)) {
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
      SimpleOperation lastOperation = historyLine.getLastOperation();

      if (lastOperation != null && lastOperation.equals(SimpleOperation.DIVIDE) && isZero(divisor)) {
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
      if (isZero(tailSpecialOperationHistory.calculate()) && operation.equals(SpecialOperation.ONEDIVX)) {
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
      if (isLessThenZero(tailSpecialOperationHistory.calculate()) && operation.equals(SpecialOperation.ROOT)) {
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
      if (isLessThenZero(number) && operation.equals(SpecialOperation.ROOT)) {
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
      if (buildingNumber) {
         return new AnswerDTO(enteringNumber.getNumberDTO(), historyLine.getHistoryLineDTO(), tailSpecialOperationHistory.getDTO(), memory.getDTO());
      } else {
         return new AnswerDTO(resultNumber.getNumberDTO(), historyLine.getHistoryLineDTO(), tailSpecialOperationHistory.getDTO(), memory.getDTO());
      }
   }
}
