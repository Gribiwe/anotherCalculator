package gribiwe.model.exception;

import gribiwe.model.dto.AnswerDto;

/**
 * CalculatorException class designed to be thrown
 * if was an overflow
 *
 * @author Gribiwe
 * @see CalculatorException
 */
public class OverflowException extends CalculatorException {

   /**
    * creates exception with answerDto inside
    * @param answerDto current answer dto of model
    */
   public OverflowException(String message, AnswerDto answerDto) {
      super(message, answerDto);
   }

}
