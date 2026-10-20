# Correction : le donjon, héritage et composition

## Organisation

```
src/main/java/be/ifapme/dungeon
├── Main.java                assemble les objets et lance l'exploration
├── domain                   les règles du jeu, sans console
│   ├── behavior             façons d'encaisser et effets de fin de tour
│   ├── character            les personnages
│   ├── combat               le calcul des dégâts
│   └── loot                 butin, coffre et ce que laisse un vaincu
├── application              le déroulement de l'exploration
└── infrastructure           l'affichage console
src/test/java                les tests, dans les mêmes packages
lib                          JUnit (console standalone)
```

Les dépendances vont dans un seul sens : `infrastructure` → `application` → `domain`.

## Commandes

```bash
javac -d out $(find src/main/java -name '*.java')
java -cp out be.ifapme.dungeon.Main

javac -d test-out -cp out:lib/junit-platform-console-standalone-6.1.3.jar $(find src/test/java -name '*.java')
java -jar lib/junit-platform-console-standalone-6.1.3.jar execute -cp out:test-out --scan-class-path
```

Dans VS Code, ouvrir ce dossier comme racine : `.vscode/settings.json` déclare les
sources, les tests et JUnit.
