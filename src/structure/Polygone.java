package structure;

import essaisSuccessifs.EssaisSuccessifs;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Arrays;

public class Polygone{
    public int nbSommets;
    public double [][] sommets;
    public ArrayList<Corde> cordes;
    public ArrayList<Corde> cordesPossibles;
    public ArrayList<Integer> indexCordes  = new ArrayList<Integer>(); // pour fonctionner avec la liste des cordes possibles


    public Polygone(int nbSommets, double [][] sommets,  ArrayList <Corde> cordes) {
        this.nbSommets = nbSommets;
        this.sommets = sommets;
        this.cordes = cordes;
        this.cordesPossibles = new ArrayList<Corde>();
        calculCordesPossibles();
    }

    @Override
    public String toString() {
        return "Polygone{" +
                "nbSommets=" + nbSommets +
                ", sommets=" + Arrays.toString(sommets) +
                ", cordes=" + cordes +
                '}';
    }

    private ArrayList<Corde> calculCordesPossibles(){//génère la liste des cordes que l'on peut tracer dans ce polygone
        ArrayList<Corde> arrayList = new ArrayList<Corde>();
        boolean cordeNonDoublon = true;
        for (int i = 0; i < nbSommets; i++){
            for (int j = 0; j < nbSommets; j++){
                if (validecordePolygoneVide(i,j)){
                    for(Corde corde : cordesPossibles){
                        if((corde.sommet1 == i && corde.sommet2 == j) || (corde.sommet1 == j && corde.sommet2 == i)) { //évitons les doublons
                            cordeNonDoublon = false;
                            break; //est-ce une mauvaise pratique ?
                        }
                    }
                    if (cordeNonDoublon){
                        ajouterCordeACordesPossibles(i, j);
                    }
                }
                cordeNonDoublon = true;
            }
        }
        return arrayList;
    }

    public ArrayList<Corde> cordesPossiblesElaguee (ArrayList<ArrayList<Corde>>tabSol){//renvoie une liste des cordes possibles
        //auxquelles on a enlevé les cordes qui mènerons à une solution déjà trouvée
        ArrayList<Corde> cordesPossiblesElaguee = (ArrayList<Corde>)this.cordesPossibles.clone();
        boolean solElagableTrouvee = true;
        for(ArrayList<Corde> sol : tabSol){
            //si toutes les cordes du polygone sont dans une solution, on enlève de cordesPossibleElaguee les cordes de cette solution
            for(Corde corde : this.cordes){
                if(!sol.contains(corde)){
                    solElagableTrouvee = false;
                }
            }
            if (solElagableTrouvee){//on enlève toutes les cordes de cette solution à cordesPossiblesElaguee, comme cela,
                //on ne considère ni les cordes qui sont déjà dans le polygone, ni les cordes qui amènerons à une solution
                //déjà existante
                for(Corde cordeSol : sol){
                    cordesPossiblesElaguee.remove(cordeSol);
                }
            }
            solElagableTrouvee = true;
        }
        return cordesPossiblesElaguee;
    }

    public double getSommetX (int sommet){
        return this.sommets[sommet] [0];
    }

    public double getSommetY (int sommet){
        return this.sommets[sommet] [1];
    }

    public double [] convertSommetsToDoubleList (){//sert pour l'affichage de polygone
        double [] doubles = new double[2*this.nbSommets];
        for(int i = 0; i < this.nbSommets; i++){
            doubles[2*i] = this.getSommetX(i);
            doubles[2*i +1] = this.getSommetY(i);
        }
        return doubles;
    }

    public ArrayList<double[]> convertSommetsToDoubleList(ArrayList<Corde> sol1) {//servait pour l'affichage de polygone
        ArrayList <double []> arrayList = new ArrayList<double[]>();
        for(Corde corde : sol1){
            double [] doubles = {0.0, 0.0, 0.0, 0.0};
            doubles [0] = this.getSommetX(corde.sommet1);
            System.out.println("doubles[0] : " + doubles [0]);
            doubles [1] = this.getSommetY(corde.sommet1);
            System.out.println("doubles[1] : " + doubles [1]);
            doubles [2] = this.getSommetX(corde.sommet2);
            System.out.println("doubles[2] : " + doubles [2]);
            doubles [3] = this.getSommetY(corde.sommet2);
            System.out.println("doubles[3] : " + doubles [3]);
            arrayList.add(doubles);
        }
        return arrayList;
    }

