package com.logistics.jis;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "production_parts")
public class ProductionPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer sequenceNumber;

    @Column(nullable = false)
    private String partCode;

    @Column(nullable = false)
    private String carModel;

    private LocalDateTime scannedAt;

    private boolean loaded;

    public ProductionPart() {}

    public ProductionPart(Integer sequenceNumber, String partCode, String carModel) {
        this.sequenceNumber = sequenceNumber;
        this.partCode = partCode;
        this.carModel = carModel;
        this.scannedAt = LocalDateTime.now();
        this.loaded = false;
    }

    public Long getId() { return id; }
    public Integer getSequenceNumber() { return sequenceNumber; }
    public String getPartCode() { return partCode; }
    public String getCarModel() { return carModel; }
    public LocalDateTime getScannedAt() { return scannedAt; }
    public boolean isLoaded() { return loaded; }

    public void setLoaded(boolean loaded) { this.loaded = loaded; }
}