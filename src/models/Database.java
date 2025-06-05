package models;

import java.sql.*;

public class Database implements AutoCloseable {
    private Connection connection;
    private String dbFile;
    private String dbUrl;
    private String tableName;

    public Database(Model model) throws SQLException {
        this.dbFile =model.getScoreDatabase();
        this.dbUrl = "jdbc:sqlite:" + dbFile;  // Andmebaasiga ühendamiseks
        this.tableName = model.getScoreTable();

        // Alustame ühendusega
        connect();
        ensureTableExists(); // Veendu et tabel on olemas, kui pole, siis teed

    }

    /**
     * Tablei loomine
     */
    private void ensureTableExists() {
        String createTableSql ="CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "time INTEGER NOT NULL," +
                "clicks INTEGER," +
                "board_size INTEGER," +
                "game_time TEXT);";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSql); // Loob tabeli kui pole
            System.out.println("Tabel loodud/ kontrollitud: " + tableName); // TEST
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            System.err.println("Viga tabeli loomisel: " + e.getMessage());
            System.out.println(createTableSql);
        }
    }

    private void connect() throws SQLException {
        connection = DriverManager.getConnection(dbUrl);
        System.out.println("Ühendus loodud: " + dbUrl); // TEST
    }


    @Override
    public void close() throws Exception {
        if(connection != null) {
            try {
                connection.close(); // Sulge ühendus
                System.out.println("ühendus suletud");  // TEST

            } catch (SQLException e) {
                //throw new RuntimeException(e);
                System.err.println("Viga ühenduse sulgemisel: " + e.getMessage());
            }
        }
    }

    /**
     * Mängija andmete lisamine andmebaasi tabelisse
     * @param name Nimi
     * @param time Aeg sekundites
     * @param clicks Klikkimiste arv
     * @param boardSize  Mängulaua suurus 10-15
     * @param played Mängu kuupäev ja kellaaeg
     */
    public void insert( String name, int time, int clicks, int boardSize, String played){
        String sql = "INSERT INTO " + tableName + " (name, time, clicks, board_size, game_time) VALUES (?,?,?,?,?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, name);
            stmt.setInt(2, time);
            stmt.setInt(3, clicks);
            stmt.setInt(4, boardSize);
            stmt.setString(5, played);
            stmt.executeUpdate();
        } catch (SQLException e) {
           // throw new RuntimeException(e);
            System.err.println("Viga andmete lisamisel: " + e.getMessage());
        }


    }
}
