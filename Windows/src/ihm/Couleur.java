package src.ihm;

import src.*;
import src.metier.*;

public class Couleur
{
	public static final String RED_UNDERLINED = "\033[4;31m";

	public static final String RESET   = "\u001B[0m";
	public static final String BLACK   = "\u001B[30m";
	public static final String RED     = "\u001B[31m";
	public static final String GREEN   = "\u001B[32m";
	public static final String YELLOW  = "\u001B[33m";
	public static final String BLUE    = "\u001B[34m";
	public static final String PURPLE  = "\u001B[35m";
	public static final String CYAN    = "\u001B[36m";
	public static final String WHITE   = "\u001B[37m";

	public static final String BLACK_BACKGROUND  = "\u001B[40m";
	public static final String RED_BACKGROUND    = "\u001B[41m";
	public static final String GREEN_BACKGROUND  = "\u001B[42m";
	public static final String YELLOW_BACKGROUND = "\u001B[43m";
	public static final String BLUE_BACKGROUND   = "\u001B[44m";
	public static final String PURPLE_BACKGROUND = "\u001B[45m";
	public static final String CYAN_BACKGROUND   = "\u001B[46m";
	public static final String WHITE_BACKGROUND  = "\u001B[47m";

	public static final String GRAS = "\u001B[1m";

	private String theme;
	private String nom;
	private char   stylo;
	private char   gras;

	public Couleur(String theme, String nom, char stylo, char gras)
	{
		this.theme = theme;
		this.nom   = nom;
		this.stylo = stylo;
		this.gras  = gras;
	}

	public String getTheme() { return this.theme; }
	public String getNom()   { return this.nom;   }
	public char   getStylo() { return this.stylo; }
	public char   getGras()  { return this.gras;  }

	public String getNomColore()
	{
		return ecrire(getStylo()) + getNom() + ecrire('0');
	}

	public String ecrire(char stylo)
	{
		switch(stylo)
		{
			case 'R' : return RED;
			case 'G' : return GREEN;
			case 'B' : return BLUE;
			case 'Y' : return YELLOW;
			case 'P' : return PURPLE;
			case 'C' : return CYAN;
			default  : return RESET;
		}
	}

	public String surligner(char feutre)
	{
		switch(feutre)
		{
			case 'R' : return RED_BACKGROUND;
			case 'G' : return GREEN_BACKGROUND;
			case 'B' : return BLUE_BACKGROUND;
			case 'Y' : return YELLOW_BACKGROUND;
			case 'P' : return PURPLE_BACKGROUND;
			case 'C' : return CYAN_BACKGROUND;
			default  : return BLACK_BACKGROUND;
		}
	}

	public String front(char gras)
	{
		if(gras == '0')
			return "";
		else
			return GRAS;
	}

	public String toSring()
	{
		String sRet = "";

		sRet += "Couleur : theme_" + this.theme + "nom_" + this.nom + " stylo_" + this.stylo + " gras_" + this.gras;

		return sRet;
	}
}