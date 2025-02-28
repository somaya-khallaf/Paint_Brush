package paint_brush;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Line extends Shape {

    public Line(int x1, int y1, int x2, int y2, Color c, boolean s, boolean d) {
        super(x1, y1, x2, y2, c, s, d);
    }

    void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (getDotted()) {
            float[] dashPattern = {5, 5};
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
        } else {
            g2d.setStroke(new BasicStroke());
        }
        g.setColor(getColor());
        g.drawLine(getDim1(), getDim2(), getDim3(), getDim4());
    }
}
