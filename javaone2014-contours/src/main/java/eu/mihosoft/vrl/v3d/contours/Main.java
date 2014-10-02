/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.v3d.contours;

import eu.mihosoft.vrl.visolines.Data_float;
import eu.mihosoft.vrl.visolines.Path_float;
import eu.mihosoft.vrl.workflow.fx.ScalableContentPane;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class Main extends Application {

    private final int size = 64;
    private final float scale = 15f;
    private final MetaCircle[] metaBalls = new MetaCircle[10];
    private final Node[] nodes = new Node[10];
    private final Data_float forceField = new Data_float(size,size);
    private List<Path> prevPaths = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        ScalableContentPane root = getRootPane();

        Scene scene = new Scene(root,
                forceField.getWidth()*scale, forceField.getHeight()*scale);

        stage.setTitle("Delicious Meatballs");
        stage.setScene(scene);
        stage.show();

    }

    public ScalableContentPane getRootPane() {
        // init force field
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                forceField.set((byte)0,x,y);
            }
        }
        MetaCircleWorld mcWorld = new MetaCircleWorld(size, size, scale, scale);
        for (int i = 0; i < metaBalls.length; i++) {
            MetaCircle mc = new MetaCircle();
            metaBalls[i] = mc;
            mcWorld.addMetaCircle(mc);
        }
        ScalableContentPane root = new ScalableContentPane();
        Pane pane = new Pane();
        root.setContentPane(pane);
        double w = forceField.getWidth()*scale;
        double h = forceField.getHeight()*scale;
        root.setMaxSize(w, h);
        root.setPrefSize(w, h);
        root.setMinSize(w, h);
        pane.setMinSize(w,h);
        pane.setMaxSize(w,h);
        pane.setPrefSize(w,h);
        for (int i = 0; i < metaBalls.length; i++) {
            final Circle c1 = new Circle(20);
            c1.setLayoutX(forceField.getWidth()*scale*0.5);
            c1.setLayoutY(forceField.getHeight()*scale*0.5);
            
            c1.setFill(Color.GREEN);
            
            nodes[i] = c1;

            pane.getChildren().add(c1);

            MouseControlUtil.makeDraggable(c1);
            final int finalI = i;
            c1.setOnZoom((ZoomEvent event) -> {
                final double zoom = event.getZoomFactor();
                c1.setRadius(zoom*c1.getRadius());
                metaBalls[finalI].setStrength(metaBalls[finalI].getStrength()*zoom*zoom);
            });
            
            c1.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
                    if (metaBalls[finalI].getStrength()<0) {
                        c1.setFill(Color.GREEN);
                    } else {
                        c1.setFill(Color.RED);
                    }
                    metaBalls[finalI].setStrength(metaBalls[finalI].getStrength()*-1);
                    updateForceField(pane, nodes, mcWorld);
                }
            });

            c1.boundsInParentProperty().addListener((ov, oldV, newV) -> {
                updateForceField(pane, nodes, mcWorld);
            });
        }
        updateForceField(pane, nodes, mcWorld);
        return root;
    }

    private void updateForceField(Pane p, Node[] metaballs, MetaCircleWorld mcWorld) {


        for (int i = 0; i < metaBalls.length; i++) {
            MetaCircle circle1 = metaBalls[i];
            circle1.position().x = (float) metaballs[i].getLayoutX() / scale;
            circle1.position().y = (float) metaballs[i].getLayoutY() / scale;
        }

        List<Path_float> paths = mcWorld.update();
        Collections.reverse(paths);
        List<Path> fxPaths = new ArrayList<>();
        Color fill = new Color(1, 1, 0, 1);
        for (Path_float path : paths) {
            Path jfxPath = path.toJavaFXPath(true,scale, scale);
            jfxPath.setStroke(Color.BLACK);
            jfxPath.setFill(fill);
            fxPaths.add(jfxPath);
            fill = new Color(fill.getRed()*0.95, fill.getGreen()*0.9,0, fill.getOpacity());
        }

        p.getChildren().addAll(fxPaths);

        fxPaths.forEach(fxNode -> fxNode.toBack());

        p.getChildren().removeAll(prevPaths);

        prevPaths = fxPaths;
    }

}
