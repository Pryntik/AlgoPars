package ihm;

public class Couleur
{
	private String nom;
	private String stylo;
	private String gras;

	public Couleur(String nom, String stylo, String gras)
	{
		this.nom   = nom;
		this.stylo = stylo;
		this.gras  = gras;
	}

	public String ecrire(char stylo)
	{
		switch(stylo)
		{
			case 'R' : return "\u001B[31m";
			case 'V' : return "\u001B[32m";
			case 'B' : return "\u001B[34m";
			case 'N' : return "\u001B[30m";
			default  : return "\u001B[0m";
		}
	}

	public String surligner(char feutre)
	{
		switch(feutre)
		{
			case 'R' : return "\u001b[41m";
			case 'V' : return "\u001b[42m";
			case 'B' : return "\u001b[44m";
			default  : return "\u001b[40m";
		}
	}
}