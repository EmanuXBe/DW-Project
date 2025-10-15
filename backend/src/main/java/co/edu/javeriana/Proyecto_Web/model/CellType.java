package co.edu.javeriana.Proyecto_Web.model;

public enum CellType {
    WATER(' '), // Agua - navegable
    WALL('X'), // Pared - destruye barco
    START('P'), // Partida - inicio
    FINISH('M'); // Meta - fin

    private final char symbol;

    CellType(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public static CellType fromChar(char c) {
        for (CellType type : CellType.values()) {
            if (type.symbol == c) {
                return type;
            }
        }
        return WATER; // Default
    }
}
