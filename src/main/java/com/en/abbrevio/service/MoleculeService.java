package com.en.abbrevio.service;

import com.en.abbrevio.dto.ResponseTO;
import com.en.abbrevio.model.Molecule;
import com.en.abbrevio.repository.MoleculeRepository;
import com.en.abbrevio.service.parser.ParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoleculeService {
    private final ParserService parserService;
    private final MoleculeRepository molRepository;

    public ResponseTO parseCDXML(String xml) {
        List<String> moleculesAbbreviations = parserService.parse(xml);
        ResponseTO responseTO = new ResponseTO();
        for (String abbr : moleculesAbbreviations) {
            Optional<Molecule> currentMolecule = molRepository.getByName(abbr);
            if (currentMolecule.isEmpty()) {
                responseTO.addUnidentifiedAbbreviation(abbr);
            } else {
                responseTO.addIdentifiedMolecule(currentMolecule.get());
            }
        }
        return responseTO;
    }
}
