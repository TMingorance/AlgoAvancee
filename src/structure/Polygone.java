package structure;

import java.util.ArrayList;

public class Polygone{
    public int nbSommets;
    public int [][] sommets;
    public ArrayList<Corde> cordes;

    public Polygone(int nbSommets, int [][] sommets,  ArrayList <Corde> a) {
        this.nbSommets = nbSommets;
        this.sommets = new int [nbSommets] [2];
        this.cordes = a;
    }

    public boolean validecorde(int i, int j){ //Méthode testée et approuvée
        for(Corde a : cordes){
            if ((a.sommet1 == i && a.sommet2 == j) || a.sommet1 == j && a.sommet2 == i ){
                return false;
            }
            if ((a.sommet1 > Integer.min(i,j) && a.sommet1 < Integer.max(i,j) && (a.sommet2 > Integer.max(i,j) || a.sommet2 < Integer.min(i,j))) //si a.sommet1 in [min(i,j), max(i,j)] && a.sommet2 in [min(i,j), max(i,j)] c'est OK
                    || ((a.sommet1 < Integer.min(i,j) || a.sommet1 > Integer.max(i,j)) && a.sommet2 > Integer.min(i,j) && a.sommet2 < Integer.max(i,j))){//si a.sommet1 not in [min(i,j), max(i,j)] && a.sommet2 not in [min(i,j), max(i,j)], c'est OK, sinon, non
                return false;
            }
        }
        return true;
    }
}