/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author juicyp
 */
public class Lanka {
    
    private Integer id;
    private String otsikko;
    private String lauta;
    
    public Lanka(Integer id, String otsikko, String lauta) {
        this.id = id;
        this.otsikko = otsikko;
        this.lauta = lauta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    public String getLauta() {
        return lauta;
    }

    public void setLauta(String lauta) {
        this.lauta = lauta;
    }
}
