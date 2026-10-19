package ru.self.printlab_backend.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.self.printlab_backend.model.ExperimentResult;
import ru.self.printlab_backend.model.ModelGeometry;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Подбирает оптимальный процент заполнения в пределах исследованных моделей
 * и сопоставимых условий испытаний.
 *
 * <p>Логика MVP: берём только эксперименты с известным процентом заполнения,
 * фильтруем по ограничениям пользователя, сортируем по «прочности на единицу
 * затрат» и возвращаем лучший вариант.</p>
 */
@Slf4j
@Component
public class ExperimentalOptimizationAlgorithm implements OptimizationAlgorithm {

    @Override
    public OptimizationOutcome optimize(ModelGeometry geometry,
                                        List<ExperimentResult> experiments,
                                        Constraints constraints) {
        List<ExperimentResult> candidates = experiments.stream()
                .filter(e -> e.getInfillPercent() > 0)
                .filter(e -> constraints.maxPrintTimeMin() <= 0
                        || e.getPrintTimeMin() <= constraints.maxPrintTimeMin())
                .filter(e -> constraints.maxMaterialConsumptionG() <= 0
                        || e.getMaterialConsumptionG() <= constraints.maxMaterialConsumptionG())
                .sorted(Comparator.comparingDouble(this::efficiencyScore).reversed())
                .toList();

        if (candidates.isEmpty()) {
            throw new IllegalStateException(
                    "Нет экспериментов, удовлетворяющих ограничениям. Ослабьте условия.");
        }

        ExperimentResult best = candidates.get(0);
        log.debug("Выбран эксперимент {}: infill={}%, strength={} МПа",
                best.getId(), best.getInfillPercent(), best.getCompressiveStrengthMpa());

        String explanation = String.format(
                "Выбрана исследованная конфигурация %s: заполнение %.1f%%, ожидаемая прочность %.2f МПа, "
                        + "время печати %.1f мин, расход %.2f г. В пределах допустимых ограничений.",
                best.getId(), best.getInfillPercent(), best.getCompressiveStrengthMpa(),
                best.getPrintTimeMin(), best.getMaterialConsumptionG());

        return new OptimizationOutcome(
                best.getInfillPercent(),
                best.getCompressiveStrengthMpa(),
                best.getPrintTimeMin(),
                best.getMaterialConsumptionG(),
                explanation);
    }

    /** Прочность на единицу затрат: чем выше, тем лучше. */
    private double efficiencyScore(ExperimentResult e) {
        double cost = Math.max(e.getMaterialConsumptionG(), 0.001)
                * Math.max(e.getPrintTimeMin(), 0.001);
        return e.getCompressiveStrengthMpa() / Math.sqrt(cost);
    }
}