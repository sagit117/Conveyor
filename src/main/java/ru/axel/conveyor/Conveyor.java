package ru.axel.conveyor;

import ru.axel.logger.MiniLogger;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.logging.Logger;

public class Conveyor <K ,V> implements IConveyor<K, V> {
    private final Logger logger;
    private final LinkedHashMap<K, V> pipelines = new LinkedHashMap<>();

    public Conveyor() {
        logger = MiniLogger.getLogger(Conveyor.class);
    }
    public Conveyor(Logger loggerInstance) {
        logger = loggerInstance;
    }

    /**
     * Добавляет в конвейер новую фазу
     * @param phase имя фазы
     * @param pipelineExecute обработчик фазы
     */
    @Override
    public void addPipelines(K phase, V pipelineExecute) {
        pipelines.put(phase, pipelineExecute);
        logger.config("Добавлен элемент конвейера в конец списка. Имя элемента: " + phase);
    }

    /**
     * Добавляет в конвейер новую фазу, после указанной
     * @param phase имя фазы
     * @param afterPhase имя фазы после которой необходимо вставить новую
     * @param pipelineExecute обработчик фазы
     * @throws PipelinesException ошибка добавления фазы
     */
    @Override
    public void addPipelinesAfter(
        K phase,
        K afterPhase,
        V pipelineExecute
    ) throws PipelinesException {
        if (!pipelines.containsKey(afterPhase)) {
            throw new PipelinesException("Фазы " + afterPhase + " не существует!");
        }

        final LinkedHashMap<K, V> pipelinesCopy = new LinkedHashMap<>();

        pipelines.forEach((phaseName, exec) -> {
            pipelinesCopy.put(phaseName, exec);

            if (Objects.equals(phaseName, afterPhase)) {
                pipelinesCopy.put(phase, pipelineExecute);
            }
        });

        pipelines.clear();
        pipelines.putAll(pipelinesCopy);
        logger.config("Добавлен элемент конвейера после " + afterPhase + ". Имя элемента: " + phase);
    }

    /**
     * Добавляет в конвейер новую фазу, до указанной
     * @param phase имя фазы
     * @param beforePhase имя фазы до которой необходимо вставить новую
     * @param pipelineExecute обработчик фазы
     * @throws PipelinesException ошибка добавления фазы
     */
    @Override
    public void addPipelinesBefore(
        K phase,
        K beforePhase,
        V pipelineExecute
    ) throws PipelinesException {
        if (!pipelines.containsKey(beforePhase)) {
            throw new PipelinesException("Фазы " + beforePhase + " не существует!");
        }

        final LinkedHashMap<K, V> pipelinesCopy = new LinkedHashMap<>();

        pipelines.forEach((phaseName, exec) -> {
            if (Objects.equals(phaseName, beforePhase)) {
                pipelinesCopy.put(phase, pipelineExecute);
            }

            pipelinesCopy.put(phaseName, exec);
        });

        pipelines.clear();
        pipelines.putAll(pipelinesCopy);
        logger.config("Добавлен элемент конвейера перед " + beforePhase + ". Имя элемента: " + phase);
    }

    /**
     * Метод возвращает значение фазы по имени
     *
     * @param phase имя фазы
     * @return значение фазы
     */
    @Override
    public V getPhase(K phase) {
        return pipelines.get(phase);
    }

    /**
     * Метод возвращает весь конвейер
     * @return конвейер
     */
    @Override
    @SuppressWarnings("unchecked")
    public LinkedHashMap<K, V> getPipelines() {
        return (LinkedHashMap<K, V>) pipelines.clone();
    }
}
