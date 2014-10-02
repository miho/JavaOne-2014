package eu.mihosoft.vrl.javaone2014.con2951.physlayout.testing;

import eu.mihosoft.vrl.javaone2014.con2951.physlayout.layout.PhysLayout;
import eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.Box2DSpringSimulation;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * An abstract template for example applications. This class creates the basic
 * prerequisites (layout and simulation) along with a GUI to start, stop and
 * reset the simulation.
 *
 * @author Christoph Burschka &lt;christoph@burschka.de&gt;
 */
public abstract class Example extends Application {

    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    private Box2DSpringSimulation simulation;
    final PhysLayout layout;
    Pane canvas;
    BorderPane root;
    ToolBar menu;
    Stage primaryStage;
    private final Button startStop, reset, step;

    /**
     * Super-constructor for all examples.
     */
    public Example() {
        root = new BorderPane();
        menu = new ToolBar();
        canvas = new Pane();
        layout = new PhysLayout(canvas);
        root.setCenter(canvas);
        root.setTop(menu);

        reset = new Button("Reset");
        startStop = new Button("Start");
        step = new Button("Step");
        Button exit = new Button("Exit");
        menu.getItems().addAll(startStop, step, reset);
        startStop.setMinWidth(startStop.getWidth() + 50);

        exit.setOnAction((event) -> {
            System.exit(0);
        });

        reset.setOnAction((ActionEvent event) -> {
            simulation.stopSimulation();
            reset();
        });

        setSimulation(new Box2DSpringSimulation(layout));
    }

    public Pane getRootPane() {
        return root;
    }

    protected Box2DSpringSimulation getSimulation() {
        return simulation;
    }

    /**
     * Replace the example applications main simulator. The new simulator will
     * be attached to the button controls.
     *
     * @param simulation
     */
    protected final void setSimulation(Box2DSpringSimulation simulation) {
        this.simulation = simulation;

        simulation.getRunning().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            startStop.setText(newValue ? "Stop" : "Start");
        });

        startStop.setOnAction((ActionEvent event) -> {
            if (simulation.isRunning()) {
                simulation.stopSimulation();
            } else {
                simulation.startSimulation();
            }
        });
        step.setOnAction((ActionEvent event) -> {
            if (!simulation.isRunning()) {
                simulation.updateModel();
                simulation.step();
                simulation.updateView();
            }
        });
        step.disableProperty().bind(simulation.getRunning());
    }

    /**
     * Sets up the application. This is called internally; the proper method to
     * start the application is launch().
     *
     * @param primaryStage
     */
    @Override
    public final void start(Stage primaryStage) {

        reset();
        this.primaryStage = primaryStage;
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle(getTitle());
        primaryStage.show();
    }

    public void startSimulation() {
        simulation.startSimulation();
    }

    public void stopSimulation() {
        simulation.stopSimulation();
    }

    /**
     * Reset the simulation. This is called once during the setup, and whenever
     * the reset button is pressed.
     */
    public abstract void reset();

    /**
     * Get the window title.
     *
     * @return the string that the title will be set to.
     */
    public abstract String getTitle();
}
