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
    private Integer maara;

    public Lauta(String nimi, String motd, Integer maara) {
        this.nimi = nimi;
        this.motd = motd;
        this.maara = maara;
    }

    public Integer getMaara() {
        return maara;
    }

    public void setMaara(Integer maara) {
        this.maara = maara;
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
