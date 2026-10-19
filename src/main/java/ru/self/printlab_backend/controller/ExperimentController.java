package ru.self.printlab_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.self.printlab_backend.dto.experiment.ExperimentResponse;
import ru.self.printlab_backend.dto.experiment.ResearchModelResponse;
import ru.self.printlab_backend.model.ExperimentResult;
import ru.self.printlab_backend.service.ExperimentService;

import java.util.List;

@RestController
@RequestMapping("/api/experiments")
@RequiredArgsConstructor
public class ExperimentController {

    private final ExperimentService experimentService;

    @GetMapping
    public List<ExperimentResponse> findAll(@RequestParam(required = false) String modelId) {
        List<ExperimentResult> results = (modelId == null || modelId.isBlank())
                ? experimentService.findAll()
                : experimentService.findByModelId(modelId);
        return results.stream().map(ExperimentResponse::from).toList();
    }

    @GetMapping("/models")
    public List<ResearchModelResponse> researchModels() {
        return experimentService.findAllResearchModels().stream()
                .map(ResearchModelResponse::from)
                .toList();
    }

    @PostMapping
    public ExperimentResponse save(@RequestBody ExperimentResult result) {
        return ExperimentResponse.from(experimentService.save(result));
    }
}