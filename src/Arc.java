
/**
 * Classe représentant les arcs, composé d'un sommet de début et de fin
 * @author Alexandre Schwitthal
 * @version 1.0
 */
public class Arc
{
    private Sommet debut;
    private Sommet fin;
    
    public Arc(Sommet debut, Sommet fin)
    {
        this.debut = debut;
        this.fin = fin;
    }
    
    public Sommet getDebut()
    {
        return debut;
    }

    public Sommet getFin()
    {
        return fin;
    }

    public void setDebut(Sommet debut)
    {
        this.debut = debut;
    }

    public void setFin(Sommet fin)
    {
        this.fin = fin;
    }
   
    @Override
    public String toString()
    {
        return "arc de " + this.debut.getNom() + " à " + this.fin.getNom();
    }
}
