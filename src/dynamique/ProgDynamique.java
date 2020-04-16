package dynamique;

import Plotting.XYPolygonAnnotationDemo1;
import org.jfree.ui.RefineryUtilities;
import structure.Corde;
import structure.Polygone;

import java.util.ArrayList;

/**
 * Triangulation par la programmation dynamique
 * Calcul de la triangulation minimale et estimation du temps
 * Affichage de la triangulation minimale pour un polygone donné
 */
public class ProgDynamique {


    final static double MAX_Value = 10000;


    /**
     * Création des polygones
     * jeux de données
     * Des points différents pour les polygones
     */

    private static double coord[][] = {{0, 10}, {0, 20}, {8, 26}, {12, 27.6}, {15, 28}, {18, 27}, {27, 21}, {27, 15}, {25, 12}, {18, 5}, {10, 0}, {2, 0}};
    private static double coord2[][] = {{1, 10}, {3, 19}, {7, 26}, {15, 28}, {18, 27}, {25, 12}, {18, 5}, {10, 0}, {2, 0}};
    private static double coord3[][] = {{0, 20}, {8, 26}, {15, 26}, {27, 21}, {22, 12}, {10, 0}, {0, 10}};
    private static double coord4[][] = {{0, 0}, {1, 0}, {2, 1}, {1, 2}, {0, 2}};


    private static Polygone dodecagone = new Polygone(coord.length, coord, new ArrayList<Corde>());
    private static Polygone nonagone = new Polygone(coord2.length, coord2, new ArrayList<Corde>());
    private static Polygone heptagone = new Polygone(coord3.length, coord3, new ArrayList<Corde>());
    private static Polygone pentagone = new Polygone(coord4.length, coord4, new ArrayList<Corde>());

    private static Polygone polygoneDeChauffe = new Polygone(coord.length, coord, new ArrayList<Corde>());

    /**
     * Effectue la triangulation d'un polygone de taille t en partant du sommet i
     * On ajoute les cordes, le tracé se fait après, à partir du tableau de cordes
     *
     * @param i        le sommet de départ
     * @param t        la taille du polygone
     * @param polygone le polygone à traiter
     */
    public static void triangulationMini(Polygone polygone, int i, int t) {
        //Cas général
        if (t >= 5) {
            //on va tirer aléatoirement k compris entre 2 et t - 3
            int k = 2 + (int) Math.random() * (t - 3 - 2);
            polygone.ajouterCorde(i, (i + k) % polygone.nbSommets);
            polygone.ajouterCorde((i + k) % polygone.nbSommets, (i + t - 1) % polygone.nbSommets);

            triangulationMini(polygone, i, (k + 1) % polygone.nbSommets);
            triangulationMini(polygone, (i + k) % polygone.nbSommets, t - k);
        }
        //Cas de base, t = 3 ou t = 4
        else {
            //Si le polygone est un carré, on trace la corde de taille minimale
            //Si c'est un triangle, on ne fait rien
            if (t == 4) {
                if (polygone.distance(i, (i + 2) % polygone.nbSommets) <= polygone.distance((i + 3) % polygone.nbSommets, (i + 1) % polygone.nbSommets)) {
                    polygone.ajouterCorde(i, (i + 2) % polygone.nbSommets);
                } else {
                    polygone.ajouterCorde((i + 3) % polygone.nbSommets, (i + 1) % polygone.nbSommets);
                }
            }

        }
    }

