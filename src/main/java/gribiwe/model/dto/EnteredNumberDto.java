package gribiwe.model.dto;

import java.math.BigDecimal;

/**
 * DTO of building number at calculator
 * extends OutputNumberDto so all of
 * entered numbers is OutputNumber too
 *
 * @author Gribiwe
 * @see OutputNumberDto
 */
public class EnteredNumberDto extends OutputNumberDto {

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
    * initials of EnteredNumberDto
    *
    * @param value     current number
    * @param isPointed needs to be pointed
    * @param isNegated needs to be negated
    */
   public EnteredNumberDto(BigDecimal value, boolean isPointed, boolean isNegated) {
      super(value);
      this.isPointed = isPointed;
      this.isNegated = isNegated;
   }

   public boolean isPointed() {
      return isPointed;
   }

   public boolean isNegated() {
      return isNegated;
   }
}
