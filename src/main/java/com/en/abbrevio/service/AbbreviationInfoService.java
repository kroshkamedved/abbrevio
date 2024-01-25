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
    private final ParserService<String, String> parserService;
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
        fullfillStepPart(abbreviations.getProducts(), currentStep.getProducts(), currentStep.getUnidentifiedAbbreviation());
        fullfillStepPart(abbreviations.getReagents(), currentStep.getReagents(), currentStep.getUnidentifiedAbbreviation());
        fullfillStepPart(abbreviations.getReactants(), currentStep.getReactants(), currentStep.getUnidentifiedAbbreviation());
    }

    private void fullfillStepPart(List<String> abbreviations, List<AbbreviationInfo> resultList, List<String> unidentifiedAbbreviation) {
        for (String abbr : abbreviations) {
            Optional<AbbreviationInfo> currentMolecule = molRepository.getBySynonym(abbr.toUpperCase());
            currentMolecule.ifPresentOrElse(resultList::add,
                    () -> unidentifiedAbbreviation.add(abbr));
        }
    }

    public AbbreviationInfo addAbbreviation(AbbreviationInfo newRecord) {
        return molRepository.save(newRecord);
    }
}
