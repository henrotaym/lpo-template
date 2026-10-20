# 01.01 — La caisse de la friterie

🟢 Révision · ~15 min · Crash course : [§2 Variables et types](../../01-CRASH-COURSE.md#2-variables-et-types), [§3 Opérateurs](../../01-CRASH-COURSE.md#3-opérateurs-et-pièges-numériques)

## Contexte

Une friterie veut remplacer son carnet par un petit programme de caisse.
Le patron est formel : **jamais un centime d'écart**.

Tarif de la maison :

| Article          | Prix   |
| ---------------- | ------ |
| Cornet de frites | 3,50 € |
| Sauce            | 0,80 € |
| Boisson          | 2,50 € |

## Consigne

Écris un programme `Main.java` qui traite **une commande de 2 frites, 3 sauces et 1 boisson**,
payée avec un billet de 20 €.

Il doit afficher :

1. le récapitulatif de la commande,
2. le sous-total **en centimes**,
3. le même sous-total **formaté en euros**, sous la forme `11,90 €`,
4. le montant payé, formaté,
5. la monnaie à rendre, formatée,
6. combien de **pièces de 2 €** entrent dans cette monnaie, et ce qu'il reste,
7. le prix des 3 sauces calculé de deux façons : en centimes (`int`) et en euros (`double`).

## Contrainte imposée

**Tous les montants sont stockés en centimes, dans des `int`.**
Les prix du tarif sont des constantes `final`.
Aucun `double` n'intervient dans un calcul de caisse — le point 7 est là uniquement pour te montrer
pourquoi.

Écris une méthode `formatEuros(int amountInCents)` qui retourne la chaîne formatée,
et appelle-la partout où tu affiches un montant.

## Sortie attendue

```
Commande   : 2 frites, 3 sauces, 1 boisson
Sous-total : 1190 centimes
Sous-total : 11,90 €
Payé       : 20,00 €
Monnaie    : 8,10 €
Rendu      : 4 pièces de 2 €, reste 0,10 €
3 sauces en centimes : 240
3 sauces en euros    : 2.4000000000000004
```

Oui, la dernière ligne est bien le résultat que Java produit. Ce n'est pas une faute de frappe,
et c'est tout l'intérêt de l'exercice.

## Pour aller plus loin

- Ajoute une réduction de 10 % sur le sous-total. Où places-tu l'arrondi, et dans quel sens
  arrondis-tu ? Qui gagne le demi-centime, le client ou la friterie ?
- Rends la monnaie en détaillant toutes les pièces : 2 €, 1 €, 50 c, 20 c, 10 c.
