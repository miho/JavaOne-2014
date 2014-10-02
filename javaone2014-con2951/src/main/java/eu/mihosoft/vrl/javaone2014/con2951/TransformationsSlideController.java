/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2014.con2951;

import at.bestsolution.javafx.ide.editor.ContentProposalComputer;
import eu.mihosoft.vrl.ide.CodeProposal;
import eu.mihosoft.vrl.ide.RedirectableStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import eu.mihosoft.vrl.ide.VCodeEditor;
import eu.mihosoft.vrl.javaone2014.Slide;
import eu.mihosoft.vrl.javaone2014.ViewMode;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class TransformationsSlideController implements Initializable {

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
    private VBox demoPane;
    @FXML
    private Circle circle;
    @FXML
    private Rectangle rectangle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        editor = new VCodeEditor();

        editor.addProposal(new CodeProposal(
                ContentProposalComputer.Type.TYPE, "VNode", "VNode"));
        editor.addProposal(new CodeProposal(
                ContentProposalComputer.Type.TYPE, "VFlow", "VFlow"));
        editor.addProposal(new CodeProposal(
                ContentProposalComputer.Type.TYPE, "FlowFactory", "FlowFactory"));
        editor.addProposal(new CodeProposal(
                ContentProposalComputer.Type.TYPE, "FXSkinFactory", "FXSkinFactory"));

        editorPane.getChildren().add(editor);

        AnchorPane.setTopAnchor(editor, 0.0);
        AnchorPane.setBottomAnchor(editor, 0.0);
        AnchorPane.setLeftAnchor(editor, 0.0);
        AnchorPane.setRightAnchor(editor, 0.0);

        editor.setText(
                "// scale shape #1\n"
                + "\n\n"
                + "// rotate shape #2\n");

        editor.setBlendMode(BlendMode.MULTIPLY);

        runBtn.toFront();
        resetBtn.toFront();

        demoPane.getStylesheets().add("/eu/mihosoft/vrl/workflow/fx/default.css");
    }

    public void setSlide(Slide s) {
        this.slide = s;

        s.getView().parentProperty().addListener(
                (ov, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }

                    // redirect sout
                    RedirectableStream sout = new RedirectableStream(
                            RedirectableStream.ORIGINAL_SOUT, outputArea);
                    sout.setRedirectToUi(true);
                    System.setOut(sout);

                    // redirect serr
                    serr = new RedirectableStream(
                            RedirectableStream.ORIGINAL_SERR, outputArea);
                    serr.setRedirectToUi(false);
                    System.setErr(serr);
                });

        s.viewModeProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue == ViewMode.INDEX) {
                //
            }
        });
    }

    @FXML
    private void onCompile(ActionEvent e) {
        serr.setRedirectToUi(true);
        outputArea.setText("");
        System.out.println("----------------------------------\n>> compiling...");

        CompilerConfiguration cConfig = new CompilerConfiguration();
        cConfig.addCompilationCustomizers(new ImportCustomizer().
                addStarImports(
                        "javafx.scene.shape",
                        "javafx.scene.transform"));

        GroovyShell shell = new GroovyShell(cConfig);
        Script script = shell.parse(editor.getText());

//        demoPane.getChildren().clear();
//        VCanvas canvas = new VCanvas();
//        canvas.setTranslateToMinNodePos(false);
//        canvas.setMaxScaleX(1);
//        canvas.setMaxScaleY(1);
////        canvas.setStyle("-fx-border-color: black;");
//        canvas.setContentPane(new Pane());
//        demoPane.getChildren().add(canvas);
//        AnchorPane.setTopAnchor(canvas, 0.0);
//        AnchorPane.setBottomAnchor(canvas, 0.0);
//        AnchorPane.setLeftAnchor(canvas, 0.0);
//        AnchorPane.setRightAnchor(canvas, 0.0);
        script.setProperty("canvas", demoPane);
        script.setProperty("circle", circle);
        script.setProperty("rectangle", rectangle);

        System.out.println("----------------------------------\n>> runing...");
        script.run();
        serr.setRedirectToUi(false);
    }

    @FXML
    private void onReset(ActionEvent e) {

        editor.setText(
                "// scale shape #1\n"
                + "Scale scale = new Scale(0.5, 0.5);\n"
                + "circle.getTransforms().add(scale);"
                + "\n\n"
                + "// rotate shape #2\n"
                + "Rotate rotate = new Rotate(45);\n"
                + "rotate.setPivotX(rectangle.getWidth()*0.5);\n"
                + "rotate.setPivotY(rectangle.getHeight()*0.5);\n"
                + "rectangle.getTransforms().add(rotate);"
                + "\n\n"
        );

        Platform.runLater(() -> {
            serr.setRedirectToUi(true);
            outputArea.setText("");
        });

    }
}
