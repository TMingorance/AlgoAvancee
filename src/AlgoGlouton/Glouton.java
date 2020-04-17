package AlgoGlouton;

import Plotting.XYPolygonAnnotationDemo1;
import structure.Corde;
import structure.Polygone;

import java.awt.geom.GeneralPath;
import java.util.ArrayList;

public class Glouton {

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


    private static Polygone polygonePrinc = new Polygone(pentagone.nbSommets, pentagone.sommets, new ArrayList<Corde>());
    public static Polygone polygone = new Polygone(pentagone.nbSommets, pentagone.sommets, new ArrayList<Corde>()); //polygone secondaire
    private static ArrayList<ArrayList <Corde>> tabSol = new ArrayList <ArrayList<Corde>> ();
    private static double longMin = 0;
    private static ArrayList<Double> longueursSol = new ArrayList<Double>();

    private static ArrayList<Integer> sommets = new ArrayList<Integer>(); /*tableau qui va garder les correspondances entre
    entre  les numéros de sommet du polygone secondaire et le principal : c'est un tableau qui contient sommets du polygone principal
    donc on accède à ces sommets avec sommet.get(numéroDuSommetDuPolygoneSecondaire), au fur et à mesure que l'on rajoute des
    cordes, on retire en fait des sommets au polygone secondaire, et ils ne sont donc plus accessibles non plus pour tracer des
    cordes dans le polygone principal*/
    public static void init(Polygone polygone){ //initialise sommets
        for(int i = 0; i < polygone.nbSommets; i++){
            sommets.add(i);
        }
    }

    public static void glouton(Polygone polygone){
        if((polygone.nbSommets > 3)) {
            double tailleMin = polygone.tailleCorde(0, 2); //on initialise l'algorithme à la première corde
            int numCordeMin = 0;
            for (int i = 1; i < polygone.nbSommets; i++) { //recherche de min sur la taille de la corde
                if (polygone.tailleCorde(i, (i + 2) % polygone.nbSommets) < tailleMin) {
                    tailleMin = polygone.tailleCorde(i, (i + 2) % polygone.nbSommets);
                    numCordeMin = i; //on note l'indice de la corde
                }
            }
            //enregistrer corde dans le polygone principal
            Glouton.polygonePrinc.ajouterCorde(Glouton.sommets.get(numCordeMin), Glouton.sommets.get((numCordeMin + 2) % polygone.nbSommets));
            Glouton.sommets.remove((numCordeMin + 1) % polygone.nbSommets); //on enlève la référence au sommet du
            // polygone principal qu'on veut enlever dans notre polygone secondaire en enlevant le sommet d'indice (numCordeMin + 1) % polygone.nbSommets
            glouton(polygone.retirerSommet((numCordeMin + 1) % polygone.nbSommets));
        }
    }

    public static void main (String[] args){

        long startTime = System.nanoTime(); //pour chronomètrer l'execution

        Glouton.init(Glouton.polygone);

        Glouton.glouton(Glouton.polygone);

        long endTime = System.nanoTime();
        //********Affichage*************
        XYPolygonAnnotationDemo1 demo = new XYPolygonAnnotationDemo1(
                "XYPolygonAnnotationDemo1", Glouton.polygonePrinc, Glouton.polygonePrinc.cordes);
        demo.pack();
        //RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        //******************************
        System.out.println("Taille de la sol pour ce polygone à " + Glouton.polygonePrinc.nbSommets + " sommets : " +
                Glouton.polygonePrinc.longueurCordes());

        long totalTime = endTime - startTime;
        System.out.println("Time : " + totalTime);

    }
}
