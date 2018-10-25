package gribiwe.model;

import gribiwe.model.util.MemoryOperation;

import java.math.BigDecimal;

import static gribiwe.model.util.MemoryOperation.ADD;

/**
 * Class for keeping some number in specified place
 *
 * @author Gribiwe
 */
class Memory {

   /**
    * current memory number
    */
   private BigDecimal number;

   /**
    * @return current memory number
    */
   public BigDecimal getNumber() {
      checkAndUpdateNullNumberToZero();
      return number;
   }

   /**
    * method checks is number is null
    * if it's null, sets number to BigDecimal.ZERO value
    */
   private void checkAndUpdateNullNumberToZero() {
      if (number == null) {
         number = BigDecimal.ZERO;
      }
   }

   /**
    * making operation to current number with new one
    *
    * @param num       number to operate
    * @param operation to process
    * @see MemoryOperation
    */
   void doOperation(BigDecimal num, MemoryOperation operation) {
      checkAndUpdateNullNumberToZero();
      if (operation == ADD) {
         number = number.add(num);
      } else {
         number = number.subtract(num);
      }
   }

   boolean isEnable() {
      return number != null;
   }

   /**
    * clears memory
    */
   void clear() {
      number = null;
   }
}
