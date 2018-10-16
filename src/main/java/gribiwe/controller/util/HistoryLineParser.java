package gribiwe.controller.util;

import gribiwe.model.dto.HistoryLineDto;

import static gribiwe.model.util.SimpleOperation.*;

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
    * method for parsing {@code HistoryLineDto} to string value
    *
    * @param historyLineDto DTO of history line from model
    * @return string value of parsed history
    */
   public String parse(HistoryLineDto historyLineDto) {
      StringBuilder stringBuilder = new StringBuilder();

      for (int i = 0; i < historyLineDto.getNumbers().size(); i++) {
         String number;
         if (historyLineDto.getSpecialOperations().get(i) != null) {
            number = new LastSpecialOperationStoryParser().parse(historyLineDto.getNumbers().get(i), historyLineDto.getSpecialOperations().get(i));
         } else {
            number = parser.formatResult((historyLineDto.getNumbers().get(i)), false);
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
}
