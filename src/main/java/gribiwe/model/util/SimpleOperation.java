package gribiwe.model.util;

/**
 * enum of simple operations
 * plus, subtract, multiply and divide
 */
public enum SimpleOperation implements Operation{

   /**
    * operation of adding numbers to each other
    */
   PLUS,

   /**
    * operation of subtracting
    * one number from another
    */
   SUBTRACT,

   /**
    * operation of multiplying
    * one number by another one
    */
   MULTIPLY,

   /**
    * operation of dividing
    * one number by another one
    */
   DIVIDE,

   /**
    * empty operation for
    * ignoring in history
    */
   NOTHING
}
