# TP 09 : Des valeurs qui portent leurs règles

## Contexte

Aria explore un donjon élémentaire où le feu, la glace et le poison s'affrontent.
Elle y croise un Gobelin, un Squelette, un Fantôme, un Troll et un Dragon, et un
coffre l'attend au fond. Certaines créatures volent, d'autres se régénèrent,
d'autres laissent un butin, et certaines cumulent plusieurs de ces capacités. Un
coffre n'est pas un personnage, mais il se frappe et se pille comme un monstre.
Chaque butin a une rareté qui fait varier sa valeur.

## Les éléments

- Il existe exactement quatre éléments : Physique, Feu, Glace et Poison.
- Le Feu est fort contre la Glace, la Glace contre le Poison, le Poison contre le
  Feu. Le Physique n'est fort contre rien.
- Un élément fort contre celui du défenseur multiplie les dégâts par 1,5. Sinon le
  multiplicateur vaut 1,0.

## Tous les personnages

- Ont un nom, des PV maximum, une attaque, une défense et un élément, fixés une fois
  pour toutes.
- Refusent d'exister avec un nom vide, des PV maximum nuls ou négatifs, une attaque
  ou une défense négative.
- Sans élément précisé, sont Physique.
- Commencent avec tous leurs PV, ne descendent jamais sous 0 et ne dépassent jamais
  leur maximum.
- Savent tous attaquer, toujours avec leur élément, mais aucun « personnage »
  générique n'existe : chacun est d'une sorte précise.
- Ont une façon d'encaisser les coups, choisie à leur création.
- Se classent entre eux selon leurs PV actuels.
- S'affichent ainsi : `Gobelin [26/40 PV, ATK 7] ~ Physique`.

## Les personnages

| Personnage | PV | ATK | DEF | Élément | Encaisse | Vole | Se régénère |
| - | - | - | - | - | - | - | - |
| Aria | 220 | 16 | 4 | Feu | normalement | non | non |
| Gobelin | 40 | 7 | 2 | Physique | normalement | non | non |
| Squelette | 55 | 9 | 3 | Physique | la moitié | non | non |
| Fantôme | 45 | 10 | 1 | Glace | normalement | oui, esquive 30 % | non |
| Troll | 80 | 15 | 5 | Poison | normalement | non | 5 PV |
| Dragon | 120 | 20 | 8 | Feu | normalement | oui, esquive 15 % | 8 PV |

Un monstre d'une sorte donnée a toujours ces valeurs. Aria reçoit son élément à sa
création.

## Les capacités

- **Encaisser** : « normalement », le personnage subit les dégâts calculés. « La
  moitié », il n'en subit que la moitié arrondie vers le bas, avec un minimum de 1.
- **Voler** : une créature volante annonce son déplacement et sa chance d'esquive à
  son apparition. Sans précision, l'esquive vaut 30 %.
- **Se régénérer** : à la fin de chaque tour, s'il est debout et blessé, le
  personnage regagne ses PV de régénération, sans dépasser son maximum, et l'annonce.
- **Laisser un butin** : quand on la vainc, la source donne son butin, qui est
  ramassé et compté dans l'or récolté.
- **Être frappé** : un personnage comme un coffre peut recevoir des dégâts et être
  détruit.
- **Le Fantôme** : un coup sur deux le traverse sans effet, en commençant par le
  premier, et il l'annonce.

## L'attaque

- Une attaque est décrite par son élément, sa puissance et le fait qu'elle soit
  critique ou non.
- Elle refuse d'exister sans élément ou avec une puissance négative.
- Deux attaques aux mêmes valeurs sont interchangeables.

## Le calcul des dégâts

- Il ne reçoit que l'attaque et le défenseur.
- Dégâts = puissance, doublée si l'attaque est critique, multipliée par le
  multiplicateur élémentaire et arrondie à l'entier le plus proche, moins la défense
  du défenseur, avec un minimum de 1.
