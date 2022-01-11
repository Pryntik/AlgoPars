package src.metier;

import src.*;
import src.ihm.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Algo
{

	private Controleur ctrl;
	private String sNom;
	private ArrayList<String> alLignes = new ArrayList<String>();
	private ArrayList<Variable> alVariables = new ArrayList<Variable>();
	private ArrayList<String> alVarsTracer = new ArrayList<String>();
	private ArrayList<String> alConsole = new ArrayList<String>();

	public Algo(Controleur ctrl, ArrayList<String> alLignes, ArrayList<String> alVarsTracer)
	{
		this.ctrl = ctrl;
		this.alLignes = alLignes;
		this.alVarsTracer = alVarsTracer;

		setNom();
	}

	public void debutInterpretationAuto()
	{
		// DEBUT DES INSTRUCTIONS //
		String ligne = "";

		recupVar(ligne);

		ligne = alLignes.get(0);

		for(int i = 1; i<alLignes.size(); i++){

			ligne = alLignes.get(i);

			if(ligne.contains("DEBUT")){
				while(!ligne.contains("FIN")){
					executeInstruction(ligne);
					i++;
					ligne = alLignes.get(i);
				}
			}
		}
	}

	public void debutInterpretationPAP()
	{
		String ligne = "";

		ligne = alLignes.get(0);

		for(int i = 1; i<alLignes.size(); i++){

			ligne = alLignes.get(i);

			if(ligne.contains("DEBUT")){
				while(!ligne.contains("FIN")){
					executeInstruction(ligne);
					i++;
					ligne = alLignes.get(i);
				}
			}
		}
	}

	public void recupVar(String ligne)
	{
		String[] parts;
		String   type;

		for(int i = 0; i<alLignes.size(); i++)
		{
			// RECUPERATION DES VARIABLES ET CONSTANTES //
			ligne = alLignes.get(i);

			// Constantes //
			if(ligne.contains("constante"))
			{
				i++;
				ligne = alLignes.get(i);
				while(!ligne.contains("DEBUT") && !ligne.contains("variable"))
				{
					parts = ligne.split(":");
					type   = String.valueOf(parts[1]);
					if(parts[0].contains(","))
					{
						parts  = parts[0].split(",");
						for(int j = 0; j<parts.length; j++)
						{
							this.ajouterVariable(parts[j], type, true);
						}
					}
					else
					{
						this.ajouterVariable(parts[0], type, true);
					}
					i++;
					ligne = alLignes.get(i);
				}
			}

			// Variables //
			if(ligne.contains("variable"))
			{
				i++;
				ligne = alLignes.get(i);
				while(!ligne.contains("DEBUT"))
				{
					parts = ligne.split(":");
					type   = String.valueOf(parts[1]);
					if(parts[0].contains(","))
					{
						parts  = parts[0].split(",");
						for(int j = 0; j<parts.length; j++)
						{
							this.ajouterVariable(parts[j], type, false);
						}
					}
					else
					{
						this.ajouterVariable(parts[0], type, false);
					}
					i++;
					ligne = alLignes.get(i);
				}
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
		for(Variable var : alVariables)
		{
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
				this.alConsole.add(parts[1]);
			}
			else
			{
				parts = sLigne.split(Pattern.quote("("));
				parts = parts[1].split(Pattern.quote(")"));
				this.alConsole.add(getVariable(parts[0].trim()).getValeur());
			}
		}
		if(sLigne.contains("lire"))
		{
			String sInput, ligne = "";
			Scanner sc = new Scanner(System.in);
			recupVar(ligne);	
			parts      = sLigne.split(Pattern.quote("("));
			parts      = parts[1].split(Pattern.quote(")"));
			System.out.println("Lecture de la variable "+parts[0]+" au clavier");
			sInput     = sc.nextLine();
			this.alConsole.add(sInput);
			getVariable(parts[0].trim()).setValeur(sInput);
		}
	}

	public String getNom(){ return this.sNom; }
	public ArrayList<String> getVarTracer(){ return this.alVarsTracer; }
	public ArrayList<Variable> getVariables(){ return this.alVariables; }
	public ArrayList<String> getAlConsole() { return this.alConsole;}
}
