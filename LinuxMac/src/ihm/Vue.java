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
	private ArrayList<String> alLignes;
	private String            sTabs;
	private String            choix = "";

	public Vue(Controleur ctrl, ArrayList<String> alLignes)
	{
		this.ctrl  = ctrl;
		this.alLignes = alLignes;
		
		y = 0;

		sTabs = "";
		for(int i = 0; i<iEspacesParTab; i++)
			sTabs += " ";

		if(ctrl.cMode != 'A')
		{
			while(y < alLignes.size())
			{
				Scanner sc = new Scanner(System.in);

				for (Variable var : ctrl.alVariables)
				{
					System.out.println(var);
				}
				BaseTableau();
				if(choix.equals("r") && y > 0)
					y--;
				else
					y++;

				choix = sc.nextLine();
				ctrl.ClearConsole();
			}
			while(y < alLignes.size())
			{
				BaseTableau();
				y++;
			}
			
		}
		else
		{
			BaseTableau();
		}
	}

	public void BaseTableau()
	{
		// *** DESSIN DE L'EN TETE DE BASE *** //
		System.out.print(ctrl.listCouleur.get(0).surligner('0'));
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
		// *** DESSIN DU FICHIER *** //
		String sLigne;
		for(int i = 0; i < alLignes.size(); i++)
		{
			if(i == y)
				System.out.print(ctrl.listCouleur.get(0).surligner('R'));
			sLigne = alLignes.get(i).replaceAll("\t", sTabs);
			dessinerCase(String.format("%3d",i) + " " + sLigne, 1, iNbColonnes-6-sLigne.length(),true);
			saut(1);
			System.out.print(ctrl.listCouleur.get(0).surligner('0'));
		}
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

	public static void dessinerCase(String sTexte, int iNbColonnesPrefixe, int iNbColonnesSuffixe, boolean bLeftBar){

		String sPrintTexte;

		if(bLeftBar)
			sPrintTexte = String.format("|%"+(iNbColonnesPrefixe+sTexte.length())+"s%"+iNbColonnesSuffixe+"s|",sTexte,"");
		else
			sPrintTexte = String.format("%"+(iNbColonnesPrefixe+sTexte.length())+"s%"+iNbColonnesSuffixe+"s|",sTexte,"");

		System.out.print(sPrintTexte);

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

	public void caseInstruction(String sLigne)
	{
        dessinerCase(sLigne,0,79-sLigne.length(),true);
        saut(1);
	}

	public void dessinnerEnTeteConsole(){
		dessinerCase("CONSOLE",1,1,true);
        saut(1);
        dessinerTrema(79);
        saut(1);
	}
}