package com.nimesia.sweetvillas.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles", schema = "crm")
public class RoleEntity {

    @Id
    @Column(name = "role_id")
    private @Getter @Setter String id;

    @Column(name = "role")
    private @Getter @Setter String name;

}