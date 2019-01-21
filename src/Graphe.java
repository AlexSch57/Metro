
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Classe représentant le graphe
 * @author Alexandre Schwitthal
 * @version 1.0
 */
public class Graphe 
{
    final private List<Sommet> lesSommets;
    
    public Graphe()
    {
        this.lesSommets = new ArrayList<>();
    }

    public List<Sommet> getLesSommets()
    {
        return lesSommets;
    }
    
    /**
    ** Initialisation du graphe.
    ** créer la liste des sommets et des arcs du graphe
    @param nomDoc nom du fichier txt utilisé pour la génération
    */
    public void initialisation(String nomDoc) throws FileNotFoundException, IOException
    {
        boolean partieDeux = false;
        // récupération des informations du fichier txt
        File f = new File("./DossierTxt/" + nomDoc);  
        BufferedReader br = new BufferedReader(new FileReader(f));
        String inputLine;
        // parcours du fichier txt
        while ((inputLine = br.readLine()) != null)
        {
            if(!(inputLine.contains("%")))
            {
                //Lorsque l'on atteint la partie "### connections" l'on passe à la lecture des arcs, sinon l'on crée les sommets
                if(inputLine.contains("###"))
                {
                    partieDeux = true;
                }
                
                /*
                ----- CREATION DES SOMMETS -----
                */
                if(!(partieDeux))
                {                    
                    if(!(inputLine.contains("Sens Ouest-Est")))
                    { 
                        // vérification de l'existence ou non du sommets dans la listes des sommets pour éviter les doublons
                        boolean exist = false;
                        for(Sommet s: lesSommets)
                        {
                            if((s.getNom().equals(inputLine)))
                            {
                                exist = true;
                            }
                        }  
                        if(!(exist))
                        {
                            // Selon le fichier utilisé, l'on créer un sommet classique ou un sommet spécifique
                            // Ici, le seul sommet spécifique est réservé au fichier pour la question bonus (metro-wl.txt)
                            if(nomDoc.equals("metro-wl.txt"))
                            {
                                this.lesSommets.add(new SommetMetro(inputLine));
                            }
                            else
                            {
                                this.lesSommets.add(new Sommet(inputLine));  
                            }
                        }
                    } 
                }
                /*
                ----- CREATION DES ARCS -----
                */
                else
                {
                    if(!(inputLine.contains("###")))
                    {
                        if(inputLine.contains(":") || inputLine.contains(">"))
                        {
                            // Selon le séparateur présent dans le fichier texte, certaines opérations différeront légèrement
                            // Arc orienté          >
                            // Arc non orienté      : 
                            String separator;
                            if(inputLine.contains(":"))
                            {
                                separator = ":";
                            }
                            else
                            {
                                separator = ">";
                            }
                            String[] tab = inputLine.split(separator);
                            Sommet debut = new Sommet();
                            Sommet fin = new Sommet();
                            // création d'une variable emplacementFin utilisé pour la place d'un élément dans le tableau tab récupérer par le split de l'inputLine
                            // emplacementFin désignera l'emplacement du futur sommet présent à la fin de l'arc
                            // dans la situation "normal" il s'agira du 2ème élement du tableau (donc 1)
                            // dans la situation spécifique de la question bonus, il s'agira du 3ème élement (2) car le deuxième élement étant l'indication de la ligne de metro
                            int emplacementFin = 1;
                            for(Sommet s: lesSommets)
                            {
                                if(s instanceof SommetMetro)
                                {
                                   emplacementFin = 2;     
                                }
                                if(s.getNom().equals(tab[0]))
                                {
                                    debut = s;
                                }
                                if(s.getNom().equals(tab[emplacementFin]))
                                {
                                    fin = s;
                                }
                                // partie réservé à la question bonus :
                                // si les sommets début et fin ne contiennent pas la ligne correspondant à l'arc courant
                                // alors l'on ajoute la ligne au(x) sommet(s)
                                if(debut instanceof SommetMetro && fin instanceof SommetMetro)
                                {
                                    if(!(((SommetMetro) debut).getLignes().contains(tab[1])))
                                    {
                                        ((SommetMetro) debut).addLignes(tab[1]);     
                                    }   
                                    if(!(((SommetMetro) fin).getLignes().contains(tab[1])))
                                    {
                                        ((SommetMetro) fin).addLignes(tab[1]);                             
                                    } 
                                }
                                emplacementFin = 1;
                            }
                            
                            // vérification de l'existence de l'arc
                            // si debut ou fin existe déjà dans la liste des sommets adjacent de l'autre, alors l'on ne fait rien
                            // sinon, l'on ajoute l'adjacence des sommets entre eux
                            boolean exist = false;
                            if(debut.getSommetAdjacent().contains(fin) || fin.getSommetAdjacent().contains(debut))
                            {
                                exist = true;
                            }
      
                            if(!(exist))
                            {
                                // selon le type d'arc (orienté ou non), l'on effectue deux traitements différents
                                if(separator.equals(":"))
                                {
                                    debut.addSommet(fin);
                                    fin.addSommet(debut);
                                }
                                else
                                {
                                    debut.addSommet(fin);
                                }

                            }
                        }
                    }
                } 
            }
        }
    }
    
