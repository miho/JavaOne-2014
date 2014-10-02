/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2014;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Slide that displays fxml content.
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class FXMLSlide extends TemplateSlide {

    /**
     * Location of the fxml resource path
     *
     * @param fxmlPath path
     */
    public FXMLSlide(String fxmlPath, Object controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));

        if (controller != null) {

            fxmlLoader.setController(controller);
        }

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(FXMLSlide.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        Parent p = fxmlLoader.getRoot();

//        StackPane.setAlignment(p, Pos.CENTER);
        getContent().getChildren().add(p);
        try {
            
            controller = fxmlLoader.getController();

            Method m = controller.getClass().getMethod("setSlide", Slide.class);
            m.invoke(controller, this);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NullPointerException ex) {
           Logger.getLogger(FXMLSlide.class.getName()).log(Level.WARNING, "method setSlide() not implemented for slide " +  getTitle());
        }
    }

    public FXMLSlide(String fxmlPath) {
        this(fxmlPath, null);
    }
}
