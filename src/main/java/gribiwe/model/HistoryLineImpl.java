package gribiwe.model;

import gribiwe.model.dto.HistoryLineDTO;
import gribiwe.model.util.CalculatorMath;
import gribiwe.model.util.SimpleOperation;
import gribiwe.model.util.SpecialOperation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static gribiwe.model.util.SimpleOperation.*;

/**
 * implementation of HistoryLine interface
 * for keeping information about operations
 * and numbers at history
 *
 * @author Gribiwe
 * @see HistoryLine
 */
class HistoryLineImpl implements HistoryLine {

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

   @Override
   public void uploadSpecialOperations(List<SpecialOperation> specialOperations) {
      this.specialOperations.set(this.specialOperations.size() - 1, specialOperations);
   }

   @Override
   public void add(BigDecimal number, SimpleOperation operation, boolean saveOperation) {
      numbers.add(number);
      operations.add(operation);
      if (saveOperation) {
         savedOperation = operation;
      }
      this.specialOperations.add(null);

   }

   @Override
   public BigDecimal getSavedResult() {
      return savedResult;
   }

   @Override
   public SimpleOperation getSavedOperation() {
      return savedOperation;
   }

   @Override
   public void setSavedResult(BigDecimal savedResult) {
      this.savedResult = savedResult;
   }

   @Override
   public HistoryLineDTO getHistoryLineDTO() {
      return new HistoryLineDTO(numbers, operations, specialOperations);
   }

   @Override
   public void changeLastOperation(SimpleOperation operation) {
      operations.set(operations.size() - 1, operation);
      savedOperation = operation;
   }

   @Override
   public SimpleOperation getLastOperation() {
      if (operations.size() == 0) {
         return null;
      } else {
         return operations.get(operations.size() - 1);
      }
   }

   @Override
   public void clearHistory() {
      operations.clear();
      numbers.clear();
      specialOperations.clear();
   }

   @Override
   public BigDecimal calculate() {
      if (numbers.size() == 0) {
         return BigDecimal.ZERO;
      }

      CalculatorMath calculatorMath = new CalculatorMath();
      BigDecimal calculatingResult = calculatorMath.calculateSpecialOperations(numbers.get(0), specialOperations.get(0));

      for (int i = 0; i < operations.size() && i < numbers.size() - 1; i++) {
         SimpleOperation currentOperation = operations.get(i);
         if (currentOperation == null) {
            continue;
         }
         BigDecimal number = calculatorMath.calculateSpecialOperations(numbers.get(i + 1), specialOperations.get(i + 1));

         if (currentOperation == PLUS) {
            calculatingResult = calculatorMath.plus(calculatingResult, number);
         } else if (currentOperation == SUBTRACT) {
            calculatingResult = calculatorMath.subtract(calculatingResult, number);
         } else if (currentOperation == MULTIPLY) {
            calculatingResult = calculatorMath.multiply(calculatingResult, number);
         } else if (currentOperation == DIVIDE) {
            calculatingResult = calculatorMath.divide(calculatingResult, number);
         }
      }
      return calculatingResult;
   }
}