package com.nimesia.sweetvillas.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "translations", schema = "crm")
public class TextEntity extends AbsEntity {

    @Id
    @Column(name = "translation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Integer id;

    @Column(name = "translation")
    private @Getter @Setter String text;

    @OneToOne
    @JoinColumn(name = "lang_id")
    private @Getter @Setter LangEntity lang;

}