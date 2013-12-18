package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * author aybek.bukabayev
 */
@Entity
@Table(name="walls")
public class Wall extends Model {
    @Id
    public Long id;

    @ManyToOne
    public AuthorisedUser user;

    public int wallId;
    public String name;

    public Wall(){

    }
}
