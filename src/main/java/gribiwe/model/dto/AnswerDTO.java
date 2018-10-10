package gribiwe.model.dto;

public class AnswerDTO {

   private OutputNumberDTO outputNumberDTO;
   private HistoryLineDTO historyLineDTO;
   private LastSpecialOperationHistoryDTO lastSpecialOperationHistoryDTO;
   private MemoryDTO memoryDTO;

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
