# Déroulé, suite : du code de la classe à la sortie complète

Point de départ : la branche `26-11-03` (régénération, Golem et combat contre la liste faits).
Les fichiers nouveaux sont donnés en entier, les fichiers modifiés en `diff`
(`+` à ajouter, `-` à retirer). Tout reste dans `src/main/java`, sans package, comme en classe.

Chaque étape a été compilée et lancée en Java 25 ; les blocs « Sortie obtenue » sont la vraie sortie.

```bash
javac -d out src/main/java/*.java && java -cp out Main
```

| Étape | Thème | Nouveaux fichiers |
| - | - | - |
| 0 | Remettre le code à niveau | `HalvingProtection` |
| 1 | Ce que laisse un vaincu | `Drop`, `LootDrop`, `Lootable`, `Looter`, `NoDrop`, `ShieldDrop` |
| 2 | Le coffre | `Chest` |
| 3 | Le bilan |  |

---

## Étape 0 : Remettre le code à niveau

**Question à poser** : Relisez l'énoncé ligne par ligne : qu'est-ce que notre programme affiche ou accepte de différent ?

**Ce qu'il faut retenir**

- `null` testé avant `isEmpty` (sinon NullPointerException), et `isBlank` pour refuser « » ;
- l'énoncé refuse les valeurs **négatives**, pas 0 : `< 0` au lieu de `<= 0` ;
- `takeDamage` et `heal` ignorent aussi les montants négatifs : `<= 0` au lieu de `== 0` ;
- le minimum de 1 s'applique **avant** la protection (« Les dégâts » de l'énoncé) ;
- « Squelette » et « Grrrooonnnk. » comme dans l'énoncé, `HavingProtection` renommée `HalvingProtection` (« halving » = diviser par deux) ;
- la ligne vide avant `--- ... apparaît ---`.

**Nouveau** `HalvingProtection.java`

```java
public class HalvingProtection implements Protection {
    public int reduce(int damage) {
        return Math.max(1, damage / 2);
    }
}
```

**Modifié** `ArmorProtection.java`

```diff
@@ -3,5 +3,5 @@
 
     public ArmorProtection(int shield) {
-        if (shield <= 0) {
+        if (shield < 0) {
             throw new IllegalArgumentException("Shield value cannot be less than 0.");
         }
```

**Modifié** `Character.java`

```diff
@@ -10,5 +10,5 @@
     public Character(String name, int maxHealth, int attackPower, int defense, Protection protection,
             TurnEffect turnEffect) {
-        if (name.isEmpty() || name == null) {
+        if (name == null || name.isBlank()) {
             throw new IllegalArgumentException("Name cannot be empty.");
         }
@@ -18,9 +18,9 @@
         }
 
-        if (attackPower <= 0) {
+        if (attackPower < 0) {
             throw new IllegalArgumentException("AttackPower value cannot be less than 0.");
         }
 
-        if (defense <= 0) {
+        if (defense < 0) {
             throw new IllegalArgumentException("Defense value cannot be less than 0.");
         }
@@ -52,5 +52,5 @@
 
     public void heal(int amount) {
-        if (amount == 0) {
+        if (amount <= 0) {
             return;
         }
@@ -64,5 +64,5 @@
 
     public void takeDamage(int amount) {
-        if (amount == 0) {
+        if (amount <= 0) {
             return;
         }
```

**Modifié** `DamageCalculator.java`

```diff
@@ -1,5 +1,5 @@
 public class DamageCalculator {
     public int computeDamage(Character attacker, Character defenser) {
-        return Math.max(1, defenser.absorb(attacker.getAttackPower() - defenser.getDefense()));
+        return defenser.absorb(Math.max(1, attacker.getAttackPower() - defenser.getDefense()));
     }
 }
```

**Modifié** `Fight.java`

```diff
@@ -1,4 +1,5 @@
 public class Fight {
     public void fight(Hero hero, Character monster, DamageCalculator damageCalculator) {
+        IO.println("");
         IO.println("--- " + monster + " apparaît ---");
         IO.println(monster.getName() + " : " + monster.getBattleCry());
```

**Modifié** `Golem.java`

```diff
@@ -1,10 +1,10 @@
 public class Golem extends Character {
     public Golem() {
-        super("Golem", 70, 12, 4, new HavingProtection(), new Regeneration(3));
+        super("Golem", 70, 12, 4, new HalvingProtection(), new Regeneration(3));
     }
 
     @Override
     public String getBattleCry() {
-        return "Grrrooonnnk";
+        return "Grrrooonnnk.";
     }
 }
```

**Modifié** `Loot.java`

