package com.en.abbrevio.repository;

import com.en.abbrevio.model.AbbreviationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbbreviationInfoRepository extends JpaRepository<AbbreviationInfo, Long> {
    Optional<AbbreviationInfo> getBySynonym(String synonym);
}
