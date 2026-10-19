package ru.self.printlab_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.self.printlab_backend.algorithm.OptimizationAlgorithm;
import ru.self.printlab_backend.algorithm.OptimizationAlgorithm.Constraints;
import ru.self.printlab_backend.algorithm.OptimizationAlgorithm.OptimizationOutcome;
import ru.self.printlab_backend.model.ExperimentResult;
import ru.self.printlab_backend.model.ModelGeometry;
import ru.self.printlab_backend.repository.ExperimentRepository;

import java.util.List;

/**
 * Связывает STL-анализ, экспериментальные данные и алгоритм оптимизации.
 */
@Service
@RequiredArgsConstructor
public class OptimizationService {

    private final ExperimentRepository experimentRepository;
    private final OptimizationAlgorithm optimizationAlgorithm;

    public OptimizationOutcome optimize(ModelGeometry geometry, Constraints constraints) {
        List<ExperimentResult> experiments = experimentRepository.findAllExperiments();
        return optimizationAlgorithm.optimize(geometry, experiments, constraints);
    }
}