# Déroulé pas à pas : le donjon, héritage et composition

Le code à écrire avec les étudiants, étape par étape. Chaque étape compile et se lance.
Les fichiers nouveaux sont donnés en entier. Les fichiers modifiés sont donnés en
`diff` : les lignes `+` sont à ajouter, les lignes `-` à retirer.

Tous les chemins partent de `src/main/java/be/ifapme/dungeon/`.

Lancer après chaque étape :

```bash
javac -d out $(find src/main/java -name '*.java') && java -cp out be.ifapme.dungeon.Main
```

| Étape | Thème | Nouveaux fichiers |
| - | - | - |
| 1 | Le butin | `Main`, `Loot` |
| 2 | Un Gobelin, tout seul | `Goblin` |
| 3 | Faire apparaître le parent | `Character`, `Hero`, `Skeleton` |
| 4 | Premier combat, tout dans Main |  |
| 5 | Sortir le calcul des dégâts | `DamageCalculator` |
| 6 | La façon d'encaisser | `HalvingProtection`, `NoProtection`, `Protection` |
| 7 | L'effet de fin de tour | `Healable`, `NoEffect`, `Regeneration`, `TurnEffect`, `Troll` |
| 8 | Le Golem | `Golem` |
| 9 | Ce que laisse un vaincu | `AnnouncedLooter`, `ArmorProtection`, `Drop`, `LootDrop`, `Looter`, `NoDrop`, `ShieldDrop` |
| 10 | Le coffre et le bilan | `Chest`, `Lootable` |
| 11 | Sortir le combat de Main, en couches | `AnnouncedLooter`, `BattleLog`, `Exploration`, `ConsoleBattleLog` |

---

## Étape 1 : Le butin

**Question à poser** : Qu'est-ce qui doit être impossible pour un butin ?

**Nouveau** `Main.java`

```java
package be.ifapme.dungeon;

import be.ifapme.dungeon.domain.loot.Loot;

public class Main {
    public static void main(String[] args) {
        IO.println(new Loot("Dague ébréchée", 15));
        try {
            new Loot("Dague ébréchée", -1);
        } catch (IllegalArgumentException exception) {
            IO.println("Refusé : " + exception.getMessage());
        }
    }
}
```

**Nouveau** `domain/loot/Loot.java`

```java
package be.ifapme.dungeon.domain.loot;

public class Loot {
    private final String name;
    private final int goldValue;

    public Loot(String name, int goldValue) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Un butin doit avoir un nom.");
        }
        if (goldValue < 0) {
            throw new IllegalArgumentException("La valeur d'un butin ne peut pas être négative.");
        }
        this.name = name;
        this.goldValue = goldValue;
    }

    public String getName() {
        return this.name;
    }

    public int getGoldValue() {
        return this.goldValue;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.goldValue + " po)";
    }
}
```

**Vérification**

```
Dague ébréchée (15 po)
Refusé : La valeur d'un butin ne peut pas être négative.
```

**À nommer** : Encapsulation : l'objet refuse lui-même d'être invalide.

---

## Étape 2 : Un Gobelin, tout seul

**Question à poser** : Qui a le droit de mettre les PV du Gobelin à -50 ?

**Nouveau** `domain/character/Goblin.java`

```java
package be.ifapme.dungeon.domain.character;

public class Goblin {
    private final String name;
    private final int maxHealth;
    private final int attackPower;
    private final int defense;
    private int health;

    public Goblin() {
        this.name = "Gobelin";
        this.maxHealth = 40;
        this.attackPower = 7;
        this.defense = 2;
        this.health = this.maxHealth;
    }

    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.health;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void takeDamage(int amount) {
        if (amount <= 0) {
            return;
        }
        this.health = Math.max(0, this.health - amount);
    }

    @Override
    public String toString() {
        return this.name + " [" + this.health + "/" + this.maxHealth + " PV, ATK " + this.attackPower + "]";
    }
}
```

**Modifié** `Main.java`

```diff
@@ -1,14 +1,14 @@
 package be.ifapme.dungeon;
 
-import be.ifapme.dungeon.domain.loot.Loot;
+import be.ifapme.dungeon.domain.character.Goblin;
 
 public class Main {
     public static void main(String[] args) {
-        IO.println(new Loot("Dague ébréchée", 15));
-        try {
-            new Loot("Dague ébréchée", -1);
-        } catch (IllegalArgumentException exception) {
-            IO.println("Refusé : " + exception.getMessage());
-        }
+        Goblin goblin = new Goblin();
+        IO.println(goblin);
+        goblin.takeDamage(18);
+        IO.println(goblin);
+        goblin.takeDamage(500);
+        IO.println(goblin + " debout ? " + goblin.isAlive());
     }
 }
```

**Vérification**

```
Gobelin [40/40 PV, ATK 7]
Gobelin [22/40 PV, ATK 7]
Gobelin [0/40 PV, ATK 7] debout ? false
```

**À nommer** : Attributs privés, état protégé par les méthodes.

---

## Étape 3 : Faire apparaître le parent

**Question à poser** : Écrire le Squelette en copiant le Gobelin. Puis : « si la règle des PV change, combien de fichiers tu touches ? » et « c'est quoi, un personnage sans sorte ? »

**Nouveau** `domain/character/Character.java`

```java
package be.ifapme.dungeon.domain.character;

public abstract class Character {
    private final String name;
    private final int maxHealth;
    private final int attackPower;
    private final int defense;
    private int health;

    protected Character(String name, int maxHealth, int attackPower, int defense) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Le nom d'un personnage ne peut pas être vide.");
        }
        if (maxHealth <= 0) {
            throw new IllegalArgumentException("Les points de vie maximum doivent être positifs.");
        }
        if (attackPower < 0) {
            throw new IllegalArgumentException("La puissance d'attaque ne peut pas être négative.");
        }
        if (defense < 0) {
            throw new IllegalArgumentException("La défense ne peut pas être négative.");
        }
        this.name = name;
        this.maxHealth = maxHealth;
        this.attackPower = attackPower;
        this.defense = defense;
        this.health = maxHealth;
    }

    public abstract String getBattleCry();

    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getAttackPower() {
        return this.attackPower;
    }

    public int getDefense() {
        return this.defense;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void takeDamage(int amount) {
        if (amount <= 0) {
            return;
        }
        this.health = Math.max(0, this.health - amount);
    }

    @Override
    public String toString() {
        return this.name + " [" + this.health + "/" + this.maxHealth + " PV, ATK " + this.attackPower + "]";
    }
}
```

**Nouveau** `domain/character/Hero.java`

```java
package be.ifapme.dungeon.domain.character;

public class Hero extends Character {
    public Hero(String name, int maxHealth, int attackPower, int defense) {
        super(name, maxHealth, attackPower, defense);
    }

    @Override
    public String getBattleCry() {
        return "Pour la lumière !";
    }
}
```

**Nouveau** `domain/character/Skeleton.java`

