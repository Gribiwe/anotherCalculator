package gribiwe.model;

import gribiwe.model.dto.AnswerDTO;
import gribiwe.model.exception.CalculatorException;
import gribiwe.model.exception.ZeroDivideException;
import gribiwe.model.exception.ZeroDivideZeroException;
import gribiwe.model.util.Digit;
import gribiwe.model.util.ResultNumberStatus;
import gribiwe.model.util.SimpleOperation;
import gribiwe.model.util.SpecialOperation;

import java.math.BigDecimal;

public class ModelBrainImpl implements ModelBrain {

   private boolean buildingNumber;
   private EnteringNumber enteringNumber;
   private LastSpecialOperationHistory lastSpecialOperationHistory;
   private HistoryLine historyLine;
   private Memory memory;
   private ResultNumber resultNumber;

   private static final String DIGIT_IS_NULL_EXCEPTION_TEXT = "Digit is formAnswer(). I cant make calculations with formAnswer()";
   private static final String OPERATION_IS_NULL_EXCEPTION_TEXT = "Operation is formAnswer(). I cant make calculations using formAnswer() as operation";

   public ModelBrainImpl() {
      buildingNumber = false;
      enteringNumber = new EnteringNumberImpl();
      lastSpecialOperationHistory = new LastSpecialOperationHistory();
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
      buildingNumber = true;
      enteringNumber.removeAllSymbols();
      return formAnswer();
   }

   @Override
   public AnswerDTO deleteAllDigitsAndHistory() {
      buildingNumber = true;
      enteringNumber = new EnteringNumberImpl();
      lastSpecialOperationHistory = new LastSpecialOperationHistory();
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

   private void startBuildingNumber() {
      if (!buildingNumber) {
         lastSpecialOperationHistory.clear();
         buildingNumber = true;
      }
   }

   @Override
   public AnswerDTO doOperation(SimpleOperation operation) throws CalculatorException {
      verifyNull(operation, OPERATION_IS_NULL_EXCEPTION_TEXT);

      if (lastSpecialOperationHistory.isProcessing()) {
         doOperationAfterSpecialOperation();
      } else if (buildingNumber || resultNumber.getStatus().equals(ResultNumberStatus.EQUALS_RESULT)) {
         doOperationWithBuiltOrEqualsNumber(operation);
      } else if (resultNumber.getStatus().equals(ResultNumberStatus.HISTORY_RESULT)) {
         historyLine.changeLastOperation(operation);
      }
      return formAnswer();
   }

   @Override
   public AnswerDTO doEquals() throws CalculatorException {
      if (lastSpecialOperationHistory.isProcessing()) {
         doEqualsAfterSpecialOperation();
      } else if (resultNumber.getStatus().equals(ResultNumberStatus.EQUALS_RESULT)) {
         doEqualsAfterEquals();
      } else {
         doEqualsWithHistoryAndBuiltNumber();
      }
      return formAnswer();
   }

   private void doOperationAfterSpecialOperation() {

   }

   private void doEqualsAfterSpecialOperation() {

   }

   @Override
   public AnswerDTO doSpecialOperation(SpecialOperation operation) throws CalculatorException {

      buildingNumber = false;
      return formAnswer();
   }

   @Override
   public AnswerDTO doNegate() throws CalculatorException {
      if (buildingNumber) {
         enteringNumber.negate();
      }
      return formAnswer();
   }

   @Override
   public AnswerDTO addMemory() {
      return formAnswer();
   }

   @Override
   public AnswerDTO removeMemory() {
      return formAnswer();
   }

   @Override
   public AnswerDTO loadFromMemory() {
      return formAnswer();
   }

   @Override
   public AnswerDTO clearMemory() {
      return formAnswer();
   }

   @Override
   public AnswerDTO doAddOrRemoveMemory(boolean add) {
      return formAnswer();
   }

   private BigDecimal stopBuildingAndGetNumber() {
      buildingNumber = false;
      BigDecimal builtNumber;
      builtNumber = enteringNumber.getNumber();
      enteringNumber = new EnteringNumberImpl();
      return builtNumber;
   }

   private void doOperationWithBuiltOrEqualsNumber(SimpleOperation operation) throws ZeroDivideZeroException, ZeroDivideException {
      BigDecimal numberToAdd;
      if (buildingNumber) {
         numberToAdd = stopBuildingAndGetNumber();
      } else {
         numberToAdd = resultNumber.getNumber();
      }
      verifyZeroDividingByZero(numberToAdd, operation);
      verifyDividingByZero(numberToAdd, operation);
      historyLine.add(numberToAdd, operation, true);
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

   private void doEqualsWithHistoryAndBuiltNumber() throws ZeroDivideZeroException {
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

   private void verifyZeroDividingByZero(BigDecimal divisor) throws ZeroDivideZeroException {
      verifyZeroDividingByZero(divisor, null);
   }

   private void verifyDividingByZero(BigDecimal divisor) throws ZeroDivideZeroException {
      verifyZeroDividingByZero(divisor, null);
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

   private boolean isZero(BigDecimal number) {
      return number.compareTo(BigDecimal.ZERO) == 0;
   }

   private AnswerDTO formAnswer() {
      if (buildingNumber) {
         return new AnswerDTO(enteringNumber.getNumberDTO(), historyLine.getHistoryLineDTO(), lastSpecialOperationHistory.getDTO(), memory.getDTO());
      } else {
         return new AnswerDTO(resultNumber.getNumberDTO(), historyLine.getHistoryLineDTO(), lastSpecialOperationHistory.getDTO(), memory.getDTO());
      }
   }
}
