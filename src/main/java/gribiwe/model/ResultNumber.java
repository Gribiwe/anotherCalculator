package gribiwe.model;

import gribiwe.model.dto.OutputNumberDto;
import gribiwe.model.util.ResultNumberStatus;

import java.math.BigDecimal;

/**
 * interface of class for keeping number
 * which will be viewed as result number
 *
 * @author Gribiwe
 * @see ResultNumberImpl
 */
public interface ResultNumber {
   /**
    * method for loading number with
    * provided status
    *
    * @param result      number to load
    * @param resultNumberStatus status of number
    */
   void loadResult(BigDecimal result, ResultNumberStatus resultNumberStatus);

   /**
    * forms new OutputNumberDto object with
    * number on it
    *
    * @return current number in OutputNumberDto object
    */
   OutputNumberDto getNumberDTO();

   /**
    * @return current BigDecimal number
    */
   BigDecimal getNumber();

   /**
    * @return current status or result number
    * @see ResultNumberStatus
    */
   ResultNumberStatus getStatus();

   /**
    * clears result number
    */
   void clear();
}
