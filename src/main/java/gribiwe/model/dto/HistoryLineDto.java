package gribiwe.model.dto;

import gribiwe.model.util.SimpleOperation;
import gribiwe.model.util.SpecialOperation;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO of history of calculations and numbers \
 * of calculator
 *
 * @author Gribiwe
 */
public class HistoryLineDto {
   /**
    * list of numbers at history
    */
   private final List<BigDecimal> numbers;

   /**
    * list of operations in history
    */
   private final List<SimpleOperation> operations;

   /**
    * list of lists for special operations of numbers
    * at history
    */
   private final List<List<SpecialOperation>> specialOperations;

   /**
    * initials of DTO
    *
    * @param numbers           numbers at history
    * @param operations        operations in history
    * @param specialOperations special operations of numbers at history
    */
   public HistoryLineDto(List<BigDecimal> numbers, List<SimpleOperation> operations, List<List<SpecialOperation>> specialOperations) {
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
