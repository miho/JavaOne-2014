///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package eu.mihosoft.vrl.visolines;
//
//import eu.mihosoft.vrl.v3d.imagej.BSpline;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.LineTo;
//import javafx.scene.shape.MoveTo;
//
///**
// *
// * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
// * @param <T>
// */
//public class Path<T> {
//
//    private final T isoValue;
//
//    private final List<List<Vector2d>> contours;
//    private List<Vector2d> vertices;
//
//    private final BSpline optimizer = new BSpline();
//
//    public Path(T isoValue) {
//        contours = new ArrayList<>();
//        vertices = new ArrayList<>();
//        contours.add(vertices);
//        this.isoValue = isoValue;
//    }
//
//    public int getNumberOfContours() {
//        return contours.size();
//    }
//
//    public List<Vector2d> getContour(int i) {
//        return contours.get(i);
//    }
//
//    public void moveTo(double x, double y) {
//        moveTo(new Vector2d(x, y));
//    }
//
//    public void moveTo(Vector2d p) {
//        breakContour();
//        vertices.add(p);
//
//    }
//
//    public void lineTo(Vector2d p) {
//        vertices.add(p);
//    }
//
//    public void lineTo(double x, double y) {
//        lineTo(new Vector2d(x, y));
//    }
//
//    public void lineTo(Vector2d... p) {
//        lineTo(Arrays.asList(p));
//    }
//
//    public void lineTo(Collection<Vector2d> p) {
//        this.vertices.addAll(p);
//    }
//
//    public void breakContour() {
//        if (!vertices.isEmpty()) {
//            vertices = new ArrayList<>();
//            contours.add(vertices);
//        }
//    }
//
//    /**
//     * @return the isoValue
//     */
//    public T getIsoValue() {
//        return isoValue;
//    }
//
//    public boolean isEmpty() {
//        return contours.isEmpty() || contours.stream().allMatch(verts -> verts.isEmpty());
//    }
//
//    public javafx.scene.shape.Path toJavaFXPath(boolean optimized, double scaleX, double scaleY) {
//
//        javafx.scene.shape.Path jfxPath = new javafx.scene.shape.Path();
//
//        if (this.isEmpty()) {
//            return jfxPath;
//        }
//
//        for (int i = 0; i < this.getNumberOfContours(); i++) {
//            List<Vector2d> path = this.getContour(i);
//            if (path.isEmpty()) {
//                continue;
//            }
//
//            if (optimized) {
//                path = optimizer.convert(path, true);
//            }
//
//            Vector2d firstP = path.get(0);
//            jfxPath.getElements().add(
//                    new MoveTo(firstP.x * scaleX, firstP.y * scaleY));
//            for (int j = 1; j < path.size(); j++) {
//
//                Vector2d p = path.get(j);
//
//                jfxPath.getElements().add(
//                        new LineTo(p.x * scaleX, p.y * scaleY));
//            }
//
//        }
//
//        jfxPath.getElements().add(new javafx.scene.shape.ClosePath());
//
//        return jfxPath;
//    }
//
//    public javafx.scene.shape.Path toJavaFXPath(boolean optimized) {
//        return toJavaFXPath(optimized, 1, 1);
//    }
//
//    public javafx.scene.shape.Path toJavaFXPath(double scaleX, double scaleY) {
//        return toJavaFXPath(true, scaleX, scaleY);
//    }
//
//}
