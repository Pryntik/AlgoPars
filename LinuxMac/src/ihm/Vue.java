package src.ihm;

import src.*;
import src.metier.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.lang.ProcessBuilder;

public class Vue
{
	private final static int  iNbColonnes    = 80;
	private final static int  iEspacesParTab =  3;
	private Controleur        ctrl;
	private int               y;
	private ArrayList<String> alCopie = new ArrayList<>();
	private ArrayList<String> alLignes;
	private String            sTabs;
	private String            choix = "";
	private int               taille;
	private boolean           f     = true;

	public Vue(Controleur ctrl, ArrayList<String> alLignes)
	{
		this.ctrl  = ctrl;
        this.alLignes = alLignes;
        this.y = 0;
	}

	public void baseTableau(String sCoul)
	{
		// *** DESSIN DU FICHIER *** //
		baseTableauTete();
		selectVar(sCoul);
		baseTableauFin();
	}
	

	public void baseTableauTete()
	{
		// *** DESSIN DE L'EN TETE DU TABLEAU *** //
		System.out.print(ctrl.coulRest);
		saut(3);
		dessinerTrema(11);
		espace(iNbColonnes-11);
		dessinerTrema(11);
		saut(1);
		dessinerLigne(iNbColonnes + 80);
		saut(1);
		dessinerCase("CODE", 2, 3,true);
		dessinerCase("", 1, iNbColonnes-12,false);
		dessinerCase("DONNEES", 1, 1,false);
		saut(1);
		dessinerLigne(iNbColonnes + 80);
		saut(1);
		dessinerTrema(iNbColonnes);
		espace(1);
		dessinerTrema(79);
		saut(1);
	}

	public void selectVar(String sCoul)
	{
		String  sLigne    = "";
		int     iSpace    =  0;
		ArrayList<String> sStock  = new ArrayList<String>();
		ArrayList<String> alVari  = new ArrayList<String>();
		ArrayList<String> alDonnee = new ArrayList<String>();

		for(int i = 0; i < alLignes.size(); i++)
		{
			sLigne = alLignes.get(i).replaceAll("\t", sTabs);
			alDonnee.add(getDonnee(sLigne, i));

			if(sLigne.contains("si ") || sLigne.contains("tq ") || sLigne.contains("écrire") ||
			   sLigne.contains("fsi") || sLigne.contains("ftq") || sLigne.contains("lire")   ||
			   sLigne.contains("sinon"))
			{
				sLigne = coloreFoncCond(sLigne);
				iSpace = 10;
			}
			
			for(int j = 0; j < ctrl.alVariables.size(); j++)
			{
				alVari.add(ctrl.alVariables.get(j).getNom());

				if(sLigne.contains(alVari.get(j) + " ") || sLigne.contains(alVari.get(j) + ","))
				{
					if(i == y)
					{
						sStock.add(stockCase(String.format("%3d",i) + coloreThis(sLigne + space(iSpace), alVari.get(j), sCoul),
										     1, iNbColonnes-6-sLigne.length(),true) + ctrl.coulRest + alDonnee.get(i) + "\n");
					}
					else
						sStock.add(stockCase(String.format("%3d",i) + coloreThis(sLigne + space(iSpace), alVari.get(j), ""),
										     1, iNbColonnes-6-sLigne.length(),true) + ctrl.coulRest + alDonnee.get(i) + "\n");
				}
			}

			if(!sLigne.contains(alVari.get(i)))
				sStock.add(stockCase(String.format("%3d ",i) + sLigne + space(iSpace),
				                     1, iNbColonnes-6-sLigne.length(),true) + ctrl.coulRest + alDonnee.get(i) + "\n");
			
			if(i == y)
			{
				System.out.print(sCoul + sStock.get(0));
			}
			else
			{
				System.out.print(sStock.get(0));
			}

			System.out.print(ctrl.coulRest);
			sStock.clear();
			iSpace = 0;
		}
	}

	// *** FORCE L'AFFICHAGE DES VARIABLES UNE SEULE FOIS *** //
	public String getDonnee(String sLigne, int num)
	{
		String sDonnee = "";
		alCopie.add(num + (num < 10 ? " " : "") + sLigne);
		if(f)
			taille = ctrl.algo.getVariables().size();
		if(num < taille)
		{
			sDonnee +=  stockCase(" " + ctrl.algo.alVariables.get(num).getNom(),    1, iNbColonnes/2-3-ctrl.algo.alVariables.get(num).getNom().length(), false);
			sDonnee +=  stockCase(" " + ctrl.algo.alVariables.get(num).getValeur(), 1, iNbColonnes/2-4-ctrl.algo.alVariables.get(num).getValeur().length(), false);
			num++;
		}
		return sDonnee;
	}

	public String coloreThis(String sLigne, String sVari, String sCoul)
	{
		String   sRet  = "";
		String[] parts = sLigne.split(sVari);
		
		if(sLigne.contains(sVari + ","))
		{
			parts = sLigne.split(",");
			for (int i = 0; i < parts.length; i++)
			{
				if(i == parts.length - 1)
				{
					parts = parts[i].split(":");
					sRet += sCoul + ctrl.coulVari + parts[0] + ctrl.styloRest + ":" + parts[1];
					return " " + sRet;
				}
				else
				{
					sRet += sCoul + ctrl.coulVari + parts[i] + ctrl.styloRest + ",";
				}
			}
			return null;
		}
		else
		{
			for (int i = 0; i < parts.length; i++)
			{
				sRet += sCoul + parts[i];
				if(i != parts.length - 1)
					sRet +=  ctrl.coulVari + sVari + ctrl.styloRest;
			}
			return " " + sRet;
		}
	}

