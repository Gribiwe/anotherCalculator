package gribiwe.model;

import gribiwe.model.util.ResultNumberStatus;

import java.math.BigDecimal;

/**
 * Class for keeping number with some status
 *
 * @author Gribiwe
 */
class ResultNumberImpl {

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

   /**
    * method for loading number with
    * provided status
    *
    * @param result      number to load
    * @param resultNumberStatus status of number
    */
   void loadResult(BigDecimal result, ResultNumberStatus resultNumberStatus) {
      number = result;
      status = resultNumberStatus;
   }

   /**
    * @return current BigDecimal number
    */
   BigDecimal getNumber() {
      return number;
   }

   /**
    * @return current status or result number
    * @see ResultNumberStatus
    */
   ResultNumberStatus getStatus() {
      return status;
   }

   /**
    * clears result number
    */
   void clear() {
      number = BigDecimal.ZERO;
      status = ResultNumberStatus.EQUALS_RESULT;
   }
}
