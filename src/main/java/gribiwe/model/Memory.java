package gribiwe.model;

import gribiwe.model.dto.MemoryDTO;

import java.math.BigDecimal;

class Memory {

   private boolean enable;
   private BigDecimal number;

   Memory() {
      number = BigDecimal.ZERO;
   }

   public BigDecimal getNumber() {
      return number;
   }

   void setEnable(boolean enable) {
      this.enable = enable;
   }

   void add(BigDecimal num) {
      number = number.add(num);
   }

   void remove(BigDecimal num) {
      number  = number.subtract(num);
   }

   void clear() {
      number = BigDecimal.ZERO;
      enable = false;
   }

   MemoryDTO getDTO() {
      return new MemoryDTO(number, enable);
   }

}
