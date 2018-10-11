package gribiwe.model;

import gribiwe.model.dto.EnteredNumberDTO;
import gribiwe.model.util.Digit;

import java.math.BigDecimal;

public class EnteringNumberImpl implements EnteringNumber {

   private static final int MAX_LENGTH = 16;
   private static final int MAX_LENGTH_WITH_POINT = 17;
   private static final int MAX_LENGTH_WITH_POINT_WHEN_FIRST_IS_ZERO = 18;

   private String numberString;
   private boolean needNegate;

   public EnteringNumberImpl() {
      numberString = "";
      needNegate = false;
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
      } else if (numberString.length() < limitToCompare && !(numberString.equals("0") && digit.equals(Digit.ZERO))) {
         numberString += digit.ordinal();
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
      }
   }

   @Override
   public void negate() {
      if (!numberString.equals("0") && !numberString.equals("")) {
         needNegate = !needNegate;
      }
   }

   @Override
   public void removeSymbol() {
      if (numberString.length() > 1) {
         numberString = numberString.substring(0, numberString.length() - 1);
      } else {
         numberString = "0";
      }

      if (numberString.equals("0")) {
         needNegate = false;
      }
   }

   @Override
   public void removeAllSymbols() {
      numberString = "";
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
   public EnteredNumberDTO getNumberDTO() {
      BigDecimal number = getNumber();
      if (needNegate) {
         number = number.negate();
      }
      int rightDigits = 0;
      boolean needPoint = numberString.contains(".");
      if (needPoint) {
         rightDigits = numberString.substring(numberString.indexOf(".")+1).length();
      }
      return new EnteredNumberDTO(number, rightDigits, needPoint, needNegate);
   }
}
