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


    }
}
