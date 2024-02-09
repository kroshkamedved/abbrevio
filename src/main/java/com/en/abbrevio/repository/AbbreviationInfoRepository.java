package com.en.abbrevio.repository;

import com.en.abbrevio.model.AbbreviationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbbreviationInfoRepository extends JpaRepository<AbbreviationInfo, Long> {
    @Query("SELECT ai FROM AbbreviationInfo ai WHERE UPPER(ai.synonym) = :synonym")
    Optional<AbbreviationInfo> getBySynonym(String synonym);
}
