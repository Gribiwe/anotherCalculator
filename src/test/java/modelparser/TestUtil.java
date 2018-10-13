package modelparser;

import gribiwe.controller.util.LastSpecialOperationStoryParser;
import gribiwe.controller.util.HistoryLineParser;
import gribiwe.controller.util.OutputNumberParser;
import gribiwe.model.ModelBrain;
import gribiwe.model.ModelBrainImpl;
import gribiwe.model.dto.AnswerDTO;
import gribiwe.model.dto.EnteredNumberDTO;
import gribiwe.model.dto.OutputNumberDTO;
import gribiwe.model.exception.CalculatorException;
import gribiwe.model.util.Digit;
import gribiwe.model.util.SimpleOperation;
import gribiwe.model.util.SpecialOperation;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import static gribiwe.model.util.MemoryOperation.*;

/**
 * Util class for testing parsed model values
 *
 * @author Gribiwe
 */
class TestUtil extends Assert {

   /**
    * exemplar of Main model class
    */
   private ModelBrain mainModel;

   /**
    * default constructor which
    * makes initialization of main model
    */
   TestUtil() {
      mainModel = new ModelBrainImpl();
   }

   /**
    * test method for testing parsed model value
    *
    * @param actionSequence       string value of sequence
    *                             which will be emulated
    * @param expectedOutputNumber expected value of
    *                             parsed model value
    * @param expectedHistory      value of expected
    *                             history line value
    */
   void doTest(String actionSequence, String expectedOutputNumber, String expectedHistory) {
      doTest(actionSequence, expectedOutputNumber, expectedHistory, "0");
   }

   /**
    * method for testing of thrown
    * exception after emulating actions
    *
    * @param actionSequence string value of sequence
    *                       which will be emulated
    * @param expected       class of expected thrown exception
    * @param <T>            any throwable object
    */
   <T extends Throwable> void doExceptionTest(String actionSequence, Class<T> expected) {
      mainModel = new ModelBrainImpl();
      Assertions.assertThrows(expected, () -> {
         for (String command : actionSequence.split(" ")) {
            emulate(command);
         }
      });
   }

   /**
    * test method for testing parsed model value
    *
    * @param actionSequence       string value of sequence
    *                             which will be emulated
    * @param expectedOutputNumber expected value of
    *                             parsed model value
    * @param expectedHistory      value of expected history line
    * @param expectedMemory       value of expected memory value
    */
   void doTest(String actionSequence, String expectedOutputNumber, String expectedHistory, String expectedMemory) {
      mainModel = new ModelBrainImpl();
      AnswerDTO answerDTO = null;

      HistoryLineParser historyLineParser = new HistoryLineParser();
      LastSpecialOperationStoryParser lastSpecialOperationStoryParser = new LastSpecialOperationStoryParser();

      for (String command : actionSequence.split(" ")) {
         try {
            answerDTO = emulate(command);
         } catch (CalculatorException e) {
            fail("Unexpected exception in test: \n");
            e.printStackTrace();
         }
      }

      assertNotNull("mistake at action sequence", answerDTO);
      OutputNumberParser outputNumberParser = new OutputNumberParser();

      OutputNumberDTO outputNumberDTO = answerDTO.getOutputNumberDTO();
      String outputNumberAtResult;
      if (outputNumberDTO.getClass().equals(EnteredNumberDTO.class)) {
         outputNumberAtResult = outputNumberParser.formatInput((EnteredNumberDTO) answerDTO.getOutputNumberDTO());
      } else {
         outputNumberAtResult = outputNumberParser.formatResult(outputNumberDTO.getValue(), true);
      }
      assertEquals(expectedOutputNumber, outputNumberAtResult);

      String historyLineAtResult;
      historyLineAtResult = historyLineParser.parse(answerDTO.getHistoryLineDTO());
      historyLineAtResult += lastSpecialOperationStoryParser.parse(answerDTO.getLastSpecialOperationHistoryDTO());
      assertEquals(expectedHistory, historyLineAtResult);

      String memoryNumberAtResult;
      memoryNumberAtResult = outputNumberParser.formatResult(answerDTO.getMemoryDTO().getMemoryNumber(), true);
      assertEquals(expectedMemory, memoryNumberAtResult);
   }

