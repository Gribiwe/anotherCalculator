package gribiwe.model;

import gribiwe.model.dto.HistoryLineDto;
import gribiwe.model.util.SimpleOperation;
import gribiwe.model.util.SpecialOperation;

import java.math.BigDecimal;
import java.util.List;

/**
 * interface for class to represent a history line object
 * with information of special operations, simple operations or numbers
 * for it
 *
 * @author Gribiwe
 * @see HistoryLineImpl
 */
public interface HistoryLine {

   /**
    * uploads list of special operations to save it
    * at history to last number
    *
    * @param specialOperations list of special operations
    * @see SpecialOperation
    * @see List
    */
   void uploadSpecialOperations(List<SpecialOperation> specialOperations);

   /**
    * adds number with provided operation
    * to history
    *
    * @param number    number to add
    * @param operation operation to add
    * @param saveOperation      if true - saves last operation
    *                  for some requirements
    * @see SimpleOperation
    */
   void add(BigDecimal number, SimpleOperation operation, boolean saveOperation);

   /**
    * @return saved number
    */
   BigDecimal getSavedResult();

   /**
    * @return saved operation
    * @see SimpleOperation
    */
   SimpleOperation getSavedOperation();

   /**
    * sets saved number
    *
    * @param savedResult number to save
    */
   void setSavedResult(BigDecimal savedResult);

   /**
    * forms dto for answering from model
    *
    * @return dto of history line
    * @see HistoryLineDto
    */
   HistoryLineDto getHistoryLineDTO();

   /**
    * changing the last operation
    *
    * @param operation operation change to
    * @see SimpleOperation
    */
   void changeLastOperation(SimpleOperation operation);

   /**
    * @return last simple operation in history
    * @see SimpleOperation
    */
   SimpleOperation getLastOperation();

   /**
    * clears the history. All of arrays become empty
    */
   void clearHistory();

   /**
    * calculating a numbers with
    * operations in history
    *
    * @return result of calculation
    */
   BigDecimal calculate();
}
