package src.metier;

import src.*;
import src.ihm.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Algo
{

	private Controleur ctrl;
	private String sNom;
	private boolean f = true;
	private boolean boolTmp = true;
	private int indexFtq = 0;
	private int indexTq = 0;
	private int indexFsi = 0;
	private int indexSi = 0;
	private int indexSinon = -1;
	private ArrayList<String> alLignes = new ArrayList<String>();
	public ArrayList<Variable> alVariables = new ArrayList<Variable>();
	public ArrayList<String> alVarsTracer = new ArrayList<String>();
	private ArrayList<String> alConsole = new ArrayList<String>();

	public Algo(Controleur ctrl, ArrayList<String> alLignes, ArrayList<String> alVarsTracer)
	{
		this.ctrl         = ctrl;
		this.alLignes     = alLignes;
		this.alVarsTracer = alVarsTracer;

		setNom();
	}

	public void debutInterpretationAuto()
	{
		// DEBUT DES INSTRUCTIONS //
		String ligne = "";

		recupVar();

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

	public ArrayList<Variable> recupVar()
	{
		String   ligne = "";
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
							this.alVariables.add(new Variable(ctrl, parts[j], type, true));
						}
					}
					else
					{
						this.alVariables.add(new Variable(ctrl, parts[0], type, true));
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
							this.alVariables.add(new Variable(ctrl, parts[j], type, true));
						}
					}
					else
					{
						this.alVariables.add(new Variable(ctrl, parts[0], type, true));
					}
					i++;
					ligne = alLignes.get(i);
				}
			}
		}
		return this.alVariables;
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
		ArrayList<String> ligneFichier = ctrl.vue.getAlCopie();

		if(f)
		{
			for(int i = 0; i < ligneFichier.size(); i++)
			{
				if(ligneFichier.get(i).length() > 4 && ligneFichier.get(i).replaceAll(" ", "").substring(0, 4).contains("tq"))
				{	
					if(ligneFichier.get(i).replaceAll(" ", "").substring(2,4).equals("tq"))
						indexTq = Integer.parseInt(ligneFichier.get(i).replaceAll(" ", "").substring(0,2));
				}
				if(ligneFichier.get(i).contains("ftq"))
					indexFtq = Integer.parseInt(ligneFichier.get(i).trim().substring(0,2));
				if(ligneFichier.get(i).contains("si "))
					indexSi = Integer.parseInt(ligneFichier.get(i).trim().substring(0,2));
				if(ligneFichier.get(i).contains("fsi"))
					indexFsi = Integer.parseInt(ligneFichier.get(i).trim().substring(0,2));
				if(ligneFichier.get(i).contains("sinon"))
					indexSinon = Integer.parseInt(ligneFichier.get(i).substring(0, 2).trim());
			}
			//Collections.reverse(alIndexFsi);
		}
		f = false;

		if(sLigne.length() >= 2 && !sLigne.contains("sinon") && !sLigne.contains("ftq"))
		{			
			if (sLigne.trim().substring(0, 2).equals("tq"))
			{
				if(sLigne.contains("/="))
				{
					if(!evaluerBooleen(sLigne))
						ctrl.vue.setCompteur(indexFtq-1);
				}
				else 
				{
					if(evaluerBooleen(sLigne))
						ctrl.vue.setCompteur(indexFtq-1);
				}
			}

			if(sLigne.trim().substring(0, 2).equals("si"))
			{
				if(sLigne.contains("/="))
				{
					boolTmp = !evaluerBooleen(sLigne);
					if(evaluerBooleen(sLigne))
					{
						if(indexSinon != -1) // initialisé à -1 pour éviter les erreurs, changé si le programme contient un "sinon"
							ctrl.vue.setCompteur(indexSinon-1);
					}
				}
				else 
				{
					boolTmp = evaluerBooleen(sLigne);
					if(!evaluerBooleen(sLigne))
					{
						if(indexSinon != -1) // initialisé à -1 pour éviter les erreurs, changé si le programme contient un "sinon"
							ctrl.vue.setCompteur(indexSinon-1);
					}
				}
			}
		}

		if(sLigne.trim().equals("sinon"))
		{
			System.out.println(boolTmp);
			if(boolTmp)
			{
				ctrl.vue.setCompteur(indexFsi-1);
			}
		}

		if(sLigne.trim().equals("ftq"))
		{
			String ligneTq = ligneFichier.get(indexTq);

			if(ligneTq.contains("/="))
			{
				if(evaluerBooleen(ligneTq.substring(2)))
					ctrl.vue.setCompteur(indexTq-1);
			}
			else 
			{
				if(!evaluerBooleen(ligneTq.substring(2)))
					ctrl.vue.setCompteur(indexTq-1);
			}

		}
		// Affectation //
		if (sLigne.contains(""))
		if(sLigne.contains("<--"))
		{
			parts = sLigne.split("<--");
			if(parts[1].contains(" + ") || parts[1].contains(" - ") || parts[1].contains(" × ") || parts[1].contains(" / "))
				getVariable(parts[0].trim()).setValeur(evaluerExpression(parts[1]));
			else
				getVariable(parts[0].trim()).setValeur(parts[1].replaceAll(" ", ""));
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
		if (sLigne.contains("//"))
		{
			parts = sLigne.split("//");
		}
		if(sLigne.contains("lire"))
		{
			String sInput, ligne = "";
			Scanner sc = new Scanner(System.in);
			recupVar();	
			parts      = sLigne.split(Pattern.quote("("));
			parts      = parts[1].split(Pattern.quote(")"));
			System.out.println("Lecture de la variable "+parts[0]+" au clavier");
			sInput     = sc.nextLine();
			this.alConsole.add(sInput);
			getVariable(parts[0].trim()).setValeur(sInput);
		}
	}

	public boolean evaluerBooleen(String ligne)
	{
		String parts[];
		String sDroite;
		String sNomPremierOperateur;
		String sNomSecondOperateur;
		String sValeurPremierOperateur;
		String sValeurSecondOperateur;
		boolean bEstChaine;

		// *** = *** //

		if(ligne.contains(" =")){
			ligne.replaceAll(" ", "");

			parts = ligne.split("");
			if     (ligne.contains("tq")){ parts = ligne.split("tq"); } // AJOUTER REGEX SI POSSIBLE ICI POUR DETECTER TQ SI ... OU LIMITER SPLIT A 2 //
			else if(ligne.contains("si")){ parts = ligne.split("si"); } // AJOUTER REGEX SI POSSIBLE ICI POUR DETECTER TQ SI ... OU LIMITER SPLIT A 2//
			parts = parts[1].split("=");
			sDroite = parts[1];

			// PREMIER OPERATEUR //

			sNomPremierOperateur = parts[0];

			if(sNomPremierOperateur.contains("\'") || sNomPremierOperateur.contains("\"")){
				parts[0].replaceAll("\'", "");
				parts[0].replaceAll("\"", "");
				sNomPremierOperateur = parts[0];
				bEstChaine = true;
			}
			else{
				sNomPremierOperateur = parts[0];
				bEstChaine = false;
			}

			if(getVariable(parts[0].trim()) == null || bEstChaine){
				sValeurPremierOperateur = sNomPremierOperateur.trim();
			}
			else{
				sValeurPremierOperateur = getVariable(sNomPremierOperateur.trim()).getValeur();
			}

			// SECOND OPERATEUR //

			if     (ligne.contains("alors")){ parts = sDroite.split("alors"); } // AJOUTER REGEX SI POSSIBLE ICI POUR DETECTER TQ SI ... OU LIMITER SPLIT A 2 //
			else if(ligne.contains("faire")){ parts = sDroite.split("faire"); }

			sNomSecondOperateur = parts[0];

			if(sNomSecondOperateur.contains("\'") || sNomSecondOperateur.contains("\"")){
				parts[0] = parts[0].replaceAll("\'", "");
				parts[0] = parts[0].replaceAll("\"", "");
				sNomSecondOperateur = parts[0];
				bEstChaine = true;
			}
			else{
				sNomSecondOperateur = parts[0];
				bEstChaine = false;
			}

			if(getVariable(parts[0].trim()) == null || bEstChaine){
				sValeurSecondOperateur = sNomSecondOperateur.trim();
			}
			else{
				sValeurSecondOperateur = getVariable(sNomSecondOperateur.trim()).getValeur();
			}

			return sValeurPremierOperateur.equals(sValeurSecondOperateur.trim());

		}

		// *** != *** //

		else if(ligne.contains("/=")){
			ligne.replaceAll(" ", "");

			parts = ligne.split("");
			if     (ligne.contains("tq")){ parts = ligne.split("tq"); } // AJOUTER REGEX SI POSSIBLE ICI POUR DETECTER TQ SI ... OU LIMITER SPLIT A 2 //
			else if(ligne.contains("si")){ parts = ligne.split("si"); } // AJOUTER REGEX SI POSSIBLE ICI POUR DETECTER TQ SI ... OU LIMITER SPLIT A 2//
			parts = parts[1].split(Pattern.quote("/="));
			sDroite = parts[1];

			// PREMIER OPERATEUR //

			sNomPremierOperateur = parts[0];

			if(sNomPremierOperateur.contains("\'") || sNomPremierOperateur.contains("\"")){
				parts[0].replaceAll("\'", "");
				parts[0].replaceAll("\"", "");
				sNomPremierOperateur = parts[0];
				bEstChaine = true;
			}
			else{
				sNomPremierOperateur = parts[0];
				bEstChaine = false;
			}
			
			if(getVariable(sNomPremierOperateur.trim()) == null || bEstChaine){
				sValeurPremierOperateur = sNomPremierOperateur;
			}
			else{
				sValeurPremierOperateur = getVariable(sNomPremierOperateur.trim()).getValeur();
			}

			// SECOND OPERATEUR //

			if     (ligne.contains("alors")){ parts = sDroite.split("alors"); } // AJOUTER REGEX SI POSSIBLE ICI POUR DETECTER TQ SI ... OU LIMITER SPLIT A 2 //
			else if(ligne.contains("faire")){ parts = sDroite.split("faire"); }

			sNomSecondOperateur = parts[0];

			if(sNomSecondOperateur.contains("\'") || sNomSecondOperateur.contains("\"")){
				parts[0].replaceAll("\'", "");
				parts[0].replaceAll(Pattern.quote("\""), "");
				sNomSecondOperateur = parts[0].replaceAll("\'", "");
				bEstChaine = true;
			}
			else{
				sNomSecondOperateur = parts[0];
				bEstChaine = false;
			}

			if(getVariable(parts[0]) == null || bEstChaine){
				sValeurSecondOperateur = sNomSecondOperateur;
			}
			else{
				sValeurSecondOperateur = getVariable(sNomSecondOperateur).getValeur();
			}

			System.out.println(sValeurPremierOperateur + " | " + sValeurSecondOperateur);

			return !(sValeurPremierOperateur.equals(sValeurSecondOperateur.trim()));

		}

		// *** >= *** //

		else if(ligne.contains(">=")){
			ligne.replaceAll(" ", "");

			parts = ligne.split("");
			if     (ligne.contains("tq")){ parts = ligne.split("tq"); } // AJOUTER REGEX SI POSSIBLE ICI POUR DETECTER TQ SI ... OU LIMITER SPLIT A 2 //
			else if(ligne.contains("si")){ parts = ligne.split("si"); } // AJOUTER REGEX SI POSSIBLE ICI POUR DETECTER TQ SI ... OU LIMITER SPLIT A 2//
			parts = parts[1].split(Pattern.quote(">="));
			sDroite = parts[1];

			// PREMIER OPERATEUR //

			sNomPremierOperateur = parts[0].trim();

			if(sNomPremierOperateur.contains("\'") || sNomPremierOperateur.contains("\"")){
				parts[0].replaceAll("\'", "");
				parts[0].replaceAll("\"", "");
				sNomPremierOperateur = parts[0];
				bEstChaine = true;
			}
			else{
				sNomPremierOperateur = parts[0];
				bEstChaine = false;
			}

			if(getVariable(parts[0]) == null || bEstChaine){
				sValeurPremierOperateur = sNomPremierOperateur;
			}
			else{
				sValeurPremierOperateur = getVariable(sNomPremierOperateur).getValeur();
			}

			// SECOND OPERATEUR //

			if     (ligne.contains("alors")){ parts = sDroite.split("alors"); } // AJOUTER REGEX SI POSSIBLE ICI POUR DETECTER TQ SI ... OU LIMITER SPLIT A 2 //
			else if(ligne.contains("faire")){ parts = sDroite.split("faire"); }

			sNomSecondOperateur = parts[0].trim();

			if(sNomSecondOperateur.contains("\'") || sNomSecondOperateur.contains("\"")){
				parts[0].replaceAll("\'", "");
				parts[0].replaceAll("\"", "");
				sNomSecondOperateur = parts[0];
				bEstChaine = true;
			}
			else{
				sNomSecondOperateur = parts[0];
				bEstChaine = false;
			}

			if(getVariable(parts[0].trim()) == null || bEstChaine){
				sValeurSecondOperateur = sNomSecondOperateur;
			}
			else{
				sValeurSecondOperateur = getVariable(sNomSecondOperateur.trim()).getValeur();
			}

			sValeurPremierOperateur = sValeurPremierOperateur.trim();
			sValeurSecondOperateur  = sValeurSecondOperateur.trim();

			return (Integer.parseInt(sValeurPremierOperateur) >= Integer.parseInt(sValeurSecondOperateur));

		}
		else if(ligne.contains("<=")){
			ligne.replaceAll(" ", "");

			parts = ligne.split("");
			if     (ligne.contains("tq")){ parts = ligne.split("tq"); } // AJOUTER REGEX SI POSSIBLE ICI POUR DETECTER TQ SI ... OU LIMITER SPLIT A 2 //
			else if(ligne.contains("si")){ parts = ligne.split("si"); } // AJOUTER REGEX SI POSSIBLE ICI POUR DETECTER TQ SI ... OU LIMITER SPLIT A 2//
			parts = parts[1].split(Pattern.quote("<="));
			sDroite = parts[1];

			// PREMIER OPERATEUR //

			sNomPremierOperateur = parts[0];

			if(sNomPremierOperateur.contains("\'") || sNomPremierOperateur.contains("\"")){
				parts[0].replaceAll("\'", "");
				parts[0].replaceAll("\"", "");
				sNomPremierOperateur = parts[0];
				bEstChaine = true;
			}
			else{
				sNomPremierOperateur = parts[0];
				bEstChaine = false;
			}

			if(getVariable(parts[0].trim()) == null || bEstChaine){
				sValeurPremierOperateur = sNomPremierOperateur.trim();
			}
			else{
				sValeurPremierOperateur = getVariable(sNomPremierOperateur.trim()).getValeur();
			}

			// SECOND OPERATEUR //

			if     (ligne.contains("alors")){ parts = sDroite.split("alors"); } // AJOUTER REGEX SI POSSIBLE ICI POUR DETECTER TQ SI ... OU LIMITER SPLIT A 2 //
			else if(ligne.contains("faire")){ parts = sDroite.split("faire"); }

			sNomSecondOperateur = parts[0];

			if(sNomSecondOperateur.contains("\'") || sNomSecondOperateur.contains("\"")){
				parts[0].replaceAll("\'", "");
				parts[0].replaceAll("\"", "");
				sNomSecondOperateur = parts[0];
				bEstChaine = true;
			}
			else{
				sNomSecondOperateur = parts[0];
				bEstChaine = false;
			}

			if(getVariable(parts[0].trim()) == null || bEstChaine){
				sValeurSecondOperateur = sNomSecondOperateur;
			}
			else{
				sValeurSecondOperateur = getVariable(sNomSecondOperateur.trim()).getValeur();
			}

			sValeurPremierOperateur = sValeurPremierOperateur.trim();
			sValeurSecondOperateur  = sValeurSecondOperateur.trim();

			return (Integer.parseInt(sValeurPremierOperateur.trim()) <= Integer.parseInt(sValeurSecondOperateur.trim()));

		}
		else if(ligne.contains("< ")){
			ligne.replaceAll(" ", "");

			parts = ligne.split("");
			if     (ligne.contains("tq")){ parts = ligne.split("tq"); } // AJOUTER REGEX SI POSSIBLE ICI POUR DETECTER TQ SI ... OU LIMITER SPLIT A 2 //
			else if(ligne.contains("si")){ parts = ligne.split("si"); } // AJOUTER REGEX SI POSSIBLE ICI POUR DETECTER TQ SI ... OU LIMITER SPLIT A 2//
			parts = parts[1].split(Pattern.quote("<"));
			sDroite = parts[1];

			// PREMIER OPERATEUR //

			sNomPremierOperateur = parts[0];

			if(sNomPremierOperateur.contains("\'") || sNomPremierOperateur.contains("\"")){
				parts[0].replaceAll("\'", "");
				parts[0].replaceAll("\"", "");
				sNomPremierOperateur = parts[0];
				bEstChaine = true;
			}
			else{
				sNomPremierOperateur = parts[0];
				bEstChaine = false;
			}

			if(getVariable(parts[0].trim()) == null || bEstChaine){
				sValeurPremierOperateur = sNomPremierOperateur;
			}
			else{
				sValeurPremierOperateur = getVariable(sNomPremierOperateur.trim()).getValeur();
			}

			// SECOND OPERATEUR //

			if     (ligne.contains("alors")){ parts = sDroite.split("alors"); } // AJOUTER REGEX SI POSSIBLE ICI POUR DETECTER TQ SI ... OU LIMITER SPLIT A 2 //
			else if(ligne.contains("faire")){ parts = sDroite.split("faire"); }

			sNomSecondOperateur = parts[0];

			if(sNomSecondOperateur.contains("\'") || sNomSecondOperateur.contains("\"")){
				parts[0].replaceAll("\'", "");
				parts[0].replaceAll("\"", "");
				sNomSecondOperateur = parts[0];
				bEstChaine = true;
			}
			else{
				sNomSecondOperateur = parts[0];
				bEstChaine = false;
			}

			if(getVariable(parts[0].trim()) == null || bEstChaine){
				sValeurSecondOperateur = sNomSecondOperateur;
			}
			else{
				sValeurSecondOperateur = getVariable(sNomSecondOperateur.trim()).getValeur();
			}

			sValeurPremierOperateur = sValeurPremierOperateur.trim();
			sValeurSecondOperateur  = sValeurSecondOperateur.trim();

			return (Integer.parseInt(sValeurPremierOperateur.trim()) < Integer.parseInt(sValeurSecondOperateur.trim()));
			
		}
		else if(ligne.contains("> ")){
			ligne.replaceAll(" ", "");

			parts = ligne.split("");
			if     (ligne.contains("tq")){ parts = ligne.split("tq"); } // AJOUTER REGEX SI POSSIBLE ICI POUR DETECTER TQ SI ... OU LIMITER SPLIT A 2 //
			else if(ligne.contains("si")){ parts = ligne.split("si"); } // AJOUTER REGEX SI POSSIBLE ICI POUR DETECTER TQ SI ... OU LIMITER SPLIT A 2//
			parts = parts[1].split(Pattern.quote(">"));
			sDroite = parts[1];

			// PREMIER OPERATEUR //

			sNomPremierOperateur = parts[0];

			if(sNomPremierOperateur.contains("\'") || sNomPremierOperateur.contains("\"")){
				parts[0].replaceAll("\'", "");
				parts[0].replaceAll("\"", "");
				sNomPremierOperateur = parts[0];
				bEstChaine = true;
			}
			else{
				sNomPremierOperateur = parts[0];
				bEstChaine = false;
			}

			if(getVariable(parts[0].trim()) == null || bEstChaine){
				sValeurPremierOperateur = sNomPremierOperateur;
			}
			else{
				sValeurPremierOperateur = getVariable(sNomPremierOperateur.trim()).getValeur();
			}

			// SECOND OPERATEUR //

			if     (ligne.contains("alors")){ parts = sDroite.split("alors"); } // AJOUTER REGEX SI POSSIBLE ICI POUR DETECTER TQ SI ... OU LIMITER SPLIT A 2 //
			else if(ligne.contains("faire")){ parts = sDroite.split("faire"); }

			sNomSecondOperateur = parts[0];

			if(sNomSecondOperateur.contains("\'") || sNomSecondOperateur.contains("\"")){
				parts[0].replaceAll("\'", "");
				parts[0].replaceAll("\"", "");
				sNomSecondOperateur = parts[0];
				bEstChaine = true;
			}
			else{
				sNomSecondOperateur = parts[0];
				bEstChaine = false;
			}

			if(getVariable(parts[0].trim()) == null || bEstChaine){
				sValeurSecondOperateur = sNomSecondOperateur;
			}
			else{
				sValeurSecondOperateur = getVariable(sNomSecondOperateur.trim()).getValeur();
			}

			sValeurPremierOperateur = sValeurPremierOperateur.trim();
			sValeurSecondOperateur  = sValeurSecondOperateur.trim();

			return (Integer.parseInt(sValeurPremierOperateur.trim()) > Integer.parseInt(sValeurSecondOperateur.trim()));

		}
		return false;
	}


	public String evaluerExpression(String expression){
        expression = expression.replaceAll(" ", "");
        String parts[];

        if(expression.contains("×")){
            parts = expression.split(Pattern.quote("×"));
            for(Variable var : alVariables){
                if(var.getNom().equals(parts[0])){
                    parts[0] = var.getValeur();
                }
                if(var.getNom().equals(parts[1])){
                    parts[1] = var.getValeur();
                }
            }
            return String.valueOf(Integer.parseInt(parts[0]) * Integer.parseInt(parts[1]));
        }
        else if(expression.contains("/")){
            parts = expression.split(Pattern.quote("/"));
            for(Variable var : alVariables){
                if(var.getNom().equals(parts[0])){
                    parts[0] = var.getValeur();
                }
                if(var.getNom().equals(parts[1])){
                    parts[1] = var.getValeur();
                }
            }
            return String.valueOf(Integer.parseInt(parts[0]) / Integer.parseInt(parts[1]));
        }
        else if(expression.contains("-")){
            parts = expression.split(Pattern.quote("-"));
            for(Variable var : alVariables){
                if(var.getNom().equals(parts[0])){
                    parts[0] = var.getValeur();
                }
                if(var.getNom().equals(parts[1])){
                    parts[1] = var.getValeur();
                }
            }
            return String.valueOf(Integer.parseInt(parts[0]) - Integer.parseInt(parts[1]));
        }
        else if(expression.contains("+")){
            parts = expression.split(Pattern.quote("+"));
            for(Variable var : alVariables){
                if(var.getNom().equals(parts[0])){
                    parts[0] = var.getValeur();
                }
                if(var.getNom().equals(parts[1])){
                    parts[1] = var.getValeur();
                }
            }
            return String.valueOf(Integer.parseInt(parts[0]) + Integer.parseInt(parts[1]));
        }
        else{
            return "Erreur Expression";
        }
    }

	public String getNom(){ return this.sNom; }
	public ArrayList<String> getVarTracer(){ return this.alVarsTracer; }
	public ArrayList<Variable> getVariables(){ return this.alVariables; }
	public ArrayList<String> getAlConsole() { return this.alConsole;}
}
