package structure;

import java.util.Objects;

public class Corde {
    public int sommet1;
    public int sommet2;
    public double longueur;

    public Corde(int sommet1, int sommet2, double longueur) {
        this.sommet1 = sommet1;
        this.sommet2 = sommet2;
        this.longueur = longueur;
    }

    @Override
    public String toString() {
        return "Corde{" +
                "sommet1=" + sommet1 +
                ", sommet2=" + sommet2 +
                ", longueur=" + longueur +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Corde corde = (Corde) o;
        return sommet1 == corde.sommet1 &&
                sommet2 == corde.sommet2 &&
                Double.compare(corde.longueur, longueur) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sommet1, sommet2, longueur);
    }
}
