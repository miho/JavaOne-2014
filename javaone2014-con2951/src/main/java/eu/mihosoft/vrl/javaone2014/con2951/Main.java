package eu.mihosoft.vrl.javaone2014.con2951;

import eu.mihosoft.vrl.javaone2014.FXMLSlide;
import eu.mihosoft.vrl.javaone2014.ImageSlide;
import eu.mihosoft.vrl.javaone2014.Presentation;
import eu.mihosoft.vrl.javaone2014.Slide;
import eu.mihosoft.vrl.javaone2014.SlideApplication;
import eu.mihosoft.vrl.javaone2014.TitleSlide;
import eu.mihosoft.vrl.javaone2014.TransitionType;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class Main extends SlideApplication {

    private static final String PREFIX = "/eu/mihosoft/vrl/javaone2014/con2951/";

    @Override
    public void initPresentation(Presentation presentation) {
        presentation.setTitle("JavaOne 2014 [CON 2951]");
        presentation.addSlide(new ImageSlide(PREFIX + "01-title.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "01b-outline.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "slide-about-me.png"));
        presentation.addSlide(new TitleSlide("Interactive Visualization"));
        presentation.addSlide(new ImageSlide(PREFIX + "interactive-visualization-01.png"));

        Slide simpleVis = new FXMLSlide(PREFIX + "SimpleInteractiveVizSlide.fxml");
        simpleVis.setTitle("Interactive Visualization");
        presentation.addSlide(simpleVis);
        presentation.addSlide(new ImageSlide(PREFIX + "interactive-visualization-02-why-javafx-01.png"));

        Slide plotVis2d = new FXMLSlide("/eu/mihosoft/vrl/javaone2014/plot2d/Plotter2DSlide.fxml");
        plotVis2d.setTitle("2d Plotting Application");
        presentation.addSlide(plotVis2d);

        presentation.addSlide(new TitleSlide("Search & Categories"));
        presentation.addSlide(new ImageSlide(PREFIX + "1d-search-results-01.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "2d-search-results-01.png"));

        Slide forceFieldSlide = new FXMLSlide(PREFIX + "ForceFieldVizSlide.fxml");
        forceFieldSlide.setTitle("Force Fields");
        presentation.addSlide(forceFieldSlide);

        presentation.addSlide(new ImageSlide(PREFIX + "m-square-01.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "m-square-discretize-01.png"));

        Slide contourSlide = new FXMLSlide(PREFIX + "ContourLineVizSlide.fxml");
        contourSlide.setTitle("Contour Lines");
        presentation.addSlide(contourSlide);

        presentation.addSlide(new TitleSlide("Graphs & Workflows"));
        presentation.addSlide(new ImageSlide(PREFIX + "what-is-a-graph-01.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "graph-examples-01.png"));

        presentation.addSlide(new TitleSlide("Visualizing Graphs\nwith\nVWorkflows"));

        Slide vworkflows1 = new FXMLSlide(PREFIX + "VWorkflowsSlide1.fxml");
        vworkflows1.setTitle("VWorkflow Demo 1");
        presentation.addSlide(vworkflows1);
        Slide vworkflows2 = new FXMLSlide(PREFIX + "VWorkflowsSlide2.fxml");
        vworkflows2.setTitle("VWorkflows Demo 2");
        presentation.addSlide(vworkflows2);

        presentation.addSlide(new TitleSlide("Scalable Content/Transforms"));
        Slide transformations1 = new FXMLSlide(PREFIX + "TransformationsSlide.fxml");
        transformations1.setTitle("Transformations");
        presentation.addSlide(transformations1);

        presentation.addSlide(new TitleSlide("Scenegraph Optimization"));
        presentation.addSlide(new ImageSlide(PREFIX + "scalable-content-pane-01.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "optimizable-content-pane-01.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "optimizationrule-01.png"));

        Slide vworkflows3 = new FXMLSlide(PREFIX + "VWorkflowsSlide4.fxml");
        vworkflows3.setTitle("Scalable Content Demo");
        presentation.addSlide(vworkflows3);

        presentation.addSlide(new TitleSlide("Interactive Abstract Syntax Trees"));
        presentation.addSlide(new ImageSlide(PREFIX + "ast-01.png"));

        Slide h1 = new ImageSlide(PREFIX + "02-history-01.png");
        Slide h2 = new ImageSlide(PREFIX + "02b-history-01.png");
        Slide h3 = new ImageSlide(PREFIX + "03-history-01.png");

        presentation.addSlides(TransitionType.MOVE_HORIZONTAL, h1, h2, h3);

        presentation.addSlide(new ImageSlide(PREFIX + "04-history-01.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "05-history-01.png"));

        presentation.addSlide(new TitleSlide("VRL"));

        presentation.addSlide(new ImageSlide(PREFIX + "16-visual-compiler-01.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "16b-visual-compiler-01.png"));

        Slide langModel1 = new FXMLSlide(PREFIX + "VLangModelSlide01.fxml",
                new VLangModelSlide01Controller());
        langModel1.setTitle("VRL Demo 1");
        presentation.addSlide(langModel1);

        presentation.addSlide(new TitleSlide("VRL Demo 2"));

        presentation.addSlide(new TitleSlide("Physics & JavaFX"));

        Slide discretizedWorld = new FXMLSlide(PREFIX + "DiscretizedWorldSlide1.fxml");
        discretizedWorld.setTitle("Discretized World");
        presentation.addSlide(discretizedWorld);

        Slide discretizedWorld2 = new FXMLSlide(PREFIX + "DiscretizedWorldSlide.fxml");
        discretizedWorld2.setTitle("Discretized World");
        presentation.addSlide(discretizedWorld2);

        presentation.addSlide(new TitleSlide("Framerate & Timesteps"));

        Slide springSample = new FXMLSlide(PREFIX + "PhysicsSlide2.fxml");
        springSample.setTitle("Spring Example");
        presentation.addSlide(springSample);

        Slide vbox = new FXMLSlide(PREFIX + "PhysicsSlide3.fxml");
        vbox.setTitle("HBox/VBox Pane");
        presentation.addSlide(vbox);

        Slide wheelPane = new FXMLSlide(PREFIX + "PhysicsSlide1.fxml");
        wheelPane.setTitle("Wheel Pane");
        presentation.addSlide(wheelPane);

        Slide fields = new FXMLSlide(PREFIX + "PhysicsSlide4.fxml");
        fields.setTitle("Force Fields");
        presentation.addSlide(fields);

        presentation.addSlide(new ImageSlide(PREFIX + "98-thanks.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "99-q&a.png"));

//        for (int i = 0; i < 10; i++) {
//            Slide s = new TitleSlide("Slide #" + i);
//            presentation.addSlide(s);
//        }
    }

}
