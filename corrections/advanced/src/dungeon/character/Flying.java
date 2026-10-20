package dungeon.character;

public interface Flying {
    default int dodgeChance() {
        return 30;
    }

    default String movementDescription() {
        return "vole au-dessus du sol";
    }
}
