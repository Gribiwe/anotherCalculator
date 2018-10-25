package gribiwe.model;

import gribiwe.model.dto.BuildingNumber;
import gribiwe.model.util.BigDecimalZeroComparator;
import gribiwe.model.util.Digit;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Class for building a new number
 *
 * @author Gribiwe
 * @see BuildingNumber
 */
class NumberBuilder {

   /**
    * max length of building number
    */
   private static final int MAX_LENGTH = 16;

   /**
    * current building number
    */
   private BigDecimal number;

   /**
    * shows is current number needs
    * to be negated
    */
   private boolean needNegate;

   /**
    * shows is current number should be
    * pointed
    */
   private boolean needPoint;

   /**
    * length of before point part
    * of number
    */
   private int leftLength;

   /**
    * current scale of number.
    * length of after point number length
    */
   private int currentScale;

   /**
    * first initials of building number
    */
   NumberBuilder() {
      removeAllSymbols();
   }

   /**
    * adds new digit to number
    *
    * @param digit digit to add
    */
   void addDigit(Digit digit) {
      int currentLimit = MAX_LENGTH;

      if (number.intValue() == 0) {
         currentLimit++;
      }

      if (leftLength + currentScale < currentLimit) {
         if (needPoint) {
            addDigitAfterPoint(digit.ordinal());
         } else {
            addDigit(digit.ordinal());
         }
      }
   }

   /**
    * adds point to number
    * for creation decimal number
    */
   void addPoint() {
      needPoint = true;
   }

   /**
    * negates number
    */
   void negate() {
      if (!(isZero() && leftLength + currentScale == 1) || needPoint) {
         needNegate = !needNegate;
      }
   }

   /**
    * removing one digit or point
    */
   void removeSymbol() {
      if (needPoint && currentScale == 0) {
         needPoint = false;
      } else if (currentScale > 0) {
         decreaseScale();
      } else if (leftLength > 1) {
         number = number.divide(BigDecimal.TEN, 0, RoundingMode.DOWN);
         leftLength--;
      } else if (leftLength == 1) {
         makeNumberZero();
         currentScale = 0;
      }

      if (!needPoint && needNegate && isZero() && currentScale == 0) {
         needNegate = false;
      }
   }

   /**
    * starts creation of number from the begin
    */
   void removeAllSymbols() {
      makeNumberZero();
      currentScale = 0;
      leftLength = 1;
      needNegate = false;
      needPoint = false;
   }

   /**
    * shows is current number equals to zero
    *
    * @return true, if number equals to zero
    */
   private boolean isZero() {
      return BigDecimalZeroComparator.isZero(number);
   }

   /**
    * makes number equals to zero
    * with scaling 0
    */
   private void makeNumberZero() {
      number = BigDecimal.ZERO.setScale(0, RoundingMode.DOWN);
   }

   /**
    * adds digit to the pointed number
    *
    * @param digit adds to number
    */
   private void addDigitAfterPoint(int digit) {
      increaseScale();
      double doubleNumberToAdd = digit / Math.pow(10, currentScale);
      BigDecimal numberToAdd = BigDecimal.valueOf(doubleNumberToAdd).setScale(currentScale, RoundingMode.DOWN);
      number = number.add(numberToAdd);
   }

   /**
    * increases scale value of number
    */
   private void increaseScale() {
      number = number.setScale(currentScale + 1, RoundingMode.DOWN);
      currentScale++;
   }

   /**
    * decreases scale value of number
    */
   private void decreaseScale() {
      number = number.setScale(currentScale - 1, RoundingMode.DOWN);
      currentScale--;
   }

   /**
    * adds digit to the non-pointed number
    *
    * @param digit adds to number
    */
   private void addDigit(int digit) {
      if (number.intValue() != 0) {
         leftLength++;
      }
      number = number.multiply(BigDecimal.valueOf(10)).add(BigDecimal.valueOf(digit));
   }

   /**
    * @return constructed number as BigInteger
    */
   BigDecimal getNumber() {
      BigDecimal numberToReturn = number;
      if (needNegate) {
         numberToReturn = numberToReturn.negate();
      }
      return numberToReturn;
   }

   /**
    * @return constructed number
    * with additional information
    */
   BuildingNumber getBuildingNumberDTO() {
      BigDecimal numberToReturn = number;
      if (needNegate) {
         numberToReturn = numberToReturn.negate();
      }
      return new BuildingNumber(numberToReturn, needPoint, needNegate);
   }
}
