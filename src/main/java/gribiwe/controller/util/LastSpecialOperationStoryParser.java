package gribiwe.controller.util;

import gribiwe.model.dto.TailSpecialOperationHistoryDto;
import gribiwe.model.util.Operation;
import gribiwe.model.util.SpecialOperation;

import java.math.BigDecimal;
import java.util.List;

/**
 * class for parsing a part of history
 * with special operations. Because of
 * calculator's behaviour i called it "Fake"
 * history part so this reason why this is LastSpecialOperationStoryParser
 *
 * @author Gribiwe
 */
public class LastSpecialOperationStoryParser {

   /**
    * method for parsing string value of
    * special operations with one number
    *
    * @param number     processing number
    * @param operations special operations of number
    * @return string value of parsed number with operations
    */
   String parse(BigDecimal number, List<SpecialOperation> operations) {
      String result;
      result = new OutputNumberParser().formatResult(number, false);
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
    * method for parsing {@code TailSpecialOperationHistoryDto} value to string value
    *
    * @param tailSpecialOperationHistoryDto dto of fake history from model
    * @return string value of parsed history
    */
   public String parse(TailSpecialOperationHistoryDto tailSpecialOperationHistoryDto) {
      if (tailSpecialOperationHistoryDto == null) {
         return "";
      }

      BigDecimal number = tailSpecialOperationHistoryDto.getNumber();
      List<SpecialOperation> operations = tailSpecialOperationHistoryDto.getOperations();
      return parse(number, operations);
   }

}
