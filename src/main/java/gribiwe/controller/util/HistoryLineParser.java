package gribiwe.controller.util;

import gribiwe.model.dto.HistoryLineDTO;
import gribiwe.model.dto.OutputNumberDTO;
import gribiwe.model.util.SimpleOperation;

/**
 * class for parsing a history line value to string
 *
 * @author Gribiwe
 */
public class HistoryLineParser {

   /**
    * exemplar of {@code OutputNumberParser} for parsing numbers
    */
   private OutputNumberParser parser;

   /**
    * initial of {@code OutputNumberParser}
    */
   public HistoryLineParser() {
      parser = new OutputNumberParser();
   }

   /**
    * method for parsing {@code HistoryLineDTO} to string value
    *
    * @param historyLineDTO DTO of history line from model
    * @return string value of parsed history
    */
   public String parse(HistoryLineDTO historyLineDTO) {
      StringBuilder stringBuilder = new StringBuilder();

      for (int i = 0; i < historyLineDTO.getNumbers().size(); i++) {
         String number;
         if (historyLineDTO.getSpecialOperations().get(i) != null) {
            number = new LastSpecialOperationStoryParser().parse(historyLineDTO.getNumbers().get(i), historyLineDTO.getSpecialOperations().get(i));
         } else {
            number = parser.formatResult((historyLineDTO.getNumbers().get(i)), false);
         }

         if (historyLineDTO.getOperations().get(i).equals(SimpleOperation.PLUS)) {
            stringBuilder.append(number).append("   +   ");
         } else if (historyLineDTO.getOperations().get(i).equals(SimpleOperation.SUBTRACT)) {
            stringBuilder.append(number).append("   -   ");
         } else if (historyLineDTO.getOperations().get(i).equals(SimpleOperation.MULTIPLY)) {
            stringBuilder.append(number).append("   ×   ");
         } else if (historyLineDTO.getOperations().get(i).equals(SimpleOperation.DIVIDE)) {
            stringBuilder.append(number).append("   ÷   ");
         }
      }
      return stringBuilder.toString();
   }
}
