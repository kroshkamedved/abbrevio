package com.en.abbrevio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Molecule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String formula;
    @Column(nullable = false)
    private Double mw;
    @Column(nullable = false)
    private Double em;
    private String inchi;
    private String smiles;

    public static Molecule getUnrecognized() {
        Molecule mol = new Molecule();
        mol.setName("UNRECOGNIZED");
        return mol;
    }
}
