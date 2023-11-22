package com.en.abbrevio.repository;

import com.en.abbrevio.model.Molecule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoleculeRepository extends JpaRepository<Molecule, Long> {
    Optional<Molecule> getByName(String name);
}
