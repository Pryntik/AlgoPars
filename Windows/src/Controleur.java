package src;

import src.metier.*;
import src.ihm.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Controleur
{
	public Menu     menu;
	public Algo     algo;
	public Vue      vue;

	public  char                cMode;
	public  ArrayList<Couleur>  listCouleur;
	public  ArrayList<Variable> alVariables;

	private ArrayList<Couleur>  alTheme;
	private ArrayList<String>   sAllFile;
	private ArrayList<String>   sLigneAlgo;
	private ArrayList<String>   sLigneVar;

	public static void main(String[] args)
	{
		Controleur ctrl = new Controleur();
	}

	public Controleur()
	{
		menu     = new Menu(this);
		cMode    = menu.choixMode();
		sAllFile = menu.recupFichier();

		sLigneAlgo  = LireFichierALGO(sAllFile.get(0));
		sLigneVar   = LireFichierALGO(sAllFile.get(1));
		listCouleur = LireFichierXML(sAllFile.get(2));

		alTheme = new ArrayList<Couleur>();
		alTheme = menu.choixTheme();

		algo = new Algo(this, sLigneAlgo, sLigneVar);
		alVariables  = algo.getVariables();

		ClearConsole();

		vue  = new Vue(this, sLigneAlgo);
		if (cMode == 'A')
		{
			vue.defilementAuto();
			vue.dessinnerEnTeteConsole();
			algo.debutInterpretationAuto();
		}
		else
		{
			vue.defilementPAP();
		}
	}

	public ArrayList<String> LireFichierALGO(String fichier)
	{
		try
		{
			// Creation d'objet pour la lecture de fichier.algo
			FileInputStream   fis  = new FileInputStream(fichier);							// Fichier d'entree
			InputStreamReader isr  = new InputStreamReader(fis, StandardCharsets.UTF_8);	// File Reader
			BufferedReader    br   = new BufferedReader(isr);								// BufferedReader
			StringBuffer      sb   = new StringBuffer();    
			String line;
			ArrayList<String> sList = new ArrayList<String>();
			int      i = 0;

			while((line = br.readLine()) != null)
			{
				// Ajoute la ligne a l'arraylist
				sList.add(line);
				i++;
			}
			isr.close();

			return sList;
		}
		catch(IOException e){e.printStackTrace(); return null;}
	}

	// *** LECTURE DU FICHIER XML *** //

	public ArrayList<Couleur> LireFichierXML(String fichier)
	{
		String nomVari = "";
		String nomFonc = "";
		String nomCond = "";
		String nomGene = "";

		char coulVari  = ' ';
		char coulFonc  = ' ';
		char coulCond  = ' ';
		char coulGene  = ' ';

		char poidsVari = ' ';
		char poidsFonc = ' ';
		char poidsCond = ' ';
		char poidsGene = ' ';
		listCouleur = new ArrayList<Couleur>();

		try
		{
			SAXBuilder sax    = new SAXBuilder();
			Document   doc    = sax.build(new File(fichier));
			Element    racine = doc.getRootElement();
			List<Element> listConfig = racine.getChildren("theme");
			for (Element config : listConfig)
			{
				String        theme     = config.getAttributeValue("num");
				List<Element> listVar   = config.getChildren("variable");
				List<Element> listCons  = config.getChildren("constante");
				List<Element> listChif  = config.getChildren("chiffre");
				List<Element> listGene  = config.getChildren("generale");

				for (Element vari : listVar)
				{
					nomVari   = vari.getAttributeValue("nom");
					coulVari  = vari.getAttributeValue("couleur").charAt(0);
					poidsVari = vari.getAttributeValue("poids").charAt(0);
				}
				for (Element cons : listCons)
				{
					nomFonc   = cons.getAttributeValue("nom");
					coulFonc  = cons.getAttributeValue("couleur").charAt(0);
					poidsFonc = cons.getAttributeValue("poids").charAt(0);
				}
				for (Element chif : listChif)
				{
					nomCond   = chif.getAttributeValue("nom");
					coulCond  = chif.getAttributeValue("couleur").charAt(0);
					poidsCond = chif.getAttributeValue("poids").charAt(0);
				}
				for (Element gene : listGene)
				{
					nomGene   = gene.getAttributeValue("nom");
					coulGene  = gene.getAttributeValue("couleur").charAt(0);
					poidsGene = gene.getAttributeValue("poids").charAt(0);
				}
				listCouleur.add(new Couleur(theme, nomVari, coulVari, poidsVari));
				listCouleur.add(new Couleur(theme, nomFonc, coulFonc, poidsFonc));
				listCouleur.add(new Couleur(theme, nomCond, coulCond, poidsCond));
			}
			return listCouleur;
		}
		catch(Exception e){e.printStackTrace(); return null;}
	}

	public ArrayList<String> recupFichiers()
	{
		ArrayList<String> listFichiers = new ArrayList<>();
		File f = new File("../src/fichiers/");
		for (File file : f.listFiles())
			if (!file.isDirectory())
				if (file.getName().contains(".algo"))
					listFichiers.add(file.getName());
				
		return listFichiers;

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