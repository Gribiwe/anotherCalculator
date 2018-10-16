package gribiwe.model;

import gribiwe.model.dto.TailSpecialOperationHistoryDto;
import gribiwe.model.util.CalculatorMath;
import gribiwe.model.util.SpecialOperation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * implementation of interface designed
 * to represent tail of history line
 * with processing special operations
 *
 * @author Gribiwe
 * @see TailSpecialOperationHistory
 * @see SpecialOperation
 */
class TailSpecialOperationHistoryImpl implements TailSpecialOperationHistory {

   /**
    * list of special operations
    *
    * @see SpecialOperation
    * @see ArrayList
    */
   private List<SpecialOperation> operations;

   /**
    * current start number of tail
    */
   private BigDecimal number;

   /**
    * shows is tail forming special operation sequence
    * at the tail of history line
    */
   private boolean processing;

   /**
    * initial of default values
    */
   TailSpecialOperationHistoryImpl() {
      number = null;
      operations = new ArrayList<>();
      processing = false;
   }

   @Override
   public boolean isProcessing() {
      return processing;
   }

   @Override
   public BigDecimal getNumber() {
      return number;
   }

   @Override
   public void addOperation(SpecialOperation operation) {
      operations.add(operation);
   }

   @Override
   public void initNumber(BigDecimal number) {
      this.number = number;
      processing = true;
   }

   @Override
   public void clear() {
      number = null;
      operations = new ArrayList<>();
      processing = false;
   }

   @Override
   public TailSpecialOperationHistoryDto getDTO() {
      if (number == null) return null;
      return new TailSpecialOperationHistoryDto(operations, number);
   }

   @Override
   public BigDecimal calculate() {
      return new CalculatorMath().calculateSpecialOperations(number, operations);
   }

   @Override
   public List<SpecialOperation> getOperations() {
      return operations;
   }
}
