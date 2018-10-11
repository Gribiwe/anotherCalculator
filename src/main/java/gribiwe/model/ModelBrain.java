package gribiwe.model;

import gribiwe.model.dto.AnswerDTO;
import gribiwe.model.exception.CalculatorException;
import gribiwe.model.util.Digit;
import gribiwe.model.util.SimpleOperation;
import gribiwe.model.util.SpecialOperation;

public interface ModelBrain {

   AnswerDTO deleteAllDigitsAndHistory();

   AnswerDTO addDigit(Digit digit) throws CalculatorException;

   AnswerDTO doOperation(SimpleOperation operation) throws CalculatorException;

   AnswerDTO doPercent() throws CalculatorException;

   AnswerDTO doEquals() throws CalculatorException;

   AnswerDTO doSpecialOperation(SpecialOperation operation) throws CalculatorException;

   AnswerDTO addPoint();

   AnswerDTO deleteDigit();

   AnswerDTO deleteAllDigits();

   AnswerDTO doNegate() throws CalculatorException;

   AnswerDTO addMemory();

   AnswerDTO removeMemory();

   AnswerDTO loadFromMemory();

   AnswerDTO clearMemory();
}
