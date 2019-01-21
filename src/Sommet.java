
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant le Sommet, composé d'un nom (unique) et d'une liste de sommet qu'il peut atteindre directement sans passé par d'autre sommet
 * @author Alexandre Schwitthal
 * @version 1.0
 */
public class Sommet
{
    protected String nom;
    protected List<Sommet> sommetAdjacent;
        
    public Sommet(String nom, List<Sommet> sommetAdjacent)
    {
        this.nom = nom;
        this.sommetAdjacent = sommetAdjacent;
    }
        
    public Sommet(String nom)
    {
        this.nom = nom;
        this.sommetAdjacent = new ArrayList<>();
    }
    
    public Sommet()
    {
        this.nom = null;
        this.sommetAdjacent = new ArrayList<>();
    }
    
    public String getNom()
    {
        return nom;
    }

    public List<Sommet> getSommetAdjacent()
    {
        return sommetAdjacent;
    }
    
    public void addSommet(Sommet s)
    {
        this.sommetAdjacent.add(s);
    }
    
    public boolean equals(Sommet s)
    {
        return this.getNom().equals(s.getNom());
    }
    
    @Override
    public String toString()
    {
      return this.getNom();
    }
    
    /**
    ** METHODE QUI NE SERA JAMAIS APPELLE
    ** elle sert uniquement à ne pas provoquer d'erreur lors de l'appel sur un sommetMetro
    *  la vérification s'il s'agit bien d'un sommetMetro est effectué avant son appel
    */
    public List<String> getLignes()
    {
        return null;
    }
}
