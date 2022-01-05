package metier;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class LireFichier
{
	public LireFichier() {}

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
		catch(IOException e){System.out.println(e); return null;}
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