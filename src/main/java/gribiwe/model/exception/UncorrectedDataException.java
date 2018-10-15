package gribiwe.model.exception;

import gribiwe.model.dto.AnswerDTO;

/**
 * CalculatorException class designed to be thrown
 * if was trying to get root of negated value
 *
 * @author Gribiwe
 * @see CalculatorException
 */
public class UncorrectedDataException extends CalculatorException {

   /**
    * creates exception with answerDTO inside
    * @param answerDTO current answer dto of model
    */
   public UncorrectedDataException(String message, AnswerDTO answerDTO) {
      super(message, answerDTO);
   }
}
