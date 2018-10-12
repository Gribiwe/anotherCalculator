package gribiwe.model;

import gribiwe.model.dto.OutputNumberDTO;
import gribiwe.model.util.ResultNumberStatus;

import java.math.BigDecimal;

/**
 * implementation of interface for keeping number
 * with some status
 *
 * @author Gribiwe
 * @see ResultNumber
 */
public class ResultNumberImpl implements ResultNumber {

   /**
    * current number
    */
   private BigDecimal number;

   /**
    * current status
    *
    * @see ResultNumberStatus
    */
   private ResultNumberStatus status;

   /**
    * initial of default values
    */
   ResultNumberImpl() {
      number = BigDecimal.ZERO;
      status = ResultNumberStatus.EQUALS_RESULT;
   }

   @Override
   public void loadResult(BigDecimal result, ResultNumberStatus resultNumberStatus) {
      number = result;
      status = resultNumberStatus;
   }

   @Override
   public OutputNumberDTO getNumberDTO() {
      return new OutputNumberDTO(number);
   }

   @Override
   public BigDecimal getNumber() {
      return number;
   }

   @Override
   public ResultNumberStatus getStatus() {
      return status;
   }
}
