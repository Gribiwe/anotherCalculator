package gribiwe.model;

import gribiwe.model.dto.AnswerDto;
import gribiwe.model.exception.CalculatorException;
import gribiwe.model.exception.OverflowException;
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
   AnswerDto deleteAllDigitsAndHistory();

   /**
    * method for addition new digit to input
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    * @throws NullPointerException if digit is null
    */
   AnswerDto addDigit(Digit digit) throws NullPointerException;

   /**
    * method for adding new operation to history
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    * @throws CalculatorException  if was trying to divide number (not zero) by zero
    *                              or was trying to divide zero by zero
    * @throws NullPointerException if operation is null
    */
   AnswerDto doOperation(SimpleOperation operation) throws CalculatorException, NullPointerException;

   /**
    * method for adding operation percent
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDto doPercent() throws OverflowException;

   /**
    * method for calculation an equals result of calculator
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    * @throws CalculatorException if was trying to divide number (not zero) by zero
    *                             or was trying to divide zero by zero
    */
   AnswerDto doEquals() throws CalculatorException;

   /**
    * method for addition new special operation
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    * @throws CalculatorException  or was trying to divide number (not zero) by zero
    *                              or was trying to calculate root of negated value
    * @throws NullPointerException if operation is null
    */
   AnswerDto doSpecialOperation(SpecialOperation operation) throws CalculatorException, NullPointerException;

   /**
    * method negating number
    *
    * @return for answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDto doNegate();

   /**
    * method for adding point to input number
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDto addPoint();

   /**
    * method for removing one digit or point from input
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDto deleteDigit();

   /**
    * method for clearing number line
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDto deleteAllDigits();

   /**
    * method for adding or subtracting memory number
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    * @throws NullPointerException if operation is null
    */
   AnswerDto operateMemory(MemoryOperation operation) throws NullPointerException;

   /**
    * method for loading memory number from memory
    * to result line
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDto loadFromMemory() throws OverflowException;

   /**
    * method for cleaning memory
    *
    * @return answer from model with
    * info about number at number field, history, memory.
    */
   AnswerDto clearMemory();

   AnswerDto clearModel();
}
