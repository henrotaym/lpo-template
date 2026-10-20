# 01.02 — La ruche connectée

🟡 Application · ~20 min · Crash course : [§2 Variables et types](../../01-CRASH-COURSE.md#2-variables-et-types), [§3 Opérateurs](../../01-CRASH-COURSE.md#3-opérateurs-et-pièges-numériques)

## Contexte

Une balance connectée sous une ruche relève son poids total chaque jour.
L'apiculteur veut savoir si la colonie prend du poids, et de combien.

Relevés de la semaine, en grammes :

| Jour     | Poids    |
| -------- | -------- |
| Lundi    | 24 500 g |
| Mardi    | 25 230 g |
| Mercredi | 26 800 g |
| Jeudi    | 27 145 g |

## Consigne

Écris un programme `Main.java` qui affiche :

1. le nombre de relevés,
2. le poids total,
3. la moyenne calculée **en division entière**,
4. la moyenne **exacte**,
5. la moyenne **arrondie à l'entier le plus proche**,
6. la **croissance en pourcentage** entre lundi et jeudi, avec **une décimale**,
7. la même croissance, calculée avec `100` au lieu de `100.0`.

## Contrainte imposée

Un relevé = une variable `int`. Pas de tableau : on n'en a pas encore parlé, et ce n'est pas le
sujet.

Les points 3 et 7 produisent des résultats **faux**. Tu dois les afficher quand même, à côté des
bons. Un piège qu'on a vu se déclencher une fois ne se redéclenche plus.

## Sortie attendue

```
Relevés          : 4
Total            : 103675 g
Moyenne entière  : 25918 g   <- 0,75 g perdus
Moyenne exacte   : 25918.75 g
Moyenne arrondie : 25919 g
Croissance       : 10.8 %
Croissance sans le .0 : 10.0 %
```

## Pour aller plus loin

- Ajoute le gain quotidien moyen en grammes par jour. Attention : entre 4 relevés, il y a
  3 intervalles, pas 4.
- Le capteur envoie parfois `0` quand il est décalibré. Que devient ta moyenne ?
  Comment l'écarter du calcul ?
