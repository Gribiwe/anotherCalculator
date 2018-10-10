package gribiwe.model.dto;

import gribiwe.model.util.SimpleOperation;
import gribiwe.model.util.SpecialOperation;

import java.math.BigDecimal;
import java.util.ArrayList;

public class HistoryLineDTO {

   private ArrayList<BigDecimal> numbers;
   private ArrayList<SimpleOperation> operations;
   private ArrayList<ArrayList<SpecialOperation>> specialOperations;

   public HistoryLineDTO(ArrayList<BigDecimal> numbers, ArrayList<SimpleOperation> operations, ArrayList<ArrayList<SpecialOperation>> specialOperations) {
      this.numbers = numbers;
      this.operations = operations;
      this.specialOperations = specialOperations;
   }

   public ArrayList<ArrayList<SpecialOperation>> getSpecialOperations() {
      return specialOperations;
   }

   public ArrayList<BigDecimal> getNumbers() {
      return numbers;
   }

   public ArrayList<SimpleOperation> getOperations() {
      return operations;
   }
}
