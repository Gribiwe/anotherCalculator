import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class sff {
   @Test
   public void ad() {
      BigDecimal df = new BigDecimal("20.92");

      System.out.println(df.setScale(1, RoundingMode.DOWN));
   }
}
