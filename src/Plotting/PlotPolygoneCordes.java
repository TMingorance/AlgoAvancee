package Plotting;

import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.annotations.XYPolygonAnnotation;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import structure.Corde;
import structure.Polygone;

import java.util.ArrayList;

public class PlotPolygoneCordes extends ApplicationFrame {

    /**
     * A demonstration application showing an XY series containing a null value.
     *
     * @param title  the frame title.
     */

    private Polygone polygone;

    public PlotPolygoneCordes(final String title, Polygone polygone) {

        super(title);
        final XYSeries seriesPolygone = new XYSeries("Polygone");
        /*for(int i = 0; i < polygone.nbSommets; i++){
            seriesPolygone.add(polygone.sommets[i] [0], polygone.sommets[i] [1]);
        }*/

        seriesPolygone.add(0.0, 5.2);
        seriesPolygone.add(3.0, 5.2);
        seriesPolygone.add(2.0,4.5);

        ArrayList <XYSeries> cordeSeries = new ArrayList<XYSeries>();
        for (Corde corde : polygone.cordes){
            final XYSeries seriesCorde = new XYSeries("Corde " + corde.sommet1 + ", " + corde.sommet2);
            seriesCorde.add(polygone.getSommetX(corde.sommet1), polygone.getSommetY(corde.sommet1));
            seriesCorde.add(polygone.getSommetX(corde.sommet2), polygone.getSommetY(corde.sommet2));
        }

        final XYSeriesCollection PolygoneData = new XYSeriesCollection(seriesPolygone);
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Polygone",
                "X",
                "Y",
                PolygoneData,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }

// ****************************************************************************
// * JFREECHART DEVELOPER GUIDE                                               *
// * The JFreeChart Developer Guide, written by David Gilbert, is available   *
// * to purchase from Object Refinery Limited:                                *
// *                                                                          *
// * http://www.object-refinery.com/jfreechart/guide.html                     *
// *                                                                          *
// * Sales are used to provide funding for the JFreeChart project - please    *
// * support us so that we can continue developing free software.             *
// ****************************************************************************

}
