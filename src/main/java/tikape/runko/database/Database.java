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
                + "FOREIGN KEY(lanka_id) REFERENCES Lanka(id)\n"
                + ");");

        lista.add("INSERT INTO Lauta (nimi, motd) VALUES ('Ohjelmointi', 'Netbeans on paras');");
        lista.add("INSERT INTO Lauta (nimi, motd) VALUES ('Japani', 'Pls vastatkaa');");
        lista.add("INSERT INTO Lauta (nimi, motd) VALUES ('Sekalainen', 'Trump pelasti maailman');");
        lista.add("INSERT INTO Lanka (id, otsikko, lauta) VALUES (1, 'Paras ohjelmointiympäristö?', 'Ohjelmointi');");
        lista.add("INSERT INTO Lanka (id, otsikko, lauta) VALUES (2, 'Eka Japaninreissu', 'Japani');");
        lista.add("INSERT INTO Lanka (id, otsikko, lauta) VALUES (3, 'Terkut tulevaisuudesta', 'Sekalainen');");
        lista.add("INSERT INTO Lanka (id, otsikko, lauta) VALUES (4, 'Onks ramen hyvää', 'Japani');");
        lista.add("INSERT INTO Viesti (id, sisalto, nimimerkki, aika, lanka_id) VALUES (1, 'Netbeans on paras', 'Fuksi', 1488688434112, 1);");
        lista.add("INSERT INTO Viesti (id, sisalto, nimimerkki, aika, lanka_id) VALUES (2, 'Kek', 'Koodimestari', 1488699444121, 1);");
        lista.add("INSERT INTO Viesti (id, sisalto, nimimerkki, aika, lanka_id) VALUES (3, 'Mihin kannattais mennä?', 'I love nippon', 1488699774232, 2);");
        lista.add("INSERT INTO Viesti (id, sisalto, nimimerkki, aika, lanka_id) VALUES (4, 'Trump pelasti maailman', 'Trump', 11188888443124, 3);");
        lista.add("INSERT INTO Viesti (id, sisalto, nimimerkki, aika, lanka_id) VALUES (5, 'Niin, onko se?', 'Ramenmies', 1488699884253, 4);");
        lista.add("INSERT INTO Viesti (id, sisalto, nimimerkki, aika, lanka_id) VALUES (6, 'Pls vastatkaa', 'Ramenmies', 1488699994356, 4);");

        return lista;
    }
}
