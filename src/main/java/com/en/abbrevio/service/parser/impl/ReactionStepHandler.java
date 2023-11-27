package com.en.abbrevio.service.parser.impl;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.SortedSet;
import java.util.TreeSet;

@Component
@Getter
public class ReactionStepHandler extends DefaultHandler {
    private static final String TARGET_ATTRIBUTE = "BoundingBox";
    private int reactionStepsQuantity = 0;
    private final SortedSet<BoundingBox> stepArrowBounds = new TreeSet<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("arrow")) {
            stepArrowBounds.add(BoundingBox.createFromString(attributes.getValue(TARGET_ATTRIBUTE)));
        } else if (qName.equals("step")) {
            reactionStepsQuantity++;
        }
    }
}
