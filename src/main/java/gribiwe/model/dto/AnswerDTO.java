package gribiwe.model.dto;

/**
 * DTO for sending answer from model somewhere
 * consists of value of Output number (EnteredNumber), history,
 * memory and even tail of history for special
 * operations.
 *
 * @author Gribiwe
 * @see OutputNumberDTO
 * @see EnteredNumberDTO
 * @see HistoryLineDTO
 * @see TailSpecialOperationHistoryDTO
 * @see MemoryDTO
 */
public class AnswerDTO {

   /**
    * Output number of calculator
    * shows value of entered number or result
    * of calculations. Can be as EnteredNumberDTO
    * which uses for keeping info about building
    * number.
    *
    * @see EnteredNumberDTO
    */
   private final OutputNumberDTO outputNumberDTO;

   /**
    * Keeps info about history of calculations and
    * numbers at history
    */
   private final HistoryLineDTO historyLineDTO;

   /**
    * keeps information about processing
    * special operation building
    */
   private final TailSpecialOperationHistoryDTO tailSpecialOperationHistoryDTO;

   /**
    * keeps information about memory values
    * (number, is enable)
    */
   private final MemoryDTO memoryDTO;

   /**
    * initials of answer from model
    *
    * @param outputNumberDTO                number of result or
    *                                       building number
    * @param historyLineDTO                 history of calculations
    *                                       and numbers
    * @param tailSpecialOperationHistoryDTO processing building of
    *                                       special operations
    * @param memoryDTO                      current memory values
    */
   public AnswerDTO(OutputNumberDTO outputNumberDTO, HistoryLineDTO historyLineDTO, TailSpecialOperationHistoryDTO tailSpecialOperationHistoryDTO, MemoryDTO memoryDTO) {
      this.outputNumberDTO = outputNumberDTO;
      this.historyLineDTO = historyLineDTO;
      this.tailSpecialOperationHistoryDTO = tailSpecialOperationHistoryDTO;
      this.memoryDTO = memoryDTO;
   }

   public MemoryDTO getMemoryDTO() {
      return memoryDTO;
   }

   public TailSpecialOperationHistoryDTO getTailSpecialOperationHistoryDTO() {
      return tailSpecialOperationHistoryDTO;
   }

   public OutputNumberDTO getOutputNumberDTO() {
      return outputNumberDTO;
   }

   public HistoryLineDTO getHistoryLineDTO() {
      return historyLineDTO;
   }
}
