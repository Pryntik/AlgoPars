package src.ihm;

import src.*;
import src.metier.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu
{
    private Controleur ctrl;
    private Couleur coul;
    private Vue v;
    private String sFileAlgo;
    private String sFileVar;
    private String sFileXML;
    private char iType;
    

    public Menu(Controleur ctrl)
    {
        this.ctrl   = ctrl;
        this.coul   = new Couleur(" ", ' ', ' ');
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
        String theme =  choixTheme();

        

        ClearConsole();
        ctrl.algo = new Algo(this.ctrl.LireFichier(sFileAlgo), this.ctrl.LireFichier(sFileVar), this.ctrl);
        ctrl.creerVue(this.ctrl.LireFichier(sFileAlgo), iType);
    }

    public String choixTheme()
    {
        int    choix;
        String sRes = "";
        ArrayList<Couleur> alCouleur = new ArrayList<Couleur>();
        alCouleur = ctrl.LireFichierXML(this.sFileXML);

        String c1Vari = coul.ecrire(alCouleur.get(0).getStylo()) + "Variables"  + coul.ecrire('0'); // Vert
        String c1Cons = coul.ecrire(alCouleur.get(1).getStylo()) + "Constantes" + coul.ecrire('0'); // Jaune
        String c1Chif = coul.ecrire(alCouleur.get(2).getStylo()) + "Chiffres"   + coul.ecrire('0'); // Bleu
        String c2Vari = coul.ecrire(alCouleur.get(3).getStylo()) + "Variables"  + coul.ecrire('0'); // Violet
        String c2Cons = coul.ecrire(alCouleur.get(4).getStylo()) + "Constantes" + coul.ecrire('0'); // Magenta
        String c2Chif = coul.ecrire(alCouleur.get(5).getStylo()) + "Chiffres"   + coul.ecrire('0'); // Rouge
        String c3Vari = coul.ecrire(alCouleur.get(6).getStylo()) + "Variables"  + coul.ecrire('0'); // Vert
        String c3Cons = coul.ecrire(alCouleur.get(7).getStylo()) + "Constantes" + coul.ecrire('0'); // Cyan
        String c3Chif = coul.ecrire(alCouleur.get(8).getStylo()) + "Chiffres"   + coul.ecrire('0'); // Jaune

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

                  "| " + c1Vari + "               |"  + c2Vari + "                |"  + c3Vari  + "                |" + "\n" +
                  "| " + c1Cons + "              |"   + c2Cons + "               |"   + c3Cons  + "               |"  + "\n" +
                  "| " + c1Chif + "                |" + c2Chif + "                 |" + c3Chif  + "                 |"+ "\n" +
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
            
        return "theme" + choix;
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