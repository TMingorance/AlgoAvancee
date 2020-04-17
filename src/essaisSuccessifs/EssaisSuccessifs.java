package essaisSuccessifs;



import Plotting.XYPolygonAnnotationDemo1;
import structure.Corde;
import structure.Polygone;

import java.util.ArrayList;


public class EssaisSuccessifs {

    /**
     * Création des polygones
     * jeux de données
     * Des points différents pour les polygones
     */

    private static double coord13[][] = {{0, 10}, {0, 20}, {8, 26}, {12, 27.6}, {15, 28}, {18, 27}, {27, 21}, {27, 15}, {25, 12}, {18, 5}, {14, 2}, {10, 0}, {2, 0}};
    private static double coord12[][] = {{0, 10}, {0, 20}, {8, 26}, {12, 27.6}, {15, 28}, {18, 27}, {27, 21}, {27, 15}, {25, 12}, {18, 5}, {10, 0}, {2, 0}};
    private static double coord11[][] = {{0, 10}, {0, 20}, {8, 26}, {12, 27.6}, {15, 28}, {18, 27}, {27, 21}, {25, 12}, {18, 5}, {10, 0}, {2, 0}};
    private static double coord10[][] = {{0, 10}, {0, 20}, {8, 26}, {12, 27.6}, {15, 28}, {18, 27}, {25, 12}, {18, 5}, {10, 0}, {2, 0}};
    private static double coord9[][] = {{0, 10}, {0, 20}, {8, 26}, {12, 27.6}, {15, 28}, {18, 27}, {18, 5}, {10, 0}, {2, 0}};
    private static double coord8[][] = {{0, 10}, {0, 20}, {8, 26}, {12, 27.6}, {15, 28}, {18, 27}, {18, 5}, {10, 0}};
    private static double coord7[][] = {{0, 20}, {8, 26}, {15, 26}, {27, 21}, {22, 12}, {10, 0}, {0, 10}};
    private static double coord6[][] = {{0, 10}, {8, 26}, {15, 28}, {18, 27}, {18, 5}, {10, 0}};
    private static double coord5[][] = {{0, 20}, {8, 26}, {15, 26}, {22, 12}, {10, 0}};

    private static Polygone tridecagone = new Polygone(coord13.length, coord13, new ArrayList<Corde>());
    private static Polygone dodecagone = new Polygone(coord12.length, coord12, new ArrayList<Corde>());
    private static Polygone hendagone = new Polygone(coord11.length, coord11, new ArrayList<Corde>());
    private static Polygone decagone = new Polygone(coord10.length, coord10, new ArrayList<Corde>());
    private static Polygone nonagone = new Polygone(coord9.length, coord9, new ArrayList<Corde>());
    private static Polygone octogone = new Polygone(coord8.length, coord8, new ArrayList<Corde>());
    private static Polygone heptagone = new Polygone(coord7.length, coord7, new ArrayList<Corde>());
    private static Polygone hexagone = new Polygone(coord6.length, coord6, new ArrayList<Corde>());
    private static Polygone pentagone = new Polygone(coord5.length, coord5, new ArrayList<Corde>());


    private static Polygone polygone = pentagone;
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

    public static ArrayList<Corde> rechercheSolMin(ArrayList <ArrayList <Corde>> tabSolution){
        ArrayList <Corde> solRetenue = tabSolution.get(0);
        double tailleSol = 0;
        for(Corde corde : tabSolution.get(0)){ //initialisation de tailleMin
            tailleSol += corde.longueur;
        }
        double tailleMin = tailleSol;
        for(ArrayList<Corde> sol : tabSolution){//recherche de la solution minimale
            for(Corde corde : sol){
                tailleSol += corde.longueur;
            }
            if (tailleSol < tailleMin){
                tailleMin = tailleSol;
                solRetenue = sol;
            }
        }
        longMin = tailleMin;
        return solRetenue;
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
        toutessolElagage(1);

        EssaisSuccessifs.rechercheSolMin(tabSol);
        //******************************

        long endTime = System.nanoTime();

        System.out.println(tabSol);

        System.out.println("Longueurs des solutions : " + longueursSol);

        System.out.println("Nb de solutions : " + tabSol.size());

        long totalTime = endTime - startTime;
        System.out.println("Time : " + totalTime);

        System.out.println("Longueur de la solution minimale = " + longMin);

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