- La façon d'encaisser du défenseur s'applique ensuite.
- Exemples à respecter :

| Attaque | Défenseur | Dégâts |
| - | - | - |
| Physique, 12 | Gobelin | 10 |
| Physique, 12, critique | Gobelin | 22 |
| Feu, 20 | Fantôme | 29 |
| Physique, 20 | Fantôme | 19 |
| Physique, 1 | Troll | 1 |
| Physique, 13 | Squelette | 5 |

## La rareté

Il existe exactement quatre raretés :

| Rareté | Multiplicateur de valeur | Symbole | Description |
| - | - | - | - |
| Commun | 1,0 | `*` | Un objet banal. |
| Rare | 1,5 | `**` | Une belle trouvaille. |
| Épique | 2,5 | `***` | Une pièce exceptionnelle ! |
| Légendaire | 5,0 | `****` | Une pièce exceptionnelle ! |

## Le butin

- A un nom, une valeur de base et une rareté.
- Refuse d'exister sans nom, sans rareté ou avec une valeur de base négative.
- Sa valeur réelle est sa valeur de base multipliée par sa rareté, arrondie à
  l'entier le plus proche.
- Deux butins aux mêmes nom, valeur de base et rareté sont égaux. Une rareté
  différente suffit à les distinguer.
- S'affiche ainsi : `Massue de troll (120 po) ** Rare`.
- Au ramassage, sa description s'affiche à la suite.

| Source | Butin | Valeur de base | Rareté |
| - | - | - | - |
| Gobelin | Dague ébréchée | 15 | Commun |
| Troll | Massue de troll | 80 | Rare |
| Dragon | Écaille de dragon | 500 | Légendaire |
| Coffre du donjon | Couronne ancienne | 250 | Épique |

Le Squelette et le Fantôme ne laissent rien. Exemple à respecter : une Couronne de
valeur de base 100 et Épique vaut 250 po.

## Le coffre

- S'appelle « Coffre du donjon », a 30 de solidité et contient la Couronne ancienne.
- Perd autant de solidité que les dégâts reçus, sans descendre sous 0, et est
  détruit à 0.
- S'affiche ainsi : `Coffre du donjon [30 de solidité]`.
- Aria ne l'ouvre que si elle a survécu aux monstres. Elle le frappe avec la
  puissance d'une attaque non critique jusqu'à le détruire, puis ramasse son contenu.

## Le combat

- Les monstres se présentent dans l'ordre de leurs PV, du plus faible au plus
  robuste.
- Aria affronte les monstres un par un, jusqu'à ce que l'un des deux tombe.
- À chaque tour : Aria frappe, le monstre riposte s'il est debout, puis le tour se
  termine.
- Chaque coup d'Aria affiche l'élément de l'attaque et le multiplicateur appliqué,
  puis l'état du monstre sur une ligne à part.
- Les coups d'Aria sont critiques une fois sur cinq au hasard. Les ripostes ne le
  sont jamais. Le hasard est initialisé avec la graine 42 et un coup est critique
  quand le tirage entre 0 et 99 est inférieur à 20.
- Aria compte elle-même ses victoires.
- L'exploration s'arrête quand le donjon est vide ou qu'Aria tombe, puis vient le
  coffre, puis le bilan.

## Sortie attendue