    public ArrayList<double[][]> convertSommetsToDoubleListForData(ArrayList<Corde> sol1) {//sert pour l'affichage
        ArrayList <double [][]> arrayList = new ArrayList<double[][]>();
        for(Corde corde : sol1){
        double[] x1 = new double[]{this.getSommetX(corde.sommet1), this.getSommetX(corde.sommet2)};
        double[] y1 = new double[]{this.getSommetY(corde.sommet1), this.getSommetY(corde.sommet2)};
        double[][] data = new double[][]{x1, y1};
        arrayList.add(data);
        }
        return arrayList;
    }

    public ArrayList <double []> getCordeDoubleListArrayList (){//servait pour l'affichage
        ArrayList <double []> arrayList = new ArrayList<double []>();
        for (Corde corde : this.cordes){
            double [] doubles = {this.getSommetX(corde.sommet1), this.getSommetY(corde.sommet1), this.getSommetX(corde.sommet2), this.getSommetY(corde.sommet2)};
            arrayList.add(doubles);
        }
        return arrayList;
    }

    public boolean validecorde(int i, int j){ //renvoie vrai si la corde entre les deux points est valide
        if(Math.abs(i - j) <= 1){//il n'y a pas de corde entre deux sommets adjacents
            return false;
        }
        if(Math.abs(i - j) >= this.nbSommets - 1){//les sommets d'indices 0 et nbSommet-1 sont aussi adjacents
            return false;
        }
        for(Corde corde : this.cordes){
            if ((corde.sommet1 == i && corde.sommet2 == j) || (corde.sommet1 == j && corde.sommet2 == i) ){//vérifie si la corde n'existe pas déjà
                return false;
            }//ici on teste si la corde de sommets (i,j) ne coupe pas une ancienne
            if ((corde.sommet1 > Integer.min(i,j) && corde.sommet1 < Integer.max(i,j) && (corde.sommet2 > Integer.max(i,j) || corde.sommet2 < Integer.min(i,j))) //si a.sommet1 in [min(i,j), max(i,j)] && a.sommet2 in [min(i,j), max(i,j)] c'est OK
                    || ((corde.sommet1 < Integer.min(i,j) || corde.sommet1 > Integer.max(i,j)) && (corde.sommet2 > Integer.min(i,j) && corde.sommet2 < Integer.max(i,j)))){//si a.sommet1 not in [min(i,j), max(i,j)] && a.sommet2 not in [min(i,j), max(i,j)], c'est OK, sinon, non
                return false;
            }
        }
        return true;
    }

    public boolean validecorde(Corde corde){ //renvoie vrai si la corde entre les deux points est valide
        int i = corde.sommet1;
        int j = corde.sommet2;

        if(Math.abs(i - j) <= 1){//il n'y a pas de corde entre deux sommets adjacents
            return false;
        }
        if(Math.abs(i - j) >= this.nbSommets - 1){//les sommets d'indices 0 et nbSommet-1 sont aussi adjacents
            return false;
        }
        for(Corde cordePolygone : this.cordes){
            if ((cordePolygone.sommet1 == i && cordePolygone.sommet2 == j) || (cordePolygone.sommet1 == j && cordePolygone.sommet2 == i) ){//vérifie si la corde n'existe pas déjà
                return false;
            }//ici on teste si la corde de sommets (i,j) ne coupe pas une ancienne
            if ((cordePolygone.sommet1 > Integer.min(i,j) && cordePolygone.sommet1 < Integer.max(i,j) && (cordePolygone.sommet2 > Integer.max(i,j) || cordePolygone.sommet2 < Integer.min(i,j))) //si a.sommet1 in [min(i,j), max(i,j)] && a.sommet2 in [min(i,j), max(i,j)] c'est OK
                    || ((cordePolygone.sommet1 < Integer.min(i,j) || cordePolygone.sommet1 > Integer.max(i,j)) && (cordePolygone.sommet2 > Integer.min(i,j) && cordePolygone.sommet2 < Integer.max(i,j)))){//si a.sommet1 not in [min(i,j), max(i,j)] && a.sommet2 not in [min(i,j), max(i,j)], c'est OK, sinon, non
                return false;
            }
        }
        return true;
    }

