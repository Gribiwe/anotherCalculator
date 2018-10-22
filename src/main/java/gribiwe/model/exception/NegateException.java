package gribiwe.model.exception;

/**
 * Runtime exception for operation negate supposed to be
 * not thrown. If it's thrown, model coder should to repair it
 *
 * @author Gribiwe
 */
public class NegateException extends RuntimeException {

   /**
    * creation of negate exception
    * @param message message text of exception
    * @param throwable another throwable object,
    *                  thrown in negate operation
    */
   public NegateException(String message, Throwable throwable) {
      super(message, throwable);
   }
}
