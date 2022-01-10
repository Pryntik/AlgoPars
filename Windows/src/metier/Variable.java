package src.metier;

import src.*;
import src.ihm.*;

public class Variable {

	private Controleur ctrl;
	private String     sNom;
	private String     sType;
	private boolean    bConstante;

	//private int     iValeur;
	//private char    cValeur;
	private String  sValeur = "";
	//private boolean bValeur;

	public Variable(Controleur ctrl, String sNom, String sType, boolean bConstante){
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
        if(this.sType.equals("entier") && !this.bConstante)
            return ctrl.listCouleur.get(2).ecrire(ctrl.listCouleur.get(2).getStylo()) + this.sNom + ctrl.listCouleur.get(2).ecrire('0');
        else if(this.bConstante)
            return ctrl.listCouleur.get(1).ecrire(ctrl.listCouleur.get(1).getStylo()) + this.sNom + ctrl.listCouleur.get(1).ecrire('0');
        else
            return ctrl.listCouleur.get(0).ecrire(ctrl.listCouleur.get(0).getStylo()) + this.sNom + ctrl.listCouleur.get(0).ecrire('0');
    }

    public void setValeur(String sValeur) { this.sValeur = sValeur; }

    public String toString()
    {
        return "Nom: "        + String.format("%-15s",this.getNomColore()) 
        +      " Type: "      + String.format("%-10s",sType) 
        +      " Constante: " + String.format("%-10s",bConstante)
        +      " Valeur: "    + sValeur;
    }
}
