
import java.util.ArrayList;
import java.util.List;

/**
 * Classe héritée de Chemin, spéficique à la question bonus
 * @author Alexandre Schwitthal
 * @version 1.0
 */
public class CheminMetro extends Chemin
{
    private List<String> lignes;
    
    public CheminMetro(List<Arc> lesArcs, Sommet extremiteDebut, Sommet extremiteFin, int longueur, List<String> lignes)
    {
        super(lesArcs, extremiteDebut, extremiteFin, longueur);
        this.lignes = lignes;
    }
        
    public CheminMetro()
    {
        this.lesArcs = new ArrayList<>();
        this.extremiteDebut = null;
        this.extremiteFin = null;
        // initialisation à -1, car le premier arc ajouté sera toujours un arc d'un sommet x à ce même sommet x, 
        // qui passera alors la longueur à 0
        this.longueur = -1;
        this.lignes = new ArrayList<>();
    }

    @Override
    public List<String> getLignes()
    {
        return lignes;
    }
       
    /**
    ** fonction gérant l'ajout d'un arc, avec la mise à jour automatique des extremités du graphe.
    @param a : objet de type Arc qui sera le nouvelle Arc à ajouté
    * de plus, l'on gère aussi l'ajout de nouvelle ligne ou non dans la liste des lignes du cheminMetro
    */
    @Override
    public void addArc(Arc a)
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
            // ajout des lignes dans le chemin
            SommetMetro d = (SommetMetro) a.getDebut();
            SommetMetro f = (SommetMetro) a.getFin();
             
            List<String> communs = new ArrayList<>(d.getLignes());
            communs.retainAll(f.getLignes());
                
            if(!(d.equals(f)))
            {
                for (String s : communs)
                {
                    if(!(this.lignes.contains(s)))
                    {
                        this.lignes.add(s);
                        break;
                    }
                } 
            } 
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
                SommetMetro d = (SommetMetro) a.getDebut();
                SommetMetro f = (SommetMetro) a.getFin();

                List<String> communs = new ArrayList<>(d.getLignes());
                communs.retainAll(f.getLignes());
                if(!(d.equals(f)))
                {
                    for (String s : communs)
                    {
                        if(!(this.lignes.contains(s)))
                        {
                            this.lignes.add(s);
                            break;
                        }
                    } 
                }
            }
        }
    }
    /**
    ** Clone du CheminMetro
    ** le clonage est une deep copy, ainsi il s'agira bien d'un nouveau chemin (disposant de valeur similaire)
    *  mais qui réferera uniquement à lui-même, la modification de l'un n'entreinera pas la modification de l'autre 
    */
    @Override
    public CheminMetro clone()
    {
        List<Arc> listA = new ArrayList<>();
        for(Arc a : this.lesArcs)
        {
            listA.add(a);
        }
        Sommet d = new Sommet(this.extremiteDebut.getNom(), this.extremiteDebut.getSommetAdjacent());
        Sommet f = new Sommet(this.extremiteFin.getNom(), this.extremiteFin.getSommetAdjacent());
        int l = this.longueur;
        
        List<String> listS = new ArrayList<>();
        {
            for(String s : this.lignes)
            {
                listS.add(s);
            }
        }
        CheminMetro c = new CheminMetro(listA, d, f, l, listS);
        return c; 
    }
}
