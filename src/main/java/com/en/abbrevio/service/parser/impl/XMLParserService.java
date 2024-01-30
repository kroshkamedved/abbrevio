package com.en.abbrevio.service.parser.impl;

import com.en.abbrevio.exception.ParsingException;
import com.en.abbrevio.model.ReactionStep;
import com.en.abbrevio.service.parser.ParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

@Service
@RequiredArgsConstructor
public class XMLParserService implements ParserService<String, String> {
    private final MoleculeAbbreviationHandler moleculeAbbreviationHandler;
    private final ReactionStepHandler reactionStepHandler;
    private final SAXParser saxParser;

    @Override
    public List<ReactionStep<String>> parse(String source) {
        try {
            parseSource(saxParser, source, reactionStepHandler);
            parseSource(saxParser, source, moleculeAbbreviationHandler);

            SortedSet<BoundingBox> stepArrowBounds = reactionStepHandler.getStepArrowBounds();
            Map<BoundingBox, String> abbreviations = moleculeAbbreviationHandler.getAbbreviations();

            if (stepArrowBounds.isEmpty() || abbreviations.isEmpty()) {
                throw new ParsingException("nothing to identify! ");
            }
            List<ReactionStep<String>> reactionSteps = new ArrayList<>(stepArrowBounds.size());
            Iterator<BoundingBox> iterator = stepArrowBounds.iterator();

            if (stepArrowBounds.size() == 1) {
                reactionSteps.add(groupByPositionForSingleStep(iterator.next(), abbreviations));
            } else {
                int index = 0;
                while (iterator.hasNext()) {
                    BoundingBox current = iterator.next();
                    if (index == 0) {
                        reactionSteps.add((groupByPositionForFirstStep(current, abbreviations)));
                    } else if (index < stepArrowBounds.size() - 1) {
                        reactionSteps.add(groupByPositionForMiddleStep(current, abbreviations, reactionSteps, index - 1));
                    } else {
                        reactionSteps.add(groupByPositionForLastStep(current, abbreviations, index - 1, reactionSteps));
                    }
                    index++;
                }
            }
            return reactionSteps;
        } catch (SAXException | IOException e) {
            throw new ParsingException(e.getMessage());
        }
    }


    private ReactionStep<String> groupByPositionForSingleStep(BoundingBox currentArrow, Map<BoundingBox, String> abbreviations) {
        ReactionStep<String> currentStep = new ReactionStep<>();

        for (Map.Entry<BoundingBox, String> entry : abbreviations.entrySet()) {
            BoundingBox currentAbbreviationBounds = entry.getKey();

            if (currentAbbreviationBounds.getMinX() < currentArrow.getMinX()) {
                currentStep.getReactants().add(entry.getValue());
            } else if (currentAbbreviationBounds.getMinX() > currentArrow.getMinX() && currentAbbreviationBounds.getMinX() < currentArrow.getMaxX()) {
                currentStep.getReagents().add(entry.getValue());
            } else {
                currentStep.getProducts().add(entry.getValue());
            }
        }
        return currentStep;
    }


    private ReactionStep<String> groupByPositionForLastStep(BoundingBox currentArrow, Map<BoundingBox, String> abbreviations, int previousStepIndex, List<ReactionStep<String>> reactionSteps) {
        ReactionStep<String> currentStep = new ReactionStep<>();

        for (Map.Entry<BoundingBox, String> entry : abbreviations.entrySet()) {
            BoundingBox currentAbbreviationBounds = entry.getKey();

            if (currentAbbreviationBounds.getMinX() < currentArrow.getMinX()) {
                currentStep.getReactants().add(entry.getValue());
                reactionSteps.get(previousStepIndex).getProducts().add(entry.getValue());
            } else if (currentAbbreviationBounds.getMinX() > currentArrow.getMinX() && currentAbbreviationBounds.getMinX() < currentArrow.getMaxX()) {
                currentStep.getReagents().add(entry.getValue());
            } else {
                currentStep.getProducts().add(entry.getValue());
            }
        }
        return currentStep;
    }

    private ReactionStep<String> groupByPositionForMiddleStep(BoundingBox currentArrow, Map<BoundingBox, String> abbreviations, List<ReactionStep<String>> reactionSteps, int previousStepIndex) {
        ReactionStep<String> currentStep = new ReactionStep<>();
        Iterator<Map.Entry<BoundingBox, String>> iterator = abbreviations.entrySet().iterator();
        boolean wasAdded = false;

        while (iterator.hasNext()) {
            Map.Entry<BoundingBox, String> entry = iterator.next();
            BoundingBox currentAbbreviationBounds = entry.getKey();

            if (currentAbbreviationBounds.getMinX() < currentArrow.getMinX()) {
                currentStep.getReactants().add(entry.getValue());
                reactionSteps.get(previousStepIndex).getProducts().add(entry.getValue());
                wasAdded = true;
            } else if (currentAbbreviationBounds.getMinX() > currentArrow.getMinX() && currentAbbreviationBounds.getMinX() < currentArrow.getMaxX()) {
                currentStep.getReagents().add(entry.getValue());
                wasAdded = true;
            }
            if (wasAdded) {
                iterator.remove();
                wasAdded = false;
            }
        }
        return currentStep;
    }

    private ReactionStep<String> groupByPositionForFirstStep(BoundingBox currentArrow, Map<BoundingBox, String> abbreviations) {
        ReactionStep<String> currentStep = new ReactionStep<>();
        Iterator<Map.Entry<BoundingBox, String>> iterator = abbreviations.entrySet().iterator();
        boolean wasAdded = false;

        while (iterator.hasNext()) {
            Map.Entry<BoundingBox, String> entry = iterator.next();
            BoundingBox currentAbbreviationBounds = entry.getKey();

            if (currentAbbreviationBounds.getMinX() < currentArrow.getMinX()) {
                currentStep.getReactants().add(entry.getValue());
                wasAdded = true;
            } else if (currentAbbreviationBounds.getMinX() > currentArrow.getMinX() && currentAbbreviationBounds.getMinX() < currentArrow.getMaxX()) {
                currentStep.getReagents().add(entry.getValue());
                wasAdded = true;
            }
            if (wasAdded) {
                iterator.remove();
                wasAdded = false;
            }
        }
        return currentStep;
    }

    private void parseSource(SAXParser parser, String source, DefaultHandler handler) throws IOException, SAXException {
        InputSource inputSource = new InputSource(new StringReader(source));
        parser.parse(inputSource, handler);
    }

}
