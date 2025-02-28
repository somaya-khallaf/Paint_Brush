package paint_brush;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape {

    private int dim1;
    private int dim2;
    private int dim3;
    private int dim4;
    private Color color;

    private boolean solid;
    private boolean dotted;

    public Shape(int d1, int d2, int d3, int d4, Color c, boolean s, boolean d) {
        dim1 = d1;
        dim2 = d2;
        dim3 = d3;
        dim4 = d4;
        color = c;
        solid = s;
        dotted = d;
    }

    public void setDotted(boolean dotted) {
        this.dotted = dotted;
    }

    public boolean getDotted() {
        return dotted;
    }

    public void setDim1(int d1) {
        dim1 = d1;
    }

    public int getDim1() {
        return dim1;
    }

    public void setDim2(int d2) {
        dim2 = d2;
    }

    public int getDim2() {
        return dim2;
    }

    public void setDim3(int d3) {
        dim3 = d3;
    }

    public int getDim3() {
        return dim3;
    }

    public void setDim4(int d4) {
        dim4 = d4;
    }

    public int getDim4() {
        return dim4;
    }

    public void setSolid(boolean b) {
        solid = b;
    }

    public boolean getSolid() {
        return solid;
    }

    public Color getColor() {
        return color;
    }

    abstract void draw(Graphics g);

}
