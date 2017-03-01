/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.sql.Timestamp;

/**
 *
 * @author Wagahai
 */
public class Viesti {

    private Integer id;
    private String sisalto;
    private String nimimerkki;
    private Timestamp aika;
    private Integer lanka_id;
    private Integer vastaus;

    public Viesti(Integer id, String sisalto, String nimimerkki, Timestamp aika, Integer lanka_id, Integer vastaus) {
        if (nimimerkki.length() > 20)
            nimimerkki = nimimerkki.substring(0, 20);
        if (sisalto.length() > 1000)
            sisalto = sisalto.substring(0, 1000);
        this.id = id;
        this.sisalto = sisalto;
        this.nimimerkki = nimimerkki;
        this.aika = aika;
        this.lanka_id = lanka_id;
        this.vastaus = vastaus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSisalto() {
        return sisalto;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public void setNimimerkki(String nimimerkki) {
        this.nimimerkki = nimimerkki;
    }

    public Timestamp getAika() {
        return aika;
    }

    public void setAika(Timestamp aika) {
        this.aika = aika;
    }

    public Integer getLanka_id() {
        return lanka_id;
    }

    public void setLanka_id(Integer lanka_id) {
        this.lanka_id = lanka_id;
    }

    public Integer getVastaus() {
        return vastaus;
    }

    public void setVastaus(Integer vastaus) {
        this.vastaus = vastaus;
    }
}