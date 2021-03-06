package com.nimesia.sweetvillas.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "stores", schema = "ecommerce")
public class StoreEntity extends AbsEntity {

    @Id
    @Column(name = "store_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Integer id;

    @Column(name = "store")
    private @Getter @Setter String name;

    @Column(name = "street")
    private @Getter @Setter String street;

    @Column(name = "postalCode")
    private @Getter @Setter Integer postalCode;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "city_id")
    private @Getter @Setter CityEntity city;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinTable(
            name = "stores_products",
            schema = "ecommerce",
            joinColumns = @JoinColumn(name = "store_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private @Getter @Setter
    List<ProductEntity> products;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @NotNull
    @JoinTable(
            name = "users_stores",
            schema = "ecommerce",
            joinColumns = @JoinColumn(name = "store_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private @Getter @Setter
    UserEntity user;

}