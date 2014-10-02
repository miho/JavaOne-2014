/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2014;

import javafx.scene.layout.Pane;

/**
 * Presentation interface.
 *
 * A presentation contains one index slide (overview) and an arbitrary number of
 * node sildes (FXML, HTML, Image, ...)
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface Presentation {

    /**
     * Adds a slide to this presentation.
     *
     * @param s slide to add
     */
    public void addSlide(Slide s);

    /**
     * Adds two slides to this presentation with the specified transition.
     *
     * @param s1 first slide
     * @param s2 second slide
     * @param t transition
     */
    public void addSlides(Slide s1, Slide s2, TransitionType t);

    /**
     * Returns the slider controller (allows to switch slides).
     *
     * @return the slider controller
     */
    public SlideController getSlideController();

    /**
     * Returns the action controller (allows to perform or undo actions).
     *
     * @return the action controller
     */
    public ActionController getActionController();

    /**
     * Shows the index slide.
     */
    public void showIndex();

    /**
     * Returns the JavaFX node of this presentation (all visual content of this
     * presentation a child of this node).
     *
     * @return JavaFX node of this presentation
     */
    public Pane getView();

    /**
     * Defines the index slide that shall be used for this presentation.
     *
     * @param s index slide
     */
    public void setIndexSlide(IndexSlide s);

    /**
     * Determines whether this presentation currently shows the index slide.
     *
     * @return <code>true</code> if this presentation currently shows the index
     * slide; <code>false</code> otherwise
     */
    public boolean showsIndex();

    /**
     * Defines the application title.
     *
     * @param title title
     */
    public void setTitle(String title);

    /**
     * Returns the application title.
     *
     * @return application title
     */
    public String getTitle();

    /**
     * Defines transition between the specified slides.
     *
     * @param s1 first slide
     * @param s2 second slide
     * @param transitionType transition type
     */
    public void setTransition(Slide s1, Slide s2, TransitionType transitionType);

    /**
     * Adds the specified slides.
     *
     * @param slides slides to add
     */
    public void addSlides(Slide... slides);

    /**
     * Adds the specified slides and defines the transition between the slides.
     *
     * @param transition transition type
     * @param slides slides to add
     */
    public void addSlides(TransitionType transition, Slide... slides);
}
