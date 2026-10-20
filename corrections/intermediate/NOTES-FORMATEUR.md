# Notes formateur : le donjon, héritage et composition

Règle de conduite : laisser l'étudiant écrire une version, même fausse, puis poser
la question. Ne jamais donner la solution d'une étape avant.

## Déroulé

| # | Faire | Question à poser | C'est bon quand |
| - | - | - | - |
| 1 | `Loot` : nom, valeur, validations, `toString` | « Qu'est-ce qui doit être impossible pour un butin ? » | `Dague ébréchée (15 po)` s'affiche, une valeur négative lève une exception |
| 2 | `Goblin` seul : PV privés, `takeDamage`, `isAlive` | « Qui a le droit de mettre ses PV à -50 ? » | 500 dégâts le laissent à 0 PV |
| 3 | `Skeleton` par copier-coller, puis remonter le commun dans `Character` abstrait | « Si la règle des PV change, combien de fichiers tu touches ? » | `new Character(...)` ne compile pas, chaque sorte a son cri |
| 4 | Premier combat Aria contre Gobelin, tout dans `Main` | (accepté pour l'instant) | les lignes du Gobelin sont identiques à l'énoncé |
| 5 | Sortir le calcul dans `DamageCalculator` | « Comment tu testes les 18 dégâts sans lancer le combat ? » | les 4 exemples du tableau des dégâts sont justes |
| 6 | Interface `Protection` (normal, moitié) reçue par le constructeur | « Le Golem encaisse aussi la moitié. Tu ajoutes un `if` ? » | 8 dégâts sur le Squelette, ATK 1 donne 1 |
| 7 | Interface `TurnEffect` (aucun, `Regeneration(n)`) | « Le Golem régénère aussi. Tu copies la méthode du Troll ? » | Golem blessé de 10 remonte à 63 |
| 8 | Écrire le Golem | « Combien de `if` dans le Golem ? » (zéro) | `Golem` = un `super(...)` et un cri |
| 9 | Interface `Drop` (rien, butin, bouclier) + `Looter` implémenté par Aria | « Et si demain le Golem laisse aussi un bouclier ? » | aucun `instanceof`, le Golem fait 6 dégâts à Aria au lieu de 8 |
| 10 | `Chest` + interface `Lootable` partagée avec les personnages | « Un coffre a-t-il un cri et une attaque ? » | une seule méthode ramasse sur un monstre et sur le coffre |
| 11 | Couches : `Exploration` + `BattleLog` + `ConsoleBattleLog`, `Main` assemble | « Si on affiche le combat sur une page web, combien de classes du jeu changent ? » | aucun `IO.println` hors `infrastructure`, sortie identique |
| 12 | Tests unitaires | « Quelle règle n'est vérifiée par aucun test ? » | tous les tests verts |

## À dire au bon moment

- Après 3 : héritage = ce qu'un personnage **est**.
- Après 8 : composition = ce qu'il **a**, et ça peut changer (le bouclier). Le Golem
  en est la preuve.
- Après 9 : on demande à l'objet de faire, on ne teste pas ce qu'il est.
- Fin de séance : un nouveau cas = une nouvelle classe, pas une classe modifiée.

## Où s'arrêter

- En difficulté : étape 8.
- Dans le rythme : étape 10.
- En avance : 11 et 12, puis ajouter un effet « épines » sans toucher à l'existant.

## Pièges fréquents

| On voit | On demande |
| - | - |
| `instanceof` ou test sur le nom dans le combat | « Et quand on ajoute un monstre de plus ? » |
| Une sous-classe par combinaison | « Combien de classes pour 3 protections et 2 effets ? » |
| Attributs `public`, setters partout | « Qui a le droit de casser cet objet ? » |
| `HalvingProtection` renvoie 0 | relire « minimum 1 » avec ATK 1 contre Squelette |
| `Chest extends Character` | « Un coffre a-t-il des PV et un cri ? » |
| `IO.println` dans `Regeneration` | « Et sans console ? » |
| `package` différent du dossier | « Dans quel dossier est le fichier ? » |
