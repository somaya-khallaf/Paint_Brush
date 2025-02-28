/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint_brush;

import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class paint extends Applet implements ItemListener {

    class CalcNewDim {

        int[] CalcDimensions(int sX, int sY, int eX, int eY) {
            int[] dim = new int[4]; // 0 w , 1 h , 2 fX , 3 fY 
            if (sX < eX) {
                dim[0] = eX - sX;
            } else {
                dim[0] = sX - eX;
            }
            if (sY < eY) {
                dim[1] = eY - sY;
            } else {
                dim[1] = sY - eY;
            }

            if (eX < sX && eY > sY) {
                dim[2] = eX;
                dim[3] = sY;
            } else if (eY < sY && eX < sX) {
                dim[2] = eX;
                dim[3] = eY;
            } else if (sX < eX && sY < eY) {
                dim[2] = sX;
                dim[3] = sY;
            } else {
                dim[2] = sX;
                dim[3] = eY;
            }
            return dim;
        }
    }

    //References Button
    private Button red, green, blue, rectangle, oval, line, eraser, Free_Hand, Clear, undo;

    private Checkbox filled, dotted;

    private int shape = 0;
    static final int RECTANGLE = 1, OVAL = 2, LINE = 3;

    private int startX = 0, startY = 0, endX = 0, endY = 0, width = 0, height = 0, finalX = 0, finalY = 0;

    private boolean isDragging = false, solid = false, remove = false, Free_Hand_Flag = false, isDotted = false;

    ArrayList<Shape> shapes = new ArrayList<>();
    Color currentColor = Color.BLACK;

    public void init() {

        red = new Button("RED");
        green = new Button("GREEN");
        blue = new Button("BLUE");
        rectangle = new Button("RECTANGLE");
        oval = new Button("OVAL");
        line = new Button("LINE");
        Free_Hand = new Button("Free Hand");
        filled = new Checkbox("Solid");
        dotted = new Checkbox("Dotted");
        eraser = new Button("Eraser");
        Clear = new Button("Clear");
        undo = new Button("Undo");

        // Register Button Colors
        red.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentColor = Color.RED;
                remove = false;
            }
        });
        green.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentColor = Color.GREEN;
                remove = false;
            }
        });
        blue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentColor = Color.BLUE;
                remove = false;
            }
        });

        // Register Button Shapes
        rectangle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shape = RECTANGLE;
                remove = false;
                Free_Hand_Flag = false;
            }
        });
        oval.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shape = OVAL;
                remove = false;
                Free_Hand_Flag = false;
            }
        });
        line.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shape = LINE;
                remove = false;
                Free_Hand_Flag = false;
            }
        });
        Free_Hand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Free_Hand_Flag = true;
                remove = false;
            }
        });
        eraser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove = true;
                Free_Hand_Flag = false;
            }
        });
        Clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove = false;
                Free_Hand_Flag = false;
                shapes.clear();
                repaint();
            }
        });
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove = false;
                Free_Hand_Flag = false;
                int size = shapes.size();
                shapes.remove(size - 1);
                repaint();
            }
        });

        filled.addItemListener(this);
        dotted.addItemListener(this);

        addMouseListener(new MouseListener() {

            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
                isDragging = true;

                if (remove) {
                    shapes.add(new Rectangle(startX, startY, 10, 10, Color.WHITE, true, false));
                } else if (Free_Hand_Flag) {
                    shapes.add(new Line(startX, startY, startX, startY, currentColor, false, false));
                }
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
                if (Free_Hand_Flag) {
                    isDragging = false;
                } else if (!remove) {
                    isDragging = false;
                    endX = e.getX();
                    endY = e.getY();

                    paint p = new paint();
                    paint.CalcNewDim calc = p.new CalcNewDim();
                    int[] dim = calc.CalcDimensions(startX, startY, endX, endY);

                    if (shape == RECTANGLE) {
                        shapes.add(new Rectangle(dim[2], dim[3], dim[0], dim[1], currentColor, solid, isDotted));
                    } else if (shape == OVAL) {
                        shapes.add(new Oval(dim[2], dim[3], dim[0], dim[1], currentColor, solid, isDotted));
                    } else if (shape == LINE) {
                        shapes.add(new Line(startX, startY, endX, endY, currentColor, solid, isDotted));
                    }
                    repaint();
                }
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {

                if (remove && isDragging) {
                    endX = e.getX();
                    endY = e.getY();
                    shapes.add(new Rectangle(endX, endY, 10, 10, Color.WHITE, true, false));
                    repaint();

                } else if (isDragging && Free_Hand_Flag) {
                    endX = e.getX();
                    endY = e.getY();
                    shapes.add(new Line(startX, startY, endX, endY, currentColor, false, isDotted));
                    startX = endX;
                    startY = endY;
                    repaint();
                } else if (isDragging) {
                    endX = e.getX();
                    endY = e.getY();
                    repaint();
                }
            }

            public void mouseMoved(MouseEvent e) {
            }
        });

        add(red);
        add(green);
        add(blue);
        add(rectangle);
        add(oval);
        add(line);
        add(filled);
        add(dotted);
        add(eraser);
        add(Free_Hand);
        add(Clear);
        add(undo);
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == filled) {
            solid = filled.getState();
        } else if (e.getSource() == dotted) {
            isDotted = dotted.getState();
        }
    }

    public void paint(Graphics g) {
        Font f = new Font("Arial", Font.BOLD, 20);
        g.setFont(f);

        for (Shape sh : shapes) {
            sh.draw(g);
        }

        if (isDragging && !remove) {
            g.setColor(currentColor);

            paint p = new paint();
            paint.CalcNewDim calc = p.new CalcNewDim();
            int[] dim = calc.CalcDimensions(startX, startY, endX, endY);

            Graphics2D g2d = (Graphics2D) g;
            if (isDotted) {
                float[] dashPattern = {5, 5};
                g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
            } else {
                g2d.setStroke(new BasicStroke());
            }
            switch (shape) {
                //Rectangle
                case RECTANGLE:

                    if (solid) {
                        g.fillRect(dim[2], dim[3], dim[0], dim[1]);
                    } else {
                        g.drawRect(dim[2], dim[3], dim[0], dim[1]);
                    }
                    break;
                //Oval
                case OVAL:
                    if (solid) {
                        g.fillOval(dim[2], dim[3], dim[0], dim[1]);
                    } else {
                        g.drawOval(dim[2], dim[3], dim[0], dim[1]);
                    }
                    break;
                //Line
                case LINE:
                    g.drawLine(startX, startY, endX, endY);
                    break;
                default:
                    System.out.println("Invalid operator");
                    break;
            }
        }
    }
}
