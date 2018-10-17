package gribiwe.controller.util;

import gribiwe.model.dto.FormingSpecialOperationsDto;
import gribiwe.model.dto.HistoryLineDto;
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
    * method for parsing {@code HistoryLineDto} to string value
    *
    * @param historyLineDto DTO of history line from model
    * @return string value of parsed history
    */
   public static String parse(HistoryLineDto historyLineDto) {
      StringBuilder stringBuilder = new StringBuilder();

      for (int i = 0; i < historyLineDto.getNumbers().size(); i++) {
         String number;
         if (historyLineDto.getSpecialOperations().get(i) != null) {
            number = parseSpecialOperations(historyLineDto.getNumbers().get(i), historyLineDto.getSpecialOperations().get(i));
         } else {
            number = OutputNumberParser.formatResult((historyLineDto.getNumbers().get(i)), false);
         }

         if (historyLineDto.getOperations().get(i).equals(PLUS)) {
            stringBuilder.append(number).append("   +   ");
         } else if (historyLineDto.getOperations().get(i).equals(SUBTRACT)) {
            stringBuilder.append(number).append("   -   ");
         } else if (historyLineDto.getOperations().get(i).equals(MULTIPLY)) {
            stringBuilder.append(number).append("   ×   ");
         } else if (historyLineDto.getOperations().get(i).equals(DIVIDE)) {
            stringBuilder.append(number).append("   ÷   ");
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
      result = OutputNumberParser.formatResult(number, false);
      for (Operation operation : operations) {
         if (operation.equals(SpecialOperation.SQUARE)) {
            result = "sqr( " + result + " )";
         } else if (operation.equals(SpecialOperation.ONE_DIV_X)) {
            result = "1/( " + result + " )";
         } else if (operation.equals(SpecialOperation.ROOT)) {
            result = "√( " + result + " )";
         } else if (operation.equals(SpecialOperation.NEGATE)) {
            result = "negate( " + result + " )";
         }
      }
      return result;
   }

   /**
    * method for parsing dto with
    * information about forming special operations
    *
    * @param formingSpecialOperationsDto dto to parse
    * @return string value of parsed number with operations
    */
   public static String parseSpecialOperations(FormingSpecialOperationsDto formingSpecialOperationsDto) {
      return parseSpecialOperations(formingSpecialOperationsDto.getNumber(), formingSpecialOperationsDto.getOperations());
   }
}
