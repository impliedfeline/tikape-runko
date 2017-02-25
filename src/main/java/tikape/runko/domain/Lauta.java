/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author Wagahai
 */
public class Lauta {

    private String nimi;
    private String motd;

    public Lauta(String nimi, String motd) {
        this.nimi = nimi;
        this.motd = motd;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getMotd() {
        return motd;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }
}
