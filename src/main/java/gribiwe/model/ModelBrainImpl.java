package gribiwe.model;

import gribiwe.model.dto.AnswerDTO;
import gribiwe.model.exception.*;
import gribiwe.model.util.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ModelBrainImpl implements ModelBrain {

   private boolean buildingNumber;
   private EnteringNumber enteringNumber;
   private TailSpecialOperationHistory tailSpecialOperationHistory;
   private HistoryLine historyLine;
   private Memory memory;
   private ResultNumber resultNumber;

   private static final String DIGIT_IS_NULL_EXCEPTION_TEXT = "Digit is formAnswer(). I cant make calculations with formAnswer()";
   private static final String OPERATION_IS_NULL_EXCEPTION_TEXT = "Operation is formAnswer(). I cant make calculations using formAnswer() as operation";

   public ModelBrainImpl() {
      buildingNumber = false;
      enteringNumber = new EnteringNumberImpl();
      tailSpecialOperationHistory = new TailSpecialOperationHistory();
      historyLine = new HistoryLine();
      memory = new Memory();
      resultNumber = new ResultNumber();
   }

   @Override
   public AnswerDTO deleteDigit() {
      if (buildingNumber) {
         enteringNumber.removeSymbol();
      }
      return formAnswer();
   }

   @Override
   public AnswerDTO deleteAllDigits() {
      startBuildingNumber();
      enteringNumber.removeAllSymbols();

      return formAnswer();
   }

   @Override
   public AnswerDTO deleteAllDigitsAndHistory() {
      buildingNumber = true;
      enteringNumber = new EnteringNumberImpl();
      tailSpecialOperationHistory = new TailSpecialOperationHistory();
      historyLine = new HistoryLine();
      return formAnswer();
   }

   @Override
   public AnswerDTO addDigit(Digit digit) throws CalculatorException {
      verifyNull(digit, DIGIT_IS_NULL_EXCEPTION_TEXT);
      startBuildingNumber();
      enteringNumber.addDigit(digit);
      return formAnswer();
   }

   @Override
   public AnswerDTO addPoint() {
      startBuildingNumber();
      enteringNumber.addPoint();
      return formAnswer();
   }

   @Override
   public AnswerDTO doOperation(SimpleOperation operation) throws CalculatorException {
      verifyNull(operation, OPERATION_IS_NULL_EXCEPTION_TEXT);

      if (tailSpecialOperationHistory.isProcessing()) {
         doOperationAfterSpecialOperation(operation);
      } else if (buildingNumber || resultNumber.getStatus().equals(ResultNumberStatus.EQUALS_RESULT)) {
         doOperationWithBuiltOrEqualsNumber(operation);
      } else if (resultNumber.getStatus().equals(ResultNumberStatus.HISTORY_RESULT)) {
         historyLine.changeLastOperation(operation);
      } else if (resultNumber.getStatus().equals(ResultNumberStatus.LOADED_FROM_MEMORY)) {
         doOperationWithBuiltOrEqualsNumber(operation);
      } else if (resultNumber.getStatus().equals(ResultNumberStatus.BLOCKED_BY_MEMORY)) {
         if (historyLine.getLastOperation() == null) {
            doOperationWithBuiltOrEqualsNumber(operation);
         } else {
            historyLine.changeLastOperation(operation);
         }
      }
      verifyOverflow();
      return formAnswer();
   }

   @Override
   public AnswerDTO doPercent() throws CalculatorException {
      BigDecimal numberOfPercents;
      if (buildingNumber) {
         numberOfPercents = stopBuildingAndGetNumber();
      } else {
         numberOfPercents = resultNumber.getNumber();
      }
      BigDecimal previousNumber;
      previousNumber = historyLine.calculate();
      CalculatorMath calculatorMath = new CalculatorMath();
      BigDecimal result = calculatorMath.percent(previousNumber, numberOfPercents);
      resultNumber.loadAsEqualsResult(result);
      tailSpecialOperationHistory.clear();
      tailSpecialOperationHistory.initNumber(result);
      verifyOverflow();
      return formAnswer();
   }

   @Override
   public AnswerDTO doEquals() throws CalculatorException {
      if (tailSpecialOperationHistory.isProcessing()) {
         doEqualsAfterSpecialOperation();
      } else if (buildingNumber || !resultNumber.getStatus().equals(ResultNumberStatus.EQUALS_RESULT)) {
         doEqualsWithHistoryAndBuiltNumber();
      } else if (resultNumber.getStatus().equals(ResultNumberStatus.EQUALS_RESULT)) {
         doEqualsAfterEquals();
      }
      verifyOverflow();
      return formAnswer();
   }

   @Override
   public AnswerDTO doSpecialOperation(SpecialOperation operation) throws CalculatorException {
      if (tailSpecialOperationHistory.isProcessing()) {
         doNextSpecialOperation(operation);
      } else {
         doFirstSpecialOperation(operation);
      }
      BigDecimal tailSpecialOperationHistoryResult;
      tailSpecialOperationHistoryResult = tailSpecialOperationHistory.calculate();
      resultNumber.loadAsEqualsResult(tailSpecialOperationHistoryResult);
      verifyOverflow();
      return formAnswer();
   }

   @Override
   public AnswerDTO doNegate() throws CalculatorException {
      if (buildingNumber) {
         enteringNumber.negate();
      } else {
         doSpecialOperation(SpecialOperation.NEGATE);
      }
      return formAnswer();
   }

   @Override
   public AnswerDTO addMemory() {
      memory.setEnable(true);
      BigDecimal numberToMemory;
      numberToMemory = getNumberFromBuildingOrResultNumber();
      memory.add(numberToMemory);
      stopBuildingAndGetNumber();
      resultNumber.loadAsMemoryNumbersWithBlock(numberToMemory);
      return formAnswer();
   }

   @Override
   public AnswerDTO removeMemory() {
      memory.setEnable(true);
      BigDecimal numberToMemory;
      numberToMemory = getNumberFromBuildingOrResultNumber();
      memory.remove(numberToMemory);
      stopBuildingAndGetNumber();
      resultNumber.loadAsMemoryNumbersWithBlock(numberToMemory);
      return formAnswer();
   }

   @Override
   public AnswerDTO loadFromMemory() {
      BigDecimal numberFromMemory;
      numberFromMemory = memory.getNumber();
      stopBuildingAndGetNumber();
      resultNumber.loadAsMemoryNumber(numberFromMemory);
      return formAnswer();
   }

   @Override
   public AnswerDTO clearMemory() {
      memory.clear();
      BigDecimal numberToMakeEquals;
      numberToMakeEquals = getNumberFromBuildingOrResultNumber();
      stopBuildingAndGetNumber();
      resultNumber.loadAsMemoryNumbersWithBlock(numberToMakeEquals);
      return formAnswer();
   }

   private BigDecimal getNumberFromBuildingOrResultNumber() {
      if (buildingNumber) {
         return stopBuildingAndGetNumber();
      } else {
         return resultNumber.getNumber();
      }
   }

   private BigDecimal stopBuildingAndGetNumber() {
      buildingNumber = false;
      BigDecimal builtNumber;
      builtNumber = enteringNumber.getNumber();
      enteringNumber = new EnteringNumberImpl();
      return builtNumber;
   }

   private void startBuildingNumber() {
      if (!buildingNumber) {
         tailSpecialOperationHistory.clear();
         buildingNumber = true;
      }
   }

   private void doOperationWithBuiltOrEqualsNumber(SimpleOperation operation) throws ZeroDivideZeroException, ZeroDivideException {
      BigDecimal numberToHistory;
      if (buildingNumber) {
         numberToHistory = stopBuildingAndGetNumber();
      } else {
         numberToHistory = resultNumber.getNumber();
      }
      verifyZeroDividingByZero(numberToHistory, operation);
      verifyDividingByZero(numberToHistory, operation);
      historyLine.add(numberToHistory, operation, true);
      BigDecimal historyResult;
      historyResult = historyLine.calculate();
      resultNumber.loadAsHistoryResult(historyResult);
   }

   private void doOperationAfterSpecialOperation(SimpleOperation operation) {
      historyLine.add(tailSpecialOperationHistory.getNumber(), operation, true);
      ArrayList<SpecialOperation> tailSpecialOperations;
      tailSpecialOperations = tailSpecialOperationHistory.getOperations();
      historyLine.uploadSpecialOperations(tailSpecialOperations);
      tailSpecialOperationHistory.clear();
      BigDecimal historyResult;
      historyResult = historyLine.calculate();
      resultNumber.loadAsHistoryResult(historyResult);
   }

   private void doEqualsAfterEquals() {
      historyLine.add(resultNumber.getNumber(), historyLine.getSavedOperation(), false);
      historyLine.add(historyLine.getSavedResult(), SimpleOperation.NOTHING, false);
      BigDecimal equalsResult;
      equalsResult = historyLine.calculate();
      historyLine.clearHistory();
      resultNumber.loadAsEqualsResult(equalsResult);
   }

   private void doEqualsWithHistoryAndBuiltNumber() throws ZeroDivideZeroException, ZeroDivideException {
      BigDecimal numberToCalculate;

      if (buildingNumber) {
         numberToCalculate = stopBuildingAndGetNumber();
         verifyZeroDividingByZero(numberToCalculate);
         verifyDividingByZero(numberToCalculate);
      } else {
         numberToCalculate = resultNumber.getNumber();
      }

      historyLine.setSavedResult(numberToCalculate);
      historyLine.add(numberToCalculate, SimpleOperation.NOTHING, false);

      BigDecimal equalsResult = historyLine.calculate();
      resultNumber.loadAsEqualsResult(equalsResult);

      historyLine.clearHistory();
   }

   private void doEqualsAfterSpecialOperation() {
      BigDecimal specialOperationsResult;
      specialOperationsResult = tailSpecialOperationHistory.calculate();
      historyLine.add(tailSpecialOperationHistory.getNumber(), SimpleOperation.NOTHING, false);
      historyLine.setSavedResult(specialOperationsResult);

      ArrayList<SpecialOperation> tailSpecialOperations;
      tailSpecialOperations = tailSpecialOperationHistory.getOperations();
      historyLine.uploadSpecialOperations(tailSpecialOperations);

      tailSpecialOperationHistory.clear();
      BigDecimal equalsResult;
      equalsResult = historyLine.calculate();
      historyLine.clearHistory();
      resultNumber.loadAsEqualsResult(equalsResult);
   }

   private void doNextSpecialOperation(SpecialOperation operation) throws ZeroDivideException, UncorrectedDataException {
      verifyOneDivX(operation);
      verifyRootOfNegatedNumberInNextSpecialOperation(operation);
      tailSpecialOperationHistory.addOperation(operation);
   }

   private void doFirstSpecialOperation(SpecialOperation operation) throws UncorrectedDataException, ZeroDivideException {
      BigDecimal numberForSpecialOperations;
      if (buildingNumber) {
         numberForSpecialOperations = stopBuildingAndGetNumber();
      } else {
         numberForSpecialOperations = resultNumber.getNumber();
      }
      verifyRootOfNegatedNumberInFirstSpecialOperation(numberForSpecialOperations, operation);

      tailSpecialOperationHistory.initNumber(numberForSpecialOperations);
      verifyOneDivX(operation);
      tailSpecialOperationHistory.addOperation(operation);
   }

   private void verifyOverflow() throws OverflowException {
      String pattern = "0.###############E0";
      DecimalFormat myFormatter = new DecimalFormat(pattern);
      String output = myFormatter.format(resultNumber.getNumber());
      long eshka = Long.parseLong(output.substring(output.indexOf("E") + 1));
      if (eshka >= 10000 || eshka <= -10000) {
         throw new OverflowException(formAnswer());
      }
   }

   private void verifyZeroDividingByZero(BigDecimal divisor) throws ZeroDivideZeroException {
      verifyZeroDividingByZero(divisor, null);
   }

   private void verifyDividingByZero(BigDecimal divisor) throws ZeroDivideException {
      verifyDividingByZero(divisor, null);
   }

   private void verifyZeroDividingByZero(BigDecimal divisor, SimpleOperation operation) throws ZeroDivideZeroException {
      BigDecimal dividend = historyLine.calculate();
      SimpleOperation lastOperation = historyLine.getLastOperation();

      if (lastOperation != null && isZero(dividend) &&
              lastOperation.equals(SimpleOperation.DIVIDE) && isZero(divisor)) {
         if (operation != null) {
            historyLine.add(divisor, operation, false);
         }
         throw new ZeroDivideZeroException(formAnswer());
      }
   }

   private void verifyDividingByZero(BigDecimal divisor, SimpleOperation operation) throws ZeroDivideException {
      SimpleOperation lastOperation = historyLine.getLastOperation();

      if (lastOperation != null && lastOperation.equals(SimpleOperation.DIVIDE) && isZero(divisor)) {
         if (operation != null) {
            historyLine.add(divisor, operation, false);
         }
         throw new ZeroDivideException(formAnswer());
      }
   }

   private void verifyNull(Object object, String message) throws CalculatorException {
      if (object == formAnswer()) {
         throw new CalculatorException(message, formAnswer());
      }
   }

   private void verifyOneDivX(SpecialOperation operation) throws ZeroDivideException {
      if (isZero(tailSpecialOperationHistory.calculate()) && operation.equals(SpecialOperation.ONEDIVX)) {
         tailSpecialOperationHistory.addOperation(operation);
         throw new ZeroDivideException(formAnswer());
      }
   }

   private void verifyRootOfNegatedNumberInNextSpecialOperation(SpecialOperation operation) throws UncorrectedDataException {
      if (isLessThenZero(tailSpecialOperationHistory.calculate()) && operation.equals(SpecialOperation.ROOT)) {
         tailSpecialOperationHistory.addOperation(operation);
         throw new UncorrectedDataException(formAnswer());
      }
   }

   private void verifyRootOfNegatedNumberInFirstSpecialOperation(BigDecimal number, SpecialOperation operation) throws UncorrectedDataException {
      if (isLessThenZero(number) && operation.equals(SpecialOperation.ROOT)) {
         tailSpecialOperationHistory.initNumber(number);
         tailSpecialOperationHistory.addOperation(operation);
         throw new UncorrectedDataException(formAnswer());
      }
   }

   private boolean isZero(BigDecimal number) {
      return number.compareTo(BigDecimal.ZERO) == 0;
   }

   private boolean isLessThenZero(BigDecimal number) {
      return number.compareTo(BigDecimal.ZERO) < 0;
   }

   private AnswerDTO formAnswer() {
      if (buildingNumber) {
         return new AnswerDTO(enteringNumber.getNumberDTO(), historyLine.getHistoryLineDTO(), tailSpecialOperationHistory.getDTO(), memory.getDTO());
      } else {
         return new AnswerDTO(resultNumber.getNumberDTO(), historyLine.getHistoryLineDTO(), tailSpecialOperationHistory.getDTO(), memory.getDTO());
      }
   }
}
