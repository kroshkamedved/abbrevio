package com.en.abbrevio.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The ReactionStep class is an entity that represents a specific step in a chemical reaction, which could be either a single-step or multi-step reaction.
 * <p>
 * This class encapsulates data about all the abbreviation involved in a reaction step, including reactants, reagents, and products. It also keeps track of any abbreviations provided in the reaction scheme that could not be recognized.
 * <p>
 * Each instance of this class corresponds to a single reaction step, with separate lists to hold the reactants, reagents, products, and unidentified abbreviations.
 *
 * @param <T> the type of the element which represents the single abbreviation in different groups (reactants, reagents, products).
 */
@Data
public class ReactionStep<T> {
    private final List<T> reactants = new ArrayList<>();
    private final List<T> reagents = new ArrayList<>();
    private final List<T> products = new ArrayList<>();
    private final List<String> unidentifiedAbbreviation = new ArrayList<>();
}
