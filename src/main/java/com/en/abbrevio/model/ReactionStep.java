package com.en.abbrevio.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReactionStep<T> {
    private final List<T> reactants = new ArrayList<>();
    private final List<T> reagents = new ArrayList<>();
    private final List<T> products = new ArrayList<>();
    private final List<String> unidentifiedAbbreviation = new ArrayList<>();
}
