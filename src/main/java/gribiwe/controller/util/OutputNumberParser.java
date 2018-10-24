package gribiwe.controller.util;

import gribiwe.model.dto.BuildingNumber;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import static gribiwe.model.util.BigDecimalZeroComparator.isZero;

/**
 * class for parsing {@code BuildingNumber} to string value
 *
 * @author Gribiwe
 */
public class OutputNumberParser {

   /**
    * scale for cutting BigDecimal value
    */
   private static final int SCALE_SIZE = 9984;

   /**
    * count of numbers in each
    * number group
    */
   private static final int GROUP_SIZE = 3;

   /**
    * decimal pattern for number with
    * exponent and one symbol before point
    */
   private final static String PATTERN_PLUS_EXPONENT_VALUE = "0.###############E0";

   /**
    * decimal pattern for non-decimal number
    * without exponent and point
    */
   private final static String PATTERN_NOT_POINTED_NOT_EXPONENT_VALUE = "################";

   /**
    * decimal patter for number with
    * one digit before point before point
    * without exponent
    */
   private final static String PATTERN_POINTED_NOT_EXPONENT_VALUE = "#.################";

   /**
    * max count of numbers in out
    */
   private final static int MAX_NUMBERS = 16;

   /**
    * value of index of third zero after point
    */
   private final static int THIRD_ZERO_AFTER_POINT_INDEX = 2;

   /**
    * Decimal formatter for formatting
    * result value
    */
   private final static DecimalFormat RESULT_FORMATTER = new DecimalFormat();

   /**
    * Decimal formatter for formatting
    * number, entered by user
    */
   private final static DecimalFormat BUILT_NUMBER_FORMATTER = new DecimalFormat();

   /**
    * Decimal symbols for formatting
    */
   private final static DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();

   /**
    * string of plus exponent
    */
   private final static String EXPONENT_PLUS = "e+";

   /**
    * string of minus exponent
    */
   private final static String EXPONENT_MINUS = "e";

   /**
    * string of minus prefix of building number formatter
    */
   private final static String MINUS = "-";

   /**
    * enum for showing is exponent needs
    * and what exactly exponent needs
    */
   private enum Exponent {

      /**
       * exponent doesn't need
       */
      NOT_NEEDS,

      /**
       * needs negate exponent
       * e-
       */
      NEGATE,

      /**
       * needs plus exponent
       * e+
       */
      PLUS
   }

   /*
    * static field for initialization of
    * {@link #BUILT_NUMBER_FORMATTER} and
    * {@link #RESULT_FORMATTER}
    */
   static {
      decimalFormatSymbols.setDecimalSeparator(',');
      decimalFormatSymbols.setGroupingSeparator(' ');

      BUILT_NUMBER_FORMATTER.setDecimalFormatSymbols(decimalFormatSymbols);
      BUILT_NUMBER_FORMATTER.setGroupingSize(GROUP_SIZE);
      BUILT_NUMBER_FORMATTER.setGroupingUsed(true);
   }

   /**
    * method for parsing a value of {@code BuildingNumber} which appeared
    * by user's input
    *
    * @param dto {@code BuildingNumber} to parse
    * @return string value of {@code BuildingNumber}
    */
   public static String formatInput(BuildingNumber dto) {
      BigDecimal value = dto.getValue();
      BUILT_NUMBER_FORMATTER.setMinimumFractionDigits(value.scale());
      BUILT_NUMBER_FORMATTER.setDecimalSeparatorAlwaysShown(dto.isPointed());
      if (dto.isNegated() && isZero(value)) {
         BUILT_NUMBER_FORMATTER.setPositivePrefix(MINUS);
      }
      String result = BUILT_NUMBER_FORMATTER.format(value);
      BUILT_NUMBER_FORMATTER.setPositivePrefix(MINUS);
      //      if (dto.isNegated() && isZero(value)) { //todo minus adding have a question
//         result = "-" + result;
//      }
      return result;
   }


