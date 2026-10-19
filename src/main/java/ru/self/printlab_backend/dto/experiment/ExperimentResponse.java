package ru.self.printlab_backend.dto.experiment;

import ru.self.printlab_backend.model.ExperimentResult;

public record ExperimentResponse(
        String id,
        String modelId,
        String material,
        double infillPercent,
        double compressiveStrengthMpa,
        double printTimeMin,
        double materialConsumptionG
) {
    public static ExperimentResponse from(ExperimentResult e) {
        return new ExperimentResponse(
                e.getId(), e.getModelId(), e.getMaterial(), e.getInfillPercent(),
                e.getCompressiveStrengthMpa(), e.getPrintTimeMin(), e.getMaterialConsumptionG());
    }
}