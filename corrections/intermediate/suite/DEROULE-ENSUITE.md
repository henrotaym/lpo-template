# Déroulé, ensuite : tous les messages au même endroit

À voir **après** `DEROULE-SUITE.md`. Point de départ : le code de la fin de ce déroulé
(étape 3, sortie complète identique à l'énoncé).

Rien ne change à l'écran : chaque étape réorganise le code et la sortie reste identique.
C'est le moment de montrer qu'on peut améliorer la structure sans casser le comportement.

Les fichiers nouveaux sont donnés en entier, les fichiers modifiés en `diff`. Chaque étape a été
compilée, lancée et comparée à la sortie attendue en Java 25.

| Étape | Thème | Nouveaux fichiers |
| - | - | - |
| 4 | Tous les messages au même endroit : BattleLog | `BattleLog`, `ConsoleBattleLog` |
| 5 | Annoncer le butin sans que le Drop parle : AnnouncedLooter | `AnnouncedLooter` |

---

## Étape 4 : Tous les messages au même endroit : BattleLog

**Question à poser** : « Demain on affiche le combat dans une page web au lieu de la console. Combien de fichiers faut-il ouvrir ? » Aujourd'hui : `Fight`, `Main`, `LootDrop`, `ShieldDrop`.

**Ce qu'il faut retenir**

- `BattleLog` liste tout ce qui peut arriver pendant l'exploration ;
- `ConsoleBattleLog` l'implémente avec des `IO.println` ;
- `Fight` reçoit le log dans son constructeur et lui raconte ce qui se passe, sans savoir que c'est une console ;
- `Main` passe aussi par le log ;
- il reste deux `IO.println` hors du log : dans `LootDrop` et `ShieldDrop`. C'est l'étape suivante.

**Nouveau** `BattleLog.java`

```java
public interface BattleLog {
    void heroEntered(Hero hero);

    void monsterAppeared(Character monster);

    void heroStruck(Hero hero, Character monster, int damage);

    void monsterRetaliated(Character monster, Hero hero, int damage);

    void regenerated(Character character, int amount);

    void monsterDefeated(Character monster);

    void heroFell(Hero hero);

    void chestReached(Chest chest);

    void chestStruck(Hero hero, Chest chest);

    void explorationEnded(int victories, int monsterCount, int gold);
}
```

**Nouveau** `ConsoleBattleLog.java`

```java
public class ConsoleBattleLog implements BattleLog {
    public void heroEntered(Hero hero) {
        IO.println(hero + " entre dans le donjon.");
    }

    public void monsterAppeared(Character monster) {
        IO.println("");
        IO.println("--- " + monster + " apparaît ---");
        IO.println(monster.getName() + " : " + monster.getBattleCry());
    }

    public void heroStruck(Hero hero, Character monster, int damage) {
        IO.println(hero.getName() + " frappe " + monster.getName() + " pour " + damage + " dégâts. " + monster);
    }

    public void monsterRetaliated(Character monster, Hero hero, int damage) {
        IO.println(monster.getName() + " riposte pour " + damage + " dégâts. " + hero);
    }

    public void regenerated(Character character, int amount) {
        IO.println(character.getName() + " régénère " + amount + " PV. " + character);
    }

    public void monsterDefeated(Character monster) {
        IO.println(monster.getName() + " est vaincu !");
    }

    public void heroFell(Hero hero) {
        IO.println(hero.getName() + " est morte. " + hero);
    }

    public void chestReached(Chest chest) {
        IO.println("");
        IO.println("--- " + chest + " ---");
    }

    public void chestStruck(Hero hero, Chest chest) {
        IO.println(hero.getName() + " frappe le coffre. " + chest);
    }

    public void explorationEnded(int victories, int monsterCount, int gold) {
        IO.println("");
        IO.println("=== Fin de l'exploration ===");
        IO.println("Monstres vaincus : " + victories + " / " + monsterCount);
        IO.println("Or récolté : " + gold + " po");
    }
}
```

**Modifié** `Fight.java`

```diff
@@ -1,17 +1,20 @@
 public class Fight {
+    private BattleLog log;
+
+    public Fight(BattleLog log) {
+        this.log = log;
+    }
+
     public void fight(Hero hero, Character monster, DamageCalculator damageCalculator) {
-        IO.println("");
-        IO.println("--- " + monster + " apparaît ---");
-        IO.println(monster.getName() + " : " + monster.getBattleCry());
+        this.log.monsterAppeared(monster);
 
         while (hero.isAlive() && monster.isAlive()) {
             int heroDamage = damageCalculator.computeDamage(hero, monster);
             monster.takeDamage(heroDamage);
-            IO.println(hero.getName() + " frappe " + monster.getName() + " pour " + heroDamage + " dégâts. "
-                    + monster);
+            this.log.heroStruck(hero, monster, heroDamage);
             if (monster.isAlive()) {
                 int monsterDamage = damageCalculator.computeDamage(monster, hero);
                 hero.takeDamage(monsterDamage);
-                IO.println(monster.getName() + " riposte pour " + monsterDamage + " dégâts. " + hero);
+                this.log.monsterRetaliated(monster, hero, monsterDamage);
 
                 int beforeEndOfTurnHealth = monster.getHealth();
@@ -19,8 +22,8 @@
                 int regeneratedHealth = monster.getHealth() - beforeEndOfTurnHealth;
                 if (regeneratedHealth > 0) {
-                    IO.println(monster.getName() + " régénère " + regeneratedHealth + " PV. " + monster);
+                    this.log.regenerated(monster, regeneratedHealth);
                 }
             } else {
-                IO.println(monster.getName() + " est vaincu !");
+                this.log.monsterDefeated(monster);
                 hero.recordVictory();
                 this.loot(hero, monster);
@@ -30,10 +33,9 @@
 
     public void open(Hero hero, Chest chest) {
-        IO.println("");
-        IO.println("--- " + chest + " ---");
+        this.log.chestReached(chest);
 
         while (!chest.isOpen()) {
             chest.takeDamage(hero.getAttackPower());
-            IO.println(hero.getName() + " frappe le coffre. " + chest);
+            this.log.chestStruck(hero, chest);
         }
 
```

**Modifié** `Main.java`

```diff
@@ -6,8 +6,9 @@
         Chest chest = new Chest("Coffre du donjon", 30, new LootDrop(new Loot("Couronne ancienne", 250)));
         Hero aria = new Hero();
-        Fight fight = new Fight();
+        BattleLog log = new ConsoleBattleLog();
+        Fight fight = new Fight(log);
         DamageCalculator damageCalculator = new DamageCalculator();
 
-        IO.println(aria + " entre dans le donjon.");
+        log.heroEntered(aria);
 
         for (Character monster : monsters) {
@@ -15,5 +16,5 @@
 
             if (!aria.isAlive()) {
-                IO.println("Aria est morte. " + aria);
+                log.heroFell(aria);
                 break;
             }
@@ -24,8 +25,5 @@
         }
 
-        IO.println("");
-        IO.println("=== Fin de l'exploration ===");
-        IO.println("Monstres vaincus : " + aria.getVictories() + " / " + monsters.size());
-        IO.println("Or récolté : " + aria.getGold() + " po");
+        log.explorationEnded(aria.getVictories(), monsters.size(), aria.getGold());
     }
 }
```

**Vérification**

Sortie identique à l'énoncé (diff vide). `IO.println` reste dans ConsoleBattleLog.java, LootDrop.java et ShieldDrop.java.

**À nommer** : Responsabilité unique : l'affichage a sa propre classe. Dépendre d'une interface (`BattleLog`) plutôt que de la console.

---

## Étape 5 : Annoncer le butin sans que le Drop parle : AnnouncedLooter

**Question à poser** : « Les `Drop` affichent encore leur message. Qui doit prévenir le log qu'un butin est ramassé ? Le `Drop` ? Aria ? » Les deux mélangeraient deux métiers.

**Ce qu'il faut retenir**

- un `Drop` ne fait plus qu'une chose : donner au `Looter`. Il n'affiche plus rien, et n'a donc plus besoin de la source ;
- `AnnouncedLooter` **est** un `Looter` qui enveloppe le vrai (Aria) : il lui transmet `collect` / `equip`, **puis** prévient le log ;
- le `Drop` ne voit pas la différence : pour lui, c'est un `Looter` comme un autre ;
- l'annonce est écrite une seule fois : tout futur `Drop` sera annoncé automatiquement, puisqu'il passe forcément par `collect` ou `equip` ;
- `Fight` choisit d'annoncer : `giveTo(new AnnouncedLooter(looter, log, source))`. Passer `looter` tout seul donnerait un pillage silencieux.

**Nouveau** `AnnouncedLooter.java`

```java
public class AnnouncedLooter implements Looter {
    private Looter looter;
    private BattleLog log;
    private Lootable source;

    public AnnouncedLooter(Looter looter, BattleLog log, Lootable source) {
        this.looter = looter;
        this.log = log;
        this.source = source;
    }

    public String getName() {
        return this.looter.getName();
    }

    public void collect(Loot loot) {
        this.looter.collect(loot);
        this.log.lootCollected(loot);
    }

    public void equip(Protection shield) {
        this.looter.equip(shield);
        this.log.shieldPickedUp(this.looter, this.source);
    }
}
```

**Modifié** `BattleLog.java`

```diff
@@ -14,4 +14,8 @@
     void heroFell(Hero hero);
 
+    void lootCollected(Loot loot);
+
+    void shieldPickedUp(Looter looter, Lootable source);
+
     void chestReached(Chest chest);
 
```

**Modifié** `ConsoleBattleLog.java`

```diff
@@ -30,4 +30,12 @@
     }
 
+    public void lootCollected(Loot loot) {
+        IO.println("Butin : " + loot);
+    }
+
+    public void shieldPickedUp(Looter looter, Lootable source) {
+        IO.println(looter.getName() + " ramasse le bouclier de " + source.getName() + ".");
+    }
+
     public void chestReached(Chest chest) {
         IO.println("");
```

**Modifié** `Drop.java`

```diff
@@ -1,3 +1,3 @@
 public interface Drop {
-    void giveTo(Looter looter, Lootable source);
+    void giveTo(Looter looter);
 }
```

**Modifié** `Fight.java`

```diff
@@ -44,5 +44,5 @@
 
     private void loot(Looter looter, Lootable source) {
-        source.getDrop().giveTo(looter, source);
+        source.getDrop().giveTo(new AnnouncedLooter(looter, this.log, source));
     }
 }
```

**Modifié** `LootDrop.java`

```diff
@@ -10,7 +10,6 @@
     }
 
-    public void giveTo(Looter looter, Lootable source) {
+    public void giveTo(Looter looter) {
         looter.collect(this.loot);
-        IO.println("Butin : " + this.loot);
     }
 }
```

**Modifié** `NoDrop.java`

```diff
@@ -1,4 +1,4 @@
 public class NoDrop implements Drop {
-    public void giveTo(Looter looter, Lootable source) {
+    public void giveTo(Looter looter) {
     }
 }
```

**Modifié** `ShieldDrop.java`

```diff
@@ -10,7 +10,6 @@
     }
 
-    public void giveTo(Looter looter, Lootable source) {
+    public void giveTo(Looter looter) {
         looter.equip(this.shield);
-        IO.println(looter.getName() + " ramasse le bouclier de " + source.getName() + ".");
     }
 }
```

**Vérification**

Sortie identique à l'énoncé (diff vide). `IO.println` n'apparaît plus que dans ConsoleBattleLog.java.

**À nommer** : Décorateur : un objet qui en enveloppe un autre, avec la même interface, pour lui ajouter un comportement.
