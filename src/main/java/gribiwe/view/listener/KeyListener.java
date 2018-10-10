package gribiwe.view.listener;

import gribiwe.controller.Controller;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * class for listening a keys on keyboard
 *
 * @author Gribiwe
 */
public class KeyListener {

   /**
    * this value uses to activate or deactivate
    * a listener
    */
   private boolean enable;

   /**
    * exemplar of aplication controller
    */
   private Controller controller;

   /**
    * node for checking is it blocked by
    * some calculator's exception
    *
    * @see gribiwe.model.exception.CalculatorException
    */
   private Node someNodeForCheckBlock;

   /**
    * creates listener for keyboard keys
    *
    * @param controller            application controller
    * @param someNodeForCheckBlock some node for checking
    *                              listener block by
    *                              some exception.
    *                              this node must be blocked
    *                              when exception appears
    */
   public KeyListener(Controller controller, Node someNodeForCheckBlock) {
      this.controller = controller;
      this.someNodeForCheckBlock = someNodeForCheckBlock;
      enable = true;
   }

   public void setEnable(boolean enable) {
      this.enable = enable;
   }

   /**
    * method for listening a key event
    * some keys calls a controller methods
    *
    * @param keyEvent pressed key event
    */
   public void manageKeyEvent(KeyEvent keyEvent) {
      if (enable) {
         if (keyEvent.isShiftDown()) {
            if (someNodeForCheckBlock.isDisable()) return;
            if (keyEvent.getCode().equals(KeyCode.EQUALS)) {
               controller.doPlus();

            } else if (keyEvent.getCode().equals(KeyCode.DIGIT2)) {
               controller.doRoot();

            } else if (keyEvent.getCode().equals(KeyCode.DIGIT5)) {
               controller.doPercent();

            } else if (keyEvent.getCode().equals(KeyCode.DIGIT8)) {
               controller.doMultiply();
            }
         } else {
            if (!someNodeForCheckBlock.isDisable()) {
               if (keyEvent.getCode().equals(KeyCode.EQUALS) ||
                       keyEvent.getCode().equals(KeyCode.ENTER)) {
                  controller.doEquals();

               } else if (keyEvent.getCode().equals(KeyCode.MINUS)) {
                  controller.doSubtract();

               } else if (keyEvent.getCode().equals(KeyCode.SLASH)) {
                  controller.doDivide();

               } else if (keyEvent.getCode().equals(KeyCode.COMMA)) {
                  controller.addPoint();

               } else if (keyEvent.getCode().equals(KeyCode.Q)) {
                  controller.doSquare();

               } else if (keyEvent.getCode().equals(KeyCode.R)) {
                  controller.doOneDivX();

               } else if (keyEvent.getCode().equals(KeyCode.F9)) {
                  controller.doNegate();

               }
            }
            if (keyEvent.getCode().equals(KeyCode.DIGIT1)) {
               controller.addDigitOne();
            } else if (keyEvent.getCode().equals(KeyCode.DIGIT2)) {
               controller.addDigitTwo();

            } else if (keyEvent.getCode().equals(KeyCode.DIGIT3)) {
               controller.addDigitThree();

            } else if (keyEvent.getCode().equals(KeyCode.DIGIT4)) {
               controller.addDigitFour();

            } else if (keyEvent.getCode().equals(KeyCode.DIGIT5)) {
               controller.addDigitFive();

            } else if (keyEvent.getCode().equals(KeyCode.DIGIT6)) {
               controller.addDigitSix();

            } else if (keyEvent.getCode().equals(KeyCode.DIGIT7)) {
               controller.addDigitSeven();

            } else if (keyEvent.getCode().equals(KeyCode.DIGIT8)) {
               controller.addDigitEight();

            } else if (keyEvent.getCode().equals(KeyCode.DIGIT9)) {
               controller.addDigitNine();

            } else if (keyEvent.getCode().equals(KeyCode.DIGIT0)) {
               controller.addDigitZero();

            } else if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
               controller.deleteDigit();

            } else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
               controller.deleteAllDigitsAndHistory();

            } else if (keyEvent.getCode().equals(KeyCode.DELETE)) {
               controller.deleteAllDigits();
            }
         }
      }
   }
}
