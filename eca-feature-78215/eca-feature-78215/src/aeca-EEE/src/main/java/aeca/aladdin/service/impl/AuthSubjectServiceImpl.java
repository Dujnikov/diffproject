package aeca.aladdin.service.impl;

import aeca.aladdin.domain.entity.AuthSubject;
import aeca.aladdin.service.AuthSubjectService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthSubjectServiceImpl implements AuthSubjectService {

    @Override
    public Set<AuthSubject> getAllAuthSubject() {
        return TestData.subjects;
    }

    @Override
    public AuthSubject getAuthSubject(int id) {
        Optional<AuthSubject> result = TestData.subjects.stream().filter(authSubject -> authSubject.getId() == id).findAny();
        return result.orElse(null);
    }

    @Override
    public int createAuthSubject(String subjectDN, String subjectAltName, Set<Integer> groupIds) {
        //todo проверка уникальности имен, запись
//        int id = new Random().nextInt();
//        return id > 0 ? id : id * -1;
        return 5;
    }

    @Override
    public boolean editAuthSubject(int id, String subjectDN, String subjectAltName) {
        return id != 0;
        //todo изменение субъекта
    }

    @Override
    public boolean addGroupToSubject(int subjectId, int groupId) {
        return subjectId != 0 && groupId != 0;
        //todo добавление группы
    }

    @Override
    public boolean removeSubjectToGroup(int subjectId, int groupId) {
        return subjectId != 0 && groupId != 0;
        //todo удаление группы
    }

    @Override
    public boolean setGroupsToSubject(int subjectId, Set<Integer> groupIds) {
        return subjectId != 0 && groupIds != null && !groupIds.isEmpty();
    }

    @Override
    public boolean deleteSubject(int id) {
        return id != 0;
        //todo удаление субъекта
    }
}