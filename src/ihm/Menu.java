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

        this.sFile = "../src/fichiers/" + choixFichier();
        ClearConsole();
        this.v = new Vue(this.lf.LireFichier(sFile), iType);
    }

    // *** SELECTION DU FICHIER *** //

    public String choixFichier()
    {
        String sRes;
        int    i;
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
            i = Integer.parseInt(sc.nextLine());
            while(i <= 0 || i > alFichiers.size())
            {
                System.out.print("Le fichier n'est pas dans la liste\n" +
                                   "Nouveau choix : ");
                i = Integer.parseInt(sc.nextLine());
            }
            
            return alFichiers.get(i-1);
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