package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Lauta\n"
                + "(\n"
                + "nimi text PRIMARY KEY,\n"
                + "motd text\n"
                + ");");
        lista.add("CREATE TABLE Lanka\n"
                + "(\n"
                + "id integer PRIMARY KEY,\n"
                + "otsikko text,\n"
                + "lauta text NOT NULL,\n"
                + "FOREIGN KEY(lauta) REFERENCES Lauta(nimi)\n"
                + ")");
        lista.add("CREATE TABLE Viesti\n"
                + "(\n"
                + "id integer PRIMARY KEY,\n"
                + "sisalto text NOT NULL,\n"
                + "nimimerkki text,\n"
                + "aika integer NOT NULL,\n"
                + "lanka_id integer NOT NULL,\n"
                + "vastaus_id integer,\n"
                + "FOREIGN KEY(lanka_id) REFERENCES Lanka(id),\n"
                + "FOREIGN KEY(vastaus_id) REFERENCES Viesti(id)\n"
                + ");");

//        Model for db tables/inserts
        lista.add("INSERT INTO Lauta (nimi, motd) VALUES ('Loli', 'Goemon saigoo :D');");
        lista.add("INSERT INTO Lauta (nimi, motd) VALUES ('3DPD', 'Kek');");
        lista.add("INSERT INTO Lanka (id, otsikko, lauta) VALUES (1, 'Best Girl in 2017 Spring', 'Loli');");
        lista.add("INSERT INTO Viesti (id, sisalto, nimimerkki, aika, lanka_id, vastaus_id) VALUES (1, 'Oniichan on paras', 'Loli Next Door', 1, 1, null);");
        lista.add("INSERT INTO Viesti (id, sisalto, nimimerkki, aika, lanka_id, vastaus_id) VALUES (2, 'Niin säki :)', 'Oniitan', 2, 1, null);");

        return lista;
    }
}
