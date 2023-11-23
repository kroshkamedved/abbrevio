package com.en.abbrevio.service.parser;

import com.en.abbrevio.exception.ParsingException;
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
import java.util.Arrays;
import java.util.List;

@ActiveProfiles("test")
@ContextConfiguration(classes = XMLParserTestCofiguration.class)
//@Import(XMLParserTestCofiguration.class)
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
        List<String> expectedList = Arrays.asList("DIPEA");
        List<String> parseResult = parserService.parse(content);

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
