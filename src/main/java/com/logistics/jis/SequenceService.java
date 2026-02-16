package com.logistics.jis;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SequenceService {

    private final PartRepository partRepository;

    public SequenceService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    @Transactional
    public ProductionPart registerPart(Integer sequence, String code, String model) {
        if (partRepository.findBySequenceNumber(sequence).isPresent()) {
            throw new IllegalArgumentException("Sequence number already exists");
        }
        return partRepository.save(new ProductionPart(sequence, code, model));
    }

    public List<ProductionPart> getLoadingPlan() {
        return partRepository.findByLoadedFalseOrderBySequenceNumberAsc();
    }

    @Transactional
    public void confirmLoading(Long id) {
        ProductionPart part = partRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Part not found"));
        part.setLoaded(true);
        partRepository.save(part);
    }
}