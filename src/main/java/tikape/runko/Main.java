package tikape.runko;

import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.*;
import tikape.runko.domain.*;

public class Main {

    public static void main(String[] args) throws Exception {
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
            String aika = "25243543"; //miten aika
            Viesti uusviesti = new Viesti(x, req.queryParams("viesti"), req.queryParams("nimimerkki"), aika, 1, 2);
            viestiDao.add(uusviesti);
            res.redirect("/langat/" + req.params(":id"));
            return "jes";
        });
    }
}
