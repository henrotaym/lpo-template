# Le donjon : héritage et composition

## Contexte

Aria explore un donjon. Elle y affronte, dans l'ordre, un Gobelin, un Squelette, un
Troll et un Golem, puis un coffre l'attend au fond.

Chaque sorte de monstre est différente, mais leurs comportements se ressemblent :
certains encaissent mieux les coups, d'autres se régénèrent. Ces comportements sont
des pièces qu'on assemble. Un nouveau monstre doit pouvoir réutiliser les pièces
existantes, et un personnage doit pouvoir changer de pièce en cours de partie.

## Tous les personnages

- Ont un nom, des PV maximum, une attaque et une défense, fixés une fois pour toutes.
- Refusent d'exister avec un nom vide, des PV maximum nuls ou négatifs, une attaque
  ou une défense négative.
- Commencent avec tous leurs PV, ne descendent jamais sous 0 et ne dépassent jamais
  leur maximum.
- Ont un cri de guerre, propre à chaque sorte. Aucun « personnage » générique
  n'existe : chacun est d'une sorte précise.
- Ont une **façon d'encaisser** et un **effet de fin de tour**, reçus à leur
  création. Un personnage sans l'un ou l'autre est refusé.
- S'affichent ainsi : `Gobelin [22/40 PV, ATK 7]`.

## Les comportements

**Façons d'encaisser** : elles transforment les dégâts calculés en dégâts subis.

| Façon d'encaisser | Dégâts subis |
| - | - |
| Normalement | les dégâts calculés |
| La moitié | la moitié, arrondie vers le bas, minimum 1 |
| Armure de N | les dégâts moins N, minimum 1 |

**Effets de fin de tour** : ils s'appliquent au personnage à la fin de chaque tour.

| Effet | Ce qui se passe |
| - | - |
| Aucun | rien |
| Régénération de N | s'il est debout et blessé, regagne N PV sans dépasser son maximum, et l'annonce |

## Les personnages

| Personnage | PV | ATK | DEF | Encaisse | Fin de tour | Cri de guerre | Laisse |
| - | - | - | - | - | - | - | - |
| Aria | 200 | 20 | 4 | normalement | aucun | Pour la lumière ! | rien |
| Gobelin | 40 | 7 | 2 | normalement | aucun | Mon trésor, à moi ! | Dague ébréchée (15 po) |
| Squelette | 55 | 9 | 3 | la moitié | aucun | Clac, clac, clac... | un bouclier (armure de 2) |
| Troll | 80 | 15 | 5 | normalement | régénération de 5 | Troll écraser ! | Massue de troll (80 po) |
| Golem | 70 | 12 | 4 | la moitié | régénération de 3 | Grrrooonnnk. | rien |

Un monstre d'une sorte donnée a toujours ces valeurs. Le Golem n'a aucune règle à
lui : il combine deux comportements existants.

## Le bouclier

- Quand un monstre qui porte un bouclier est vaincu, Aria le ramasse et l'annonce.
- À partir de ce moment, Aria encaisse avec ce bouclier au lieu d'encaisser
  normalement.

## Les dégâts

- Attaque de l'attaquant moins défense du défenseur, avec un minimum de 1.
- La façon d'encaisser du défenseur s'applique ensuite.

| Attaquant | Défenseur | Dégâts |
| - | - | - |
| Aria | Gobelin | 18 |
| Aria | Squelette | 8 |
| Golem | Aria avec bouclier | 6 |
| ATK 1 | Squelette | 1 |

## Le butin

- A un nom et une valeur en pièces d'or.
- Refuse d'exister sans nom ou avec une valeur négative.
- S'affiche ainsi : `Dague ébréchée (15 po)`.
- Quand une source de butin est vaincue ou ouverte, son butin est ramassé et compté
  dans l'or récolté.

## Le coffre

- S'appelle « Coffre du donjon », a 30 de solidité et contient une Couronne
  ancienne de 250 po.
- Perd autant de solidité que les coups reçus, sans descendre sous 0, et s'ouvre
  à 0.
- S'affiche ainsi : `Coffre du donjon [30 de solidité]`.
- Aria ne l'attaque que si elle a survécu aux monstres. Elle le frappe de toute son
  attaque jusqu'à l'ouvrir, puis ramasse son contenu.

## Le combat

- Aria affronte les monstres un par un, jusqu'à ce que l'un des deux tombe.
- À son apparition, le monstre pousse son cri de guerre.
- À chaque tour : Aria frappe, le monstre riposte s'il est debout, puis l'effet de
  fin de tour du monstre s'applique.
