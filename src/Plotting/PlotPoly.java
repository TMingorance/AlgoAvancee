package Plotting;

// Java code to draw a polygon
// using drawLine(int x, int y, int x1, int y1)
// function
import java.awt.*;
import javax.swing.*;


public class PlotPoly extends JApplet {

    // called when applet is started
    public void init()
    {
        // set the size of applet to 300, 300
        setSize(200, 200);
        show();
    }

    // invoked when applet is started
    public void start()
    {
    }

    // invoked when applet is closed
    public void stop()
    {
    }

    public void paint(Graphics g)
    {
        // x coordinates of vertices
        int x[] = { 10, 30, 40, 50, 110, 140 };

        // y coordinates of vertices
        int y[] = { 140, 110, 50, 40, 30, 10 };

        // number of vertices
        int numberofpoints = 6;

        // set the color of line drawn to blue
        g.setColor(Color.blue);

        // join the adjacent vertices
        for (int i = 0; i < numberofpoints - 1; i++)
            g.drawLine(x[i], y[i], x[i + 1], y[i + 1]);

        // join the first and last vertex
        g.drawLine(x[0], y[0], x[numberofpoints - 1], y[numberofpoints - 1]);
    }
}

