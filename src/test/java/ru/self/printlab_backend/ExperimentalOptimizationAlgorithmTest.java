package ru.self.printlab_backend;

import org.junit.jupiter.api.Test;
import ru.self.printlab_backend.algorithm.ExperimentalOptimizationAlgorithm;
import ru.self.printlab_backend.algorithm.OptimizationAlgorithm.Constraints;
import ru.self.printlab_backend.algorithm.OptimizationAlgorithm.OptimizationOutcome;
import ru.self.printlab_backend.model.ExperimentResult;
import ru.self.printlab_backend.model.ModelGeometry;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExperimentalOptimizationAlgorithmTest {

    private final ExperimentalOptimizationAlgorithm algorithm = new ExperimentalOptimizationAlgorithm();
    private final ModelGeometry geometry = ModelGeometry.builder().volumeMm3(8000).build();

    @Test
    void picksBestExperimentWithinConstraints() {
        ExperimentResult light = ExperimentResult.builder()
                .id("a").infillPercent(20).compressiveStrengthMpa(18)
                .printTimeMin(40).materialConsumptionG(8).build();
        ExperimentResult heavy = ExperimentResult.builder()
                .id("b").infillPercent(80).compressiveStrengthMpa(45)
                .printTimeMin(120).materialConsumptionG(30).build();

        OptimizationOutcome outcome = algorithm.optimize(
                geometry, List.of(light, heavy), new Constraints(60, 0));

        assertEquals(20.0, outcome.recommendedInfillPercent());
    }

    @Test
    void throwsWhenNoExperimentFits() {
        ExperimentResult heavy = ExperimentResult.builder()
                .id("b").infillPercent(80).compressiveStrengthMpa(45)
                .printTimeMin(120).materialConsumptionG(30).build();

        assertThrows(IllegalStateException.class, () ->
                algorithm.optimize(geometry, List.of(heavy), new Constraints(10, 0)));
    }
}