package gribiwe.model.exception;

import gribiwe.model.dto.AnswerDTO;
import gribiwe.model.dto.LastSpecialOperationHistoryDTO;
import gribiwe.model.dto.HistoryLineDTO;
import gribiwe.model.dto.OutputNumberDTO;

public class CalculatorException extends Exception {

   private AnswerDTO answerDTO;

   public CalculatorException(String message, AnswerDTO answerDTO) {
      super(message);
      this.answerDTO = answerDTO;
   }

   public AnswerDTO getAnswerDTO() {
      return answerDTO;
   }
}
