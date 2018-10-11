package gribiwe.model.dto;

import gribiwe.model.util.SpecialOperation;

import java.math.BigDecimal;
import java.util.ArrayList;

public class LastSpecialOperationHistoryDTO {

   private ArrayList<SpecialOperation> operations;
   private BigDecimal number;

   public LastSpecialOperationHistoryDTO(ArrayList<SpecialOperation> operations, BigDecimal number) {
      this.operations = operations;
      this.number = number;
   }

   public ArrayList<SpecialOperation> getOperations() {
      return operations;
   }

   public BigDecimal getNumber() {
      return number;
   }
}
