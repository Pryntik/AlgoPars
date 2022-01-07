package src.metier;

import src.*;
import src.ihm.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Algo
{

	private String sNom;
	private ArrayList<String> alLignes = new ArrayList<String>();
	private ArrayList<Variable> alVariables = new ArrayList<Variable>();
	private String   ligne = "";
	private String[] parts;
	private String   type;
	private int      i = 0;

	public Algo(ArrayList<String> alLignes, ArrayList<String> alVarsTracer)
	{
		this.alLignes = alLignes;
		setNom();

		// *** INTERPRETATION DU CODE *** //
		while(this.i<alLignes.size())
		{
			// RECUPERATION DES VARIABLES ET CONSTANTES //
			this.ligne = alLignes.get(this.i);
			recupVariable("constante");
			recupVariable("variable");

			// DEBUT DES INSTRUCTIONS //
			if(this.ligne.contains("DEBUT"))
			{
				while(!this.ligne.contains("FIN"))
				{
					executeInstruction(this.ligne);
					this.i++;
					this.ligne = alLignes.get(this.i);
				}
			}
			this.i++;
		}

		// Affichage des variables gardées en mémoire //
		System.out.println("--- Variables et Constantes --- ");
		for(Variable var : alVariables)
		{
			System.out.println(var);
		}
	}

	public void recupVariable(String sVar)
	{
		String sVardiff = "constante";
		if(sVar.contains("constante"))
			sVardiff = "variable";

		if(this.ligne.contains(sVar))
		{
			this.i++;
			this.ligne = alLignes.get(this.i);
			while(!this.ligne.contains("DEBUT") && !this.ligne.contains(sVardiff))
			{
				this.parts = this.ligne.split(":");
				type  = String.valueOf(this.parts[1]);
				if(this.parts[0].contains(","))
				{
					this.parts  = this.parts[0].split(",");
					for(int j = 0; j<this.parts.length; j++)
					{
						this.ajouterVariable(this.parts[j], type, true);
					}
				}
				else
				{
					this.ajouterVariable(this.parts[0], type, true);
				}
				this.i++;
				this.ligne = alLignes.get(this.i);
			}
		}
	}

	public void ajouterVariable(String sNom, String sType, boolean bConstante)
	{
		alVariables.add(new Variable(sNom, sType, bConstante));
	}

	public void ajouterLigne(String ligne)
	{
		alLignes.add(ligne);
	}

	public void setNom()
	{
		String[] parts;
		parts = alLignes.get(0).split("ALGORITHME");
		this.sNom = parts[1].trim();
	}

	public Variable getVariable(String sNomVar)
	{
		for(Variable var : alVariables){
			if(sNomVar.equals(var.getNom()))
			{
				return var;
			}
		}
		return null;
	}

	public void executeInstruction(String sLigne)
	{
		String[] parts;

		// Affectation //
		if(sLigne.contains("<--"))
		{
			parts = sLigne.split("<--");
			getVariable(parts[0].trim()).setValeur(parts[1]);
		}
		if(sLigne.contains("écrire"))
		{
			if(sLigne.contains("\""))
			{
				parts = sLigne.split("\"");
				System.out.println(parts[1]);
			}
			else{
				parts = sLigne.split(Pattern.quote("("));
				parts = parts[1].split(Pattern.quote(")"));
				System.out.println(getVariable(parts[0].trim()).getValeur());
			}
		}
		if(sLigne.contains("lire"))
		{
			String sInput;
			Scanner sc = new Scanner(System.in);
			parts      = sLigne.split(Pattern.quote("("));
			parts      = parts[1].split(Pattern.quote(")"));
			sInput     = sc.nextLine();
			getVariable(parts[0].trim()).setValeur(sInput);
		}
	}
}
