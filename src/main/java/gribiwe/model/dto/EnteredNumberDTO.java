package gribiwe.model.dto;

import java.math.BigDecimal;

public class EnteredNumberDTO extends OutputNumberDTO {

   private int rightNumbersLength;
   private boolean isPointed;
   private boolean isNegated;

   public EnteredNumberDTO(BigDecimal value, int rightNumbersLength, boolean isPointed, boolean isNegated) {
      super(value);
      this.rightNumbersLength = rightNumbersLength;
      this.isPointed = isPointed;
      this.isNegated = isNegated;
   }

   public int getRightNumbersLength() {
      return rightNumbersLength;
   }

   public boolean isPointed() {
      return isPointed;
   }

   public boolean isNegated() {
      return isNegated;
   }
}
