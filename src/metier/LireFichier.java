import java.io.*;

public class LireFichier
{
	public static void main(String args[])
	{
		LireFichier lf = new LireFichier("../fichiers/file.txt");
	}

	public LireFichier(String fichier)
	{
		try
		{
			// Creation d'objet pour la lecture de fichier.txt
			File           file = new File(fichier);    	// Fichier d'entrée
			FileReader     fr   = new FileReader(file);		// File Reader        
			BufferedReader br   = new BufferedReader(fr);	// BufferedReader
			StringBuffer   sb   = new StringBuffer();    
			String line;
			while((line = br.readLine()) != null)
			{
				// Ajoute la ligne au buffer
				sb.append(line);      
				sb.append("\n");     
			}
			fr.close();    
			System.out.println("Contenu du fichier : ");
			System.out.println(sb.toString());
		}
		catch(IOException e){e.printStackTrace();}
	}
}