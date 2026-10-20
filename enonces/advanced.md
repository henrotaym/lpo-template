# Le donjon complet

## Contexte

Aria explore un donjon élémentaire où le feu, la glace et le poison s'affrontent.
Elle y croise un Gobelin, un Squelette, un Fantôme, un Troll et un Dragon, et un
coffre l'attend au fond. Certaines créatures volent, d'autres se régénèrent,
d'autres laissent un butin, et certaines cumulent plusieurs de ces capacités. Un
coffre n'est pas un personnage, mais il se frappe et se pille comme un monstre.
Aria emporte des potions et range ce qu'elle trouve dans ses sacs.

## L'organisation du code

- Le code est réparti en trois packages : `dungeon.character` (les personnages et
  leurs capacités), `dungeon.combat` (attaques, éléments, protections, calcul des
  dégâts et déroulement du combat) et `dungeon.loot` (butins, potions, sacs et
  coffre).
- Le programme se lance depuis une classe `Main` hors de ces packages, avec une
  méthode `public static void main(String[] args)`.
- `Main` crée Aria, les monstres et le coffre, puis confie l'exploration au
  combat. Il ne contient aucune règle du jeu.

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
- Attaquent tous de la même façon : avec leur élément et toute leur attaque.
- Ont chacun leur cri de guerre. Aucun « personnage » générique n'existe : chacun est
  d'une sorte précise.
- Ont une façon d'encaisser les coups, choisie à leur création.
- Peuvent être soignés d'un nombre de PV, ou avec une potion.
- Se classent entre eux selon leurs PV actuels.
- Sont égaux quand ils portent le même nom.
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

| Personnage | Cri de guerre |
| - | - |
| Aria | En avant ! |
| Gobelin | Ma dague a soif ! |
| Squelette | Clac clac clac. |
| Fantôme | Hoooou... |
| Troll | Troll écraser ! |
| Dragon | Brûlez, misérables ! |

Un monstre d'une sorte donnée a toujours ces valeurs. Aria reçoit son nom, ses
valeurs et son élément à sa création.

## Les capacités

- **Encaisser** : « normalement », le personnage subit les dégâts calculés. « La
  moitié », il n'en subit que la moitié arrondie vers le bas, avec un minimum de 1.
- **Voler** : une créature volante annonce son déplacement et sa chance d'esquive à
  son apparition. Sans précision, l'esquive vaut 30 %.
- **Se régénérer** : à la fin de chaque tour, s'il est debout et blessé, le
  personnage regagne ses PV de régénération, sans dépasser son maximum, et l'annonce.
- **Laisser un butin** : quand on la vainc, la source donne son butin, qui est
  ramassé par Aria.
- **Être frappé** : un personnage comme un coffre peut recevoir des dégâts et être
  détruit.
- **Le Fantôme** : un coup sur deux le traverse sans effet, en commençant par le
  premier, et il l'annonce.

## L'attaque

- Une attaque est décrite par son élément, sa puissance et le fait qu'elle soit
  critique ou non.
- Elle refuse d'exister sans élément ou avec une puissance négative.
- Deux attaques aux mêmes valeurs sont égales.

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
- Sa valeur en or est sa valeur de base multipliée par sa rareté, arrondie à
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

## Les potions

- Une potion a un nom, un nombre de PV soignés et une valeur en or.
- Elle refuse d'exister sans nom, avec un soin nul ou négatif, ou une valeur
  négative.
- La potion de soin standard s'appelle « Potion de soin », soigne 40 PV et vaut
  20 po. On l'obtient sans répéter ces valeurs.
- S'affiche ainsi : `Potion de soin (+40 PV)`.

## Les sacs

- Un sac ne contient que des objets qui ont une valeur en or. Y ranger autre chose
  est refusé à la compilation.
- Un même sac sait contenir des butins ou des potions, sans être écrit deux fois.
- Il refuse un objet absent.
- Il connaît son nombre d'objets et leur valeur totale en or. Vide, il vaut 0.
- On en retire les objets dans l'ordre où ils ont été rangés. Prendre dans un sac
  vide est refusé.
- Le contenu d'un sac ne se modifie que par le sac lui-même.

## Aria

