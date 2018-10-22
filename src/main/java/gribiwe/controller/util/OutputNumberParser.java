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
    * Decimal formatter for formatting
    * result value
    */
   private final static DecimalFormat RESULT_FORMATTER;

   /**
    * Decimal formatter for formatting
    * number, entered by user
    */
   private final static DecimalFormat BUILT_NUMBER_FORMATTER;

   /*
    * static field for initialization of
    * {@link #BUILT_NUMBER_FORMATTER} and
    * {@link #RESULT_FORMATTER}
    */
   static {
      DecimalFormatSymbols decimalFormatSymbols;
      decimalFormatSymbols = new DecimalFormatSymbols();
      decimalFormatSymbols.setDecimalSeparator(',');
      decimalFormatSymbols.setGroupingSeparator(' ');
      decimalFormatSymbols.setExponentSeparator("e");

      RESULT_FORMATTER = new DecimalFormat();
      RESULT_FORMATTER.setDecimalFormatSymbols(decimalFormatSymbols);
      RESULT_FORMATTER.setRoundingMode(RoundingMode.HALF_UP);

      BUILT_NUMBER_FORMATTER = new DecimalFormat();
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
      String minus = "";
      if (dto.isNegated()) {
         minus = "-";
      }

      BigDecimal value = dto.getValue().abs();
      BUILT_NUMBER_FORMATTER.setMinimumFractionDigits(value.scale());
      BUILT_NUMBER_FORMATTER.setDecimalSeparatorAlwaysShown(dto.isPointed());
      return minus + BUILT_NUMBER_FORMATTER.format(value);
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
   public static String parseResult(BigDecimal value, boolean needSpaces) {
      String minus = "";
      if (value.compareTo(BigDecimal.ZERO) < 0) {
         minus = "-";
      }
      String result = formResult(value.abs(), needSpaces);
      return minus + result;
   }

   /**
    * method for parsing a BigDecimal value
    * it's can be really big or small values with exponents
    *
    * @param value number to parse, must be not negated
    * @return parsed number
    */
   private static String formResult(BigDecimal value, boolean needSpaces) {
      String toReturn;
      if (value.compareTo(BigDecimal.ZERO) == 0) {
         toReturn = "0";
      } else if (value.toBigInteger().compareTo(BigInteger.ONE) >= 0) {
         toReturn = parseResultBiggerOrEqualsOne(value, needSpaces);
      } else {
         toReturn = parseResultLessOne(value, needSpaces);
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
   private static String parseResultBiggerOrEqualsOne(BigDecimal value, boolean needSpace) {
      String leftValue = value.toBigInteger().toString();
      String toReturn;
      if (leftValue.length() > MAX_NUMBERS) {
         toReturn = formatWithFormatter(PATTERN_POINTED_EXPONENT_VALUE, value, needSpace);
      } else if (isHaveNotZerosAfterPoint(value)) {
         toReturn = formatWithFormatter(PATTERN_NOT_POINTED_NOT_EXPONENT_VALUE, value, needSpace);
      } else {
         String formattedValue = formatWithFormatter(PATTERN_ONE_SYMBOL_AFTER_POINT_NOT_EXPONENT_VALUE, value, false);
         int outputIntegerValueSize = formattedValue.substring(0, formattedValue.indexOf(",")).length();
         int fractionalNumberLength = MAX_NUMBERS - outputIntegerValueSize;
         toReturn = formatWithFormatter("", value, needSpace, fractionalNumberLength);
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
   private static String parseResultLessOne(BigDecimal value, boolean needSpace) {
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
         toReturn = formatWithFormatter(PATTERN_POINTED_EXPONENT_VALUE, value, needSpace);
      } else {
         toReturn = formatWithFormatter(PATTERN_POINTED_NOT_EXPONENT_VALUE, value, needSpace);
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
   private static String formatWithFormatter(String pattern, BigDecimal value, boolean needSpace) {
      return formatWithFormatter(pattern, value, needSpace, -1);
   }

   /**
    * formats string value of decimal
    * with formatter
    *
    * @param pattern                formatting pattern
    * @param value                  number to format
    * @param maximumFractionNumbers maximum digits after point,
    *                               if -1, will be used default value
    * @return string of formatted number
    */
   private static String formatWithFormatter(String pattern, BigDecimal value, boolean needSpace, int maximumFractionNumbers) {
      RESULT_FORMATTER.applyPattern(pattern);
      if (maximumFractionNumbers > -1) {
         RESULT_FORMATTER.setMaximumFractionDigits(maximumFractionNumbers);
      }
      if (needSpace) {
         RESULT_FORMATTER.setGroupingSize(GROUP_SIZE);
         RESULT_FORMATTER.setGroupingUsed(true);
      }
      String output = RESULT_FORMATTER.format(value);
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

   /**
    * method shows is BigDecimal number have
    * not zero numbers after point
    *
    * @param value checking number
    * @return true if number have not zero
    * number after point
    */
   private static boolean isHaveNotZerosAfterPoint(BigDecimal value) {
      value = value.setScale(SCALE_SIZE, RoundingMode.DOWN);
      BigDecimal valueWithScaleZero = value.setScale(0, RoundingMode.DOWN);
      return valueWithScaleZero.subtract(value).compareTo(BigDecimal.ZERO) == 0;
   }
}