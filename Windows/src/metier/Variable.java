package src.metier;

import src.*;
import src.ihm.*;

public class Variable
{
	private Controleur ctrl;
	private String     sNom;
	private String     sType;
	private boolean    bConstante;

	private String  sValeur = "";

	public Variable(Controleur ctrl, String sNom, String sType, boolean bConstante)
	{
		this.ctrl       = ctrl;
		this.sNom       = sNom.trim();
		this.sType      = sType.trim();
		this.bConstante = bConstante;
	}

	public String getValeur(){ return this.sValeur; }

	public String getNom(){ return sNom; }

	public String getNomColore(){ return ctrl.alTheme.get(0).ecrire(ctrl.alTheme.get(0).getStylo()) + this.sNom + ctrl.alTheme.get(0).ecrire('0'); }

	public void setValeur(String sValeur){ this.sValeur = sValeur; }

	public String toString()
	{
		return "Nom: "        + String.format("%-20s",getNomColore())
		+      " Type: "      + String.format("%-20s",sType) 
		+      " Constante: " + String.format("%-20s",bConstante)
		+      " Valeur: "    + String.format("%-20s",sValeur);
	}

	
}
