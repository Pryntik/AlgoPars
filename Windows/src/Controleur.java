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
import java.util.Collection;
import java.util.Collections;
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

	public  char   cMode;
	public  String sCoulRest;
	public  String sStyloRest;
	public  String cCoulVari;
	public  String cCoulFonc;
	public  String cCoulCond;

	public  ArrayList<Couleur>  alCouleur;
	public  ArrayList<Variable> alVariables;

	public  ArrayList<Couleur>  alTheme;
	private ArrayList<String>   alAllFile;
	private ArrayList<String>   alLigneAlgo;
	private ArrayList<String>   alLigneVar;

	public static void main(String[] args)
	{
		Controleur ctrl = new Controleur();
	}

	public Controleur()
	{

		// DEMARRAGE DU PROGRAMME //

		menu      = new Menu(this);
		cMode     = menu.choixMode();
		alAllFile = menu.recupFichier();

		// RECUPERATION DES FICHIERS .algo , .var , .xml //

		alLigneAlgo  = LireFichierALGO(alAllFile.get(0));
		alLigneVar   = LireFichierALGO(alAllFile.get(1));
		alCouleur    = LireFichierXML(alAllFile.get(2));

		// CHOIX DU THEME ET OBTENTION DES COULEURS //

		alTheme    = new ArrayList<Couleur>();
		alTheme    = menu.choixTheme();
		sCoulRest  = alTheme.get(0).ecrire('r');
		sStyloRest = alTheme.get(0).ecrire('W');
		cCoulVari  = alTheme.get(0).ecrire(alTheme.get(0).getStylo());
		cCoulFonc  = alTheme.get(1).ecrire(alTheme.get(1).getStylo());
		cCoulCond  = alTheme.get(2).ecrire(alTheme.get(2).getStylo());

		// CREATION DE L'ALGO ET RECUPERATION DES VARIABLES ET CONSTANTES //

		algo         = new Algo(this, alLigneAlgo, alLigneVar);
		alVariables  = algo.recupVar();
		
		clearConsole();

		// AFFICHAGE DU FICHIER EN FONCTION DU MODE CHOISI //
		
		vue  = new Vue(this, alLigneAlgo);
		if (cMode == 'A')
		{
			vue.defilementAuto();
			vue.baseConsole();
			algo.debutInterpretationAuto();
		}
		else
		{
			vue.defilementPAP();
		}
	}

	// *** PERMET DE LIRE UN FICHIER DONNE EN PARAMETRE ET DE RETOURNER UNE ARRAYLIST DE CHAQUE LIGNE DE CE FICHIER *** //
	public ArrayList<String> LireFichierALGO(String fichier)
	{
		try
		{
			// Creation d'objet pour la lecture de fichier.algo
			FileInputStream   fis    = new FileInputStream(fichier);				        // Fichier d'entree
			InputStreamReader isr    = new InputStreamReader(fis, StandardCharsets.UTF_8);	// File Reader
			BufferedReader    br     = new BufferedReader(isr);								// BufferedReader
			StringBuffer      sb     = new StringBuffer();    

			ArrayList<String> alList = new ArrayList<String>();

			String sLine;
			int i = 0;

			while((sLine = br.readLine()) != null)
			{
				// Ajoute la ligne a l'arraylist
				alList.add(sLine);
				i++;
			}
			isr.close();

			return alList;
		}
		catch(IOException e){e.printStackTrace(); return null;}
	}

	// *** PERMET DE LIRE UN FICHIER XML DONNE EN PARAMETRE ET DE RETOURNER UNE ARRAYLIST DE COULEUR *** //
	public ArrayList<Couleur> LireFichierXML(String fichier)
	{
		String sNomVari   = "";
		String sNomFonc   = "";
		String sNomCond   = "";
		String sNomGene   = "";

		char   cCoulVari  = ' ';
		char   cCoulFonc  = ' ';
		char   cCoulCond  = ' ';
		char   cCoulGene  = ' ';

		char   cPoidsVari = ' ';
		char   cPoidsFonc = ' ';
		char   cPoidsCond = ' ';
		char   cPoidsGene = ' ';

		alCouleur = new ArrayList<Couleur>();

		// LECTURE DU FICHIER .xml //
		try
		{
			SAXBuilder sax    = new SAXBuilder();
			Document   doc    = sax.build(new File(fichier));
			Element    racine = doc.getRootElement();

			List<Element> listConfig = racine.getChildren("theme");
			
			for (Element config : listConfig)
			{
				String        sTheme     = config.getAttributeValue("num");
				List<Element> listVari   = config.getChildren("variable");
				List<Element> listFonc   = config.getChildren("constante");
				List<Element> listCond   = config.getChildren("chiffre");
				List<Element> listGene   = config.getChildren("generale");

				for (Element vari : listVari)
				{
					sNomVari   = vari.getAttributeValue("nom");
					cCoulVari  = vari.getAttributeValue("couleur").charAt(0);
					cPoidsVari = vari.getAttributeValue("poids").charAt(0);
				}
				for (Element cons : listFonc)
				{
					sNomFonc   = cons.getAttributeValue("nom");
					cCoulFonc  = cons.getAttributeValue("couleur").charAt(0);
					cPoidsFonc = cons.getAttributeValue("poids").charAt(0);
				}
				for (Element chif : listCond)
				{
					sNomCond   = chif.getAttributeValue("nom");
					cCoulCond  = chif.getAttributeValue("couleur").charAt(0);
					cPoidsCond = chif.getAttributeValue("poids").charAt(0);
				}
				for (Element gene : listGene)
				{
					sNomGene   = gene.getAttributeValue("nom");
					cCoulGene  = gene.getAttributeValue("couleur").charAt(0);
					cPoidsGene = gene.getAttributeValue("poids").charAt(0);
				}
				alCouleur.add(new Couleur(sTheme, sNomVari, cCoulVari, cPoidsVari));
				alCouleur.add(new Couleur(sTheme, sNomFonc, cCoulFonc, cPoidsFonc));
				alCouleur.add(new Couleur(sTheme, sNomCond, cCoulCond, cPoidsCond));
			}
			return alCouleur;
		}
		catch(Exception e){e.printStackTrace(); return null;}
	}

	// RECUPERE LES FICHIERS .algo //
	public ArrayList<String> recupFichiers()
	{
		ArrayList<String> alFichiers = new ArrayList<>();
		File f = new File("../src/fichiers/");

		for (File file : f.listFiles())
			if (!file.isDirectory())
				if (file.getName().contains(".algo"))
					alFichiers.add(file.getName());
				
		Collections.sort(alFichiers);
		return alFichiers;
	}

    // *** METHODE POUR EFFACER LA CONSOLE *** //
	public static void clearConsole()
	{
		try
		{
			String sOperatingSystem = System.getProperty("os.name"); //Check the current operating system
			  
			if(sOperatingSystem.contains("Windows"))
			{        
				ProcessBuilder pb    = new ProcessBuilder("cmd", "/c", "cls");
				Process startProcess = pb.inheritIO().start();
				startProcess.waitFor();
			} else
			{
				ProcessBuilder pb    = new ProcessBuilder("clear");
				Process startProcess = pb.inheritIO().start();

				startProcess.waitFor();
			} 
		}catch(Exception e){e.printStackTrace();}
	}
}