```diff
@@ -4,9 +4,9 @@
 
     public Loot(String name, int goldValue) {
-        if (name.isEmpty() || name == null) {
+        if (name == null || name.isBlank()) {
             throw new IllegalArgumentException("Name cannot be empty.");
         }
 
-        if (goldValue <= 0) {
+        if (goldValue < 0) {
             throw new IllegalArgumentException("Gold value cannot be less than 0.");
         }
```

**Modifié** `Main.java`

```diff
@@ -3,5 +3,4 @@
 public class Main {
     public static void main(String[] args) {
-        // Loot chest = new Loot("chest", 30);
         List<Character> monsters = List.of(new Gobelin(), new Skeleton(), new Troll(), new Golem());
         Hero aria = new Hero();
```

**Modifié** `Regeneration.java`

```diff
@@ -15,8 +15,4 @@
 
     public void apply(Healable target) {
-        // if (target.isAlive() && target.isWounded()) {
-        // target.heal(this.amount);
-        // }
-
         if (!target.isAlive() || !target.isWounded()) {
             return;
```

**Modifié** `Skeleton.java`

```diff
@@ -1,5 +1,5 @@
 public class Skeleton extends Character {
     public Skeleton() {
-        super("Skeleton", 55, 9, 3, new HavingProtection(), new NoTurnEffect());
+        super("Squelette", 55, 9, 3, new HalvingProtection(), new NoTurnEffect());
     }
 
```

**Supprimé** `HavingProtection.java` (renommé ci-dessus).

**Sortie obtenue**

```
Aria [200/200 PV, ATK 20] entre dans le donjon.

--- Gobelin [40/40 PV, ATK 7] apparaît ---
Gobelin : Mon trésor, à moi !
Aria frappe Gobelin pour 18 dégâts. Gobelin [22/40 PV, ATK 7]
Gobelin riposte pour 3 dégâts. Aria [197/200 PV, ATK 20]
...
Gobelin est vaincu !
Squelette est vaincu !
Troll est vaincu !
```

**À nommer** : Le programme suit l'énoncé à la lettre. Le Golem fait encore 8 dégâts : Aria n'a pas de bouclier.

---

## Étape 1 : Ce que laisse un vaincu

**Question à poser** : Laisser écrire `if (monster instanceof Skeleton) ...` dans `Fight`. Puis : « Et si demain le Golem laisse aussi un bouclier ? » Relire la colonne « Laisse » : rien, un butin, un bouclier. Trois cas d'un même comportement, comme `Protection`.

**Ce qu'il faut retenir**

- `Drop` est une interface, exactement comme `Protection` et `TurnEffect` : chaque monstre reçoit le sien dans `super(...)` ;
- deux rôles, deux petites interfaces : `Looter` (celui qui ramasse : Aria) et `Lootable` (celui qui laisse : le monstre) ;
- le `Drop` ne connaît ni `Hero` ni `Skeleton` : il reçoit « quelqu'un qui ramasse » et « quelqu'un qui laisse » ;
- `Fight` ne teste aucune sorte : une seule ligne `monster.getDrop().giveTo(hero, monster)`.

**Nouveau** `Drop.java`

```java
public interface Drop {
    void giveTo(Looter looter, Lootable source);
}
```

**Nouveau** `LootDrop.java`

```java
public class LootDrop implements Drop {
    private Loot loot;

    public LootDrop(Loot loot) {
        if (loot == null) {
            throw new IllegalArgumentException("Loot value cannot be null.");
        }

        this.loot = loot;
    }

    public void giveTo(Looter looter, Lootable source) {
        looter.collect(this.loot);
        IO.println("Butin : " + this.loot);
    }
}
```

**Nouveau** `Lootable.java`

```java
public interface Lootable {
    String getName();

    Drop getDrop();
}
```

**Nouveau** `Looter.java`

```java
public interface Looter {
    String getName();

    void collect(Loot loot);

    void equip(Protection shield);
}
```

**Nouveau** `NoDrop.java`

```java
public class NoDrop implements Drop {
    public void giveTo(Looter looter, Lootable source) {
    }
}
```

**Nouveau** `ShieldDrop.java`

```java
public class ShieldDrop implements Drop {
    private Protection shield;

    public ShieldDrop(Protection shield) {
        if (shield == null) {
            throw new IllegalArgumentException("Shield value cannot be null.");
        }

        this.shield = shield;
    }

    public void giveTo(Looter looter, Lootable source) {
        looter.equip(this.shield);
        IO.println(looter.getName() + " ramasse le bouclier de " + source.getName() + ".");
    }
}
```

**Modifié** `Character.java`