    /**
    ** Question 1 et 3a:
    ** Recherche dans la liste des sommets du graphe les 10 sommets disposant du plus grand nombre de connexions adjacentes non-redondantes
    @return listeSommets : La liste des 10 sommets
    */
    public List<Sommet> tenMaxConnections()
    {
        int min = 9999;
        List<Sommet> listeSommets = new ArrayList<>();
        // parcours de la liste des sommets
        for(Sommet s: lesSommets)
        {
            // ajout systématique du sommet si la liste n'est pas de taille 10
            if(listeSommets.size() < 10)
            {
                listeSommets.add(s);
            }
            //sinon
            else
            {
                // recherche du sommets présent dans la liste
                // disposant du plus petit nombre de sommet adjacent (Sommet nommé "toRemove)
                Sommet toRemove = null;
                for(Sommet so : listeSommets)
                {
                    if(so.getSommetAdjacent().size() < min)
                    {
                        
                        min = so.getSommetAdjacent().size();
                        toRemove = so;
                    }
                   
                }
                // Si le sommet courant à plus de sommet adjacent que le plus petit (toRemove)
                // retrait de toRemove et ajout du nouveau sommet
                if(s.getSommetAdjacent().size() > min)
                {
                    listeSommets.remove(toRemove);
                    listeSommets.add(s);
                }
                min = 9999;
            }     
        }
        
        //trie de la liste par ordre décroissant selon le nombre de sommet adjacent
        Collections.sort(listeSommets, (Sommet s1, Sommet s2) -> s2.getSommetAdjacent().size() - (s1.getSommetAdjacent().size()));
        return listeSommets;
    }
    
    /**
    ** Question 2 et 3b:
    ** Recherche d'un plus court chemin entre deux sommets donnée
    @param debut    : Sommet de départ du chemin
    @param fin      : Sommet d'arrivé du chemin
    * 
    @exception CloneNotSupportedException   : erreur lors du clonage du chemin
    @exception CheminInexistantException    : le chemin n'existe pas
    * 
    @return : Objet de type chemin dont le sommet de départ est "debut" et le sommet d'arrivé est "fin"
    */
    public Chemin trajet(Sommet debut, Sommet fin) throws CloneNotSupportedException, CheminInexistantException
    {
        // déclaration des variables
        List<Chemin> lesChemins = new ArrayList<>();
        List<Sommet> sommetMarque = new ArrayList<>();
        
        // marquage du sommet debut
        sommetMarque.add(debut);
        
        /*
            création d'un chemin de départ, 
            qui disposera comme arc de départ un arc composé uniquement du sommet debut
            puis ajout de ce chemin dans la liste de Chemin 
        */
        Chemin cheminInitial = new Chemin();
        cheminInitial.addArc(new Arc(debut, debut));
        lesChemins.add(cheminInitial);
        
        // Parcours de la liste des chemins
        for(int i = 0; i < lesChemins.size(); i++)
        {
            // Récupération de l'extremite finale du cheminCourant 
            Chemin cheminCourant = lesChemins.get(i);
            Sommet s = cheminCourant.getExtremiteFin();
            // pour chaque sommet adjacent à l'extrémité finale du cheminCourant
            for(Sommet so : s.getSommetAdjacent())
            {
                // si le sommet n'est pas marqué
                if(!(sommetMarque.contains(so)))
                {
                    /* 
                        création d'un nouveau chemin
                        auquel l'on ajoute un arc allant de l'extrémité du chemin courant (s) au sommet non marqué présent dans la liste des sommets adjacent de s
                        puis, ajout du chemin à la liste des chemins
                        et ajout du sommet à la liste des sommets marqués
                    */
                    Chemin nouveauChemin = (Chemin) cheminCourant.clone();
                    nouveauChemin.addArc(new Arc(s, so));
                    lesChemins.add(nouveauChemin);
                    sommetMarque.add(so);    
                        
                    // si le sommet atteint est égal au sommet final, alors l'on a trouvé le chemin à retourner 
                    if(so.equals(fin))
                    {
                        return nouveauChemin;
                    }
                }    
            }
        }
        // si l'on atteint cette partie, cela signifit que tout les suivant de debut ont été marqué
        // mais que le sommet fin ne fait pas partie des suivants de debut
        // cela signifit alors qu'il n'existe aucun chemin de debut à fin
        throw new CheminInexistantException();
    }
    
    
    /**
    ** Question 4 :
    ** Methode permettant la preuve de l'existence ou non des six degrés de séparation 
    * 
    @return un booléen :
    * true si l'on peut atteindre tout les sommets du graphe à l'aide d'un chemin de longueur inférieur à 6
    * false sinon
    */
    public boolean sixDegrees() throws CheminInexistantException, CloneNotSupportedException
    {
        List<Sommet> listCourante = new ArrayList<>();
        for(Sommet s : lesSommets)
        {
            listCourante.add(s);
        }
        // l'on vérifie si pour chaque sommet, l'on peut atteindre tout les autres
        for(Sommet s : lesSommets)
        {
            // Parcours de la liste des sommets
            for(Sommet so : listCourante)
            {
                // si le sommet courant (so) est différent du sommet s
                if(!(so.equals(s)))
                {
                    /*
                        Réalisation du chemin de s à so
                        s'il est plus grand que 6, alors les 6 degrés de séparation sont invalides
                        l'on retourne alors false
                        de plus, si la création du chemin est impossible, alors les 6 degrés sont forcément faux aussi
                    */
                    try
                    {
                        Chemin c = trajet(s, so);
                        if(c.getLongueur() > 6)
                        {
                            return false;
                        } 
                    }
                    catch(CheminInexistantException cie)
                    {
                        return false;
                    }

                }
            }
             listCourante.remove(s);
        }
      
        // si l'on atteint cette partie, cela signifie qu'il n'existe aucun sommet que s ne peut atteindre en moins de 6 étapes
        // les 6 degrés sont alors confirmé, l'on retourne donc true
        return true;
    }
        
