/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        Integer id = rs.getInt("id");
        String sisalto = rs.getString("sisalto");
        String nimimerkki = rs.getString("nimimerkki");
        Timestamp aika = new Timestamp(rs.getLong("aika"));
        Integer lanka_id = rs.getInt("lanka_id");
        Integer vastaus = rs.getInt("vastaus");

        Viesti v = new Viesti(id, sisalto, nimimerkki, aika, lanka_id, vastaus);
        rs.close();
        stmt.close();
        connection.close();

        return v;
    }

    public List<Viesti> findByLanka(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE lanka_id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();

        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String sisalto = rs.getString("sisalto");
            String nimimerkki = rs.getString("nimimerkki");
            Timestamp aika = new Timestamp(rs.getLong("aika"));
            Integer lanka_id = rs.getInt("lanka_id");
            Integer vastaus = null;
            try {
                vastaus = rs.getInt("vastaus");
            } catch (Exception e) {
            }

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

        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String sisalto = rs.getString("sisalto");
            String nimimerkki = rs.getString("nimimerkki");
            Timestamp aika = new Timestamp(rs.getLong("aika"));
            Integer lanka_id = rs.getInt("lanka_id");
            Integer vastaus = null;
            try {
                vastaus = rs.getInt("vastaus");
            } catch (Exception e) {
            }

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

    @Override
    public void add(Viesti obj) throws SQLException {
        Integer id = obj.getId();
        String sisalto = obj.getSisalto();
        String nimimerkki = obj.getNimimerkki();
        Long aika = obj.getAika().getTime();
        Integer lanka_id = obj.getLanka_id();
        Integer vastaus = obj.getVastaus();
        if (nimimerkki == null || nimimerkki.isEmpty()) {
            nimimerkki = "Nyymi";
        }
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viesti "
                + "(id, sisalto, nimimerkki, aika, lanka_id, vastaus_id) "
                + "VALUES (" + id + ", '" + sisalto + "', '" + nimimerkki + "', "
                + aika + ", " + lanka_id + ", " + vastaus + ");");
        stmt.execute();
    }

}
