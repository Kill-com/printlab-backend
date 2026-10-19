package ru.self.printlab_backend.dto.optimization;

/**
 * Параметры расчёта, которые присылает frontend.
 * Значения <= 0 означают «без ограничения».
 */
public record OptimizationRequest(
        double maxPrintTimeMin,
        double maxMaterialConsumptionG
) {
}