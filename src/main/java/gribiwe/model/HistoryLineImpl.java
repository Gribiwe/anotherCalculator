package gribiwe.model;

import gribiwe.model.dto.HistoryLineDto;
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
class HistoryLineImpl {

   /**
    * list of numbers at history
    */
   private List<BigDecimal> numbers;

   /**
    * list of simple operations between numbers
    * at history
    *
    * @see SimpleOperation
    */
   private List<SimpleOperation> operations;

   /**
    * list of lists of special operations of
    * some number
    * operations at index 2 will be accepted to
    * number at index 2 of {@link #numbers}
    */
   private List<List<SpecialOperation>> specialOperations;

   /**
    * saved number
    */
   private BigDecimal savedResult;

   /**
    * saved operation
    */
   private SimpleOperation savedOperation;

   /**
    * initial of default values
    */
   HistoryLineImpl() {
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
      this.specialOperations.set(this.specialOperations.size() - 1, specialOperations);
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
    * @see HistoryLineDto
    */
   HistoryLineDto getHistoryLineDto() {
      return new HistoryLineDto(numbers, operations, specialOperations);
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
      if (numbers.size() == 0) {
         return BigDecimal.ZERO;
      }

      BigDecimal calculatingResult = CalculatorMath.calculateSpecialOperations(numbers.get(0), specialOperations.get(0));

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
      return calculatingResult;
   }
}