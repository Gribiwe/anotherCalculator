package gribiwe.model;

import gribiwe.model.dto.EnteredNumberDTO;
import gribiwe.model.util.Digit;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EnteringNumberImpl implements EnteringNumber {

   private static final int MAX_LENGTH = 16;

   private BigDecimal number;
   private boolean needNegate;
   private boolean needPoint;
   private int leftLength;

   public EnteringNumberImpl() {
      number = BigDecimal.ZERO.setScale(0, RoundingMode.DOWN);
      leftLength = 1;
      needNegate = false;
      needPoint = false;
   }

   @Override
   public void addDigit(Digit digit) {
      int currentLimit = MAX_LENGTH;

      if (number.intValue() == 0) {
         currentLimit++;
      }

      if (leftLength+number.scale() < currentLimit) {
         if (needPoint) {
            addDigitToPointed(digit.ordinal());
         } else {
            addDigit(digit.ordinal());
         }
      }
   }

   private void addDigitToPointed(int digit) {
      int currentScale = increaseScale();
      number = number.add(BigDecimal.valueOf(digit).divide(BigDecimal.valueOf(Math.pow(10, currentScale)), number.scale(), RoundingMode.DOWN));
   }

   private int increaseScale() {
      number = number.setScale(number.scale()+1, RoundingMode.DOWN);
      return number.scale();
   }

   private void decreaseScale() {
      number = number.setScale(number.scale()-1, RoundingMode.DOWN);
   }

   private void addDigit(int digit) {
      if (number.intValue() != 0) {
         leftLength++;
      }
      number = number.multiply(BigDecimal.valueOf(10)).add(BigDecimal.valueOf(digit));
   }

   @Override
   public void addPoint() {
      needPoint = true;

   }

   @Override
   public void negate() {
      if (!(number.compareTo(BigDecimal.ZERO) == 0 && leftLength+number.scale()  == 1 ) || needPoint) {
         needNegate = !needNegate;
      }
   }

   @Override
   public void removeSymbol() {
      //TODO make simple
      if (needPoint && number.scale() == 0) {
         needPoint = false;
      } else if (number.scale() > 0) {
         decreaseScale();
      } else if (leftLength > 1) {
         number = number.divide(BigDecimal.valueOf(10), 0, RoundingMode.DOWN);
      } else if (leftLength == 1) {
         number = BigDecimal.ZERO.setScale(0, RoundingMode.DOWN);
      }

      if (!needPoint && needNegate && number.compareTo(BigDecimal.ZERO) == 0 && number.scale()  == 0) {
         needNegate = false;
      }
   }

   @Override
   public void removeAllSymbols() {
      number = BigDecimal.ZERO.setScale(0, RoundingMode.DOWN);
      needNegate = false;
      needPoint = false;
      leftLength = 1;
   }

   @Override
   public BigDecimal getNumber() {
      BigDecimal numberToReturn = number;
      if (needNegate) {
         numberToReturn = numberToReturn.negate();
      }
      return numberToReturn;
   }

   @Override
   public EnteredNumberDTO getNumberDTO() {
      BigDecimal numberToReturn = number;
      if (needNegate) {
         numberToReturn = numberToReturn.negate();
      }
      return new EnteredNumberDTO(numberToReturn, needPoint, needNegate);
   }
}
