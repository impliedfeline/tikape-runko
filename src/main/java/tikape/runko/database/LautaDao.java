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
public class LautaDao {

    private Database database;

    public LautaDao(Database database) {
        this.database = database;
    }

    public Lauta haeLauta(String lauta_nimi) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lauta WHERE nimi = ?");
        stmt.setObject(1, lauta_nimi);
        
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
}
