# La médiathèque : List, Set ou Map ?

## Contexte

La médiathèque veut trois outils : l'historique des emprunts d'un membre, le
comptage des visiteurs du jour et le stock des exemplaires. Chaque outil pose une
question différente, et chaque question appelle une collection différente.

Les trois parties se font dans l'ordre. Chaque outil est une classe qui garde sa
collection pour elle : le reste du programme passe par ses méthodes.

## Partie 1 : l'historique des emprunts (List)

La question : **dans quel ordre, et combien de fois ?**

- L'historique garde chaque emprunt dans l'ordre où il a eu lieu.
- Un même titre peut apparaître plusieurs fois : Alice a emprunté Dune deux fois.
- L'historique donne le nombre d'emprunts, le premier, le dernier, et combien de
  fois un titre a été emprunté.
- Il donne aussi le nombre d'emprunts par titre, trié par titre.
- On ne peut pas modifier l'historique depuis l'extérieur en passant par la liste
  qu'il renvoie.

Données : Alice emprunte, dans l'ordre, Dune, Le Hobbit, Dune, Fondation.

## Partie 2 : les visiteurs du jour (Set)

La question : **qui est venu, sans compter deux fois la même personne ?**

- À l'entrée, chaque membre scanne son badge. Un membre peut scanner plusieurs fois
  dans la journée.
- Le compteur distingue les passages scannés et les visiteurs différents.
- Scanner un badge répond tout de suite s'il s'agit d'une nouvelle entrée ou d'un
  badge déjà compté.
- On peut demander si un badge est venu aujourd'hui.
- Les visiteurs sont restitués par leur nom, dans l'ordre de leur première arrivée.
- Le nom d'un membre se retrouve à partir de son numéro de badge, sans parcourir
  tous les membres. Un badge inconnu donne « Inconnu ».

Données : les membres sont Alice (1042), Bruno (2077) et Chloé (3150). Les badges
scannés, dans l'ordre, sont 1042, 2077, 1042, 3150, 2077, 1042.

## Partie 3 : le stock (Map)

La question : **pour ce titre, combien d'exemplaires ?**

- Le stock associe à chaque titre son nombre d'exemplaires disponibles.
- Ajouter des exemplaires d'un titre déjà présent les additionne.
- Un titre inconnu a 0 exemplaire, sans erreur.
- Un emprunt est refusé quand il ne reste aucun exemplaire. Un retour rend un
  exemplaire.
- Le stock s'affiche trié par titre, quel que soit l'ordre d'ajout.

Données : on ajoute 1 Le Hobbit, 3 Dune, 2 Fondation. Puis on emprunte Dune, Le
Hobbit, Le Hobbit, on rend Fondation, et on demande le stock d'Alien.

## Sortie attendue

```
=== Historique d'Alice ===
Emprunts : [Dune, Le Hobbit, Dune, Fondation]
Nombre d'emprunts : 4
Premier : Dune
Dernier : Fondation
Dune emprunté 2 fois
Par titre : {Dune=2, Fondation=1, Le Hobbit=1}

=== Visiteurs du jour ===
Badge 1042 : entrée comptée
Badge 2077 : entrée comptée
Badge 1042 : déjà compté
Badge 3150 : entrée comptée
Badge 2077 : déjà compté
Badge 1042 : déjà compté
Passages scannés : 6
Visiteurs différents : 3
Chloé est venue : true
Le badge 9999 est venu : false
Ordre d'arrivée : [Alice, Bruno, Chloé]
Badge 4000 : Inconnu

=== Stock ===
Stock : {Dune=3, Fondation=2, Le Hobbit=1}
Emprunt de Dune : accepté
Emprunt de Le Hobbit : accepté
Emprunt de Le Hobbit : refusé, plus d'exemplaire
Retour de Fondation
Exemplaires de Alien : 0
Stock : {Dune=2, Fondation=3, Le Hobbit=0}
```

## Partie 4 : à vous de choisir

### 4a. Choisir sans coder

Pour chaque besoin, choisissez List, Set ou Map (et sa variante si l'ordre compte),
puis justifiez en une phrase.

1. La file d'attente des réservations d'un livre : premier arrivé, premier servi.
2. Les genres d'un livre (roman, policier, jeunesse), sans doublon.
3. Retrouver un livre à partir de son ISBN.
4. Les 10 derniers titres consultés sur la borne, doublons compris.
5. Les mots-clés d'une recherche, sans doublon, dans l'ordre où ils ont été tapés.
6. Le nombre d'emprunts de chaque membre, affiché par ordre alphabétique de nom.

### 4b. Coder deux nouveaux outils

**Les genres d'un livre**

- Un livre a des genres. Ajouter un genre déjà présent ne change rien, et le dit.
- Les genres s'affichent par ordre alphabétique.
- Données : on ajoute, dans l'ordre, roman, policier, roman, jeunesse.

**Les emprunts par membre**

- Chaque emprunt est enregistré au nom du membre.
- Le compteur donne le nombre d'emprunts de chaque membre, affiché par ordre
  alphabétique de nom.
- Données : les emprunts sont faits, dans l'ordre, par Chloé, Alice, Bruno, Alice,
  Chloé, Alice.

Sortie attendue de la partie 4b :

```
Genre roman : ajouté
Genre policier : ajouté
Genre roman : déjà présent
Genre jeunesse : ajouté
Genres : [jeunesse, policier, roman]
Emprunts par membre : {Alice=3, Bruno=1, Chloé=2}
```

## Critères d'acceptation

- La console affiche exactement les sorties attendues.
- Aucune classe n'expose sa collection de façon modifiable.
- Le nom d'un membre est retrouvé à partir de son badge sans boucle.
- Le nombre de visiteurs différents n'est jamais calculé en parcourant les passages.
- Chaque choix de la partie 4 est justifié par la question posée : ordre, doublons
  ou accès par clé.