```java
package be.ifapme.dungeon.domain.character;

public class Skeleton extends Character {
    public Skeleton() {
        super("Squelette", 55, 9, 3);
    }

    @Override
    public String getBattleCry() {
        return "Clac, clac, clac...";
    }
}
```

**Modifié** `Main.java`

```diff
@@ -1,14 +1,17 @@
 package be.ifapme.dungeon;
 
+import java.util.List;
+
+import be.ifapme.dungeon.domain.character.Character;
 import be.ifapme.dungeon.domain.character.Goblin;
+import be.ifapme.dungeon.domain.character.Hero;
+import be.ifapme.dungeon.domain.character.Skeleton;
 
 public class Main {
     public static void main(String[] args) {
-        Goblin goblin = new Goblin();
-        IO.println(goblin);
-        goblin.takeDamage(18);
-        IO.println(goblin);
-        goblin.takeDamage(500);
-        IO.println(goblin + " debout ? " + goblin.isAlive());
+        List<Character> characters = List.of(new Hero("Aria", 200, 20, 4), new Goblin(), new Skeleton());
+        for (Character character : characters) {
+            IO.println(character + " : " + character.getBattleCry());
+        }
     }
 }
```

**Modifié** `domain/character/Goblin.java`

```diff
@@ -1,41 +1,12 @@
 package be.ifapme.dungeon.domain.character;
 
-public class Goblin {
-    private final String name;
-    private final int maxHealth;
-    private final int attackPower;
-    private final int defense;
-    private int health;
-
+public class Goblin extends Character {
     public Goblin() {
-        this.name = "Gobelin";
-        this.maxHealth = 40;
-        this.attackPower = 7;
-        this.defense = 2;
-        this.health = this.maxHealth;
-    }
-
-    public String getName() {
-        return this.name;
-    }
-
-    public int getHealth() {
-        return this.health;
-    }
-
-    public boolean isAlive() {
-        return this.health > 0;
-    }
-
-    public void takeDamage(int amount) {
-        if (amount <= 0) {
-            return;
-        }
-        this.health = Math.max(0, this.health - amount);
+        super("Gobelin", 40, 7, 2);
     }
 
     @Override
-    public String toString() {
-        return this.name + " [" + this.health + "/" + this.maxHealth + " PV, ATK " + this.attackPower + "]";
+    public String getBattleCry() {
+        return "Mon trésor, à moi !";
     }
 }
```

**Vérification**

```
Aria [200/200 PV, ATK 20] : Pour la lumière !
Gobelin [40/40 PV, ATK 7] : Mon trésor, à moi !
Squelette [55/55 PV, ATK 9] : Clac, clac, clac...
```

**À nommer** : Héritage, classe abstraite, méthode abstraite, `protected`. `new Character(...)` ne compile pas.

---

## Étape 4 : Premier combat, tout dans Main

