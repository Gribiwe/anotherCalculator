package gribiwe.model.exception;

import gribiwe.model.dto.AnswerDTO;
import gribiwe.model.dto.LastSpecialOperationHistoryDTO;
import gribiwe.model.dto.HistoryLineDTO;
import gribiwe.model.dto.OutputNumberDTO;

public class ZeroDivideZeroException extends CalculatorException {

   private static final String ZERO_DIVIDE_BY_ZERO_EXCEPTION_TEXT = "Результат не определен";

   public ZeroDivideZeroException(AnswerDTO answerDTO) {
      super(ZERO_DIVIDE_BY_ZERO_EXCEPTION_TEXT, answerDTO);
   }
}
