package metier;

import java.util.ArrayList;

public class Menu
{

    public void demande()
    {
        System.out.print("+----------------------------------------------+\n" + 
                           "|                                              |\n" +
                           "| Quel mode souhaitez vous ?                   |\n" +
                           "|                                              |\n" + 
                           "| [A]utomatique ?                              |\n" +
                           "| [P]as à pas ?                                |\n" + 
                           "| [Q]uitter ?                                  |\n" +
                           "+----------------------------------------------+\n");

    }
    public void modeAutomatique(boolean auto)
    {
        if (auto)
            System.out.println("Mode Automatique activé !");
        else
            System.out.println ("Mode pas à pas activé !");
    }

    public void choixFichier(ArrayList<String> arrayFichiers)
    {
        String sRes;
        if (arrayFichiers.size() == 0)
        {
            sRes =         "+----------------------------------------------+\n" +
                           "| Il n'y a pas de fichiers .algo               |\n" +
                           "| à lire dans le dossiers \"fichiers\"         |\n" +
                           "+----------------------------------------------+\n" ;
            System.out.println(sRes);
            return;    
        }
        int cpt = 1;
        sRes =   "+----------------------------------------------+\n" + 
                 "|                                              |\n" +
                 "| Quel fichier souhaitez vous lire ?           |\n";
        for (String s : arrayFichiers)
        {
            if (cpt<10)
            {
                sRes +="| ("+cpt+") " + String.format("%-41s", s) + "|\n";
            }
            else
            {
                sRes +="| ("+cpt+") " + String.format("%-40s", s) + "|\n";
            }
            cpt++;
        }           
        sRes += "+----------------------------------------------+\n";
        System.out.println(sRes);        
    }
}