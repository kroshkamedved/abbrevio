package com.en.abbrevio.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReactionStep<T> {
    private final List<T> reactants = new ArrayList<>();
    private final List<T> reagents = new ArrayList<>();
    private final List<T> products = new ArrayList<>();
    private final List<String> unidentifiedAbbreviation = new ArrayList<>();
}
