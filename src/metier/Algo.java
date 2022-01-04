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

		// A FINIR CREER LES VARIABLES ET LES AJOUTER A L'ARRAYLIST (utiliser while pas for)//
		for(String ligne: alLignes){
			if(ligne.contains("variable")){
				while(!ligne.contains("DEBUT") && !ligne.contains("constante")){

				}
			}
		}
	}

	public void ajouterVariable(String sNom, String sValeur)
	{
		alVariables.add(new Variable(sNom, sValeur));
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
