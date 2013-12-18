package models.utils;

import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.google.common.base.Strings;
import models.AuthorisedUser;
import models.SecurityRole;
import play.db.ebean.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nmb
 * Date: 10/4/13
 * Time: 9:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class AuthorisedUserUtils {
    public static DateFormat YMD = new SimpleDateFormat("dd/MM/yyyy");

    public static Model.Finder<Long,AuthorisedUser> find = new Model.Finder<Long,AuthorisedUser>(Long.class, AuthorisedUser.class);

    public static AuthorisedUser getById(Long id) {
        return find.byId(id);
    }

    public static List<AuthorisedUser> getAll() {
        return find.all();
    }

    public static AuthorisedUser getUser(String email,String password) {
        return  find.where().eq("email", email).eq("password", password).findUnique();
    }

    public static AuthorisedUser getUserByEmail(String email) {
        return  find.where().eq("email", email).findUnique();
    }

    public static List<AuthorisedUser> getByName(String name) {
        return find.where().ilike("firstName", "%" + name + "%").findList();
    }

    public static List<AuthorisedUser> getUsersByRoleAndStatus(SecurityRole role, boolean accepted, int first, int second) {
        return find.where().and(Expr.like("roles.name", "%" + role.name + "%"),
                Expr.eq("accepted", accepted)).orderBy("lastName").setFirstRow(first).setMaxRows(second).findList();
    }

}
