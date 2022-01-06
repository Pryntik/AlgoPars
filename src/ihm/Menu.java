package ihm;

import metier.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu
{
    private LireFichier lf;
    private Couleur coul;
    private Vue v;
    private String sFile;
    private String sFileXML;
    private char iType;
    

    public Menu(boolean auto)
    {
        this.lf   = new LireFichier();
        this.coul = new Couleur(" ", ' ', ' ');

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

        this.sFileXML = "../src/metier/configuration.xml";
        this.lf.LireFichierXML(sFileXML);
        choixConfig();

        this.sFile = "../src/fichiers/" + choixFichier();
        ClearConsole();
        this.v = new Vue(this.lf.LireFichier(sFile), iType);
    }

    public String choixConfig()
    {
        int    choix;
        String sRes = "";
        ArrayList<Couleur> alCouleur = new ArrayList<Couleur>();
        alCouleur = lf.LireFichierXML(this.sFileXML);

        coul.start();
        String c1Vari = coul.ecrire(alCouleur.get(0).getStylo()) + "Variables" + coul.ecrire('0'); // Vert
        String c1Cons = coul.ecrire(alCouleur.get(1).getStylo()) + "Constantes" + coul.ecrire('0'); // Jaune
        String c1Chif = coul.ecrire(alCouleur.get(2).getStylo()) + "Chiffres" + coul.ecrire('0'); // Bleu
        String c2Vari = coul.ecrire(alCouleur.get(3).getStylo()) + "Variables" + coul.ecrire('0'); // Violet
        String c2Cons = coul.ecrire(alCouleur.get(4).getStylo()) + "Constantes" + coul.ecrire('0'); // Magenta
        String c2Chif = coul.ecrire(alCouleur.get(5).getStylo()) + "Chiffres" + coul.ecrire('0'); // Rouge
        String c3Vari = coul.ecrire(alCouleur.get(6).getStylo()) + "Variables" + coul.ecrire('0'); // Vert
        String c3Cons = coul.ecrire(alCouleur.get(7).getStylo()) + "Constantes" + coul.ecrire('0'); // Cyan
        String c3Chif = coul.ecrire(alCouleur.get(8).getStylo()) + "Chiffres" + coul.ecrire('0'); // Jaune

        sRes += "+-----------------------------------------------------------------------------+\n" +
                "|                                                                             |\n" +
                "| Quel configuration souhaitez vous utiliser ?                                |\n" +
                "|                                                                             |\n" +
                "+-----------------------------------------------------------------------------+\n|";
        for (int i = 1; i <= 3; i++)
        {
            sRes += " ["+i+"] " + String.format("%-20s", "config" + i) + "|";
        }
        sRes += "\n+-------------------------+-------------------------+-------------------------+\n" +

                  "| " + c1Vari + "               | " + c2Vari + "               | "   + c3Vari + "               |\n"  +
                  "| " + c1Cons + "              | "  + c2Cons + "              | "    + c3Cons + "              |\n"   +
                  "| " + c1Chif + "                | " + c2Chif + "                | " + c3Chif + "                |\n" +
                  "+-------------------------+-------------------------+-------------------------+\n";

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