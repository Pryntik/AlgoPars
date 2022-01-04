package ihm;

import metier.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu
{
    private int type;
    private LireFichier lf;

    public Menu(boolean auto)
    {
        this.lf = new LireFichier();

        if (auto)
        {
            System.out.println("Mode Automatique activé !");
            this.type = 0;
        }

        else
        {
            System.out.println ("Mode pas à pas activé !");
            this.type = 1;
        }

        choixFichier();

        String file = "../src/fichiers/file.algo";
        this.lf.LireFichier(file, type);
    }

    public String choixFichier()
    {
        String sRes;
        ArrayList<String> arrayFichiers = new ArrayList<String>();
        arrayFichiers = lf.recupFichiers();
        if (arrayFichiers.size() == 0)
        {
            sRes =         "+----------------------------------------------+\n" +
                           "| Il n'y a pas de fichiers .algo               |\n" +
                           "| à lire dans le dossiers \"fichiers\"         |\n" +
                           "+----------------------------------------------+\n" ;
            System.out.println(sRes);
            return null;
        }
        else
        {
            int cpt = 1;
            sRes =  "+----------------------------------------------+\n" + 
                    "|                                              |\n" +
                    "| Quel fichier souhaitez vous lire ?           |\n";
            for (String s : arrayFichiers)
            {
                if (cpt<10)
                {
                    sRes +="| ["+cpt+"] " + String.format("%-41s", s) + "|\n";
                }
                else
                {
                    sRes +="| ["+cpt+"] " + String.format("%-40s", s) + "|\n";
                }
                cpt++;
            }
            sRes += "+----------------------------------------------+\n";
            System.out.println(sRes);
            Scanner sc = new Scanner(System.in);
            return sc.nextLine();
        }
    }
}