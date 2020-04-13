package dynamique;

import Plotting.XYPolygonAnnotationDemo1;
import structure.Corde;
import structure.Polygone;

import java.util.ArrayList;

public class ProgDynamique {

    private final static int nbSommets = 12;

    private static double coord2[][] = {{0,10}, {0,20}, {2,22}, {8,26}, {12,27}, {15,26}, {18,23}, {27,21}, {27,15}, {25,12}, {18,5}, {10,0}/*, {2,0}*/};

    private static double coord[][] = {{0,10}, {0,20}, {8,26}, {12,27}, {15,26}, {18,23}, {27,21}, {27,15}, {25,12}, {18,5}, {10,0}, {2,0}};

    private static Polygone polygone = new Polygone(nbSommets, coord, new ArrayList<Corde>());


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


    public static void main (String [] args){

        triangulationMini(1, 12);

        XYPolygonAnnotationDemo1 demo = new XYPolygonAnnotationDemo1(
                "XYPolygonAnnotationDemo1", polygone, polygone.cordes);
        demo.pack();
        //RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

        int x = (int) (-1)%9 ;
        System.out.println("test : "+x);
    }
}
