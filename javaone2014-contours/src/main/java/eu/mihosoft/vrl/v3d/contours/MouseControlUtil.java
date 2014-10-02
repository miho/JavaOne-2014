/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.mihosoft.vrl.v3d.contours;

/**
 * MouseControlUtil.java
 *
 * Copyright (c) 2011-2014, JFXtras
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the organization nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;


/**
 * This is a utility class that provides methods for mouse gesture control.
 * Currently, it can be used to make nodes draggable.
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class MouseControlUtil {

    // no instanciation allowed
    private MouseControlUtil() {
        throw new AssertionError(); // not in this class either!
    }

    /**
     * Makes a node draggable via mouse gesture.
     *
     * <p> <b>Note:</b> Existing handlers will be replaced!</p>
     *
     * @param n the node that shall be made draggable
     */
    public static void makeDraggable(final Node n) {

        makeDraggable(n, null, null);
    }


    

    /**
     * Makes a node draggable via mouse gesture.
     *
     * <p> <b>Note:</b> Existing handlers will be replaced!</p>
     *
     * @param n the node that shall be made draggable
     * @param dragHandler additional drag handler
     * @param pressHandler additional press handler
     */
    public static void makeDraggable(final Node n,
            EventHandler<MouseEvent> dragHandler,
            EventHandler<MouseEvent> pressHandler) {


//        n.setOnMouseDragged(dragHandler);
//        n.setOnMousePressed(pressHandler);

        n.layoutXProperty().unbind();
        n.layoutYProperty().unbind();

        _makeDraggable(n, dragHandler, pressHandler);
    }

//    public static void makeResizable(Node n) {
//        
//    }
    private static void _makeDraggable(
            final Node n,
            EventHandler<MouseEvent> dragHandler,
            EventHandler<MouseEvent> pressHandler) {


        DraggingControllerImpl draggingController =
                new DraggingControllerImpl();
        draggingController.apply(n, dragHandler, pressHandler);
    }
}

class DraggingControllerImpl {

    private double nodeX;
    private double nodeY;
    private double mouseX;
    private double mouseY;
    private EventHandler<MouseEvent> mouseDraggedEventHandler;
    private EventHandler<MouseEvent> mousePressedEventHandler;

    public DraggingControllerImpl() {
        //
    }

    public void apply(Node n,
            EventHandler<MouseEvent> draggedEvtHandler,
            EventHandler<MouseEvent> pressedEvtHandler) {
        init(n);
    }

    private void init(final Node n) {
        mouseDraggedEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                performDrag(n, event);

                event.consume();
            }
        };

        mousePressedEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                performDragBegin(n, event);
                event.consume();
            }
        };
        
        n.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDraggedEventHandler);
        n.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedEventHandler);
    }

    public void performDrag(
            Node n, MouseEvent event) {
        final double parentScaleX = n.getParent().
                localToSceneTransformProperty().getValue().getMxx();
        final double parentScaleY = n.getParent().
                localToSceneTransformProperty().getValue().getMyy();

        // Get the exact moved X and Y

        double offsetX = event.getSceneX() - mouseX;
        double offsetY = event.getSceneY() - mouseY;

        nodeX += offsetX;
        nodeY += offsetY;

        double scaledX = nodeX * 1 / parentScaleX;
        double scaledY = nodeY * 1 / parentScaleY;

        n.setLayoutX(scaledX);
        n.setLayoutY(scaledY);

        // again set current Mouse x AND y position
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();
    }

    public void performDragBegin(
            Node n, MouseEvent event) {

        final double parentScaleX = n.getParent().
                localToSceneTransformProperty().getValue().getMxx();
        final double parentScaleY = n.getParent().
                localToSceneTransformProperty().getValue().getMyy();

        // record the current mouse X and Y position on Node
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();

        nodeX = n.getLayoutX() * parentScaleX;
        nodeY = n.getLayoutY() * parentScaleY;

        n.toFront();
    }
}

