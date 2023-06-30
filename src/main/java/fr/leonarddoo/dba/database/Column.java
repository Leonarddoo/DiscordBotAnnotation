package fr.leonarddoo.dba.database;

import java.util.List;

public record Column(String name, String type, List<ColumnAttribute> attributes) {
}

