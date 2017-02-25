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

/**
 *
 * @author juicyp
 */
public class LankaDao {
    
    private Database database;

    public LautaDao(Database database) {
        this.database = database;
    }

    public Lauta haeLauta(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lauta WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        
        Integer id = rs.getInt("id");
        String motd = rs.getString("motd");

        Lauta l = new Lauta(nimi, motd);
        rs.close();
        stmt.close();
        connection.close();

        return l;
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
