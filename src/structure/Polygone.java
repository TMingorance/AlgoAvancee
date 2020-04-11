package structure;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Arrays;

public class Polygone{
    public int nbSommets;
    public double [][] sommets;
    public ArrayList<Corde> cordes;

    public Polygone(int nbSommets, double [][] sommets,  ArrayList <Corde> cordes) {
        this.nbSommets = nbSommets;
        this.sommets = sommets;
        this.cordes = cordes;
    }

    @Override
    public String toString() {
        return "Polygone{" +
                "nbSommets=" + nbSommets +
                ", sommets=" + Arrays.toString(sommets) +
                ", cordes=" + cordes +
                '}';
    }

    public double getSommetX (int sommet){
        return this.sommets[sommet] [0];
    }

    public double getSommetY (int sommet){
        return this.sommets[sommet] [1];
    }

    public double [] convertSommetsToDoubleList (){
        double [] doubles = new double[2*this.nbSommets];
        for(int i = 0; i < this.nbSommets; i++){
            doubles[2*i] = this.getSommetX(i);
            doubles[2*i +1] = this.getSommetY(i);
        }
        return doubles;
    }

    public ArrayList<double[]> convertSommetsToDoubleList(ArrayList<Corde> sol1) {
        double [] doubles = {0.0, 0.0, 0.0, 0.0};
        ArrayList <double []> arrayList = new ArrayList<double[]>();
        int i = 0;
        for(Corde corde : sol1){
            doubles [0] = this.getSommetX(corde.sommet1);
            doubles [1] = this.getSommetY(corde.sommet1);
            doubles [2] = this.getSommetX(corde.sommet2);
            doubles [3] = this.getSommetY(corde.sommet2);
            arrayList.add(doubles);
            doubles [0] = 0;
            doubles [1] = 0;
            doubles [2] = 0;
            doubles [3] = 0;

            i++;
        }
        return arrayList;
    }

    public ArrayList <double []> getCordeDoubleListArrayList (){
        ArrayList <double []> arrayList = new ArrayList<double []>();
        for (Corde corde : this.cordes){
            double [] doubles = {this.getSommetX(corde.sommet1), this.getSommetY(corde.sommet1), this.getSommetX(corde.sommet2), this.getSommetY(corde.sommet2)};
            arrayList.add(doubles);
        }
        return arrayList;
    }

    public boolean validecorde(int i, int j){ //Méthode testée et approuvée, renvoie vrai si la corde entre les deux points est valide
        if(Math.abs(i - j) <= 1){
            return false;
        }
        for(Corde corde : this.cordes){
            if ((corde.sommet1 == i && corde.sommet2 == j) || corde.sommet1 == j && corde.sommet2 == i ){
                return false;
            }
            if ((corde.sommet1 > Integer.min(i,j) && corde.sommet1 < Integer.max(i,j) && (corde.sommet2 > Integer.max(i,j) || corde.sommet2 < Integer.min(i,j))) //si a.sommet1 in [min(i,j), max(i,j)] && a.sommet2 in [min(i,j), max(i,j)] c'est OK
                    || ((corde.sommet1 < Integer.min(i,j) || corde.sommet1 > Integer.max(i,j)) && corde.sommet2 > Integer.min(i,j) && corde.sommet2 < Integer.max(i,j))){//si a.sommet1 not in [min(i,j), max(i,j)] && a.sommet2 not in [min(i,j), max(i,j)], c'est OK, sinon, non
                return false;
            }
        }
        return true;
    }

    public void ajouterCorde(int sommet1, int sommet2){
        cordes.add(new Corde(sommet1, sommet2, Math.sqrt(Math.pow((this.sommets[sommet2][1] - this.sommets[sommet1] [1]),2) +
                Math.pow((this.sommets[sommet2][0] - this.sommets[sommet1] [0]),2)))); //sqrt((yS2 - yS1)² + (xS2 - xS1)²)
    }

    public void supprCorde(int sommet1, int sommet2){
        cordes.remove(new Corde(sommet1, sommet2, Math.sqrt(Math.pow((this.sommets[sommet2][1] - this.sommets[sommet1] [1]),2) +
                Math.pow((this.sommets[sommet2][0] - this.sommets[sommet1] [0]),2))));
    }


}