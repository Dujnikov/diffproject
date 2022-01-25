package aeca.aladdin.service;

import aeca.aladdin.domain.entity.Template;

import java.util.Set;

/**
 * Интерфейс для работы с шаблонами сертификата
 */
public interface TemplateService {

    /**
     * Метод возвращает все доступные шаблоны сертификатов
     *
     * @return Множество доступных шаблонов
     */
    Set<Template> getAllTemplates();

    /**
     * Метод возвращает шаблон с переданным идентификатором
     *
     * @param id идентификатор шаблона сертификата
     * @return найденый шаблон сертификата или null, если нет совпадений
     */
    Template getTemplate(int id);
}