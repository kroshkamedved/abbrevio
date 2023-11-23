package com.en.abbrevio.dto;

import com.en.abbrevio.model.Molecule;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResponseTO {
    private List<Molecule> identified = new ArrayList<>();
    private List<String> unidentifiedAbbreviation = new ArrayList<>();


    public void addIdentifiedMolecule(Molecule identifiedMolecule) {
        identified.add(identifiedMolecule);
    }

    public void addUnidentifiedAbbreviation(String abbr) {
        unidentifiedAbbreviation.add(abbr);
    }
}
