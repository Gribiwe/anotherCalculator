package gribiwe.controller.util;

import gribiwe.model.dto.BuildingNumber;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

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
   private final static String PATTERN_POINTED_EXPONENT_VALUE = "0.###############E0";

   /**
    * decimal pattern for non-decimal number
    * without exponent and point
    */
   private final static String PATTERN_NOT_POINTED_NOT_EXPONENT_VALUE = "################";

   /**
    * decimal point for number with only
    * one symbol after point
    * without exponent
    */
   private final static String PATTERN_ONE_SYMBOL_AFTER_POINT_NOT_EXPONENT_VALUE = "###############.0";

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
    * method for parsing a value of {@code BuildingNumber} which appeared
    * by user's input
    *
    * @param dto {@code BuildingNumber} to parse
    * @return string value of {@code BuildingNumber}
    */
   public static String formatInput(BuildingNumber dto) {
      String minus = "";
      if (dto.isNegated()) {
         minus = "-";
      }
      BigDecimal value = dto.getValue().abs();
      StringBuilder pattern;
      pattern = new StringBuilder("0");
      if (dto.isPointed()) {
         pattern.append(".");
      }
      for (int i = 0; i < value.scale(); i++) {
         pattern.append("0");
      }
      String formatResult = formatWithFormatter(pattern.toString(), value);
      return minus + addSpaces(formatResult);
   }

   /**
    * method for parsing a BigDecimal value
    * it's can be really big or small values with exponents
    *
    * @param value      number to parse
    * @param needSpaces if true string will be
    *                   with group separate spaces
    * @return parsed number
    */
   public static String formatResult(BigDecimal value, boolean needSpaces) {
      String minus = "";

      if (value.compareTo(BigDecimal.ZERO) < 0) {
         minus = "-";
      }

      String result = formatResult(value.abs());
      if (needSpaces) {
         result = addSpaces(result);
      }
      return minus + result;
   }

   /**
    * method for adding spaces to parsed number
    *
    * @param original string value of parsed number
    * @return parsed number with spaces
    */
   private static String addSpaces(String original) {
      String leftOfPoint;
      if (original.contains(",")) {
         leftOfPoint = original.substring(0, original.indexOf(","));
         original = original.substring(original.indexOf(","));
      } else {
         leftOfPoint = original;
         original = "";
      }
      for (int i = leftOfPoint.length() - GROUP_SIZE; i > 0; i -= GROUP_SIZE) {
         leftOfPoint = leftOfPoint.substring(0, i) + " " + leftOfPoint.substring(i);
      }
      return leftOfPoint + original;
   }

   /**
    * method for parsing a BigDecimal value
    * it's can be really big or small values with exponents
    *
    * @param value number to parse, must be not negated
    * @return parsed number
    */
   private static String formatResult(BigDecimal value) {
      String toReturn;
      if (value.compareTo(BigDecimal.ZERO) == 0) {
         toReturn = "0";
      } else if (value.toBigInteger().compareTo(BigInteger.ONE) >= 0) {
         toReturn = parseResultBiggerOrEqualsOne(value);
      } else {
         toReturn = parseResultLessOne(value);
      }
      return toReturn;
   }

   /**
    * method for parsing result value
    * bigger or equals to one
    *
    * @param value parsing number
    * @return parsed string value
    */
   private static String parseResultBiggerOrEqualsOne(BigDecimal value) {
      String leftValue = value.toBigInteger().toString();
      String toReturn;
      if (leftValue.length() > MAX_NUMBERS) {
         toReturn = formatWithFormatter(PATTERN_POINTED_EXPONENT_VALUE, value);
      } else if (isHaveNotZerosAfterPoint(value)) {
         toReturn = formatWithFormatter(PATTERN_NOT_POINTED_NOT_EXPONENT_VALUE, value);
      } else {
         StringBuilder pattern = new StringBuilder(PATTERN_ONE_SYMBOL_AFTER_POINT_NOT_EXPONENT_VALUE);
         String formattedValue = formatWithFormatter(pattern.toString(), value);
         int outputIntegerValueSize = formattedValue.substring(0, formattedValue.indexOf(",")).length();

         pattern = new StringBuilder();
         for (int i = 0; i < outputIntegerValueSize; i++) {
            pattern.append("#");
         }
         pattern.append(".");
         for (int i = 0; i < MAX_NUMBERS - outputIntegerValueSize; i++) {
            pattern.append("#");
         }
         toReturn = formatWithFormatter(pattern.toString(), value);
      }
      return toReturn;
   }

   /**
    * method for parsing result
    * number less then one.
    * finds non-zero digits at decimal
    * number and moving them to start with
    * negative exponent (if needs)
    *
    * @param value parsing number
    * @return parsed string value
    */
   private static String parseResultLessOne(BigDecimal value) {
      String allValue = value.toPlainString();
      allValue = allValue.substring(allValue.indexOf(".") + 1);

      int indexOfNotZeroNumber = -1;
      for (int i = 0; i < allValue.length(); i++) {
         if (allValue.charAt(i) != '0') {
            indexOfNotZeroNumber = i;
            break;
         }
      }

      int indexOfLastNormNumber = indexOfNotZeroNumber;
      int zeroCount = 0;
      for (int i = indexOfNotZeroNumber; i < allValue.length() && zeroCount < MAX_NUMBERS - 1; i++) {
         if (allValue.charAt(i) != '0') {
            indexOfLastNormNumber = i;
            zeroCount = 0;
         } else {
            zeroCount++;
         }
      }
      String toReturn;
      if (isNumberSequenceNeedMinusExponent(indexOfLastNormNumber, indexOfNotZeroNumber)) {
         toReturn = formatWithFormatter(PATTERN_POINTED_EXPONENT_VALUE, value);

      } else {
         toReturn = formatWithFormatter(PATTERN_POINTED_NOT_EXPONENT_VALUE, value);
      }
      return toReturn;
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
      boolean isAfterThirdZero = indexOfNotZeroNumber > 2;
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
    * @param pattern formatting pattern
    * @param value   number to format
    * @return string of formatted number
    */
   private static String formatWithFormatter(String pattern, BigDecimal value) {
      DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
      decimalFormatSymbols.setDecimalSeparator(',');
      decimalFormatSymbols.setGroupingSeparator(' ');
      decimalFormatSymbols.setExponentSeparator("e");

      DecimalFormat myFormatter = new DecimalFormat(pattern, decimalFormatSymbols);
      myFormatter.setRoundingMode(RoundingMode.HALF_UP);

      String output = myFormatter.format(value);
      if (!output.contains("e-")) {
         if (output.contains(",")) {
            output = output.replaceAll("e", "e+");
         } else {
            output = output.replaceAll("e", ",e+");
         }
      } else if (!output.contains(",")) {
         output = output.replaceAll("e", ",e");
      }
      return output;
   }

   private static boolean isHaveNotZerosAfterPoint(BigDecimal value) {
      value = value.setScale(SCALE_SIZE, RoundingMode.DOWN);
      BigDecimal valueWithScaleZero = value.setScale(0, RoundingMode.DOWN);
      return valueWithScaleZero.subtract(value).compareTo(BigDecimal.ZERO) == 0;
   }
}
