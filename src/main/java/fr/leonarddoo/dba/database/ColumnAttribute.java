package fr.leonarddoo.dba.database;

public enum ColumnAttribute {
    NOT_NULL("NOT NULL"),
    UNIQUE("UNIQUE"),
    FOREIGN_KEY("FOREIGN KEY"),
    AUTO_INCREMENT("AUTO_INCREMENT");

    private final String name;

    ColumnAttribute(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}