package com.en.abbrevio.service.parser.impl;

import com.en.abbrevio.exception.ParsingException;
import com.en.abbrevio.service.parser.ParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.SAXParser;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class XMLParserService implements ParserService {
    private final MoleculeAbbreviationHandler parseHandler;
    private final SAXParser saxParser;

    @Override
    public List<String> parse(String source) {
        try {
            InputSource inputSource = new InputSource(new StringReader(source));
            saxParser.parse(inputSource, parseHandler);
            return parseHandler.getAbbreviations();
        } catch (SAXException | IOException e) {
            throw new ParsingException(e.getMessage());
        }
    }
}
