package com.en.abbrevio.service.parser.impl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Getter
@Setter
public class MoleculeAbbreviationHandler extends DefaultHandler {

    private static final String FRAGMENT_TAG_NAME = "fragment";
    private static final String TARGET_TAG_NAME = "s";
    private static final String PARENT_TARGET_TAG_NAME = "t";
    private static final String TARGET_ATTRIBUTE = "BoundingBox";
    private static final String REGEX = "\\s*\\+\\s*|[a-zA-Z]\\s*=\\s*\\d";
    private static final Pattern pattern = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);

    private boolean parentTag;
    private boolean targetTag;
    private boolean fragmentTag;

    private Map<BoundingBox, String> abbreviations;
    private BoundingBox currentElementBoundingBox;

    @Override
    public void startDocument() {
        abbreviations = new HashMap<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals(FRAGMENT_TAG_NAME)) {
            fragmentTag = true;
        } else if (qName.equals(PARENT_TARGET_TAG_NAME) && !fragmentTag) {
            //Error if (qName.equals(PARENT_TARGET_TAG_NAME) && attributes.getValue("Warning") != null) {
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
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals(FRAGMENT_TAG_NAME)) {
            fragmentTag = false;
        } else if (targetTag && qName.equals(TARGET_TAG_NAME)) {
            targetTag = false;
            currentElementBoundingBox = null;
        } else if (parentTag && qName.equals((PARENT_TARGET_TAG_NAME))) {
            parentTag = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String content = new String(ch, start, length);
        Matcher matcher = pattern.matcher(content);
        if (isTargetTag() && !(matcher.find())) {
            abbreviations.put(currentElementBoundingBox, content);
        }
    }
}
