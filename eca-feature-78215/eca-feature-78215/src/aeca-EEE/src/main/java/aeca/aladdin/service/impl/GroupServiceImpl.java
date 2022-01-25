package aeca.aladdin.service.impl;

import aeca.aladdin.domain.entity.Group;
import aeca.aladdin.service.GroupService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class GroupServiceImpl implements GroupService {

    @Override
    public Set<Group> getAllGroups() {
        return TestData.groups;
    }

    @Override
    public Group getGroup(int id) {
        Optional<Group> result = TestData.groups.stream().filter(group -> group.getId() == id).findAny();
        return result.orElse(null);
    }
}
