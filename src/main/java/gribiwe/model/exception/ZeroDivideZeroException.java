package gribiwe.model.exception;

import gribiwe.model.dto.AnswerDto;

/**
 * CalculatorException class designed to be thrown
 * if was trying to divide zero by zero
 *
 * @author Gribiwe
 * @see CalculatorException
 */
public class ZeroDivideZeroException extends CalculatorException {

   /**
    * creates exception with answerDto inside
    * @param answerDto current answer dto of model
    */
   public ZeroDivideZeroException(String message, AnswerDto answerDto) {
      super(message, answerDto);
   }
}
