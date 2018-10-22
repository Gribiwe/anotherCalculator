package gribiwe.controller;

import gribiwe.model.exception.OverflowException;
import gribiwe.model.exception.UncorrectedDataException;
import gribiwe.model.exception.ZeroDivideException;
import gribiwe.model.exception.ZeroDivideZeroException;

import java.math.BigDecimal;

/**
 * functional interface for controller
 * designed for processing actions with model
 *
 * @author Gribiwe
 */
@FunctionalInterface
interface ModelAction {

   /**
    * designed to process some actions with returning BigDecimal value
    *
    * @return result of processing operations
    * @throws OverflowException        if was overflow value at result
    * @throws ZeroDivideException      is was trying to divide some number (not zero)
    *                                  by zero
    * @throws ZeroDivideZeroException  if was trying to divide zero by zero
    * @throws UncorrectedDataException if was trying to find root from negative value
    */
   BigDecimal doAction() throws OverflowException, ZeroDivideException, ZeroDivideZeroException, UncorrectedDataException;
}
