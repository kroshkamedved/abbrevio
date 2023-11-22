package com.en.abbrevio.service;

import com.en.abbrevio.model.Molecule;
import com.en.abbrevio.service.parser.ParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MoleculeService {
    private final ParserService parserService;
    //  private final MoleculeRepository molRepository;

    public List<Molecule> fetchByXml(String xml) {
        List<String> moleculesAbbreviations = parserService.parse(xml);
        moleculesAbbreviations.stream()
                .forEach(System.out::println);
        return null;
    }
}
