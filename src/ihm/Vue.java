package ihm;

public class Vue
{
	private final static int    iNbColonnes = 80;
	private       static String sNomAlgo    = "NomAlgo";

	public Vue()
	{
		// *** DESSIN DE L'EN TETE DE BASE *** //

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

		dessinerCase(String.format("%3d",0)   + " ALGORITHME " + sNomAlgo, 1, 56,true);
		saut(1);

		dessinerCase(String.format("%3d",1)  + " consante(s)", 1, 63,true);
		saut(1);

		dessinerCase(String.format("%3d",2) + " variable(s)", 1, 63,true);
		saut(1);
		
	}

	// Permet de dessiner une ligne avec des '-' avec un nombre de colonnes donné //
	public static void dessinerLigne(int inbColonnes)
	{

		for(int i=0; i<inbColonnes; i++)
			System.out.print("-");

	}

	public static void dessinerTrema(int inbPoints)
	{

		for(int i=0; i<inbPoints; i++)
			System.out.print("¨");

	}

	public static void dessinerCase(String sTexte, int iNbColonnesPrefixe, int iNbColonnesSuffixe, boolean bLeftBar)
	{

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
}