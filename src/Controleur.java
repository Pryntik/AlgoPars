import metier.*;
import ihm.*;
import java.util.Scanner;


public class Controleur
{
	public static void main(String[] args)
	{
		/*
		boolean auto = true;
		Menu m = new Menu();

		int i = 1;
		while (i==1)
		{
			m.demande();
			Scanner sc = new Scanner(System.in);
			switch (sc.nextLine())
			{
				case "A":
					m.modeAutomatique(auto);
					i = 2;
					break;
				case "P":
					auto = false;
					m.modeAutomatique(auto);
					i = 2;
					break;
				case "Q":
					return;
			}
		}
		*/
		LireFichier lf = new LireFichier("../src/fichiers/file.txt");
		Vue         v  = new Vue();
	}
}