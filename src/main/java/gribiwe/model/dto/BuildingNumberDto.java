package gribiwe.model.dto;

import java.math.BigDecimal;

/**
 * DTO of building number at calculator
 *
 * @author Gribiwe
 */
public class BuildingNumberDto {

   /**
    * shows is building number needs
    * to be pointed
    */
   private final boolean isPointed;

   /**
    * shows is building number needs
    * to be negated
    */
   private final boolean isNegated;

   /**
    * BigDecimal building number
    */
   private final BigDecimal value;

   /**
    * initials of BuildingNumberDto
    *
    * @param value     current number
    * @param isPointed needs to be pointed
    * @param isNegated needs to be negated
    */
   public BuildingNumberDto(BigDecimal value, boolean isPointed, boolean isNegated) {
      this.value = value;
      this.isPointed = isPointed;
      this.isNegated = isNegated;
   }

   public boolean isPointed() {
      return isPointed;
   }

   public boolean isNegated() {
      return isNegated;
   }

   public BigDecimal getValue() {
      return value;
   }
}
