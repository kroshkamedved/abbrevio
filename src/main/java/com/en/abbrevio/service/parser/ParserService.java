package com.en.abbrevio.service.parser;

import com.en.abbrevio.model.ReactionStep;

import java.util.List;

public interface ParserService<T> {
    List<ReactionStep<T>> parse(String source);
}
