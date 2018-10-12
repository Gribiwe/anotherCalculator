package gribiwe.model.dto;

public class AnswerDTO {

   private final OutputNumberDTO outputNumberDTO;
   private final HistoryLineDTO historyLineDTO;
   private final LastSpecialOperationHistoryDTO lastSpecialOperationHistoryDTO;
   private final MemoryDTO memoryDTO;
   // todo do reading on final
   // todo make final fields whenever possible

   public AnswerDTO(OutputNumberDTO outputNumberDTO, HistoryLineDTO historyLineDTO, LastSpecialOperationHistoryDTO lastSpecialOperationHistoryDTO, MemoryDTO memoryDTO) {
      this.outputNumberDTO = outputNumberDTO;
      this.historyLineDTO = historyLineDTO;
      this.lastSpecialOperationHistoryDTO = lastSpecialOperationHistoryDTO;
      this.memoryDTO = memoryDTO;
   }

   public MemoryDTO getMemoryDTO() {
      return memoryDTO;
   }

   public LastSpecialOperationHistoryDTO getLastSpecialOperationHistoryDTO() {
      return lastSpecialOperationHistoryDTO;
   }

   public OutputNumberDTO getOutputNumberDTO() {
      return outputNumberDTO;
   }

   public HistoryLineDTO getHistoryLineDTO() {
      return historyLineDTO;
   }
}