   /**
    * method for formatting a BigDecimal value
    * it's can be really big or small values with exponents
    *
    * @param value number to formatting, must be not negated
    * @return formatted number
    */
   public static String formatResult(BigDecimal value, boolean needSpaces) {//todo remove minus str done
      String toReturn;
      if (isZero(value)) {
         toReturn = "0";
      } else if (value.toBigInteger().abs().compareTo(BigInteger.ONE) >= 0) {
         toReturn = formatResultBiggerOrEqualsOne(value, needSpaces);
      } else {
         toReturn = formatResultLessOne(value, needSpaces);
      }
      return toReturn;
   }

   /**
    * method for formatting result value
    * bigger or equals to one
    *
    * @param value formatting number
    * @return formatted string value
    */
   private static String formatResultBiggerOrEqualsOne(BigDecimal value, boolean needSpace) {
      int integerDigitsLength = integerDigitsLength(value);
      String toReturn;
      if (integerDigitsLength > MAX_NUMBERS) {
         toReturn = formatWithFormatter(PATTERN_PLUS_EXPONENT_VALUE, value, needSpace, Exponent.PLUS);
      } else if (isHaveNotZerosAfterPoint(value)) {
         toReturn = formatWithFormatter(PATTERN_NOT_POINTED_NOT_EXPONENT_VALUE, value, needSpace, Exponent.NOT_NEEDS);
      } else {
         int fractionalNumberLength = MAX_NUMBERS - integerDigitsLength;
         toReturn = formatWithFormatter("", value, needSpace, Exponent.NOT_NEEDS, fractionalNumberLength);
      }
      return toReturn;
   }

   /**
    * method for formatting result
    * number less then one.
    * finds non-zero digits at decimal
    * number and moving them to start with
    * negative exponent (if needs)
    *
    * @param value formatting number
    * @return formatted string value
    */
   private static String formatResultLessOne(BigDecimal value, boolean needSpace) {//todo removed string done
      int indexOfNotZeroNumber = value.scale() - value.precision();
      int indexOfLastNormNumber = indexOfNotZeroNumber;

      if (indexOfNotZeroNumber > 0) {
         int maxLastIndex = indexOfLastNormNumber + MAX_NUMBERS;
         if (reScaleAndGetLastNotZeroDigit(value, maxLastIndex) != indexOfNotZeroNumber) {
            indexOfLastNormNumber = reScaleAndGetLastNotZeroDigit(value, maxLastIndex + 2);
         }
      }

      String toReturn;
      if (isNumberSequenceNeedMinusExponent(indexOfLastNormNumber, indexOfNotZeroNumber)) {
         toReturn = formatWithFormatter(PATTERN_PLUS_EXPONENT_VALUE, value, needSpace, Exponent.NEGATE);
      } else {
         toReturn = formatWithFormatter(PATTERN_POINTED_NOT_EXPONENT_VALUE, value, needSpace, Exponent.NOT_NEEDS);
      }
      return toReturn;
   }

   /**
    * method which re-scales BigDecimal number
    * and finds last not zero digit from fractional
    * part of number
    *
    * @param number   number to re-scale and process
    * @param newScale value of new scale of number
    * @return index of not zero digit
    */
   private static int reScaleAndGetLastNotZeroDigit(BigDecimal number, int newScale) {
      number = reScale(number, newScale);
      return number.stripTrailingZeros().scale() - 1;
   }

   /**
    * re-scales BigDecimal number
    *
    * @param number   to re-scale
    * @param newScale value of new number scale
    * @return re-scaled number
    */
   private static BigDecimal reScale(BigDecimal number, int newScale) {
      return number.setScale(newScale, RoundingMode.DOWN);
   }

   /**
    * method for checking is number should be
    * with minus exponent format by
    * indexes of start and end of normal
    * number sequence
    *
    * @param indexOfLastNormNumber end index of normal number sequence
    * @param indexOfNotZeroNumber  start index of normal number sequence
    * @return true if number should be with minus exponent
    */
   private static boolean isNumberSequenceNeedMinusExponent(int indexOfLastNormNumber, int indexOfNotZeroNumber) {
      boolean isAfterThirdZero = indexOfNotZeroNumber > THIRD_ZERO_AFTER_POINT_INDEX;
      boolean isHaveEnoughLength = indexOfLastNormNumber >= indexOfNotZeroNumber + MAX_NUMBERS;
      boolean isStartsAtMaxNumberLimitIndex = indexOfNotZeroNumber + 1 == MAX_NUMBERS + 1;
      boolean isHaveMoreThenOneNumber = indexOfLastNormNumber >= indexOfNotZeroNumber;
      boolean isStartsAfterMaxNumberLimit = indexOfNotZeroNumber + 1 > MAX_NUMBERS + 1;

      return isAfterThirdZero && isHaveEnoughLength ||
              isStartsAtMaxNumberLimitIndex && isHaveMoreThenOneNumber ||
              isStartsAfterMaxNumberLimit;
   }

