package gribiwe.model;

import gribiwe.model.dto.AnswerDTO;
import gribiwe.model.exception.CalculatorException;
import gribiwe.model.util.Digit;
import gribiwe.model.util.SimpleOperation;
import gribiwe.model.util.SpecialOperation;

/**
 * some class for logic of calculator
 * // todo write doc
 * // todo document throws block
 *
 * @author Gribiwe
 * @see ModelBrainImpl
 */
public interface ModelBrain {

   /**
    * method for deletion all numbers at result field
    * and clearing history line
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDTO deleteAllDigitsAndHistory();

   /**
    * method for addition new digit to input
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDTO addDigit(Digit digit) throws CalculatorException;

   /**
    * method for adding new operation to history
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDTO doOperation(SimpleOperation operation) throws CalculatorException;

   /**
    * method for adding operation percent
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDTO doPercent() throws CalculatorException;

   /**
    * method for calculation an equals result of calculator
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDTO doEquals() throws CalculatorException;

   /**
    * method for addition new special operation
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDTO doSpecialOperation(SpecialOperation operation) throws CalculatorException;

   /**
    * method for adding point to input number
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDTO addPoint();

   /**
    * method for removing one digit or point from input
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDTO deleteDigit();

   /**
    * method for clearing number line
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDTO deleteAllDigits();

   /**
    * method negating number
    *
    * @return for answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDTO doNegate() throws CalculatorException;

   /**
    * method for adding to memory a number
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDTO addMemory();

   /**
    * method for removing a number from the memory
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDTO removeMemory();

   /**
    * method for loading memory number from memory
    * to result line
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDTO loadFromMemory();

   /**
    * method for cleaning memory
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDTO clearMemory();
}
