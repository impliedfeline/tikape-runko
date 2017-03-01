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
            String aika = ""; //miten aika
            Viesti uusviesti = new Viesti(x, req.queryParams("viesti"), req.queryParams("nimimerkki"),
                    System.currentTimeMillis(), Integer.parseInt(req.params(":id")), null); // mites vastaaminen
            viestiDao.add(uusviesti);
            res.redirect("/langat/" + req.params(":id"));
            return "jes";
        });
        post("/:nimi", (req, res) -> {
            int x = lankaDao.findAll().size() + 1;
            Lanka uusLanka = new Lanka(x, req.queryParams("otsikko"), req.params(":nimi"));
            lankaDao.add(uusLanka);
            String aika = ""; //miten aika
            int y = viestiDao.findAll().size() + 1;
            Viesti uusviesti = new Viesti(y, req.queryParams("viesti"), req.queryParams("nimimerkki"),
                    System.currentTimeMillis(), x, null); //mites vastaaminen
            viestiDao.add(uusviesti);
            res.redirect("/langat/" + x);
            return "jes";
        });

    }
}
