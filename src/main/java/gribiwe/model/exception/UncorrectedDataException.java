package gribiwe.model.exception;

import gribiwe.model.dto.AnswerDTO;

public class UncorrectedDataException extends CalculatorException {

   private static final String INCORRECT_DATA_EXCEPTION_TEXT = "Введены неверные данные";

   public UncorrectedDataException(AnswerDTO answerDTO) {
      super(INCORRECT_DATA_EXCEPTION_TEXT, answerDTO);
   }
}
