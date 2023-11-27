package com.en.abbrevio.service;

import com.en.abbrevio.dto.ResponseTO;
import com.en.abbrevio.model.Molecule;
import com.en.abbrevio.model.ReactionStep;
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
    private final ParserService<String> parserService;
    private final MoleculeRepository molRepository;

    public ResponseTO parseCDXML(String xml) {
        List<ReactionStep<String>> moleculesAbbreviations = parserService.parse(xml);
        ResponseTO responseTO = new ResponseTO();
        for (ReactionStep<String> step : moleculesAbbreviations) {
            ReactionStep<Molecule> currentStep = new ReactionStep<>();
            fullFillStep(step, currentStep);
            responseTO.addReactionStep(currentStep);
        }
        return responseTO;
    }

    private void fullFillStep(ReactionStep<String> abbreviations, ReactionStep<Molecule> currentStep) {
        for (String abbr : abbreviations.getProducts()) {
            Optional<Molecule> currentMolecule = molRepository.getByName(abbr.toUpperCase());
            if (currentMolecule.isEmpty()) {
                currentStep.getUnidentifiedAbbreviation().add(abbr);
            } else {
                currentStep.getProducts().add(currentMolecule.get());
            }
        }
        for (String abbr : abbreviations.getReagents()) {
            Optional<Molecule> currentMolecule = molRepository.getByName(abbr.toUpperCase());
            if (currentMolecule.isEmpty()) {
                currentStep.getUnidentifiedAbbreviation().add(abbr);
            } else {
                currentStep.getReagents().add(currentMolecule.get());
            }
        }
        for (String abbr : abbreviations.getReactants()) {
            Optional<Molecule> currentMolecule = molRepository.getByName(abbr.toUpperCase());
            if (currentMolecule.isEmpty()) {
                currentStep.getUnidentifiedAbbreviation().add(abbr);
            } else {
                currentStep.getReactants().add(currentMolecule.get());
            }
        }
    }
}
