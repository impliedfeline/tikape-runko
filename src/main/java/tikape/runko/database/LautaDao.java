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
import tikape.runko.domain.Lauta;
import tikape.runko.domain.Viesti;

/**
 *
 * @author Wagahai
 */
public class LautaDao implements Dao<Lauta, String> {

    private Database database;

    public LautaDao(Database database) {
        this.database = database;
    }

    @Override
    public Lauta findOne(String key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lauta WHERE nimi = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        String nimi = rs.getString("nimi");
        String motd = rs.getString("motd");
        Integer maara = count(nimi, connection);
        Viesti viimeisin = viimeisin(nimi, connection);

        Lauta l = new Lauta(nimi, motd, maara, viimeisin);
        rs.close();
        stmt.close();
        connection.close();

        return l;
    }

    @Override
    public List<Lauta> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lauta");

        ResultSet rs = stmt.executeQuery();
        List<Lauta> laudat = new ArrayList<>();
        while (rs.next()) {
            String nimi = rs.getString("nimi");
            String motd = rs.getString("motd");
            Integer maara = count(nimi, connection);
            Viesti viimeisin = viimeisin(nimi, connection);
            Lauta l = new Lauta(nimi, motd, maara, viimeisin);

            laudat.add(l);
        }

        rs.close();
        stmt.close();
        connection.close();

        return laudat;
    }

    @Override
    public void delete(String key) throws SQLException {
        throw new UnsupportedOperationException("Ei tarvita."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add(Lauta obj) throws SQLException {
        String nimi = obj.getNimi();
        String motd = obj.getMotd();

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Lauta "
                + "(nimi, motd) "
                + "VALUES (" + nimi + ", " + motd + ");");
        stmt.execute();
    }

    private Integer count(String nimi, Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) AS maara FROM Viesti v "
                + "INNER JOIN Lanka l ON v.lanka_id = l.id INNER JOIN Lauta la ON l.lauta = la.nimi WHERE la.nimi = ?;");
        stmt.setObject(1, nimi);
        ResultSet rs = stmt.executeQuery();
        Integer maara = rs.getInt("maara");

        rs.close();
        stmt.close();

        return maara;
    }

    private Viesti viimeisin(String nimi, Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti v "
                + "INNER JOIN Lanka l ON v.lanka_id = l.id INNER JOIN Lauta la ON l.lauta = la.nimi WHERE la.nimi = ? "
                + "ORDER BY aika DESC LIMIT 1;");
        stmt.setObject(1, nimi);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Integer id = rs.getInt("id");
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
