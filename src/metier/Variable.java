package metier;

public class Variable {

	private String   sNom;
	private String   sType;
	private boolean  bConstante;

	private int     iValeur;
	private char    cValeur;
	private String  sValeur;
	private boolean bValeur;

	public Variable(String sNom, String sType, boolean bConstante){
		this.sNom       = sNom.trim();
		this.sType      = sType.trim();
		this.bConstante = bConstante;
	}

	public String getValeur(){

		switch(sType){

			case "entier":      return String.valueOf(iValeur);
			case "caractère":   return String.valueOf(cValeur);
			case "chaine":      return String.valueOf(sValeur);
			case "booléen":     return String.valueOf(bValeur);
		}
		return null;
	}

	public String getNom(){ return sNom; }

	public String toString(){
		return "Nom: "        + String.format("%-15s",sNom) 
		+      " Type: "      + String.format("%-10s",sType) 
		+      " Constante: " + bConstante;
	}
	
}
