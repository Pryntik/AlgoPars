package ihm;

import metier.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu
{
    private LireFichier lf;
    private Vue v;
    private String sFile;
    private char iType;
    

    public Menu(boolean auto)
    {
        this.lf = new LireFichier();

        if (auto)
        {
            System.out.println("Mode Automatique activé !");
            this.iType = '0';
        }

        else
        {
            System.out.println ("Mode pas à pas activé !");
            this.iType = 'R';
        }

        this.lf.LireFichierXML("../src/metier/configuration.xml");
        choixConfig();

        this.sFile = "../src/fichiers/" + choixFichier();
        ClearConsole();
        this.v = new Vue(this.lf.LireFichier(sFile), iType);
    }

    public String choixConfig()
    {
        String sRes;
        int    choix;

        sRes =  "+----------------------------------------------+\n" + 
                "|                                              |\n" +
                "| Quel configuration souhaitez vous utiliser ? |\n";
        for (int i = 1; i <= 3; i++)
        {
            sRes +="| ["+i+"] " + String.format("%-41s", "config" + i) + "|\n";
        }
        sRes += "+----------------------------------------------+\n";
        System.out.println(sRes);

        Scanner sc = new Scanner(System.in);
        choix = Integer.parseInt(sc.nextLine());
        while(choix <= 0 || choix > 3)
        {
            System.out.print("La configuration n'est pas dans la liste\n" +
                             "Nouveau choix : ");
            choix = Integer.parseInt(sc.nextLine());
        }
            
        return "config" + choix;
    }

    // *** SELECTION DU FICHIER *** //

    public String choixFichier()
    {
        String sRes;
        int    choix;
        ArrayList<String> alFichiers = new ArrayList<String>();
        alFichiers = lf.recupFichiers();
        if (alFichiers.size() == 0)
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
            for (String s : alFichiers)
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
            choix = Integer.parseInt(sc.nextLine());
            while(choix <= 0 || choix > alFichiers.size())
            {
                System.out.print("Le fichier n'est pas dans la liste\n" +
                                   "Nouveau choix : ");
                choix = Integer.parseInt(sc.nextLine());
            }
            
            return alFichiers.get(choix-1);
        }
    }

    // *** METHODE POUR EFFACER LA CONSOLE *** //

    public static void ClearConsole()
	{
        try
		{
            String operatingSystem = System.getProperty("os.name"); //Check the current operating system
              
            if(operatingSystem.contains("Windows"))
			{        
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else
			{
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            } 
        }catch(Exception e){e.printStackTrace();}
    }
}