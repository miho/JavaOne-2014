package eu.mihosoft.javaone2014.con5199;

import eu.mihosoft.vrl.javaone2014.FXMLSlide;
import eu.mihosoft.vrl.javaone2014.ImageSlide;
import eu.mihosoft.vrl.javaone2014.Presentation;
import eu.mihosoft.vrl.javaone2014.Slide;
import eu.mihosoft.vrl.javaone2014.SlideApplication;
import eu.mihosoft.vrl.javaone2014.TitleSlide;
import eu.mihosoft.vrl.javaone2014.TransitionType;
import java.text.DecimalFormat;

public class Main extends SlideApplication {

    private static final String PREFIX = "/eu/mihosoft/vrl/javaone2014/con5199/";

    @Override
    public void initPresentation(Presentation presentation) {
        presentation.setTitle("JavaOne 2014 [CON5199]");

        presentation.addSlide(new ImageSlide(PREFIX + "01-title.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "01b-outline.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "robot-overview-01.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "robot-overview-02.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "robot-overview-03.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "robot-overview-04.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "robot-overview-05.png"));

        presentation.addSlide(new TitleSlide("Turning a Servo into a Motor"));
        presentation.addSlide(new ImageSlide(PREFIX + "servo-motor-01.jpg"));
        presentation.addSlide(new ImageSlide(PREFIX + "servo-motor-02.jpg"));
        presentation.addSlide(new ImageSlide(PREFIX + "servo-motor-03.jpg"));
        presentation.addSlide(new ImageSlide(PREFIX + "servo-motor-04.jpg"));
        presentation.addSlide(new ImageSlide(PREFIX + "servo-motor-05.jpg"));
        presentation.addSlide(new ImageSlide(PREFIX + "servo-motor-06.jpg"));
        presentation.addSlide(new ImageSlide(PREFIX + "servo-motor-07.jpg"));
        presentation.addSlide(new ImageSlide(PREFIX + "servo-motor-08.jpg"));

        presentation.addSlide(new ImageSlide(PREFIX + "servo&pwm-01.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "full-circuit-01.png"));

        presentation.addSlide(new ImageSlide(PREFIX + "3dprinting-01.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "3dprinting-02.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "3dprinting-03.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "3dprinting-04.png"));

        presentation.addSlide(new TitleSlide("How to create 3D Geometries?"));

        presentation.addSlide(new ImageSlide(PREFIX + "software-04.png"));

        presentation.addSlide(new ImageSlide(PREFIX + "jcsg-01.png"));
        presentation.addSlide(new FXMLSlide(PREFIX + "JCSGSlide1.fxml").
                setTitle("JCSG (Union)"));
        presentation.addSlide(new FXMLSlide(PREFIX + "JCSGSlide2.fxml").
                setTitle("JCSG (Difference)"));
        presentation.addSlide(new FXMLSlide(PREFIX + "JCSGSlide3.fxml").
                setTitle("JCSG (Intersect)"));
        presentation.addSlide(new FXMLSlide(PREFIX + "JCSGSlide4.fxml").
                setTitle("JCSG (Hull)"));

        presentation.addSlide(new ImageSlide(PREFIX + "pow-software-stack-01.png"));

        presentation.addSlide(new ImageSlide(PREFIX + "software-01.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "software-02.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "software-03.png"));

        presentation.addSlide(new ImageSlide(PREFIX + "pow-lab-01.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "pow-lab-02.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "pow-lab-03.png"));

        presentation.addSlide(new ImageSlide(PREFIX + "pow-api.png"));
        
        Slide[] xbees = new Slide[11];
        
        DecimalFormat numberFormat = new DecimalFormat("000");
        
        for(int i = 0; i < xbees.length;i++) {
            String slideURL = PREFIX + "XBee-Slides/XBee."
                    +numberFormat.format(i+1)+".png";
            System.out.println(slideURL);
            xbees[i] = new ImageSlide(slideURL).setTitle("");
        }
        
        presentation.addSlides(TransitionType.NONE, xbees);

        presentation.addSlide(new ImageSlide(PREFIX + "98-thanks.png"));
        presentation.addSlide(new ImageSlide(PREFIX + "99-q&a.png"));
    }
}
