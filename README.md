
# Projet SAE: Search Engine

https://docs.python.org/3/library/pathlib.html
## projet incluant plusieurs language :
    1. Java (Majoritaire)
    2. HTML5
    3. CSS
    4. Python 3
    5. JavaScript 

Le but de ce projet est de construire un moteur de recherche en Java, utilisable en ligne de commande.
À terme, ce moteur devra délivrer des résultats pertinents en un temps raisonnable sur un site web de taille relativement important (10 000 à 50 000 pages).
Le contenu francophone de Wikipédia étant trop vaste pour ce projet, nous nous rabattrons sur Vikidia, qui est une version de Wikipedia destinée aux enfants. Il contient environ 40 000 articles relativement courts, ce qui est un objectif raisonnable.

## Indexation à Froid

Pour l'instant, vous avez utilisé des fichiers d'index déjà fournis.
Autrement dit, vous n'avez fait que la 2e moitié du travail : exploiter les résultats de l'indexation pour fournir des résultats en temps réel.
Si vous voulez pouvoir effectuer des recherches sur un autre site que Vikidia.fr, il vous faut donc maîtriser aussi la phase amont d'indexation.
Cela suppose de savoir récupérer une page web d'un site (effectuer une requête GET)
de parser la page pour récupérer le texte de la page (sans les balises notamment)
de lemmatiser son contenu et de générer les fichiers d'index
Dans un premier temps, vous pouvez vous contenter de fournir manuellement une liste d'URL de pages à indexer.
Dans un second temps, l'objectif serait d'arriver à parcourir le site en suivant les liens hypertextes des pages pour construire la liste de toutes les URL du site, et ne pas avoir à les fournir manuellement. Attention à ne pas tourner en rond !

## Autocorrection

Il est fréquent de taper un mot incorrectement, soit par inadvertance, soit par ce qu'on ne connaît pas son orthographe.

Non corrigées, ces erreurs vont évidemment fortement impacter le nombre et la qualités des résultats de la recherche.

Plusieurs approches peuvent être utilisées pour renvoyer malgré tout des résultats pertinents.

On peut utiliser un dictionnaire des erreurs les plus prévisibles, notamment concernant les fautes d'orthographe. Par exemple, "parmis" -> "parmi", ou "emener" -> "emmener".
On peut aussi chercher les mots existants les plus proches.
On peut utiliser des approches probabilistes (IA), mais cela nécessite l'analyse de très grandes quantités de données (phase d'apprentissage).
La 2e approche est la plus pertinente en ce qui nous concerne.
Voici quelques pistes si vous souhaitez vous pencher sur la question :
La distance de Levenshtein permet de mesurer la distance entre deux mots, mais elle est assez coûteuse à calculer.
On peut commencer par faire une préselection de mots assez proches avec une distance rapide à calculer, par exemple la distance de Jaccard, et calculer la distance de Lebenshtein seulement sur les mots sélectionnés pour affiner la correction.

## Comment installer le serveur 
Visualiser le dossier, et localiser le fichier requirement.txt
celui-ci va installer les librairies nécessaire pour l'éxecution du script sans soucis.

```bash
 pip install -r requirements.txt

```
## 


## Authors

- [@ckizp](https://github.com/ckizp) - Chef du Projet + Développeur

- [@BresThomas](https://github.com/BresThomas/) - Développeur

- [@akirasanthakumaran](https://github.com/Akira98000/) - Développeur

- [@PhanSayam](https://github.com/PhanSayam) - Développeur

- [@Ti21l](https://github.com/Ti21l) - Développeur



## Badges  & License


[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)


## Screenshots - Interface Web
![site](https://user-images.githubusercontent.com/75495075/228978886-56179410-5b99-4cac-a7a9-df780ec870cc.PNG)

