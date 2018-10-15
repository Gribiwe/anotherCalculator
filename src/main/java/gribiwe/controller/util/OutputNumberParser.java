package gribiwe.controller.util;

import gribiwe.model.dto.EnteredNumberDTO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * class for parsing {@code OutputNumberDTO} to string value
 *
 * @author Gribiwe
 */
public class OutputNumberParser {

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
    * method for adding spaces to parsed number
    *
    * @param original string value of parsed number
    * @return parsed number with spaces
    */
   private String addSpaces(String original) {
      String leftOfPoint;
      if (original.contains(",")) {
         leftOfPoint = original.substring(0, original.indexOf(","));
         original = original.substring(original.indexOf(","));
      } else {
         leftOfPoint = original;
         original = "";
      }
      for (int i = leftOfPoint.length() - 3; i > 0; i -= 3) {
         leftOfPoint = leftOfPoint.substring(0, i) + " " + leftOfPoint.substring(i);
      }
      return leftOfPoint + original;
   }

   /**
    * method for parsing a value of {@code OutputNumberDTO} which appeared
    * by user's input
    *
    * @param dto {@code OutputNumberDTO} to parse
    * @return string value of {@code OutputNumberDTO}
    */
   public String formatInput(EnteredNumberDTO dto) {
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
   public String formatResult(BigDecimal value, boolean needSpaces) {
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
    * method for parsing a BigDecimal value
    * it's can be really big or small values with exponents
    *
    * @param value number to parse, must be not negated
    * @return parsed number
    */
   private String formatResult(BigDecimal value) {
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
   private String parseResultBiggerOrEqualsOne(BigDecimal value) {
      String leftValue = value.toBigInteger().toString();
      String toReturn;
      if (leftValue.length() > 16) {
         toReturn = formatWithFormatter(PATTERN_POINTED_EXPONENT_VALUE, value);
      } else if (new BigDecimal(value.toBigInteger().toString()).subtract(cut(value)).compareTo(BigDecimal.ZERO) == 0) {
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
         for (int i = 0; i < 16 - outputIntegerValueSize; i++) {
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
   private String parseResultLessOne(BigDecimal value) {
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
      for (int i = indexOfNotZeroNumber; i < allValue.length() && zeroCount < 15; i++) {
         if (allValue.charAt(i) != '0') {
            indexOfLastNormNumber = i;
            zeroCount = 0;
         } else {
            zeroCount++;
         }
      }
      String toReturn;
      if ((indexOfNotZeroNumber > 2 && indexOfLastNormNumber >= indexOfNotZeroNumber + 16) ||
              indexOfNotZeroNumber + 1 == 17 && indexOfLastNormNumber >= indexOfNotZeroNumber ||
              indexOfNotZeroNumber + 1 > 17) {
         toReturn = formatWithFormatter(PATTERN_POINTED_EXPONENT_VALUE, value);

      } else {
         toReturn = formatWithFormatter(PATTERN_POINTED_NOT_EXPONENT_VALUE, value);
      }
      return toReturn;
   }

   /**
    * formats string value of decimal
    * with formatter
    *
    * @param pattern formatting pattern
    * @param value   number to format
    * @return string of formatted number
    */
   private String formatWithFormatter(String pattern, BigDecimal value) {
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

   /**
    * cuts scale of number to 10000
    *
    * @param a number to re-scale
    * @return rightly scaled number
    */
   private BigDecimal cut(BigDecimal a) {
      String aStr = a.toPlainString();
      if (aStr.length() > 10000) {
         return new BigDecimal(aStr.substring(0, 10001));
      } else {
         return a;
      }
   }
}
