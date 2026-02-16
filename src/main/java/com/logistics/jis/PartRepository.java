package com.logistics.jis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<ProductionPart, Long> {
    Optional<ProductionPart> findBySequenceNumber(Integer sequenceNumber);
    List<ProductionPart> findAllByOrderBySequenceNumberAsc();
    List<ProductionPart> findByLoadedFalseOrderBySequenceNumberAsc();
}