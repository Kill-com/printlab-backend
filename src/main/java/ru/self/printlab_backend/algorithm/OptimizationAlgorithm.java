package ru.self.printlab_backend.algorithm;

import ru.self.printlab_backend.model.ExperimentResult;
import ru.self.printlab_backend.model.ModelGeometry;

import java.util.List;

/**
 * Контракт алгоритма подбора оптимального процента заполнения.
 */
public interface OptimizationAlgorithm {

    OptimizationOutcome optimize(ModelGeometry geometry, List<ExperimentResult> experiments, Constraints constraints);

    record Constraints(double maxPrintTimeMin, double maxMaterialConsumptionG) {
    }

    record OptimizationOutcome(double recommendedInfillPercent,
                               double expectedCompressiveStrengthMpa,
                               double expectedPrintTimeMin,
                               double expectedMaterialConsumptionG,
                               String explanation) {
    }
}