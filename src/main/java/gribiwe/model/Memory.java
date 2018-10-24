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

   // TODO: 24.10.2018 remove enable flag done

   /**
    * current memory number
    */
   private BigDecimal number;

   /**
    * initial of default values
    */
   Memory() {
      number = null;
   }

   /**
    * @return current memory number
    */
   public BigDecimal getNumber() {
      if (number == null) {
         number = BigDecimal.ZERO;
      }

      return number;
   }

   /**
    * making operation to current number with new one
    *
    * @param num       number to operate
    * @param operation to process
    * @see MemoryOperation
    */
   void doOperation(BigDecimal num, MemoryOperation operation) {
      if (number == null) {
         number = BigDecimal.ZERO;
      }

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
