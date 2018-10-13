package gribiwe.controller;

import gribiwe.model.dto.AnswerDTO;
import gribiwe.model.exception.CalculatorException;

/**
 * Functional interface for forming a operations to mainModel of controller
 * it's used for removing a lot of try-catches from controller
 *
 * @author Gribiwe
 */
@FunctionalInterface
public interface CalculatorAction {
   AnswerDTO doAction() throws CalculatorException;
}
