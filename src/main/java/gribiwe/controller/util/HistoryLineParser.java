package gribiwe.controller.util;

import gribiwe.model.dto.BuildingSpecialOperations;
import gribiwe.model.dto.HistoryInfo;
import gribiwe.model.util.Operation;
import gribiwe.model.util.SpecialOperation;

import java.math.BigDecimal;
import java.util.List;

import static gribiwe.model.util.SimpleOperation.*;

/**
 * class for parsing a history line value to string
 *
 * @author Gribiwe
 */
public class HistoryLineParser {

   /**
    * String formatting pattern for square
    * special operation
    */
   private static final String SQR_PATTERN =  "sqr( %s )";

   /**
    * String formatting pattern for
    * one divide by x special operation
    */
   private static final String ONE_DIVIDE_BY_X_PATTERN =  "1/( %s )";

   /**
    * String formatting pattern for root
    * special operation
    */
   private static final String ROOT_PATTERN =  "√( %s )";

   /**
    * String formatting pattern for negate
    * special operation
    */
   private static final String NEGATE_PATTERN =  "negate( %s )";

   /**
    * string value for operation
    * plus for parsing
    */
   private static final String PLUS_STRING =  "   +   ";

   /**
    * string value for operation
    * subtract for parsing
    */
   private static final String SUBTRACT_STRING =  "   -   ";

   /**
    * string value for operation
    * multiply for parsing
    */
   private static final String MULTIPLY_STRING =  "   ×   ";

   /**
    * string value for operation
    * divide for parsing
    */
   private static final String DIVIDE_STRING =  "   ÷   ";

   /**
    * method for parsing {@code HistoryInfo} to string value
    *
    * @param historyInfo DTO of historyInfo line from model
    * @return string value of parsed historyInfo
    */
   public static String parse(HistoryInfo historyInfo) {
      StringBuilder stringBuilder = new StringBuilder();

      for (int i = 0; i < historyInfo.getNumbers().size(); i++) {
         String number;
         if (historyInfo.getSpecialOperations().get(i) != null) {
            number = parseSpecialOperations(historyInfo.getNumbers().get(i), historyInfo.getSpecialOperations().get(i));
         } else {
            number = OutputNumberParser.parseResult((historyInfo.getNumbers().get(i)), false);
         }

         if (historyInfo.getOperations().get(i).equals(PLUS)) {
            stringBuilder.append(number).append(PLUS_STRING);
         } else if (historyInfo.getOperations().get(i).equals(SUBTRACT)) {
            stringBuilder.append(number).append(SUBTRACT_STRING);
         } else if (historyInfo.getOperations().get(i).equals(MULTIPLY)) {
            stringBuilder.append(number).append(MULTIPLY_STRING);
         } else if (historyInfo.getOperations().get(i).equals(DIVIDE)) {
            stringBuilder.append(number).append(DIVIDE_STRING);
         }
      }
      return stringBuilder.toString();
   }

   /**
    * method for parsing string value of
    * special operations with one number
    *
    * @param number     processing number
    * @param operations special operations of number
    * @return string value of parsed number with operations
    */
   private static String parseSpecialOperations(BigDecimal number, List<SpecialOperation> operations) {
      String result;
      result = OutputNumberParser.parseResult(number, false);
      for (Operation operation : operations) {
         if (operation.equals(SpecialOperation.SQUARE)) {
            result = String.format(SQR_PATTERN, result);
         } else if (operation.equals(SpecialOperation.ONE_DIV_X)) {
            result = String.format(ONE_DIVIDE_BY_X_PATTERN, result);
         } else if (operation.equals(SpecialOperation.ROOT)) {
            result = String.format(ROOT_PATTERN, result);
         } else if (operation.equals(SpecialOperation.NEGATE)) {
            result = String.format(NEGATE_PATTERN, result);
         }
      }
      return result;
   }

   /**
    * method for parsing dto with
    * information about forming special operations
    *
    * @param buildingSpecialOperations dto to parse
    * @return string value of parsed number with operations
    */
   public static String parseSpecialOperations(BuildingSpecialOperations buildingSpecialOperations) {
      return parseSpecialOperations(buildingSpecialOperations.getNumber(), buildingSpecialOperations.getOperations());
   }
}
