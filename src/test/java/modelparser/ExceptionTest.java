package modelparser;

import gribiwe.model.exception.UncorrectedDataException;
import gribiwe.model.exception.ZeroDivideException;
import gribiwe.model.exception.ZeroDivideZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * class for testing exceptions
 * provided by some cases
 */
public class ExceptionTest extends Assert {

   /**
    * exemplar of TestUtil class for testing the cases
    */
   private static TestUtil testUtil;

   /**
    * initialization of testUtil
    */
   @Before
   public void setup() {
      testUtil = new TestUtil();
   }

   /**
    * testing exception issued
    * by dividing by zero
    */
   @Test
   public void testZeroDivideException() {
      testZeroDivideException("5 / 0 =");
      testZeroDivideException("5 / 0 /");
      testZeroDivideException("1/x");
      testZeroDivideException("0 1/x");
      testZeroDivideException("0 sqr 1/x");
   }


   /**
    * testing exception issued
    * by dividing zero by zero
    */
   @Test
   public void testUncertainException() {
      testUncertainException("0 / 0 =");
      testUncertainException("0 / 0 /");
      testUncertainException("/ ce /");
   }

   /**
    * testing exception issued
    * by getting root value from negated value
    */
   @Test
   public void testUncorrectedDataException() {
      testUncorrectedException("5 n √");
      testUncorrectedException("15 n √");
      testUncorrectedException("5 n 1 * 1 - 1 = √");
      testUncorrectedException("89 n √");
      testUncorrectedException("15 sqr n √");
      testUncorrectedException("0,5 n 1321325 √");
      testUncorrectedException("9999999999 n √");
   }

   /**
    * method for testing is emulated
    * action sequence provides ZeroDivideException
    *
    * @param sequence string value of actions which will be emulated
    */
   private void testZeroDivideException(String sequence) {
      testUtil.doExceptionTest(sequence, ZeroDivideException.class);
   }

   /**
    * method for testing is emulated
    * action sequence provides UncorrectedDataException
    *
    * @param sequence string value of actions which will be emulated
    */
   private void testUncorrectedException(String sequence) {
      testUtil.doExceptionTest(sequence, UncorrectedDataException.class);
   }

   /**
    * method for testing is emulated
    * action sequence provides ZeroDivideZeroException
    *
    * @param sequence string value of actions which will be emulated
    */
   private void testUncertainException(String sequence) {
      testUtil.doExceptionTest(sequence, ZeroDivideZeroException.class);
   }
}
