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

		//  val, nbAction : entier
		String   ligne = "";
		String[] parts;
		for(int i = 0; i<alLignes.size() ;i++){
			ligne = alLignes.get(i);
			if(ligne.contains("variable")){
				i++;
				ligne = alLignes.get(i);
				while(!ligne.contains("DEBUT") && !ligne.contains("constante")){
					parts = ligne.split(":");
					this.ajouterVariable(String.valueOf(parts[0]), String.valueOf(parts[1]));
					i++;
					ligne = alLignes.get(i);
				}
			}
		}
	}

	public void ajouterVariable(String sNom, String sType)
	{
		alVariables.add(new Variable(sNom, sType));
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
