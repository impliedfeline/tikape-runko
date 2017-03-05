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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Lanka;
import tikape.runko.domain.Viesti;

/**
 *
 * @author juicyp
 */
public class LankaDao implements Dao<Lanka, Integer> {

    private Database database;

    public LankaDao(Database database) {
        this.database = database;
    }

    @Override
    public Lanka findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lanka WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");
        String lauta = rs.getString("lauta");
        Integer maara = count(id, connection);
        Viesti viimeisin = viimeisin(id, connection);
        Lanka l = new Lanka(id, otsikko, lauta, maara, viimeisin);

        rs.close();
        stmt.close();
        connection.close();

        return l;
    }

    public List<Lanka> findByLauta(String lauta_nimi) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lanka WHERE lauta = ?");
        stmt.setObject(1, lauta_nimi);

        ResultSet rs = stmt.executeQuery();

        List<Lanka> langat = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");
            String lauta = rs.getString("lauta");
            Integer maara = count(id, connection);
            Viesti viimeisin = viimeisin(id, connection);
            Lanka l = new Lanka(id, otsikko, lauta, maara, viimeisin);

            langat.add(new Lanka(id, otsikko, lauta, maara, viimeisin));
        }

        rs.close();
        stmt.close();
        connection.close();

        return langat;
    }

    @Override
    public List<Lanka> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lanka");

        ResultSet rs = stmt.executeQuery();

        List<Lanka> langat = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");
            String lauta = rs.getString("lauta");
            Integer maara = count(id, connection);
            Viesti viimeisin = viimeisin(id, connection);
            Lanka l = new Lanka(id, otsikko, lauta, maara, viimeisin);

            langat.add(new Lanka(id, otsikko, lauta, maara, viimeisin));
        }

        rs.close();
        stmt.close();
        connection.close();

        return langat;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add(Lanka obj) throws SQLException {
        Integer id = obj.getId();
        String otsikko = obj.getOtsikko();
        String lauta = obj.getLauta();

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Lanka "
                + "(id, otsikko, lauta) VALUES (" + id + ", '"
                + otsikko + "', '" + lauta + "');");
        stmt.execute();
    }

    private Integer count(Integer id, Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) AS maara FROM Viesti v "
                + "INNER JOIN Lanka l ON v.lanka_id = l.id WHERE l.id = ?;");
        stmt.setObject(1, id);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt("maara");
    }

    private Viesti viimeisin(Integer id, Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti v "
                + "INNER JOIN Lanka l ON v.lanka_id = l.id WHERE l.id = ? "
                + "ORDER BY aika DESC LIMIT 1;");
        stmt.setObject(1, id);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String sisalto = rs.getString("sisalto");
            String nimimerkki = rs.getString("nimimerkki");
            Timestamp aika = new Timestamp(rs.getLong("aika"));
            Integer lanka_id = rs.getInt("lanka_id");

            Viesti v = new Viesti(id, sisalto, nimimerkki, aika, lanka_id);
            rs.close();
            stmt.close();

            return v;
        }
        rs.close();
        stmt.close();
        
        return null;
    }
}
