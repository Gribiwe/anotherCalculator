package gribiwe.model.exception;


/**
 * Exception class designed to be thrown
 * if was an overflow
 *
 * @author Gribiwe
 */
public class OverflowException extends Exception {

   /**
    * default constructor for messaged exception
    *
    * @param message message of exception
    */
   public OverflowException(String message) {
      super(message);
   }
}
