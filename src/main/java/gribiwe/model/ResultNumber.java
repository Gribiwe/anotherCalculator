package gribiwe.model;

import gribiwe.model.dto.OutputNumberDTO;
import gribiwe.model.util.ResultNumberStatus;

import java.math.BigDecimal;

public class ResultNumber {

   private BigDecimal number;
   private ResultNumberStatus status;

   ResultNumber() {
      number = BigDecimal.ZERO;
      status = ResultNumberStatus.EQUALS_RESULT;
   }

   void loadAsHistoryResult(BigDecimal historyResult) {
      number = historyResult;
      status = ResultNumberStatus.HISTORY_RESULT;
   }

   void loadAsMemoryNumbersWithBlock(BigDecimal memoryNumber) {
      number = memoryNumber;
      status = ResultNumberStatus.BLOCKED_BY_MEMORY;
   }

   void loadAsMemoryNumber(BigDecimal memoryNumber) {
      number = memoryNumber;
      status = ResultNumberStatus.LOADED_FROM_MEMORY;
   }


   void loadAsEqualsResult(BigDecimal equalsResult) {
      number = equalsResult;
      status = ResultNumberStatus.EQUALS_RESULT;
   }

   OutputNumberDTO getNumberDTO() {
      return new OutputNumberDTO(number);
   }

   public BigDecimal getNumber() {
      return number;
   }

   ResultNumberStatus getStatus() {
      return status;
   }
}
