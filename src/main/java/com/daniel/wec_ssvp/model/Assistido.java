package com.daniel.wec_ssvp.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "assistido")
public class Assistido {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(nullable = false, length = 100)
        private String nome;

        @Column(nullable = false)
        private int idade;
}
