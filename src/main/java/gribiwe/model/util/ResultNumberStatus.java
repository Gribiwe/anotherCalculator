package gribiwe.model.util;

/**
 * shows what status result number have
 */
public enum ResultNumberStatus {

   /**
    * result number is number
    * after equals operation
    */
   EQUALS_RESULT,

   /**
    * result number is number
    * appeared after history calculation
    * after adding new operation
    */
   HISTORY_RESULT,

   /**
    * result number is number
    * blocked after operation with
    * memory (except loading from memory)
    */
   BLOCKED_BY_MEMORY,

   /**
    * number loaded from memory
    */
   LOADED_FROM_MEMORY
}
