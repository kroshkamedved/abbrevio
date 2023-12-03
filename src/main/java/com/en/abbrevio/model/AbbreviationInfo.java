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
    private String name;
    private String formula;
    @Column(nullable = false)
    private Double mw;
    @Column(nullable = false)
    private Double em;
    private String inchi;
    private String smiles;
}
