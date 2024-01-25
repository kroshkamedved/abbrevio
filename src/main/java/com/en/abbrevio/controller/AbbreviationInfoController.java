package com.en.abbrevio.controller;

import com.en.abbrevio.dto.ResponseTO;
import com.en.abbrevio.model.AbbreviationInfo;
import com.en.abbrevio.service.AbbreviationInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The AbbreviationController class is the REST controller that provides the functionality to analyse
 * abbreviation presented in the reaction scheme, recognise them, and retrieve corresponding data about abbreviation from database.
 */
@CrossOrigin(origins = "${allowed.request.origin}")
@RestController
@RequestMapping(path = "api/v1/abbreviation")
@RequiredArgsConstructor
public class AbbreviationInfoController {
    private final AbbreviationInfoService molService;


    @PostMapping(path = "seek", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseTO> parseCDXML(@RequestBody String xml) {
        ResponseTO response = molService.parseCDXML(xml);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * accepts json presentation of AbbreviationInfo and add the new AbbreviationInfo  record to the DB
     *
     * @param newRecord AbbreviationInfo class entity in json presentation
     * @return created AbbreviationInfo if record was successfully saved to the DB
     */
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AbbreviationInfo> addAbbreviation(@RequestBody AbbreviationInfo newRecord) {
        AbbreviationInfo savedRecord = molService.addAbbreviation(newRecord);
        return ResponseEntity.status(HttpStatus.OK).body(savedRecord);
    }
}
