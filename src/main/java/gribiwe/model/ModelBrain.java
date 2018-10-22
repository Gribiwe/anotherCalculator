package gribiwe.model;

import gribiwe.model.dto.BuildingNumber;
import gribiwe.model.dto.BuildingSpecialOperations;
import gribiwe.model.dto.HistoryInfo;
import gribiwe.model.exception.*;
import gribiwe.model.util.*;

import java.math.BigDecimal;
import java.util.List;

import static gribiwe.model.util.ResultNumberStatus.*;
import static gribiwe.model.util.SimpleOperation.DIVIDE;
import static gribiwe.model.util.SpecialOperation.*;

/**
 * Class for working with calculator from controller
 *
 * @author Gribiwe
 */
public class ModelBrain {

   /**
    * shows that {@link #numberBuilder} builds a number
    */
   private boolean buildingNumber;

   /**
    * needs for building a new number
    *
    * @see NumberBuilder
    */
   private final NumberBuilder numberBuilder;

   /**
    * keeps last non-saved special operation progress
    *
    * @see SpecialOperationBuilder
    */
   private SpecialOperationBuilder tailSpecialOperationHistory;

   /**
    * keeps history of numbers and operations
    * calculates it
    *
    * @see History
    */
   private History history;

   /**
    * keeps number saved to memory
    *
    * @see Memory
    */
   private final Memory memory;

   /**
    * keeps number to send in answer
    * is {@link #buildingNumber} is false
    * and status of number
    *
    * @see ResultNumber
    */
   private final ResultNumber resultNumber;

   /**
    * exception message for digit which is null
    */
   private static final String DIGIT_IS_NULL_EXCEPTION_TEXT = "Digit is null. I cant make calculations with null";

   /**
    * exception message for operation which is null
    */
   private static final String OPERATION_IS_NULL_EXCEPTION_TEXT = "Operation is null. I cant make calculations using null as operation";

   /**
    * creation of new exemplar of this class
    * sets default values
    */
   public ModelBrain() {
      buildingNumber = false;
      this.numberBuilder = new NumberBuilder();
      tailSpecialOperationHistory = new SpecialOperationBuilder();
      history = new History();
      memory = new Memory();
      resultNumber = new ResultNumber();
   }

   /**
    * method for removing one digit or point from input
    */
   public BigDecimal deleteDigit() {
      if (buildingNumber) {
         numberBuilder.removeSymbol();
      }
      return resultNumber.getNumber();
   }

   /**
    * method for clearing number line
    */
   public BigDecimal deleteAllDigits() {
      if (buildingNumber) {
         numberBuilder.removeAllSymbols();
      } else {
         startBuildingNumber();
      }
      return resultNumber.getNumber();
   }

   /**
    * method for deletion all numbers at result field
    * and clearing history line
    */
   public BigDecimal deleteAllDigitsAndHistory() {
      deleteAllDigits();
      tailSpecialOperationHistory = new SpecialOperationBuilder();
      history = new History();
      return resultNumber.getNumber();
   }

   /**
    * method for addition new digit to input
    *
    * @throws NullPointerException if digit is null
    */
   public BigDecimal addDigit(Digit digit) {
      verifyNull(digit, DIGIT_IS_NULL_EXCEPTION_TEXT);
      startBuildingNumber();
      numberBuilder.addDigit(digit);
      return resultNumber.getNumber();
   }

   /**
    * method for adding point to input number
    */
   public BigDecimal addPoint() {
      startBuildingNumber();
      numberBuilder.addPoint();
      return resultNumber.getNumber();
   }

   /**
    * method for adding new operation to history
    *
    * @throws ZeroDivideException     if was trying to divide number (not zero) by zero
    * @throws ZeroDivideZeroException if was trying to divide zero by zero
    * @throws NullPointerException    if operation is null
    * @throws OverflowException       if result value is overflow
    */
   public BigDecimal doOperation(SimpleOperation operation) throws ZeroDivideZeroException, ZeroDivideException, OverflowException {
      verifyNull(operation, OPERATION_IS_NULL_EXCEPTION_TEXT);

      if (tailSpecialOperationHistory.isProcessing()) {
         doOperationAfterSpecialOperation(operation);
      } else {
         ResultNumberStatus resultNumberStatus = resultNumber.getStatus();

         if (buildingNumber || resultNumberStatus == EQUALS_RESULT) {
            doOperationWithBuiltOrEqualsOrMemoryNumber(operation);
         } else if (resultNumberStatus == HISTORY_RESULT) {
            history.changeLastOperation(operation);
         } else if (resultNumberStatus == LOADED_FROM_MEMORY) {
            doOperationWithBuiltOrEqualsOrMemoryNumber(operation);
         } else if (resultNumberStatus == BLOCKED_BY_MEMORY) {

            if (history.getLastOperation() == null) {
               doOperationWithBuiltOrEqualsOrMemoryNumber(operation);
            } else {
               history.changeLastOperation(operation);
            }
         }
      }
      verifyOverflow();
      return resultNumber.getNumber();
   }

