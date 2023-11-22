package com.en.abbrevio.service.parser.impl;

import com.en.abbrevio.service.parser.ParserService;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@Service
public class XMLParserService implements ParserService {
    private final SAXParser saxParser;
    private final MoleculeAbbreviationHandler parseHandler;

    public XMLParserService(MoleculeAbbreviationHandler defaultHandler) throws SAXException {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            saxParser = factory.newSAXParser();
            this.parseHandler = defaultHandler;
        } catch (ParserConfigurationException exception) {
            //TODO modify exception handling and client notification
            throw new RuntimeException("Failed to create SAXParser", exception);
        }
    }

    @Override
    public List<String> parse(String source) {
        try {
            InputSource inputSource = new InputSource(new StringReader(source));
            saxParser.parse(inputSource, parseHandler);
            return parseHandler.getAbbreviations();
        } catch (SAXException | IOException e) {
            //TODO modify exception handling and client notification
            throw new RuntimeException(e);
        }
    }
}
