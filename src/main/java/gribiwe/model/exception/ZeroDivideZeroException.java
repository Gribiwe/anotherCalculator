package gribiwe.model.exception;


/**
 * Exception class designed to be thrown
 * if was trying to divide zero by zero
 *
 * @author Gribiwe
 */
public class ZeroDivideZeroException extends Exception {

   /**
    * default constructor for messaged exception
    *
    * @param message message of exception
    */
   public ZeroDivideZeroException(String message) {
      super(message);
   }
}
