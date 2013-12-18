package models.utils;

import models.UserPermission;
import play.db.ebean.Model;

/**
 * Created with IntelliJ IDEA.
 * User: nmb
 * Date: 10/4/13
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserPermissionUtils {

    public static final Model.Finder<Long, UserPermission> find = new Model.Finder<Long, UserPermission>(Long.class, UserPermission.class);

    public static UserPermission findByValue(String value)
    {
        return find.where()
                .eq("value",
                        value)
                .findUnique();
    }

}
