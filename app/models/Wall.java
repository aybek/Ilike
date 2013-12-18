package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * author aybek.bukabayev
 */
@Entity
@Table(name="walls")
public class Wall extends Model {
    @Id
    public Long id;

    public int wallId;
    public String name;

    public Wall(){

    }
}
