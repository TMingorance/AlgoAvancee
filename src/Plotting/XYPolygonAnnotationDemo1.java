package Plotting;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYPolygonAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.Layer;
import structure.Corde;
import structure.Polygone;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class XYPolygonAnnotationDemo1 extends ApplicationFrame {

    /**
     * Creates a new demo instance.
     *
     * @param title the frame title.
     * @param sol1
     */
    public XYPolygonAnnotationDemo1(String title, Polygone polygone, ArrayList<Corde> sol1) {
        super(title);
        JPanel chartPanel = createDemoPanel(polygone, sol1);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }

    /**
     * Creates a sample dataset.
     *
     * @return The dataset.
     */
    public static XYDataset createDataset() {
        DefaultXYDataset d = new DefaultXYDataset();
       /* double[] x1 = new double[]{1.7, 2.2, 2.7, 3.0};
        double[] y1 = new double[]{4.0, 3.0, 6.0, 1.0};
        double[][] data1 = new double[][]{x1, y1};
        d.addSeries("Series 1", data1);*/
        /*double[] x2 = new double[]{2.1, 2.2, 2.4, 2.7, 3.2};
        double[] y2 = new double[]{4.5, 6.0, 4.0, 8.0, 5.1};
        double[][] data2 = new double[][]{x2, y2};
        d.addSeries("Series 2", data2);*/
        return d;
    }

    /**
     * Creates a chart.
     *
     * @param dataset the dataset.
     * @param sol1
     * @return The chart.
     */
    private static JFreeChart createChart(XYDataset dataset, Polygone polygone, ArrayList<Corde> sol1) {
        JFreeChart chart = ChartFactory.createScatterPlot(
                "XYPolygonAnnotationDemo1", "X", "Y", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        XYLineAndShapeRenderer renderer
                = (XYLineAndShapeRenderer) plot.getRenderer();


        XYPolygonAnnotation a = new XYPolygonAnnotation(polygone.convertSommetsToDoubleList(), null, new Color(0,220,0,0),
                new Color(0, 0, 0, 100));
        a.setToolTipText("Polygone");
        renderer.addAnnotation(a, Layer.BACKGROUND);

        ArrayList<double[]> arrayList = polygone.convertSommetsToDoubleList(sol1);
        int i = 0;

        for(double[] doubles : arrayList){
            System.out.println("Les cordes sont : " + doubles[0] + ", " + doubles[1] + ", " + doubles[2] + ", " + doubles[3]);
            XYPolygonAnnotation cordeAnnot = new XYPolygonAnnotation(doubles, null, new Color(220,0,0,200),
                    new Color(220,0,0,200));
            cordeAnnot.setToolTipText("Corde " + i);
            renderer.addAnnotation(cordeAnnot, Layer.FOREGROUND);

            i++;
        }
        return chart;
    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public static JPanel createDemoPanel(Polygone polygone, ArrayList<Corde> sol1) {
        JFreeChart chart = createChart(createDataset(), polygone, sol1);
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        return panel;
    }
}