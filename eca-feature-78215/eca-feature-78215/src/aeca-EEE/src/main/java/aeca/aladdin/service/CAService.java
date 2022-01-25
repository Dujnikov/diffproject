package aeca.aladdin.service;

import aeca.aladdin.domain.dto.TypeCAEnum;
import aeca.aladdin.domain.entity.CAInfo;
import aeca.aladdin.domain.entity.Certificate;

import java.util.Set;

/**
 * Интерфейс для работы с УЦ
 */
public interface CAService {

    /**
     * Метод возвращает информацию об УЦ для переданного идентификатора
     *
     * @param id идентификатор УЦ
     * @return информация о найденом УЦ или null, если не удалось найти УЦ с переданным идентификатором
     */
    CAInfo getCA(int id);

    /**
     * Метод возвращает множетсво всех доступных УЦ
     *
     * @return информация о всех найденных УЦ или null, если не удалось найти ни одного УЦ
     * @deprecated устарел в связи с концепцией единственного УЦ на одном инстансе
     */
    @Deprecated
    Set<CAInfo> getAllCA();

    /**
     * Метод возвращает множество сертификатов для переданного идентификатора УЦ
     *
     * @param id идентификатор УЦ
     * @return найденые сертификаты указаного УЦ или null, если не найдет УЦ по идентификатору, нет доступных сертификатов
     */
    Set<Certificate> getAllCertificateByActiveCA(int id);

    /**
     * Метод создаец УЦ и возвращает информацию о созданном УЦ
     *
     * @param encryptionType строковое описание криптографии
     * @param type           тип УЦ (ROOTCA(1), SUBCA(2))
     * @param name           имя УЦ
     * @param subjectDN      Отличимое имя УЦ
     * @param subjectAltName Отличимый суффикс УЦ
     * @param hash           хэш сумма УЦ
     * @param keyLength      длинна клюса УЦ
     * @param validity       срок действия УЦ
     * @return информация о созданном УЦ или null, если не удалось создать УЦ
     */
    CAInfo createCA(String encryptionType, TypeCAEnum type, String name, String subjectDN,
                    String subjectAltName, String hash, Integer keyLength, Long validity);

    /**
     * Метод актифирующий УЦ по идентификатору
     *
     * @param id идентификатор УЦ
     * @return true - УЦ активирован, остальные УЦ деактивированы, false - УЦ не найден, УЦ уже активен, не удалось активировать указанный УЦ, не удалось деактивировать остальные УЦ
     * @deprecated устарел в связи с концепцией единственного УЦ на одном инстансе
     */
    @Deprecated
    boolean activateCA(int id);

    /**
     * Метод формирующий файл-сертификат для УЦ
     *
     * @param id         идентификатор УЦ
     * @param fileFormat формат необходимого сертификата (crt, crl, csr)
     * @return Файл в формате base64 или null, если не удалось найти УЦ или не удалось сформировать файл
     */
    String getFile(int id, String fileFormat);

    /**
     * Метод принимающий файл-ответ для подписания и активации сертификата УЦ
     *
     * @param id   идентификатор УЦ
     * @param file файд-ответ в формате base64
     * @return true - УЦ успешно подписан и активирован, false - УЦ не найден, файл-ответ не соответствует УЦ, не удалось изменить статус сертификата УЦ
     */
    boolean importResponse(int id, String file);

    /**
     * Метод изменяющий точки распространения УЦ
     *
     * @param id                   идентификатор УЦ
     * @param aiaPublicationPoint  адрес публикации AIA
     * @param aiaDistributionPoint адрес распространения AIA
     * @param crlPublicationPoint  адрес публикации CRL
     * @param crlDistributionPoint адрес распространения CRL
     * @return true - успешно сохранены изменения в УЦ, false - не удалось изменить данные в УЦ
     */
    boolean editPublicationPoint(int id, String aiaPublicationPoint, String aiaDistributionPoint, String crlPublicationPoint, String crlDistributionPoint);

    /**
     * Метод публикации AIA и CRL
     *
     * @param id   идентификатор УЦ
     * @param type тип данных, требующих публикации (crl, aia)
     * @return 200 - успешная публикация, 4** - ошибка публикации
     */
    int publication(int id, String type);
}