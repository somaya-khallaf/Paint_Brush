package paint_brush;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Rectangle extends Shape {

    public Rectangle(int x, int y, int w, int h, Color c, boolean s, boolean d) {
        super(x, y, w, h, c, s, d);
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
        if (getSolid()) {
            g.fillRect(getDim1(), getDim2(), getDim3(), getDim4());
        } else {
            g.drawRect(getDim1(), getDim2(), getDim3(), getDim4());
        }
    }
}
