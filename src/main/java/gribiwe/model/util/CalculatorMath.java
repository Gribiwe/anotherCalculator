package gribiwe.model.util;

import gribiwe.model.dto.OutputNumberDTO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * class for calculation
 *
 * @author Gribiwe
 */
public class CalculatorMath {

   /**
    * scale of decimal number by default
    */
   private static final int DEFAULT_SCALE = 10000;

   /**
    * scale of decimal numbers for detecting an overflow
    */
   private static final int MAX_SCALE = 20000;

   /**
    * number for calculation a percent
    * because percent is {@value ONE_HUNDRED} part of number
    */
   private static final int ONE_HUNDRED = 100;

   /**
    * method for catting off scale tail of {@code BigDecimal} number
    *
    * @param number number to process
    * @return processed number
    */
   private BigDecimal proceed(BigDecimal number) {
      if (number.setScale(DEFAULT_SCALE, RoundingMode.HALF_UP).compareTo(BigDecimal.ZERO) == 0) {
         return BigDecimal.ZERO;
      } else {
         return number;
      }
   }

   /**
    * method for adding one number to another one
    *
    * @param a first number
    * @param b second number
    * @return sum of number a and b
    */
   public BigDecimal plus(BigDecimal a, BigDecimal b) {
      return proceed(a.add(b));
   }

   /**
    * method for subtracting one number from another one
    *
    * @param a first number
    * @param b second number
    * @return subtraction of number a and b
    */
   public BigDecimal subtract(BigDecimal a, BigDecimal b) {
      return proceed(a.subtract(b));
   }

   /**
    * method for multiplying one number by another one
    *
    * @param a first number
    * @param b second number
    * @return result of multiplying
    */
   public BigDecimal multiply(BigDecimal a, BigDecimal b) {
      return a.multiply(b);
   }

   /**
    * method for dividing one number by another one
    * sets scale to {@value MAX_SCALE} for detecting is this number overflows calculator
    *
    * @param a first number
    * @param b second number
    * @return result of dividing
    */
   public BigDecimal divide(BigDecimal a, BigDecimal b) {
      if (a.compareTo(BigDecimal.ZERO) == 0) {
         return BigDecimal.ZERO;
      }
      return a.divide(b, MAX_SCALE, RoundingMode.HALF_UP);
   }

   /**
    * method for calculation square of number
    *
    * @param a number to square
    * @return result of squaring
    */
   public BigDecimal square(BigDecimal a) {
      return a.multiply(a);
   }

   /**
    * method for calculation a value
    * of one divide by x
    *
    * @param x by this number one will be divided
    * @return result of dividing number one to x
    */
   public BigDecimal oneDivX(BigDecimal x) {
      return BigDecimal.ONE.divide(x, DEFAULT_SCALE, RoundingMode.HALF_UP);
   }

   /**
    * calculation of root value of number
    * algorithm was took from this discussion:
    * https://www.java-forums.org/advanced-java/44345-square-rooting-bigdecimal.html
    *
    * @param a number to find root
    * @return root value
    */
   public BigDecimal root(BigDecimal a) {
      if (a.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
      if (a.compareTo(BigDecimal.ONE) == 0) return a;
      BigInteger n = a.movePointRight((DEFAULT_SCALE + 1) << 1).toBigInteger();
      int bits = (n.bitLength() + 1) >> 1;
      BigInteger ix = n.shiftRight(bits);
      BigInteger ixPrev;
      do {
         ixPrev = ix;
         ix = ix.add(n.divide(ix)).shiftRight(1);
         Thread.yield();
      } while (ix.compareTo(ixPrev) != 0);
      return new BigDecimal(ix, DEFAULT_SCALE + 1);
   }

   /**
    * method for calculation of percent value of number b from a
    *
    * @param a first number
    * @param b number of percents of first number
    * @return percent value of a
    */
   public BigDecimal percent(BigDecimal a, BigDecimal b) {
      return proceed(a.multiply(b.divide(BigDecimal.valueOf(ONE_HUNDRED), DEFAULT_SCALE, RoundingMode.HALF_UP)));
   }

   /**
    * method for calculation a special operations
    * provided by number and array of special operations for this number
    *
    * @param previousNumber number for calculation percent
    * @param number         number to make calculations
    * @param operations     operations to do
    * @return processed number by operations
    */
   public BigDecimal calculateSpecialOperations(BigDecimal previousNumber, BigDecimal number, ArrayList<SpecialOperation> operations) {
      BigDecimal result = number;
      for (Operation operation : operations) {
         if (operation.equals(SpecialOperation.SQUARE)) {
            result = square(result);
         } else if (operation.equals(SpecialOperation.ONEDIVX)) {
            result = oneDivX(result);
         } else if (operation.equals(SpecialOperation.ROOT)) {
            result = root(result);
         } else if (operation.equals(SpecialOperation.NEGATE)) {
            result = result.negate();
         } else if (previousNumber != null && operation.equals(SpecialOperation.PERCENT)) {
            result = percent(previousNumber, result);
         }
      }
      return result;
   }
}
