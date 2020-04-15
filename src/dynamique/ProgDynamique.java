package dynamique;

import Plotting.XYPolygonAnnotationDemo1;
import structure.Corde;
import structure.Polygone;

import java.util.ArrayList;

public class ProgDynamique {

    private final static int nbSommets = 12;

    private static double coord2[][] = {{0,10}, {0,20}, {2,22}, {8,26}, {12,27}, {15,26}, {18,27}, {27,21}, {27,15}, {25,12}, {18,5}, {10,0}/*, {2,0}*/};

    private static double coord[][] = {{0,10}, {0,20}, {8,26}, {12,27}, {15,28}, {18,27}, {27,21}, {27,15}, {25,12}, {18,5}, {10,0}, {2,0}};

    private static Polygone polygone = new Polygone(nbSommets, coord, new ArrayList<Corde>());

    /*
    public static void triangulationMini(int i, int t){
        if (t >= 5){
            int k = 2 + (int) Math.random()*(t-3 - 2);
            polygone.ajouterCorde(i, (i + k)%nbSommets);
            polygone.ajouterCorde((i + k)%nbSommets, (i + t - 1)%nbSommets);

            triangulationMini(i, (k + 1)%nbSommets);
            triangulationMini((i+k)%nbSommets, t-k);
        }
        else{
            if (t == 4){
                if (polygone.distance(i, (i+2)) <= polygone.distance((i+3)%nbSommets, (i+1)%nbSommets)){
                    polygone.ajouterCorde((i + 3)%nbSommets, (i + 1)%nbSommets);
                }
                else{
                    polygone.ajouterCorde(i, (i + 2)%nbSommets);
                }
            }

        }
    }
    */

    public static void triangulationMini(int i, int t){
        if (t >= 5){
            int k = 2 + (int) Math.random()*(t-3 - 2);
            polygone.ajouterCorde(i, (i + k)%nbSommets);
            polygone.ajouterCorde((i + k)%nbSommets, (i + t - 1)%nbSommets);

            triangulationMini(i, (k + 1)%nbSommets);
            triangulationMini((i+k)%nbSommets, t-k);
        }
        else{
            if (t == 4){
                if (polygone.distance(i, (i+2)) <= polygone.distance((i+3)%nbSommets, (i+1)%nbSommets)){
                    polygone.ajouterCorde((i + 3)%nbSommets, (i + 1)%nbSommets);
                }
                else{
                    polygone.ajouterCorde(i, (i + 2)%nbSommets);
                }
            }

        }
    }

    public static double longueurTrianglMini(int i, int t){
        if (t >= 5) {
            double longueurMini = 0;
            int indiceMini = 0;
            double longueurMiniTemp;
            for (int k = 2; k < t-3; k++) {
                if (longueurMini == 0){
                    longueurMini = longueurTrianglMini(i, k + 1) + longueurTrianglMini((i + k)%nbSommets, t - k);
                    indiceMini = k;
                }
                else{
                    longueurMiniTemp = longueurTrianglMini(i, k + 1) + longueurTrianglMini((i + k)%nbSommets, t - k);
                    if (longueurMini > longueurMiniTemp){
                        longueurMini = longueurMiniTemp;
                        indiceMini = k;
                    }
                }
            }
            return longueurMini + polygone.distance((i+indiceMini)%nbSommets, i) + polygone.distance((i+t-1)%nbSommets, (i+indiceMini)%nbSommets);
        }
        else{
            if (t == 3){
                return 0;
            }
            else{
                return Double.min(polygone.distance(i, (i+2)%nbSommets),polygone.distance((i+1)%nbSommets, (i+3)%nbSommets));
            }
        }
    }

    public static void main (String [] args){

        triangulationMini(5, 12);
        Double trianglMiniValue = longueurTrianglMini(1, 12);
        System.out.println("valeur mini : "+ trianglMiniValue);

        XYPolygonAnnotationDemo1 demo = new XYPolygonAnnotationDemo1(
                "XYPolygonAnnotationDemo1", polygone, polygone.cordes);
        demo.pack();
        //RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);



        int x = (int) (-1)%9 ;
        System.out.println("test : "+x);
    }
}
