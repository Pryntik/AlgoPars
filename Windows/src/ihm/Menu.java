package src.ihm;

import src.*;
import src.metier.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu
{
    private Controleur        ctrl;
    private Vue               v;
    
    private ArrayList<String> sAllFile;
    private String            sFileAlgo;
    private String            sFileVar;
    private String            sFileXML;
    private char              cMode;
    

    public Menu(Controleur ctrl)
    {
        this.ctrl = ctrl;
    }

    public ArrayList<String> recupFichier()
    {
        String   sAlgo = choixFichier();
        String[] parts = sAlgo.split(Pattern.quote("."));

        sFileAlgo = "../src/fichiers/" + sAlgo;
        sFileVar  = "../src/fichiers/" + parts[0] + ".var";
        sFileXML  = "../src/metier/configuration.xml";

        sAllFile = new ArrayList<String>();
        sAllFile.add(sFileAlgo);
        sAllFile.add(sFileVar);
        sAllFile.add(sFileXML);
        return sAllFile;
    }

    public char choixMode()
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
				case "Q": System.exit(1);
			}
		}

        if (bAuto)
        {
            System.out.println("Mode Automatique activé !");
            cMode = 'A';
        }

        else
        {
            System.out.println ("Mode pas à pas activé !");
            cMode = 'P';
        }

        return cMode;
    }

    // *** SELECTION DU THEME DE COULEUR *** //

    public ArrayList<Couleur> choixTheme()
    {
        int    choix;
        String sRes = "";
        ArrayList<Couleur> alTheme1  = new ArrayList<Couleur>();
        ArrayList<Couleur> alTheme2  = new ArrayList<Couleur>();
        ArrayList<Couleur> alTheme3  = new ArrayList<Couleur>();
        ctrl.listCouleur.get(0).start();

        for (int i = 0; i <= 2; i++)
            alTheme1.add(ctrl.alCouleur.get(i)); // Vert + Jaune + Bleu

        for (int i = 3; i <= 5; i++)
            alTheme2.add(ctrl.alCouleur.get(i));// Violet + Magenta + Rouge

        for (int i = 6; i <= 8; i++)
            alTheme3.add(ctrl.alCouleur.get(i)); // Vert + Cyan + Jaune

        sRes += "+-----------------------------------------------------------------------------+\n" +
                "|                                                                             |\n" +
                "| Quel theme de couleur souhaitez vous utiliser ?                             |\n" +
                "|                                                                             |\n" +
                "+-----------------------------------------------------------------------------+\n|";
        for (int i = 1; i <= 3; i++)
        {
            sRes += " ["+i+"] " + String.format("%-20s", "theme") + "|";
        }
        sRes += "\n+-------------------------+-------------------------+-------------------------+\n";
        for (int i = 0; i <= 2; i++)
        {
            sRes += String.format("| %-33s",alTheme1.get(i).getNomColore()) +
                    String.format("| %-33s",alTheme2.get(i).getNomColore()) +
                    String.format("| %-33s",alTheme3.get(i).getNomColore()) + "|\n";
        }
        sRes += "+-------------------------+-------------------------+-------------------------+\n";

        System.out.println(sRes);
        System.out.println(ctrl.alCouleur.get(0).front('1'));
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
}