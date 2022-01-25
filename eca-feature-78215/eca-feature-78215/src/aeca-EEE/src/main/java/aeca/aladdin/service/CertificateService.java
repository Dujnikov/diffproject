package aeca.aladdin.service;

import aeca.aladdin.domain.entity.Certificate;

/**
 * Интерфейс для работы с сертификатами
 */
public interface CertificateService {

    /**
     * Метод возвращает группу субъекта с переданным Id
     *
     * @param certSerialNumber серийный номер сертификата в строковом представлении
     * @return информация о найденом сертификате или null, если нет совпадений
     */
    Certificate getCertificate(String certSerialNumber);

    /**
     * Метод отзывает сертификат при условии, что серийному номеру соответствует сертификат, сертификат активен
     *
     * @param certSerialNumber серийный номер сертификата в строковом представлении
     * @param code             код отзыва сертификата (CERTIFICATE_ACTIVE(-1), CERTIFICATE_HOLD(1), UNSPECIFIED(2), KEY_COMPROMISE(3), CA_COMPROMISE(4), AFFILIATION_CHANGED(5), SUPERSEDED(6), CESSATION_OF_OPERATION(7), PRIVILEGES_WITH_DRAWN(8), AA_COMPROMISE(9))
     * @return true - сертификат найден, первоначальный статус - активен, отзыв произошел успешно, false - не выполнено одно из условий
     */
    boolean certificateRevoke(String certSerialNumber, int code);

    /**
     * Метод приостонавливает действие сертификата при условии, что серийному номеру соответствует сертификат, сертификат активен
     *
     * @param certSerialNumber серийный номер сертификата в строковом представлении
     * @return true - сертификат найден, первоначальный статус - активен, отзыв произошел успешно, false - не выполнено одно из условий
     */
    boolean certificateReactivate(String certSerialNumber);

    /**
     * Метод создания сертификата по запросу
     *
     * @param certFormat    формат создаваемого/выпускаемого сертификата
     * @param templateId    идентификатор шаблона сертификатов
     * @param authSubjectId идентификатор субъекта, с которым будет ассоциирован созданный сертификат
     * @param file          файл запрос, содержащий данные, необходимые для создания сертификата
     * @return Созданные сертификат или null, если не пройдена проверка связи шаблона с субъектом, не достаточно данных в файле, дублирование уникальных данных
     */
    Certificate createCertToCsr(String certFormat, int templateId, int authSubjectId, String file);

    /**
     * Метод создания сертификата и субъекта по запросу
     *
     * @param certFormat формат создаваемого/выпускаемого сертификата
     * @param templateId идентификатор шаблона сертификатов
     * @param groupId    идентификатор группы, с которым будет ассоциирован созданный субъект
     * @param file       файл запрос, содержащий данные, необходимые для создания сертификата
     * @return Созданные сертификат или null, если не пройдена проверка связи шаблона с группой, не достаточно данных в файле, дублирование уникальных данных
     */
    Certificate createSubjectAndCertToCsr(String certFormat, int templateId, int groupId, String file);
}