package com.en.abbrevio.service.parser;

import com.en.abbrevio.exception.ParsingException;
import com.en.abbrevio.model.ReactionStep;
import com.en.abbrevio.service.parser.impl.MoleculeAbbreviationHandler;
import com.en.abbrevio.service.parser.impl.XMLParserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.FileCopyUtils;
import org.xml.sax.InputSource;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.SAXParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ActiveProfiles("test")
@ContextConfiguration(classes = XMLParserTestCofiguration.class)
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class XMLParserServiceTest {
    @Autowired
    private XMLParserService parserService;

    @Test
    @SneakyThrows
    void testXmlParserService() {
        String content = FileCopyUtils.copyToString(new InputStreamReader
                (new ClassPathResource("request_body_example.xml").getInputStream()));
        List<ReactionStep<String>> parseResult = parserService.parse(content);

        ReactionStep<String> firstStep = new ReactionStep<>();
        List<String> firstStepReactants = List.of("TRT");
        List<String> firstStepReagents = List.of("DIPEA", "AA");
        List<String> firstStepProducts = List.of("FRG", "FGR");
        firstStep.getReactants().addAll(firstStepReactants);
        firstStep.getReagents().addAll(firstStepReagents);
        firstStep.getProducts().addAll(firstStepProducts);

        ReactionStep<String> secondStep = new ReactionStep<>();
        List<String> secondStepReactants = List.of("FRG", "FGR");
        List<String> secondStepReagents = List.of("AA");
        secondStep.getReactants().addAll(secondStepReactants);
        secondStep.getReagents().addAll(secondStepReagents);

        List<ReactionStep<String>> expectedList = new ArrayList<>();
        expectedList.add(firstStep);
        expectedList.add(secondStep);

        Assertions.assertIterableEquals(expectedList, parseResult);
    }

    @Test
    @SneakyThrows
    void testSAXParserParseWithException() {
        String content = FileCopyUtils.copyToString(new InputStreamReader
                (new ClassPathResource("request_body_example.xml").getInputStream()));
        final String contentWithError = content.substring(32);
        Assertions.assertThrows(ParsingException.class, () -> {
            parserService.parse(contentWithError);
        });
    }
}
