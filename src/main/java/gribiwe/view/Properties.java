package gribiwe.view;

/**
 * class for keeping global vars
 * about sizes, etc.
 */
public class Properties {

   /**
    * width of left side menu
    */
   static final double SIDE_MENU_WIDTH = 255.0;

   /**
    * pixels out of visible calculator
    * for resize in X coordinate
    */
   public final static double X_BORDER = 8;

   /**
    * pixels out of visible calculator
    * for resize in X coordinate
    */
   public final static double Y_BORDER = 6;

   /**
    * height of monitor screen
    */
   private static int screenHeight = 0;

   /**
    * width of monitor screen
    */
   private static int screenWidth = 0;

   static void setScreenHeight(int screenHeight) {
      Properties.screenHeight = screenHeight;
   }

   static void setScreenWidth(int screenWidth) {
      Properties.screenWidth = screenWidth;
   }

   static int getScreenHeight() {
      return screenHeight;
   }

   public static int getScreenWidth() {
      return screenWidth;
   }
}
