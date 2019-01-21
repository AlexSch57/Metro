
import java.io.IOException;

/**
 * Classe Main, où est exécuté le programme principal
 * @author Alexandre Schwitthal
 * @version 1.0
 */
public class Main
{
     public static void main(String [] args) throws IOException, CloneNotSupportedException, CheminInexistantException
     {
        Menu m = new Menu();
        while(true)
        {
            m.displayMenu();
            m.switchChoix();
        }
     }
}
