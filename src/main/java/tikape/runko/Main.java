package tikape.runko;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.*;
import tikape.runko.domain.*;

public class Main {

    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Database database = new Database("jdbc:sqlite:tekstilauta.db");
        database.init();

        LautaDao lautaDao = new LautaDao(database);
        LankaDao lankaDao = new LankaDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Lauta> laudat = lautaDao.findAll();
            map.put("laudat", laudat);
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/:nimi", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Lanka> langat = lankaDao.findByLauta(req.params(":nimi"));
            map.put("langat", langat);
            Lauta lauta = lautaDao.findOne(req.params(":nimi"));
            map.put("lauta", lauta);
            return new ModelAndView(map, "lauta");
        }, new ThymeleafTemplateEngine());

        get("/langat/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Viesti> viestit = viestiDao.findByLanka(Integer.parseInt(req.params(":id")));
            map.put("viestit", viestit);
            Lanka lanka = lankaDao.findOne(Integer.parseInt(req.params(":id")));
            map.put("lanka", lanka);
            return new ModelAndView(map, "lanka");
        }, new ThymeleafTemplateEngine());

        post("/langat/:id", (req, res) -> {
            int x = viestiDao.findAll().size() + 1;
            Timestamp aika = new Timestamp(System.currentTimeMillis());
            Viesti uusviesti = new Viesti(x, req.queryParams("viesti"), req.queryParams("nimimerkki"),
                    aika, Integer.parseInt(req.params(":id")));
            viestiDao.add(uusviesti);
            res.redirect("/langat/" + req.params(":id"));
            return "ok";
        });
        post("/:nimi", (req, res) -> {
            int x = lankaDao.findAll().size() + 1;
            String otsikko = req.queryParams("otsikko");
            if(otsikko.isEmpty()) {
                res.redirect("/" + req.params(":nimi"));
                return "ok";
            }
            Lanka uusLanka = new Lanka(x, otsikko, req.params(":nimi"), 1, null);
            lankaDao.add(uusLanka);
            int y = viestiDao.findAll().size() + 1;
            Timestamp aika = new Timestamp(System.currentTimeMillis());
            Viesti uusviesti = new Viesti(y, req.queryParams("viesti"), req.queryParams("nimimerkki"),
                    aika, x);
            viestiDao.add(uusviesti);
            res.redirect("/langat/" + x);
            return "ok";
        });
    }
}
