package com.en.abbrevio.service.parser;

import com.en.abbrevio.service.parser.impl.MoleculeAbbreviationHandler;
import com.en.abbrevio.service.parser.impl.XMLParserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

@TestConfiguration
public class XMLParserTestCofiguration {

    @Bean
    public SAXParser saxParser() throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        return factory.newSAXParser();
    }

    @Bean
    public MoleculeAbbreviationHandler moleculeAbbreviationHandler() {
        return new MoleculeAbbreviationHandler();
    }

    @Bean
    public XMLParserService xmlParserService() throws SAXException, ParserConfigurationException {
        return new XMLParserService(moleculeAbbreviationHandler(), saxParser());
    }
}
