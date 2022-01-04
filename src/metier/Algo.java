package metier;

import java.util.ArrayList;

public class Algo {

	private String sNom;
	private ArrayList<String> alLignes = new ArrayList<String>();
	private ArrayList<String> alConstantes = new ArrayList<String>();

	public Algo(ArrayList<String> alLignes){

		this.alLignes = alLignes;
		setNom();

	}

	public void ajouterConstante(String sType, String sNom){

		alConstantes.add(sType + sNom);

	}

	public void ajouterLigne(String ligne){

		alLignes.add(ligne);

	}

	public String toString(){

		String sRet = "";

		for( String sLigne : alLignes){
			sRet += alLignes.indexOf(sLigne) +  "\n";
		}

		return sRet;

	}

	public void setNom(){
		String[] parts;
		parts = alLignes.get(0).split("ALGORITHME");
		this.sNom = parts[1].trim();
	}
}
