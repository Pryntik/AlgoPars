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

	public Algo algo;
	public Menu menu;
	public Vue vue;

	public static void main(String[] args)
	{
		Controleur ctrl = new Controleur();
	}

	public Controleur()
	{
		menu = new Menu(this);
	}

	public ArrayList<String> LireFichier(String fichier)
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

			String[] split = fichier.split("/");
			System.out.println("Contenu du fichier " + split[split.length-1] + " : ");
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
		char coulVari  = ' ';
		char poidsVari = ' ';
		char coulCons  = ' ';
		char poidsCons = ' ';
		char coulChif  = ' ';
		char poidsChif = ' ';
		char coulGene  = ' ';
		char poidsGene = ' ';
		ArrayList<Couleur> listCouleur = new ArrayList<Couleur>();

		try
		{
			SAXBuilder sax    = new SAXBuilder();
			Document   doc    = sax.build(new File(fichier));
			Element    racine = doc.getRootElement();
			List<Element> listConfig = racine.getChildren("theme");
			for (Element config : listConfig)
			{
				String        nomTheme  = config.getAttributeValue("nom");
				List<Element> listVar   = config.getChildren("variable");
				List<Element> listCons  = config.getChildren("constante");
				List<Element> listChif  = config.getChildren("chiffre");
				List<Element> listGene  = config.getChildren("generale");

				for (Element vari : listVar)
				{
					coulVari  = vari.getAttributeValue("couleur").charAt(0);
					poidsVari = vari.getAttributeValue("poids").charAt(0);
				}
				for (Element cons : listCons)
				{
					coulCons  = cons.getAttributeValue("couleur").charAt(0);
					poidsCons = cons.getAttributeValue("poids").charAt(0);
				}
				for (Element chif : listChif)
				{
					coulChif  = chif.getAttributeValue("couleur").charAt(0);
					poidsChif = chif.getAttributeValue("poids").charAt(0);
				}
				for (Element gene : listGene)
				{
					coulGene  = gene.getAttributeValue("couleur").charAt(0);
					poidsGene = gene.getAttributeValue("poids").charAt(0);
				}
				listCouleur.add(new Couleur(nomTheme, coulVari, poidsVari));
				listCouleur.add(new Couleur(nomTheme, coulCons, poidsCons));
				listCouleur.add(new Couleur(nomTheme, coulChif, poidsChif));
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

	public void creerVue(ArrayList<String> al, char cType){
		this.vue = new Vue(al, cType, this);
	}
}