package gribiwe.model.exception;

import gribiwe.model.dto.AnswerDTO;
import gribiwe.model.dto.LastSpecialOperationHistoryDTO;
import gribiwe.model.dto.HistoryLineDTO;
import gribiwe.model.dto.OutputNumberDTO;

/**
 * CalculatorException class designed to be thrown
 * if value of calculator was overflowed
 *
 * @author Gribiwe
 * @see CalculatorException
 */
public class OverflowException extends CalculatorException {

   /**
    * message of this exception
    */
   private static final String OVERFLOW_EXCEPTION_TEXT = "Переполнение";

   /**
    * creates exception with answerDTO inside
    * @param answerDTO current answer dto of model
    */
   public OverflowException(AnswerDTO answerDTO) {
      super(OVERFLOW_EXCEPTION_TEXT, answerDTO);
   }
}
