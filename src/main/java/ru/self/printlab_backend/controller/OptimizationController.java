package ru.self.printlab_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.self.printlab_backend.algorithm.OptimizationAlgorithm.Constraints;
import ru.self.printlab_backend.algorithm.OptimizationAlgorithm.OptimizationOutcome;
import ru.self.printlab_backend.dto.optimization.OptimizationRequest;
import ru.self.printlab_backend.dto.optimization.OptimizationResponse;
import ru.self.printlab_backend.model.ModelGeometry;
import ru.self.printlab_backend.service.OptimizationService;
import ru.self.printlab_backend.service.StlAnalysisService;

@RestController
@RequestMapping("/api/optimization")
@RequiredArgsConstructor
public class OptimizationController {

    private final OptimizationService optimizationService;
    private final StlAnalysisService stlAnalysisService;

    /**
     * Оптимизация по загруженному STL-файлу.
     */
    @PostMapping(value = "/analyze-stl", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public OptimizationResponse optimizeByStl(@RequestParam("file") MultipartFile file,
                                              OptimizationRequest request) {
        ModelGeometry geometry = stlAnalysisService.analyze(file).geometry();
        return optimizeForGeometry(geometry, request);
    }

    /**
     * Оптимизация по уже известной геометрии (например, если STL уже проанализирован).
     */
    @PostMapping("/optimize")
    public OptimizationResponse optimize(@RequestBody OptimizationBody body) {
        return optimizeForGeometry(body.geometry(), body.request());
    }

    private OptimizationResponse optimizeForGeometry(ModelGeometry geometry, OptimizationRequest request) {
        Constraints constraints = new Constraints(
                request != null ? request.maxPrintTimeMin() : 0,
                request != null ? request.maxMaterialConsumptionG() : 0);
        OptimizationOutcome outcome = optimizationService.optimize(geometry, constraints);
        return OptimizationResponse.from(outcome);
    }

    public record OptimizationBody(ModelGeometry geometry, OptimizationRequest request) {
    }
}