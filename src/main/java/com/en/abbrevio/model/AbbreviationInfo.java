package com.en.abbrevio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

/**
 * The AbbreviationInfo class is an entity which represents specific chemical abbreviation.
 * It encapsulates all the relevant data associated with an abbreviation that is necessary for reaction calculation.
 * Each instance of this class corresponds to a single abbreviation.
 */
@Data
@Entity
@Table(name = "abbreviation_info")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AbbreviationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String synonym;
    private String formula;
    @Column(nullable = false)
    private Double mw;
    private Double em;
    private String inchi;
    private String smiles;
    private String structure;
    private String condition;
    private String name;
    private String cas;
    private Double density;
}
