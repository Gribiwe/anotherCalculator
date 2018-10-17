package modelparser;

import gribiwe.controller.util.HistoryLineParser;
import gribiwe.controller.util.OutputNumberParser;
import gribiwe.model.EnteringNumberImpl;
import gribiwe.model.ModelBrainImpl;
import gribiwe.model.dto.BuildingNumberDto;
import gribiwe.model.dto.FormingSpecialOperationsDto;
import gribiwe.model.exception.OverflowException;
import gribiwe.model.exception.UncorrectedDataException;
import gribiwe.model.exception.ZeroDivideException;
import gribiwe.model.exception.ZeroDivideZeroException;
import gribiwe.model.util.Digit;
import gribiwe.model.util.SimpleOperation;
import gribiwe.model.util.SpecialOperation;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import static gribiwe.model.util.MemoryOperation.ADD;
import static gribiwe.model.util.MemoryOperation.SUBTRACT;

/**
 * Util class for testing parsed model values
 *
 * @author Gribiwe
 */
class TestUtil extends Assert {

   /**
    * exemplar of Main model class
    */
   private ModelBrainImpl mainModel;

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

      HistoryLineParser historyLineParser = new HistoryLineParser();
      OutputNumberParser outputNumberParser = new OutputNumberParser();

      for (String command : actionSequence.split(" ")) {
         try {
            emulate(command);
         } catch (OverflowException e) {
            e.printStackTrace();
         } catch (ZeroDivideZeroException e) {
            e.printStackTrace();
         } catch (ZeroDivideException e) {
            e.printStackTrace();
         } catch (UncorrectedDataException e) {
            e.printStackTrace();
         }
      }

      String outputNumberAtResult;
      if (mainModel.isBuildingNumber()) {
         outputNumberAtResult = outputNumberParser.formatInput(mainModel.getBuildingNumber());
      } else {
         outputNumberAtResult = outputNumberParser.formatResult(mainModel.getResultNumber(), true);
      }
      assertEquals(expectedOutputNumber, outputNumberAtResult);

      String historyLineAtResult;
      historyLineAtResult = historyLineParser.parse(mainModel.getHistoryLineDto());
      if (mainModel.isFormingSpecialOperation()) {
         FormingSpecialOperationsDto formingSpecialOperationsDto = mainModel.getFormingSpecialOperationsDto();
         historyLineAtResult += historyLineParser.parseSpecialOperations(formingSpecialOperationsDto);
      }
      assertEquals(expectedHistory, historyLineAtResult);

      String memoryNumberAtResult;
      memoryNumberAtResult = outputNumberParser.formatResult(mainModel.getMemoryNumber(), true);
      assertEquals(expectedMemory, memoryNumberAtResult);
   }

   /**
    * method for emulating actions
    * provided by string value of section
    *
    * @param section string value of
    *                operation or number to emulate
    * @return answer of model
    */
   private void emulate(String section) throws OverflowException, ZeroDivideZeroException, ZeroDivideException, UncorrectedDataException {
      if (section.equals("/")) {
         mainModel.doOperation(SimpleOperation.DIVIDE);
      } else if (section.equals("*")) {
         mainModel.doOperation(SimpleOperation.MULTIPLY);
      } else if (section.equals("-")) {
         mainModel.doOperation(SimpleOperation.SUBTRACT);
      } else if (section.equals("+")) {
         mainModel.doOperation(SimpleOperation.PLUS);
      } else if (section.equals("=")) {
         mainModel.doEquals();
      } else if (section.equals("√")) {
         mainModel.doSpecialOperation(SpecialOperation.ROOT);
      } else if (section.equals("1/x")) {
         mainModel.doSpecialOperation(SpecialOperation.ONE_DIV_X);
      } else if (section.equals("sqr")) {
         mainModel.doSpecialOperation(SpecialOperation.SQUARE);
      } else if (section.equals("%")) {
         mainModel.doPercent();
      } else if (section.equals("m+")) {
         mainModel.operateMemory(ADD);
      } else if (section.equals("m-")) {
         mainModel.operateMemory(SUBTRACT);
      } else if (section.equals("mr")) {
         mainModel.loadFromMemory();
      } else if (section.equals("mc")) {
         mainModel.clearMemory();
      } else if (section.equals("ce")) {
         mainModel.deleteAllDigits();
      } else if (section.equals("c")) {
         mainModel.deleteAllDigitsAndHistory();
      } else if (section.equals("backspace")) {
         mainModel.deleteDigit();
      } else if (section.equals("n")) {
         mainModel.doNegate();
      } else {
         enterNumber(section);
      }
   }

   /**
    * method for emulating action
    * which contains only one symbol.
    * Expected, it's a numbers or point
    *
    * @param character char value to emulate
    * @return answer of model
    */
   private void emulate(Character character) throws OverflowException, ZeroDivideZeroException, ZeroDivideException, UncorrectedDataException {
      if (character == ',') {
          mainModel.addPoint();
      } else if (character == '0') {
          mainModel.addDigit(Digit.ZERO);
      } else if (character == '1') {
          mainModel.addDigit(Digit.ONE);
      } else if (character == '2') {
          mainModel.addDigit(Digit.TWO);
      } else if (character == '3') {
          mainModel.addDigit(Digit.THREE);
      } else if (character == '4') {
          mainModel.addDigit(Digit.FOUR);
      } else if (character == '5') {
          mainModel.addDigit(Digit.FIVE);
      } else if (character == '6') {
          mainModel.addDigit(Digit.SIX);
      } else if (character == '7') {
          mainModel.addDigit(Digit.SEVEN);
      } else if (character == '8') {
          mainModel.addDigit(Digit.EIGHT);
      } else if (character == '9') {
          mainModel.addDigit(Digit.NINE);
      }
   }

   /**
    * method for entering a number to model
    *
    * @param number string value of number to enter
    */
   private void enterNumber(String number) throws OverflowException, UncorrectedDataException, ZeroDivideZeroException, ZeroDivideException {
      for (int i = 0; i < number.length() - 1; i++) {
         char character = number.charAt(i);
         emulate(character);
      }
      emulate(number.charAt(number.length() - 1));
   }
}