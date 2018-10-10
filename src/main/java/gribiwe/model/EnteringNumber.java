package gribiwe.model;

import gribiwe.model.dto.EnteredNumberDTO;
import gribiwe.model.util.Digit;

import java.math.BigDecimal;

/**
 * Interface for classes to build numbers
 *
 * @author Gribiwe
 */
public interface EnteringNumber {

   /**
    * adds new digit to number
    *
    * @param digit digit to add
    */
   void addDigit(Digit digit);

   /**
    * adds point to number
    * for creation decimal number
    */
   void addPoint();

   /**
    * negates number
    */
   void negate();

   /**
    * removing one digit or point
    */
   void removeSymbol();

   /**
    * starts creation of number from the begin
    */
   void removeAllSymbols();

   /**
    * @return constructed number
    */
   BigDecimal getNumber();

   /**
    * @return constructed number
    * with additional information
    */
   EnteredNumberDTO getNumberDTO();

}
