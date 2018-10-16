package gribiwe.model;

import gribiwe.model.dto.EnteredNumberDto;
import gribiwe.model.util.Digit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Logger;

public class EnteringNumberWithStringImpl implements EnteringNumber {

   private static final int MAX_LENGTH = 16;
   private static final int MAX_LENGTH_WITH_POINT = 17;
   private static final int MAX_LENGTH_WITH_POINT_WHEN_FIRST_IS_ZERO = 18;

   private String numberString;
   private boolean needNegate;
   private Logger logger;

   public EnteringNumberWithStringImpl() {
      numberString = "";
      needNegate = false;
      logger = Logger.getLogger(getClass().getName());
      logger.info("Entering number initiated!");
   }

   @Override
   public void addDigit(Digit digit) {
      int limitToCompare;

      if (numberString.contains(".")) {
         if (numberString.charAt(0) == '0') {
            limitToCompare = MAX_LENGTH_WITH_POINT_WHEN_FIRST_IS_ZERO;
         } else {
            limitToCompare = MAX_LENGTH_WITH_POINT;
         }
      } else {
         limitToCompare = MAX_LENGTH;
      }
      if (numberString.equals("0") || numberString.equals("")) {
         numberString = String.valueOf(digit.ordinal());
         logger.info("added digit " + digit + ". Number: " + numberString);
      } else if (numberString.length() < limitToCompare && !(numberString.equals("0") && digit.equals(Digit.ZERO))) {
         numberString += digit.ordinal();
         logger.info("added digit " + digit + ". Number: " + numberString);
      } else {
         logger.info("digit " + digit + " doesn't added. Length already is max.");
      }
   }

   @Override
   public void addPoint() {
      if (!numberString.contains(".")) {
         if (numberString.equals("")) {
            numberString = "0.";
         } else {
            numberString += ".";
         }
         logger.info("added point to number");
      }
   }

   @Override
   public void negate() {
      if (!numberString.equals("0") && !numberString.equals("")) {
         needNegate = !needNegate;
      }
      if (needNegate) {
         logger.info("number will be negated");
      } else {
         logger.info("number will not be negated");
      }
   }

   @Override
   public void removeSymbol() {
      if (numberString.length() > 1) {
         numberString = numberString.substring(0, numberString.length() - 1);
      } else {
         numberString = "0";
      }
      logger.info("removed one symbol from number. Number: " + numberString);

      if (numberString.equals("0")) {
         needNegate = false;
      }
   }

   @Override
   public void removeAllSymbols() {
      numberString = "";
      logger.info("now ready for building new number");
      needNegate = false;
   }

   @Override
   public BigDecimal getNumber() {
      if (numberString.equals("")) {
         return BigDecimal.ZERO;
      } else if (needNegate) {
         return new BigDecimal(numberString).negate();
      } else {
         return new BigDecimal(numberString);
      }
   }

   @Override
   public EnteredNumberDto getNumberDTO() {
      BigDecimal number = getNumber();
      if (needNegate) {
         number = number.negate();
      }
      int rightDigits = 0;
      boolean needPoint = numberString.contains(".");
      if (needPoint) {
         rightDigits = numberString.substring(numberString.indexOf(".") + 1).length();
      }
      number = number.setScale(rightDigits, RoundingMode.DOWN);
      return new EnteredNumberDto(number, needPoint, needNegate);
   }

}
