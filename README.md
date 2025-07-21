# AlgoPars

Utiliser par les professeurs et destiner pour les élèves AlgoPars est un outil permettant de mieux comprendre la logique de la programmation à travers l'exécution d'un algorithme écrit en pseudo-code. 
Ce logiciel permettra de visualiser le déroulement d'un algorithme afin d’expliquer les différentes notions aux élèves de première année de DUT informatique.

**Installation** 
Pour utiliser le logiciel, veuillez extraire le contenu de l'archive **AlgoPars.zip** 
Une fois extrait vous trouverez différents répertoire :

\AlgoPars <= Répertoire racine
	\lib <= Fichier.jar necessaire au path 
	\LinuxMac <= Application version Linux et MacOS => Même contenu dans \Windows 
	\Windows <= Application version Windows => Même contenu dans \LinuxMac 
		\src <= Répertoire du code Java 
		\bin <= Fichier.class lors de la compilation Java 
			\ihm <= Fichier.java necessaire à l'affichage 
			\metier <= Fichier.java necessaire au fonctionnement
			\fichiers <= Fichier.algo et Fichier.var permettant de crée les algorithmes peuvent être ajoutés pour l'utilisateur et Primordiale pour le fonctionnement de l'application.

Dans le lib comme écrit ci-dessus, il faut en plus de se munir des fichiers .jar à l'intérieur, il faut les ajouter à son CLASSPATH Windows, Linux ou MacOS, car notre application utilise en grande majorité ces différentes librairies, il faut donc donner au système la possibilité de les utiliser. Pour plus de détails sur comment ajouter des librairies à son système allé sur ce lien et suivez la démarche proposée : https://di.iut.univ-lehavre.fr/pedago/info1/R1_01_Init_Dev/ressources/java_installation.xml et suivre la démarche "Classe Clavier et autres classes utiles, CLASSPATH"

Une fois cela fait, tout est prêt ne touchez pas les Fichiers .java, vous pouvez cependant modifier les fichiers .var et .algo en respectant une démarche précise expliquer dans notre document Utilisateur ou une version résumé un peu plus tard dans ce README.

# Windows
**Lancement** 
Si vous êtes sur Windows, pour lancer l'application, il vous suffit de double-clic sur le fichier runWindows.bat voici son répertoire : ./AlgoPars/Windows/runWindows.bat
Le programme se lance normalement. 

**Utilisation**

Afin de faire fonctionner le programme et ne pas avoir de bug, vous devrez suivre une préparation afin d'éviter de faire planter l'application. En plus des programmes déjà présent pour faire des tests et montrer le fonctionnement, vous pouvez ajouter vos propres programmes pour utiliser l'application de votre façon. Vous pouvez si vous le souhaitez supprimer les programmes déjà présents.
Cependant, pour créer votre nouveau fichier, il est important de prendre comme base les fichiers déjà existants. Notre application utilise des fichiers respectant une écriture spécifique d'un algorithme et suit une documentation précise de chaque ligne voir Fichier.algo déjà présent dans le dossier.

# Linux et MacOS
**Lancement** 
Si vous êtes sur Linux ou MacOS, il faudra autoriser l'exécution du fichier runLinux.sh voici son répertoire : ./AlgoPars/LinuxMac/runLinux.sh. Pour cela, il vous faut ouvrir un nouveau terminale.
Déplacer vous à l'endroit du runLinux.sh à l'aide de la commande cd "cheminVersRunLinux". Une fois à l'intérieur du dossier "LinuxMac" avec votre terminale faites chmod 777 runLinux.sh cela va vous permettre par la suite de pouvoir lancer notre application dans ce même terminal.
Le programme se lance alors normalement.

**Utilisation**

Afin de faire fonctionner le programme et ne pas avoir de bug, vous devrez suivre une préparation afin d'éviter de faire planter l'application. En plus des programmes déjà présent pour faire des tests et montrer le fonctionnement, vous pouvez ajouter vos propres programmes pour utiliser l'application de votre façon. Vous pouvez si vous le souhaitez supprimer les programmes déjà présents.
Cependant, pour créer votre nouveau fichier, il est important de prendre comme base les fichiers déjà existants. Notre application utilise des fichiers respectant une écriture spécifique d'un algorithme et suit une documentation précise de chaque ligne voir Fichier.algo déjà présent dans le dossier.

## Licence & copyright

© Alexandre Hacher, Logan Coruble, Marvin Clapson, Alexandre Lemetteil