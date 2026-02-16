package com.logistics.jis;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jis")
public class SequenceController {

    private final SequenceService sequenceService;

    public SequenceController(SequenceService sequenceService) {
        this.sequenceService = sequenceService;
    }

    @PostMapping("/scan")
    public ResponseEntity<ProductionPart> scanPart(@RequestBody PartRequest request) {
        return ResponseEntity.ok(
                sequenceService.registerPart(request.sequence(), request.code(), request.model())
        );
    }

    @GetMapping("/plan")
    public ResponseEntity<List<ProductionPart>> getLoadingPlan() {
        return ResponseEntity.ok(sequenceService.getLoadingPlan());
    }

    @PutMapping("/load/{id}")
    public ResponseEntity<Void> confirmLoad(@PathVariable Long id) {
        sequenceService.confirmLoading(id);
        return ResponseEntity.ok().build();
    }

    public record PartRequest(Integer sequence, String code, String model) {}
}