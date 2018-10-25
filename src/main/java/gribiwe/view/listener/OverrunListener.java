package gribiwe.view.listener;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * class for listening a output number's overrun
 */
public class OverrunListener {

   /**
    * label which are listening by this listener
    */
   private final Label label;

   /**
    * creates listener
    * @param label label which will be listened
    */
   public OverrunListener(Label label) {
      this.label = label;
      this.label.widthProperty().addListener((observable) -> updateSizes());
      this.label.textProperty().addListener((observable) -> updateSizes());
   }

   /**
    * magic spell which shows that font at label
    * become big and it's have to be fixed
    */
   private boolean fixingBigFontSize = false;

   /**
    * value of the biggest font size
    */
   private static final double BIGGEST_FONT_SIZE = 46;

   /**
    * value of step of decreasing a font size of label for running out of overrun
    */
   private static final double STEP_FOR_DECREASING_SIZE = 0.2;

   /**
    * method for fixing a overran value of label
    */
   private void updateSizes() {
      String fontName = label.getFont().getName();
      if (fixingBigFontSize) {
         Text textNode = (Text) label.lookup(".text");
         while (true) {
            label.layout();
            String originalString = label.getText();
            String actualString = textNode.getText();

            if (!originalString.equals(actualString)) {
               label.setFont(new Font(fontName, label.getFont().getSize() - STEP_FOR_DECREASING_SIZE));
               label.layout();
            } else {
               fixingBigFontSize = false;
               break;
            }
         }
      } else{
         label.setFont(new Font(fontName, BIGGEST_FONT_SIZE));
         fixingBigFontSize = true;
         updateSizes();
      }

   }
}
