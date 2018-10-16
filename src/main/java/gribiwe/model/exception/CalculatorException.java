package gribiwe.model.exception;

import gribiwe.model.dto.AnswerDto;

/**
 * exception class designed to be thrown
 * while working with calculator
 *
 * @author Gribiwe
 * @see Exception
 */
public class CalculatorException extends Exception {

   /**
    * answer for checking
    * current values of model
    */
   private AnswerDto answerDto;

   /**
    * creates new Calculator exception
    *
    * @param message   message of exception
    * @param answerDto current answer dto of model
    */
   public CalculatorException(String message, AnswerDto answerDto) {
      super(message);
      this.answerDto = answerDto;
   }

   public AnswerDto getAnswerDto() {
      return answerDto;
   }
}
