package aeca.aladdin.service;

import aeca.aladdin.domain.entity.Group;

import java.util.Set;

/**
 * Интерфейс для работы с группаси субъектов
 */
public interface GroupService {

    /**
     * Метод возвращает все доступные группы субъектов
     *
     * @return Множество доступных групп субъектов
     */
    Set<Group> getAllGroups();

    /**
     * Метод возвращает группу субъекта с переданным идентификатором
     *
     * @param id идентификатор группы субъекта
     * @return найденная группа субъекта или null, если нет совпадений
     */
    Group getGroup(int id);
}