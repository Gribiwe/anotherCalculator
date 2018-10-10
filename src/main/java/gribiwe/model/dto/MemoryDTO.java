package gribiwe.model.dto;

import java.math.BigDecimal;

public class MemoryDTO {

   private BigDecimal memoryNumber;
   private boolean enable;

   public MemoryDTO(BigDecimal memoryNumber, boolean enable) {
      this.memoryNumber = memoryNumber;
      this.enable = enable;
   }

   public BigDecimal getMemoryNumber() {
      return memoryNumber;
   }

   public boolean isEnable() {
      return enable;
   }
}
