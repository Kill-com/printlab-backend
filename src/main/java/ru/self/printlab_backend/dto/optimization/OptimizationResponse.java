package ru.self.printlab_backend.dto.optimization;

import ru.self.printlab_backend.algorithm.OptimizationAlgorithm.OptimizationOutcome;

public record OptimizationResponse(
        double recommendedInfillPercent,
        double expectedCompressiveStrengthMpa,
        double expectedPrintTimeMin,
        double expectedMaterialConsumptionG,
        String explanation
) {
    public static OptimizationResponse from(OptimizationOutcome outcome) {
        return new OptimizationResponse(
                outcome.recommendedInfillPercent(),
                outcome.expectedCompressiveStrengthMpa(),
                outcome.expectedPrintTimeMin(),
                outcome.expectedMaterialConsumptionG(),
                outcome.explanation());
    }
}