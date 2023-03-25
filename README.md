# SearchEngine

## Update du site à 00:54 le 25/03/2023
Bienvenue sur le projet de notre site web de recherche de mots ! Ce projet vise à développer un site web interactif qui utilise un programme Java pour effectuer des recherches de mots. Nous avons choisi Java pour sa robustesse et ses capacités avancées en traitement de texte.

Nous avons hébergé notre projet sur GitHub, une plateforme populaire de gestion de code source et de collaboration entre développeurs. Le référentiel (repository) de notre projet est disponible ici, où nous stockons tous les fichiers et les codes source nécessaires pour le site web et le programme Java.

En utilisant les fonctionnalités de Git, nous avons suivi l'historique des modifications apportées à notre code et avons collaboré avec d'autres membres de l'équipe pour améliorer constamment notre site web et notre programme Java. Nous sommes impatients de partager notre travail avec le monde entier et nous espérons que vous trouverez notre projet utile et intéressant. N'hésitez pas à contribuer ou à nous contacter pour toute question ou suggestion.

Voici la preview de notre site le 25/03/2023 à 00:51 : <img width="1440" alt="Capture d’écran 2023-03-25 à 00 51 18" src="https://user-images.githubusercontent.com/75495075/227663692-fb4f20db-7c0a-416f-9499-707a1cb1b36a.png">

## Update du Site à 2:54am le 25/03/2023
Notre site web utilise Flask (Python) pour connecter le frontend et le backend. Le fichier app.py dans le dossier web_version gère cette connexion. Cependant, nous avons remarqué que la performance du site pourrait être améliorée en optimisant cette connexion.

Afin d'automatiser la gestion des URLs, nous sommes en train de développer un programme qui ouvre un fichier texte et lit les mots à l'intérieur. Le programme choisira ensuite un mot parmi ceux lus pour le remplacer dans l'URL du site web. Ce processus sera exécuté de manière aléatoire pour garantir que le mot choisi varie à chaque visiteur. Le but de cette fonctionnalité est de fournir une expérience utilisateur unique à chaque visiteur du site web.

Actuellement, la connexion entre le frontend et le backend est gérée à travers des URLs prédéfinies. Nous travaillons actuellement sur l'amélioration de cette connexion pour permettre une plus grande flexibilité dans la gestion des URLs.

Nous nous engageons à continuer à améliorer notre site web et à fournir une expérience utilisateur optimale. Si vous avez des commentaires ou des suggestions, n'hésitez pas à nous contacter.

Voici le résultat en images du console : 

![getflask](https://user-images.githubusercontent.com/75495075/227679885-ac02d114-ea94-421b-acb0-d56ba0f876c7.PNG)

Voici le résultat du site après les url prédéfini : 
![capture 2](https://user-images.githubusercontent.com/75495075/227679906-251ac202-6190-4041-858a-b333dcd03a35.PNG)

## Modification du programme Python et ajout de redirections vers Vikidia (3:50am le 25/03/2023)

J'ai modifié le programme Python app.py pour faciliter l'implémentation de nouvelles fonctionnalités. De plus, j'ai ajouté un fichier texte contenant des mots basiques de tous les jours pour rediriger les utilisateurs vers les articles de Vikidia. Pour cela, j'ai utilisé la méthode de lecture de fichier en Python pour récupérer le contenu du fichier texte. Ensuite, j'ai utilisé la fonction random pour choisir un mot au hasard parmi ceux disponibles. Enfin, j'ai affiché le contenu correspondant à ce mot sur la page HTML.

Voici les images de la version 0.1 à 4:00am le 25/03/2023: 
## Comment j'ai réaliser la boucle ( Code à l'indienne ) 
![Challenge](https://user-images.githubusercontent.com/75495075/227686711-b226389b-26ee-4224-96ec-4896bed1877c.PNG)

##Rendu Final : 4:01am 25/03/2023
![Capture3](https://user-images.githubusercontent.com/75495075/227686915-d7787161-1dab-4e8b-b950-c52f0c9006da.PNG)