```
Aria [220/220 PV, ATK 16] ~ Feu entre dans le donjon.

--- Gobelin [40/40 PV, ATK 7] ~ Physique apparaît ---
Aria frappe Gobelin - Feu x1.0 - 14 dégâts
  Gobelin [26/40 PV, ATK 7] ~ Physique
  Gobelin riposte pour 3 dégâts. Aria [217/220 PV, ATK 16] ~ Feu
Aria frappe Gobelin - Feu x1.0 - 14 dégâts
  Gobelin [12/40 PV, ATK 7] ~ Physique
  Gobelin riposte pour 3 dégâts. Aria [214/220 PV, ATK 16] ~ Feu
Aria frappe Gobelin - Feu x1.0 - 14 dégâts
  Gobelin [0/40 PV, ATK 7] ~ Physique
Gobelin est vaincu !
Butin : Dague ébréchée (15 po) * Commun - Un objet banal.

--- Fantôme [45/45 PV, ATK 10] ~ Glace apparaît ---
Fantôme vole au-dessus du sol (esquive 30 %).
Le coup traverse le Fantôme !
Aria frappe Fantôme - Feu x1.5 - 23 dégâts
  Fantôme [45/45 PV, ATK 10] ~ Glace
  Fantôme riposte pour 6 dégâts. Aria [208/220 PV, ATK 16] ~ Feu
Aria frappe Fantôme - Feu x1.5 - 23 dégâts
  Fantôme [22/45 PV, ATK 10] ~ Glace
  Fantôme riposte pour 6 dégâts. Aria [202/220 PV, ATK 16] ~ Feu
Le coup traverse le Fantôme !
Aria frappe Fantôme - Feu x1.5 - 23 dégâts
  Fantôme [22/45 PV, ATK 10] ~ Glace
  Fantôme riposte pour 6 dégâts. Aria [196/220 PV, ATK 16] ~ Feu
Aria frappe Fantôme - Feu x1.5 - 47 dégâts (critique !)
  Fantôme [0/45 PV, ATK 10] ~ Glace
Fantôme est vaincu !

--- Squelette [55/55 PV, ATK 9] ~ Physique apparaît ---
Aria frappe Squelette - Feu x1.0 - 14 dégâts (critique !)
  Squelette [41/55 PV, ATK 9] ~ Physique
  Squelette riposte pour 5 dégâts. Aria [191/220 PV, ATK 16] ~ Feu
Aria frappe Squelette - Feu x1.0 - 14 dégâts (critique !)
  Squelette [27/55 PV, ATK 9] ~ Physique
  Squelette riposte pour 5 dégâts. Aria [186/220 PV, ATK 16] ~ Feu
Aria frappe Squelette - Feu x1.0 - 6 dégâts
  Squelette [21/55 PV, ATK 9] ~ Physique
  Squelette riposte pour 5 dégâts. Aria [181/220 PV, ATK 16] ~ Feu
Aria frappe Squelette - Feu x1.0 - 6 dégâts
  Squelette [15/55 PV, ATK 9] ~ Physique
  Squelette riposte pour 5 dégâts. Aria [176/220 PV, ATK 16] ~ Feu
Aria frappe Squelette - Feu x1.0 - 14 dégâts (critique !)
  Squelette [1/55 PV, ATK 9] ~ Physique
  Squelette riposte pour 5 dégâts. Aria [171/220 PV, ATK 16] ~ Feu
Aria frappe Squelette - Feu x1.0 - 6 dégâts
  Squelette [0/55 PV, ATK 9] ~ Physique
Squelette est vaincu !

--- Troll [80/80 PV, ATK 15] ~ Poison apparaît ---
Aria frappe Troll - Feu x1.0 - 11 dégâts
  Troll [69/80 PV, ATK 15] ~ Poison
  Troll riposte pour 19 dégâts. Aria [152/220 PV, ATK 16] ~ Feu
Le Troll régénère 5 PV. Troll [74/80 PV, ATK 15] ~ Poison
Aria frappe Troll - Feu x1.0 - 11 dégâts
  Troll [63/80 PV, ATK 15] ~ Poison
  Troll riposte pour 19 dégâts. Aria [133/220 PV, ATK 16] ~ Feu
Le Troll régénère 5 PV. Troll [68/80 PV, ATK 15] ~ Poison
Aria frappe Troll - Feu x1.0 - 11 dégâts
  Troll [57/80 PV, ATK 15] ~ Poison
  Troll riposte pour 19 dégâts. Aria [114/220 PV, ATK 16] ~ Feu
Le Troll régénère 5 PV. Troll [62/80 PV, ATK 15] ~ Poison
Aria frappe Troll - Feu x1.0 - 11 dégâts
  Troll [51/80 PV, ATK 15] ~ Poison
  Troll riposte pour 19 dégâts. Aria [95/220 PV, ATK 16] ~ Feu
Le Troll régénère 5 PV. Troll [56/80 PV, ATK 15] ~ Poison
Aria frappe Troll - Feu x1.0 - 11 dégâts
  Troll [45/80 PV, ATK 15] ~ Poison
  Troll riposte pour 19 dégâts. Aria [76/220 PV, ATK 16] ~ Feu
Le Troll régénère 5 PV. Troll [50/80 PV, ATK 15] ~ Poison
Aria frappe Troll - Feu x1.0 - 11 dégâts
  Troll [39/80 PV, ATK 15] ~ Poison
  Troll riposte pour 19 dégâts. Aria [57/220 PV, ATK 16] ~ Feu
Le Troll régénère 5 PV. Troll [44/80 PV, ATK 15] ~ Poison
Aria frappe Troll - Feu x1.0 - 27 dégâts (critique !)
  Troll [17/80 PV, ATK 15] ~ Poison
  Troll riposte pour 19 dégâts. Aria [38/220 PV, ATK 16] ~ Feu
Le Troll régénère 5 PV. Troll [22/80 PV, ATK 15] ~ Poison
Aria frappe Troll - Feu x1.0 - 27 dégâts (critique !)
  Troll [0/80 PV, ATK 15] ~ Poison
Troll est vaincu !
Butin : Massue de troll (120 po) ** Rare - Une belle trouvaille.

--- Dragon [120/120 PV, ATK 20] ~ Feu apparaît ---
Dragon vole au-dessus du sol (esquive 15 %).
Aria frappe Dragon - Feu x1.0 - 8 dégâts
  Dragon [112/120 PV, ATK 20] ~ Feu
  Dragon riposte pour 16 dégâts. Aria [22/220 PV, ATK 16] ~ Feu
Le Dragon régénère 8 PV. Dragon [120/120 PV, ATK 20] ~ Feu
Aria frappe Dragon - Feu x1.0 - 8 dégâts
  Dragon [112/120 PV, ATK 20] ~ Feu
  Dragon riposte pour 16 dégâts. Aria [6/220 PV, ATK 16] ~ Feu
Le Dragon régénère 8 PV. Dragon [120/120 PV, ATK 20] ~ Feu
Aria frappe Dragon - Feu x1.0 - 24 dégâts (critique !)
  Dragon [96/120 PV, ATK 20] ~ Feu
  Dragon riposte pour 16 dégâts. Aria [0/220 PV, ATK 16] ~ Feu
Le Dragon régénère 8 PV. Dragon [104/120 PV, ATK 20] ~ Feu

=== Fin de l'exploration ===
Monstres vaincus : 4 / 5
Or récolté : 135 po
```

## Critères d'acceptation

- La console affiche exactement la sortie attendue.
- Le calcul des dégâts ne connaît ni l'attaquant, ni aucune sorte de monstre
  précise.
- Ajouter une nouvelle façon d'encaisser les coups ne demande de toucher ni au
  calcul des dégâts ni aux personnages existants.
- Deux sortes de personnages peuvent partager la même façon d'encaisser.
- Donner une capacité (voler, se régénérer, laisser un butin) à une sorte de monstre
  ne demande pas de toucher aux autres sortes.
- Le butin se ramasse de la même façon sur un monstre et sur un coffre.
- Ajouter un élément ou une rareté ne demande de toucher qu'à la définition des
  éléments ou des raretés : ni le calcul des dégâts, ni le butin, ni les personnages.
- Changer l'élément d'Aria ne demande pas de créer une nouvelle sorte de personnage.
- Une attaque ou un butin invalide ne peut pas exister.
- Une règle commune n'est écrite qu'une fois.

## Bonus

Vérifier les exemples de calcul des dégâts et de butin par des tests unitaires,
sans lancer de combat.