   /**
    * method for adding operation percent
    *
    * @throws OverflowException if result value is overflow
    */
   public BigDecimal doPercent() throws OverflowException {
      BigDecimal result = CalculatorMath.percent(history.calculate(), getNumberFromBuildingOrResultNumber());
      resultNumber.loadResult(result, EQUALS_RESULT);
      tailSpecialOperationHistory.clear();
      tailSpecialOperationHistory.initNumber(result);
      verifyOverflow();
      return resultNumber.getNumber();
   }

   /**
    * method for calculation an equals result of calculator
    *
    * @throws ZeroDivideException     if was trying to divide number (not zero) by zero
    * @throws ZeroDivideZeroException if was trying to divide zero by zero
    * @throws OverflowException       if result value is overflow
    */
   public BigDecimal doEquals() throws ZeroDivideZeroException, ZeroDivideException, OverflowException {
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
      verifyOverflow();
      return resultNumber.getNumber();
   }

   /**
    * method for addition new special operation
    *
    * @throws ZeroDivideException      if was trying to divide number (not zero) by zero
    * @throws NullPointerException     if operation is null
    * @throws UncorrectedDataException if was trying to find root of negated value
    */
   public BigDecimal doSpecialOperation(SpecialOperation operation) throws UncorrectedDataException, ZeroDivideException, OverflowException {
      verifyNull(operation, OPERATION_IS_NULL_EXCEPTION_TEXT);
      if (tailSpecialOperationHistory.isProcessing()) {
         doNextSpecialOperation(operation);
      } else {
         doFirstSpecialOperation(operation);
      }
      resultNumber.loadResult(tailSpecialOperationHistory.calculate(), EQUALS_RESULT);
      verifyOverflow();
      return resultNumber.getNumber();
   }

   /**
    * method negating number
    */
   public BigDecimal doNegate() {
      if (buildingNumber) {
         numberBuilder.negate();
      } else {
         try {
            doSpecialOperation(NEGATE);
         } catch (UncorrectedDataException | OverflowException | ZeroDivideException e) {
            throw new NegateException("unexpected exception in model on working with negate as with special operation", e);
         }
      }
      return resultNumber.getNumber();
   }

   /**
    * method for adding or subtracting memory number
    *
    * @throws NullPointerException if operation is null
    */
   public BigDecimal operateMemory(MemoryOperation operation) throws NullPointerException {
      verifyNull(operation, OPERATION_IS_NULL_EXCEPTION_TEXT);
      BigDecimal numberToOperate = getNumberFromBuildingOrResultNumber();
      memory.doOperation(numberToOperate, operation);
      resultNumber.loadResult(numberToOperate, BLOCKED_BY_MEMORY);
      return resultNumber.getNumber();
   }

   /**
    * method for loading memory number from memory
    * to result line
    *
    * @throws OverflowException if result value is overflow
    */
   public BigDecimal loadFromMemory() throws OverflowException {
      buildingNumber = false;
      resultNumber.loadResult(memory.getNumber(), LOADED_FROM_MEMORY);
      verifyOverflow();
      return resultNumber.getNumber();
   }

   /**
    * method for cleaning memory
    */
   public BigDecimal clearMemory() {
      memory.clear();
      resultNumber.loadResult(getNumberFromBuildingOrResultNumber(), BLOCKED_BY_MEMORY);
      return resultNumber.getNumber();
   }

   /**
    * clears model all values
    */
   public void clearModel() {
      history.clearHistory();
      numberBuilder.removeAllSymbols();
      memory.clear();
      tailSpecialOperationHistory.clear();
      resultNumber.clear();
      buildingNumber = false;
   }

   /**
    * takes current number
    * if number is building, built number will be returned
    * in another case - number from {@link #resultNumber}
    *
    * @return current number for operations
    */
   private BigDecimal getNumberFromBuildingOrResultNumber() {
      BigDecimal number;
      if (buildingNumber) {
         buildingNumber = false;
         number = numberBuilder.getNumber();
      } else {
         number = resultNumber.getNumber();
      }
      return number;
   }

   /**
    * starts building a new number
    */
   private void startBuildingNumber() {
      if (!buildingNumber) {
         numberBuilder.removeAllSymbols();
         tailSpecialOperationHistory.clear();
         buildingNumber = true;
      }
   }