    /**
    ** Question bonus :
    ** Recherche d'un plus court chemin qui utilise au moins un tronçon de chacune des lignes de métro
    @return CheminMetro : objet de type de CheminMetro étant le plus court chemin trouvé utilisant au moins un tronçon de chaque ligne
    */
    public CheminMetro trajetLigne()
    {
        /*
        ----- Recherche du nombre de ligne présent dans le graphe (en cas d'utilisation avec un autre metro que celui de Paris par exemple) -----
        */
        int nbLignes = 0;
        List<String> listLignes = new ArrayList<>();
        for(Sommet s : lesSommets)
        {
            for(String str : s.getLignes())
            {
                if(!(listLignes.contains(str)))
                {
                    listLignes.add(str);
                    nbLignes++;
                }
            }
        }
        /*
        ----- Fin recherche ----- 
        */

        
        // c : chemin qui sera retourné
        CheminMetro c = new CheminMetro();
        // min : taille du plus petit chemin parcourant chaque ligne
        int min = 9999;
        // limite : taille à partir de laquel, l'on arrête de chercher un chemin
        int limite = 30;
        // parcours de la liste des sommets
        for(Sommet s : lesSommets)
        {
            // déclaration des variables
            List<Chemin> lesChemins = new ArrayList<>();
            CheminMetro cheminInitial = new CheminMetro();
            
            // ajout d'un arc ayant pour sommet unique le sommet courant (s)
            cheminInitial.addArc(new Arc(s, s));
            lesChemins.add(cheminInitial);
            boolean found = false; 
        
            loop:
            while(!(found))
            {
                // ligneMax : determine le ou les chemins actuellement présent disposant du maximum de ligne
                int ligneMax = 0;
                for(int i = 0; i < lesChemins.size(); i++)
                {
                    // parcours les chemins de la liste, ignore les chemins dont le nombre de ligne est inférieur à ligneMax
                    Chemin ch = lesChemins.get(i);
                    if(ch.getLignes().size() >= ligneMax)
                    {
                        // remplace ligneMax par la valeur du nombre de chemin du chemin courant s'il est supérieur
                        if(ch.getLignes().size() > ligneMax)
                        {
                            ligneMax = ch.getLignes().size();
                        }
                        /*
                            Pour chaque extremité terminale du chemin courant 
                            créer un clone du chemin courant et y ajoute l'extremité courante
                            et ajoute ce nouveau chemin à la liste des chemins
                        */
                        for(Sommet so : ch.getExtremiteFin().getSommetAdjacent())
                        {
                            CheminMetro nouveauChemin = (CheminMetro) ch.clone();
                            nouveauChemin.addArc(new Arc(ch.getExtremiteFin(), so));
                            lesChemins.add(nouveauChemin);
                            
                            /*
                                Si le nouveau chemin dispose d'un nombre de ligne supérieur ou égal au nombre recherché (dans le cas du metro de paris : 16)
                                alors l'on test s'il est plus court que le chemin courant
                                si c'est le cas, alors il devient le plus court chemin passant par chaque ligne pour le sommet courant (s)
                            */
                            if(nouveauChemin.getLignes().size() >= nbLignes)
                            {
                                if(nouveauChemin.getLongueur() <= min)
                                {
                                    min = nouveauChemin.getLongueur();
                                    c = nouveauChemin;
                                    limite = min;
                                    found = true;  
                                    break loop;

                                }
                            }
                            /*
                                instauration d'une limite pour limiter la consommation de ressource mémoire
                                étant donné que l'on recherche le plus court chemin pour tout les sommets
                                il est inutile de chercher à générer un chemin plus long que le chemin minimal déjà présent
                            */
                            if(nouveauChemin.getLongueur() > limite)
                            {
                                break loop;
                            }
                        }
                    }
                }
            }
        }
        return c;
    }
}
