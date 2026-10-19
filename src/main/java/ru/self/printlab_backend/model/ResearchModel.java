package ru.self.printlab_backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Исследованный образец: модель, для которой уже проводились физические эксперименты.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResearchModel {

    private String id;
    private String name;
    private String material;
    private ModelGeometry geometry;
}