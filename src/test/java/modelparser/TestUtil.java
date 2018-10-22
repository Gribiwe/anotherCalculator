package modelparser;

import gribiwe.controller.util.HistoryLineParser;
import gribiwe.controller.util.OutputNumberParser;
import gribiwe.model.ModelBrain;
import gribiwe.model.dto.BuildingSpecialOperations;
import gribiwe.model.exception.OverflowException;
import gribiwe.model.exception.UncorrectedDataException;
import gribiwe.model.exception.ZeroDivideException;
import gribiwe.model.exception.ZeroDivideZeroException;
import gribiwe.model.util.Digit;
import gribiwe.model.util.SimpleOperation;
import gribiwe.model.util.SpecialOperation;

import java.math.BigDecimal;

import static gribiwe.model.util.MemoryOperation.ADD;
import static gribiwe.model.util.MemoryOperation.SUBTRACT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Util class for testing parsed model values
 *
 * @author Gribiwe
 */
class TestUtil {

   /**
    * exemplar of Main model class
    */
   private ModelBrain mainModel;

   /**
    * default constructor which
    * makes initialization of main model
    */
   TestUtil() {
      mainModel = new ModelBrain();
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
      mainModel = new ModelBrain();
      BigDecimal modelResult = null;
      for (String command : actionSequence.split(" ")) {
         try {
            modelResult = emulate(command);
         } catch (OverflowException | ZeroDivideZeroException | ZeroDivideException | UncorrectedDataException e) {
            fail("unexpected exception");
         }
      }

      String outputNumberAtResult;
      if (mainModel.isBuildingNumber()) {
         outputNumberAtResult = OutputNumberParser.formatInput(mainModel.getBuildingNumber());
      } else {
         outputNumberAtResult = OutputNumberParser.parseResult(modelResult, true);
      }
      assertEquals(expectedOutputNumber, outputNumberAtResult);

      String historyLineAtResult;
      historyLineAtResult = HistoryLineParser.parse(mainModel.getHistoryLineDto());
      if (mainModel.isFormingSpecialOperation()) {
         BuildingSpecialOperations buildingSpecialOperations = mainModel.getFormingSpecialOperationsDto();
         historyLineAtResult += HistoryLineParser.parseSpecialOperations(buildingSpecialOperations);
      }
      assertEquals(expectedHistory, historyLineAtResult);

      String memoryNumberAtResult;
      memoryNumberAtResult = OutputNumberParser.parseResult(mainModel.getMemoryNumber(), true);
      assertEquals(expectedMemory, memoryNumberAtResult);
   }

   /**
    * method for emulating actions
    * provided by string value of section
    *
    * @param section string value of
    *                operation or number to emulate
    * @return result from model after emulating action
    */
   private BigDecimal emulate(String section) throws OverflowException, ZeroDivideZeroException, ZeroDivideException, UncorrectedDataException {
      BigDecimal toReturn;
      if (section.equals("/")) {
         toReturn = mainModel.doOperation(SimpleOperation.DIVIDE);
      } else if (section.equals("*")) {
         toReturn = mainModel.doOperation(SimpleOperation.MULTIPLY);
      } else if (section.equals("-")) {
         toReturn = mainModel.doOperation(SimpleOperation.SUBTRACT);
      } else if (section.equals("+")) {
         toReturn = mainModel.doOperation(SimpleOperation.PLUS);
      } else if (section.equals("=")) {
         toReturn = mainModel.doEquals();
      } else if (section.equals("√")) {
         toReturn = mainModel.doSpecialOperation(SpecialOperation.ROOT);
      } else if (section.equals("1/x")) {
         toReturn = mainModel.doSpecialOperation(SpecialOperation.ONE_DIV_X);
      } else if (section.equals("sqr")) {
         toReturn = mainModel.doSpecialOperation(SpecialOperation.SQUARE);
      } else if (section.equals("%")) {
         toReturn = mainModel.doPercent();
      } else if (section.equals("m+")) {
         toReturn = mainModel.operateMemory(ADD);
      } else if (section.equals("m-")) {
         toReturn = mainModel.operateMemory(SUBTRACT);
      } else if (section.equals("mr")) {
         toReturn = mainModel.loadFromMemory();
      } else if (section.equals("mc")) {
         toReturn = mainModel.clearMemory();
      } else if (section.equals("ce")) {
         toReturn = mainModel.deleteAllDigits();
      } else if (section.equals("c")) {
         toReturn = mainModel.deleteAllDigitsAndHistory();
      } else if (section.equals("backspace")) {
         toReturn = mainModel.deleteDigit();
      } else if (section.equals("n")) {
         toReturn = mainModel.doNegate();
      } else {
         toReturn = enterNumber(section);
      }
      return toReturn;
   }

   /**
    * method for emulating action
    * which contains only one symbol.
    * Expected, it's a numbers or point
    *
    * @param character char value to emulate
    * @return number from model after entering character
    */
   private BigDecimal emulate(Character character) {
      BigDecimal toReturn;
      if (character == ',') {
         toReturn = mainModel.addPoint();
      } else if (character == '0') {
         toReturn = mainModel.addDigit(Digit.ZERO);
      } else if (character == '1') {
         toReturn = mainModel.addDigit(Digit.ONE);
      } else if (character == '2') {
         toReturn = mainModel.addDigit(Digit.TWO);
      } else if (character == '3') {
         toReturn = mainModel.addDigit(Digit.THREE);
      } else if (character == '4') {
         toReturn = mainModel.addDigit(Digit.FOUR);
      } else if (character == '5') {
         toReturn = mainModel.addDigit(Digit.FIVE);
      } else if (character == '6') {
         toReturn = mainModel.addDigit(Digit.SIX);
      } else if (character == '7') {
         toReturn = mainModel.addDigit(Digit.SEVEN);
      } else if (character == '8') {
         toReturn = mainModel.addDigit(Digit.EIGHT);
      } else if (character == '9') {
         toReturn = mainModel.addDigit(Digit.NINE);
      } else {
         System.out.println(character);
         fail("error in syntax");
         toReturn = null;
      }
      return toReturn;
   }

   /**
    * method for entering a number to model
    *
    * @param number string value of number to enter
    * @return number from model after entering number
    */
   private BigDecimal enterNumber(String number) {
      for (int i = 0; i < number.length() - 1; i++) {
         char character = number.charAt(i);
         emulate(character);
      }
      return emulate(number.charAt(number.length() - 1));
   }
}