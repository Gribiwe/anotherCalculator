package gribiwe.model;

import gribiwe.model.dto.AnswerDTO;
import gribiwe.model.exception.CalculatorException;
import gribiwe.model.exception.ZeroDivideException;
import gribiwe.model.exception.ZeroDivideZeroException;
import gribiwe.model.util.Digit;
import gribiwe.model.util.MemoryOperation;
import gribiwe.model.util.SimpleOperation;
import gribiwe.model.util.SpecialOperation;

/**
 * interface of main model class for
 * manipulation of controller with data
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
    * @throws CalculatorException if digit is null
    */
   AnswerDTO addDigit(Digit digit) throws CalculatorException;

   /**
    * method for adding new operation to history
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    * @throws CalculatorException if operation is null
    *                             or was trying to divide number (not zero) by zero
    *                             or was trying to divide zero by zero
    */
   AnswerDTO doOperation(SimpleOperation operation) throws CalculatorException;

   /**
    * method for adding operation percent
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDTO doPercent();

   /**
    * method for calculation an equals result of calculator
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    * @throws CalculatorException if was trying to divide number (not zero) by zero
    *                             or was trying to divide zero by zero
    */
   AnswerDTO doEquals() throws CalculatorException;

   /**
    * method for addition new special operation
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    * @throws CalculatorException if operation is null
    *                             or was trying to divide number (not zero) by zero
    *                             or was trying to calculate root of negated value
    */
   AnswerDTO doSpecialOperation(SpecialOperation operation) throws CalculatorException;

   /**
    * method negating number
    *
    * @return for answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDTO doNegate();

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
    * method for adding or subtracting memory number
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    * @throws CalculatorException if operation is null
    */
   AnswerDTO operateMemory(MemoryOperation operation) throws CalculatorException;

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
