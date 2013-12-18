/*
 * Copyright 2012 Steve Chaloner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package models;

import be.objectify.deadbolt.core.models.Subject;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import models.utils.SecurityRoleUtils;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Email;
import play.db.ebean.Model;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rauan.kussembayev
 */
@Entity
@Table(name = "users")
public class AuthorisedUser extends Model implements Subject {
    @Id
    public Long id;

    @Column(unique = true)
    @Constraints.Required
    @Email
    public String email;

    @Constraints.Required
    public String password;

    public String firstName;

    public String lastName;

    public String middleName;

    public String phoneNumber;

    public String address;

    @ManyToMany
    public List<SecurityRole> roles = new ArrayList<SecurityRole>();

    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
    public List<Wall> walls = new ArrayList<Wall>();



    @ManyToMany
    public List<UserPermission> permissions = new ArrayList<UserPermission>();

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE NOT NULL")
    public Boolean accepted = false;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE NOT NULL")
    public Boolean deleted = false;

    public AuthorisedUser(String firstName, String lastName, String middleName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.password = password;
    }

    public AuthorisedUser() {}

    @Override
    public List<? extends SecurityRole> getRoles() {
        return roles;
    }

    @Override
    public List<? extends UserPermission> getPermissions() {
        return permissions;
    }

    @Override
    public String getIdentifier() {
        return firstName;
    }

    public boolean is(String roleName) {
        return Collections2.transform(roles, new Function<SecurityRole, String>() {
            @Nullable
            @Override
            public String apply(@Nullable SecurityRole securityRole) {
                return securityRole.getName();
            }
        }).contains(roleName);
    }

    public void addRole(String roleName) {
        roles.add(SecurityRoleUtils.findByName(roleName));
    }

}
