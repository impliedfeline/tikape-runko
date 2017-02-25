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
            res.redirect("/laudat");
            return "ok";
        });
        
        get("/laudat", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Lauta> laudat = lautaDao.findAll();
            map.put("laudat", laudat);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        get("/laudat/:nimi", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Lanka> langat = lankaDao.findByLauta(req.params(":nimi"));
            map.put("langat", langat);
            map.put("lauta", req.params(":nimi"));
            return new ModelAndView(map, "lauta");
        }, new ThymeleafTemplateEngine());


    }
}
