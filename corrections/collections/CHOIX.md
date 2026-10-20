# Correction : quelle collection, et pourquoi

## La règle en une question

| Je me demande... | Collection | Variante quand l'ordre compte |
| - | - | - |
| Dans quel ordre ? Combien de fois ? | `List` (`ArrayList`) | l'ordre d'ajout est déjà gardé |
| Est-ce présent ? Sans doublon ? | `Set` (`HashSet`) | `LinkedHashSet` (ordre d'arrivée), `TreeSet` (trié) |
| Pour cette clé, quelle valeur ? | `Map` (`HashMap`) | `LinkedHashMap` (ordre d'ajout), `TreeMap` (trié par clé) |

`HashSet` et `HashMap` ne promettent aucun ordre d'affichage. Dès que la sortie doit
être stable, on prend la variante `Linked...` ou `Tree...`.

## Partie 1 : pourquoi une List

- L'historique doit garder l'ordre : seule une `List` sait répondre « premier » et
  « dernier ».
- Dune apparaît deux fois, et c'est voulu. Un `Set` aurait gardé un seul Dune :
  l'historique dirait 3 emprunts au lieu de 4.
- `getTitles()` renvoie `List.copyOf(...)` : l'appelant reçoit une copie figée et ne
  peut pas modifier l'historique.
- `countByTitle()` passe à une `TreeMap` dès que la question devient « pour ce titre,
  combien ? ». Le tri par titre vient du `Tree`.

## Partie 2 : pourquoi un Set

- La question est « qui est venu ? », donc chaque badge doit compter une seule fois.
  `Set.add` renvoie `false` si le badge est déjà là : la réponse « déjà compté » est
  gratuite.
- Avec une `List` seule, on obtiendrait 6 visiteurs au lieu de 3. C'est pourquoi la
  classe garde aussi une `List` : les passages et les visiteurs sont deux questions
  différentes.
- `LinkedHashSet` plutôt que `HashSet`, parce que l'énoncé demande l'ordre de
  première arrivée.
- Les membres sont dans une `Map<Integer, String>` : on retrouve un nom par son badge
  en une seule instruction, `getOrDefault`, sans boucle sur tous les membres.

## Partie 3 : pourquoi une Map

- Chaque titre a un nombre : c'est une association clé → valeur.
- Avec une `List` de titres et une `List` de nombres, il faudrait chercher la position
  du titre dans l'une pour lire l'autre, et les garder synchronisées à la main.
- `getOrDefault(title, 0)` règle le cas du titre inconnu (Alien) sans `if`.
- `TreeMap` trie par clé : le stock s'affiche par titre, alors que Le Hobbit a été
  ajouté en premier.

## Partie 4 : réponses

1. **File d'attente des réservations** : `List`. L'ordre est la question, et on sert
   le premier.
2. **Genres d'un livre** : `Set`. Un genre est présent ou non, jamais deux fois.
   `TreeSet` pour un affichage trié (voir `BookGenres`).
3. **Livre par ISBN** : `Map<Isbn, Book>`. On accède par une clé.
4. **10 derniers titres consultés** : `List`. L'ordre compte et les doublons aussi.
5. **Mots-clés d'une recherche** : `LinkedHashSet`. Sans doublon, et dans l'ordre de
   saisie.
6. **Emprunts par membre, triés par nom** : `TreeMap<String, Integer>`. Un nombre par
   membre, trié par clé (voir `LoansByMember`).

Partie 4b (les genres d'un livre, les emprunts par membre) : `java Choices`.

```
Genre roman : ajouté
Genre policier : ajouté
Genre roman : déjà présent
Genre jeunesse : ajouté
Genres : [jeunesse, policier, roman]
Emprunts par membre : {Alice=3, Bruno=1, Chloé=2}
```
