package com.en.abbrevio.service;

import com.en.abbrevio.dto.ResponseTO;
import com.en.abbrevio.service.parser.XMLParserTestCofiguration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AbbreviationInfoServiceTestConfiguration.class, XMLParserTestCofiguration.class})
public class AbbreviationInfoServiceTest {
    @Autowired
    private AbbreviationInfoService abbreviationInfoService;

    @Test
    @SneakyThrows
    void testParseCDXMLServiceMethod() {
        URL responceUrl = getClass().getClassLoader().getResource("response_example.json");
        URL requestUrl = getClass().getClassLoader().getResource("request_body_example.xml");
        String expectedResult = Files.readString(Paths.get(responceUrl.toURI()));
        String parsingSource = Files.readString(Paths.get(requestUrl.toURI()));
        ResponseTO responseTO = abbreviationInfoService.parseCDXML(parsingSource);
        Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();
        String obtainedParseResultJson = gson.toJson(responseTO);
        Assertions.assertEquals(expectedResult, obtainedParseResultJson);
    }
}
