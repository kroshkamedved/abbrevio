package com.en.abbrevio.service;

import com.en.abbrevio.dto.ResponseTO;
import com.en.abbrevio.model.AbbreviationInfo;
import com.en.abbrevio.model.ReactionStep;
import com.en.abbrevio.repository.AbbreviationInfoRepository;
import com.en.abbrevio.service.parser.ParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AbbreviationInfoService {
    private final ParserService<String,String> parserService;
    private final AbbreviationInfoRepository molRepository;

    public ResponseTO parseCDXML(String xml) {
        List<ReactionStep<String>> moleculesAbbreviations = parserService.parse(xml);
        ResponseTO responseTO = new ResponseTO();
        for (ReactionStep<String> step : moleculesAbbreviations) {
            ReactionStep<AbbreviationInfo> currentStep = new ReactionStep<>();
            fullFillStep(step, currentStep);
            responseTO.addReactionStep(currentStep);
        }
        return responseTO;
    }

    private void fullFillStep(ReactionStep<String> abbreviations, ReactionStep<AbbreviationInfo> currentStep) {
        for (String abbr : abbreviations.getProducts()) {
            Optional<AbbreviationInfo> currentMolecule = molRepository.getByName(abbr.toUpperCase());
            currentMolecule.ifPresentOrElse((mol -> currentStep.getProducts().add(mol)),
                    () -> currentStep.getUnidentifiedAbbreviation().add(abbr));
        }
        for (String abbr : abbreviations.getReagents()) {
            Optional<AbbreviationInfo> currentMolecule = molRepository.getByName(abbr.toUpperCase());
            currentMolecule.ifPresentOrElse((mol) -> currentStep.getReagents().add(mol),
                    () -> currentStep.getUnidentifiedAbbreviation().add(abbr));
        }
        for (String abbr : abbreviations.getReactants()) {
            Optional<AbbreviationInfo> currentMolecule = molRepository.getByName(abbr.toUpperCase());
            currentMolecule.ifPresentOrElse((mol) -> currentStep.getReactants().add(mol),
                    () -> currentStep.getUnidentifiedAbbreviation().add(abbr));
        }
    }
}