```diff
@@ -1,3 +1,3 @@
-public abstract class Character implements Healable {
+public abstract class Character implements Healable, Lootable {
     private String name;
     private int health;
@@ -7,7 +7,8 @@
     private Protection protection;
     private TurnEffect turnEffect;
+    private Drop drop;
 
     public Character(String name, int maxHealth, int attackPower, int defense, Protection protection,
-            TurnEffect turnEffect) {
+            TurnEffect turnEffect, Drop drop) {
         if (name == null || name.isBlank()) {
             throw new IllegalArgumentException("Name cannot be empty.");
@@ -34,4 +35,8 @@
         }
 
+        if (drop == null) {
+            throw new IllegalArgumentException("Drop value cannot be null.");
+        }
+
         this.name = name;
         this.maxHealth = maxHealth;
@@ -41,4 +46,5 @@
         this.protection = protection;
         this.turnEffect = turnEffect;
+        this.drop = drop;
     }
 
@@ -75,8 +81,22 @@
     }
 
+    public void changeProtection(Protection protection) {
+        if (protection == null) {
+            throw new IllegalArgumentException("Protection value cannot be null.");
+        }
+
+        this.protection = protection;
+    }
+
+    @Override
+    public Drop getDrop() {
+        return this.drop;
+    }
+
     public int getAttackPower() {
         return this.attackPower;
     }
 
+    @Override
     public String getName() {
         return this.name;
```

**Modifié** `Fight.java`

```diff
@@ -23,4 +23,5 @@
             } else {
                 IO.println(monster.getName() + " est vaincu !");
+                monster.getDrop().giveTo(hero, monster);
             }
         }
```

**Modifié** `Gobelin.java`

```diff
@@ -1,5 +1,5 @@
 public class Gobelin extends Character {
     public Gobelin() {
-        super("Gobelin", 40, 7, 2, new NoProtection(), new NoTurnEffect());
+        super("Gobelin", 40, 7, 2, new NoProtection(), new NoTurnEffect(), new LootDrop(new Loot("Dague ébréchée", 15)));
     }
 
```

**Modifié** `Golem.java`

```diff
@@ -1,5 +1,5 @@
 public class Golem extends Character {
     public Golem() {
-        super("Golem", 70, 12, 4, new HalvingProtection(), new Regeneration(3));
+        super("Golem", 70, 12, 4, new HalvingProtection(), new Regeneration(3), new NoDrop());
     }
 
```

**Modifié** `Hero.java`

```diff
@@ -1,5 +1,7 @@
-public class Hero extends Character {
+public class Hero extends Character implements Looter {
+    private int gold;
+
     public Hero() {
-        super("Aria", 200, 20, 4, new NoProtection(), new NoTurnEffect());
+        super("Aria", 200, 20, 4, new NoProtection(), new NoTurnEffect(), new NoDrop());
     }
 
@@ -8,3 +10,17 @@
         return "Pour la lumière !";
     }
+
+    @Override
+    public void collect(Loot loot) {
+        this.gold += loot.getGoldValue();
+    }
+
+    @Override
+    public void equip(Protection shield) {
+        this.changeProtection(shield);
+    }
+
+    public int getGold() {
+        return this.gold;
+    }
 }
```

**Modifié** `Main.java`

```diff
@@ -18,4 +18,6 @@
             }
         }
+
+        IO.println("Or récolté : " + aria.getGold() + " po");
     }
 }
```

**Modifié** `Skeleton.java`

```diff
@@ -1,5 +1,5 @@
 public class Skeleton extends Character {
     public Skeleton() {
-        super("Squelette", 55, 9, 3, new HalvingProtection(), new NoTurnEffect());
+        super("Squelette", 55, 9, 3, new HalvingProtection(), new NoTurnEffect(), new ShieldDrop(new ArmorProtection(2)));
     }
 
```

**Modifié** `Troll.java`

```diff
@@ -1,5 +1,5 @@
 public class Troll extends Character {
     public Troll() {
-        super("Troll", 80, 15, 5, new NoProtection(), new Regeneration(5));
+        super("Troll", 80, 15, 5, new NoProtection(), new Regeneration(5), new LootDrop(new Loot("Massue de troll", 80)));
     }
 
```

**Sortie obtenue**

```
Gobelin est vaincu !
Butin : Dague ébréchée (15 po)
Squelette est vaincu !
Aria ramasse le bouclier de Squelette.
Troll est vaincu !
Butin : Massue de troll (80 po)
Golem est vaincu !
...
Golem riposte pour 6 dégâts. Aria [95/200 PV, ATK 20]
Golem riposte pour 6 dégâts. Aria [89/200 PV, ATK 20]
...
Or récolté : 95 po
```

**À nommer** : Troisième comportement, polymorphisme à la place des `if`, petites interfaces : on dit au `Drop` de se donner, il sait lui-même s'il donne de l'or ou un bouclier.

---

## Étape 2 : Le coffre

**Question à poser** : « Un coffre a-t-il un cri de guerre et une attaque ? » Non : pas d'héritage de `Character`. « Qu'a-t-il en commun avec un Gobelin ? » Il laisse quelque chose : il est `Lootable`.

