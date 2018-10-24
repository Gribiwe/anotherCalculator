package gribiwe.model.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Class for comparing BigDecimal values with zero
 */
public class BigDecimalZeroComparator {

   /**
    * checks BigDecimal number is zero
    *
    * @param number  BigDecimal number to check
    * @return true if BigDecimal number equals to zero
    */
   public static boolean isZero(BigDecimal number) {
      return number.compareTo(BigDecimal.ZERO) == 0;
   }

   /**
    * checks BigInteger number is zero
    *
    * @param number  BigInteger number to check
    * @return true if BigInteger number equals to zero
    */
   public static boolean isZero(BigInteger number) {
      return number.compareTo(BigInteger.ZERO) == 0;
   }

   /**
    * checks BigDecimal is number less then zero
    *
    * @param number number to check
    * @return true if number less to zero
    */
   public static boolean isLessThenZero(BigDecimal number) {
      return number.compareTo(BigDecimal.ZERO) < 0;
   }
}
