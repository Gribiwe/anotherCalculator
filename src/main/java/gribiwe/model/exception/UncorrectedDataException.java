package gribiwe.model.exception;

import gribiwe.model.dto.AnswerDto;

/**
 * CalculatorException class designed to be thrown
 * if was trying to get root of negated value
 *
 * @author Gribiwe
 * @see CalculatorException
 */
public class UncorrectedDataException extends CalculatorException {

   /**
    * creates exception with answerDto inside
    * @param answerDto current answer dto of model
    */
   public UncorrectedDataException(String message, AnswerDto answerDto) {
      super(message, answerDto);
   }
}