**Ce qu'il faut retenir**

- `Chest` n'hérite de rien, il implémente seulement `Lootable` ;
- la ligne de pillage servirait deux fois : elle devient la méthode `loot(Looter, Lootable)` de `Fight`, utilisée pour le monstre et pour le coffre.

**Nouveau** `Chest.java`

```java
public class Chest implements Lootable {
    private String name;
    private int durability;
    private Drop drop;

    public Chest(String name, int durability, Drop drop) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        if (durability <= 0) {
            throw new IllegalArgumentException("Durability value cannot be less than 1.");
        }

        if (drop == null) {
            throw new IllegalArgumentException("Drop value cannot be null.");
        }

        this.name = name;
        this.durability = durability;
        this.drop = drop;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Drop getDrop() {
        return this.drop;
    }

    public boolean isOpen() {
        return this.durability == 0;
    }

    public void takeDamage(int amount) {
        if (amount <= 0) {
            return;
        }

        this.durability = Math.max(0, this.durability - amount);
    }

    @Override
    public String toString() {
        return this.name + " [" + this.durability + " de solidité]";
    }
}
```

**Modifié** `Fight.java`

```diff
@@ -23,7 +23,23 @@
             } else {
                 IO.println(monster.getName() + " est vaincu !");
-                monster.getDrop().giveTo(hero, monster);
+                this.loot(hero, monster);
             }
         }
     }
+
+    public void open(Hero hero, Chest chest) {
+        IO.println("");
+        IO.println("--- " + chest + " ---");
+
+        while (!chest.isOpen()) {
+            chest.takeDamage(hero.getAttackPower());
+            IO.println(hero.getName() + " frappe le coffre. " + chest);
+        }
+
+        this.loot(hero, chest);
+    }
+
+    private void loot(Looter looter, Lootable source) {
+        source.getDrop().giveTo(looter, source);
+    }
 }
```

**Modifié** `Main.java`

```diff
@@ -4,4 +4,5 @@
     public static void main(String[] args) {
         List<Character> monsters = List.of(new Gobelin(), new Skeleton(), new Troll(), new Golem());
+        Chest chest = new Chest("Coffre du donjon", 30, new LootDrop(new Loot("Couronne ancienne", 250)));
         Hero aria = new Hero();
         Fight fight = new Fight();
@@ -19,4 +20,8 @@
         }
 
+        if (aria.isAlive()) {
+            fight.open(aria, chest);
+        }
+
         IO.println("Or récolté : " + aria.getGold() + " po");
     }
```

**Sortie obtenue**

```

--- Coffre du donjon [30 de solidité] ---
Aria frappe le coffre. Coffre du donjon [10 de solidité]
Aria frappe le coffre. Coffre du donjon [0 de solidité]
Butin : Couronne ancienne (250 po)
Or récolté : 345 po
```

**À nommer** : Une interface relie deux classes sans lien de parenté : c'est le critère « Le butin se ramasse de la même façon sur un monstre et sur un coffre ».

---

## Étape 3 : Le bilan

**Question à poser** : Qui doit compter les victoires et l'or ? Celle qui les gagne : Aria.

**Ce qu'il faut retenir**

- `Hero` compte ses victoires comme elle compte son or ;
- `Main` ne fait qu'afficher.

**Modifié** `Fight.java`

```diff
@@ -23,4 +23,5 @@
             } else {
                 IO.println(monster.getName() + " est vaincu !");
+                hero.recordVictory();
                 this.loot(hero, monster);
             }
```

**Modifié** `Hero.java`

```diff
@@ -1,4 +1,5 @@
 public class Hero extends Character implements Looter {
     private int gold;
+    private int victories;
 
     public Hero() {
@@ -24,3 +25,11 @@
         return this.gold;
     }
+
+    public void recordVictory() {
+        this.victories++;
+    }
+
+    public int getVictories() {
+        return this.victories;
+    }
 }
```

**Modifié** `Main.java`

```diff
@@ -24,4 +24,7 @@
         }
 
+        IO.println("");
+        IO.println("=== Fin de l'exploration ===");
+        IO.println("Monstres vaincus : " + aria.getVictories() + " / " + monsters.size());
         IO.println("Or récolté : " + aria.getGold() + " po");
     }
```

**Sortie obtenue**

```
--- Coffre du donjon [30 de solidité] ---
Aria frappe le coffre. Coffre du donjon [10 de solidité]
Aria frappe le coffre. Coffre du donjon [0 de solidité]
Butin : Couronne ancienne (250 po)

=== Fin de l'exploration ===
Monstres vaincus : 4 / 4
Or récolté : 345 po

(sortie complète identique à l'énoncé, vérifiée par diff)
```

**À nommer** : Encapsulation : l'état appartient à l'objet qui le fait évoluer.
