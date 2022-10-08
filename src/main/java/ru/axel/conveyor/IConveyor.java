package ru.axel.conveyor;

import java.util.LinkedHashMap;

/**
 * Класс хранит конвейер последовательных обработчиков
 * @param <K> ключ
 * @param <V> значение
 */
abstract class IConveyor<K, V> {
    /**
     * Добавляет в конвейер новую фазу
     * @param phase имя фазы
     * @param pipelineExecute обработчик фазы
     */
    abstract public void addPipelines(K phase, V pipelineExecute);

    /**
     * Добавляет в конвейер новую фазу, после указанной
     * @param phase имя фазы
     * @param afterPhase имя фазы после которой необходимо вставить новую
     * @param pipelineExecute обработчик фазы
     * @throws PipelinesException ошибка добавления фазы
     */
    abstract public void addPipelinesAfter(K phase, K afterPhase, V pipelineExecute) throws PipelinesException;

    /**
     * Добавляет в конвейер новую фазу, до указанной
     * @param phase имя фазы
     * @param beforePhase имя фазы до которой необходимо вставить новую
     * @param pipelineExecute обработчик фазы
     * @throws PipelinesException ошибка добавления фазы
     */
    abstract public void addPipelinesBefore(K phase, K beforePhase, V pipelineExecute) throws PipelinesException;

    /**
     * Метод возвращает значение фазы по имени
     * @param phase имя фазы
     * @return значение фазы
     */
    abstract public V getPhase(K phase);

    /**
     * Метод возвращает весь конвейер
     * @return конвейер
     */
    abstract public LinkedHashMap<K, V> getPipelines();
}
