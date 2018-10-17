package gribiwe.model.exception;

/**
 * Exception class designed to be thrown
 * if was trying to get root of negated value
 *
 * @author Gribiwe
 */
public class UncorrectedDataException extends Exception {

   /**
    * default constructor for messaged exception
    *
    * @param message message of exception
    */
   public UncorrectedDataException(String message) {
      super(message);
   }
}
