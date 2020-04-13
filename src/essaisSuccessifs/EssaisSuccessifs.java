package essaisSuccessifs;



import Plotting.PlotPoly;
import Plotting.PlotPolygoneCordes;
import Plotting.XYPolygonAnnotationDemo1;
import org.jfree.ui.RefineryUtilities;
import structure.Corde;
import structure.Polygone;
import structure.PolygoneSansCoord;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.StandardSocketOptions;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import static java.lang.Integer.min;

public class EssaisSuccessifs {

    private final static int nbSommets = 13;

    private static double coord [] [] = {{0,20}, {8,26}, {15,26}, {27,21}, {22,12}, {10,0}, {0,10}};
    private static PolygoneSansCoord polygone = new PolygoneSansCoord(nbSommets, new ArrayList<Corde>());
    private static ArrayList<ArrayList <Corde>> tabSol = new ArrayList <ArrayList<Corde>> ();


/*Procédure essaisSuccessifs
param : un Polygone
    Si on ne peux plus faire de corde : // à remanier pour inclure dans l'autre boucle a
        enregistrer la config des cordes du Polygone
        Fin de procédure
    FSi
    Pour tous les sommets a :
        Pour tous les sommets b pour lesquels on peut faire une corde entre a et b :
            Faire la corde
            lancer essaisSuccessifs sur le nouveau Polygone qui a la corde en plus
        FPour
    FPour
Fin
 */

public static void toutessolElagage(int nbCordes){
    //calcul d'élagage pour enlever les cordes qui ont déjà été choisies dans une solution avec les cordes actuelles du polygone
    ArrayList<Corde> ensCordeElagage = polygone.cordesPossiblesElaguee(tabSol);
    for(Corde corde : ensCordeElagage){
        if (polygone.validecorde(corde)) {
            polygone.ajouterCorde(corde);
            if (polygone.cordes.size() >= polygone.nbSommets - 3) {
                tabSol.add((ArrayList<Corde>) polygone.cordes.clone());
            } else {
                toutessolElagage(nbCordes + 1);
            }
            polygone.supprCorde(corde);
        }
    }
}

public static void toutessol(int nbCordes){
    for(Corde corde : polygone.cordesPossibles){
        if (polygone.validecorde(corde)) {
            polygone.ajouterCorde(corde);
            if (polygone.cordes.size() >= polygone.nbSommets - 3) {
                tabSol.add((ArrayList<Corde>) polygone.cordes.clone());
            } else {
                toutessol(nbCordes + 1);
            }
            polygone.supprCorde(corde);
        }
    }
}

    public static void main (String [] args){

        //tests
        /*
        polygone.ajouterCorde(2,5);
        System.out.println(polygone);
        polygone.supprCorde(2,5);
        System.out.println(polygone);
        */

       /* ArrayList<Double> listX = new ArrayList<Double>();
        ArrayList<Double> listY = new ArrayList<Double>();
        for(int i = 0 ; i < polygone.nbSommets ; i++) {
            listX.add((double)polygone.sommets[i][0]);
            listY.add((double)polygone.sommets[i][1]);
        }
        listX.add((double)polygone.sommets[0][0]); //on remet le premier point pour faire un polygone fermé
        listY.add((double)polygone.sommets[0][1]);
*/
        long startTime = System.nanoTime();



        //System.out.println(listX,listY);*/
        //*********Execution************
        toutessolElagage(1);
        //******************************

        long endTime   = System.nanoTime();

        System.out.println(tabSol);

        System.out.println("Nb de solutions : " + tabSol.size());

        long totalTime = endTime - startTime;
        System.out.println("Time : " + totalTime);
/*

        for(ArrayList<Corde> sol : tabSol) {
            XYPolygonAnnotationDemo1 demo = new XYPolygonAnnotationDemo1(
                    "XYPolygonAnnotationDemo1", polygone, sol);
            demo.pack();
            //RefineryUtilities.centerFrameOnScreen(demo);
            demo.setVisible(true);
        }

        System.out.println("Cordes possibles : " + polygone.cordesPossibles);
*/
    }

}

