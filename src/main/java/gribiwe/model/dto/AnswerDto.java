package gribiwe.model.dto;

/**
 * DTO for sending answer from model somewhere
 * consists of value of Output number (EnteredNumber), history,
 * memory and even tail of history for special
 * operations.
 *
 * @author Gribiwe
 * @see OutputNumberDto
 * @see EnteredNumberDto
 * @see HistoryLineDto
 * @see TailSpecialOperationHistoryDto
 * @see MemoryDto
 */
public class AnswerDto {

   /**
    * Output number of calculator
    * shows value of entered number or result
    * of calculations. Can be as EnteredNumberDto
    * which uses for keeping info about building
    * number.
    *
    * @see EnteredNumberDto
    */
   private final OutputNumberDto outputNumberDto;

   /**
    * Keeps info about history of calculations and
    * numbers at history
    */
   private final HistoryLineDto historyLineDto;

   /**
    * keeps information about processing
    * special operation building
    */
   private final TailSpecialOperationHistoryDto tailSpecialOperationHistoryDto;

   /**
    * keeps information about memory values
    * (number, is enable)
    */
   private final MemoryDto memoryDto;

   /**
    * initials of answer from model
    *
    * @param outputNumberDto                number of result or
    *                                       building number
    * @param historyLineDto                 history of calculations
    *                                       and numbers
    * @param tailSpecialOperationHistoryDto processing building of
    *                                       special operations
    * @param memoryDto                      current memory values
    */
   public AnswerDto(OutputNumberDto outputNumberDto, HistoryLineDto historyLineDto, TailSpecialOperationHistoryDto tailSpecialOperationHistoryDto, MemoryDto memoryDto) {
      this.outputNumberDto = outputNumberDto;
      this.historyLineDto = historyLineDto;
      this.tailSpecialOperationHistoryDto = tailSpecialOperationHistoryDto;
      this.memoryDto = memoryDto;
   }

   public MemoryDto getMemoryDto() {
      return memoryDto;
   }

   public TailSpecialOperationHistoryDto getTailSpecialOperationHistoryDto() {
      return tailSpecialOperationHistoryDto;
   }

   public OutputNumberDto getOutputNumberDto() {
      return outputNumberDto;
   }

   public HistoryLineDto getHistoryLineDto() {
      return historyLineDto;
   }
}