    private boolean validecordePolygoneVide(int i, int j){ //Méthode servant à remplir cordesPossibles
        if(Math.abs(i - j) <= 1){
            return false;
        }
        if(Math.abs(i - j) >= this.nbSommets - 1){
            return false;
        }
        return true;
    }

    public void ajouterCorde(int sommet1, int sommet2){
        cordes.add(new Corde(sommet1, sommet2, Math.sqrt(Math.pow((this.sommets[sommet2][1] - this.sommets[sommet1] [1]),2) +
                Math.pow((this.sommets[sommet2][0] - this.sommets[sommet1] [0]),2)))); //sqrt((yS2 - yS1)² + (xS2 - xS1)²)
    }

    public double distance(int sommet1, int sommet2){
        return Math.pow((this.sommets[sommet2][0] - this.sommets[sommet1] [0]),2); //sqrt((yS2 - yS1)² + (xS2 - xS1)²)
    }


    public double longueurCordes (){//renvoie la somme de toutes les cordes du polygone
        double somme = 0;
        for(Corde corde : this.cordes){
            somme += corde.longueur;
        }
        return somme;
    }

    public void ajouterCorde(Corde corde){
        cordes.add(corde); //sqrt((yS2 - yS1)² + (xS2 - xS1)²)
        /*
        int i = 0;
        while(!((corde.sommet1 == cordesPossibles.get(i).sommet1 && corde.sommet2 == cordesPossibles.get(i).sommet2) ||
                (corde.sommet1 == cordesPossibles.get(i).sommet2 && corde.sommet2 == cordesPossibles.get(i).sommet1))) {
            i++;
        }
        indexCordes.add(i);*/
    }

    public void ajouterCordeACordesPossibles(int sommet1, int sommet2){
        cordesPossibles.add(new Corde(sommet1, sommet2, Math.sqrt(Math.pow((this.sommets[sommet2][1] - this.sommets[sommet1] [1]),2) +
                Math.pow((this.sommets[sommet2][0] - this.sommets[sommet1] [0]),2)))); //sqrt((yS2 - yS1)² + (xS2 - xS1)²)
    }

    public void supprCorde(int sommet1, int sommet2){
        cordes.remove(new Corde(sommet1, sommet2, Math.sqrt(Math.pow((this.sommets[sommet2][1] - this.sommets[sommet1] [1]),2) +
                Math.pow((this.sommets[sommet2][0] - this.sommets[sommet1] [0]),2))));
    }

    public void supprCorde(Corde corde){
        cordes.remove(corde);
    }

    public double tailleCorde (int sommet1, int sommet2){
        return Math.sqrt(Math.pow((this.sommets[sommet2][1] - this.sommets[sommet1] [1]),2) +
                Math.pow((this.sommets[sommet2][0] - this.sommets[sommet1] [0]),2));
    }

    public double tailleCorde (Corde corde){
        return Math.sqrt(Math.pow((this.sommets[corde.sommet2][1] - this.sommets[corde.sommet1] [1]),2) +
                Math.pow((this.sommets[corde.sommet2][0] - this.sommets[corde.sommet1] [0]),2));
    }

    public Polygone retirerSommet (int sommet) {///renvoie un polygone créé en retirant le sommet numéro sommet au polygone this
        double[][] newSommets = new double[this.nbSommets - 1][2];
        boolean sommetPasse = false;
        for (int i = 0; i < this.nbSommets; i++) {
            if (i == sommet) {
                sommetPasse = true;
            } else {
                if (!sommetPasse) {
                    newSommets[i][0] = this.sommets[i][0];
                    newSommets[i][1] = this.sommets[i][1];
                } else {
                    newSommets[i - 1][0] = this.sommets[i][0];
                    newSommets[i - 1][1] = this.sommets[i][1];
                }
            }

        }
        return new Polygone(this.nbSommets - 1, newSommets, this.cordes);
    }
}