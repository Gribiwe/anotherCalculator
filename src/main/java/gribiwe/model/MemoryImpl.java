package gribiwe.model;

import gribiwe.model.dto.MemoryDTO;
import gribiwe.model.util.MemoryOperation;

import java.math.BigDecimal;

import static gribiwe.model.util.MemoryOperation.ADD;

/**
 * implementation of Memory
 * for keeping some number in specified place
 *
 * @see Memory
 * @author Gribiwe
 */
class MemoryImpl implements Memory {

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

   @Override
   public BigDecimal getNumber() {
      return number;
   }

   @Override
   public void doOperation(BigDecimal num, MemoryOperation operation) {
      if (operation == ADD) {
         number = number.add(num);
      } else {
         number = number.subtract(num);
      }
      enable = true;
   }

   @Override
   public void clear() {
      number = BigDecimal.ZERO;
      enable = false;
   }

   @Override
   public MemoryDTO getDTO() {
      return new MemoryDTO(number, enable);
   }

}
