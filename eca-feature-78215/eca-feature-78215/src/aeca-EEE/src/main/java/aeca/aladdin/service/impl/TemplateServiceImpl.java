package aeca.aladdin.service.impl;

import aeca.aladdin.domain.entity.Template;
import aeca.aladdin.service.TemplateService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Override
    public Set<Template> getAllTemplates() {
        return TestData.templates;
    }

    @Override
    public Template getTemplate(int id) {
        Optional<Template> result = TestData.templates.stream().filter(template -> template.getId() == id).findAny();
        return result.orElse(null);
    }
}
