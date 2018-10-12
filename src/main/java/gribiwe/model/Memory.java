package gribiwe.model;

import gribiwe.model.dto.MemoryDTO;

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
    * making add operation to current number
    *
    * @param num number to add
    */
   void add(BigDecimal num);

   /**
    * making subtract operation from current number
    *
    * @param num number to subtract
    */
   void remove(BigDecimal num);

   /**
    * clears memory
    */
   void clear();

   /**
    * forms a DTO with info about memory
    *
    * @return dto for answer from model
    */
   MemoryDTO getDTO();
}
