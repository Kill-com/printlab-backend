package ru.self.printlab_backend.dto.model;

import lombok.Builder;
import ru.self.printlab_backend.model.ModelGeometry;
import ru.self.printlab_backend.model.ResearchModel;

@Builder
public record ModelAnalysisResponse(
        ModelGeometry geometry,
        String matchedModelId,
        String matchedModelName,
        boolean matched
) {
    public static ModelAnalysisResponse of(ModelGeometry geometry, ResearchModel model) {
        return ModelAnalysisResponse.builder()
                .geometry(geometry)
                .matched(model != null)
                .matchedModelId(model != null ? model.getId() : null)
                .matchedModelName(model != null ? model.getName() : null)
                .build();
    }
}