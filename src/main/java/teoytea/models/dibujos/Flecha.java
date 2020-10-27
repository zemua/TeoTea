/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.dibujos;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

/**
 *
 * @author https://stackoverflow.com/questions/2027613/how-to-draw-a-directed-arrow-line-in-java
 */
public class Flecha {

    public static Shape createArrowShape(Point fromPt, Point toPt) {
        Polygon arrowPolygon = new Polygon();
        arrowPolygon.addPoint(-6, 1);
        arrowPolygon.addPoint(3, 1);
        arrowPolygon.addPoint(3, 3);
        arrowPolygon.addPoint(6, 0);
        arrowPolygon.addPoint(3, -3);
        arrowPolygon.addPoint(3, -1);
        arrowPolygon.addPoint(-6, -1);

        Point midPoint = midpoint(fromPt, toPt);

        double rotate = Math.atan2(toPt.y - fromPt.y, toPt.x - fromPt.x);

        AffineTransform transform = new AffineTransform();
        transform.translate(midPoint.x, midPoint.y);
        double ptDistance = fromPt.distance(toPt);
        double scale = ptDistance / 12.0; // 12 because it's the length of the arrow polygon.
        transform.scale(scale, scale);
        transform.rotate(rotate);

        return transform.createTransformedShape(arrowPolygon);
    }

    private static Point midpoint(Point p1, Point p2) {
        return new Point((int) ((p1.x + p2.x) / 2.0),
                (int) ((p1.y + p2.y) / 2.0));
    }
}
