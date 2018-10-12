package gribiwe.model.dto;

import gribiwe.model.util.SpecialOperation;

import java.math.BigDecimal;
import java.util.List;

public class LastSpecialOperationHistoryDTO {

   private List<SpecialOperation> operations;
   private BigDecimal number;

   public LastSpecialOperationHistoryDTO(List<SpecialOperation> operations, BigDecimal number) {
      this.operations = operations;
      this.number = number;
   }

   public List<SpecialOperation> getOperations() {
      return operations;
   }

   public BigDecimal getNumber() {
      return number;
   }
}
