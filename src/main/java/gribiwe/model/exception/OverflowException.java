package gribiwe.model.exception;

import gribiwe.model.dto.AnswerDTO;
import gribiwe.model.dto.LastSpecialOperationHistoryDTO;
import gribiwe.model.dto.HistoryLineDTO;
import gribiwe.model.dto.OutputNumberDTO;

public class OverflowException extends CalculatorException {

   private static final String OVERFLOW_EXCEPTION_TEXT = "Переполнение";

   public OverflowException(AnswerDTO answerDTO) {
      super(OVERFLOW_EXCEPTION_TEXT, answerDTO);
   }
}
