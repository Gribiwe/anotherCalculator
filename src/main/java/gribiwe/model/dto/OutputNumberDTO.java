package gribiwe.model.dto;

import java.math.BigDecimal;

public class OutputNumberDTO {
   private BigDecimal value;

   public OutputNumberDTO(BigDecimal value) {
      this.value = value;
   }

   public BigDecimal getValue() {
      return value;
   }
}
