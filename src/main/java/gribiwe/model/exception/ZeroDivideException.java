package gribiwe.model.exception;


/**
 * Exception class designed to be thrown
 * if was trying to divide some number (not zero) by zero
 *
 * @author Gribiwe
 */
public class ZeroDivideException extends Exception {

   /**
    * default constructor for messaged exception
    *
    * @param message message of exception
    */
   public ZeroDivideException(String message) {
      super(message);
   }
}
