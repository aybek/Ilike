package models.utils;

import models.AuthorisedUser;
import models.SecurityRole;
import play.db.ebean.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nmb
 * Date: 10/4/13
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class SecurityRoleUtils {

    public static Model.Finder<Long,SecurityRole> find = new Model.Finder<Long,SecurityRole>(Long.class, SecurityRole.class);

    public static List<SecurityRole> getRoles(){
        return find.all();
    }

    public static void setRoles(SecurityRole securityRole){

    }

    public static SecurityRole getById(Long id){
        return find.byId(id);
    }

    public static SecurityRole getRoleByName(String name){
        return find.where().ilike("name", name).findUnique();
    }

    public static SecurityRole getRoleByUser(AuthorisedUser user){
        return find.where().eq("student", user).findUnique();
    }

    public static void deleteRoleById(Long id){
        find.ref(id).delete();
    }

    public static List<String> list(){
        List<SecurityRole> SecurityRoles = find.all();
        List<String> names = new ArrayList<String>();
        for(int i=0;i<SecurityRoles.size();i++){
            names.add(SecurityRoles.get(i).name);
        }
        return names;
    }

    public static SecurityRole findByName(String name)
    {
        return find.where()
                .eq("name",
                        name)
                .findUnique();
    }

}
