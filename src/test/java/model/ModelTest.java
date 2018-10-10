package model;

import gribiwe.model.ModelBrain;
import gribiwe.model.ModelBrainImpl;
import gribiwe.model.dto.AnswerDTO;
import gribiwe.model.dto.OutputNumberDTO;
import gribiwe.model.exception.CalculatorException;
import gribiwe.model.util.Digit;
import gribiwe.model.util.SimpleOperation;
import gribiwe.model.util.SpecialOperation;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * class for testing model directly
 */
public class ModelTest extends Assert {

   /**
    * exemplar of Main model class
    */
   private static ModelBrain mainModel;

   /**
    * initialization of mainModel
    */
   @BeforeAll
   static void setup() {
      mainModel = new ModelBrainImpl();
   }

   /**
    * testing model
    * 3 * 2 - 5 * 10 - 1 √ have be 3
    */
   @Test
   void testModel() {
      AnswerDTO answerDTO;
      try {
         mainModel.addDigit(Digit.THREE);
         mainModel.doOperation(SimpleOperation.MULTIPLY);
         mainModel.addDigit(Digit.TWO);
         mainModel.doOperation(SimpleOperation.SUBTRACT);
         mainModel.addDigit(Digit.FIVE);
         mainModel.doOperation(SimpleOperation.MULTIPLY);
         mainModel.addDigit(Digit.ONE);
         mainModel.addDigit(Digit.ZERO);
         mainModel.doOperation(SimpleOperation.SUBTRACT);
         mainModel.addDigit(Digit.ONE);
         mainModel.doEquals();
         answerDTO = mainModel.doSpecialOperation(SpecialOperation.ROOT);
      } catch (CalculatorException e) {
         System.out.println(e.getMessage());
         return;
      }
      OutputNumberDTO outputNumberDTO = answerDTO.getOutputNumberDTO();
      assertEquals(0, outputNumberDTO.getValue().compareTo(BigDecimal.valueOf(3)));
   }

   /**
    * testing behaviour of
    * calculator on working with exceptions
    */
   @Test
   void testDividingZero() {
      Digit a = Digit.ZERO;
      Digit b = Digit.ZERO;
      SimpleOperation c = SimpleOperation.DIVIDE;

      AnswerDTO answerDTO;
      try {
         mainModel.addDigit(a);
         mainModel.doOperation(c);
         mainModel.addDigit(b);
         answerDTO = mainModel.doEquals();
         OutputNumberDTO outputNumberDTO = answerDTO.getOutputNumberDTO();
         System.out.println(outputNumberDTO.getValue());
      } catch (CalculatorException e) {
         System.out.println(e.getMessage());
      }
   }
}
