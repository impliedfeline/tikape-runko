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
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Lauta;

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

        Lauta l = new Lauta(nimi, motd);
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

            laudat.add(new Lauta(nimi, motd));
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
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Lauta " +
                            "(nimi, motd) " +
                            "VALUES (" + nimi + ", " + motd + ");");
        stmt.execute();
    }

}
