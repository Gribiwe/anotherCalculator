package gribiwe.model.exception;

import gribiwe.model.dto.AnswerDTO;

/**
 * CalculatorException class designed to be thrown
 * if was trying to divide zero by zero
 *
 * @author Gribiwe
 * @see CalculatorException
 */
public class ZeroDivideZeroException extends CalculatorException {

   /**
    * message of this exception
    */
   private static final String ZERO_DIVIDE_BY_ZERO_EXCEPTION_TEXT = "Результат не определен";

   /**
    * creates exception with answerDTO inside
    * @param answerDTO current answer dto of model
    */
   public ZeroDivideZeroException(AnswerDTO answerDTO) {
      super(ZERO_DIVIDE_BY_ZERO_EXCEPTION_TEXT, answerDTO);
   }
}
