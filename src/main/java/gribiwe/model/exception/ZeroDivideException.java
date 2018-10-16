package gribiwe.model.exception;

import gribiwe.model.dto.AnswerDto;

/**
 * CalculatorException class designed to be thrown
 * if was trying to divide some number (not zero) by zero
 *
 * @author Gribiwe
 * @see CalculatorException
 */
public class ZeroDivideException extends CalculatorException {


   /**
    * creates exception with answerDto inside
    * @param answerDto current answer dto of model
    */
   public ZeroDivideException(String message, AnswerDto answerDto) {
      super(message, answerDto);
   }
}
