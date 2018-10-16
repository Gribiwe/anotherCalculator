package model;

import gribiwe.model.EnteringNumberImpl;
import gribiwe.model.ModelBrain;
import gribiwe.model.ModelBrainImpl;
import gribiwe.model.dto.AnswerDto;
import gribiwe.model.dto.OutputNumberDto;
import gribiwe.model.exception.CalculatorException;
import gribiwe.model.util.Digit;
import gribiwe.model.util.MemoryOperation;
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
      mainModel = new ModelBrainImpl(new EnteringNumberImpl());
   }

   /**
    * testing model
    * 3 * 2 - 5 * 10 - 1 √ have be 3
    */
   @Test
   void testModel() {
      AnswerDto answerDto;
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
         answerDto = mainModel.doSpecialOperation(SpecialOperation.ROOT);
      } catch (CalculatorException e) {
         System.out.println(e.getMessage());
         return;
      }
      OutputNumberDto outputNumberDto = answerDto.getOutputNumberDto();
      assertEquals(0, outputNumberDto.getValue().compareTo(BigDecimal.valueOf(3)));
   }

   @Test
   void testModel2() {
      // TODO: 16.10.2018 work with memory
      // TODO: sqr 100 times
      AnswerDto answerDto;
      try {
         mainModel.addDigit(Digit.THREE);
         mainModel.addDigit(Digit.TWO);
         mainModel.addDigit(Digit.FIVE);
         mainModel.addDigit(Digit.ONE);
         mainModel.addDigit(Digit.ZERO);
         answerDto = mainModel.operateMemory(MemoryOperation.ADD);
         System.out.println("memory is "+ answerDto.getMemoryDto().getMemoryNumber());
         answerDto = mainModel.operateMemory(MemoryOperation.SUBTRACT);
         System.out.println("memory is "+ answerDto.getMemoryDto().getMemoryNumber());
         answerDto = mainModel.operateMemory(MemoryOperation.SUBTRACT);
         System.out.println("memory is "+ answerDto.getMemoryDto().getMemoryNumber());
         mainModel.operateMemory(null);
      } catch (NullPointerException e) {
         System.out.println(e.getMessage());
      }
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

      AnswerDto answerDto;
      try {
         mainModel.addDigit(a);
         mainModel.doOperation(c);
         mainModel.addDigit(b);
         answerDto = mainModel.doEquals();
         OutputNumberDto outputNumberDto = answerDto.getOutputNumberDto();
         System.out.println(outputNumberDto.getValue());
      } catch (CalculatorException e) {
         System.out.println(e.getMessage());
      }
   }
}
