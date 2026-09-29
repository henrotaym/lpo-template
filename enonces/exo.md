# TP 05 : Des monstres qui se ressemblent

## Contexte

Aria explore un donjon. Elle y affronte, dans l'ordre, un Gobelin, un Squelette et
un Troll. Chaque sorte de monstre se bat à sa façon. D'autres sortes viendront.

## Tous les personnages

- Ont un nom, des PV maximum et une attaque, fixés une fois pour toutes.
- Commencent avec tous leurs PV.
- Ne descendent jamais sous 0 PV et ne dépassent jamais leur maximum.
- Perdent autant de PV que les dégâts reçus.
- Frappent avec toute leur attaque.
- Tombent à 0 PV.
- S'affichent ainsi : `Gobelin [28/40 PV, ATK 7]`.

## Les personnages

| Personnage | PV | ATK | Besoin propre |
| - | - | - | - |
| Aria | 120 | 12 | aucun |
| Gobelin | 40 | 7 | aucun |
| Squelette | 55 | 9 | ne perd que la moitié des dégâts reçus, arrondie vers le bas, et l'annonce |
| Troll | 80 | 15 | à la fin de chaque tour, s'il est debout et blessé, regagne 5 PV et l'annonce |

Un monstre d'une sorte donnée a toujours ces valeurs.

## Le combat

- Aria affronte les monstres un par un, jusqu'à ce que l'un des deux tombe.
- À chaque tour : Aria frappe, le monstre riposte s'il est debout, puis le tour se
  termine.
- L'exploration s'arrête quand le donjon est vide ou qu'Aria tombe, puis un bilan
  s'affiche.

## Sortie attendue

```
Aria [120/120 PV, ATK 12] entre dans le donjon.
Le donjon contient 3 monstres.

--- Gobelin [40/40 PV, ATK 7] apparaît ---
Aria frappe Gobelin pour 12 dégâts. Gobelin [28/40 PV, ATK 7]
Gobelin frappe Aria pour 7 dégâts. Aria [113/120 PV, ATK 12]
Aria frappe Gobelin pour 12 dégâts. Gobelin [16/40 PV, ATK 7]
Gobelin frappe Aria pour 7 dégâts. Aria [106/120 PV, ATK 12]
Aria frappe Gobelin pour 12 dégâts. Gobelin [4/40 PV, ATK 7]
Gobelin frappe Aria pour 7 dégâts. Aria [99/120 PV, ATK 12]
Aria frappe Gobelin pour 12 dégâts. Gobelin [0/40 PV, ATK 7]
Gobelin est vaincu !

--- Squelette [55/55 PV, ATK 9] apparaît ---
Les os encaissent la moitié du coup.
Aria frappe Squelette pour 12 dégâts. Squelette [49/55 PV, ATK 9]
Squelette frappe Aria pour 9 dégâts. Aria [90/120 PV, ATK 12]
Les os encaissent la moitié du coup.
Aria frappe Squelette pour 12 dégâts. Squelette [43/55 PV, ATK 9]
Squelette frappe Aria pour 9 dégâts. Aria [81/120 PV, ATK 12]
Les os encaissent la moitié du coup.
Aria frappe Squelette pour 12 dégâts. Squelette [37/55 PV, ATK 9]
Squelette frappe Aria pour 9 dégâts. Aria [72/120 PV, ATK 12]
Les os encaissent la moitié du coup.
Aria frappe Squelette pour 12 dégâts. Squelette [31/55 PV, ATK 9]
Squelette frappe Aria pour 9 dégâts. Aria [63/120 PV, ATK 12]
Les os encaissent la moitié du coup.
Aria frappe Squelette pour 12 dégâts. Squelette [25/55 PV, ATK 9]
Squelette frappe Aria pour 9 dégâts. Aria [54/120 PV, ATK 12]
Les os encaissent la moitié du coup.
Aria frappe Squelette pour 12 dégâts. Squelette [19/55 PV, ATK 9]
Squelette frappe Aria pour 9 dégâts. Aria [45/120 PV, ATK 12]
Les os encaissent la moitié du coup.
Aria frappe Squelette pour 12 dégâts. Squelette [13/55 PV, ATK 9]
Squelette frappe Aria pour 9 dégâts. Aria [36/120 PV, ATK 12]
Les os encaissent la moitié du coup.
Aria frappe Squelette pour 12 dégâts. Squelette [7/55 PV, ATK 9]
Squelette frappe Aria pour 9 dégâts. Aria [27/120 PV, ATK 12]
Les os encaissent la moitié du coup.
Aria frappe Squelette pour 12 dégâts. Squelette [1/55 PV, ATK 9]
Squelette frappe Aria pour 9 dégâts. Aria [18/120 PV, ATK 12]
Les os encaissent la moitié du coup.
Aria frappe Squelette pour 12 dégâts. Squelette [0/55 PV, ATK 9]
Squelette est vaincu !

--- Troll [80/80 PV, ATK 15] apparaît ---
Aria frappe Troll pour 12 dégâts. Troll [68/80 PV, ATK 15]
Troll frappe Aria pour 15 dégâts. Aria [3/120 PV, ATK 12]
Le Troll régénère 5 PV. Troll [73/80 PV, ATK 15]
Aria frappe Troll pour 12 dégâts. Troll [61/80 PV, ATK 15]
Troll frappe Aria pour 15 dégâts. Aria [0/120 PV, ATK 12]
Le Troll régénère 5 PV. Troll [66/80 PV, ATK 15]

=== Fin de l'exploration ===
Monstres vaincus : 2 / 3
Aria est tombée face au Troll.
```

## Critères d'acceptation

- La console affiche exactement la sortie attendue.
- Ajouter une nouvelle sorte de monstre ne demande pas de toucher au combat.
- Une règle commune à tous les personnages n'est écrite qu'une fois.
