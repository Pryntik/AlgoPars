package src.ihm;

import src.*;
import src.metier.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu
{
    private Controleur ctrl;
    private Vue v;
    private String sFileAlgo;
    private String sFileVar;
    private String sFileXML;
    private char iType;
    

    public Menu(Controleur ctrl)
    {
        this.ctrl   = ctrl;
        creerMenus();
    }

    public void creerMenus()
    {

        boolean bAuto = true;

        System.out.print("+----------------------------------------------+\n" + 
                         "|                                              |\n" +
                         "| Quel mode souhaitez vous ?                   |\n" +
                         "|                                              |\n" + 
                         "| [A]utomatique ?                              |\n" +
                         "| [P]as à pas ?                                |\n" + 
                         "| [Q]uitter ?                                  |\n" +
                         "+----------------------------------------------+\n");

        boolean bSelection = true;
		while (bSelection)
		{
			Scanner sc = new Scanner(System.in);
			switch (sc.nextLine())
			{
				case "A":
                    bAuto = true;
                    bSelection = false;
					break;
				case "P":
					bAuto = false;
                    bSelection = false;
					break;
				case "Q":
					return;
			}
		}

        if (bAuto)
        {
            System.out.println("Mode Automatique activé !");
            this.iType = '0';
        }

        else
        {
            System.out.println ("Mode pas à pas activé !");
            this.iType = 'R';
        }

        String   sAlgo = choixFichier();
        String[] parts = sAlgo.split(Pattern.quote("."));

        this.sFileAlgo = "../src/fichiers/" + sAlgo;
        this.sFileXML  = "../src/metier/configuration.xml";
        this.sFileVar  = "../src/fichiers/" + parts[0] + ".var";

        this.ctrl.LireFichierXML(sFileXML);
        ArrayList<Couleur> alTheme  = new ArrayList<Couleur>();
        alTheme =  choixTheme();

        ClearConsole();
        this.ctrl.algo = new Algo(this.ctrl.LireFichier(sFileAlgo), this.ctrl.LireFichier(sFileVar));
        this.ctrl.vue  = new Vue(this.ctrl.LireFichier(sFileAlgo), iType);
    }

    public ArrayList<Couleur> choixTheme()
    {
        int    choix;
        String sRes = "";
        ArrayList<Couleur> alCouleur = new ArrayList<Couleur>();
        ArrayList<Couleur> alTheme1  = new ArrayList<Couleur>();
        ArrayList<Couleur> alTheme2  = new ArrayList<Couleur>();
        ArrayList<Couleur> alTheme3  = new ArrayList<Couleur>();
        alCouleur = ctrl.LireFichierXML(this.sFileXML);

        for (int i = 0; i <= 2; i++)
            alTheme1.add(alCouleur.get(i)); // Vert + Jaune + Bleu

        for (int i = 3; i <= 5; i++)
            alTheme2.add(alCouleur.get(i));// Violet + Magenta + Rouge

        for (int i = 6; i <= 8; i++)
            alTheme3.add(alCouleur.get(i)); // Vert + Cyan + Jaune

        sRes += "+-----------------------------------------------------------------------------+\n" +
                "|                                                                             |\n" +
                "| Quel theme souhaitez vous utiliser ?                                        |\n" +
                "|                                                                             |\n" +
                "+-----------------------------------------------------------------------------+\n|";
        for (int i = 1; i <= 3; i++)
        {
            sRes += " ["+i+"] " + String.format("%-20s", "theme") + "|";
        }
        sRes += "\n+-------------------------+-------------------------+-------------------------+\n" +

                  "| " + alTheme1.get(0).getNomColore() + "                | "  + alTheme2.get(0).getNomColore() + "                | "  + alTheme3.get(0).getNomColore()  + "                |" + "\n" +
                  "| " + alTheme1.get(1).getNomColore() + "               | "   + alTheme2.get(1).getNomColore() + "               | "   + alTheme3.get(1).getNomColore()  + "               |"  + "\n" +
                  "| " + alTheme1.get(2).getNomColore() + "                 | " + alTheme2.get(2).getNomColore() + "                 | " + alTheme3.get(2).getNomColore()  + "                 |"+ "\n" +
                  "+-------------------------+-------------------------+-------------------------+\n";

        System.out.println(sRes);
        System.out.println(alCouleur.get(0).front('1'));
        Scanner sc = new Scanner(System.in);
        choix = Integer.parseInt(sc.nextLine());
        while(choix <= 0 || choix > 3)
        {
            System.out.print("La configuration n'est pas dans la liste\n" +
                             "Nouveau choix : ");
            choix = Integer.parseInt(sc.nextLine());
        }

        switch(choix)
        {
            case 1  : return alTheme1;
            case 2  : return alTheme2;
            case 3  : return alTheme3;
            default : return null;
        }
    }

    // *** SELECTION DU FICHIER *** //

    public String choixFichier()
    {
        String sRes;
        int    choix;
        ArrayList<String> alFichiers = new ArrayList<String>();
        alFichiers = ctrl.recupFichiers();
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