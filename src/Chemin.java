
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un chemin, qui est composé d'un ensemble d'arc
 * @author Alexandre Schwitthal
 * @version 1.0
 */
public class Chemin implements Cloneable
{
    protected List<Arc> lesArcs;
    protected Sommet extremiteDebut;
    protected Sommet extremiteFin;
    protected int longueur;

    public List<Arc> getLesArcs()
    {
        return lesArcs;
    }

    public Sommet getExtremiteDebut()
    {
        return extremiteDebut;
    }

    public Sommet getExtremiteFin()
    {
        return extremiteFin;
    }

    public int getLongueur()
    {
        return longueur;
    }
    
    public void setExtremiteDebut(Sommet extremiteDebut)
    {
        this.extremiteDebut = extremiteDebut;
    }

    public void setExtremiteFin(Sommet extremiteFin)
    {
        this.extremiteFin = extremiteFin;
    }

    public void setLongueur(int longueur)
    {
        this.longueur = longueur;
    }
    
    
    public Chemin(List<Arc> lesArcs, Sommet extremiteDebut, Sommet extremiteFin, int longueur)
    {
        this.lesArcs = lesArcs;
        this.extremiteDebut = extremiteDebut;
        this.extremiteFin = extremiteFin;
        this.longueur = longueur;
    }
        

    public Chemin()
    {
        this.lesArcs = new ArrayList<>();
        this.extremiteDebut = null;
        this.extremiteFin = null;
        this.longueur = -1;
    }
    
    @Override
    public String toString()
    {
      String s = "";
      for(Arc a: this.lesArcs)
      {
          s += a.getFin().toString();
          s += "\n";
      }
      return s;
    }
    
    /**
    ** fonction gérant l'ajout d'un arc, avec la mise à jour automatique des extremités du graphe.
    @param a : objet de type Arc qui sera le nouvelle Arc à ajouté
    */
    public void addArc(Arc a)
    {
        boolean alreadyExist = false;
        for(Arc ar: this.lesArcs)
        {
            if((ar.getDebut() == a.getDebut() && ar.getFin() == a.getFin()) ||
                    ar.getDebut() == a.getFin() && ar.getFin() == a.getDebut())
            {
                alreadyExist = true;
            }
        }
        if(!(alreadyExist))
        {
            if((this.extremiteDebut == null) || (this.extremiteFin == null))
            {
                if((this.extremiteDebut == null))
                {
                    this.extremiteDebut = a.getDebut();
                }
                if((this.extremiteFin == null))
                {
                    this.extremiteFin = a.getFin(); 
                }
                this.lesArcs.add(a);
                this.longueur++;
            }
            else
            {
                if(!((!(this.extremiteDebut.equals(a.getDebut()))) && 
                        (!(this.extremiteFin.equals(a.getFin()))) &&
                        (!(this.extremiteDebut.equals(a.getFin()))) &&
                        (!(this.extremiteFin.equals(a.getDebut())))))
                {
                   
                    if(this.extremiteFin.equals(a.getDebut()))
                    {
                        this.extremiteFin = a.getFin();
                    }
                    if(this.extremiteFin.equals(a.getFin()))
                    {
                        this.extremiteFin = a.getFin();
                    }
                    this.lesArcs.add(a);
                    this.longueur++;
                }
            }
        }
    }
    
    public boolean isExtremite(Sommet s)
    {
        return (s.equals(this.extremiteDebut) || s.equals(this.extremiteFin));
    }
    
    public boolean isExtremiteFinale(Sommet s)
    {
        return (s.equals(this.extremiteFin));
    }
    
    /**
    ** Clone du Chemin
    ** le clonage est une deep copy, ainsi il s'agira bien d'un nouveau chemin (disposant de valeur similaire)
    *  mais qui réferera uniquement à lui-même, la modification de l'un n'entreinera pas la modification de l'autre 
    */
    @Override
    public Chemin clone()
    {
        List<Arc> listA = new ArrayList<>();
        for(Arc a : this.lesArcs)
        {
            listA.add(a);
        }
        Sommet d = new Sommet(this.extremiteDebut.getNom(), this.extremiteDebut.getSommetAdjacent());
        Sommet f = new Sommet(this.extremiteFin.getNom(), this.extremiteFin.getSommetAdjacent());
        int l = this.longueur;
        
        Chemin c = new Chemin(listA, d, f, l);
        return c; 
    }
    
    
    public boolean contains(Sommet s)
    {
        boolean exist = false;
        for(Arc a : this.lesArcs)
        {
            if((a.getDebut() == s) || (a.getFin() == s))
            {
                exist = true;
            }
        }
        return exist;
    }
    
        /**
    ** METHODE QUI NE SERA JAMAIS APPELLE
    ** elle sert uniquement à ne pas provoquer d'erreur lors de l'appel sur un cheminMetro
    *  la vérification s'il s'agit bien d'un cheminMetro est effectué avant son appel
    */
    public List<String> getLignes()
    {
        return null;
    }
    
}
