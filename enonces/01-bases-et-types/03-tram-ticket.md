# 01.03 — Le ticket de tram

🟡 Application · ~20 min · Crash course : [§2 Variables et types](../../01-CRASH-COURSE.md#2-variables-et-types), [§3 Opérateurs](../../01-CRASH-COURSE.md#3-opérateurs-et-pièges-numériques)

## Contexte

Un ticket de tram est valable **60 minutes** à partir de sa validation.
Le validateur ne connaît pas les dates : il compte en **minutes écoulées depuis minuit**.
23h40 devient donc `23 * 60 + 40`, soit `1420`.

## Consigne

Écris un programme `Main.java` qui traite une validation à **23h40** et affiche :

1. l'heure de validation, au format `HH:MM`,
2. l'heure d'expiration, au même format,
3. un booléen indiquant si la validité **franchit minuit**.

Puis, deuxième partie, la société de transport veut estimer le temps de trajet cumulé sur un an :
**380 000 trajets par jour**, **365 jours**, **60 secondes** par trajet en moyenne.

4. Affiche ce total calculé en `int`.
5. Affiche le même total calculé en `long`.
6. Affiche la capacité maximale d'un `int`.

## Contrainte imposée

Écris une méthode `formatClock(int minutesSinceMidnight)` qui retourne la chaîne `HH:MM`.
Elle doit toujours produire deux chiffres : `00:40`, jamais `0:40`.

L'heure d'expiration doit être calculée **sans aucun `if`**. Un seul opérateur suffit.

## Sortie attendue

```
Validation : 23:40
Expiration : 00:40
Passe minuit : true
Secondes de trajet par an, en int  : -267934592
Secondes de trajet par an, en long : 8322000000
Capacité maximale d'un int         : 2147483647
```

La valeur négative n'est pas un bug de ton code. C'est le comportement normal de Java,
et c'est exactement ce que l'exercice veut te faire voir.

## Pour aller plus loin

- Le contrôleur monte à `00:15`. Écris la condition qui dit si le ticket est encore valable.
  Le passage de minuit rend la comparaison moins évidente qu'il n'y paraît.
- Généralise : une validation à n'importe quelle heure, une durée de validité quelconque.
