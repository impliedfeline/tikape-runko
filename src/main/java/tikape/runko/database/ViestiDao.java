/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Viesti;

/**
 *
 * @author Wagahai
 */
public class ViestiDao {

    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    public Viesti haeViesti(Integer viesti_id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lauta WHERE nimi = ?");
        stmt.setObject(1, viesti_id);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        Integer id = rs.getInt("id");
        String sisalto = rs.getString("sisalto");
        String nimimerkki = rs.getString("nimimerkki");
        Time aika = rs.getTime("aika");
        Integer lanka_id = rs.getInt("lanka_id");
        Integer vastaus = rs.getInt("vastaus");

        Viesti v = new Viesti(id, sisalto, nimimerkki, aika, lanka_id, vastaus);
        rs.close();
        stmt.close();
        connection.close();

        return v;
    }

    public List<Lauta> haeLaudat() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lauta");

        ResultSet rs = stmt.executeQuery();
        List<Lauta> laudat = new ArrayList<>();
        while (rs.next()) {
            String nimi = rs.getString("nimi");
            String motd = rs.getString("motd");

            laudat.add(new Lauta(nimi, motd));
        }

        rs.close();
        stmt.close();
        connection.close();

        return laudat;
    }
}
