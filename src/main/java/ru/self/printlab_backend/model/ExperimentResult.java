package ru.self.printlab_backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Результат одного физического эксперимента.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperimentResult {

    private String id;
    private String modelId;
    private String material;

    /** Процент заполнения, %. */
    private double infillPercent;

    /** Прочность на сжатие, МПа. */
    private double compressiveStrengthMpa;

    /** Толщина стенки, мм. */
    private double wallThicknessMm;

    /** Высота слоя, мм. */
    private double layerHeightMm;

    /** Температура сопла, °C. */
    private double nozzleTemperatureC;

    /** Скорость печати, мм/с. */
    private double printSpeedMmS;

    /** Время печати, мин. */
    private double printTimeMin;

    /** Расход материала, г. */
    private double materialConsumptionG;
}