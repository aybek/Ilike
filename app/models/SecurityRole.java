package models;

import be.objectify.deadbolt.core.models.Role;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints;
import play.db.ebean.Model;

/**
 * @author rauan.kussembayev
 */
@Entity 
@Table(name="roles")
public class SecurityRole extends Model implements Role {
    public static final String STUDENT = "USER";
    public static final String ADMIN = "ADMIN";
    public static final String GUEST = "GUEST";

    @Id
    public Long id;

	@Constraints.Required
    @Column(unique = true)
	public String name;

	@ManyToMany(mappedBy="roles")
	public List<AuthorisedUser> users;

    @Override
    public String getName() {
        return name;
    }

}
