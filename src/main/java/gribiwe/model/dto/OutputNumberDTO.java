package gribiwe.model.dto;

import java.math.BigDecimal;

/**
 * dto of number to show at result
 *
 * @author Gribiwe
 */
public class OutputNumberDTO {

   /**
    * number to show
    */
   private final BigDecimal value;

   /**
    * initials of dto
    *
    * @param value current number to show
    */
   public OutputNumberDTO(BigDecimal value) {
      this.value = value;
   }

   public BigDecimal getValue() {
      return value;
   }
}
