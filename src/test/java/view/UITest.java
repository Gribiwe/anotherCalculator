package view;

import gribiwe.view.Starter;
import javafx.scene.Node;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runners.MethodSorters;
import org.loadui.testfx.utils.FXTestUtils;
import org.testfx.api.FxToolkit;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Parent class for view test classes.
 * Contains only one method setUp which calls before all tests.
 * This class needs for starting any single view test class
 * with needed window for it. New window will not
 * try to appear if it already appeared (e.g. if running full package test).
 * Also making initialization of {@code RobotUtil}, {@code WindowUtil}.
 * FXApplication starts at new thread by {@code ExecutorService}.
 *
 * @author Gribiwe
 * @see RobotUtil
 * @see WindowUtil
 * @see ExecutorService
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
class UITest extends Assert {

   private static final String[] args = {null};
   private static Starter bodyView;
   private static ExecutorService executorServiceFX;
   static Node borderedVisionPaneNode;
   static Robot robot;

   @BeforeAll
   static void setUp() {
      if (!FxToolkit.isFXApplicationThreadRunning()) {
         bodyView = new Starter();
         executorServiceFX = Executors.newSingleThreadExecutor();
         executorServiceFX.execute(() -> {
            bodyView.start(args);
            executorServiceFX.shutdown();
         });
         while (!FxToolkit.isFXApplicationThreadRunning()) {
            try {
               Thread.sleep(1);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }

         FXTestUtils.awaitEvents();
         WindowUtil.init();
         RobotUtil.initRobot();
         robot = RobotUtil.getRobot();
         borderedVisionPaneNode = WindowUtil.getNode("#visionPane");
      }
   }
}
