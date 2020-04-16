package essaisSuccessifs;



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

    private final static int nbSommets = 5;

    //private static double coord [] [] = {{0,10}, {0,20}, {3,22}, {8,26}, {12,27}, {15,26}, {18,23}, {27,21}, {27,15}, {22,12}, {15,5}, {10,0}, {2,0}};
    private static double coord [] [] = {{0,10}, {0,20}, {3,25}, {8,27}, {12,27}};
    //private static double coord [] [] = {{0,10}, {0,20}, {3,24}, {8,27}, {12,27}, {15,26}, {18,23}, {20,21}, {22,12}, {15,5}};
    private static Polygone polygone = new Polygone(nbSommets, coord, new ArrayList<Corde>());
    private static ArrayList<ArrayList <Corde>> tabSol = new ArrayList <ArrayList<Corde>> ();
    private static double longMin = 0;
    private static ArrayList<Double> longueursSol = new ArrayList<Double>();


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



    public static void toutessol(int nbCordes){
        for(Corde corde : polygone.cordesPossibles){
            if (polygone.validecorde(corde)) {
                polygone.ajouterCorde(corde);
                if (nbCordes >= polygone.nbSommets - 3) {//arrivé à n-3 cordes, on a triangularisé le polygone
                    tabSol.add((ArrayList<Corde>) polygone.cordes.clone());
                } else {
                    toutessol(nbCordes + 1);//si on n'arrive pas à une solution, on rajoute une corde
                }
                polygone.supprCorde(corde);//une fois qu'on a étudié toutes les possibilités avec la corde, on l'enlève
                //pour passer à la suivante
            }
        }
    }

    public static void toutessolElagage(int nbCordes){
        //calcul d'élagage pour enlever les cordes qui ont déjà été choisies dans une solution avec les cordes actuelles du polygone
        ArrayList<Corde> ensCordeElagage = polygone.cordesPossiblesElaguee(tabSol);
        for(Corde corde : ensCordeElagage){ // on utilise l'ensemble des cordes qui ne font pas déjà partie d'une solution
            if (polygone.validecorde(corde)) {
                polygone.ajouterCorde(corde);
                if (nbCordes >= polygone.nbSommets - 3) {
                    tabSol.add((ArrayList<Corde>) polygone.cordes.clone());
                } else {
                    toutessolElagage(nbCordes + 1);
                }
                polygone.supprCorde(corde);
            }
        }
    }

    public static void toutessolElagageLongueur(int nbCordes, double longueurCordes) {
        //On arrête aussi de suivre une solution si sa longueur est supérieure à la longueur totale la plus petite trouvée
        ArrayList<Corde> ensCordeElagage = polygone.cordesPossiblesElaguee(tabSol);
        if (longueurCordes < longMin || longMin == 0) { //à ce stade si la longueur actuelle des cordes est supérieure ou égale
            //à la longueur totale minimale trouvée, on ne continue pas. (longMin peut ne pas être initialisé, on autorise de continuer dans ce cas).
            for (Corde corde : ensCordeElagage) {
                if (polygone.validecorde(corde)) {
                    polygone.ajouterCorde(corde);
                    longueurCordes += corde.longueur;//on recalcule la longueur totale de la configuration actuelle des cordes
                    if (nbCordes >= polygone.nbSommets - 3) {
                        if(longueurCordes < longMin || longMin == 0) {
                            tabSol.add((ArrayList<Corde>) polygone.cordes.clone());
                            longMin = longueurCordes;
                            longueursSol.add(longueurCordes);
                        }
                    } else {
                        toutessolElagageLongueur(nbCordes + 1, longueurCordes);
                    }
                    polygone.supprCorde(corde);
                    longueurCordes -= corde.longueur;//on recalcule la longueur totale de la configuration actuelle des cordes
                }
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
        long startTime = System.nanoTime(); //pour chronomètrer l'execution



        //System.out.println(listX,listY);*/
        //*********Execution************
        toutessolElagageLongueur(1, 0.0);
        //******************************

        long endTime = System.nanoTime();

        System.out.println(tabSol);

        System.out.println("Longueurs des solutions : " + longueursSol);

        System.out.println("Nb de solutions : " + tabSol.size());

        long totalTime = endTime - startTime;
        System.out.println("Time : " + totalTime);

        System.out.println("longMin = " + longMin);

        //************Affichage**************
        //for(ArrayList<Corde> sol : tabSol) {
            XYPolygonAnnotationDemo1 demo = new XYPolygonAnnotationDemo1(
                    "XYPolygonAnnotationDemo1", polygone, tabSol.get(0));
            demo.pack();
            //RefineryUtilities.centerFrameOnScreen(demo);
            demo.setVisible(true);
       // }
        //**********************************



        System.out.println("Cordes possibles : " + polygone.cordesPossibles);

    }

}

