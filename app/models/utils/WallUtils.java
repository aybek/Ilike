package models.utils;

import models.Wall;
import play.db.ebean.Model;

import java.util.List;

/**
 * author aybek.bukabayev
 */
public class WallUtils {

    public static final Model.Finder<Long, Wall> find = new Model.Finder<Long,Wall>(Long.class, Wall.class);

    public static List<Wall> getAll(){
        return find.all();
    }

    public static Wall getById(Long id){
        return find.byId(id);
    }
}
