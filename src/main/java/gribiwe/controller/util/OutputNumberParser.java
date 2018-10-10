package gribiwe.controller.util;

import gribiwe.model.dto.EnteredNumberDTO;
import gribiwe.model.dto.OutputNumberDTO;

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
    * method for adding spaces to parsed number
    *
    * @param origin string value of parsed number
    * @return parsed number with spaces
    */
   private String addSpaces(String origin) {
      String leftOfPoint;
      if (origin.contains(",")) {
         leftOfPoint = origin.substring(0, origin.indexOf(","));
         origin = origin.substring(origin.indexOf(","));
      } else {
         leftOfPoint = origin;
         origin = "";
      }
      for (int i = leftOfPoint.length() - 3; i > 0; i -= 3) {
         leftOfPoint = leftOfPoint.substring(0, i) + " " + leftOfPoint.substring(i, leftOfPoint.length());
      }
      return leftOfPoint + origin;
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
      for (int i = 0; i < dto.getRightNumbersLength(); i++) {
         pattern.append("0");
      }
      DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
      decimalFormatSymbols.setGroupingSeparator(' ');
      decimalFormatSymbols.setDecimalSeparator(',');
      DecimalFormat myFormatter = new DecimalFormat(pattern.toString(), decimalFormatSymbols);
      myFormatter.setRoundingMode(RoundingMode.HALF_UP);
      return minus + addSpaces(myFormatter.format(value));
   }

   /**
    * method for parsing a BigDecimal value
    * it's can be really big or small values with exponents
    *
    * @param value     number to parse
    * @param addSpaces if true - result will be with group
    *                  separating spaces
    * @return parsed number
    */
   public String formatResult(BigDecimal value, boolean addSpaces) {
      String minus = "";
      if (cut(value).compareTo(BigDecimal.ZERO) < 0) {
         minus = "-";
      }
      String result;
      value = value.abs();
      if (value.compareTo(BigDecimal.ZERO) == 0) {
         return "0";
      }
      Long localLeftOfPoint;
      String leftValue;
      String rightValue;

      if (value.toBigInteger().compareTo(BigInteger.ONE) >= 0) {
         leftValue = value.toBigInteger().toString();
         if (leftValue.length() > 16) {
            String patternn = "0.###############E0";
            DecimalFormat myFormatter = new DecimalFormat(patternn);
            myFormatter.setRoundingMode(RoundingMode.HALF_UP);
            result = myFormatter.format(value);
            if (result.contains(",")) {
               result = result.replaceAll("E", "e+");
            } else {
               result = result.replaceAll("E", ",e+");
            }
         } else if (new BigDecimal(value.toBigInteger().toString()).subtract(cut(value)).compareTo(BigDecimal.ZERO) == 0) {
            String pattern = "################";
            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
            decimalFormatSymbols.setDecimalSeparator(',');
            decimalFormatSymbols.setGroupingSeparator(' ');
            DecimalFormat myFormatter = new DecimalFormat(pattern, decimalFormatSymbols);
            myFormatter.setRoundingMode(RoundingMode.HALF_UP);
            result = myFormatter.format(value);
         } else {
            localLeftOfPoint = Long.parseLong(leftValue);
            rightValue = value.toPlainString().substring(value.toPlainString().indexOf(".") + 1);
            StringBuilder pattern = new StringBuilder("###############.0");
            DecimalFormat myFormatter = new DecimalFormat(pattern.toString());
            myFormatter.setRoundingMode(RoundingMode.HALF_UP);
            String output = myFormatter.format(new BigDecimal(localLeftOfPoint + "." + rightValue));
            int outputIntegerValueSize = output.substring(0, output.indexOf(",")).length();

            pattern = new StringBuilder();
            for (int i = 0; i < outputIntegerValueSize; i++) {
               pattern.append("#");
            }
            pattern.append(".");
            for (int i = 0; i < 16 - outputIntegerValueSize; i++) {
               pattern.append("#");
            }

            myFormatter = new DecimalFormat(pattern.toString());
            myFormatter.setRoundingMode(RoundingMode.HALF_UP);
            result = myFormatter.format(new BigDecimal(localLeftOfPoint + "." + rightValue));
         }
      } else {
         String allValue = value.toPlainString();

         int indexOfNormNumber = -1;
         for (int i = 0; i < allValue.length(); i++) {
            if (allValue.charAt(i) != '0') {
               indexOfNormNumber = i;
               break;
            }
         }

         int indexOfLastNormNumber = indexOfNormNumber;
         int zeroCount = 0;
         for (int i = indexOfNormNumber; i < allValue.length() && zeroCount < 15; i++) {
            if (allValue.charAt(i) != '0') {
               indexOfLastNormNumber = i;
               zeroCount = 0;
            } else {
               zeroCount++;
            }
         }
         if ((indexOfNormNumber > 2 && indexOfLastNormNumber >= indexOfNormNumber + 16) ||
                 indexOfNormNumber + 1 == 17 && indexOfLastNormNumber >= indexOfNormNumber ||
                 indexOfNormNumber + 1 > 17) {
            String pattern = "#.###############E0";
            DecimalFormat myFormatter = new DecimalFormat(pattern);
            myFormatter.setRoundingMode(RoundingMode.HALF_UP);
            String formattedNumber = myFormatter.format(value);
            formattedNumber = formattedNumber.replaceAll("E", "e");
            if (!formattedNumber.contains(",")) {
               formattedNumber = formattedNumber.replace("e", ",e");
            }
            result = formattedNumber;
         } else {
            String pattern = "#.################";
            DecimalFormat myFormatter = new DecimalFormat(pattern);
            myFormatter.setRoundingMode(RoundingMode.HALF_UP);
            result = myFormatter.format(value);
         }
      }
      if (addSpaces) {
         result = addSpaces(result);
      }

      return minus + result;
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
