package com.en.abbrevio.dto;

import com.en.abbrevio.model.AbbreviationInfo;
import com.en.abbrevio.model.ReactionStep;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The ResponseTO class is a response transfer object that encapsulates the microservice response data.
 */
@Getter
public class ReactionSchemaTO {

    private final List<ReactionStep<AbbreviationInfo>> steps = new ArrayList<>();

    public void addReactionStep(ReactionStep<AbbreviationInfo> step) {
        steps.add(step);
    }
}
