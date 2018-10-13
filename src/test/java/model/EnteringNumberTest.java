package model;

import gribiwe.controller.util.OutputNumberParser;
import gribiwe.model.EnteringNumber;
import gribiwe.model.EnteringNumberImpl;
import gribiwe.model.util.Digit;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class EnteringNumberTest extends Assert {

   private static EnteringNumber enteringNumber;
   private static OutputNumberParser outputNumberParser;

   @BeforeAll
   static void setUp() {
      outputNumberParser = new OutputNumberParser();
   }

   @Test
   void testEnterNumberWithoutPoint() {
      enteringNumber = new EnteringNumberImpl();

      enteringNumber.negate();
      assertEquals("0", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(0).compareTo(enteringNumber.getNumber()));

      enteringNumber.addDigit(Digit.ONE);
      assertEquals("1", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(1).compareTo(enteringNumber.getNumber()));

      enteringNumber.addDigit(Digit.TWO);
      assertEquals("12", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(12).compareTo(enteringNumber.getNumber()));

      enteringNumber.addDigit(Digit.THREE);
      assertEquals("123", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(123).compareTo(enteringNumber.getNumber()));

      enteringNumber.addDigit(Digit.FOUR);
      assertEquals("1 234", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(1234).compareTo(enteringNumber.getNumber()));

      enteringNumber.addDigit(Digit.FIVE);
      assertEquals("12 345", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(12345).compareTo(enteringNumber.getNumber()));

      enteringNumber.addDigit(Digit.SIX);
      assertEquals("123 456", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(123456).compareTo(enteringNumber.getNumber()));

      enteringNumber.addDigit(Digit.SEVEN);
      assertEquals("1 234 567", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(1234567).compareTo(enteringNumber.getNumber()));

      enteringNumber.addDigit(Digit.EIGHT);
      assertEquals("12 345 678", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(12345678).compareTo(enteringNumber.getNumber()));

      enteringNumber.addDigit(Digit.NINE);
      assertEquals("123 456 789", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(123456789).compareTo(enteringNumber.getNumber()));

      enteringNumber.addDigit(Digit.ZERO);
      assertEquals("1 234 567 890", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(1234567890).compareTo(enteringNumber.getNumber()));

      enteringNumber.negate();
      assertEquals("-1 234 567 890", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      System.out.println(enteringNumber.getNumber());
      System.out.println(-1234567890);
      assertEquals(0, BigDecimal.valueOf(-1234567890).compareTo(enteringNumber.getNumber()));

      enteringNumber.negate();
      assertEquals("1 234 567 890", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(1234567890).compareTo(enteringNumber.getNumber()));

      enteringNumber = new EnteringNumberImpl();
      assertEquals("0", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(0).compareTo(enteringNumber.getNumber()));

      enteringNumber.negate();
      assertEquals("0", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(0).compareTo(enteringNumber.getNumber()));

      enteringNumber = new EnteringNumberImpl();

      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.ONE);
      assertEquals("1 111 111 111 111 111", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(1111111111111111D).compareTo(enteringNumber.getNumber()));
   }

   @Test
   void testEnterNumberWithPoint() {
      enteringNumber = new EnteringNumberImpl();

      enteringNumber.addPoint();
      assertEquals("0,", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(0).compareTo(enteringNumber.getNumber()));

      enteringNumber.addPoint();
      assertEquals("0,", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(0).compareTo(enteringNumber.getNumber()));

      enteringNumber.removeSymbol();
      assertEquals("0", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(0).compareTo(enteringNumber.getNumber()));

      enteringNumber.addPoint();
      assertEquals("0,", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(0).compareTo(enteringNumber.getNumber()));

      enteringNumber.negate();
      assertEquals("-0,", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(0).compareTo(enteringNumber.getNumber()));

      enteringNumber.addDigit(Digit.ONE);
      assertEquals("-0,1", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(-0.1).compareTo(enteringNumber.getNumber()));

      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.TWO);
      assertEquals("-0,112", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(-0.112D).compareTo(enteringNumber.getNumber()));

      enteringNumber = new EnteringNumberImpl();
      enteringNumber.addPoint();
      enteringNumber.negate();
      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.TWO);
      enteringNumber.addDigit(Digit.THREE);
      enteringNumber.addDigit(Digit.FOUR);
      enteringNumber.addDigit(Digit.FIVE);
      enteringNumber.addDigit(Digit.SIX);
      enteringNumber.addDigit(Digit.SEVEN);
      enteringNumber.addDigit(Digit.EIGHT);
      enteringNumber.addDigit(Digit.NINE);
      enteringNumber.addDigit(Digit.ONE);
      enteringNumber.addDigit(Digit.TWO);
      enteringNumber.addDigit(Digit.THREE);
      enteringNumber.addDigit(Digit.FOUR);
      enteringNumber.addDigit(Digit.FIVE);
      enteringNumber.addDigit(Digit.SIX);
      enteringNumber.addDigit(Digit.SEVEN);
      enteringNumber.addDigit(Digit.EIGHT);
      enteringNumber.addDigit(Digit.NINE);
      enteringNumber.addDigit(Digit.TWO);
      enteringNumber.addDigit(Digit.TWO);
      enteringNumber.addDigit(Digit.FIVE);
      enteringNumber.addDigit(Digit.FIVE);
      assertEquals("-0,1234567891234567", outputNumberParser.formatInput(enteringNumber.getNumberDTO()));
      assertEquals(0, BigDecimal.valueOf(-0.1234567891234567D).compareTo(enteringNumber.getNumber()));
   }
}
