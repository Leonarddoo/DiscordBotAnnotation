package fr.leonarddoo.dba.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.leonarddoo.dba.exception.DatabaseOperationException;

import java.sql.Connection;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class DatabaseConnection {
    private static HikariDataSource dataSource;
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    /**
     * Initialiser la connexion à la base de données
     * @param url URL de la base de données
     * @param username Nom d'utilisateur
     * @param password Mot de passe
     */
    @SuppressWarnings("unused")
    public static void init(String url, String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        dataSource = new HikariDataSource(config);
    }

    /**
     * Récupérer une connexion à la base de données
     * @return Connexion à la base de données
     * @throws DatabaseOperationException Erreur lors de la récupération de la connexion
     */
    private static Connection getConnection() throws DatabaseOperationException {
        if (dataSource == null) {
            throw new DatabaseOperationException("Connection to database not initialized.");
        }
        try{
            return dataSource.getConnection();
        } catch (SQLException e){
            throw new DatabaseOperationException("Error getting connection to database.");
        }
    }

    /**
     * Créer une table dans la base de données
     * @param tableName Nom de la table
     * @param columns Liste des colonnes
     * @param primaryKeys Liste des clés primaires
     * @throws DatabaseOperationException Erreur lors de la création de la table
     */
    @SuppressWarnings("unused")
    public static void createTableIfNotExists(String tableName, List<Column> columns, List<String> primaryKeys) throws DatabaseOperationException {
        try (Connection conn = getConnection()) {

            // Vérifier si la table existe
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, tableName, null);
            if (tables.next()) {
                LOGGER.log(Level.INFO, "Table " + tableName + " already exists.");
                return;
            }

            // Si la table n'existe pas, la créer
            StringBuilder sb = new StringBuilder();
            sb.append("CREATE TABLE ").append(tableName).append(" (");

            int i = 0;
            for (Column column : columns) {
                sb.append(column.name()).append(" ").append(column.type());
                for (ColumnAttribute attribute : column.attributes()) {
                    sb.append(" ").append(attribute.getName());
                }
                if (i < columns.size() - 1) {
                    sb.append(", ");
                }
                i++;
            }

            if (primaryKeys != null && primaryKeys.size() > 0) {
                sb.append(", PRIMARY KEY (");
                for (int j = 0; j < primaryKeys.size(); j++) {
                    sb.append(primaryKeys.get(j));
                    if (j < primaryKeys.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(")");
            }

            sb.append(")");

            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sb.toString());
                LOGGER.log(Level.INFO, "Table " + tableName + " created successfully.");
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error creating table " + tableName);
        }
    }

    /**
     * Créer une table dans la base de données
     * @param tableName Nom de la table
     * @param columns Liste des colonnes
     * @param primaryKeys Liste des clés primaires
     * @param foreignKeys Liste des clés étrangères
     * @throws DatabaseOperationException Erreur lors de la création de la table
     */
    public static void createTableIfNotExists(String tableName, List<Column> columns, List<String> primaryKeys, List<ForeignKey> foreignKeys) throws DatabaseOperationException {
        try (Connection conn = getConnection()) {
            // Vérifier si la table existe
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, tableName, null);
            if (tables.next()) {
                LOGGER.log(Level.INFO, "Table " + tableName + " already exists.");
                return;
            }

            // Si la table n'existe pas, la créer
            StringBuilder sb = new StringBuilder();
            sb.append("CREATE TABLE ").append(tableName).append(" (");

            int i = 0;
            for (Column column : columns) {
                sb.append(column.name()).append(" ").append(column.type());
                for (ColumnAttribute attribute : column.attributes()) {
                    sb.append(" ").append(attribute.getName());
                }
                if (i < columns.size() - 1) {
                    sb.append(", ");
                }
                i++;
            }

            if (primaryKeys != null && primaryKeys.size() > 0) {
                sb.append(", PRIMARY KEY (");
                for (int j = 0; j < primaryKeys.size(); j++) {
                    sb.append(primaryKeys.get(j));
                    if (j < primaryKeys.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(")");
            }

            if (foreignKeys != null && foreignKeys.size() > 0) {
                for (ForeignKey foreignKey : foreignKeys) {
                    sb.append(", FOREIGN KEY (").append(foreignKey.columnName()).append(") REFERENCES ").append(foreignKey.referencedTable()).append("(").append(foreignKey.columnName()).append(")");
                }
            }

            sb.append(")");

            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sb.toString());
                LOGGER.log(Level.INFO, "Table " + tableName + " created successfully.");
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error creating table " + tableName);
        }
    }


    /**
     * Insert into a table
     * @param tableName the name of the table
     * @param values a map of the values to insert
     * @throws DatabaseOperationException if an error occurs
     */
    @SuppressWarnings("unused")
    public static void insertIntoTable(String tableName, Map<String, Object> values) throws DatabaseOperationException {
        try (Connection conn = getConnection()) {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(tableName).append(" (");
            StringBuilder valuesBuilder = new StringBuilder("VALUES (");
            int i = 0;
            for (String key : values.keySet()) {
                sb.append(key);
                valuesBuilder.append("?");
                if (i < values.size() - 1) {
                    sb.append(", ");
                    valuesBuilder.append(", ");
                }
                i++;
            }
            sb.append(") ");
            valuesBuilder.append(")");

            String sql = sb + valuesBuilder.toString();
            PreparedStatement stmt = conn.prepareStatement(sql);
            i = 1;
            for (Object value : values.values()) {
                stmt.setObject(i, value);
                i++;
            }
            stmt.executeUpdate();
            LOGGER.log(Level.INFO, "Inserted into " + tableName + " successfully.");
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DatabaseOperationException("Duplicate entry not inserted into " + tableName);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error inserting into table " + tableName);
        }
    }

    /**
     * Select all from a table
     * @param tableName the name of the table
     * @return a list of maps containing the rows
     * @throws DatabaseOperationException if an error occurs
     */
    @SuppressWarnings("unused")
    public static List<Map<String, Object>> selectAllFromTable(String tableName) throws DatabaseOperationException {
        List<Map<String, Object>> resultList = new ArrayList<>();

        try (Connection conn = getConnection()){
            String sql = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rsmd.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                resultList.add(row);
            }

            LOGGER.log(Level.INFO, "Selected all from " + tableName + " successfully.");
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error selecting all from table " + tableName);
        }

        return resultList;
    }

    /**
     * Selects all rows from a table where the value of a column is equal to a given value.
     * @param tableName The name of the table to select from.
     * @param columnNames The names of the columns to check.
     * @param columnValues The values to check for.
     * @return A list of maps, each map representing a row in the table.
     * @throws DatabaseOperationException If an error occurs while selecting from the table.
     */
    @SuppressWarnings("unused")
    public static List<Map<String, Object>> selectByColumnValue(String tableName, List<String> columnNames, List<Object> columnValues) throws DatabaseOperationException {
        List<Map<String, Object>> resultList = new ArrayList<>();

        try (Connection conn = getConnection()){
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT * FROM ").append(tableName).append(" WHERE ");
            for (int i = 0; i < columnNames.size(); i++) {
                if (i > 0) {
                    sqlBuilder.append(" AND ");
                }
                sqlBuilder.append(columnNames.get(i)).append(" = ?");
            }
            String sql = sqlBuilder.toString();
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (int i = 0; i < columnValues.size(); i++) {
                stmt.setObject(i + 1, columnValues.get(i));
            }

            ResultSet rs = stmt.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnNameRs = rsmd.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnNameRs, value);
                }
                resultList.add(row);
            }

            LOGGER.log(Level.INFO, "Selected all rows from " + tableName + " where " + columnNames + " = " + columnValues + " successfully.");
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error selecting all rows from table " + tableName + " where " + columnNames + " = " + columnValues);
        }

        return resultList;
    }

    /**
     * Selects a row from a table by its primary key.
     * @param tableName The name of the table.
     * @param idColumnNames The names of the primary key columns.
     * @param idValues The values of the primary key columns.
     * @return A map containing the column names and values of the selected row.
     * @throws DatabaseOperationException If an error occurs while selecting the row.
     */
    @SuppressWarnings("unused")
    public static Map<String, Object> selectById(String tableName, List<String> idColumnNames, List<Object> idValues) throws DatabaseOperationException {
        Map<String, Object> row = new HashMap<>();
        try (Connection conn = getConnection()) {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM ").append(tableName).append(" WHERE ");
            for (int i = 0; i < idColumnNames.size(); i++) {
                query.append(idColumnNames.get(i)).append(" = ?");
                if (i < idColumnNames.size() - 1) {
                    query.append(" AND ");
                }
            }
            try (PreparedStatement stmt = conn.prepareStatement(query.toString())) {
                for (int i = 0; i < idValues.size(); i++) {
                    stmt.setObject(i + 1, idValues.get(i));
                }
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    ResultSetMetaData meta = rs.getMetaData();
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        row.put(meta.getColumnName(i), rs.getObject(i));
                    }
                }
            }
            LOGGER.info("Selected row from table " + tableName + " with id " + idValues + " successfully.");
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error executing SELECT query on table " + tableName);
        }
        return row;
    }

    /**
     * Updates a row in a table.
     * @param tableName Name of the table to update
     * @param data Map of column names and values to update
     * @param conditionColumns List of column names to use in the WHERE clause
     * @param conditionValues List of values to use in the WHERE clause
     * @throws DatabaseOperationException If an error occurs while updating the row
     */
    @SuppressWarnings("unused")
    public static void update(String tableName, Map<String, Object> data, List<String> conditionColumns, List<Object> conditionValues) throws DatabaseOperationException {
        try (Connection conn = getConnection(); PreparedStatement pstmt = createUpdateStatement(conn, tableName, data, conditionColumns, conditionValues)) {
            int rowsUpdated = pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Updated " + rowsUpdated + " row(s) in table " + tableName);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error updating row(s) in table " + tableName);
        }
    }

    /**
     * Creates a PreparedStatement for updating a row in a table.
     * @param conn Connection to use for creating the PreparedStatement
     * @param tableName Name of the table to update
     * @param data Map of column names and values to update
     * @param conditionColumns List of column names to use in the WHERE clause
     * @param conditionValues List of values to use in the WHERE clause
     * @return PreparedStatement for updating a row in a table
     * @throws SQLException If an error occurs while creating the PreparedStatement
     */
    @SuppressWarnings("unused")
    private static PreparedStatement createUpdateStatement(Connection conn, String tableName, Map<String, Object> data, List<String> conditionColumns, List<Object> conditionValues) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(tableName).append(" SET ");
        List<Object> values = new ArrayList<>();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            sb.append(entry.getKey()).append("=?, ");
            values.add(entry.getValue());
        }
        sb.delete(sb.length() - 2, sb.length()); // Remove last comma and space
        if (conditionColumns != null && !conditionColumns.isEmpty()) {
            sb.append(" WHERE ");
            for (int i = 0; i < conditionColumns.size(); i++) {
                if (i > 0) {
                    sb.append(" AND ");
                }
                sb.append(conditionColumns.get(i)).append("=?");
                values.add(conditionValues.get(i));
            }
        }
        String query = sb.toString();
        PreparedStatement pstmt = conn.prepareStatement(query);
        for (int i = 0; i < values.size(); i++) {
            pstmt.setObject(i + 1, values.get(i));
        }
        return pstmt;
    }

    /**
     * Deletes a rows from a table.
     * @param tableName The name of the table.
     * @param conditionColumns The names of the primary key columns.
     * @param conditionValues The values of the primary key columns.
     * @throws DatabaseOperationException If the row could not be deleted.
     */
    @SuppressWarnings("unused")
    public static void deleteRows(String tableName, List<String> conditionColumns, List<Object> conditionValues) throws DatabaseOperationException {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("DELETE FROM ").append(tableName).append(" WHERE ");
        for (int i = 0; i < conditionColumns.size(); i++) {
            if (i > 0) {
                queryBuilder.append(" AND ");
            }
            queryBuilder.append(conditionColumns.get(i)).append(" = ?");
        }
        String query = queryBuilder.toString();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < conditionValues.size(); i++) {
                stmt.setObject(i + 1, conditionValues.get(i));
            }
            int rowsAffected = stmt.executeUpdate();
            LOGGER.log(Level.INFO, "Deleted " + rowsAffected + " row(s) from table " + tableName);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error deleting rows with condition values: " + conditionValues);
        }
    }


    /**
     * Deletes all rows from a table.
     * @param tableName The name of the table.
     * @throws DatabaseOperationException If the rows could not be deleted.
     */
    public static void deleteAllRows(String tableName) throws DatabaseOperationException {
        String query = "DELETE FROM " + tableName;
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(query);
            LOGGER.log(Level.INFO, rowsAffected + " rows deleted successfully from table " + tableName);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error deleting all rows from table " + tableName);
        }
    }


    /**
     * Closes the connection pool.
     */
    @SuppressWarnings("unused")
    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

}

