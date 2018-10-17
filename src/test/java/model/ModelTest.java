package model;

import gribiwe.model.ModelBrainImpl;
import gribiwe.model.exception.OverflowException;
import gribiwe.model.exception.UncorrectedDataException;
import gribiwe.model.exception.ZeroDivideException;
import gribiwe.model.exception.ZeroDivideZeroException;
import gribiwe.model.util.Digit;
import gribiwe.model.util.SimpleOperation;
import gribiwe.model.util.SpecialOperation;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;


/**
 * class for testing model directly
 */
public class ModelTest extends Assert {

   /**
    * exemplar of Main model class
    */
   private static ModelBrainImpl mainModel;

   /**
    * initialization of mainModel
    */
   @BeforeAll
   static void setup() {
      mainModel = new ModelBrainImpl();
   }

   /**
    * testing model
    * 3 * 2 - 5 * 10 - 1 √ have be 3
    */
   @Test
   void testModel() {
      try {
         mainModel.addDigit(Digit.THREE);
         mainModel.doOperation(SimpleOperation.MULTIPLY);
         mainModel.addDigit(Digit.TWO);
         mainModel.doOperation(SimpleOperation.SUBTRACT);
         mainModel.addDigit(Digit.FIVE);
         mainModel.doOperation(SimpleOperation.MULTIPLY);
         mainModel.addDigit(Digit.ONE);
         mainModel.addDigit(Digit.ZERO);
         mainModel.doOperation(SimpleOperation.SUBTRACT);
         mainModel.addDigit(Digit.ONE);
         mainModel.doEquals();
         mainModel.doSpecialOperation(SpecialOperation.ROOT);
      } catch (ZeroDivideZeroException e) {
         e.printStackTrace();
      } catch (OverflowException e) {
         e.printStackTrace();
      } catch (ZeroDivideException e) {
         e.printStackTrace();
      } catch (UncorrectedDataException e) {
         e.printStackTrace();
      }
      assertEquals(0, mainModel.getResultNumber().compareTo(BigDecimal.valueOf(3)));
   }
}
