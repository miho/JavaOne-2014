/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2014.con2951;

import eu.mihosoft.vrl.ide.RedirectableStream;
import eu.mihosoft.vrl.ide.VCodeEditor;
import eu.mihosoft.vrl.javaone2014.Slide;
import eu.mihosoft.vrl.javaone2014.ViewMode;
import eu.mihosoft.vrl.workflow.ClickEvent;
import eu.mihosoft.vrl.workflow.Connector;
import eu.mihosoft.vrl.workflow.FlowFactory;
import eu.mihosoft.vrl.workflow.MouseButton;
import eu.mihosoft.vrl.workflow.VFlow;
import eu.mihosoft.vrl.workflow.VNode;
import eu.mihosoft.vrl.workflow.fx.FXSkinFactory;
import eu.mihosoft.vrl.workflow.fx.VCanvas;
import eu.mihosoft.vrl.workflow.skin.SkinFactory;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class VWorkflowsSlideController4 implements Initializable {

    @FXML
    private AnchorPane root;
    private Slide slide;
    private VCodeEditor editor;
    @FXML
    private TextArea outputArea;
    @FXML
    private AnchorPane editorPane;
    @FXML
    private Button runBtn;
    @FXML
    private Button resetBtn;
    private RedirectableStream serr;
    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;
    @FXML
    private TextField textField3;
    @FXML
    private AnchorPane demoPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        editor = new VCodeEditor();

        editorPane.getChildren().add(editor);

        AnchorPane.setTopAnchor(editor, 0.0);
        AnchorPane.setBottomAnchor(editor, 0.0);
        AnchorPane.setLeftAnchor(editor, 0.0);
        AnchorPane.setRightAnchor(editor, 0.0);

        editor.setText(
                "// create workflow\n\n");

        editor.setBlendMode(BlendMode.MULTIPLY);

        runBtn.toFront();
        resetBtn.toFront();

        demoPane.getStylesheets().add("/eu/mihosoft/vrl/workflow/fx/default.css");
    }

    public void setSlide(Slide s) {
        this.slide = s;

        s.getView().parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {

                if (newValue == null) {
                    return;
                }

                // redirect sout
                RedirectableStream sout = new RedirectableStream(RedirectableStream.ORIGINAL_SOUT, outputArea);
                sout.setRedirectToUi(true);
                System.setOut(sout);

                // redirect serr
                serr = new RedirectableStream(RedirectableStream.ORIGINAL_SERR, outputArea);
                serr.setRedirectToUi(false);
                System.setErr(serr);
            }
        });

        s.viewModeProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue == ViewMode.INDEX) {
                demoPane.getChildren().clear();
            }
        });
    }

    @FXML
    private void onCompile(ActionEvent e) {
        serr.setRedirectToUi(true);
        outputArea.setText("");
        System.out.println("----------------------------------\n>> compiling...");
        GroovyShell shell = new GroovyShell();
        Script script = shell.parse(
                "import eu.mihosoft.vrl.workflow.*;\n"
                + "import eu.mihosoft.vrl.workflow.fx.*;\n\n" + editor.getText());

        demoPane.getChildren().clear();
        demoPane.getStyleClass().setAll("vflow-background");

//        Pane p = eu.mihosoft.vrl.javaone2013.plot3d.raypicking.Main.embedSlide();
//        demoPane.getChildren().add(p);
        VFlow flow = FlowFactory.newFlow();
        flow.setVisible(true);

        counter = 0;

        demoPane.getChildren().clear();
        VCanvas canvas = new VCanvas();
        canvas.setTranslateToMinNodePos(false);
        canvas.setMaxScaleX(1);
        canvas.setMaxScaleY(1);
        canvas.setContentPane(new Pane());
        demoPane.getChildren().add(canvas);
        AnchorPane.setTopAnchor(canvas, 0.0);
        AnchorPane.setBottomAnchor(canvas, 0.0);
        AnchorPane.setLeftAnchor(canvas, 0.0);
        AnchorPane.setRightAnchor(canvas, 0.0);

        SkinFactory skinFactory = new FXSkinFactory(canvas.getContentPane());
        flow.setSkinFactories(skinFactory);

        script.setProperty("flow", flow);
        script.setProperty("controller", this);

        script.run();

        System.out.println("#nodes: " + counter);
    }

    @FXML
    private void onReset(ActionEvent e) {
        editor.setText(
                "// create flow\n"
                + "controller.createWorkflow(flow, 5, 10);"
        );

        Platform.runLater(() -> {
            serr.setRedirectToUi(true);
            outputArea.setText("");
        });

    }

    private int counter;

    public void createWorkflow(VFlow workflow, int depth, int width) {

        if (depth < 1) {
            return;
        }

        String[] connectionTypes = {"control", "data", "event"};

        for (int i = 0; i < width; i++) {

            counter++;

            VNode n;

            if (i % 2 == 0) {
                VFlow subFlow = workflow.newSubFlow();
                subFlow.setVisible(depth > 2);
                n = subFlow.getModel();
                createWorkflow(subFlow, depth - 1, width);
            } else {
                n = workflow.newNode();
            }

            n.setTitle("Node " + n.getId());

            String type = connectionTypes[i % connectionTypes.length];

            n.setMainInput(n.addInput(type));
            n.setMainInput(n.addInput("event"));

            for (int j = 0; j < 3; j++) {
                n.addInput(type);
            }

            n.addOutput(type);
            n.setMainOutput(n.addOutput("event"));
            n.addOutput(type);

            for (int j = 0; j < 3; j++) {
                n.addOutput(type);
            }

            for (final Connector connector : n.getConnectors()) {
                connector.addClickEventListener(new EventHandler<ClickEvent>() {

                    @Override
                    public void handle(ClickEvent t) {

                        if (t.getButton() != MouseButton.SECONDARY) {
                            return;
                        }
                    }
                });
            }

            n.setWidth(300);
            n.setHeight(200);

            n.setX((i % 5) * (n.getWidth() + 30));
            n.setY((i / 5) * (n.getHeight() + 30));
        }
    }
}
