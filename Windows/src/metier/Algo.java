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
	private String     sNom;
	private boolean    f = true;
	private boolean    boolTmp = true;
	private int        indexFtq = 0;
	private int        indexTq = 0;
	private int        indexFsi = 0;
	private int        indexSi = 0;
	private int        indexSinon = -1;

	private ArrayList<String>   alLignes     = new ArrayList<String>();
	public  ArrayList<Variable> alVariables  = new ArrayList<Variable>();
	public  ArrayList<String>   alVarsTracer = new ArrayList<String>();
	private ArrayList<String>   alConsole    = new ArrayList<String>();

	public Algo(Controleur ctrl, ArrayList<String> alLignes, ArrayList<String> alVarsTracer)
	{
		this.ctrl         = ctrl;
		this.alLignes     = alLignes;
		this.alVarsTracer = alVarsTracer;

		setNom();
	}

	// *** LANCE LE DEBUT DE L'INTERPRETATION EN MODE AUTOMATIQUE *** //
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

	// *** LANCE LE DEBUT DE L'INTERPRETATION EN MODE PAS A PAS *** //
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

	// *** PERMET DE RECUPERER LES VARIABLES ET CONSTANTES DE L'ALGO *** //
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

	// *** AJOUTE UNE LIGNE A L'ARRAYLIST DES LIGNES DU FICHIER ALGO *** //
	public void ajouterLigne(String ligne){ alLignes.add(ligne); }

	// *** DONNE LE NOM PRESENT APRES ALGORITHME *** //
	public void setNom()
	{
		String[] parts;
		parts = alLignes.get(0).split("ALGORITHME");
		this.sNom = parts[1].trim();
	}

	// *** RECUPERE L'OBJET VARIABLE DONT LE NOM EST PASSE EN PARAMETRE *** //
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

	// *** EXECUTE L'INSTRUCTION DE LA LIGNE *** //
	public void executeInstruction(String sLigne)
	{
		String[] parts;
		ArrayList<String> alLigneFichier = ctrl.vue.getAlCopie(); // copie du fichier actuel

		if(f)
		{
			for(int i = 0; i < alLigneFichier.size(); i++)
			{
				// PREND LE NUMERO DE LIGNE DU TQ //
				if(alLigneFichier.get(i).length() > 4 && alLigneFichier.get(i).replaceAll(" ", "").substring(0, 4).contains("tq"))
				{	
					if(alLigneFichier.get(i).replaceAll(" ", "").substring(2,4).equals("tq"))
						indexTq = Integer.parseInt(alLigneFichier.get(i).replaceAll(" ", "").substring(0,2));
				}
				// PREND LE NUMERO DE LIGNE DU FTQ //
				if(alLigneFichier.get(i).contains("ftq"))
					indexFtq = Integer.parseInt(alLigneFichier.get(i).trim().substring(0,2));
				// PREND LE NUMERO DE LIGNE DU SI //
				if(alLigneFichier.get(i).contains("si "))
					indexSi = Integer.parseInt(alLigneFichier.get(i).trim().substring(0,2));
				// PREND LE NUMERO DE LIGNE DU FSI //
				if(alLigneFichier.get(i).contains("fsi"))
					indexFsi = Integer.parseInt(alLigneFichier.get(i).trim().substring(0,2));
				// PREND LE NUMERO DE LIGNE DU SINON //
				if(alLigneFichier.get(i).contains("sinon"))
					indexSinon = Integer.parseInt(alLigneFichier.get(i).substring(0, 2).trim());
			}
		}
		f = false;

		if(sLigne.length() >= 2 && !sLigne.contains("sinon") && !sLigne.contains("ftq"))
		{
			if (sLigne.trim().substring(0, 2).equals("tq"))
			{
				if(sLigne.contains("/="))
				{
					// VERIFICATION DE LA VALIDITE DE L'INSTRUCTION DU TQ //
					if(!evaluerBooleen(sLigne))
						ctrl.vue.setCompteur(indexFtq-1); // deplacement au ftq
				}
				else 
				{
					// VERIFICATION DE LA VALIDITE DE L'INSTRUCTION DU TQ //
					if(evaluerBooleen(sLigne))
						ctrl.vue.setCompteur(indexFtq-1); // deplacement au ftq
				}
			}

			if(sLigne.trim().substring(0, 2).equals("si"))
			{
				if(sLigne.contains("/="))
				{
					// VERIFICATION DE LA VALIDITE DE L'INSTRUCTION DU SI //
					boolTmp = !evaluerBooleen(sLigne);
					if(evaluerBooleen(sLigne))
					{
						if(indexSinon != -1)
							ctrl.vue.setCompteur(indexSinon-1); // deplacement au fsi
					}
				}
				else 
				{
					// VERIFICATION DE LA VALIDITE DE L'INSTRUCTION DU SI //
					boolTmp = evaluerBooleen(sLigne);
					if(!evaluerBooleen(sLigne))
					{
						if(indexSinon != -1)
							ctrl.vue.setCompteur(indexSinon-1); // deplacement au fsi
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
			String ligneTq = alLigneFichier.get(indexTq);

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
		// AFFECTATION //
		if (sLigne.contains(""))
		if(sLigne.contains("<--"))
		{
			parts = sLigne.split("<--");
			if(parts[1].contains(" + ") || parts[1].contains(" - ") || parts[1].contains(" × ") || parts[1].contains(" / "))
				getVariable(parts[0].trim()).setValeur(evaluersExpression(parts[1]));
			else
				getVariable(parts[0].trim()).setValeur(parts[1].replaceAll(" ", ""));
		}
		// ECRIRE //
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
		// LIRE //
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

	// *** PERMET DE RETOURNER UN BOOLEEN EN FONCTION DE LA LIGNE DONNEE EN PARAMETRE *** //
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
			if     (ligne.contains("tq")){ parts = ligne.split("tq"); }
			else if(ligne.contains("si")){ parts = ligne.split("si"); }
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

			if     (ligne.contains("alors")){ parts = sDroite.split("alors"); }
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
			if     (ligne.contains("tq")){ parts = ligne.split("tq"); }
			else if(ligne.contains("si")){ parts = ligne.split("si"); }
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

			if     (ligne.contains("alors")){ parts = sDroite.split("alors"); }
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
			if     (ligne.contains("tq")){ parts = ligne.split("tq"); }
			else if(ligne.contains("si")){ parts = ligne.split("si"); }
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

			if     (ligne.contains("alors")){ parts = sDroite.split("alors"); }
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
			if     (ligne.contains("tq")){ parts = ligne.split("tq"); }
			else if(ligne.contains("si")){ parts = ligne.split("si"); }
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

			if     (ligne.contains("alors")){ parts = sDroite.split("alors"); }
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

			return !(Integer.parseInt(sValeurPremierOperateur.trim()) <= Integer.parseInt(sValeurSecondOperateur.trim()));

		}
		else if(ligne.contains("< ")){
			ligne.replaceAll(" ", "");

			parts = ligne.split("");
			if     (ligne.contains("tq")){ parts = ligne.split("tq"); }
			else if(ligne.contains("si")){ parts = ligne.split("si"); }
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

			if     (ligne.contains("alors")){ parts = sDroite.split("alors"); }
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

			return !(Integer.parseInt(sValeurPremierOperateur.trim()) < Integer.parseInt(sValeurSecondOperateur.trim()));
			
		}
		else if(ligne.contains("> ")){
			ligne.replaceAll(" ", "");

			parts = ligne.split("");
			if     (ligne.contains("tq")){ parts = ligne.split("tq"); }
			else if(ligne.contains("si")){ parts = ligne.split("si"); }
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

			if     (ligne.contains("alors")){ parts = sDroite.split("alors"); }
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

	// *** EVALUE UNE sEXPRESSION MATHEMATIQUE *** //
	public String evaluersExpression(String sExpression){
        sExpression = sExpression.replaceAll(" ", "");
        String parts[];

		// * //
        if(sExpression.contains("×")){
            parts = sExpression.split(Pattern.quote("×"));
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
		// / //
        else if(sExpression.contains("/")){
            parts = sExpression.split(Pattern.quote("/"));
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
		// - //
        else if(sExpression.contains("-")){
            parts = sExpression.split(Pattern.quote("-"));
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
		// + //
        else if(sExpression.contains("+")){
            parts = sExpression.split(Pattern.quote("+"));
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
            return "Erreur sExpression";
        }
    }

	public String getNom(){ return this.sNom; }
	public ArrayList<String> getVarTracer(){ return this.alVarsTracer; }
	public ArrayList<Variable> getVariables(){ return this.alVariables; }
	public ArrayList<String> getAlConsole() { return this.alConsole;}
}
