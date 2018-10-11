package gribiwe.model;

import gribiwe.model.dto.OutputNumberDTO;
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
    * method for loading number as history
    * calculation result
    *
    * @param historyResult number to load
    */
   void loadAsHistoryResult(BigDecimal historyResult);

   /**
    * method for loading number as memory number
    * with blocking a number for some operations with it
    *
    * @param memoryNumber number to load
    */
   void loadAsMemoryNumbersWithBlock(BigDecimal memoryNumber);

   /**
    * method for loading number as memory number
    *
    * @param memoryNumber number to load
    */
   void loadAsMemoryNumber(BigDecimal memoryNumber);

   /**
    * method for loading number as history
    * calculation result after calling of equals
    *
    * @param equalsResult number to load
    */
   void loadAsEqualsResult(BigDecimal equalsResult);

   /**
    * forms new OutputNumberDTO object with
    * number on it
    *
    * @return current number in OutputNumberDTO object
    */
   OutputNumberDTO getNumberDTO();

   /**
    * @return current BigDecimal number
    */
   BigDecimal getNumber();

   /**
    * @return current status or result number
    * @see ResultNumberStatus
    */
   ResultNumberStatus getStatus();
}
