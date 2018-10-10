package gribiwe.model.exception;

import gribiwe.model.dto.AnswerDTO;
import gribiwe.model.dto.LastSpecialOperationHistoryDTO;
import gribiwe.model.dto.HistoryLineDTO;
import gribiwe.model.dto.OutputNumberDTO;

public class ZeroDivideException extends CalculatorException {

   private static final String DIVIDE_BY_ZERO_EXCEPTION_TEXT = "Деление на ноль невозможно";

   public ZeroDivideException(AnswerDTO answerDTO) {
      super(DIVIDE_BY_ZERO_EXCEPTION_TEXT, answerDTO);
   }
}
