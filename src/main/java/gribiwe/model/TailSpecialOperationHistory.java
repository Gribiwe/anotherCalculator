package gribiwe.model;

import gribiwe.model.dto.LastSpecialOperationHistoryDTO;
import gribiwe.model.util.CalculatorMath;
import gribiwe.model.util.SpecialOperation;

import java.math.BigDecimal;
import java.util.ArrayList;

class TailSpecialOperationHistory {
   private ArrayList<SpecialOperation> operations;
   private BigDecimal number;
   private boolean processing;

   boolean isProcessing() {
      return processing;
   }

   BigDecimal getNumber() {
      return number;
   }

   TailSpecialOperationHistory() {
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

   void initNumber(BigDecimal number) {
      this.number = number;
      processing = true;
   }

   void clear() {
      number = null;
      operations = new ArrayList<>();
      processing = false;
   }

   LastSpecialOperationHistoryDTO getDTO() {
      if (number == null) return null;
      return new LastSpecialOperationHistoryDTO(operations, number);
   }

   BigDecimal calculate() {
      BigDecimal result;
      result = new CalculatorMath().calculateSpecialOperations(number, operations);
      return result;
   }

   ArrayList<SpecialOperation> getOperations() {
      return operations;
   }
}
