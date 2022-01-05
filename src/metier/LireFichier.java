package metier;

import ihm.Couleur;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;



public class LireFichier
{
	public LireFichier() {}

	// *** LECTURE DU FICHIER ALGO *** //

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

			Algo algo = new Algo(sList);

			return sList;
		}
		catch(IOException e){e.printStackTrace(); return null;}
	}

	// *** LECTURE DU FICHIER XML *** //

	public ArrayList<Couleur> LireFichierXML(String fichier)
	{
		String coulVari  = "a";
		String poidsVari = "b";
		String coulCons  = "c";
		String poidsCons = "d";
		String coulChif  = "e";
		String poidsChif = "f";
		String coulGene  = "g";
		String poidsGene = "h";
		ArrayList<Couleur> listCouleur = new ArrayList<Couleur>();

		try
		{
			SAXBuilder sax    = new SAXBuilder();
			Document   doc    = sax.build(new File(fichier));
			Element    racine = doc.getRootElement();
			List<Element> listConfig = racine.getChildren("configuration");
			for (Element config : listConfig)
			{
				String        nomConfig = config.getAttributeValue("nom");
				List<Element> listVar   = config.getChildren("variable");
				List<Element> listCons  = config.getChildren("constante");
				List<Element> listChif  = config.getChildren("chiffre");
				List<Element> listGene  = config.getChildren("generale");

				for (Element vari : listVar)
				{
					coulVari  = vari.getAttributeValue("couleur");
					poidsVari = vari.getAttributeValue("poids");
				}
				for (Element cons : listCons)
				{
					coulCons  = cons.getAttributeValue("couleur");
					poidsCons = cons.getAttributeValue("poids");
				}
				for (Element chif : listChif)
				{
					coulChif  = chif.getAttributeValue("couleur");
					poidsChif = chif.getAttributeValue("poids");
				}
				for (Element gene : listGene)
				{
					coulGene  = gene.getAttributeValue("couleur");
					poidsGene = gene.getAttributeValue("poids");
				}
				listCouleur.add(new Couleur(nomConfig, coulVari, poidsVari));
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
}