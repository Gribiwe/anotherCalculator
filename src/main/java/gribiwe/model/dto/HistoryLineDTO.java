package gribiwe.model.dto;

import gribiwe.model.util.SimpleOperation;
import gribiwe.model.util.SpecialOperation;

import java.math.BigDecimal;
import java.util.List;

public class HistoryLineDTO {

   private List<BigDecimal> numbers;
   private List<SimpleOperation> operations;
   private List<List<SpecialOperation>> specialOperations;

   public HistoryLineDTO(List<BigDecimal> numbers, List<SimpleOperation> operations, List<List<SpecialOperation>> specialOperations) {
      this.numbers = numbers;
      this.operations = operations;
      this.specialOperations = specialOperations;
   }

   public List<List<SpecialOperation>> getSpecialOperations() {
      return specialOperations;
   }

   public List<BigDecimal> getNumbers() {
      return numbers;
   }

   public List<SimpleOperation> getOperations() {
      return operations;
   }
}