- Elle porte un sac de butins et un sac de potions. Elle entre dans le donjon avec
  deux potions de soin standard.
- Elle range dans son sac de butins tout ce qu'elle ramasse.
- Elle se souvient des monstres qu'elle a vaincus.
- À la fin de chaque tour, si elle est debout, sous 30 % de ses PV maximum et qu'il
  lui reste une potion, elle boit la première de son sac et l'annonce. La potion
  bue quitte le sac. Une seule potion par tour.

## Le coffre

- S'appelle « Coffre du donjon », a 30 de solidité et contient la Couronne ancienne.
- Perd autant de solidité que les dégâts reçus, sans descendre sous 0, et est
  détruit à 0.
- S'affiche ainsi : `Coffre du donjon [30 de solidité]`.
- Aria ne l'ouvre que si elle a survécu aux monstres. Elle le frappe avec la
  puissance d'une attaque non critique jusqu'à le détruire, puis ramasse son contenu.

## Le combat

- Aria annonce son entrée puis pousse son cri. Chaque monstre pousse le sien à son
  apparition.
- Les monstres se présentent dans l'ordre de leurs PV, du plus faible au plus
  robuste.
- Aria affronte les monstres un par un, jusqu'à ce que l'un des deux tombe.
- Chaque tour se joue ainsi : Aria frappe, le monstre riposte s'il est debout, la
  fin de tour du monstre puis celle d'Aria s'appliquent.
- Chaque coup d'Aria affiche l'élément de l'attaque et le multiplicateur appliqué,
  puis l'état du monstre sur une ligne à part.
- Les coups d'Aria sont critiques une fois sur cinq au hasard. Les ripostes ne le
  sont jamais. Le hasard est initialisé avec la graine 42 et un coup est critique
  quand le tirage entre 0 et 99 est inférieur à 20.
- Un tour est un objet à part entière, qui n'existe qu'au sein d'un combat et
  utilise directement son hasard et son calcul des dégâts.
- L'exploration s'arrête quand le donjon est vide ou qu'Aria tombe, puis vient le
  coffre, puis le bilan.

## Le bilan

- Le nombre de monstres vaincus sur le nombre de monstres du donjon.
- Les noms des monstres vaincus, par ordre alphabétique et non par PV.
- L'or récolté : la valeur totale du sac de butins.
- Le nombre de potions restantes et leur valeur totale.

## Sortie attendue

