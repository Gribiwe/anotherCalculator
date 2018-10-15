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
    * message of this exception
    */
   private static final String DIVIDE_BY_ZERO_EXCEPTION_TEXT = "Деление на ноль невозможно";

   /**
    * creates exception with answerDTO inside
    * @param answerDTO current answer dto of model
    */
   public ZeroDivideException(AnswerDTO answerDTO) {
      super(DIVIDE_BY_ZERO_EXCEPTION_TEXT, answerDTO);
   }
}
