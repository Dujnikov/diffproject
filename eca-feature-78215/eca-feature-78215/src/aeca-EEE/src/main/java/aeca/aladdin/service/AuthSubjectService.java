package aeca.aladdin.service;

import aeca.aladdin.domain.entity.AuthSubject;

import java.util.Set;

/**
 * Интерфейс для работы с субъектом аутентификации
 */
public interface AuthSubjectService {

    /**
     * Метод возвращает множество субъектов аутентификации
     *
     * @return Множество доступных субъектов
     */
    Set<AuthSubject> getAllAuthSubject();

    /**
     * Метод возвращает субъект аутентификации с переданным идентификатором
     *
     * @param id идентификатор субъекта аутентификации
     * @return найденный субъект аутентификации или null, если нет совпадений
     */
    AuthSubject getAuthSubject(int id);

    /**
     * Метод создает субъект аутентификации
     *
     * @param subjectDN      отличимое имя субъекта аутентификации
     * @param subjectAltName отличимый суффикс субъекта аутентификации
     * @param groupIds       множество ассоциируемых групп субъектов
     * @return идентификатор созданного субъекта аутентификации или 0, если переданные параметры не уникальны или не удалось создать субъект аутентификации
     */
    int createAuthSubject(String subjectDN, String subjectAltName, Set<Integer> groupIds);

    /**
     * Метод изменяет субъект аутентификации
     *
     * @param id             идентификатор субъекта
     * @param subjectDN      отличимое имя субъекта аутентификации
     * @param subjectAltName отличимый суффикс субъекта аутентификации
     * @return true - субъект успешно изменен, false - субъект не найден, переданные параметры не уникальны, произошла ошибка записи изменений
     */
    boolean editAuthSubject(int id, String subjectDN, String subjectAltName);

    /**
     * Метод добавляет группу субъектов к указанному субъекту аутентификации
     *
     * @param subjectId идентификатор субъекта
     * @param groupId   идентификатор группы
     * @return true - субъекту успешно добавлена новая группа, false - субъект не найден, группа не найдена, не удалось записать изменения
     */
    boolean addGroupToSubject(int subjectId, int groupId);

    /**
     * Метод удаляет группу субъектов из указанного субъекта аутентификации
     *
     * @param subjectId идентификатор субъекта
     * @param groupId   идентификатор группы
     * @return true - у субъекта успешно удалена группа, false - субъект не найден, группа не найдена, не удалось записать изменения
     */
    boolean removeSubjectToGroup(int subjectId, int groupId);

    /**
     * Метод добавляет субъекту множество указанных групп, заменяя существующие
     *
     * @param subjectId идентификатор субъекта
     * @param groupIds  множество идентификаторов групп субъектов
     * @return true - субъекту успешно добавлены группы, false - субъект не найден, группы не найдена, не удалось записать изменения
     */
    boolean setGroupsToSubject(int subjectId, Set<Integer> groupIds);

    /**
     * Метод удаляет субъект аутентификации с переданным идентификатором
     *
     * @param id идентификатор субъекта
     * @return true - субъект успешно удален, false - субъект не найден, не удалось удалить субъект
     */
    boolean deleteSubject(int id);
}