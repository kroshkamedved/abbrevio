package com.en.abbrevio.service.parser.impl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Getter
@Setter
public class MoleculeAbbreviationHandler extends DefaultHandler {

    private static final String TARGET_TAG_NAME = "s";
    private static final String PARENT_TARGET_TAG_NAME = "t";
    private static final String TARGET_ATTRIBUTE = "BoundingBox";
    private static final String REGEX = "\\s*\\+\\s*|[a-zA-Z]\\s*=\\s*\\d";
    private static final Pattern pattern = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);

    private boolean parentTag;
    private boolean targetTag;

    private Map<BoundingBox, String> abbreviations;
    private BoundingBox currentElementBoundingBox;

    @Override
    public void startDocument() throws SAXException {
        abbreviations = new HashMap<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals(PARENT_TARGET_TAG_NAME)) {
            parentTag = true;
            String boundingBox = attributes.getValue(TARGET_ATTRIBUTE);
            if (boundingBox != null) {
                currentElementBoundingBox = BoundingBox.createFromString(boundingBox);
            }
        } else if (parentTag && qName.equals(TARGET_TAG_NAME)) {
            targetTag = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (targetTag && qName.equals(TARGET_TAG_NAME)) {
            targetTag = false;
            currentElementBoundingBox = null;
        } else if (parentTag && qName.equals((PARENT_TARGET_TAG_NAME))) {
            parentTag = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String content = new String(ch, start, length);
        Matcher matcher = pattern.matcher(content);
        if (isTargetTag() && !(matcher.find())) {
            abbreviations.put(currentElementBoundingBox, content);
        }
    }
}
