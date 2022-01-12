package src.metier;

import src.*;
import src.ihm.*;

public class Variable
{
	private Controleur ctrl;
	private String     sNom;
	private String     sType;
	private boolean    bConstante;

	//private int     iValeur;
	//private char    cValeur;
	private String  sValeur = "";
	//private boolean bValeur;

	public Variable(Controleur ctrl, String sNom, String sType, boolean bConstante)
	{
		this.ctrl       = ctrl;
		this.sNom       = sNom.trim();
		this.sType      = sType.trim();
		this.bConstante = bConstante;
	}

	public String getValeur(){

		switch(sType){

			//case "entier":      return String.valueOf(iValeur);
			//case "caractère":   return String.valueOf(cValeur);
			case "chaine":      return String.valueOf(this.sValeur);
			//case "booléen":     return String.valueOf(bValeur);
		}

		return this.sValeur;
	}

	public String getNom(){ return sNom; }

	public String getNomColore()
    {
        return ctrl.alTheme.get(0).ecrire(ctrl.alTheme.get(0).getStylo()) + this.sNom + ctrl.alTheme.get(0).ecrire('0');
    }

	public String toString(){
		return "Nom: "        + String.format("%-20s",getNomColore())
		+      " Type: "      + String.format("%-20s",sType) 
		+      " Constante: " + String.format("%-20s",bConstante)
		+      " Valeur: "    + String.format("%-20s",sValeur);
	}

	public void setValeur(String sValeur){
		this.sValeur = sValeur;
	}
	
}
