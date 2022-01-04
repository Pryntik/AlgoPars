package ihm;

import metier.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu
{
    private String file;
    private LireFichier lf;
    private Vue v;
    private int type;
    

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

        this.file = "../src/fichiers/" + choixFichier();
        this.v = new Vue(this.lf.LireFichier(file, type));
    }

    public String choixFichier()
    {
        String sRes;
        int    i;
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
            i = Integer.parseInt(sc.nextLine());
            while(i <= 0 || i > arrayFichiers.size())
            {
                System.out.print("Le fichier n'est pas dans la liste\n" +
                                   "Nouveau choix : ");
                i = Integer.parseInt(sc.nextLine());
            }
            
            return arrayFichiers.get(i-1);
        }
    }
}