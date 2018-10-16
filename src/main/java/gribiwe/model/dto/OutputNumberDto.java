package gribiwe.model.dto;

import java.math.BigDecimal;

/**
 * dto of number to show at result
 *
 * @author Gribiwe
 */
public class OutputNumberDto {

   /**
    * number to show
    */
   private final BigDecimal value;

   /**
    * initials of dto
    *
    * @param value current number to show
    */
   public OutputNumberDto(BigDecimal value) {
      this.value = value;
   }

   public BigDecimal getValue() {
      return value;
   }
}
