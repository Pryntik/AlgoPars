package metier;

import java.util.ArrayList;

public class Algo
{

	private String sNom;
	private ArrayList<String> alLignes = new ArrayList<String>();
	private ArrayList<Variable> alVariables = new ArrayList<Variable>();

	public Algo(ArrayList<String> alLignes)
	{
		this.alLignes = alLignes;
		setNom();

		// *** RECUPERATION DES VARIABLES ET CONSTANTES *** //
		String   ligne = "";
		String[] parts;
		String   type;

		// Constantes //
		for(int i = 0; i<alLignes.size(); i++){
			ligne = alLignes.get(i);
			if(ligne.contains("constante")){
				i++;
				ligne = alLignes.get(i);
				while(!ligne.contains("DEBUT") && !ligne.contains("variable")){
					parts = ligne.split(":");
					type   = String.valueOf(parts[1]);
					if(parts[0].contains(",")){
						parts  = parts[0].split(",");
						for(int j = 0; j<parts.length; j++){
							this.ajouterVariable(parts[j], type, true);
						}
					}
					else{
						this.ajouterVariable(parts[0], type, true);
					}
					i++;
					ligne = alLignes.get(i);
				}
			}

			// Variables //
			if(ligne.contains("variable")){
				i++;
				ligne = alLignes.get(i);
				while(!ligne.contains("DEBUT")){
					parts = ligne.split(":");
					type   = String.valueOf(parts[1]);
					if(parts[0].contains(",")){
						parts  = parts[0].split(",");
						for(int j = 0; j<parts.length; j++){
							this.ajouterVariable(parts[j], type, false);
						}
					}
					else{
						this.ajouterVariable(parts[0], type, false);
					}
					i++;
					ligne = alLignes.get(i);
				}
			}
		}

		// Affichage des variables gardées en mémoire //
		System.out.println("--- Variables et Constantes --- ");
		for(Variable var : alVariables){
			System.out.println(var);
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
}
