/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2014;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public abstract class SlideApplication extends Application {

    private boolean navigationActive = true;

    private final Presentation presentation = new DefaultPresentation();

    public abstract void initPresentation(Presentation presentation);

    @Override
    public final void start(Stage stage) {
        try {
            initPresentation(presentation);
        } catch (Exception ex) {
            Logger.getLogger(SlideApplication.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        Scene scene = new Scene(presentation.getView(), 1280, 720);
        PerspectiveCamera cam = new PerspectiveCamera();
        cam.setNearClip(0.0);
        cam.setFarClip(10.0);
        scene.setCamera(cam);

        scene.addEventFilter(KeyEvent.ANY, (KeyEvent event) -> {
            if (event.getEventType().equals(KeyEvent.KEY_PRESSED)
                    && event.isAltDown()
                    && event.isControlDown()
                    && event.getCode() == KeyCode.M) {
                navigationActive = !navigationActive;
            }

            if (navigationActive
                    && event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
                if (event.getCode() == KeyCode.LEFT) {
                    if (!presentation.showsIndex()) {
                        presentation.getActionController().prev();
                    }
                } else if (event.getCode() == KeyCode.RIGHT) {
                    if (presentation.showsIndex()) {
                        presentation.getSlideController().setIndex(0);
                    } else {
                        presentation.getActionController().next();
                    }
                } else if (event.getCode() == KeyCode.I) {
                    presentation.showIndex();
                } else if (event.getCode() == KeyCode.UP) {
                    if (!presentation.showsIndex()) {
                        presentation.getSlideController().prev();
                    }
                } else if (event.getCode() == KeyCode.DOWN) {
                    if (presentation.showsIndex()) {
                        presentation.getSlideController().setIndex(0);
                    } else {
                        presentation.getSlideController().next();
                    }
                }
            }
        });

        scene.setFill(new Color(122 / 255.0, 122 / 255.0, 122 / 255.0, 1));

        presentation.getView().prefWidthProperty().bind(scene.widthProperty());
        presentation.getView().prefHeightProperty().bind(scene.heightProperty());

        stage.setTitle(presentation.getTitle());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        System.exit(0);
    }
}
