package gribiwe.model;

import gribiwe.model.dto.HistoryInfo;
import gribiwe.model.util.CalculatorMath;
import gribiwe.model.util.SimpleOperation;
import gribiwe.model.util.SpecialOperation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static gribiwe.model.util.SimpleOperation.*;

/**
 * keeping information about operations
 * and numbers at history
 *
 * @author Gribiwe
 */
class History {

   /**
    * list of numbers at history
    */
   private final List<BigDecimal> numbers;

   /**
    * list of simple operations between numbers
    * at history
    *
    * @see SimpleOperation
    */
   private final List<SimpleOperation> operations;

   /**
    * list of lists of special operations of
    * some number
    * operations at index 2 will be accepted to
    * number at index 2 of {@link #numbers}
    */
   private final List<List<SpecialOperation>> specialOperations;

   /**
    * saved number
    */
   private BigDecimal savedResult;

   /**
    * saved operation
    */
   private SimpleOperation savedOperation;

   /**
    * shows is needs to calculate full history
    */
   private boolean needRecalculate = true;

   /**
    * value of saved calculation result
    */
   private BigDecimal cachedResult;

   /**
    * initial of default values
    */
   History() {
      numbers = new ArrayList<>();
      operations = new ArrayList<>();
      specialOperations = new ArrayList<>();
   }

   /**
    * uploads list of special operations to save it
    * at history to last number
    *
    * @param specialOperations list of special operations
    * @see SpecialOperation
    * @see List
    */
   void uploadSpecialOperations(List<SpecialOperation> specialOperations) {
      needRecalculate = true;
      this.specialOperations.set(this.specialOperations.size() - 1, specialOperations);
   }

   /**
    * adds number to history
    *
    * @param number        number to add
    * @see SimpleOperation
    */
   public void add(BigDecimal number) {
      needRecalculate = true;
      numbers.add(number);
      operations.add(null);
      this.specialOperations.add(new ArrayList<>());
   }

   /**
    * adds number with provided operation
    * to history
    *
    * @param number        number to add
    * @param operation     operation to add
    * @param saveOperation if true - saves last operation
    *                      for some requirements
    * @see SimpleOperation
    */
   public void add(BigDecimal number, SimpleOperation operation, boolean saveOperation) {
      needRecalculate = true;
      numbers.add(number);
      operations.add(operation);
      if (saveOperation) {
         savedOperation = operation;
      }
      this.specialOperations.add(new ArrayList<>());

   }

   /**
    * @return saved number
    */
   BigDecimal getSavedResult() {
      return savedResult;
   }

   /**
    * @return saved operation
    * @see SimpleOperation
    */
   SimpleOperation getSavedOperation() {
      return savedOperation;
   }

   /**
    * sets saved number
    *
    * @param savedResult number to save
    */
   void setSavedResult(BigDecimal savedResult) {
      this.savedResult = savedResult;
   }

   /**
    * forms dto for answering from model
    *
    * @return dto of history line
    * @see HistoryInfo
    */
   HistoryInfo getHistoryLineDto() {
      return new HistoryInfo(numbers, operations, specialOperations);
   }

   /**
    * changing the last operation
    *
    * @param operation operation change to
    * @see SimpleOperation
    */
   void changeLastOperation(SimpleOperation operation) {
      operations.set(operations.size() - 1, operation);
      savedOperation = operation;
   }

   /**
    * @return last simple operation in history
    * @see SimpleOperation
    */
   SimpleOperation getLastOperation() {
      if (operations.size() == 0) {
         return null;
      } else {
         return operations.get(operations.size() - 1);
      }
   }

   /**
    * clears the history. All of arrays become empty
    */
   void clearHistory() {
      operations.clear();
      numbers.clear();
      specialOperations.clear();
   }

   /**
    * calculating a numbers with
    * operations in history
    *
    * @return result of calculation
    */
   BigDecimal calculate() {
      BigDecimal calculatingResult;
      if (numbers.size() == 0) {
         calculatingResult =  BigDecimal.ZERO;
      } else if (needRecalculate) {
         calculatingResult = CalculatorMath.calculateSpecialOperations(numbers.get(0), specialOperations.get(0));

         for (int i = 0; i < operations.size() && i < numbers.size() - 1; i++) {
            SimpleOperation currentOperation = operations.get(i);
            if (currentOperation == null) {
               continue;
            }
            BigDecimal number = CalculatorMath.calculateSpecialOperations(numbers.get(i + 1), specialOperations.get(i + 1));

            if (currentOperation == PLUS) {
               calculatingResult = CalculatorMath.plus(calculatingResult, number);
            } else if (currentOperation == SUBTRACT) {
               calculatingResult = CalculatorMath.subtract(calculatingResult, number);
            } else if (currentOperation == MULTIPLY) {
               calculatingResult = CalculatorMath.multiply(calculatingResult, number);
            } else if (currentOperation == DIVIDE) {
               calculatingResult = CalculatorMath.divide(calculatingResult, number);
            }
         }
         needRecalculate = false;
         cachedResult = calculatingResult;
      } else {
         calculatingResult = cachedResult;
      }
      return calculatingResult;
   }
}