**Question à poser** : Faites tourner les premières lignes de la sortie attendue. (Tout dans `Main` est accepté pour l'instant.)

**Modifié** `Main.java`

```diff
@@ -1,16 +1,31 @@
 package be.ifapme.dungeon;
 
-import java.util.List;
-
-import be.ifapme.dungeon.domain.character.Character;
 import be.ifapme.dungeon.domain.character.Goblin;
 import be.ifapme.dungeon.domain.character.Hero;
-import be.ifapme.dungeon.domain.character.Skeleton;
 
 public class Main {
     public static void main(String[] args) {
-        List<Character> characters = List.of(new Hero("Aria", 200, 20, 4), new Goblin(), new Skeleton());
-        for (Character character : characters) {
-            IO.println(character + " : " + character.getBattleCry());
+        Hero aria = new Hero("Aria", 200, 20, 4);
+        Goblin goblin = new Goblin();
+
+        IO.println(aria + " entre dans le donjon.");
+        IO.println("");
+        IO.println("--- " + goblin + " apparaît ---");
+        IO.println(goblin.getName() + " : " + goblin.getBattleCry());
+
+        while (goblin.isAlive() && aria.isAlive()) {
+            int damage = Math.max(1, aria.getAttackPower() - goblin.getDefense());
+            goblin.takeDamage(damage);
+            IO.println(aria.getName() + " frappe " + goblin.getName() + " pour " + damage + " dégâts. " + goblin);
+
+            if (goblin.isAlive()) {
+                int riposte = Math.max(1, goblin.getAttackPower() - aria.getDefense());
+                aria.takeDamage(riposte);
+                IO.println(goblin.getName() + " riposte pour " + riposte + " dégâts. " + aria);
+            }
+        }
+
+        if (!goblin.isAlive()) {
+            IO.println(goblin.getName() + " est vaincu !");
         }
     }
```

**Vérification**

```
Les lignes du Gobelin de la sortie attendue, à l'identique.
```

**À nommer** : Rien à nommer : on prépare la question de l'étape suivante.

---

## Étape 5 : Sortir le calcul des dégâts

**Question à poser** : Comment tu testes les 18 dégâts sans lancer tout le combat ? Et on ajoute le Squelette : tu recopies la boucle ?

**Nouveau** `domain/combat/DamageCalculator.java`

```java
package be.ifapme.dungeon.domain.combat;

import be.ifapme.dungeon.domain.character.Character;

public class DamageCalculator {
    private static final int MINIMUM_DAMAGE = 1;

    public int computeDamage(Character attacker, Character defender) {
        return Math.max(MINIMUM_DAMAGE, attacker.getAttackPower() - defender.getDefense());
    }
}
```

**Modifié** `Main.java`

```diff
@@ -1,31 +1,46 @@
 package be.ifapme.dungeon;
 
+import java.util.List;
+
+import be.ifapme.dungeon.domain.character.Character;
 import be.ifapme.dungeon.domain.character.Goblin;
 import be.ifapme.dungeon.domain.character.Hero;
+import be.ifapme.dungeon.domain.character.Skeleton;
+import be.ifapme.dungeon.domain.combat.DamageCalculator;
 
 public class Main {
     public static void main(String[] args) {
+        DamageCalculator calculator = new DamageCalculator();
         Hero aria = new Hero("Aria", 200, 20, 4);
-        Goblin goblin = new Goblin();
+        List<Character> monsters = List.of(new Goblin(), new Skeleton());
 
         IO.println(aria + " entre dans le donjon.");
+        for (Character monster : monsters) {
+            if (!aria.isAlive()) {
+                break;
+            }
+            fight(aria, monster, calculator);
+        }
+    }
+
+    private static void fight(Hero hero, Character monster, DamageCalculator calculator) {
         IO.println("");
-        IO.println("--- " + goblin + " apparaît ---");
-        IO.println(goblin.getName() + " : " + goblin.getBattleCry());
+        IO.println("--- " + monster + " apparaît ---");
+        IO.println(monster.getName() + " : " + monster.getBattleCry());
 
-        while (goblin.isAlive() && aria.isAlive()) {
-            int damage = Math.max(1, aria.getAttackPower() - goblin.getDefense());
-            goblin.takeDamage(damage);
-            IO.println(aria.getName() + " frappe " + goblin.getName() + " pour " + damage + " dégâts. " + goblin);
+        while (monster.isAlive() && hero.isAlive()) {
+            int damage = calculator.computeDamage(hero, monster);
+            monster.takeDamage(damage);
+            IO.println(hero.getName() + " frappe " + monster.getName() + " pour " + damage + " dégâts. " + monster);
 
-            if (goblin.isAlive()) {
-                int riposte = Math.max(1, goblin.getAttackPower() - aria.getDefense());
-                aria.takeDamage(riposte);
-                IO.println(goblin.getName() + " riposte pour " + riposte + " dégâts. " + aria);
+            if (monster.isAlive()) {
+                int riposte = calculator.computeDamage(monster, hero);
+                hero.takeDamage(riposte);
+                IO.println(monster.getName() + " riposte pour " + riposte + " dégâts. " + hero);
             }
         }
 
-        if (!goblin.isAlive()) {
-            IO.println(goblin.getName() + " est vaincu !");
+        if (!monster.isAlive()) {
+            IO.println(monster.getName() + " est vaincu !");
         }
     }
```

**Vérification**

```
Aria fait 17 dégâts au Squelette : faux, il encaisse la moitié. C'est l'étape suivante.
```

**À nommer** : Responsabilité unique. Le combat devient une méthode qui marche pour n'importe quel `Character`.

---

## Étape 6 : La façon d'encaisser

**Question à poser** : Laisser écrire un `if (defender instanceof Skeleton)` dans le calculateur. Puis : « le Golem encaisse aussi la moitié, tu ajoutes un `||` ? »

**Nouveau** `domain/behavior/HalvingProtection.java`

```java
package be.ifapme.dungeon.domain.behavior;

public class HalvingProtection implements Protection {
    private static final int MINIMUM_DAMAGE = 1;

    @Override
    public int reduce(int damage) {
        return Math.max(MINIMUM_DAMAGE, damage / 2);
    }
}
```

**Nouveau** `domain/behavior/NoProtection.java`

```java
package be.ifapme.dungeon.domain.behavior;

public class NoProtection implements Protection {
    @Override
    public int reduce(int damage) {
        return damage;
    }
}
```

**Nouveau** `domain/behavior/Protection.java`

```java
package be.ifapme.dungeon.domain.behavior;

public interface Protection {
    int reduce(int damage);
}
```

**Modifié** `domain/character/Character.java`

```diff
@@ -1,3 +1,5 @@
 package be.ifapme.dungeon.domain.character;
+
+import be.ifapme.dungeon.domain.behavior.Protection;
 
 public abstract class Character {
@@ -6,7 +8,8 @@
     private final int attackPower;
     private final int defense;
+    private Protection protection;
     private int health;
 
-    protected Character(String name, int maxHealth, int attackPower, int defense) {
+    protected Character(String name, int maxHealth, int attackPower, int defense, Protection protection) {
         if (name == null || name.isBlank()) {
             throw new IllegalArgumentException("Le nom d'un personnage ne peut pas être vide.");
@@ -21,8 +24,12 @@
             throw new IllegalArgumentException("La défense ne peut pas être négative.");
         }
+        if (protection == null) {
+            throw new IllegalArgumentException("Un personnage doit avoir une protection.");
+        }
         this.name = name;
         this.maxHealth = maxHealth;
         this.attackPower = attackPower;
         this.defense = defense;
+        this.protection = protection;
         this.health = maxHealth;
     }
@@ -50,4 +57,8 @@
     }
 
+    public int absorb(int damage) {
+        return this.protection.reduce(damage);
+    }
+
     public boolean isAlive() {
         return this.health > 0;
```

**Modifié** `domain/character/Goblin.java`

```diff
@@ -1,7 +1,9 @@
 package be.ifapme.dungeon.domain.character;
+
+import be.ifapme.dungeon.domain.behavior.NoProtection;
 
 public class Goblin extends Character {
     public Goblin() {
-        super("Gobelin", 40, 7, 2);
+        super("Gobelin", 40, 7, 2, new NoProtection());
     }
 
```

**Modifié** `domain/character/Hero.java`

```diff
@@ -1,7 +1,9 @@
 package be.ifapme.dungeon.domain.character;
+
+import be.ifapme.dungeon.domain.behavior.NoProtection;
 
 public class Hero extends Character {
     public Hero(String name, int maxHealth, int attackPower, int defense) {
-        super(name, maxHealth, attackPower, defense);
+        super(name, maxHealth, attackPower, defense, new NoProtection());
     }
 
```

**Modifié** `domain/character/Skeleton.java`

```diff
@@ -1,7 +1,9 @@
 package be.ifapme.dungeon.domain.character;
+
+import be.ifapme.dungeon.domain.behavior.HalvingProtection;
 
 public class Skeleton extends Character {
     public Skeleton() {
-        super("Squelette", 55, 9, 3);
+        super("Squelette", 55, 9, 3, new HalvingProtection());
     }
 
```

**Modifié** `domain/combat/DamageCalculator.java`

```diff
@@ -7,5 +7,6 @@
 
     public int computeDamage(Character attacker, Character defender) {
-        return Math.max(MINIMUM_DAMAGE, attacker.getAttackPower() - defender.getDefense());
+        int damage = Math.max(MINIMUM_DAMAGE, attacker.getAttackPower() - defender.getDefense());
+        return defender.absorb(damage);
     }
 }
```

**Vérification**

```
Aria fait 8 dégâts au Squelette.
```

**À nommer** : Interface, composition, polymorphisme : le calculateur ne sait pas quelle protection il a en face.

---

## Étape 7 : L'effet de fin de tour

**Question à poser** : Le Troll se régénère. Si l'étudiant redéfinit `endOfTurn` dans `Troll` : « le Golem régénère aussi, tu copies ? »

**Nouveau** `domain/behavior/Healable.java`

```java
package be.ifapme.dungeon.domain.behavior;

public interface Healable {
    boolean isAlive();

    boolean isWounded();

    void heal(int amount);
}
```

**Nouveau** `domain/behavior/NoEffect.java`

```java
package be.ifapme.dungeon.domain.behavior;

public class NoEffect implements TurnEffect {
    @Override
    public void apply(Healable target) {
    }
}
```

**Nouveau** `domain/behavior/Regeneration.java`

```java
package be.ifapme.dungeon.domain.behavior;

public class Regeneration implements TurnEffect {
    private final int amount;

    public Regeneration(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Une régénération rend au moins 1 PV.");
        }
        this.amount = amount;
    }

    @Override
    public void apply(Healable target) {
        if (!target.isAlive() || !target.isWounded()) {
            return;
        }
        target.heal(this.amount);
    }
}
```

**Nouveau** `domain/behavior/TurnEffect.java`

```java
package be.ifapme.dungeon.domain.behavior;

public interface TurnEffect {
    void apply(Healable target);
}
```

**Nouveau** `domain/character/Troll.java`

```java
package be.ifapme.dungeon.domain.character;

import be.ifapme.dungeon.domain.behavior.NoProtection;
import be.ifapme.dungeon.domain.behavior.Regeneration;

public class Troll extends Character {
    public Troll() {
        super("Troll", 80, 15, 5, new NoProtection(), new Regeneration(5));
    }

    @Override
    public String getBattleCry() {
        return "Troll écraser !";
    }
}
```

**Modifié** `Main.java`

```diff
@@ -7,4 +7,5 @@
 import be.ifapme.dungeon.domain.character.Hero;
 import be.ifapme.dungeon.domain.character.Skeleton;
+import be.ifapme.dungeon.domain.character.Troll;
 import be.ifapme.dungeon.domain.combat.DamageCalculator;
 
@@ -13,5 +14,5 @@
         DamageCalculator calculator = new DamageCalculator();
         Hero aria = new Hero("Aria", 200, 20, 4);
-        List<Character> monsters = List.of(new Goblin(), new Skeleton());
+        List<Character> monsters = List.of(new Goblin(), new Skeleton(), new Troll());
 
         IO.println(aria + " entre dans le donjon.");
@@ -39,4 +40,11 @@
                 IO.println(monster.getName() + " riposte pour " + riposte + " dégâts. " + hero);
             }
+
+            int healthBeforeEffect = monster.getHealth();
+            monster.endOfTurn();
+            int regenerated = monster.getHealth() - healthBeforeEffect;
+            if (regenerated > 0) {
+                IO.println(monster.getName() + " régénère " + regenerated + " PV. " + monster);
+            }
         }
 
```

**Modifié** `domain/character/Character.java`

```diff
@@ -1,15 +1,18 @@
 package be.ifapme.dungeon.domain.character;
 
+import be.ifapme.dungeon.domain.behavior.Healable;
 import be.ifapme.dungeon.domain.behavior.Protection;
+import be.ifapme.dungeon.domain.behavior.TurnEffect;
 
-public abstract class Character {
+public abstract class Character implements Healable {
     private final String name;
     private final int maxHealth;
     private final int attackPower;
     private final int defense;
+    private final TurnEffect turnEffect;
     private Protection protection;
     private int health;
 
-    protected Character(String name, int maxHealth, int attackPower, int defense, Protection protection) {
+    protected Character(String name, int maxHealth, int attackPower, int defense, Protection protection, TurnEffect turnEffect) {
         if (name == null || name.isBlank()) {
             throw new IllegalArgumentException("Le nom d'un personnage ne peut pas être vide.");
@@ -27,4 +30,7 @@
             throw new IllegalArgumentException("Un personnage doit avoir une protection.");
         }
+        if (turnEffect == null) {
+            throw new IllegalArgumentException("Un personnage doit avoir un effet de fin de tour.");
+        }
         this.name = name;
         this.maxHealth = maxHealth;
@@ -32,4 +38,5 @@
         this.defense = defense;
         this.protection = protection;
+        this.turnEffect = turnEffect;
         this.health = maxHealth;
     }
@@ -61,6 +68,12 @@
     }
 
+    @Override
     public boolean isAlive() {
         return this.health > 0;
+    }
+
+    @Override
+    public boolean isWounded() {
+        return this.health < this.maxHealth;
     }
 
@@ -73,4 +86,16 @@
 
     @Override
+    public void heal(int amount) {
+        if (amount <= 0) {
+            return;
+        }
+        this.health = Math.min(this.maxHealth, this.health + amount);
+    }
+
+    public void endOfTurn() {
+        this.turnEffect.apply(this);
+    }
+
+    @Override
     public String toString() {
         return this.name + " [" + this.health + "/" + this.maxHealth + " PV, ATK " + this.attackPower + "]";
```

**Modifié** `domain/character/Goblin.java`

```diff
@@ -2,8 +2,9 @@
 
 import be.ifapme.dungeon.domain.behavior.NoProtection;
+import be.ifapme.dungeon.domain.behavior.NoEffect;
 
 public class Goblin extends Character {
     public Goblin() {
-        super("Gobelin", 40, 7, 2, new NoProtection());
+        super("Gobelin", 40, 7, 2, new NoProtection(), new NoEffect());
     }
 
```

**Modifié** `domain/character/Hero.java`

```diff
@@ -2,8 +2,9 @@
 
 import be.ifapme.dungeon.domain.behavior.NoProtection;
+import be.ifapme.dungeon.domain.behavior.NoEffect;
 
 public class Hero extends Character {
     public Hero(String name, int maxHealth, int attackPower, int defense) {
-        super(name, maxHealth, attackPower, defense, new NoProtection());
+        super(name, maxHealth, attackPower, defense, new NoProtection(), new NoEffect());
     }
 
```

**Modifié** `domain/character/Skeleton.java`

```diff
@@ -2,8 +2,9 @@
 
 import be.ifapme.dungeon.domain.behavior.HalvingProtection;
+import be.ifapme.dungeon.domain.behavior.NoEffect;
 
 public class Skeleton extends Character {
     public Skeleton() {
-        super("Squelette", 55, 9, 3, new HalvingProtection());
+        super("Squelette", 55, 9, 3, new HalvingProtection(), new NoEffect());
     }
 
```

**Vérification**

```
Troll régénère 5 PV. Troll [70/80 PV, ATK 15]
```

**À nommer** : Réutilisation par composition. `Healable` : une petite interface, `Regeneration` n'a besoin que de trois méthodes du personnage.

---

## Étape 8 : Le Golem

**Question à poser** : Écris le Golem. Combien de `if` ? (zéro)

**Nouveau** `domain/character/Golem.java`

```java
package be.ifapme.dungeon.domain.character;

import be.ifapme.dungeon.domain.behavior.HalvingProtection;
import be.ifapme.dungeon.domain.behavior.Regeneration;

public class Golem extends Character {
    public Golem() {
        super("Golem", 70, 12, 4, new HalvingProtection(), new Regeneration(3));
    }

    @Override
    public String getBattleCry() {
        return "Grrrooonnnk.";
    }
}
```

**Modifié** `Main.java`

```diff
@@ -5,4 +5,5 @@
 import be.ifapme.dungeon.domain.character.Character;
 import be.ifapme.dungeon.domain.character.Goblin;
+import be.ifapme.dungeon.domain.character.Golem;
 import be.ifapme.dungeon.domain.character.Hero;
 import be.ifapme.dungeon.domain.character.Skeleton;
@@ -14,5 +15,5 @@
         DamageCalculator calculator = new DamageCalculator();
         Hero aria = new Hero("Aria", 200, 20, 4);
-        List<Character> monsters = List.of(new Goblin(), new Skeleton(), new Troll());
+        List<Character> monsters = List.of(new Goblin(), new Skeleton(), new Troll(), new Golem());
 
         IO.println(aria + " entre dans le donjon.");
```

**Vérification**

```
Golem régénère 3 PV. Golem [65/70 PV, ATK 12]
```

**À nommer** : Moment fort : la composition marche, le Golem n'a aucune règle.

---

## Étape 9 : Ce que laisse un vaincu

**Question à poser** : Laisser écrire `if (monster instanceof Skeleton)`. Puis relire le critère « sans jamais tester de quelle sorte ils sont » : « et si le Golem laisse aussi un bouclier ? »

**Nouveau** `AnnouncedLooter.java`

```java
package be.ifapme.dungeon;

import be.ifapme.dungeon.domain.behavior.Protection;
import be.ifapme.dungeon.domain.character.Hero;
import be.ifapme.dungeon.domain.loot.Loot;
import be.ifapme.dungeon.domain.loot.Looter;

class AnnouncedLooter implements Looter {
    private final Hero hero;
    private final String source;

    AnnouncedLooter(Hero hero, String source) {
        this.hero = hero;
        this.source = source;
    }

    @Override
    public void collect(Loot loot) {
        this.hero.collect(loot);
        IO.println("Butin : " + loot);
    }

    @Override
    public void equip(Protection shield) {
        this.hero.equip(shield);
        IO.println(this.hero.getName() + " ramasse le bouclier de " + this.source + ".");
    }
}
```

**Nouveau** `domain/behavior/ArmorProtection.java`

```java
package be.ifapme.dungeon.domain.behavior;

public class ArmorProtection implements Protection {
    private static final int MINIMUM_DAMAGE = 1;

    private final int armor;

    public ArmorProtection(int armor) {
        if (armor < 0) {
            throw new IllegalArgumentException("Une armure ne peut pas être négative.");
        }
        this.armor = armor;
    }

    @Override
    public int reduce(int damage) {
        return Math.max(MINIMUM_DAMAGE, damage - this.armor);
    }
}
```

**Nouveau** `domain/loot/Drop.java`

```java
package be.ifapme.dungeon.domain.loot;

public interface Drop {
    void giveTo(Looter looter);
}
```

**Nouveau** `domain/loot/LootDrop.java`

```java
package be.ifapme.dungeon.domain.loot;

public class LootDrop implements Drop {
    private final Loot loot;

    public LootDrop(Loot loot) {
        if (loot == null) {
            throw new IllegalArgumentException("Un butin à laisser ne peut pas être vide.");
        }
        this.loot = loot;
    }

    @Override
    public void giveTo(Looter looter) {
        looter.collect(this.loot);
    }
}
```

**Nouveau** `domain/loot/Looter.java`

```java
package be.ifapme.dungeon.domain.loot;

import be.ifapme.dungeon.domain.behavior.Protection;

public interface Looter {
    void collect(Loot loot);

    void equip(Protection shield);
}
```

**Nouveau** `domain/loot/NoDrop.java`

```java
package be.ifapme.dungeon.domain.loot;

public class NoDrop implements Drop {
    @Override
    public void giveTo(Looter looter) {
    }
}
```

**Nouveau** `domain/loot/ShieldDrop.java`

```java
package be.ifapme.dungeon.domain.loot;

import be.ifapme.dungeon.domain.behavior.Protection;

public class ShieldDrop implements Drop {
    private final Protection shield;

    public ShieldDrop(Protection shield) {
        if (shield == null) {
            throw new IllegalArgumentException("Un bouclier à laisser ne peut pas être vide.");
        }
        this.shield = shield;
    }

    @Override
    public void giveTo(Looter looter) {
        looter.equip(this.shield);
    }
}
```

**Modifié** `Main.java`

```diff
@@ -24,4 +24,5 @@
             fight(aria, monster, calculator);
         }
+        IO.println("Or récolté : " + aria.getGold() + " po");
     }
 
@@ -52,4 +53,5 @@
         if (!monster.isAlive()) {
             IO.println(monster.getName() + " est vaincu !");
+            monster.getDrop().giveTo(new AnnouncedLooter(hero, monster.getName()));
         }
     }
```

**Modifié** `domain/character/Character.java`

```diff
@@ -4,4 +4,5 @@
 import be.ifapme.dungeon.domain.behavior.Protection;
 import be.ifapme.dungeon.domain.behavior.TurnEffect;
+import be.ifapme.dungeon.domain.loot.Drop;
 
 public abstract class Character implements Healable {
@@ -11,8 +12,9 @@
     private final int defense;
     private final TurnEffect turnEffect;
+    private final Drop drop;
     private Protection protection;
     private int health;
 
-    protected Character(String name, int maxHealth, int attackPower, int defense, Protection protection, TurnEffect turnEffect) {
+    protected Character(String name, int maxHealth, int attackPower, int defense, Protection protection, TurnEffect turnEffect, Drop drop) {
         if (name == null || name.isBlank()) {
             throw new IllegalArgumentException("Le nom d'un personnage ne peut pas être vide.");
@@ -33,4 +35,7 @@
             throw new IllegalArgumentException("Un personnage doit avoir un effet de fin de tour.");
         }
+        if (drop == null) {
+            throw new IllegalArgumentException("Un personnage doit savoir ce qu'il laisse.");
+        }
         this.name = name;
         this.maxHealth = maxHealth;
@@ -39,4 +44,5 @@
         this.protection = protection;
         this.turnEffect = turnEffect;
+        this.drop = drop;
         this.health = maxHealth;
     }
@@ -46,4 +52,8 @@
     public String getName() {
         return this.name;
+    }
+
+    public Drop getDrop() {
+        return this.drop;
     }
 
@@ -66,4 +76,11 @@
     public int absorb(int damage) {
         return this.protection.reduce(damage);
+    }
+
+    public void changeProtection(Protection protection) {
+        if (protection == null) {
+            throw new IllegalArgumentException("Un personnage doit avoir une protection.");
+        }
+        this.protection = protection;
     }
 
```

**Modifié** `domain/character/Goblin.java`

```diff
@@ -1,10 +1,12 @@
 package be.ifapme.dungeon.domain.character;
 
+import be.ifapme.dungeon.domain.behavior.NoEffect;
 import be.ifapme.dungeon.domain.behavior.NoProtection;
-import be.ifapme.dungeon.domain.behavior.NoEffect;
+import be.ifapme.dungeon.domain.loot.Loot;
+import be.ifapme.dungeon.domain.loot.LootDrop;
 
 public class Goblin extends Character {
     public Goblin() {
-        super("Gobelin", 40, 7, 2, new NoProtection(), new NoEffect());
+        super("Gobelin", 40, 7, 2, new NoProtection(), new NoEffect(), new LootDrop(new Loot("Dague ébréchée", 15)));
     }
 
```

**Modifié** `domain/character/Golem.java`

```diff
@@ -3,8 +3,9 @@
 import be.ifapme.dungeon.domain.behavior.HalvingProtection;
 import be.ifapme.dungeon.domain.behavior.Regeneration;
+import be.ifapme.dungeon.domain.loot.NoDrop;
 
 public class Golem extends Character {
     public Golem() {
-        super("Golem", 70, 12, 4, new HalvingProtection(), new Regeneration(3));
+        super("Golem", 70, 12, 4, new HalvingProtection(), new Regeneration(3), new NoDrop());
     }
 
```

**Modifié** `domain/character/Hero.java`

```diff
@@ -1,10 +1,16 @@
 package be.ifapme.dungeon.domain.character;
 
+import be.ifapme.dungeon.domain.behavior.NoEffect;
 import be.ifapme.dungeon.domain.behavior.NoProtection;
-import be.ifapme.dungeon.domain.behavior.NoEffect;
+import be.ifapme.dungeon.domain.behavior.Protection;
+import be.ifapme.dungeon.domain.loot.Loot;
+import be.ifapme.dungeon.domain.loot.Looter;
+import be.ifapme.dungeon.domain.loot.NoDrop;
 
-public class Hero extends Character {
+public class Hero extends Character implements Looter {
+    private int gold;
+
     public Hero(String name, int maxHealth, int attackPower, int defense) {
-        super(name, maxHealth, attackPower, defense, new NoProtection(), new NoEffect());
+        super(name, maxHealth, attackPower, defense, new NoProtection(), new NoEffect(), new NoDrop());
     }
 
@@ -13,3 +19,17 @@
         return "Pour la lumière !";
     }
+
+    public int getGold() {
+        return this.gold;
+    }
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
 }
```

**Modifié** `domain/character/Skeleton.java`

```diff
@@ -1,10 +1,12 @@
 package be.ifapme.dungeon.domain.character;
 
+import be.ifapme.dungeon.domain.behavior.ArmorProtection;
 import be.ifapme.dungeon.domain.behavior.HalvingProtection;
 import be.ifapme.dungeon.domain.behavior.NoEffect;
+import be.ifapme.dungeon.domain.loot.ShieldDrop;
 
 public class Skeleton extends Character {
     public Skeleton() {
-        super("Squelette", 55, 9, 3, new HalvingProtection(), new NoEffect());
+        super("Squelette", 55, 9, 3, new HalvingProtection(), new NoEffect(), new ShieldDrop(new ArmorProtection(2)));
     }
 
```

**Modifié** `domain/character/Troll.java`

```diff
@@ -3,8 +3,10 @@
 import be.ifapme.dungeon.domain.behavior.NoProtection;
 import be.ifapme.dungeon.domain.behavior.Regeneration;
+import be.ifapme.dungeon.domain.loot.Loot;
+import be.ifapme.dungeon.domain.loot.LootDrop;
 
 public class Troll extends Character {
     public Troll() {
-        super("Troll", 80, 15, 5, new NoProtection(), new Regeneration(5));
+        super("Troll", 80, 15, 5, new NoProtection(), new Regeneration(5), new LootDrop(new Loot("Massue de troll", 80)));
     }
 
```

**Vérification**

```
Aria ramasse le bouclier de Squelette.
...
Golem riposte pour 6 dégâts. (au lieu de 8)
```

**À nommer** : Troisième comportement (`Drop`), polymorphisme à la place des `if`, Tell Don't Ask : le `Drop` choisit lui-même s'il appelle `collect` ou `equip`.

---

## Étape 10 : Le coffre et le bilan

**Question à poser** : Un coffre n'est pas un personnage. Qu'a-t-il en commun avec un Gobelin ?

**Nouveau** `domain/loot/Chest.java`

```java
package be.ifapme.dungeon.domain.loot;

public class Chest implements Lootable {
    private final String name;
    private final Drop drop;
    private int durability;

    public Chest(String name, int durability, Drop drop) {
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

**Nouveau** `domain/loot/Lootable.java`

```java
package be.ifapme.dungeon.domain.loot;

public interface Lootable {
    String getName();

    Drop getDrop();
}
```

**Modifié** `Main.java`

```diff
@@ -10,4 +10,8 @@
 import be.ifapme.dungeon.domain.character.Troll;
 import be.ifapme.dungeon.domain.combat.DamageCalculator;
+import be.ifapme.dungeon.domain.loot.Chest;
+import be.ifapme.dungeon.domain.loot.Loot;
+import be.ifapme.dungeon.domain.loot.LootDrop;
+import be.ifapme.dungeon.domain.loot.Lootable;
 
 public class Main {
@@ -16,4 +20,5 @@
         Hero aria = new Hero("Aria", 200, 20, 4);
         List<Character> monsters = List.of(new Goblin(), new Skeleton(), new Troll(), new Golem());
+        Chest chest = new Chest("Coffre du donjon", 30, new LootDrop(new Loot("Couronne ancienne", 250)));
 
         IO.println(aria + " entre dans le donjon.");
@@ -24,4 +29,11 @@
             fight(aria, monster, calculator);
         }
+        if (aria.isAlive()) {
+            open(aria, chest);
+        }
+
+        IO.println("");
+        IO.println("=== Fin de l'exploration ===");
+        IO.println("Monstres vaincus : " + aria.getVictories() + " / " + monsters.size());
         IO.println("Or récolté : " + aria.getGold() + " po");
     }
