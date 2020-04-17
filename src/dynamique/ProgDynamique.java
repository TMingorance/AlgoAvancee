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
    private static double coord13[][] = {{0, 10}, {0, 20}, {8, 26}, {12, 27.6}, {15, 28}, {18, 27}, {27, 21}, {27, 15}, {25, 12}, {18, 5}, {14, 2}, {10, 0}, {2, 0}};
    private static double coord12[][] = {{0, 10}, {0, 20}, {8, 26}, {12, 27.6}, {15, 28}, {18, 27}, {27, 21}, {27, 15}, {25, 12}, {18, 5}, {10, 0}, {2, 0}};
    private static double coord11[][] = {{0, 10}, {0, 20}, {8, 26}, {12, 27.6}, {15, 28}, {18, 27}, {27, 21}, {25, 12}, {18, 5}, {10, 0}, {2, 0}};
    private static double coord10[][] = {{0, 10}, {0, 20}, {3, 24}, {8, 27}, {12, 27}, {15, 26}, {18, 23}, {20, 21}, {22, 12}, {15, 5}};
    private static double coord9[][] = {{0, 10}, {0, 20}, {3, 24}, {8, 27}, {12, 27}, {15, 26}, {18, 23}, {20, 21}, {22, 12}};
    private static double coord8[][] = {{0, 10}, {0, 20}, {3, 24}, {8, 27}, {12, 27}, {15, 26}, {18, 23}, {20, 21}};
    private static double coord7[][] = {{0, 10}, {0, 20}, {3, 24}, {8, 27}, {12, 27}, {15, 26}, {18, 23}};
    private static double coord6[][] = {{0, 10}, {0, 20}, {3, 24}, {8, 27}, {12, 27}, {15, 26}};
    private static double coord5[][] = {{0, 10}, {0, 20}, {3, 24}, {8, 27}, {12, 27}};

    private static Polygone tridecagone = new Polygone(coord13.length, coord13, new ArrayList<Corde>());
    private static Polygone dodecagone = new Polygone(coord12.length, coord12, new ArrayList<Corde>());
    private static Polygone hendagone = new Polygone(coord11.length, coord11, new ArrayList<Corde>());
    private static Polygone decagone = new Polygone(coord10.length, coord10, new ArrayList<Corde>());
    private static Polygone nonagone = new Polygone(coord9.length, coord9, new ArrayList<Corde>());
    private static Polygone octogone = new Polygone(coord8.length, coord8, new ArrayList<Corde>());
    private static Polygone heptagone = new Polygone(coord7.length, coord7, new ArrayList<Corde>());
    private static Polygone hexagone = new Polygone(coord6.length, coord6, new ArrayList<Corde>());
    private static Polygone pentagone = new Polygone(coord5.length, coord5, new ArrayList<Corde>());


    private static Polygone polygoneDeChauffe = new Polygone(coord13.length, coord13, new ArrayList<Corde>());

    /**
     * Effectue la triangulation d'un polygone de taille t en partant du sommet i
     * On ajoute les cordes, le tracé se fait après, à partir du tableau de cordes
     *
     * @param i        le sommet de départ
     * @param t        la taille du polygone
     * @param polygone le polygone à traiter
     */
    public static void triangulationMini(Polygone polygone, int i, int t) {
        //Cas de base, au moins un pentagone pour pouvoir tracer les 2 cordes
        if (t >= 5) {
            double triangulationMini = MAX_Value;
            double triangulation = 0;
            int indice = 0;
            for (int k = 2; k <= t - 2; k++) {
                triangulation = polygone.distance((i + k) % polygone.nbSommets, i) + polygone.distance((i + t - 1) % polygone.nbSommets, (i + k) % polygone.nbSommets)
                        + longueurTrianglMini(polygone, i, k + 1) + longueurTrianglMini(polygone, (i + k) % polygone.nbSommets, t - k);
                if (triangulation < triangulationMini) {
                    triangulationMini = triangulation;
                    indice = k;
                }
            }
            //ajout des cordes
            polygone.ajouterCorde(i, (i + indice) % polygone.nbSommets);
            polygone.ajouterCorde((i + indice) % polygone.nbSommets, (i + t - 1) % polygone.nbSommets);

            //On relance la triangulation sur les deux polygones obtenus
            triangulationMini(polygone, i, (indice + 1) % polygone.nbSommets);
            triangulationMini(polygone, (i + indice) % polygone.nbSommets, t - indice);

        } else {
            //Si le polygone est un quadrilatère, on trace la corde de taille minimale
            //S'il s'agit d'un triangle, on ne fait rien
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
            double longueurMiniTemp;
            for (int k = 2; k <= t - 2; k++) {
                longueurMiniTemp = longueurTrianglMini(polygone, i, (k + 1) % polygone.nbSommets) + longueurTrianglMini(polygone, (i + k) % polygone.nbSommets, t - k)
                        + polygone.distance((i + k) % polygone.nbSommets, i) + polygone.distance((i + t - 1) % polygone.nbSommets, (i + k) % polygone.nbSommets);
                longueurMini = Double.min(longueurMini, longueurMiniTemp);
            }
            //on renvoie la longueur des 2 cordes tracées et on ajoute la taille de la triangulation la plus petite
            return longueurMini;
        } else {
            //Cas de base
            //si on a un quadrilatère, on trace la plus petite corde parmi les 2 possibles
            if (t == 4) {
                return Double.min(polygone.distance(i, (i + 2) % polygone.nbSommets), polygone.distance((i + 1) % polygone.nbSommets, (i + 3) % polygone.nbSommets));
            }
            //si on a un triangle, on ne peut plus rien tracer
            else {
                return 0;
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
        totalTime = endTime - startTime;
        System.out.println("Temps d'éxécution : " + totalTime + "ns");
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
        System.out.println("**************Hexagone*******************");
        analyse(hexagone);
        System.out.println("**************Heptagone******************");
        analyse(heptagone);
        System.out.println("**************Octogone*******************");
        analyse(octogone);
        System.out.println("**************Nonagone*******************");
        analyse(nonagone);
        System.out.println("**************Décagone*******************");
        analyse(decagone);
        System.out.println("**************Hendagone******************");
        analyse(hendagone);
        System.out.println("**************Dodécagone*****************");
        analyse(dodecagone);
        System.out.println("**************Tridécagone*****************");
        analyse(tridecagone);


        /**
         * Remplacer le paramètre par :
         * pentagone
         * hexagone
         * heptagone
         * octogone
         * nonagone
         * decagone
         * hendagone
         * dodecagone
         * tridecagone
         */
        draw(nonagone);
    }
}