package com.en.abbrevio.dto;

import com.en.abbrevio.model.Molecule;
import com.en.abbrevio.model.ReactionStep;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResponseTO {

    private final List<ReactionStep<Molecule>> steps = new ArrayList<>();

    public void addReactionStep(ReactionStep step) {
        steps.add(step);
    }
}
