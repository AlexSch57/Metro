
import java.util.ArrayList;
import java.util.List;

/**
 * Classe héritée de Sommet, utilisé uniquement pour la question bonus, l'on y ajoute une liste de String, correspondant à chaque ligne du sommet
 * @author Alexandre Schwitthal
 * @version 1.0
 */
public class SommetMetro extends Sommet
{
    private final List<String> lignes;
    
    public SommetMetro(String nom, List<Sommet> sommetAdjacent, List<String> lignes)
    {
        super(nom, sommetAdjacent);
        this.lignes = lignes;
    }
        
    public SommetMetro(String nom)
    {
        super(nom);
        this.sommetAdjacent = new ArrayList<>();
        this.lignes = new ArrayList<>();
    }
    
    public SommetMetro()
    {
        this.nom = null;
        this.sommetAdjacent = new ArrayList<>();
        this.lignes = new ArrayList();
    }

    @Override
    public List<String> getLignes()
    {
        return lignes;
    }
    
    public void addLignes(String ligne)
    {
        this.lignes.add(ligne);
    }
    
    @Override
    public String toString()
    {
        String s = "";
        s = lignes.stream().map((i) -> i + " ").reduce(s, String::concat);
        return super.toString() + " : " + s;
    }
}
