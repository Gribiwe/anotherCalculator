package gribiwe.model;

import gribiwe.model.util.MemoryOperation;

import java.math.BigDecimal;

import static gribiwe.model.util.MemoryOperation.ADD;

/**
 * Class for keeping some number in specified place
 *
 * @author Gribiwe
 */
class MemoryImpl {

   /**
    * shows is memory enabled
    */
   private boolean enable;

   /**
    * current memory number
    */
   private BigDecimal number;

   /**
    * initial of default values
    */
   MemoryImpl() {
      number = BigDecimal.ZERO;
   }

   /**
    * @return current memory number
    */
   public BigDecimal getNumber() {
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
      if (operation == ADD) {
         number = number.add(num);
      } else {
         number = number.subtract(num);
      }
      enable = true;
   }

   public boolean isEnable() {
      return enable;
   }

   /**
    * clears memory
    */
   void clear() {
      number = BigDecimal.ZERO;
      enable = false;
   }
}
