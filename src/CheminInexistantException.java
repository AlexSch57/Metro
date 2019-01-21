/**
 * Sous-classe de Exeption, elle est généré lorsqu'il n'existe pas de chemin d'un sommet A vers un sommet B
 * @author Alexandre Schwitthal
 * @version 1.0
 */
public class CheminInexistantException extends Exception
{
    public CheminInexistantException()
    {               
        System.out.println("il n'existe aucun chemin entre les deux sommets");
    }
}