   /**
    * method for emulating actions
    * provided by string value of section
    *
    * @param section string value of
    *                operation or number to emulate
    * @return answer of model
    * @throws CalculatorException if was overflow, taking root
    *                             of negative value, dividing by
    *                             zero or zero was divided by zero
    */
   private AnswerDTO emulate(String section) throws CalculatorException {
      if (section.equals("/")) {
         return mainModel.doOperation(SimpleOperation.DIVIDE);
      } else if (section.equals("*")) {
         return mainModel.doOperation(SimpleOperation.MULTIPLY);
      } else if (section.equals("-")) {
         return mainModel.doOperation(SimpleOperation.SUBTRACT);
      } else if (section.equals("+")) {
         return mainModel.doOperation(SimpleOperation.PLUS);
      } else if (section.equals("=")) {
         return mainModel.doEquals();
      } else if (section.equals("√")) {
         return mainModel.doSpecialOperation(SpecialOperation.ROOT);
      } else if (section.equals("1/x")) {
         return mainModel.doSpecialOperation(SpecialOperation.ONE_DIV_X);
      } else if (section.equals("sqr")) {
         return mainModel.doSpecialOperation(SpecialOperation.SQUARE);
      } else if (section.equals("%")) {
         return mainModel.doPercent();
      } else if (section.equals("m+")) {
         return mainModel.operateMemory(ADD);
      } else if (section.equals("m-")) {
         return mainModel.operateMemory(SUBTRACT);
      } else if (section.equals("mr")) {
         return mainModel.loadFromMemory();
      } else if (section.equals("mc")) {
         return mainModel.clearMemory();
      } else if (section.equals("ce")) {
         return mainModel.deleteAllDigits();
      } else if (section.equals("c")) {
         return mainModel.deleteAllDigitsAndHistory();
      } else if (section.equals("backspace")) {
         return mainModel.deleteDigit();
      } else if (section.equals("n")) {
         return mainModel.doNegate();
      } else {
         return enterNumber(section);
      }
   }

   /**
    * method for emulating action
    * which contains only one symbol.
    * Expected, it's a numbers or point
    *
    * @param character char value to emulate
    * @return answer of model
    * @throws CalculatorException if was overflow, taking root
    *                             of negative value, dividing by
    *                             zero or zero was divided by zero
    */
   private AnswerDTO emulate(Character character) throws CalculatorException {
      if (character == ',') {
         return mainModel.addPoint();
      } else if (character == '0') {
         return mainModel.addDigit(Digit.ZERO);
      } else if (character == '1') {
         return mainModel.addDigit(Digit.ONE);
      } else if (character == '2') {
         return mainModel.addDigit(Digit.TWO);
      } else if (character == '3') {
         return mainModel.addDigit(Digit.THREE);
      } else if (character == '4') {
         return mainModel.addDigit(Digit.FOUR);
      } else if (character == '5') {
         return mainModel.addDigit(Digit.FIVE);
      } else if (character == '6') {
         return mainModel.addDigit(Digit.SIX);
      } else if (character == '7') {
         return mainModel.addDigit(Digit.SEVEN);
      } else if (character == '8') {
         return mainModel.addDigit(Digit.EIGHT);
      } else if (character == '9') {
         return mainModel.addDigit(Digit.NINE);
      } else {
         return null;
      }
   }

   /**
    * method for entering a number to model
    *
    * @param number string value of number to enter
    * @return answer of model
    * @throws CalculatorException if was overflow, taking root
    *                             of negative value, dividing by
    *                             zero or zero was divided by zero
    */
   private AnswerDTO enterNumber(String number) throws CalculatorException {
      for (int i = 0; i < number.length() - 1; i++) {
         char character = number.charAt(i);
         emulate(character);
      }
      return emulate(number.charAt(number.length() - 1));
   }
}