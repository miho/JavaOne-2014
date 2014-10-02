/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2014.con2951;

import eu.mihosoft.vrl.ide.CodeProposal;
import eu.mihosoft.vrl.javaone2014.Slide;
import eu.mihosoft.vrl.javaone2014.ViewMode;
import eu.mihosoft.vrl.javaone2014.ide.RedirectableStream;
import eu.mihosoft.vrl.javaone2014.ide.VCodeEditor;
import eu.mihosoft.vrl.lang.model.CompilationUnitDeclaration;
import eu.mihosoft.vrl.lang.model.Parameter;
import eu.mihosoft.vrl.lang.model.Parameters;
import eu.mihosoft.vrl.lang.model.Scope2Code;
import eu.mihosoft.vrl.lang.model.Type;
import eu.mihosoft.vrl.lang.model.VisualCodeBuilder;
import eu.mihosoft.vrl.lang.model.VisualCodeBuilder_Impl;
import eu.mihosoft.vrl.workflow.fx.FXValueSkinFactory;
import eu.mihosoft.vrl.workflow.fx.VCanvas;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import jfxtras.labs.scene.control.window.Window;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class VLangModelSlide01Controller implements Initializable {

    @FXML
    private AnchorPane root;
    private Slide slide;
    private VCodeEditor modelEditor = new VCodeEditor();
    private VCodeEditor codeEditor = new VCodeEditor();

////    @FXML
//    private AnchorPane modelEditorPane;
////    @FXML
//    private AnchorPane codeEditorPane;
    private VCanvas uiPane;

    @FXML
    private TextArea output;

    private GroovyClassLoader gcl;

//    @FXML
//    private TextArea outputAreaErr;
//    @FXML
//    private Button runBtn;
//    @FXML
//    private Button resetBtn;
//    private RedirectableStream serr;
    public VLangModelSlide01Controller() {

//        gcl = new GroovyClassLoader(VLangModelSlide01Controller.class.getClassLoader(), cc, false);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        createUI();

    }

    private void createUI() {
        
        Window modelW = new Window("Model API");
        Window uiW = new Window("UI");
        Window codeW = new Window("Code");

        modelW.setPrefSize(400, 300);
        uiW.setPrefSize(600, 300);
        codeW.setPrefSize(400, 300);

        modelW.setLayoutX(20);
        modelW.setLayoutY(200);

        uiW.setLayoutX(20 + 400 + 20);
        uiW.setLayoutY(200);

        uiPane = new VCanvas();

        uiW.setContentPane(uiPane);

        codeW.setLayoutX(20 + 400 + 20 + 600 + 20);
        codeW.setLayoutY(200);

        configureEditor(modelW.getContentPane(), modelEditor);
//        configureEditor(uiW.getContentPane(), modelEditor);
        configureEditor(codeW.getContentPane(), codeEditor);

        root.getChildren().add(modelW);
        root.getChildren().add(uiW);
        root.getChildren().add(codeW);

        modelEditor.setText("// model code\n\nmyCls = codeBuilder.declareClass(file, new model.Type(\"demo.Main\"));\n");

        modelEditor.addProposal(CodeProposal.methodProposal("declareMethod", "m1 = codeBuilder.declareMethod(myCls, model.Type.VOID, NAME)"));
        modelEditor.addProposal(CodeProposal.methodProposal("invokeMethod", "inv1 = codeBuilder.invokeMethod(m2, VAR, m1)"));

        VisualCodeBuilder cb;
        
        root.getStylesheets().add("/eu/mihosoft/vrl/workflow/fx/default.css");
    }

    private void configureEditor(Pane pane, VCodeEditor editor) {

        pane.getChildren().add(editor);

//        AnchorPane.setTopAnchor(editor, 0.0);
//        AnchorPane.setBottomAnchor(editor, 0.0);
//        AnchorPane.setLeftAnchor(editor, 0.0);
//        AnchorPane.setRightAnchor(editor, 0.0);
        editor.setText("");

//        editor.setBlendMode(BlendMode.MULTIPLY);
    }

    private void configureGroovyCompiler(VCodeEditor editor) {

    }

    public void setSlide(Slide s) {
        this.slide = s;

        s.getView().parentProperty().addListener(
                (ov, oldParent, newParent) -> {
                    RedirectableStream sout = new RedirectableStream(RedirectableStream.ORIGINAL_SOUT, output);
//                    sout.setRedirectToStdOut(true);
                    sout.setRedirectToUi(true);
                    System.setOut(sout);

                    RedirectableStream serr = new RedirectableStream(RedirectableStream.ORIGINAL_SERR, output);
//                    sout.setRedirectToStdOut(true);
                    serr.setRedirectToUi(true);
                    System.setErr(serr);
                });
        

//        s.viewModeProperty().addListener((ov, oldValue, newValue) -> {
//            if (newValue == ViewMode.FULL) {
//                Pane p = new eu.mihosoft.vrl.v3d.contours.Main().getRootPane();
////        p.setStyle("-fx-background-color: red;");
//                StackPane.setAlignment(p, Pos.CENTER);
//                createUI();
//            } else {
//                
//            }
//        });
    }

    @FXML
    private void onCompile(ActionEvent e) {
        System.out.println("----------------------------------\n>> compiling...");

//        try{
//            VLangModelSlide01Controller.class.getClassLoader().loadClass("eu.mihosoft.vrl.lang.model.Type");
//        } catch(Exception ex) {
//            ex.printStackTrace();
//            System.out.println("!!GCL: not found");
//        }
        CompilerConfiguration cc = new CompilerConfiguration();

//        ImportCustomizer importCustomizer = new ImportCustomizer().addImport("Type",
//                "eu.​mihosoft.​vrl.​lang.​model.Type");//.addImports("eu.​mihosoft.​vrl.​lang.​model.Test");
//        cc.addCompilationCustomizers(importCustomizer);
//        String importCode = "import eu.​mihosoft.​vrl.​lang.model.Type;";
        GroovyShell shell = new GroovyShell(ClassLoader.getSystemClassLoader(), new Binding(), cc);

        VisualCodeBuilder codeBuilder = new VisualCodeBuilder_Impl();

        CompilationUnitDeclaration file = codeBuilder.declareCompilationUnit("Main", "demo");

        shell.setProperty("codeBuilder", codeBuilder);
        shell.setProperty("file", file);

        String code = addPackages(modelEditor.getText());

        Script script = shell.parse(code);

        System.out.println("----------------------------------\n>> running...");
        script.run();

        VLangModelSlide01Controller.class.getClassLoader();

        codeEditor.setText(Scope2Code.getCode(file));

        uiPane.getContentPane().getChildren().clear();

        file.getFlow().addSkinFactories(new FXValueSkinFactory(uiPane.getContentPane()));
    }

    @FXML
    private void onPanic(ActionEvent e) {
        System.out.println("panic");

        String code = 
                "// model code\nmyCls = codeBuilder.declareClass(file, new model.Type(\"demo.Main\"));\n\n";
        
        code += "// declaring methods\n";
        code += "m1 = codeBuilder.declareMethod(myCls, model.Type.VOID, \"m1\")\n";
        code += "m2 = codeBuilder.declareMethod(myCls, model.Type.VOID, \"m2\")\n\n";
        
        code += "// invoking methods\n";
        code += "inv1 = codeBuilder.invokeMethod(m2, \"this\", m1)\n";
        code += "inv2 = codeBuilder.invokeMethod(m2, \"this\", m1)\n";

        modelEditor.setText(code);

    }

    @FXML
    private void onReset(ActionEvent e) {
//        editor.setText(
//                "// create a groovy shell for parsing the source string\n"
//                + "GroovyShell shell = new GroovyShell();\n\n"
//                + "// parse the script (returns a script object)\n"
//                + "Script script = shell.parse(\"println(\'hello\')\");\n\n"
//                + "// finally, run the script\n"
//                + "script.run();\n");

        Platform.runLater(() -> {
            output.setText("");
        });
    }

    private String addPackages(String code) {
        return code.replace("model.", "eu.mihosoft.vrl.lang.model.");
    }
}
