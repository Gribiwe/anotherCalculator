package gribiwe.model;

import gribiwe.model.dto.LastSpecialOperationHistoryDTO;
import gribiwe.model.util.CalculatorMath;
import gribiwe.model.util.SpecialOperation;

import java.math.BigDecimal;
import java.util.ArrayList;

class LastSpecialOperationHistory {
   private ArrayList<SpecialOperation> operations;
   private BigDecimal number;
   private BigDecimal prevNumber;
   private boolean processing;

   boolean isProcessing() {
      return processing;
   }

   BigDecimal getNumber() {
      return number;
   }

   LastSpecialOperationHistory() {
      prevNumber = null;
      number = null;
      operations = new ArrayList<>();
      processing = false;
   }

   void addOperation(SpecialOperation operation) {
      operations.add(operation);
   }

   void clearOperations() {
      operations = new ArrayList<>();
   }

   void initNumber(BigDecimal prevNumber, BigDecimal number) {
      this.prevNumber = prevNumber;
      this.number = number;
      processing = true;
   }

   void clear() {
      number = null;
      operations = new ArrayList<>();
      processing = false;
      prevNumber = null;
   }

   LastSpecialOperationHistoryDTO getDTO() {
      if (number == null) return null;
      return new LastSpecialOperationHistoryDTO(operations, number);
   }

   BigDecimal calculate() {
      number = new CalculatorMath().calculateSpecialOperations(prevNumber, number, operations);
      return number;
   }

   ArrayList<SpecialOperation> getOperations() {
      return operations;
   }
}
