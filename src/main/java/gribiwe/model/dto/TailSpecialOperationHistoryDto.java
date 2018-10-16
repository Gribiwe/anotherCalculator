package gribiwe.model.dto;

import gribiwe.model.util.SpecialOperation;

import java.math.BigDecimal;
import java.util.List;

/**
 * dto of processing special operation building
 *
 * @author Gribiwe
 */
public class TailSpecialOperationHistoryDto {

   /**
    * list of current processing special operations
    */
   private final List<SpecialOperation> operations;

   /**
    * number of start of building special operations
    */
   private final BigDecimal number;

   /**
    * initials f dto
    *
    * @param operations current processing special operations
    * @param number     number of start of building
    */
   public TailSpecialOperationHistoryDto(List<SpecialOperation> operations, BigDecimal number) {
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
