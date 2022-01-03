import metier.LireFichier;
import ihm.Vue;

public class Controleur
{
	public static void main(String[] args)
	{
		LireFichier lf = new LireFichier("../src/fichiers/file.txt");
		Vue v = new Vue();
	}
}