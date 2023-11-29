package com.en.abbrevio.service.parser;

import com.en.abbrevio.model.ReactionStep;

import java.util.List;

/**
 * This interface defines the contract for e Parser Service.
 * It provides method to parse a given source into a list of Reaction Steps.
 *
 * @param <T> The type of element handled by this parser.
 * @param <E> The type of input source to be parsed.
 */


public interface ParserService<T, E> {
    /**
     * * Parses the input source and returns a list of Reaction Steps.
     *
     * @param source The input source to be parsed.
     * @return a list of ReactionStep objects based on the parsing of the source.
     */
    List<ReactionStep<T>> parse(E source);
}