   /**
    * formats string value of decimal
    * with formatter
    *
    * @param pattern   formatting pattern
    * @param value     number to format
    * @param needSpace shows is formatter have to add group spaces
    * @param exponent  shows what exponent needs (if it needs)
    * @return string of formatted number
    */
   private static String formatWithFormatter(String pattern, BigDecimal value, boolean needSpace, Exponent exponent) {
      return formatWithFormatter(pattern, value, needSpace, exponent, -1);
   }

   /**
    * formats string value of decimal
    * with formatter
    *
    * @param pattern                formatting pattern
    * @param value                  number to format
    * @param maximumFractionNumbers maximum digits after point,
    *                               if -1, will be used default value
    * @param needSpace              shows is formatter have to add group spaces
    * @param exponent               shows what exponent needs (if it needs)
    * @return string of formatted number
    */
   private static String formatWithFormatter(String pattern, BigDecimal value, boolean needSpace, Exponent exponent, int maximumFractionNumbers) {
      RESULT_FORMATTER.applyPattern(pattern);// TODO: 24.10.2018 remove string on it and same done
      RESULT_FORMATTER.setGroupingSize(GROUP_SIZE);
      RESULT_FORMATTER.setGroupingUsed(needSpace);
      RESULT_FORMATTER.setRoundingMode(RoundingMode.HALF_UP);
      if (maximumFractionNumbers > -1) {
         RESULT_FORMATTER.setMaximumFractionDigits(maximumFractionNumbers);
      }
      boolean showAlwaysDecimalSeparator = false;
      if (exponent != Exponent.NOT_NEEDS) {
         showAlwaysDecimalSeparator = true;
         String exponentToSet;
         if (exponent == Exponent.PLUS) {
            exponentToSet = EXPONENT_PLUS;
         } else {
            exponentToSet = EXPONENT_MINUS;
         }
         decimalFormatSymbols.setExponentSeparator(exponentToSet);
      }
      RESULT_FORMATTER.setDecimalSeparatorAlwaysShown(showAlwaysDecimalSeparator);
      RESULT_FORMATTER.setDecimalFormatSymbols(decimalFormatSymbols);
      return RESULT_FORMATTER.format(value);
   }

   /**
    * method shows is BigDecimal number have
    * not zero numbers after point
    *
    * @param value checking number
    * @return true if number have not zero
    * number after point
    */
   private static boolean isHaveNotZerosAfterPoint(BigDecimal value) {
      value = reScale(value, SCALE_SIZE);
      BigDecimal valueWithScaleZero = reScale(value, 0);
      return isZero(valueWithScaleZero.subtract(value));
   }

   /**
    * method to calculate count of
    * digits in the integer part of BigDecimal value.
    * code gotten from  this conversation:
    * https://stackoverflow.com/questions/18828377/biginteger-count-the-number-of-decimal-digits-in-a-scalable-method
    *
    * @param number number to proceed
    * @return amount of digits in the integer part of BigDecimal
    */
   private static int integerDigitsLength(BigDecimal number) {
      BigInteger huge = number.toBigInteger().abs();
      int digits = 0;
      int bits = huge.bitLength();
      // Serious reductions.
      while (bits > 4) {
         // 4 > log[2](10) so we should not reduce it too far.
         int reduce = bits / 4;
         // Divide by 10^reduce
         huge = huge.divide(BigInteger.TEN.pow(reduce));
         // Removed that many decimal digits.
         digits += reduce;
         // Recalculate bitLength
         bits = huge.bitLength();
      }
      // Now 4 bits or less - add 1 if necessary.
      if (huge.intValue() > 9) {
         digits += 1;
      }
      return digits + 1;
   }
}