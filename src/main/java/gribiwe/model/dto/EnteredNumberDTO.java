package gribiwe.model.dto;

import java.math.BigDecimal;

/**
 * DTO of building number at calculator
 * extends OutputNumberDTO so all of
 * entered numbers is OutputNumber too
 *
 * @author Gribiwe
 * @see OutputNumberDTO
 */
public class EnteredNumberDTO extends OutputNumberDTO {

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
    * initials of EnteredNumberDTO
    *
    * @param value     current number
    * @param isPointed needs to be pointed
    * @param isNegated needs to be negated
    */
   public EnteredNumberDTO(BigDecimal value, boolean isPointed, boolean isNegated) {
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
