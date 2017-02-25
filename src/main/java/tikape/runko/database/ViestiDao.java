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
public class ViestiDao implements Dao<Viesti, Integer> {

    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE nimi = ?");
        stmt.setObject(1, key);

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
    
    public List<Viesti> findAll(String lanka_nimi) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE lanka_id = ?");
        stmt.setObject(1, lanka_nimi);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
        Integer id = rs.getInt("id");
            String sisalto = rs.getString("sisalto");
            String nimimerkki = rs.getString("nimimerkki");
            Time aika = rs.getTime("aika");
            Integer lanka_id = rs.getInt("lanka_id");
            Integer vastaus = rs.getInt("vastaus");

            viestit.add(new Viesti(id, sisalto, nimimerkki, aika, lanka_id, vastaus));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
        Integer id = rs.getInt("id");
            String sisalto = rs.getString("sisalto");
            String nimimerkki = rs.getString("nimimerkki");
            Time aika = rs.getTime("aika");
            Integer lanka_id = rs.getInt("lanka_id");
            Integer vastaus = rs.getInt("vastaus");

            viestit.add(new Viesti(id, sisalto, nimimerkki, aika, lanka_id, vastaus));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
