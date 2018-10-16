package gribiwe.model;

import gribiwe.model.dto.MemoryDto;
import gribiwe.model.util.MemoryOperation;

import java.math.BigDecimal;

/**
 * interface for class designed to
 * keep some number in specified place
 * for some kind of calculations
 *
 * @author Gribiwe
 * @see MemoryImpl
 */
public interface Memory {

   /**
    * @return current memory number
    */
   BigDecimal getNumber();

   /**
    * making operation to current number with new one
    *
    * @param num number to operate
    * @param operation to process
    * @see MemoryOperation
    */
   void doOperation(BigDecimal num, MemoryOperation operation);

   /**
    * clears memory
    */
   void clear();

   /**
    * forms a DTO with info about memory
    *
    * @return dto for answer from model
    */
   MemoryDto getDTO();
}
