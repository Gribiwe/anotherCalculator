package gribiwe.model.exception;

import gribiwe.model.dto.AnswerDTO;

/**
 * CalculatorException class designed to be thrown
 * if was trying to divide some number (not zero) by zero
 *
 * @author Gribiwe
 * @see CalculatorException
 */
public class ZeroDivideException extends CalculatorException {


   /**
    * creates exception with answerDTO inside
    * @param answerDTO current answer dto of model
    */
   public ZeroDivideException(String message, AnswerDTO answerDTO) {
      super(message, answerDTO);
   }
}
