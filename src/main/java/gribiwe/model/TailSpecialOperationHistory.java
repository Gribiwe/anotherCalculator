package gribiwe.model;

import gribiwe.model.dto.LastSpecialOperationHistoryDTO;
import gribiwe.model.util.SpecialOperation;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * interface of objects to represent
 * tail of history for special operations in forming
 *
 * @author Gribiwe
 * @see SpecialOperation
 * @see TailSpecialOperationHistoryImpl
 */
public interface TailSpecialOperationHistory {

   /**
    * @return true if processing of new
    * special operations
    */
   boolean isProcessing();

   /**
    * @return start number
    */
   BigDecimal getNumber();

   /**
    * adds special operation to list of special
    * operations of current number
    *
    * @param operation operation to add
    * @see SpecialOperation
    */
   void addOperation(SpecialOperation operation);

   /**
    * initial of first number of new tail
    *
    * @param number number to start
    */
   void initNumber(BigDecimal number);

   /**
    * clears tail - all of operations and number
    */
   void clear();

   /**
    * forms a dto for answering from model
    * @return formed dto
    */
   LastSpecialOperationHistoryDTO getDTO();

   /**
    * calculates a special operations of this tail
    * @return result of calculation
    */
   BigDecimal calculate();

   /**
    * @return list of special operations
    * @see SpecialOperation
    * @see ArrayList
    */
   ArrayList<SpecialOperation> getOperations();
}