- L'exploration s'arrête quand le donjon est vide ou qu'Aria tombe, puis vient le
  coffre, puis le bilan.

## Sortie attendue

```
Aria [200/200 PV, ATK 20] entre dans le donjon.

--- Gobelin [40/40 PV, ATK 7] apparaît ---
Gobelin : Mon trésor, à moi !
Aria frappe Gobelin pour 18 dégâts. Gobelin [22/40 PV, ATK 7]
Gobelin riposte pour 3 dégâts. Aria [197/200 PV, ATK 20]
Aria frappe Gobelin pour 18 dégâts. Gobelin [4/40 PV, ATK 7]
Gobelin riposte pour 3 dégâts. Aria [194/200 PV, ATK 20]
Aria frappe Gobelin pour 18 dégâts. Gobelin [0/40 PV, ATK 7]
Gobelin est vaincu !
Butin : Dague ébréchée (15 po)

--- Squelette [55/55 PV, ATK 9] apparaît ---
Squelette : Clac, clac, clac...
Aria frappe Squelette pour 8 dégâts. Squelette [47/55 PV, ATK 9]
Squelette riposte pour 5 dégâts. Aria [189/200 PV, ATK 20]
Aria frappe Squelette pour 8 dégâts. Squelette [39/55 PV, ATK 9]
Squelette riposte pour 5 dégâts. Aria [184/200 PV, ATK 20]
Aria frappe Squelette pour 8 dégâts. Squelette [31/55 PV, ATK 9]
Squelette riposte pour 5 dégâts. Aria [179/200 PV, ATK 20]
Aria frappe Squelette pour 8 dégâts. Squelette [23/55 PV, ATK 9]
Squelette riposte pour 5 dégâts. Aria [174/200 PV, ATK 20]
Aria frappe Squelette pour 8 dégâts. Squelette [15/55 PV, ATK 9]
Squelette riposte pour 5 dégâts. Aria [169/200 PV, ATK 20]
Aria frappe Squelette pour 8 dégâts. Squelette [7/55 PV, ATK 9]
Squelette riposte pour 5 dégâts. Aria [164/200 PV, ATK 20]
Aria frappe Squelette pour 8 dégâts. Squelette [0/55 PV, ATK 9]
Squelette est vaincu !
Aria ramasse le bouclier de Squelette.

--- Troll [80/80 PV, ATK 15] apparaît ---
Troll : Troll écraser !
Aria frappe Troll pour 15 dégâts. Troll [65/80 PV, ATK 15]
Troll riposte pour 9 dégâts. Aria [155/200 PV, ATK 20]
Troll régénère 5 PV. Troll [70/80 PV, ATK 15]
Aria frappe Troll pour 15 dégâts. Troll [55/80 PV, ATK 15]
Troll riposte pour 9 dégâts. Aria [146/200 PV, ATK 20]
Troll régénère 5 PV. Troll [60/80 PV, ATK 15]
Aria frappe Troll pour 15 dégâts. Troll [45/80 PV, ATK 15]
Troll riposte pour 9 dégâts. Aria [137/200 PV, ATK 20]
Troll régénère 5 PV. Troll [50/80 PV, ATK 15]
Aria frappe Troll pour 15 dégâts. Troll [35/80 PV, ATK 15]
Troll riposte pour 9 dégâts. Aria [128/200 PV, ATK 20]
Troll régénère 5 PV. Troll [40/80 PV, ATK 15]
Aria frappe Troll pour 15 dégâts. Troll [25/80 PV, ATK 15]
Troll riposte pour 9 dégâts. Aria [119/200 PV, ATK 20]
Troll régénère 5 PV. Troll [30/80 PV, ATK 15]
Aria frappe Troll pour 15 dégâts. Troll [15/80 PV, ATK 15]
Troll riposte pour 9 dégâts. Aria [110/200 PV, ATK 20]
Troll régénère 5 PV. Troll [20/80 PV, ATK 15]
Aria frappe Troll pour 15 dégâts. Troll [5/80 PV, ATK 15]
Troll riposte pour 9 dégâts. Aria [101/200 PV, ATK 20]
Troll régénère 5 PV. Troll [10/80 PV, ATK 15]
Aria frappe Troll pour 15 dégâts. Troll [0/80 PV, ATK 15]
Troll est vaincu !
Butin : Massue de troll (80 po)

--- Golem [70/70 PV, ATK 12] apparaît ---
Golem : Grrrooonnnk.
Aria frappe Golem pour 8 dégâts. Golem [62/70 PV, ATK 12]
Golem riposte pour 6 dégâts. Aria [95/200 PV, ATK 20]
Golem régénère 3 PV. Golem [65/70 PV, ATK 12]
Aria frappe Golem pour 8 dégâts. Golem [57/70 PV, ATK 12]
Golem riposte pour 6 dégâts. Aria [89/200 PV, ATK 20]
Golem régénère 3 PV. Golem [60/70 PV, ATK 12]
Aria frappe Golem pour 8 dégâts. Golem [52/70 PV, ATK 12]
Golem riposte pour 6 dégâts. Aria [83/200 PV, ATK 20]
Golem régénère 3 PV. Golem [55/70 PV, ATK 12]
Aria frappe Golem pour 8 dégâts. Golem [47/70 PV, ATK 12]
Golem riposte pour 6 dégâts. Aria [77/200 PV, ATK 20]
Golem régénère 3 PV. Golem [50/70 PV, ATK 12]
Aria frappe Golem pour 8 dégâts. Golem [42/70 PV, ATK 12]
Golem riposte pour 6 dégâts. Aria [71/200 PV, ATK 20]
Golem régénère 3 PV. Golem [45/70 PV, ATK 12]
Aria frappe Golem pour 8 dégâts. Golem [37/70 PV, ATK 12]
Golem riposte pour 6 dégâts. Aria [65/200 PV, ATK 20]
Golem régénère 3 PV. Golem [40/70 PV, ATK 12]
Aria frappe Golem pour 8 dégâts. Golem [32/70 PV, ATK 12]
Golem riposte pour 6 dégâts. Aria [59/200 PV, ATK 20]
Golem régénère 3 PV. Golem [35/70 PV, ATK 12]
Aria frappe Golem pour 8 dégâts. Golem [27/70 PV, ATK 12]
Golem riposte pour 6 dégâts. Aria [53/200 PV, ATK 20]
Golem régénère 3 PV. Golem [30/70 PV, ATK 12]
Aria frappe Golem pour 8 dégâts. Golem [22/70 PV, ATK 12]
Golem riposte pour 6 dégâts. Aria [47/200 PV, ATK 20]
Golem régénère 3 PV. Golem [25/70 PV, ATK 12]
Aria frappe Golem pour 8 dégâts. Golem [17/70 PV, ATK 12]
Golem riposte pour 6 dégâts. Aria [41/200 PV, ATK 20]
Golem régénère 3 PV. Golem [20/70 PV, ATK 12]
Aria frappe Golem pour 8 dégâts. Golem [12/70 PV, ATK 12]
Golem riposte pour 6 dégâts. Aria [35/200 PV, ATK 20]
Golem régénère 3 PV. Golem [15/70 PV, ATK 12]
Aria frappe Golem pour 8 dégâts. Golem [7/70 PV, ATK 12]
Golem riposte pour 6 dégâts. Aria [29/200 PV, ATK 20]
Golem régénère 3 PV. Golem [10/70 PV, ATK 12]
Aria frappe Golem pour 8 dégâts. Golem [2/70 PV, ATK 12]
Golem riposte pour 6 dégâts. Aria [23/200 PV, ATK 20]
Golem régénère 3 PV. Golem [5/70 PV, ATK 12]
Aria frappe Golem pour 8 dégâts. Golem [0/70 PV, ATK 12]
Golem est vaincu !

--- Coffre du donjon [30 de solidité] ---
Aria frappe le coffre. Coffre du donjon [10 de solidité]
Aria frappe le coffre. Coffre du donjon [0 de solidité]
Butin : Couronne ancienne (250 po)

=== Fin de l'exploration ===
Monstres vaincus : 4 / 4
Or récolté : 345 po
```

## Critères d'acceptation

- La console affiche exactement la sortie attendue.
- Une règle commune à tous les personnages n'est écrite qu'une fois.
- Chaque façon d'encaisser et chaque effet de fin de tour est écrit une seule fois,
  et partagé par tous les personnages qui l'utilisent.
- Ajouter une nouvelle façon d'encaisser ou un nouvel effet de fin de tour ne
  demande de toucher ni au calcul des dégâts, ni au combat, ni aux personnages
  existants.
- Le Golem ne contient aucune règle de combat : seulement ses valeurs et ses
  comportements.
- Aria change de façon d'encaisser en cours de partie sans changer de sorte.
- Le combat parcourt les monstres sans jamais tester de quelle sorte ils sont.
- Le butin se ramasse de la même façon sur un monstre et sur un coffre.

## Bonus

Vérifier par des tests unitaires, sans lancer de combat : le calcul des dégâts,
chaque façon d'encaisser, la régénération, le changement de bouclier et le refus des
valeurs invalides.
