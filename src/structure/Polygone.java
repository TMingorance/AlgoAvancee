package structure;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Arrays;

public class Polygone{
    public int nbSommets;
    public int [][] sommets;
    public ArrayList<Corde> cordes;

    public Polygone(int nbSommets, int [][] sommets,  ArrayList <Corde> cordes) {
        this.nbSommets = nbSommets;
        this.sommets = new int [nbSommets] [2];
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

    public boolean validecorde(int i, int j){ //Méthode testée et approuvée, renvoie vrai si la corde entre les deux points est valide
        for(Corde corde : cordes){
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