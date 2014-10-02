/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.javaone2014.con5199;

import eu.mihosoft.vrl.fxscad.VFX3DUtil;
import eu.mihosoft.vrl.javaone2014.Slide;
import eu.mihosoft.vrl.javaone2014.ide.CodeEditorFactory;
import eu.mihosoft.vrl.javaone2014.ide.RedirectableStream;
import eu.mihosoft.vrl.v3d.CSG;
import eu.mihosoft.vrl.v3d.MeshContainer;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import java.net.URL;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.text.Font;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.StyleSpansBuilder;
import org.reactfx.Change;
import org.reactfx.EventStream;
import org.reactfx.EventStreams;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class JCSGHullController implements Initializable {

    private Slide slide;

    @FXML
    private AnchorPane editorContainer;

    @FXML
    private StackPane view;

    @FXML
    private TextArea output;

    protected final CodeArea editor = new CodeArea();

    private final Group viewGroup = new Group();

    private static final String[] KEYWORDS = new String[]{
        "abstract", "assert", "boolean", "break", "byte",
        "case", "catch", "char", "class", "const",
        "continue", "default", "do", "double", "else",
        "enum", "extends", "final", "finally", "float",
        "for", "goto", "if", "implements", "import",
        "instanceof", "int", "interface", "long", "native",
        "new", "package", "private", "protected", "public",
        "return", "short", "static", "strictfp", "super",
        "switch", "synchronized", "this", "throw", "throws",
        "transient", "try", "void", "volatile", "while"
    };

    private static final Pattern KEYWORD_PATTERN
            = Pattern.compile("\\b(" + String.join("|", KEYWORDS) + ")\\b");

    public JCSGHullController() {

//        gcl = new GroovyClassLoader(VLangModelSlide01Controller.class.getClassLoader(), cc, false);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

       
        editor.getStylesheets().add(CodeEditorFactory.class.getResource("groovy-keywords.css").toExternalForm());
        editor.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        editor.setFont(new Font(24));
        
        editor.textProperty().addListener(
                (ObservableValue<? extends String> observable,
                        String oldText, String newText) -> {
                    Matcher matcher = KEYWORD_PATTERN.matcher(newText);
                    int lastKwEnd = 0;
                    StyleSpansBuilder<Collection<String>> spansBuilder
                    = new StyleSpansBuilder<>();
                    while (matcher.find()) {
                        spansBuilder.add(Collections.emptyList(),
                                matcher.start() - lastKwEnd);
                        spansBuilder.add(Collections.singleton("keyword"),
                                matcher.end() - matcher.start());
                        lastKwEnd = matcher.end();
                    }
                    spansBuilder.add(Collections.emptyList(),
                            newText.length() - lastKwEnd);
                    editor.setStyleSpans(0, spansBuilder.create());
                });

        EventStream<Change<String>> textEvents
                = EventStreams.changesOf(editor.textProperty());

        textEvents.reduceSuccessions((a, b) -> b, Duration.ofMillis(500)).
                subscribe(code -> compile(code.getNewValue()));

        editor.replaceText("\n"
                + "CSG cube = new Cube(2).toCSG()\n"
                + "CSG sphere = new Sphere(1.45,32,16).toCSG()\n"
                + "\n"
                + "cube.hull(sphere)\n");
       

        editorContainer.getChildren().add(editor);

        AnchorPane.setTopAnchor(editor, 0.0);
        AnchorPane.setBottomAnchor(editor, 0.0);
        AnchorPane.setLeftAnchor(editor, 0.0);
        AnchorPane.setRightAnchor(editor, 0.0);

        SubScene subScene = new SubScene(viewGroup, 100, 100, true, SceneAntialiasing.BALANCED);
        //subScene.setFill(Color.BLACK);
        subScene.setStyle("-fx-border-color: black;");

        subScene.widthProperty().bind(view.widthProperty());
        subScene.heightProperty().bind(view.heightProperty());

        PerspectiveCamera subSceneCamera = new PerspectiveCamera(false);
        subScene.setCamera(subSceneCamera);

        view.getChildren().add(subScene);

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
                    
                    editor.setFont(new Font(24));
                });
    }

    private void compile(String code) {
        try {

            CompilerConfiguration cc = new CompilerConfiguration();

            cc.addCompilationCustomizers(new ImportCustomizer().addStarImports("eu.mihosoft.vrl.v3d").addStaticStars("eu.mihosoft.vrl.v3d.Transform"));

            GroovyShell shell = new GroovyShell(getClass().getClassLoader(), new Binding(), cc);

//            shell.getContext().setVariable("cube", csg);
            Script script = shell.parse(code);

            Object obj = script.run();
//
//            System.out.println("obj: " + obj);

            if (obj instanceof CSG) {

                System.out.println("setting mesh");

                CSG csg = (CSG) obj;
                viewGroup.getChildren().clear();

                MeshContainer meshContainer = csg.toJavaFXMesh();

                if (meshContainer.getMeshes().isEmpty()) {
                    return;
                }

                final MeshView meshView = new MeshView(meshContainer.getMeshes().get(0));

                setMeshScale(meshContainer, view.getBoundsInLocal(), meshView);

                PhongMaterial m = new PhongMaterial(Color.RED);

                meshView.setCullFace(CullFace.NONE);

                meshView.setMaterial(m);

                viewGroup.layoutXProperty().bind(view.widthProperty().divide(2));
                viewGroup.layoutYProperty().bind(view.heightProperty().divide(2));

                view.boundsInLocalProperty().addListener(
                        (ObservableValue<? extends Bounds> ov, Bounds t, Bounds t1) -> {
                            setMeshScale(meshContainer, t1, meshView);
                        });

                VFX3DUtil.addMouseBehavior(meshView, view, MouseButton.PRIMARY);

                viewGroup.getChildren().add(meshView);

            }

        } catch (Throwable ex) {
            ex.printStackTrace(System.err);
        }
    }

    private void setMeshScale(MeshContainer meshContainer, Bounds t1, final MeshView meshView) {
        double maxDim
                = Math.max(meshContainer.getWidth(),
                        Math.max(meshContainer.getHeight(), meshContainer.getDepth()));

        double minContDim = Math.min(t1.getWidth(), t1.getHeight());

        double scale = minContDim / (maxDim * 2);

        //System.out.println("scale: " + scale + ", maxDim: " + maxDim + ", " + meshContainer);
        meshView.setScaleX(scale);
        meshView.setScaleY(scale);
        meshView.setScaleZ(scale);
    }

    @FXML
    protected void onCase1(ActionEvent evt) {
        editor.replaceText("\n"
                + "CSG cube = new Cube(2).toCSG()\n"
                + "CSG sphere = new Sphere(1.45,32,16).toCSG()\n"
                + "\n"
                + "cube.hull(sphere)\n");
    }

    @FXML
    protected void onCase2(ActionEvent evt) {
       editor.replaceText("\n"
                + "CSG cube = new Cube(2).toCSG()\n"
                + "CSG sphere = new Sphere(1.25,32,16).toCSG().\n"
                + "transformed(unity().translateX(1.0))"
                + "\n"
                + "cube.hull(sphere)\n");
    }

    @FXML
    protected void onCase3(ActionEvent evt) {
        editor.replaceText("\n"
                + "CSG sphere = new Sphere(1.25,32,16).toCSG()\n"
                + "CSG sphere2 = new Sphere(1.25,32,16).toCSG().\n"
                + "transformed(unity().translateX(3.0))"
                + "\n"
                + "sphere2.hull(sphere)\n");

    }
}
