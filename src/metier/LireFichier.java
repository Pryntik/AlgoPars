package metier;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class LireFichier
{
	public LireFichier(String fichier)
	{
		System.out.println(LireFichier(fichier));
	}

	public String LireFichier(String fichier)
	{
		try
		{
			// Creation d'objet pour la lecture de fichier.txt
			FileInputStream   fis  = new FileInputStream(fichier);							// Fichier d'entree
			InputStreamReader isr  = new InputStreamReader(fis, StandardCharsets.UTF_8);	// File Reader
			BufferedReader    br   = new BufferedReader(isr);								// BufferedReader
			StringBuffer      sb   = new StringBuffer();    
			String line;
			String sRet = "";

			while((line = br.readLine()) != null)
			{
				// Ajoute la ligne au buffer
				sRet += line + "\n";
			}
			isr.close();

			String[] split = fichier.split("/");
			System.out.println("Contenu du fichier " + split[split.length-1] + " : ");
			System.out.println(sb.toString());

			return sRet;
		}
		catch(IOException e){System.out.println(e); return "\nErreur fichier introuvable\n";}
	}
}