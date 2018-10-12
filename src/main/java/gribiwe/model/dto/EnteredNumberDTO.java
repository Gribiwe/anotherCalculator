package gribiwe.model.dto;

import java.math.BigDecimal;

public class EnteredNumberDTO extends OutputNumberDTO {

   private boolean isPointed;
   private boolean isNegated;

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