	public String coloreFoncCond(String sLigne)
	{
		if(sLigne.contains("si "))
			sLigne = sLigne.replaceAll("si",     ctrl.coulCond + "si"     + ctrl.styloRest);
		if(sLigne.contains("fsi"))
			sLigne = sLigne.replaceAll("fsi",    ctrl.coulCond + "fsi"    + ctrl.styloRest);
		if(sLigne.contains("sinon"))
			sLigne = sLigne.replaceAll("sinon",  ctrl.coulCond + "sinon"  + ctrl.styloRest);
		if(sLigne.contains("tq "))
			sLigne = sLigne.replaceAll("tq",     ctrl.coulCond + "tq"     + ctrl.styloRest);
		if(sLigne.contains("ftq"))
			sLigne = sLigne.replaceAll("ftq",    ctrl.coulCond + "ftq"    + ctrl.styloRest);
		if(sLigne.contains("écrire"))
			sLigne = sLigne.replaceAll("écrire", ctrl.coulFonc + "écrire" + ctrl.styloRest);
		if(sLigne.contains("lire"))
			sLigne = sLigne.replaceAll("lire",   ctrl.coulFonc + "lire"   + ctrl.styloRest);

		return sLigne;
	}

	public void baseTableauFin()
	{
		// *** DESSIN DU PIED DU TABLEAU *** //
		f = false;
		dessinerTrema(iNbColonnes);
		espace(1);
		dessinerTrema(79);
		saut(1);
	}

	// Permet de dessiner une ligne avec des '-' avec un nombre de colonnes donné //
	public static void dessinerLigne(int inbColonnes)
	{
		for(int i=0; i<inbColonnes; i++)
			System.out.print("-");
	}

	// Permet de dessiner une ligne avec des '¨' avec un nombre de points donné //
	public static void dessinerTrema(int inbPoints)
	{
		for(int i=0; i<inbPoints; i++)
			System.out.print("¨");

	}

	public static void dessinerCase(String sTexte, int iNbColonnesPrefixe, int iNbColonnesSuffixe, boolean bLeftBar)
	{
		System.out.print((bLeftBar ? "|" : "") + String.format("%"+(iNbColonnesPrefixe+sTexte.length())+"s%"+iNbColonnesSuffixe+"s|",sTexte,""));
	}

	public static String stockCase(String sTexte, int iNbColonnesPrefixe, int iNbColonnesSuffixe, boolean bLeftBar)
	{
		String sPrintTexte;
		sPrintTexte = (bLeftBar ? "|" : "") + String.format("%"+(iNbColonnesPrefixe+sTexte.length())+"s%"+iNbColonnesSuffixe+"s|",sTexte,"");
		return sPrintTexte;
	}

	public static void saut(int iNbSauts)
	{
		for(int i=0; i<iNbSauts; i++)
			System.out.print("\n");
	}

	public static void espace(int iNbEspaces)
	{
		for(int i=0; i<iNbEspaces; i++)
			System.out.print(" ");
	}

	public static String space(int iNbEspaces)
	{
		String sRet = "";
		for(int i=0; i<iNbEspaces; i++)
			sRet += " ";
		
		return sRet;
	}

	public void caseInstruction(String sLigne)
	{
        dessinerCase(sLigne,0,79-sLigne.length(),true);
        saut(1);
	}

	public void baseConsole()
	{
		dessinerCase("CONSOLE",1,1,true);
        saut(1);
        dessinerTrema(81);
		for(int i=0; i < ctrl.algo.getAlConsole().size(); i++)
		{
			if (i == 0)
			{
				saut(1);
				caseInstruction(ctrl.algo.getAlConsole().get(i));
			}
			else
			{
				caseInstruction(ctrl.algo.getAlConsole().get(i));
			}
		}
		dessinerTrema(81);
		saut(1);
	}

	public void defilementPAP()
	{
		try
		{
			int    ichoix = 0;
			String sCoul = ctrl.listCouleur.get(0).surligner('R');
			sTabs = "";
			for(int i = 0; i<iEspacesParTab; i++)
				sTabs += " ";
	
			baseTableau(sCoul);
			while(this.y < this.alLignes.size())
			{
				Scanner sc = new Scanner(System.in);
				this.choix = sc.nextLine();
				ctrl.clearConsole();
				if(choix.equals("B") && this.y > 0)
					this.y--;
				else if(choix.isEmpty())
					this.y++;
				else
				{
					ichoix = Integer.parseInt(choix);
					y = ichoix;
				}

				baseTableau(sCoul);
				if(this.alLignes.get(y).contains("lire"))
				{
					ctrl.algo.executeInstruction(this.alLignes.get(y));
					baseConsole();
				}
				else
				{
					ctrl.algo.executeInstruction(this.alLignes.get(y));
					baseConsole();
				}
			}
		}
		catch (Exception e) { System.out.println("Ligne non trouvé"); }
	}

	public void defilementAuto()
	{
		String sCoul = ctrl.listCouleur.get(0).surligner('0');
		baseTableau(sCoul);
		ctrl.algo.debutInterpretationAuto();
		baseConsole();
	}

	public ArrayList<String> getAlCopie()
	{
		return alCopie;
	}

	public int getCompteur()
	{
		return this.y;
	}

	public ArrayList<String> getAlLignes()
	{
		return this.alLignes;
	}
	public void setCompteur(int y)
	{
		this.y = y;
	}
}