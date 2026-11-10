# La cave d'affinage : composer des comportements

## Contexte

Une fromagère d'Herve affine ses meules en cave, jour après jour. Toutes les meules
suivent les mêmes règles : elles mûrissent à un certain rythme et reçoivent un soin
chaque jour. Ce qui les distingue, ce n'est pas leur sorte, c'est la combinaison de
rythme et de soin qu'on leur donne. La fromagère peut d'ailleurs changer le soin d'une
meule en cours d'affinage.

## Toutes les meules

- Ont un nom et une maturité cible, fixés une fois pour toutes.
- Refusent d'exister avec un nom vide ou une maturité cible nulle ou négative.
- Commencent avec une maturité de 0 et un arôme de 0. L'arôme ne descend jamais sous 0.
- Ont un **rythme d'affinage** et un **soin**, reçus à leur création. Une meule sans
  l'un ou l'autre est refusée.
- Sont **prêtes** dès que leur maturité atteint ou dépasse leur maturité cible. Une
  meule prête n'évolue plus.
- S'affichent ainsi : `Herve [maturité 3/12, arôme 1]`, suivi de ` (prête)` quand
  elle est prête.

## Les rythmes d'affinage

Le rythme s'applique en premier, chaque jour.

| Rythme | Effet par jour |
| - | - |
| Lent | maturité + 1 |
| Normal | maturité + 2 |
| Pressé | maturité + 3, arôme - 1 |

## Les soins

Le soin s'applique juste après le rythme, le même jour.

| Soin | Effet |
| - | - |
| Aucun | rien |
| Retournement | les jours pairs seulement : arôme + 1 |
| Lavage à la bière | arôme + 2 |
| Saumure | arôme + 1, maturité + 1 |

## Les meules de la cave

| Meule | Maturité cible | Rythme | Soin de départ |
| - | - | - | - |
| Herve | 12 | normal | saumure |
| Chimay | 10 | lent | lavage à la bière |
| Chèvre cendré | 6 | pressé | retournement |

## La journée en cave

- La cave annonce d'abord combien de meules elle contient.
- L'affinage dure 6 jours, numérotés à partir de 1.
- Au début du jour 3, la fromagère fait passer Herve au lavage à la bière, et
  l'annonce.
- Chaque jour, les meules sont traitées dans l'ordre du tableau. Une meule qui n'est
  pas encore prête reçoit son rythme puis son soin, puis s'affiche.
- Le jour où une meule devient prête, elle l'annonce juste après son affichage.
- Une meule déjà prête s'affiche telle quelle, sans évoluer.
- À la fin, le bilan donne le nombre de meules prêtes.

## Sortie attendue

```
=== Cave d'affinage : 3 meules ===

--- Jour 1 ---
Herve [maturité 3/12, arôme 1]
Chimay [maturité 1/10, arôme 2]
Chèvre cendré [maturité 3/6, arôme 0]

--- Jour 2 ---
Herve [maturité 6/12, arôme 2]
Chimay [maturité 2/10, arôme 4]
Chèvre cendré [maturité 6/6, arôme 1] (prête)
Chèvre cendré est prête !

--- Jour 3 ---
Herve passe au lavage à la bière.
Herve [maturité 8/12, arôme 4]
Chimay [maturité 3/10, arôme 6]
Chèvre cendré [maturité 6/6, arôme 1] (prête)

--- Jour 4 ---
Herve [maturité 10/12, arôme 6]
Chimay [maturité 4/10, arôme 8]
Chèvre cendré [maturité 6/6, arôme 1] (prête)

--- Jour 5 ---
Herve [maturité 12/12, arôme 8] (prête)
Herve est prête !
Chimay [maturité 5/10, arôme 10]
Chèvre cendré [maturité 6/6, arôme 1] (prête)

--- Jour 6 ---
Herve [maturité 12/12, arôme 8] (prête)
Chimay [maturité 6/10, arôme 12]
Chèvre cendré [maturité 6/6, arôme 1] (prête)

=== Bilan ===
Meules prêtes : 2 / 3
```

## Critères d'acceptation

- La console affiche exactement la sortie attendue.
- Il n'existe **aucune sous-classe** de meule : une seule classe, et les différences
  viennent uniquement des comportements qu'elle reçoit.
- Chaque rythme et chaque soin est écrit une seule fois, et partagé par toutes les
  meules qui l'utilisent.
- Ajouter un nouveau rythme ou un nouveau soin ne demande de toucher ni à la meule,
  ni au déroulement des journées.
- Herve change de soin en cours d'affinage sans qu'on crée une nouvelle meule.
- Le déroulement des journées ne teste jamais de quelle meule il s'agit.
- Une meule invalide ne peut pas exister.

## Bonus

Vérifier par des tests unitaires, sans lancer les 6 jours : chaque rythme, chaque
soin (dont le retournement un jour pair et un jour impair), l'arôme qui ne descend pas
sous 0, la meule qui n'évolue plus une fois prête, et le refus des valeurs invalides.
