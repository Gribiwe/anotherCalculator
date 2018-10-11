package gribiwe.model;

import gribiwe.model.dto.OutputNumberDTO;
import gribiwe.model.util.ResultNumberStatus;

import java.math.BigDecimal;

public class ResultNumber {

   private BigDecimal number;
   private ResultNumberStatus status;

   public ResultNumber() {
      number = BigDecimal.ZERO;
      status = ResultNumberStatus.EQUALS_RESULT;
   }

   public void loadAsHistoryResult(BigDecimal historyResult) {
      number = historyResult;
      status = ResultNumberStatus.HISTORY_RESULT;
   }

   public void setStatus(ResultNumberStatus status) {
      this.status = status;
   }

   public void loadAsMemoryNumbersWithBlock(BigDecimal memoryNumber) {
      number = memoryNumber;
      status = ResultNumberStatus.BLOCKED_BY_MEMORY;
   }

   public void loadAsMemoryNumber(BigDecimal memoryNumber) {
      number = memoryNumber;
      status = ResultNumberStatus.LOADED_FROM_MEMORY;
   }


   public void loadAsEqualsResult(BigDecimal equalsResult) {
      number = equalsResult;
      status = ResultNumberStatus.EQUALS_RESULT;
   }

   public OutputNumberDTO getNumberDTO() {
      return new OutputNumberDTO(number);
   }

   public BigDecimal getNumber() {
      return number;
   }

   public ResultNumberStatus getStatus() {
      return status;
   }
}
