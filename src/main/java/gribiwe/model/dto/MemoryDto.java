package gribiwe.model.dto;

import java.math.BigDecimal;

/**
 * DTO of memory values of calculator
 *
 * @author Gribiwe
 */
public class MemoryDto {

   /**
    * number of memory
    */
   private final BigDecimal memoryNumber;

   /**
    * shows is memory activated
    */
   private final boolean enable;

   /**
    * initials of dto
    *
    * @param memoryNumber current number of memory
    * @param enable       is memory activated
    */
   public MemoryDto(BigDecimal memoryNumber, boolean enable) {
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