@@ -53,6 +65,21 @@
         if (!monster.isAlive()) {
             IO.println(monster.getName() + " est vaincu !");
-            monster.getDrop().giveTo(new AnnouncedLooter(hero, monster.getName()));
+            hero.recordVictory();
+            loot(hero, monster);
         }
     }
+
+    private static void open(Hero hero, Chest chest) {
+        IO.println("");
+        IO.println("--- " + chest + " ---");
+        while (!chest.isOpen()) {
+            chest.takeDamage(hero.getAttackPower());
+            IO.println(hero.getName() + " frappe le coffre. " + chest);
+        }
+        loot(hero, chest);
+    }
+
+    private static void loot(Hero hero, Lootable source) {
+        source.getDrop().giveTo(new AnnouncedLooter(hero, source.getName()));
+    }
 }
```

**Modifié** `domain/character/Character.java`

```diff
@@ -5,6 +5,7 @@
 import be.ifapme.dungeon.domain.behavior.TurnEffect;
 import be.ifapme.dungeon.domain.loot.Drop;
+import be.ifapme.dungeon.domain.loot.Lootable;
 
-public abstract class Character implements Healable {
+public abstract class Character implements Healable, Lootable {
     private final String name;
     private final int maxHealth;
@@ -50,8 +51,10 @@
     public abstract String getBattleCry();
 
+    @Override
     public String getName() {
         return this.name;
     }
 
+    @Override
     public Drop getDrop() {
         return this.drop;
```

**Modifié** `domain/character/Hero.java`

```diff
@@ -9,4 +9,5 @@
 
 public class Hero extends Character implements Looter {
+    private int victories;
     private int gold;
 
@@ -18,4 +19,12 @@
     public String getBattleCry() {
         return "Pour la lumière !";
+    }
+
+    public void recordVictory() {
+        this.victories++;
+    }
+
+    public int getVictories() {
+        return this.victories;
     }
 
```

**Vérification**

```
La sortie attendue complète, ligne à ligne.
```

**À nommer** : Une interface relie des classes sans lien de parenté. La sortie est maintenant complète.

---

## Étape 11 : Sortir le combat de Main, en couches

**Question à poser** : Si on affiche le combat sur une page web, combien de classes du jeu changent ?

**Nouveau** `application/AnnouncedLooter.java`

```java
package be.ifapme.dungeon.application;

import be.ifapme.dungeon.domain.behavior.Protection;
import be.ifapme.dungeon.domain.character.Hero;
import be.ifapme.dungeon.domain.loot.Loot;
import be.ifapme.dungeon.domain.loot.Looter;

class AnnouncedLooter implements Looter {
    private final Hero hero;
    private final BattleLog log;
    private final String source;

    AnnouncedLooter(Hero hero, BattleLog log, String source) {
        this.hero = hero;
        this.log = log;
        this.source = source;
    }

    @Override
    public void collect(Loot loot) {
        this.hero.collect(loot);
        this.log.lootCollected(loot);
    }

    @Override
    public void equip(Protection shield) {
        this.hero.equip(shield);
        this.log.shieldPickedUp(this.hero, this.source);
    }
}
```

**Nouveau** `application/BattleLog.java`

```java
package be.ifapme.dungeon.application;

import be.ifapme.dungeon.domain.character.Character;
import be.ifapme.dungeon.domain.character.Hero;
import be.ifapme.dungeon.domain.loot.Chest;
import be.ifapme.dungeon.domain.loot.Loot;

public interface BattleLog {
    void heroEntered(Hero hero);

    void monsterAppeared(Character monster);

    void heroStruck(Hero hero, Character monster, int damage);

    void monsterRetaliated(Character monster, Hero hero, int damage);

    void regenerated(Character character, int amount);

    void monsterDefeated(Character monster);

    void lootCollected(Loot loot);

    void shieldPickedUp(Hero hero, String formerOwner);

    void chestReached(Chest chest);

    void chestStruck(Hero hero, Chest chest);

    void explorationEnded(int victories, int monsterCount, int gold);
}
```

**Nouveau** `application/Exploration.java`

```java
package be.ifapme.dungeon.application;

import java.util.List;

import be.ifapme.dungeon.domain.character.Character;
import be.ifapme.dungeon.domain.character.Hero;
import be.ifapme.dungeon.domain.combat.DamageCalculator;
import be.ifapme.dungeon.domain.loot.Chest;
import be.ifapme.dungeon.domain.loot.Lootable;

public class Exploration {
    private final DamageCalculator calculator;
    private final BattleLog log;

    public Exploration(DamageCalculator calculator, BattleLog log) {
        this.calculator = calculator;
        this.log = log;
    }

    public void explore(Hero hero, List<Character> monsters, Chest chest) {
        this.log.heroEntered(hero);
        for (Character monster : monsters) {
            if (!hero.isAlive()) {
                break;
            }
            this.fight(hero, monster);
        }
        if (hero.isAlive()) {
            this.open(hero, chest);
        }
        this.log.explorationEnded(hero.getVictories(), monsters.size(), hero.getGold());
    }

    private void fight(Hero hero, Character monster) {
        this.log.monsterAppeared(monster);
        while (monster.isAlive() && hero.isAlive()) {
            this.playTurn(hero, monster);
        }
        if (!monster.isAlive()) {
            this.log.monsterDefeated(monster);
            hero.recordVictory();
            this.loot(hero, monster);
        }
    }

    private void playTurn(Hero hero, Character monster) {
        int damage = this.calculator.computeDamage(hero, monster);
        monster.takeDamage(damage);
        this.log.heroStruck(hero, monster, damage);

        if (monster.isAlive()) {
            int riposte = this.calculator.computeDamage(monster, hero);
            hero.takeDamage(riposte);
            this.log.monsterRetaliated(monster, hero, riposte);
        }

        int healthBeforeEffect = monster.getHealth();
        monster.endOfTurn();
        int regenerated = monster.getHealth() - healthBeforeEffect;
        if (regenerated > 0) {
            this.log.regenerated(monster, regenerated);
        }
    }

    private void open(Hero hero, Chest chest) {
        this.log.chestReached(chest);
        while (!chest.isOpen()) {
            chest.takeDamage(hero.getAttackPower());
            this.log.chestStruck(hero, chest);
        }
        this.loot(hero, chest);
    }

    private void loot(Hero hero, Lootable source) {
        source.getDrop().giveTo(new AnnouncedLooter(hero, this.log, source.getName()));
    }
}
```

**Nouveau** `infrastructure/ConsoleBattleLog.java`

```java
package be.ifapme.dungeon.infrastructure;

import be.ifapme.dungeon.application.BattleLog;
import be.ifapme.dungeon.domain.character.Character;
import be.ifapme.dungeon.domain.character.Hero;
import be.ifapme.dungeon.domain.loot.Chest;
import be.ifapme.dungeon.domain.loot.Loot;

public class ConsoleBattleLog implements BattleLog {
    @Override
    public void heroEntered(Hero hero) {
        IO.println(hero + " entre dans le donjon.");
    }

    @Override
    public void monsterAppeared(Character monster) {
        IO.println("");
        IO.println("--- " + monster + " apparaît ---");
        IO.println(monster.getName() + " : " + monster.getBattleCry());
    }

    @Override
    public void heroStruck(Hero hero, Character monster, int damage) {
        IO.println(hero.getName() + " frappe " + monster.getName() + " pour " + damage + " dégâts. " + monster);
    }

    @Override
    public void monsterRetaliated(Character monster, Hero hero, int damage) {
        IO.println(monster.getName() + " riposte pour " + damage + " dégâts. " + hero);
    }

    @Override
    public void regenerated(Character character, int amount) {
        IO.println(character.getName() + " régénère " + amount + " PV. " + character);
    }

    @Override
    public void monsterDefeated(Character monster) {
        IO.println(monster.getName() + " est vaincu !");
    }

    @Override
    public void lootCollected(Loot loot) {
        IO.println("Butin : " + loot);
    }

    @Override
    public void shieldPickedUp(Hero hero, String formerOwner) {
        IO.println(hero.getName() + " ramasse le bouclier de " + formerOwner + ".");
    }

    @Override
    public void chestReached(Chest chest) {
        IO.println("");
        IO.println("--- " + chest + " ---");
    }

    @Override
    public void chestStruck(Hero hero, Chest chest) {
        IO.println(hero.getName() + " frappe le coffre. " + chest);
    }

    @Override
    public void explorationEnded(int victories, int monsterCount, int gold) {
        IO.println("");
        IO.println("=== Fin de l'exploration ===");
        IO.println("Monstres vaincus : " + victories + " / " + monsterCount);
        IO.println("Or récolté : " + gold + " po");
    }
}
```

**Modifié** `Main.java`

```diff
@@ -3,4 +3,5 @@
 import java.util.List;
 
+import be.ifapme.dungeon.application.Exploration;
 import be.ifapme.dungeon.domain.character.Character;
 import be.ifapme.dungeon.domain.character.Goblin;
@@ -13,73 +14,13 @@
 import be.ifapme.dungeon.domain.loot.Loot;
 import be.ifapme.dungeon.domain.loot.LootDrop;
-import be.ifapme.dungeon.domain.loot.Lootable;
+import be.ifapme.dungeon.infrastructure.ConsoleBattleLog;
 
 public class Main {
     public static void main(String[] args) {
-        DamageCalculator calculator = new DamageCalculator();
         Hero aria = new Hero("Aria", 200, 20, 4);
         List<Character> monsters = List.of(new Goblin(), new Skeleton(), new Troll(), new Golem());
         Chest chest = new Chest("Coffre du donjon", 30, new LootDrop(new Loot("Couronne ancienne", 250)));
 
-        IO.println(aria + " entre dans le donjon.");
-        for (Character monster : monsters) {
-            if (!aria.isAlive()) {
-                break;
-            }
-            fight(aria, monster, calculator);
-        }
-        if (aria.isAlive()) {
-            open(aria, chest);
-        }
-
-        IO.println("");
-        IO.println("=== Fin de l'exploration ===");
-        IO.println("Monstres vaincus : " + aria.getVictories() + " / " + monsters.size());
-        IO.println("Or récolté : " + aria.getGold() + " po");
-    }
-
-    private static void fight(Hero hero, Character monster, DamageCalculator calculator) {
-        IO.println("");
-        IO.println("--- " + monster + " apparaît ---");
-        IO.println(monster.getName() + " : " + monster.getBattleCry());
-
-        while (monster.isAlive() && hero.isAlive()) {
-            int damage = calculator.computeDamage(hero, monster);
-            monster.takeDamage(damage);
-            IO.println(hero.getName() + " frappe " + monster.getName() + " pour " + damage + " dégâts. " + monster);
-
-            if (monster.isAlive()) {
-                int riposte = calculator.computeDamage(monster, hero);
-                hero.takeDamage(riposte);
-                IO.println(monster.getName() + " riposte pour " + riposte + " dégâts. " + hero);
-            }
-
-            int healthBeforeEffect = monster.getHealth();
-            monster.endOfTurn();
-            int regenerated = monster.getHealth() - healthBeforeEffect;
-            if (regenerated > 0) {
-                IO.println(monster.getName() + " régénère " + regenerated + " PV. " + monster);
-            }
-        }
-
-        if (!monster.isAlive()) {
-            IO.println(monster.getName() + " est vaincu !");
-            hero.recordVictory();
-            loot(hero, monster);
-        }
-    }
-
-    private static void open(Hero hero, Chest chest) {
-        IO.println("");
-        IO.println("--- " + chest + " ---");
-        while (!chest.isOpen()) {
-            chest.takeDamage(hero.getAttackPower());
-            IO.println(hero.getName() + " frappe le coffre. " + chest);
-        }
-        loot(hero, chest);
-    }
-
-    private static void loot(Hero hero, Lootable source) {
-        source.getDrop().giveTo(new AnnouncedLooter(hero, source.getName()));
+        new Exploration(new DamageCalculator(), new ConsoleBattleLog()).explore(aria, monsters, chest);
     }
 }
```

**Supprimé** `AnnouncedLooter.java` (déplacé ou remplacé ci-dessus).

**Vérification**

```
La même sortie complète. Aucun `IO.println` hors de `infrastructure`.
```

**À nommer** : Couches `domain` / `application` / `infrastructure`, inversion de dépendance (`Exploration` dépend de l'interface `BattleLog`), décorateur (`AnnouncedLooter`).

---

## Étape 12 (bonus) : les tests

**Question à poser** : quelle règle de l'énoncé n'est vérifiée par aucun test ?

Les tests vont dans `src/test/java`, dans le même package que la classe testée.
Trois exemples à écrire ensemble ; la correction complète (82 tests) est dans
`src/test/java/be/ifapme/dungeon/`.

Lancer les tests :

```bash
javac -d test-out -cp out:lib/junit-platform-console-standalone-6.1.3.jar $(find src/test/java -name '*.java')
java -jar lib/junit-platform-console-standalone-6.1.3.jar execute -cp out:test-out --scan-class-path
```

**Une règle testée sans lancer de combat** `domain/combat/DamageCalculatorTest.java`

```java
package be.ifapme.dungeon.domain.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import be.ifapme.dungeon.domain.behavior.ArmorProtection;
import be.ifapme.dungeon.domain.character.Goblin;
import be.ifapme.dungeon.domain.character.Golem;
import be.ifapme.dungeon.domain.character.Hero;
import be.ifapme.dungeon.domain.character.Skeleton;
import be.ifapme.dungeon.domain.character.Troll;

class DamageCalculatorTest {
    private final DamageCalculator calculator = new DamageCalculator();
    private final Hero aria = new Hero("Aria", 200, 20, 4);

    @Test
    void damageIsAttackMinusDefense() {
        assertEquals(18, this.calculator.computeDamage(this.aria, new Goblin()));
    }

    @Test
    void defenderProtectionAppliesAfterDefense() {
        assertEquals(8, this.calculator.computeDamage(this.aria, new Skeleton()));
    }

    @Test
    void shieldOfTheDefenderAppliesAfterDefense() {
        this.aria.changeProtection(new ArmorProtection(2));
        assertEquals(6, this.calculator.computeDamage(new Golem(), this.aria));
    }

    @Test
    void damageIsAtLeastOneBeforeProtection() {
        assertEquals(1, this.calculator.computeDamage(new Hero("Faible", 10, 1, 0), new Troll()));
    }

    @Test
    void damageIsAtLeastOneAfterProtection() {
        assertEquals(1, this.calculator.computeDamage(new Hero("Faible", 10, 1, 0), new Skeleton()));
    }
}
```

**Un faux objet écrit pour le test** `domain/behavior/FakeHealable.java`

```java
package be.ifapme.dungeon.domain.behavior;

class FakeHealable implements Healable {
    private final boolean alive;
    private final boolean wounded;
    private int healed;

    FakeHealable(boolean alive, boolean wounded) {
        this.alive = alive;
        this.wounded = wounded;
    }

    @Override
    public boolean isAlive() {
        return this.alive;
    }

    @Override
    public boolean isWounded() {
        return this.wounded;
    }

    @Override
    public void heal(int amount) {
        this.healed += amount;
    }

    int getHealed() {
        return this.healed;
    }
}
```

**Tester un comportement avec ce faux objet** `domain/behavior/RegenerationTest.java`

```java
package be.ifapme.dungeon.domain.behavior;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class RegenerationTest {
    @Test
    void healsAWoundedTarget() {
        FakeHealable target = new FakeHealable(true, true);
        new Regeneration(5).apply(target);
        assertEquals(5, target.getHealed());
    }

    @Test
    void doesNotHealAnUnhurtTarget() {
        FakeHealable target = new FakeHealable(true, false);
        new Regeneration(5).apply(target);
        assertEquals(0, target.getHealed());
    }

    @Test
    void doesNotHealADeadTarget() {
        FakeHealable target = new FakeHealable(false, true);
        new Regeneration(5).apply(target);
        assertEquals(0, target.getHealed());
    }

    @Test
    void rejectsZeroAmount() {
        assertThrows(IllegalArgumentException.class, () -> new Regeneration(0));
    }
}
```

**À nommer** : test unitaire, doublure de test (`FakeHealable`, `RecordingBattleLog`).
