package gribiwe.model;

import gribiwe.model.dto.BuildingSpecialOperations;
import gribiwe.model.util.CalculatorMath;
import gribiwe.model.util.SpecialOperation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class designed to represent tail of history line
 * with processing special operations
 *
 * @author Gribiwe
 * @see SpecialOperation
 */
class SpecialOperationBuilder {

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
   SpecialOperationBuilder() {
      number = null;
      operations = new ArrayList<>();
      processing = false;
   }

   /**
    * @return true if processing of new
    * special operations
    */
   boolean isProcessing() {
      return processing;
   }

   /**
    * @return start number
    */
   BigDecimal getNumber() {
      return number;
   }

   /**
    * adds special operation to list of special
    * operations of current number
    *
    * @param operation operation to add
    * @see SpecialOperation
    */
   void addOperation(SpecialOperation operation) {
      operations.add(operation);
   }

   /**
    * initial of first number of new tail
    *
    * @param number number to start
    */
   void initNumber(BigDecimal number) {
      this.number = number;
      processing = true;
   }

   /**
    * clears tail - all of operations and number
    */
   void clear() {
      number = null;
      operations = new ArrayList<>();
      processing = false;
   }

   /**
    * forms a dto for answering from model
    *
    * @return formed dto
    */
   BuildingSpecialOperations getFormingSpecialOperationsDto() {
      if (number == null) {
         return null;
      }
      return new BuildingSpecialOperations(operations, number);
   }

   /**
    * calculates a special operations of this tail
    *
    * @return result of calculation
    */
   BigDecimal calculate() {
      return CalculatorMath.calculateSpecialOperations(number, operations);
   }

   /**
    * @return list of special operations
    * @see SpecialOperation
    * @see List
    */
   List<SpecialOperation> getOperations() {
      return operations;
   }
}
