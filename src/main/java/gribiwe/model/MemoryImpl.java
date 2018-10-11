package gribiwe.model;

import gribiwe.model.dto.MemoryDTO;

import java.math.BigDecimal;

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
   public void enable() {
      this.enable = true;
   }

   @Override
   public void add(BigDecimal num) {
      number = number.add(num);
   }

   @Override
   public void remove(BigDecimal num) {
      number  = number.subtract(num);
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
