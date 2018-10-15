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
    * creates exception with answerDTO inside
    * @param answerDTO current answer dto of model
    */
   public ZeroDivideZeroException(String message, AnswerDTO answerDTO) {
      super(message, answerDTO);
   }
}
