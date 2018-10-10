package modelparser;

import org.junit.Before;
import org.junit.Test;

/**
 * class for testing operations wit memory
 */
public class MemoryTest {

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
    * testing add and subtract to memory operation
    */
   @Test
   public void testAddSubtractMemory() {
      testUtil.doTest("5 m+", "5", "", "5");
      testUtil.doTest("5 m+ m+ m+", "5", "", "15");
      testUtil.doTest("5 n m+", "-5", "", "-5");
      testUtil.doTest("5 n m+ m+ m+", "-5", "", "-15");

      testUtil.doTest("5 m-", "5", "", "-5");
      testUtil.doTest("5 m- m- m-", "5", "", "-15");
      testUtil.doTest("5 n m-", "-5", "", "5");
      testUtil.doTest("5 n m- m- m-", "-5", "", "15");

      testUtil.doTest("0 m-", "0", "", "0");
      testUtil.doTest("0 m- m- m-", "0", "", "0");
      testUtil.doTest("0 n m-", "0", "", "0");
      testUtil.doTest("0 n m- m- m-", "0", "", "0");
      testUtil.doTest("0, m-", "0", "", "0");
      testUtil.doTest(", m-", "0", "", "0");
      testUtil.doTest("0, m- m-", "0", "", "0");
      testUtil.doTest(", m- m-", "0", "", "0");
      testUtil.doTest("0, n m-", "0", "", "0");
      testUtil.doTest(", n m-", "0", "", "0");

      testUtil.doTest("0 m+", "0", "", "0");
      testUtil.doTest("0 m+ m+ m+", "0", "", "0");
      testUtil.doTest("0 n m+", "0", "", "0");
      testUtil.doTest("0 n m+ m+ m+", "0", "", "0");
      testUtil.doTest("0, m+", "0", "", "0");
      testUtil.doTest(", m+", "0", "", "0");
      testUtil.doTest("0, m+ m+", "0", "", "0");
      testUtil.doTest(", m+ m+", "0", "", "0");
      testUtil.doTest("0, n m+", "0", "", "0");
      testUtil.doTest(", n m+", "0", "", "0");

      testUtil.doTest("5 m+ 25", "25", "", "5");
      testUtil.doTest("5 m+ 25 mr", "5", "", "5");
      testUtil.doTest("5 m+ 25 + mr +", "30", "25   +   5   +   ", "5");
      testUtil.doTest("5 m+ 25 + mr =", "30", "", "5");
      testUtil.doTest("5 m+ 25 m+ + mr +", "55", "25   +   30   +   ", "30");

      testUtil.doTest("5 m- 25", "25", "", "-5");
      testUtil.doTest("5 m- 25 mr", "-5", "", "-5");
      testUtil.doTest("5 m- 25 + mr +", "20", "25   +   -5   +   ", "-5");
      testUtil.doTest("5 m- 25 + mr =", "20", "", "-5");
      testUtil.doTest("5 m- 25 m+ + mr +", "45", "25   +   20   +   ", "20");
      testUtil.doTest("5 m- 25 m- + mr +", "-5", "25   +   -30   +   ", "-30");

      testUtil.doTest("5 m- 25 m- mc +", "25", "25   +   ", "0");
      testUtil.doTest("5 m- 25 m- mc 5 +", "5", "5   +   ", "0");
      testUtil.doTest("5 m- 25 m- mc 5 + 5 +", "10", "5   +   5   +   ", "0");

      testUtil.doTest("5 m- ,5 m- 5 + mr +", "-0,5", "5   +   -5,5   +   ", "-5,5");
      testUtil.doTest("5 + 5 + m+ =", "20", "", "10");
      testUtil.doTest("5 + 5 + m+ =", "20", "", "10");
      testUtil.doTest("5 + 5 + m- =", "20", "", "-10");
      testUtil.doTest("5 + 5 + m- =", "20", "", "-10");
      testUtil.doTest("5 + 5 + m- + 5 + mc =", "30", "", "0");
   }
}
