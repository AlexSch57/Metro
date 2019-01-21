
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Classe représentant le Menu, l'interface avec l'utilisateur
 * @author Alexandre Schwitthal
 * @version 1.0
 */
public class Menu
{
    private String currentFile;
    private Graphe leGraphe;
    
    public Menu() throws IOException
    {
        this.currentFile = "Aucun";
        this.leGraphe = null;
    }

    public void setCurrentFile(String currentFile)
    {
        this.currentFile = currentFile;
    }

    public Graphe getLeGraphe()
    {
        return leGraphe;
    }

    public void setLeGraphe(Graphe leGraphe)
    {
        this.leGraphe = leGraphe;
    }
    
    public void displayMenu()
    {
        System.out.println("\nBienvenue dans le projet ! \n"
                + "Fichier courant : " + this.currentFile + " \n\n"
                + "Veuillez selectionner une des options suivantes : \n"
                + "1. Choisir le fichier à utiliser \n"
                + "2. Affichage des 10 sommets les plus connectés \n"
                + "3. Chosir deux sommets et trouver la connexion entre les deux \n"
                + "4. Vérification des 6 degrés de séparation du fichier courant \n"
                + "5. Affichage du chemin passant par toutes les lignes de metro (metro-wl.txt uniquement) \n"
                + "6. Quittez l'application \n");
    }
    
    /**
    ** Methode principale de la classe Mneu, elle appelle toutes les autres
    *  selon le choix de l'utilisateur, effectuera une action différente
    */
    public void switchChoix() throws IOException, CloneNotSupportedException, CheminInexistantException
    {
        switch (choixUtilisateur())
        {
            case 1:
                this.selectFile();
                break;

            case 2:
                List<Sommet> listS = this.leGraphe.tenMaxConnections();
                int place = 1;
                for(Sommet so : listS)
                {
                    System.out.println("n°" + place + " : " + so.getSommetAdjacent().size() + " - " + so.toString());
                    place++;
                }
                break;

            case 3:
                Chemin c = this.selectItineraire();
                System.out.println(c);
                System.out.println("Taille du chemin : " + c.getLongueur());
                
                break;

            case 4:
                if(this.leGraphe.sixDegrees())
                {
                    System.out.println("L'on observe les 6 degrés sur chaque sommet de ce graphe");
                }
                else
                {
                    System.out.println("Les 6 degrés ne sont pas valide pour ce graphe");
                }
                break;

            case 5:
                if(!(this.currentFile.equals("metro-wl.txt")))
                {
                    System.out.println("Le fichier courant n'est pas metro-wl.txt, veuillez choisir une autre option ou changer le fichier");
                }
                else
                {
                    System.out.println("Veuillez patientez .. Génération du chemin en cours ..");
                    CheminMetro cm = this.leGraphe.trajetLigne();
                    System.out.println("lignes : ");
                    for(String s: cm.getLignes())
                    {
                       System.out.print(s + " ");
                    }
                    System.out.println("\n");
                    System.out.println(cm);
                    System.out.println("taille : " + cm.getLongueur());
                }
                break;
                
            case 6:
                System.out.println("fin du programme");
                System.exit(0);
                break;
                
            default:
                System.out.println("ERREUR");
                break;
        }
    }
     
    /**
    ** Methode gérant le choix de l'utilisateur sur le menu 
    * @return un entier correspondant au choix de l'utilisateur
    */
    public int choixUtilisateur()
    {
        Scanner scan = new Scanner(System.in);
        int nombre = 0;
        int limite = 6;
        if(this.leGraphe == null)
        {
            limite = 1;
        }
        do
        {
            try
            {
                nombre = scan.nextInt();
                if((nombre < 1 || nombre > limite) && nombre != 6)
                {
                    if(limite == 1)
                    {
                        System.out.println("Vous ne pouvez pas faire d'autres choix que le n°1 tant que vous n'avez pas choisi de fichier");
                    }
                    else
                    {
                        System.out.println("Veuillez séléctionner un nombre entre 1 et 6");
                    }
                }
            } 
            catch (InputMismatchException ex)
            {
                if(limite == 1)
                {
                    System.out.println("Vous ne pouvez pas faire d'autres choix que le n°1 tant que vous n'avez pas choisi de fichier");
                }
                else
                {
                    System.out.println("Veuillez séléctionner un nombre entre 1 et 6");
                }
                return choixUtilisateur();
            }
        } while ((nombre < 1 || nombre > limite) && nombre != 6);

        return nombre;
    }
    
    /**
    ** Methode gérant la selection et le chargement du fichier utilisé pour la création du graphe
    */
    public void selectFile() throws FileNotFoundException, IOException
    {
        boolean exist = false;
        File f = new File(System.getProperty("user.dir"));
        f = new File(f.getAbsoluteFile() + "\\" + "DossierTxt");
        String[] tab = f.list();
        System.out.println("Liste des fichiers :");
        for(String s : tab)
        {
            System.out.println(s);
        }
        System.out.println("\n");
        while(!(exist))
        {
            System.out.println("Sélectionner un fichier :");
            Scanner sc = new Scanner(System.in);
            String reponse = sc.nextLine();
            f = new File(System.getProperty("user.dir"));
            f = new File(f.getAbsolutePath() + "\\" + "DossierTxt" + "\\" + reponse);
            if((f.exists() && f.isFile()))
            {
                this.currentFile = reponse;
                this.leGraphe = new Graphe();
                this.leGraphe.initialisation(reponse);
                exist = true;
            }    
        } 
    }
    
    /**
    ** Methode gérant la selection d'un itinéraire entre deux sommets
    * @return Objet de type Chemin allant du premier sommets choisi jusqu'au 2ème
    */
    public Chemin selectItineraire() throws CloneNotSupportedException, CheminInexistantException
    {
        Sommet[] tab = new Sommet[2];
        System.out.println("Liste des sommets : ");
        for(Sommet s : this.leGraphe.getLesSommets())
        {
            System.out.println(s.toString());
        }
        System.out.println("\nChoix du sommet de départ :");
        tab[0] = ChoixSommet();
        System.out.println("\nChoix du sommet d'arrivé :");
        do
        {
            tab[1] = ChoixSommet(); 
            if(tab[1].equals(tab[0]))
            {
                System.out.println("Se sommet est similaire au choix de départ !");
            }
        }
        while((tab[1].equals(tab[0])));
        
        return this.leGraphe.trajet(tab[0], tab[1]);
    }
    
        /**
    ** Methode utilisé dans selectItineraire(), elle permet de chosiir un sommet parmis la liste des sommets existant
    */
    public Sommet ChoixSommet()
    {
        Sommet s = null;
        String chaine;
        Scanner scan = new Scanner(System.in, "ISO-8859-1");
        try
        {
            chaine = scan.nextLine();
            if(chaine.contains(""))
            {
                chaine = chaine.replace("", "—");
            }
            for(Sommet so : this.leGraphe.getLesSommets())
            {
                if(so.getNom().equals(chaine))
                {
                    s = so;
                }
            }
            if(s == null)
            {
                System.out.println("ce Sommet n'existe pas !");
                s = ChoixSommet();
            }
        } 
        catch (InputMismatchException ex)
        {
            System.out.println("ERREUR");
        }
        return s;
    }
    
}
