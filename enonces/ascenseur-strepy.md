# L'ascenseur de Strépy-Thieu : des rôles plutôt que des familles

## Contexte

Sur le canal du Centre, l'ascenseur à bateaux de Strépy-Thieu fait franchir aux
bateaux un dénivelé de 73 mètres, dans un grand bac rempli d'eau. Au pied de
l'ascenseur, un guichet voit passer des choses qui n'ont rien en commun : des
péniches, des voiliers, des bateaux de plaisance, mais aussi des conteneurs posés
sur des barges et des bouées repêchées par le personnel.

Certains paient un péage, certains doivent être inspectés, certains les deux, d'autres
rien du tout. Le guichet doit traiter tout ce monde de la même façon, sans jamais se
demander à quoi il a affaire.

## Tout ce qui se présente

- A un nom et un poids en tonnes.
- Refuse d'exister avec un nom vide ou un poids nul ou négatif.
- S'affiche dans le journal avec son nom, sa sorte et son poids :
  `Marie-Louise (péniche, 1250 t)`.

## Les rôles

**Payer un péage**

| Sorte | Péage |
| - | - |
| Péniche | 1 € par tranche complète de 10 t (1250 t → 125 €) |
| Voilier | forfait de 12 € |
| Bateau de plaisance | forfait de 30 € |

**Être inspecté**

| Sorte | Conforme si |
| - | - |
| Péniche | ses documents sont à jour |
| Conteneur | son poids pesé est égal à son poids déclaré |

Un conteneur a donc deux poids : celui qu'on déclare et celui qu'on pèse. Son poids
« officiel », celui qui s'affiche et qui compte dans le bac, est le poids pesé. Un
conteneur refuse d'exister avec un poids déclaré nul ou négatif.

Une bouée ne paie rien et n'est pas inspectée.

## Le guichet

Chaque élément de la file passe au guichet, dans l'ordre :

1. S'il doit être inspecté, il l'est d'abord. S'il n'est pas conforme, il est refusé
   avec la raison, ne paie rien et ne monte pas.
2. S'il paie un péage, il paie.
3. Il embarque dans le bac. Si son poids ferait dépasser la capacité du bac, le bac
   monte d'abord avec ce qu'il contient, puis un nouveau bac vide l'accueille.

Quand la file est vide, le dernier bac monte s'il n'est pas vide. Le bilan donne le
nombre de montées, d'éléments montés, de refusés et le total des péages encaissés.

La capacité du bac est de 1300 t. Le journal commence par l'annoncer.

## La file d'attente

| Ordre | Nom | Sorte | Poids | Détail |
| - | - | - | - | - |
| 1 | Marie-Louise | péniche | 1250 t | documents à jour |
| 2 | Zéphyr | voilier | 8 t | |
| 3 | Balise B-7 | bouée | 1 t | |
| 4 | MSKU 4471 | conteneur | 34 t pesées | 30 t déclarées |
| 5 | Belle d'Haulchin | bateau de plaisance | 25 t | |
| 6 | Sambre | péniche | 900 t | documents expirés |
| 7 | TGHU 0912 | conteneur | 20 t pesées | 20 t déclarées |

## Sortie attendue

```
=== Ascenseur de Strépy-Thieu : bac de 1300 t ===

Marie-Louise (péniche, 1250 t) se présente.
  Inspection : conforme.
  Péage : 125 €.
  Embarque. Bac : 1250/1300 t.
Zéphyr (voilier, 8 t) se présente.
  Péage : 12 €.
  Embarque. Bac : 1258/1300 t.
Balise B-7 (bouée, 1 t) se présente.
  Embarque. Bac : 1259/1300 t.
MSKU 4471 (conteneur, 34 t) se présente.
  Inspection : non conforme (34 t pesées pour 30 t déclarées). Refusé.
Belle d'Haulchin (bateau de plaisance, 25 t) se présente.
  Péage : 30 €.
  Embarque. Bac : 1284/1300 t.
Sambre (péniche, 900 t) se présente.
  Inspection : non conforme (documents expirés). Refusé.
TGHU 0912 (conteneur, 20 t) se présente.
  Inspection : conforme.
  Bac plein : montée n°1, 4 à bord, 1284 t.
  Embarque. Bac : 20/1300 t.

Dernière montée : montée n°2, 1 à bord, 20 t.

=== Bilan ===
Montées : 2
Éléments montés : 5
Refusés : 2
Péages encaissés : 167 €
```

## Critères d'acceptation

- La console affiche exactement la sortie attendue.
- Il n'existe **aucune classe abstraite** : seulement des interfaces et des classes
  qui les implémentent.
- Le mot `instanceof` n'apparaît nulle part, et aucun `if` ne teste la sorte d'un
  élément.
- Chaque interface reste petite : trois méthodes au plus.
- C'est l'élément qui sait ce qu'il doit faire au guichet : le guichet ne lui demande
  jamais ce qu'il est.
- Ajouter une nouvelle sorte (par exemple un kayak qui paie 2 € et n'est pas inspecté)
  ne demande de toucher ni au guichet, ni au bac, ni aux sortes existantes.
- Un élément invalide ne peut pas exister.

## Bonus

Vérifier par des tests unitaires, sans faire passer toute la file : le péage d'une
péniche (dont un poids qui ne tombe pas juste sur une tranche de 10 t), l'inspection
d'un conteneur conforme et non conforme, la montée du bac quand il est plein, et le
refus des valeurs invalides.