   /**
    * method for processing new simple operation while
    * current number is on building or it's {@link #numberBuilder} with
    * status EQUALS or number from {@link #memory}
    *
    * @param operation new operation to process
    * @throws ZeroDivideZeroException if tries to divide zero by zero
    * @throws ZeroDivideException     if tries to divide any number (not zero) by zero
    */
   private void doOperationWithBuiltOrEqualsOrMemoryNumber(SimpleOperation operation) throws ZeroDivideZeroException, ZeroDivideException {
      BigDecimal numberToHistory = getNumberFromBuildingOrResultNumber();

      verifyZeroDividingByZero(numberToHistory, operation);
      verifyDividingByZero(numberToHistory, operation);

      history.add(numberToHistory, operation, true);
      resultNumber.loadResult(history.calculate(), HISTORY_RESULT);
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

      history.add(number, operation, true);
      history.uploadSpecialOperations(operations);

      tailSpecialOperationHistory.clear();
      resultNumber.loadResult(history.calculate(), HISTORY_RESULT);
   }

   /**
    * method for calculation equals value, while
    * {@link #numberBuilder} have a status EQUALS.
    * Adds to history last saved result and operation,
    * calculates it, clears history and loads result
    * as new equals result.
    */
   private void doEqualsAfterEquals() {
      history.add(resultNumber.getNumber(), history.getSavedOperation(), false);
      history.add(history.getSavedResult());

      resultNumber.loadResult(history.calculate(), EQUALS_RESULT);
      history.clearHistory();
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

      history.setSavedResult(numberToCalculate);
      history.add(numberToCalculate);

      resultNumber.loadResult(history.calculate(), EQUALS_RESULT);
      history.clearHistory();
   }

   /**
    * saves processing special operations and number
    * to history. Calculates it. Loads it as equals result
    */
   private void doEqualsAfterSpecialOperation() {
      history.add(tailSpecialOperationHistory.getNumber());
      history.setSavedResult(tailSpecialOperationHistory.calculate());
      history.uploadSpecialOperations(tailSpecialOperationHistory.getOperations());

      tailSpecialOperationHistory.clear();
      resultNumber.loadResult(history.calculate(), EQUALS_RESULT);
      history.clearHistory();
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
    * BigDecimal value of the most little number
    * of the numbers which calls overflow exception
    * because of value is too big
    */
   private static final BigDecimal MAX_BORDER_VALUE = new BigDecimal("99999999999999995E+9983");

   /**
    * the closest possible number to zero
    */
   private static final BigDecimal CLOSEST_TO_ZERO_VALUE = new BigDecimal("1.0E-9999");

   /**
    * verifies is there is an overflow exception
    */
   private void verifyOverflow() throws OverflowException {
      BigDecimal value = resultNumber.getNumber().abs();
      if (value.compareTo(MAX_BORDER_VALUE) >= 0 || value.compareTo(CLOSEST_TO_ZERO_VALUE) < 0 && !isZero(value)) {
         throw new OverflowException("Value is overflow: " + value);
      }
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
      SimpleOperation lastOperation = history.getLastOperation();
      if (lastOperation == DIVIDE && isZero(history.calculate()) && isZero(divisor)) {
         if (operation != null) {
            history.add(divisor, operation, false);
         }

         throw new ZeroDivideZeroException("Cant divide zero by zero! Uncertain is answer");
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
      if (history.getLastOperation() == DIVIDE && isZero(divisor)) {
         if (operation != null) {
            history.add(divisor, operation, false);
         }

         throw new ZeroDivideException("Divisor is zero! Can't divide by zero");
      }
   }


   /**
    * verifies is object is null
    *
    * @param object  object to check
    * @param message message of exception
    * @throws NullPointerException if object is null
    */
   private void verifyNull(Object object, String message) {
      if (object == null) {
         throw new NullPointerException(message);
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
      BigDecimal tailCalculationResult = tailSpecialOperationHistory.calculate();
      if (isZero(tailCalculationResult) && operation == ONE_DIV_X) {
         tailSpecialOperationHistory.addOperation(operation);
         throw new ZeroDivideException("Cant divide one by zero!");
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
      BigDecimal tailCalculationResult = tailSpecialOperationHistory.calculate();
      if (isLessThenZero(tailCalculationResult) && operation == ROOT) {
         tailSpecialOperationHistory.addOperation(operation);
         throw new UncorrectedDataException("You tried to find root of negated value. Not possible to calculate it.");
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

         throw new UncorrectedDataException("You tried to find root of negated value. Not possible to calculate it.");
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

   public boolean isBuildingNumber() {
      return buildingNumber;
   }

   public boolean isMemoryActive() {
      return memory.isEnable();
   }

   public BigDecimal getMemoryNumber() {
      return memory.getNumber();
   }

   public BuildingNumber getBuildingNumber() {
      return numberBuilder.getBuildingNumberDTO();
   }

   public HistoryInfo getHistoryLineDto() {
     return history.getHistoryLineDto();
   }

   public boolean isFormingSpecialOperation() {
      return tailSpecialOperationHistory.isProcessing();
   }

   public BuildingSpecialOperations getFormingSpecialOperationsDto() {
      return tailSpecialOperationHistory.getFormingSpecialOperationsDto();
   }
}
