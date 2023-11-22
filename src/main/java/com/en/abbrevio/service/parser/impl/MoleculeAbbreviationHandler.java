package com.en.abbrevio.service.parser.impl;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@Data
public class MoleculeAbbreviationHandler extends DefaultHandler {
    private static final String TARGET_TAG_NAME = "s";
    private static final String PARENT_TARGET_TAG_NAME = "t";
    private static final String PARENT_ATTRIBUTE = "Warning";
    private static final String PARENT_ATTRIBUTE_VALUE = "Chemical Interpretation is not possible for this label";

    private boolean parentTag;
    private boolean targetTag;

    private List<String> abbreviations;
    private List<String> exclusions;

    public MoleculeAbbreviationHandler() {
        exclusions = Arrays.asList("+");
    }


    @Override
    public void startDocument() throws SAXException {
        abbreviations = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals(PARENT_TARGET_TAG_NAME)) {
            String warning = attributes.getValue(PARENT_ATTRIBUTE);
            if (Objects.nonNull(warning) && warning.equals(PARENT_ATTRIBUTE_VALUE)) {
                parentTag = true;
            }
        } else if (parentTag && qName.equals(TARGET_TAG_NAME)) {
            targetTag = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (targetTag && qName.equals(TARGET_TAG_NAME)) {
            targetTag = false;
        } else if (parentTag && qName.equals((PARENT_TARGET_TAG_NAME))) {
            parentTag = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String content = new String(ch, start, length);
        if (isTargetTag() && !(exclusions.contains(content))) {
            abbreviations.add(content);
        }
    }
}
