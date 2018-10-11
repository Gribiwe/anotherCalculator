package gribiwe.model.exception;

import gribiwe.model.dto.AnswerDTO;
import gribiwe.model.dto.LastSpecialOperationHistoryDTO;
import gribiwe.model.dto.HistoryLineDTO;
import gribiwe.model.dto.OutputNumberDTO;

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
   private AnswerDTO answerDTO;

   /**
    * creates new Calculator exception
    * @param message message of exception
    * @param answerDTO current answer dto of model
    */
   public CalculatorException(String message, AnswerDTO answerDTO) {
      super(message);
      this.answerDTO = answerDTO;
   }

   public AnswerDTO getAnswerDTO() {
      return answerDTO;
   }
}