```
Aria [220/220 PV, ATK 16] ~ Feu entre dans le donjon.
Aria : « En avant ! »

--- Gobelin [40/40 PV, ATK 7] ~ Physique apparaît ---
Gobelin : « Ma dague a soif ! »
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
Fantôme : « Hoooou... »
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
Squelette : « Clac clac clac. »
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
Troll : « Troll écraser ! »
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
Aria boit Potion de soin (+40 PV). Aria [97/220 PV, ATK 16] ~ Feu
Aria frappe Troll - Feu x1.0 - 27 dégâts (critique !)
  Troll [17/80 PV, ATK 15] ~ Poison
  Troll riposte pour 19 dégâts. Aria [78/220 PV, ATK 16] ~ Feu
Le Troll régénère 5 PV. Troll [22/80 PV, ATK 15] ~ Poison
Aria frappe Troll - Feu x1.0 - 27 dégâts (critique !)
  Troll [0/80 PV, ATK 15] ~ Poison
Troll est vaincu !
Butin : Massue de troll (120 po) ** Rare - Une belle trouvaille.

--- Dragon [120/120 PV, ATK 20] ~ Feu apparaît ---
Dragon : « Brûlez, misérables ! »
Dragon vole au-dessus du sol (esquive 15 %).
Aria frappe Dragon - Feu x1.0 - 8 dégâts
  Dragon [112/120 PV, ATK 20] ~ Feu
  Dragon riposte pour 16 dégâts. Aria [62/220 PV, ATK 16] ~ Feu
Le Dragon régénère 8 PV. Dragon [120/120 PV, ATK 20] ~ Feu
Aria boit Potion de soin (+40 PV). Aria [102/220 PV, ATK 16] ~ Feu
Aria frappe Dragon - Feu x1.0 - 8 dégâts
  Dragon [112/120 PV, ATK 20] ~ Feu
  Dragon riposte pour 16 dégâts. Aria [86/220 PV, ATK 16] ~ Feu
Le Dragon régénère 8 PV. Dragon [120/120 PV, ATK 20] ~ Feu
Aria frappe Dragon - Feu x1.0 - 24 dégâts (critique !)
  Dragon [96/120 PV, ATK 20] ~ Feu
  Dragon riposte pour 16 dégâts. Aria [70/220 PV, ATK 16] ~ Feu
Le Dragon régénère 8 PV. Dragon [104/120 PV, ATK 20] ~ Feu
Aria frappe Dragon - Feu x1.0 - 8 dégâts
  Dragon [96/120 PV, ATK 20] ~ Feu
  Dragon riposte pour 16 dégâts. Aria [54/220 PV, ATK 16] ~ Feu
Le Dragon régénère 8 PV. Dragon [104/120 PV, ATK 20] ~ Feu
Aria frappe Dragon - Feu x1.0 - 8 dégâts
  Dragon [96/120 PV, ATK 20] ~ Feu
  Dragon riposte pour 16 dégâts. Aria [38/220 PV, ATK 16] ~ Feu
Le Dragon régénère 8 PV. Dragon [104/120 PV, ATK 20] ~ Feu
Aria frappe Dragon - Feu x1.0 - 8 dégâts
  Dragon [96/120 PV, ATK 20] ~ Feu
  Dragon riposte pour 16 dégâts. Aria [22/220 PV, ATK 16] ~ Feu
Le Dragon régénère 8 PV. Dragon [104/120 PV, ATK 20] ~ Feu
Aria frappe Dragon - Feu x1.0 - 8 dégâts
  Dragon [96/120 PV, ATK 20] ~ Feu
  Dragon riposte pour 16 dégâts. Aria [6/220 PV, ATK 16] ~ Feu
Le Dragon régénère 8 PV. Dragon [104/120 PV, ATK 20] ~ Feu
Aria frappe Dragon - Feu x1.0 - 8 dégâts
  Dragon [96/120 PV, ATK 20] ~ Feu
  Dragon riposte pour 16 dégâts. Aria [0/220 PV, ATK 16] ~ Feu
Le Dragon régénère 8 PV. Dragon [104/120 PV, ATK 20] ~ Feu

=== Fin de l'exploration ===
Monstres vaincus : 4 / 5
Par ordre alphabétique : Fantôme, Gobelin, Squelette, Troll
Or récolté : 135 po
Potions restantes : 0 (0 po)
```

## Critères d'acceptation

- La console affiche exactement la sortie attendue.
- Le code respecte l'organisation en packages décrite plus haut.
- Le calcul des dégâts ne connaît ni l'attaquant, ni aucune sorte de monstre
  précise.
- Ajouter une nouvelle façon d'encaisser les coups ne demande de toucher ni au
  calcul des dégâts ni aux personnages existants.
- Deux sortes de personnages peuvent partager la même façon d'encaisser.
- Donner une capacité (voler, se régénérer, laisser un butin) à une sorte de monstre
  ne demande pas de toucher aux autres sortes.
- Le butin se ramasse de la même façon sur un monstre et sur un coffre.
- Ajouter un élément ou une rareté ne demande de toucher qu'à la définition des
  éléments ou des raretés.
- Changer l'élément d'Aria ne demande pas de créer une nouvelle sorte de personnage.
- Une attaque, un butin, une potion ou un personnage invalide ne peut pas exister.
- Un sac de chaînes de caractères ne compile pas.
- Soigner d'un nombre de PV et soigner avec une potion s'écrivent de la même façon
  du point de vue de l'appelant.
- Aucune valeur partagée ne change au cours de la partie.
- Une règle commune n'est écrite qu'une fois.

## Bonus

Vérifier par des tests unitaires, sans lancer de combat :
- les exemples de calcul des dégâts ;
- la valeur et l'égalité des butins ;
- la valeur totale d'un sac, l'ordre de retrait et le refus de prendre dans un sac
  vide ;
- les deux façons de soigner et la potion bue sous 30 % des PV.
