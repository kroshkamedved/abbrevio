package com.en.abbrevio.controller;

import com.en.abbrevio.model.Molecule;
import com.en.abbrevio.service.MoleculeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/molecule")
@RequiredArgsConstructor
public class MoleculeController {

    private final MoleculeService molService;

    @PostMapping(path = "seek",consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<List<Molecule>> fetchByXml(@RequestBody String xml) {
        List<Molecule> responseMolecule = molService.fetchByXml(xml);
        return ResponseEntity.status(HttpStatus.OK).body(responseMolecule);
    }

}
