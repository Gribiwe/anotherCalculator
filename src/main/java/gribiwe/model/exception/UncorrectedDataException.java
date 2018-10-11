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
    * message of this exception
    */
   private static final String INCORRECT_DATA_EXCEPTION_TEXT = "Введены неверные данные";

   /**
    * creates exception with answerDTO inside
    * @param answerDTO current answer dto of model
    */
   public UncorrectedDataException(AnswerDTO answerDTO) {
      super(INCORRECT_DATA_EXCEPTION_TEXT, answerDTO);
   }
}
