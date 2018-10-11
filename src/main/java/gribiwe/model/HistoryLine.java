package gribiwe.model;

import gribiwe.model.dto.HistoryLineDTO;
import gribiwe.model.util.CalculatorMath;
import gribiwe.model.util.SimpleOperation;
import gribiwe.model.util.SpecialOperation;

import java.math.BigDecimal;
import java.util.ArrayList;

class HistoryLine {

   private ArrayList<BigDecimal> numbers;
   private ArrayList<SimpleOperation> operations;
   private ArrayList<ArrayList<SpecialOperation>> specialOperations;

   private BigDecimal savedResult;
   private SimpleOperation savedOperation;

   HistoryLine() {
      numbers = new ArrayList<>();
      operations = new ArrayList<>();
      specialOperations = new ArrayList<>();
   }

   void uploadSpecialOperations(ArrayList<SpecialOperation> specialOperations) {
      this.specialOperations.set(this.specialOperations.size() - 1, specialOperations);
   }

   void add(BigDecimal number, SimpleOperation operation, boolean save) {
      numbers.add(number);
      operations.add(operation);
      if (save) {
         savedOperation = operation;
      }
      this.specialOperations.add(null);

   }

   BigDecimal getSavedResult() {
      return savedResult;
   }

   SimpleOperation getSavedOperation() {
      return savedOperation;
   }

   void setSavedResult(BigDecimal savedResult) {
      this.savedResult = savedResult;
   }

   HistoryLineDTO getHistoryLineDTO() {
      return new HistoryLineDTO(numbers, operations, specialOperations);
   }

   void changeLastOperation(SimpleOperation operation) {
      operations.set(operations.size() - 1, operation);
      savedOperation = operation;
   }

   SimpleOperation getLastOperation() {
      if (operations.size() == 0) {
         return null;
      } else {
         return operations.get(operations.size()-1);
      }
   }


   void clearHistory() {
      operations = new ArrayList<>();
      numbers = new ArrayList<>();
      specialOperations = new ArrayList<>();
   }

   BigDecimal calculate() {
      CalculatorMath calculatorMath = new CalculatorMath();
      BigDecimal calculatingResult;

      if (numbers.size() == 0) {
         return BigDecimal.ZERO;
      }

      calculatingResult = numbers.get(0);

      BigDecimal number;

      if (specialOperations.get(0) != null) {
         calculatingResult = calculatorMath.calculateSpecialOperations(numbers.get(0), specialOperations.get(0));
      }

      for (int i = 0; i < operations.size() && i < numbers.size() - 1; i++) {
         number = numbers.get(i + 1);
         if (specialOperations.get(i + 1) != null) {
            number = calculatorMath.calculateSpecialOperations(numbers.get(i + 1), specialOperations.get(i + 1));
         }
         if (operations.get(i) == null) {
            continue;
         }
         if (operations.get(i).equals(SimpleOperation.PLUS)) {
            calculatingResult = calculatorMath.plus(calculatingResult, number);
         } else if (operations.get(i).equals(SimpleOperation.SUBTRACT)) {
            calculatingResult = calculatorMath.subtract(calculatingResult, number);
         } else if (operations.get(i).equals(SimpleOperation.MULTIPLY)) {
            calculatingResult = calculatorMath.multiply(calculatingResult, number);
         } else if (operations.get(i).equals(SimpleOperation.DIVIDE)) {
            calculatingResult = calculatorMath.divide(calculatingResult, number);
         }
      }

      return calculatingResult;
   }

}