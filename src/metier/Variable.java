package metier;

public class Variable {

	private String  sNom;
	private String  sType;

	private int     iValeur;
	private char    cValeur;
	private String  sValeur;
	private boolean bValeur;

	public Variable(String sNom, int iValeur){
		this.sNom    = sNom;
		this.sType   = "int";
		this.iValeur = iValeur;
	}
	public Variable(String sNom, char cValeur){
		this.sNom    = sNom;
		this.sType   = "char";
		this.cValeur = cValeur;
	}
	public Variable(String sNom, String sValeur){
		this.sNom    = sNom;
		this.sType   = "string";
		this.sValeur = sValeur;
	}
	public Variable(String sNom, boolean bValeur){
		this.sNom    = sNom;
		this.sType   = "boolean";
		this.bValeur = bValeur;
	}

	public String getValeur(){

		switch(sType){

			case "int":         return String.valueOf(iValeur);
			case "char":        return String.valueOf(cValeur);
			case "string":      return String.valueOf(sValeur);
			case "boolean":     return String.valueOf(bValeur);
		}
		return null;
	}

	public String getNom(){ return sNom; }
	
}
