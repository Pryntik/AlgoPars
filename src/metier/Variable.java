package metier;

public class Variable {

	private String  sNom;
	private String  sType;

	private int     iValeur;
	private char    cValeur;
	private String  sValeur;
	private boolean bValeur;

	public Variable(String sNom, String sType){
		this.sNom    = sNom;
		this.sType   = sType;
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
		return "Type: " + sType + " Nom: " + sNom;
	}
	
}