    /**
     * Calcul la longueur de la plus petite triangulation du polygone de taille t en partant du sommet i
     *
     * @param i        le sommet de départ
     * @param t        la taille du polygone
     * @param polygone le polygone à traiter
     * @return la longueur de la plus petite triangulation en partant du sommet i
     */
    public static double longueurTrianglMini(Polygone polygone, int i, int t) {
        //cas général
        //la taille du triangle est plus grande que 4
        if (t >= 5) {
            //on calcule les longueurs des triangulations possibles
            //on récupère le sommet dont la triangulation est la plus petite : indiceMini, une simple recherche du minimum
            double longueurMini = MAX_Value;
            int indiceMini = 0;
            double longueurMiniTemp;
            for (int k = 2; k <= t - 3; k++) {
                longueurMiniTemp = longueurTrianglMini(polygone, i, k + 1) + longueurTrianglMini(polygone, (i + k) % polygone.nbSommets, t - k);
                if (longueurMini > longueurMiniTemp) {
                    longueurMini = longueurMiniTemp;
                    indiceMini = k;
                }
            }
            //on renvoie la longueur des 2 cordes tracées et on ajoute la taille de la triangulation la plus petite
            return longueurMini + polygone.distance((i + indiceMini) % polygone.nbSommets, i) + polygone.distance((i + t - 1) % polygone.nbSommets, (i + indiceMini) % polygone.nbSommets);
        } else {
            //Cas de base
            //si on a un triangle, on ne peut plus rien tracer
            if (t == 3) {
                return 0;
            }
            //si on a un carré, on trace la plus petite corde parmi les 2 possibles
            else {
                return Double.min(polygone.distance(i, (i + 2) % polygone.nbSommets), polygone.distance((i + 1) % polygone.nbSommets, (i + 3) % polygone.nbSommets));
            }
        }
    }

    /**
     * Calcul la plus petite triangulation et l'affiche
     *
     * @param polygone la taille du polygone
     * @return la longueur de la triangulation minimale
     */
    public static double trianglMini(Polygone polygone) {

        //on parcours tous les sommets afin de récupérer la triangulation la plus petite
        int t = polygone.nbSommets;
        double min = MAX_Value; //infini
        double triangulation;
        int sommetMini = 0;
        for (int i = 0; i < t; i++) {
            triangulation = longueurTrianglMini(polygone, i, t);
            if (triangulation < min) {
                min = triangulation;
                sommetMini = i;
            }
        }
        triangulationMini(polygone, sommetMini, t);
        return min;
    }

    /**
     * Effectue le tracé de la figure
     *
     * @param polygone le polygone dont il faut tracer la configuration
     */
    public static void draw(Polygone polygone) {
        XYPolygonAnnotationDemo1 demo = new XYPolygonAnnotationDemo1(
                "XYPolygonAnnotationDemo1", polygone, polygone.cordes);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

    public static void analyse(Polygone polygone) {
        long startTime, endTime;
        double totalTime;
        //calcul de la triangulation minimale
        startTime = System.nanoTime(); //début du chrono
        Double trianglMiniValue = trianglMini(polygone);
        endTime = System.nanoTime();
        totalTime = (endTime - startTime) * Math.pow(10, -6); //nano -> millis
        System.out.println("Temps d'éxécution : " + totalTime + "ms");
        System.out.println("valeur minimale de triangulation : " + trianglMiniValue);
    }

    /**
     * Correction bug
     * il semble que la première analyse prenne toujours un temps plus élevé qu'elle ne le devrait
     * ainsi les résultats sont faussés : la triangulation d'un pentagone est plus longues que celle
     * d'un nonagone à titre d'exemple.
     * J'ignore l'origine de ce bug. J'ai tenté un Thread.sleep mais ça n'a pas fonctionné
     * Le but est donc de calculer une première triangulation qu'on ne consultera pas
     *
     * @param polygoneDeChauffe un polygone pour "chauffer" la machine
     */
    public static void tourDeChauffe(Polygone polygoneDeChauffe) {
        trianglMini(polygoneDeChauffe);
    }

    public static void main(String[] args) throws InterruptedException {
        //gestion bug
        tourDeChauffe(polygoneDeChauffe);


        //analyse de la triangulation pour les différentes configurations
        System.out.println("**************Pentagone******************");
        analyse(pentagone);
        System.out.println("**************Heptagone******************");
        analyse(heptagone);
        System.out.println("**************Nonagone******************");
        analyse(nonagone);
        System.out.println("**********Polygone à 12 sommets**********");
        analyse(dodecagone);

        /**
         * Remplacer le paramètre par :
         * pentagone
         * heptagone
         * nonagone
         * polygone
         */
        draw(dodecagone);
    }
}